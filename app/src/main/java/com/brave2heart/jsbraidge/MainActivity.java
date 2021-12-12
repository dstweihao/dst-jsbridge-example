package com.brave2heart.jsbraidge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private BridgeWebView bridgeWebview;
    private String TAG="MainActivity";
    private Button btCallJS;
    private TextView tvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }
    private void initData() {
        bridgeWebview.registerHandler("jsCallAndroid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                tvContent.setText("params from JavaScript: " + data);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nickname", "braveheart");
                    jsonObject.put("age", "18");
                    jsonObject.put("address", "China");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack("return data from Android: "+jsonObject.toString());
            }
        });
        btCallJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bridgeWebview.callHandler("functionInJs","{ name: \"weihao\" }", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        tvContent.setText("return data from JavaScript: "+data);
                    }
                });
            }
        });
        bridgeWebview.loadUrl("file:///android_asset/demo.html");

    }

    private void initView() {
        bridgeWebview = (BridgeWebView)findViewById(R.id.bridgeWebview);
        btCallJS = (Button)findViewById(R.id.bt_call_js);
        tvContent = (TextView)findViewById(R.id.tv_content);

    }

}

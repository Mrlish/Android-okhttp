package com.example.li.myapplication;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
/*
GET请求
 */
    private static final int GET = 1;
    private Button Get;
 //   private Button Post;
    private String string;
    private TextView text1;
    private EditText mbtUserNamr;
    private OkHttpClient client = new OkHttpClient();

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET:
                    text1.setText((String) msg.obj);
                    break;
        }
    }
  };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Get=findViewById(R.id.b1);
       // Post=findViewById(R.id.b2);
        text1=findViewById(R.id.V1);
        Get.setOnClickListener(this);

        mbtUserNamr=findViewById(R.id.L1);
        mbtUserNamr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                string=s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b1: //使用原生
                getDataFromGet();
                break;
        }
    }
    private void getDataFromGet(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result =get(string);
                    Log.e("TAG",result);
                    Message msg =Message.obtain();
                    msg.what=GET;
                    msg.obj=result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

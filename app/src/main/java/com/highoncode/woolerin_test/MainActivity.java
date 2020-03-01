package com.highoncode.woolerin_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    TextView mTextView1,mTextView2;
    String _res="",_req="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView1=findViewById(R.id.textView1);
        mTextView2=findViewById(R.id.textView2);



        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.prokerala.com/v1/astrology/manglik?ayanamsa=1&coordinates=10.214747,78.097626&datetime=2004-02-12T15:19:21%2B00:00")
                .get()
                .addHeader("Authorization", "Bearer e56769488793cea2c47c14d37a27b539af3f1710ea5b9efeec9c8c145dd59230")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse =response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (myResponse != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject(myResponse);
                                     _req = jsonObj.get("request").toString();
                                    Log.i("TAG",_req);
                                     _res = jsonObj.get("response").toString();
                                    Log.i("TAG",_res);
                                    mTextView1.setText("Request"+_req);
                                    mTextView2.setText("Response"+_res);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });

    }

}

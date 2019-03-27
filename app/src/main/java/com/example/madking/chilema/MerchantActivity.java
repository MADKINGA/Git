package com.example.madking.chilema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.madking.model.Merchant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MerchantActivity extends AppCompatActivity {

    private TextView type;
    private List<Merchant> merchantslist;
    private ListView merchants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        type=(TextView) findViewById(R.id.type);
        merchants=(ListView)findViewById(R.id.merchants);

        Intent intent=getIntent();
        String typename=intent.getStringExtra("typename");
        type.setText(typename);
        int id=intent.getIntExtra("typeid",0);
        String url="http://10.219.162.224:8080/meituan/getMerchants";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json=response.body().string();
                Gson gson=new Gson();

                merchantslist= gson.fromJson(json, new TypeToken<List<Merchant>>(){}.getType());

                List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
                for(Merchant merchant:merchantslist)
                {
                    Map<String,Object> map=new HashMap<String, Object>();

                    int img=MerchantActivity.this.getResources().getIdentifier(
                            merchant.getImg().split("\\.")[0],
                            "drawable",
                            MerchantActivity.this.getPackageName());
                    //map.put()
                    map.put("img", img);
                    map.put("name", merchant.getName());
                    map.put("hot", merchant.getName());
                    map.put("month", "月售"+ merchant.getMonth()+"单");
                    map.put("startprice", "¥"+ merchant.getStartprice()+"起送 | ");
                    map.put("evaluation", "评价  "+ merchant.getFee());
                    data.add(map);
                }

                final SimpleAdapter Adapter=new SimpleAdapter(
                        MerchantActivity.this,
                        data,
                        R.layout.merchant_view,
                        new String[]{"img","name","hot","month","startprice","evaluation"},
                        new int[]{R.id.merchant_img,R.id.merchant_name,R.id.merchant_hot,R.id.merchant_hot,
                                R.id.merchant_startprice,R.id.merchant_evaluation});

                merchants.post(new Runnable() {

                    @Override
                    public void run() {
                        merchants.setAdapter(Adapter);
                    }
                });
            }
        });
    }
}

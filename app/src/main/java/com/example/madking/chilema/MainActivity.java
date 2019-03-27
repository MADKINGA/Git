package com.example.madking.chilema;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.madking.View.MyGridView;
import com.example.madking.View.MyListView;
import com.example.madking.adapter.RecommendAdapter;
import com.example.madking.adapter.ViewPagerAdapter;
import com.example.madking.model.Merchant;
import com.example.madking.model.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private MyGridView typeview;
    private List<Type> typelist;
    private MyListView merchantview;
    private List<Merchant> merchantlist;
    private List<Merchant> recommendlist;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private RecommendAdapter recommendAdapter;
    private int[] imagesid=new int[]{
            R.drawable.meituan1,
            R.drawable.meituan2,
            R.drawable.meituan3
    };

    private List<ImageView> images;

    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem;

    private RecyclerView recyclerView;

    private TextView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeview = (MyGridView) findViewById(R.id.typeview);
        merchantview=(MyListView)findViewById(R.id.merchantview);
        mViewPager=(ViewPager)findViewById(R.id.vp);
        recyclerView=(RecyclerView) findViewById(R.id.recommend);
        searchView=(TextView)findViewById(R.id.edt_search);


        //typeview
        //String url = "http://192.168.43.94:8080/meituan/getTypes";
        String url = "http://10.219.162.224:8080/meituan/getTypes";
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                typelist = gson.fromJson(json, new TypeToken<List<Type>>(){}.getType());

                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                for (Type type : typelist) {
                    Map<String, Object> map = new HashMap<String, Object>();

                    int img = MainActivity.this.getResources().getIdentifier(
                            type.getPicture().split("\\.")[0],
                            "drawable",
                            MainActivity.this.getPackageName());

                    map.put("img", img);
                    map.put("name", type.getName());
                    data.add(map);
                }

                final SimpleAdapter Adapter = new SimpleAdapter(
                        MainActivity.this,
                        data,
                        R.layout.type_view,
                        new String[]{"img", "name"},
                        new int[]{R.id.typeImg, R.id.typeName});

                typeview.post(new Runnable() {
                    @Override
                    public void run() {
                        typeview.setAdapter(Adapter);
                    }
                });
            }
        });


        //merchantview
        //String murl = "http://192.168.43.94:8080/meituan/getMerchants";
        String murl = "http://10.219.162.224:8080/meituan/getMerchants";
        OkHttpClient mclient = new OkHttpClient();
        final Request mrequest = new Request.Builder()
                .url(murl)
                .get()
                .build();

        mclient.newCall(mrequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                merchantlist = gson.fromJson(json, new TypeToken<List<Merchant>>(){}.getType());

                List<Map<String, Object>> mdata = new ArrayList<Map<String, Object>>();
                for (Merchant merchant : merchantlist) {
                    Map<String, Object> map = new HashMap<String, Object>();

                    int img = MainActivity.this.getResources().getIdentifier(
                            merchant.getImg().split("\\.")[0],
                            "drawable",
                            MainActivity.this.getPackageName());

                    map.put("img", img);
                    map.put("name", merchant.getName());
                    map.put("hot", merchant.getName());
                    map.put("month", "月售"+ merchant.getMonth()+"单");
                    map.put("startprice", "¥"+ merchant.getStartprice()+"起送 | ");
                    map.put("evaluation", "评价  "+ merchant.getFee());
                    mdata.add(map);
                }

                final SimpleAdapter mAdapter = new SimpleAdapter(
                        MainActivity.this,
                        mdata,
                        R.layout.merchant_view,
                        new String[]{"img","name","hot","month","startprice","evaluation"},
                        new int[]{R.id.merchant_img,R.id.merchant_name,R.id.merchant_hot,R.id.merchant_hot,
                                R.id.merchant_startprice,R.id.merchant_evaluation});

                merchantview.post(new Runnable() {
                    @Override
                    public void run() {
                        merchantview.setAdapter(mAdapter);
                    }
                });
            }
        });



        //lunbo
        images=new ArrayList<ImageView>();
        for(int i=0;i<imagesid.length;i++)
        {
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(imagesid[i]);
            images.add(imageView);
        }

        viewPagerAdapter=new ViewPagerAdapter(images,mViewPager);
        mViewPager.setAdapter(viewPagerAdapter);


        //recommend
        //String rurl="http://192.168.43.94:8080/meituan/getMerchants";
        String rurl="http://10.219.162.224:8080/meituan/getMerchants";
        OkHttpClient rclient=new OkHttpClient();
        Request rrequest=new Request.Builder()
                .url(rurl)
                .get()
                .build();

        rclient.newCall(rrequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Gson gson=new Gson();
                recommendlist=gson.fromJson(json,new TypeToken<List<Merchant>>(){}.getType());

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recommendAdapter = new RecommendAdapter(recommendlist, MainActivity.this);
                        recyclerView.setAdapter(recommendAdapter);
                    }
                });
            }
        });


        //搜索点击


        typeview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Type type=typelist.get(position);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MerchantActivity.class);
                intent.putExtra("typename", type.getName());
                intent.putExtra("typeid", type.getId());
                startActivity(intent);
            }
        });

    }

    protected void onStart() {
        super.onStart();

        scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(viewPagerAdapter.new ViewPageTask(),3,3,TimeUnit.SECONDS);
    }

}

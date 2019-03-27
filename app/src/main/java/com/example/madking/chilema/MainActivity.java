package com.example.madking.chilema;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.madking.View.LoadView;
import com.example.madking.View.MyGridView;
import com.example.madking.View.MyListView;
import com.example.madking.adapter.RecommendAdapter;
import com.example.madking.adapter.ViewPagerAdapter;
import com.example.madking.model.Class;
import com.example.madking.model.Store;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private MyGridView classview;
    private List<Class> classlist;
    private MyListView storeview;
    private List<Store> storelist;
    private List<Store> recommendlist;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private RecommendAdapter recommendAdapter;
    private int[] imagesid=new int[]{
            R.drawable.lunbo1,
            R.drawable.lunbo2,
            R.drawable.lunbo3
    };

    private List<ImageView> images;

    private ScheduledExecutorService scheduledExecutorService;

    private RecyclerView recyclerView;

    private TextView searchView;

    private Button minprice;
    private Button hot;
    private Button evaluation;
    private int m=0,h=0,e=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classview = (MyGridView) findViewById(R.id.classview);
        storeview=(MyListView)findViewById(R.id.storeview);
        mViewPager=(ViewPager)findViewById(R.id.vp);
        recyclerView=(RecyclerView) findViewById(R.id.recommend);
        searchView=(TextView)findViewById(R.id.searchview);

        minprice=(Button)findViewById(R.id.minprice);
        hot=(Button)findViewById(R.id.hot);
        evaluation=(Button)findViewById(R.id.evaluation);

        //classview
        String url = "http://192.168.43.94:8080/SpringMVC/class/getAllClasses";
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
                classlist = gson.fromJson(json, new TypeToken<List<Class>>(){}.getType());

                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                for (Class clas : classlist) {
                    Map<String, Object> map = new HashMap<String, Object>();

                    int img = MainActivity.this.getResources().getIdentifier(
                            clas.getPicture().split("\\.")[0],
                            "drawable",
                            MainActivity.this.getPackageName());

                    map.put("img", img);
                    map.put("name", clas.getName());
                    data.add(map);
                }

                final SimpleAdapter Adapter = new SimpleAdapter(
                        MainActivity.this,
                        data,
                        R.layout.class_view,
                        new String[]{"img", "name"},
                        new int[]{R.id.classImg, R.id.className});

                classview.post(new Runnable() {
                    @Override
                    public void run() {
                        classview.setAdapter(Adapter);
                    }
                });
            }
        });


        //storeview
        String surl = "http://192.168.43.94:8080/SpringMVC/store/getAllStores";
        LoadView.LoadStores(surl,MainActivity.this,storeview);



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


        /*
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

*/


        //搜索点击
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        //class跳转
        classview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class type=classlist.get(position);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, StoreActivity.class);
                intent.putExtra("classname", type.getName());
                intent.putExtra("classid", type.getId());
                startActivity(intent);
            }
        });

        //排序
        minprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url;
                switch (m%3){
                    case 0:
                        url="http://192.168.43.94:8080/SpringMVC/store/getStoresbyMinPrice?rules=desc";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                    case 1:
                        url="http://192.168.43.94:8080/SpringMVC/store/getStoresbyMinPrice?rules=asc";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                    case 2:
                        url="http://192.168.43.94:8080/SpringMVC/store/getAllStores";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                }
                m++;
            }
        });


        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url;
                switch (h%3){
                    case 0:
                        url="http://192.168.43.94:8080/SpringMVC/store/getStoresbyClick?rules=desc";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                    case 1:
                        url="http://192.168.43.94:8080/SpringMVC/store/getStoresbyClick?rules=asc";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                    case 2:
                        url="http://192.168.43.94:8080/SpringMVC/store/getAllStores";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                }
                h++;
            }
        });

        evaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url;
                switch (e%3){
                    case 0:
                        url="http://192.168.43.94:8080/SpringMVC/store/getStoresbyComment?rules=desc";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                    case 1:
                        url="http://192.168.43.94:8080/SpringMVC/store/getStoresbyComment?rules=asc";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                    case 2:
                        url="http://192.168.43.94:8080/SpringMVC/store/getAllStores";
                        LoadView.LoadStores(url,MainActivity.this,storeview);
                        break;
                }
                e++;
            }
        });
    }

    protected void onStart() {
        super.onStart();

        scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(viewPagerAdapter.new ViewPageTask(),3,3,TimeUnit.SECONDS);
    }


}

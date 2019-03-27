package com.example.madking.View;

import android.content.Context;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.madking.chilema.MainActivity;
import com.example.madking.chilema.R;
import com.example.madking.model.Store;
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

public class LoadView {
    public static void LoadStores(String url, final Context context, final ListView storeview){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
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
                Gson gson = new Gson();
                List<Store> Stores=gson.fromJson(json,new TypeToken<List<Store>>(){}.getType());

                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                for (Store store : Stores) {
                    Map<String, Object> map = new HashMap<String, Object>();

                    int img = context.getResources().getIdentifier(
                            store.getPicture().split("\\.")[0],
                            "drawable",
                            context.getPackageName());

                    map.put("img", img);
                    map.put("name", store.getName());
                    map.put("evaluation", "评价  "+ store.getComment());
                    map.put("hot", store.getClick());
                    map.put("minprice", "¥"+ store.getMinPrice()+"起送 | ");
                    map.put("cost", "配送费¥"+ store.getCost());
                    map.put("events",store.getEvents());
                    data.add(map);
                }

                final SimpleAdapter Adapter = new SimpleAdapter(
                        context,
                        data,
                        R.layout.store_view,
                        new String[]{"img","name","evaluation","hot","minprice","cost","events"},
                        new int[]{R.id.store_img,R.id.store_name,R.id.store_evaluation,
                                R.id.store_hot,R.id.store_minprice,R.id.store_cost,
                                R.id.store_events});

                storeview.post(new Runnable() {
                    @Override
                    public void run() {
                        storeview.setAdapter(Adapter);
                    }
                });
            }
        });
    }
}

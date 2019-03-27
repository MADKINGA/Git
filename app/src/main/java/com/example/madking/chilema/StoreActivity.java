package com.example.madking.chilema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.madking.View.LoadView;
import com.example.madking.model.Store;

import java.util.List;

public class StoreActivity extends AppCompatActivity {

    private TextView clas;
    private List<Store> storelist;
    private ListView stores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        clas=(TextView) findViewById(R.id.clas);
        stores=(ListView)findViewById(R.id.stores);

        Intent intent=getIntent();
        String typename=intent.getStringExtra("classname");
        clas.setText(typename);
        int id=intent.getIntExtra("classid",0);

        String url="http://192.168.43.94:8080/SpringMVC/store/getAllStores";
        LoadView.LoadStores(url,StoreActivity.this,stores);
    }
}

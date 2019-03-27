package com.example.madking.chilema;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.madking.View.LoadView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ImageButton back;
    private EditText edit_search;
    private Button button_search;
    private ListView listview_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back=(ImageButton)findViewById(R.id.back);
        edit_search=(EditText)findViewById(R.id.edit_search);
        button_search=(Button)findViewById(R.id.button_search);
        listview_search=(ListView)findViewById(R.id.listview_search);

        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });

        //搜索
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://192.168.43.94:8080/SpringMVC/store/getStoresbyName?name="+edit_search.getText().toString().trim();
                LoadView.LoadStores(url,SearchActivity.this,listview_search);
            }
        });
    }

}

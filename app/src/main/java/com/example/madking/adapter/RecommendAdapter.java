package com.example.madking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madking.chilema.R;
import com.example.madking.model.Store;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private List<Store> storesList;
    private Context mcontext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.recommend_img);
            name=(TextView) itemView.findViewById(R.id.recommend_name);
        }
    }
    public RecommendAdapter(List<Store> merchants,Context context){
        storesList=merchants;
        mcontext=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommend_view,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store merchant=storesList.get(position);

        holder.img.setBackgroundResource(
                mcontext.getResources().getIdentifier(merchant.getPicture().split("\\.")[0],
                        "drawable",
                        mcontext.getPackageName()));
        holder.name.setText(merchant.getName());
    }

    @Override
    public int getItemCount() {
        return storesList.size();
    }


}

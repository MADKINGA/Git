package com.example.madking.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.madking.chilema.MainActivity;
import com.example.madking.chilema.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter{

    private List<ImageView> imagelist;
    private int currentItem;
    private ViewPager viewPager;

    public ViewPagerAdapter(List<ImageView> images,ViewPager viewPager){
        imagelist=images;
        this.viewPager=viewPager;
    }
    @Override
    public int getCount() {
        return imagelist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(imagelist.get(position));
        return imagelist.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(imagelist.get(position));
    }

    public class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem=(currentItem+1)%imagelist.size();
            mHandler.sendEmptyMessage(0);
        }
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currentItem);
        }
    };
}
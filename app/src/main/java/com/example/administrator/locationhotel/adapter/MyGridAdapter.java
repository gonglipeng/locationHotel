package com.example.administrator.locationhotel.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.locationhotel.R;
import com.example.administrator.locationhotel.entity.LookEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class MyGridAdapter extends BaseAdapter {

    public Activity mActivity;
    public ArrayList<LookEntity> mLookEntitys;
    public int size;

    public MyGridAdapter(Activity activity, ArrayList<LookEntity> lookEntities) {
        mActivity = activity;
        mLookEntitys = lookEntities;
        Log.i("tag", "lookEntities:" + lookEntities.size());
    }

    @Override
    public int getCount() {
        if (mLookEntitys.size() <= 4) {
            return mLookEntitys.size();
        } else {
            if (size == 0) {
                return 4;
            } else {
                return size;
            }
        }
    }

    @Override
    public LookEntity getItem(int position) {
        return mLookEntitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mActivity, R.layout.grid_view_item, null);
            viewHolder.fl1 = (FrameLayout) convertView.findViewById(R.id.fl_1);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.textView4 = (TextView) convertView.findViewById(R.id.textView4);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(getItem(position).getImageView());
        viewHolder.textView3.setText(getItem(position).getKm());
        viewHolder.textView4.setText(getItem(position).getLookName());
        return convertView;
    }

    class ViewHolder {
        private FrameLayout fl1;
        private ImageView imageView;
        private TextView textView3;
        private TextView textView4;


    }
}

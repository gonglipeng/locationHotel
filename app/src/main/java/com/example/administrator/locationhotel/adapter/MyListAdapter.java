package com.example.administrator.locationhotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.locationhotel.MainActivity;
import com.example.administrator.locationhotel.R;
import com.example.administrator.locationhotel.entity.CommentEntity;
import com.example.administrator.locationhotel.utils.Const;
import com.example.administrator.locationhotel.utils.MyObjectAnimator;
import com.example.administrator.locationhotel.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/18 0018.
 */
public class MyListAdapter extends BaseAdapter {

    public Activity mActivity;
    public ArrayList<CommentEntity> mList;
    public int size;

    public MyListAdapter(Activity activity, ArrayList<CommentEntity> list) {
        mActivity = activity;
        mList = null;
        mList = list;
        Log.i("list", "list:" + mList.size());
    }

    @Override
    public int getCount() {
        if (mList.size() < 1) {
            return mList.size();
        } else {
            if (size == 0) {
                return 1;
            } else {
                return size;
            }
        }

    }

    @Override
    public CommentEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mActivity, R.layout.list_view_item, null);
            viewHolder.ivItemIcon = (CircleImageView) convertView.findViewById(R.id.iv_item_Icon);
            viewHolder.tvItemUser = (TextView) convertView.findViewById(R.id.tv_item_user);
            viewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.tvItemTime = (TextView) convertView.findViewById(R.id.tv_item_time);
            viewHolder.ivItemStart1 = (ImageView) convertView.findViewById(R.id.iv_item_start_1);
            viewHolder.ivItemStart2 = (ImageView) convertView.findViewById(R.id.iv_item_start_2);
            viewHolder.ivItemStart3 = (ImageView) convertView.findViewById(R.id.iv_item_start_3);
            viewHolder.ivItemStart4 = (ImageView) convertView.findViewById(R.id.iv_item_start_4);
            viewHolder.ivItemStart5 = (ImageView) convertView.findViewById(R.id.iv_item_start_5);
            viewHolder.tvItemComments = (TextView) convertView.findViewById(R.id.tv_item_comments);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
            viewHolder.tvAll = (TextView) convertView.findViewById(R.id.tv_all);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.ivAll = (ImageView) convertView.findViewById(R.id.iv_all);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommentEntity entity = getItem(position);
        viewHolder.ivItemIcon.setImageResource(entity.getIvItemIcon());
        viewHolder.tvItemUser.setText(entity.getTvItemUser());
        viewHolder.textView2.setText(entity.getLv());
        viewHolder.tvItemTime.setText(entity.getTvItemTime());
        viewHolder.tvItemComments.setText(entity.getTvItemComments());
        switch (entity.getStartNums()){
            case 5:
                viewHolder.ivItemStart5.setVisibility(View.VISIBLE);
            case 4:
                viewHolder.ivItemStart4.setVisibility(View.VISIBLE);
            case 3:
                viewHolder.ivItemStart3.setVisibility(View.VISIBLE);
            case 2:
                viewHolder.ivItemStart2.setVisibility(View.VISIBLE);
            case 1:
                viewHolder.ivItemStart1.setVisibility(View.VISIBLE);
                break;
        }
        final ViewHolder finalViewHolder = viewHolder;
        final View finalConvertView = convertView;
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLookMore(finalViewHolder, position);
            }
        });
        viewHolder.tvItemComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLookMore(finalViewHolder, position);
            }
        });
        if (position > 0 && MainActivity.instance.isMoreBool) {
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0);
            animation.setDuration(200 + position * 200);

            AnimationSet set = new AnimationSet(true);
            set.addAnimation(animation);
            convertView.startAnimation(set);
            if(position==mList.size()-1) {
                MainActivity.instance.isMoreBool = false;
            }
        }
        return convertView;
    }

    private void onClickLookMore(final ViewHolder finalViewHolder, final int position) {
        final int startHeight = finalViewHolder.tvItemComments.getHeight();
        if (finalViewHolder.tvItemComments.getLineCount() == 2) {//从2行到多行
            MyObjectAnimator.rotationX1(finalViewHolder.ivAll);
            finalViewHolder.tvItemComments.setSingleLine(false);
            finalViewHolder.tvItemComments.post(new Runnable() {
                @Override
                public void run() {
                    int lvH = MainActivity.instance.lvComment.getHeight();
                    int endHeight = finalViewHolder.tvItemComments.getHeight();
                    int dx = endHeight - startHeight;
                    int lvEnd = dx + lvH;
                    Intent intent = new Intent(Const.ACTION_LISTVIEW_CHANG);
                    intent.putExtra("key", lvEnd);
                    if (getCount() != 1) {//全部显示
                        intent.putExtra("keyDown", lvEnd);
                        intent.putExtra("isAll", true);
                        if (position == 0) {//第一个item
                            intent.putExtra("keyUp",
                                    dx + MainActivity.instance.heightUp);
                        }
                    } else {//只用一个item
                        intent.putExtra("keyUp", lvEnd);
                        intent.putExtra("isAll", false);
                        intent.putExtra("keyDown", dx + MainActivity.instance.heightDown);
                        Log.i("height", "MainActivity.instance.heightDown:" + MainActivity.instance.heightDown);
                    }
                    MainActivity.instance.sendBroadcast(intent);
                    if (finalViewHolder.tvItemComments.getLineCount() == 2) {
                        MyObjectAnimator.rotationX2(finalViewHolder.ivAll);
                    }
                }
            });
        } else if (finalViewHolder.tvItemComments.getLineCount() > 2) {
            MyObjectAnimator.rotationX2(finalViewHolder.ivAll);
            finalViewHolder.tvItemComments.setMaxLines(2);
            finalViewHolder.tvItemComments.post(new Runnable() {
                @Override
                public void run() {
                    int lvH = MainActivity.instance.lvComment.getHeight();
                    int endHeight = finalViewHolder.tvItemComments.getHeight();
                    int dx = endHeight - startHeight;
                    int lvEnd = dx + lvH;
                    Intent intent = new Intent(Const.ACTION_LISTVIEW_CHANG);
                    if (getCount() != 1) {//全部显示
                        intent.putExtra("keyDown", lvEnd);
                        intent.putExtra("isAll", true);
                        if (position == 0) {//第一个item
                            intent.putExtra("keyUp",
                                    dx + MainActivity.instance.heightUp);
                        }
                    } else {//只用一个item
                        intent.putExtra("keyUp", lvEnd);
                        intent.putExtra("keyDown", dx + MainActivity.instance.heightDown);
                        intent.putExtra("isAll", false);
                    }
                    MainActivity.instance.sendBroadcast(intent);
                    if (finalViewHolder.tvItemComments.getLineCount() == 2) {
                        MyObjectAnimator.rotationX2(finalViewHolder.ivAll);
                    }
                }
            });
        }
    }

    class ViewHolder {
        private CircleImageView ivItemIcon;
        private TextView tvItemUser;
        private TextView textView2;
        private TextView tvItemTime;
        private ImageView ivItemStart1;
        private ImageView ivItemStart2;
        private ImageView ivItemStart3;
        private ImageView ivItemStart4;
        private ImageView ivItemStart5;
        private TextView tvItemComments;
        private LinearLayout linearLayout;
        private TextView tvAll;
        private TextView textView;
        private ImageView ivAll;
    }

}

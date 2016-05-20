package com.example.administrator.locationhotel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.locationhotel.adapter.MyGridAdapter;
import com.example.administrator.locationhotel.adapter.MyListAdapter;
import com.example.administrator.locationhotel.entity.CommentEntity;
import com.example.administrator.locationhotel.entity.LookEntity;
import com.example.administrator.locationhotel.utils.MyObjectAnimator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll1;
    private LinearLayout llTime;
    private LinearLayout llLocation;
    private LinearLayout llJiaotong;
    private ImageView ivBackground;
    private TextView tvLandscape;    //第一张图片
    private ImageView ivLocation;
    private TextView tvDistance;
    private TextView tvAbstract;
    private LinearLayout ll2;
    private TextView tvScenic;
    private ImageView ivScenicDown;
    private LinearLayout ll3;
    private ImageView ivMap;
    private TextView tvTime;
    private TextView tvTimeDown;
    private ImageView ivTimeDown;
    private TextView tvLocation;
    private TextView tvLocationDown;
    private ImageView ivLocationDown;
    private TextView tvJiaotong;
    private TextView tvJiaotongDown;
    private ImageView ivJiaotongDown;
    private TextView tvApply;
    private TextView tvTicket;
    private TextView tvMoney;
    private Button ivApplyDown;
    private LinearLayout ll4;
    private TextView tvDianping;
    public ListView lvComment;
    private TextView tvLook;
    private LinearLayout ll5;
    private TextView tvMore;
    private GridView gvScenic;
    private ImageView ivQuery;
    private TextView tvWrite;
    private boolean applyBool = true;
    private boolean moreBool = true;
    private MyListAdapter myListAdapter;
    private MyGridAdapter myGridAdapter;
    private int lineScenic;

    private ArrayList<CommentEntity> commentEntitys;
    private ArrayList<LookEntity> lookEntities;
    public LinearLayout ll_lv;

    public static MainActivity instance;
    public myBroadcastReceiver mReceiver;
    public int startLineLvHeight;
    LinearLayout.LayoutParams mLayoutParamsUp;
    LinearLayout.LayoutParams mLayoutParamsDown;
    public int heightUp;    //需要adapter调用计算第一个item
    public int heightDown;
    private boolean isFirst=true;

    private void assignViews() {
        ll1 = (LinearLayout) findViewById(R.id.ll_1);
        llTime = (LinearLayout) findViewById(R.id.ll_time);
        llJiaotong = (LinearLayout) findViewById(R.id.ll_jiaotong);
        llLocation = (LinearLayout) findViewById(R.id.ll_location);
        ivBackground = (ImageView) findViewById(R.id.iv_background);
        tvLandscape = (TextView) findViewById(R.id.tv_landscape);
        ivLocation = (ImageView) findViewById(R.id.iv_location);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvAbstract = (TextView) findViewById(R.id.tv_abstract);
        ll2 = (LinearLayout) findViewById(R.id.ll_2);
        tvScenic = (TextView) findViewById(R.id.tv_scenic);
        ivScenicDown = (ImageView) findViewById(R.id.iv_scenic_down);    //地图上面的第一个下拉按钮
        ll3 = (LinearLayout) findViewById(R.id.ll_3);
        ivMap = (ImageView) findViewById(R.id.iv_map);
        tvTimeDown = (TextView) findViewById(R.id.tv_time_content);
        tvTime = (TextView) findViewById(R.id.tv_time);
        ivTimeDown = (ImageView) findViewById(R.id.iv_time_down);    //时间下拉
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvLocationDown = (TextView) findViewById(R.id.tv_location_content);
        ivLocationDown = (ImageView) findViewById(R.id.iv_location_down);    //地址下拉
        tvJiaotong = (TextView) findViewById(R.id.tv_jiaotong);
        tvJiaotongDown = (TextView) findViewById(R.id.tv_jiaotong_content);
        ivJiaotongDown = (ImageView) findViewById(R.id.iv_jiaotong_down);    //交通下拉
        tvApply = (TextView) findViewById(R.id.tv_apply);
        tvTicket = (TextView) findViewById(R.id.tv_ticket);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        ivApplyDown = (Button) findViewById(R.id.iv_apply_down);
        ll4 = (LinearLayout) findViewById(R.id.ll_4);
        tvDianping = (TextView) findViewById(R.id.tv_dianping);
        lvComment = (ListView) findViewById(R.id.lv_comment);
        tvLook = (TextView) findViewById(R.id.tv_look);
        ll5 = (LinearLayout) findViewById(R.id.ll_5);
        tvMore = (TextView) findViewById(R.id.tv_more);
        gvScenic = (GridView) findViewById(R.id.gv_scenic);
        ivQuery = (ImageView) findViewById(R.id.iv_query);
        tvWrite = (TextView) findViewById(R.id.tv_write);
        ll_lv = (LinearLayout) findViewById(R.id.ll_listview);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        assignViews();
        addData();
        addListener();
        myListAdapter = new MyListAdapter(this, commentEntitys);
        lvComment.setAdapter(myListAdapter);
        myGridAdapter = new MyGridAdapter(this, lookEntities);
        gvScenic.setAdapter(myGridAdapter);
        /*new Thread() {
            @Override
            public void run() {
                if (lookEntities.size() > 4) {
                    lookEntities1 = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        lookEntities1.add(lookEntities.get(i));
                    }
                    myGridAdapter = new MyGridAdapter(MainActivity.this, lookEntities1);
                    myGridAdapter.notifyDataSetChanged();
                    gvScenic.setAdapter(myGridAdapter);
                    moreBool = true;
                }
            }
        }.start();*/
        //根据评论人数重新赋值
        tvDianping.post(new Runnable() {
            @Override
            public void run() {
                SpannableStringBuilder builder = new SpannableStringBuilder("来自" + commentEntitys.size() + "位用户点评");
                //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
                ForegroundColorSpan redSpan = new ForegroundColorSpan(0xFFF81200);
                builder.setSpan(redSpan, 2, 2 + String.valueOf(commentEntitys.size()).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvDianping.setText(builder);
            }
        });
        tvLookCom();
        tvScenic.post(new Runnable() {
            @Override
            public void run() {
                if (tvScenic.getLineCount() > 4) {

                    lineScenic = tvScenic.getLineCount();
                    tvScenic.setMaxLines(4);
                    tvScenic.setEllipsize(TextUtils.TruncateAt.END);
                }
            }
        });
        lvComment.post(new Runnable() {
            @Override
            public void run() {
                startLineLvHeight = lvComment.getHeight();
                if(isFirst){
                    heightUp=startLineLvHeight;
                    heightDown=startLineLvHeight*9;
                    Log.i("heightDown", ""+heightDown);
                    isFirst=false;
                }
            }
        });
    }

    private void tvLookCom() {
        tvLook.post(new Runnable() {
            @Override
            public void run() {
                String lookText = "查看全部" + commentEntitys.size() + "位评论";
                tvLook.setText(lookText);
            }
        });
        mReceiver = new myBroadcastReceiver();
        IntentFilter filter = new IntentFilter("myListAdapter");
        this.registerReceiver(mReceiver, filter);
    }

    private void addData() {
        commentEntitys = new ArrayList<>();
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic2, "张三", "lv2", "2016-03-02", 2, "展示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展示柜的哈巴萨和姐夫吧唧的杀菌客户的展啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展示柜展啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦了啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还车不能下上班的飞机公司价格是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        commentEntitys.add(new CommentEntity(R.drawable.pic1, "张三", "lv3", "2016-03-02", 3, "展示柜的哈巴萨和姐夫吧唧的杀菌客户的数据库萨比开车不能下上班的飞机公司价格赋予的还是vchdbshjfg"));
        lookEntities = new ArrayList<>();
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));
        lookEntities.add(new LookEntity((R.drawable.pic2), "2km", "塞纳河畔"));

    }


    private void addListener() {
        ivScenicDown.setOnClickListener(this);
        ivApplyDown.setOnClickListener(this);
        llLocation.setOnClickListener(this);
        llTime.setOnClickListener(this);
        llJiaotong.setOnClickListener(this);
        tvLook.setOnClickListener(this);
        tvMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scenic_down://地图上面的小按钮
                if (tvScenic.getLineCount() == 4) {    //行数
                   /* ScaleAnimation animation=new ScaleAnimation(1,1,0,1, Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,4/lineScenic);
                    animation.setDuration(1000);
                    tvScenic.startAnimation(animation);*/
                    down(ivScenicDown, tvScenic, 4);
                } else if (tvScenic.getLineCount() > 4) {
                    /*RotateAnimation rotateAnimation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setDuration(1000);
                    rotateAnimation.setFillAfter(true);
                    ivScenicDown.startAnimation(rotateAnimation);*/
                    MyObjectAnimator.rotationX2(ivScenicDown);
                    tvScenic.setMaxLines(4);
                }
                break;
            case R.id.ll_jiaotong://交通
                if (tvJiaotongDown.getLineCount() == 1) {    //行数
                    down(ivJiaotongDown, tvJiaotongDown, 1);
                } else {
                    MyObjectAnimator.rotationX2(ivJiaotongDown);
                    tvJiaotongDown.setSingleLine(true);
                }
                break;
            case R.id.ll_location://地址
                if (tvLocationDown.getLineCount() == 1) {    //行数
                    down(ivLocationDown, tvLocationDown, 1);

                } else {
                    MyObjectAnimator.rotationX2(ivLocationDown);
                    tvLocationDown.setSingleLine(true);
                }
                break;
            case R.id.ll_time://时间
                if (tvTimeDown.getLineCount() == 1) {    //行数
                    down(ivTimeDown, tvTimeDown, 1);
                } else {
                    MyObjectAnimator.rotationX2(ivTimeDown);
                    tvTimeDown.setSingleLine(true);
                }
                break;
            case R.id.tv_look://查看评价
                if (applyBool) {
                    myListAdapter.size = commentEntitys.size();
                    myListAdapter.notifyDataSetChanged();
                    tvLook.setText("隐藏用户评论");
                    lvComment.post(new Runnable() {
                        @Override
                        public void run() {
                            if (heightDown != 0) {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvComment.getLayoutParams();
                                params.height = heightDown;
                                params.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                                lvComment.setLayoutParams(params);
                                Log.i("height", lvComment.getHeight() + "mLayoutParamsDown");
                            } else {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvComment.getLayoutParams();
                                params.height = startLineLvHeight * myListAdapter.size;
                                params.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                                lvComment.setLayoutParams(params);
                            }
                        }
                    });

                    applyBool = false;

                } else {
                    myListAdapter.size = 1;
                    myListAdapter.notifyDataSetChanged();
                    tvLookCom();
                    applyBool = true;

                    lvComment.post(new Runnable() {
                        @Override
                        public void run() {
                            if (heightUp != 0) {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvComment.getLayoutParams();
                                params.height = heightUp;
                                params.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                                lvComment.setLayoutParams(params);
                                lvComment.setLayoutParams(params);
                                Log.i("height", lvComment.getHeight() + "mLayoutParamsUp");
                            } else {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvComment.getLayoutParams();
                                params.height = startLineLvHeight / myListAdapter.size;
                                params.weight = LinearLayout.LayoutParams.MATCH_PARENT;
                                lvComment.setLayoutParams(params);
                            }
                        }
                    });
                }
                break;
            case R.id.tv_more://查看更多景点
                if (moreBool) {
                    myGridAdapter.size = lookEntities.size();
                    myGridAdapter.notifyDataSetChanged();
                    tvMore.setText("隐藏景点");
                    moreBool = false;
                } else {
                    myGridAdapter.size = 4;
                    myGridAdapter.notifyDataSetChanged();
                    tvMore.setText("查看更多");
                    moreBool = true;
                }
                break;
        }
    }

    //下拉框
    private void down(final View ivJiaotongDown, final TextView tvJiaotongDown, final int lines) {
        MyObjectAnimator.rotationX1(ivJiaotongDown);
        tvJiaotongDown.setSingleLine(false);
        tvJiaotongDown.post(new Runnable() {
            @Override
            public void run() {
                if (tvJiaotongDown.getLineCount() == lines) {
                    MyObjectAnimator.rotationX2(ivJiaotongDown);
                    /*Toast.makeText(MainActivity.this, "没有更多了~", Toast.LENGTH_SHORT).show();*/
                }
            }
        });
    }

    class myBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getIntExtra("keyDown", 0)!=0) {
                heightDown = intent.getIntExtra("keyDown", 0);
                if(intent.getBooleanExtra("isAll",false)) {
                    mLayoutParamsDown = (LinearLayout.LayoutParams) MainActivity.instance.lvComment.getLayoutParams();
                    mLayoutParamsDown.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    mLayoutParamsDown.height = heightDown;
                    Log.i("height", heightDown + "heightDown");
                    lvComment.setLayoutParams(mLayoutParamsDown);
                }
            }
            if(intent.getIntExtra("keyUp", 0)!=0){
                heightUp = intent.getIntExtra("keyUp", 0);
                if(!intent.getBooleanExtra("isAll",true)) {
                    mLayoutParamsUp = (LinearLayout.LayoutParams) MainActivity.instance.lvComment.getLayoutParams();
                    mLayoutParamsUp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                    mLayoutParamsUp.height = heightUp;
                    lvComment.setLayoutParams(mLayoutParamsUp);
                    Log.i("height", heightUp + "heightUp");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

}

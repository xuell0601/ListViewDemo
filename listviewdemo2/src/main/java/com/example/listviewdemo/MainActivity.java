package com.example.listviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    private String TAG="";
    private Button mBtn;
    private int totl;
    private ListView lv;
    private ArrayList<Student> students;
    private int MaxNumber = 100;//表示ListView最多要显示数目
    private int lastIndex;
   public View footerView;
    public Myadpter myadpter;
    public ProgressBar mBar;
    //创建Handler 更新数据
    public Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mBar.setVisibility(View.GONE);
                mBtn.setVisibility(View.GONE);
                     //添加
                     loadData();
                     //刷新
                     myadpter.notifyDataSetChanged();

            }
        }
    };



    private void loadData() {
        //获取显示的数目
        int count = myadpter.getCount();
        /**
         * 每一页显示10条
         * count 当前适配器中显示的条数
         *
         */

        if ((count + 11) < MaxNumber) {
            for (int i = count; i < (count + 11); i++) {
                Student student = new Student();
                student.setTitle("这是第" + i + "条");
                students.add(student);
            }
        } else {
            for (int i = count; i < MaxNumber; i++) {
                Student student = new Student();
                student.setTitle("这是第" + i + "条");
                students.add(student);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ButterKnife.inject(this);
        //创建
        lv=(ListView) findViewById(R.id.lv);
        students = new ArrayList<>();
        //初始化数据
        initList();
        //初始化底部View
        initFooterView();
        myadpter = new Myadpter(students, MainActivity.this);
        lv.setAdapter(myadpter);
        //通过监听View的滚动来动态添加数据
        lv.setOnScrollListener(this);
    }
    
    private void initFooterView() {

        footerView = View.inflate(MainActivity.this, R.layout.footerview, null);

        mBtn=(Button)  footerView.findViewById(R.id.mBtn);
        mBar=(ProgressBar)  footerView.findViewById(R.id.mBar);
        lv.addFooterView(footerView);

    }

    private void initList() {
        for (int i = 0; i < 11; i++) {
            Student student = new Student();
            student.setId(i);
            student.setTitle("这是第" + i + "条数据");
            students.add(student);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当状态更改为点击滑动时把Button按钮隐藏掉
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            mBtn.setVisibility(View.GONE);
        }


        //如果是滚动的就添加
        if (scrollState == SCROLL_STATE_IDLE && lastIndex == myadpter.getCount()) {
            //加载
           // footerView.setVisibility(View.GONE);
            mBar.setVisibility(View.VISIBLE);
            mBtn.setVisibility(View.GONE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();

        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //最后一条数据
        lastIndex = firstVisibleItem + visibleItemCount - 1;
        if(totalItemCount==MaxNumber+1){
            lv.removeFooterView(footerView);
            Toast.makeText(this, "数据加载完成，无法更新", Toast.LENGTH_SHORT).show();
        }

    }
}

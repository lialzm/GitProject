package com.lialzm.baidu.toolbartest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private float headerHeight;//顶部高度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main);
        //使toolbar生效
        setSupportActionBar(toolbar);
        //设置返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setBackgroundColor(Color.argb(0, 0, 0, 0));
        toolbar.getBackground().setAlpha(0);
        View head = getLayoutInflater().inflate(R.layout.head, null);
        lv = (ListView) findViewById(R.id.lv);
        lv.addHeaderView(head);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("item" + i);
        }
        headerHeight = dp2px(200);

        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list));
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                float scrollY = getScrollY(view);
                float offset = 1 - Math.max((headerHeight - toolbar.getHeight() - scrollY) / (headerHeight - toolbar.getHeight()), 0f);
                toolbar.getBackground().setAlpha((int) (offset * 255));

            }

        });
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }


    /**
     * 得到ListView在Y轴上的偏移
     */
    public float getScrollY(AbsListView view) {
        View c = view.getChildAt(0);

        if (c == null)
            return 0;

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();
        float headerHeight = 0;
        if (firstVisiblePosition > 0) {
            headerHeight = this.headerHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

}



package com.super_zuo.study;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BaseRefreshRecyclerView rcv_test = (BaseRefreshRecyclerView) findViewById(R.id.rcv_test);
        final TestRecyclerViewAdapter madapter = new TestRecyclerViewAdapter();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rcv_test.setLayoutManager(staggeredGridLayoutManager);
        rcv_test.addItemDecoration(new SimpleItemDecoration(20,3));
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        ArrayList list = new ArrayList();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }
        madapter.setData(list);
        rcv_test.setAdapter(madapter);
        rcv_test.setOnRefreshAndLoadMoreListener(new BaseRefreshRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                rcv_test.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rcv_test.completeRefresh();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                rcv_test.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List data = madapter.getData();
                        for (int i = 0; i < 10; i++) {
                            data.add(i * 1000);
                        }
                        madapter.setData(data);
                        madapter.notifyDataSetChanged();
                        rcv_test.completeLoadMore();
                    }
                }, 3000);
            }
        });

    }
}

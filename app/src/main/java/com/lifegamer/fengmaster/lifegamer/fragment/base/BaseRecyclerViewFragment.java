package com.lifegamer.fengmaster.lifegamer.fragment.base;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.wight.MyItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 基础RecyclerView碎片
 *
 * 自带一个RecyclerView
 *
 * 默认没有head显示,需要head请extend
 */
public class BaseRecyclerViewFragment extends BaseFragment {

    public static final String DEF_NAME="";

    @BindView(R.id.rv_recycler_fragment_list)
    RecyclerView recyclerView;
    @BindView(R.id.fl_recycler_fragment_head)
    FrameLayout head;

    private BaseRecyclerViewAdapter adapter;

    public BaseRecyclerViewFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler_view, container, false);
        ButterKnife.bind(this,view);
        View headView = getHead(inflater, container, savedInstanceState);
        if (headView!=null){
            head.removeAllViews();
            head.addView(headView);
        }
        if (adapter!=null){
            recyclerView.setAdapter(adapter);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new MyItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.invalidateItemDecorations();
        return view;
    }

    public BaseRecyclerViewFragment setAdapter(BaseRecyclerViewAdapter adapter){
        this.adapter=adapter;
        if (recyclerView!=null){
            recyclerView.setAdapter(adapter);
        }
        return this;
    }




    /**
     * 头部view 钩子
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View getHead(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState){
        return null;
    }


    @Override
    public String getName() {
        if (adapter!=null){
            return adapter.getName();
        }
        return DEF_NAME;
    }
}

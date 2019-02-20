package com.lifegamer.fengmaster.lifegamer.fragment.base;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.BaseViewPagerFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FengMaster on 19/02/12.
 */
public class BaseTabFragment  extends Fragment {

    @BindView(R.id.tl_fragment_base_list)
    TabLayout tabLayout;
    @BindView(R.id.vp_fragment_base_list)
    ViewPager viewPager;
    @BindView(R.id.coordinatorLayout_fragment)
    CoordinatorLayout coordinatorLayout;

    protected List<BaseFragment> fragments = new ArrayList<>();

    protected BaseViewPagerFragmentAdapter fragmentAdapter;

    public BaseTabFragment() {
        // Required empty public constructor
    }



    @Override
    public void onPause() {
        super.onPause();
        ViewUtil.removeCoopView(coordinatorLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewUtil.addCoopView(coordinatorLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        if (container.getTag()!=null){
//            //防止多次调用出现白屏bug
//            container.removeView((View) container.getTag());
//            View view = (View) container.getTag();
//            container.setTag(null);
//            return view;
//        }

        View inflate = inflater.inflate(R.layout.fragment_base_tab_list, container, false);
        ButterKnife.bind(this, inflate);

        fragmentAdapter = new BaseViewPagerFragmentAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        container.setTag(inflate);
        return inflate;
    }



}

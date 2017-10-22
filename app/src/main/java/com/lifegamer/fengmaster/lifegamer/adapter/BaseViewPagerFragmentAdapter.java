package com.lifegamer.fengmaster.lifegamer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;

import java.util.List;

/**
 * Created by qianzise on 2017/10/10.
 */

public class BaseViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<? extends BaseFragment> fragmentList;


    public BaseViewPagerFragmentAdapter(FragmentManager fm,List<? extends BaseFragment> list) {
        super(fm);
        fragmentList=list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getName();
    }


}

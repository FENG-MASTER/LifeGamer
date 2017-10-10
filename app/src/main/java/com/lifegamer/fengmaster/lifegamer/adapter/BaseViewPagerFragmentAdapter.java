package com.lifegamer.fengmaster.lifegamer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.lifegamer.fengmaster.lifegamer.fragment.AchievementFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.HeroInfoFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.TopInfoFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;

import java.util.List;

/**
 * Created by qianzise on 2017/10/10.
 */

public class BaseViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragmentList;


    public BaseViewPagerFragmentAdapter(FragmentManager fm,List<BaseFragment> list) {
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

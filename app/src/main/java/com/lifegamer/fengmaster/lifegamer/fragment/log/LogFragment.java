package com.lifegamer.fengmaster.lifegamer.fragment.log;

import com.lifegamer.fengmaster.lifegamer.adapter.list.log.AllLogAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;

/**
 * 足迹页面
 * Created by FengMaster on 19/01/28.
 */
public class LogFragment extends BaseTabListFragment {


    @Override
    public void onActionButtonClick() {

    }

    @Override
    public Class[] getAdapterClasses() {
        return new Class[]{AllLogAdapter.class};
    }

    @Override
    public void onItemSelect(Object o) {

    }
}

package com.lifegamer.fengmaster.lifegamer.fragment.base;

import android.support.v4.app.Fragment;

import com.lifegamer.fengmaster.lifegamer.base.IName;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by qianzise on 2017/10/10.
 */

public abstract class BaseFragment extends Fragment implements IName{


    // Fragment页面onResume函数重载
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getName());

    }

    // Fragment页面onResume函数重载
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getName());
    }

}

package com.lifegamer.fengmaster.lifegamer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.wight.PreferenceFragment;

/**
 * 设置页面
 */
public class SettingFragment extends PreferenceFragment {


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);
    }

    @Override
    public String setPreferencesFileName() {
        return PreferenceUtil.PREFERENCE_NAME;
    }
}

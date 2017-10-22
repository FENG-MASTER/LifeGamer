package com.lifegamer.fengmaster.lifegamer.fragment.achievement;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;

/**
 *成就fragment
 *
 * 属于高层模块,负责整合所有成就相关适配器和相关子fragment
 */
public class AchievementFragment extends BaseTabListFragment {


    public AchievementFragment() {

    }



    @Override
    public void onActionButtonClick() {
        EditAchievementDialog dialog=new EditAchievementDialog();
        dialog.show(getChildFragmentManager(),"editAchievement");
    }

}

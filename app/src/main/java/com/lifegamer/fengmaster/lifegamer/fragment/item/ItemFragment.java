package com.lifegamer.fengmaster.lifegamer.fragment.item;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;

/**
 * 物品fragment
 *
 * 属于高层模块,负责整合所有物品相关适配器和相关子fragment
 */
public class ItemFragment extends BaseTabListFragment {


    @Override
    public void onActionButtonClick() {
        EditItemDialog dialog=new EditItemDialog();
        dialog.show(getChildFragmentManager(),"edit");
    }
}

package com.lifegamer.fengmaster.lifegamer.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.FragmentTopInfoBinding;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;
import com.lifegamer.fengmaster.lifegamer.model.Skill;


import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 顶部英雄信息面板
 */
public class TopInfoFragment extends Fragment {

    @BindView(R.id.erl_top_info)
    ExpandableRelativeLayout expandableRelativeLayout;


    public TopInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTopInfoBinding binding=FragmentTopInfoBinding.inflate(inflater);
        ButterKnife.bind(this,binding.getRoot());
        binding.setHero(Game.getInstance().getHeroManager().getHero());
        return  binding.getRoot();
    }

    public void toggle(){
        expandableRelativeLayout.toggle();
    }

}

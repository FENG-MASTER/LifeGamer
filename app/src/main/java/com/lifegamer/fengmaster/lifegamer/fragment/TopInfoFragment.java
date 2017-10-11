package com.lifegamer.fengmaster.lifegamer.fragment;


import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.FragmentTopInfoBinding;
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.wight.SqureImageView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 顶部英雄信息面板
 */
public class TopInfoFragment extends Fragment {




    public TopInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTopInfoBinding binding=FragmentTopInfoBinding.inflate(inflater);
        binding.setHero(Game.getInstance().getHeroManager().getHero());
        binding.setLp(new LifePoint());
        binding.tvTopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Skill skill=new Skill();
                skill.setName("厨艺"+ new Random().nextInt(20));
                skill.setXP(10);
                Game.getInstance().getSkillManager().addSkill(skill);

            }
        });
        return  binding.getRoot();
    }

}

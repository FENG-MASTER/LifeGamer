package com.lifegamer.fengmaster.lifegamer.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.databinding.FragmentHeroInfoBinding;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.AvatarSelectDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HeroInfoFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, OnItemSelectListener<IAvatarManager.Avatar> {


    private FragmentHeroInfoBinding binding;

    private Hero hero;

    private IAvatarManager.Avatar heroAvatar;


    public HeroInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHeroInfoBinding.inflate(inflater);
        initView();
        binding.setHero(hero);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewUtil.showSnack("修改完后把复选框的勾勾去掉才能保存哦!");
    }

    private void initView() {
        binding.cbHeroInfoName.setOnCheckedChangeListener(this);
        binding.cbHeronInfoTitle.setOnCheckedChangeListener(this);
        binding.cbHeroInfoDesc.setOnCheckedChangeListener(this);
        binding.imHeroInfoAvatar.setOnClickListener(this);


        hero=Game.getInstance().getHeroManager().getHero();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.cbHeroInfoName.setOnCheckedChangeListener(null);
        binding.cbHeronInfoTitle.setOnCheckedChangeListener(null);
        binding.cbHeroInfoDesc.setOnCheckedChangeListener(null);
        binding.imHeroInfoAvatar.setOnClickListener(null);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_hero_info_name:
                binding.etHeroInfoName.setEnabled(b);
                if (!b){
                    hero.setName(binding.etHeroInfoName.getText().toString());

                }
                break;
            case R.id.cb_heron_info_title:
                binding.etHeroInfoTitle.setEnabled(b);
                if (!b){
                    hero.setTitle(binding.etHeroInfoTitle.getText().toString());
                }
                break;
            case R.id.cb_hero_info_desc:
                binding.etHeroInfoDesc.setEnabled(b);
                if (!b){
                    hero.setIntroduction(binding.etHeroInfoDesc.getText().toString());
                }
                break;
        }
        if (!b){
            Game.getInstance().getHeroManager().updateHero(hero);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.im_hero_info_avatar){
            //更换头像
            AvatarSelectDialog dialog=new AvatarSelectDialog();
            dialog.addItemSelectListener(this);
            dialog.show(getChildFragmentManager(),"selectAvatar");
        }
    }

    @Override
    public void onItemSelect(IAvatarManager.Avatar avatar) {
        //选择头像成功
        heroAvatar=avatar;
        hero.setAvatarUrl(heroAvatar.toString());
        Game.getInstance().getHeroManager().updateHero(hero);
    }
}

package com.lifegamer.fengmaster.lifegamer.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
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


public class HeroInfoFragment extends Fragment implements  View.OnClickListener, OnItemSelectListener<IAvatarManager.Avatar> {


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

    private void initView() {
        binding.etHeroInfoName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s==null||s.toString()==null||s.toString().isEmpty()){
                    return;
                }
                hero.setName(binding.etHeroInfoName.getText().toString());

            }
        });

        binding.etHeroInfoDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s==null||s.toString()==null||s.toString().isEmpty()){
                    return;
                }
                hero.setIntroduction(binding.etHeroInfoDesc.getText().toString());

            }
        });


        binding.etHeroInfoTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s==null||s.toString()==null||s.toString().isEmpty()){
                    return;
                }
                hero.setIntroduction(binding.etHeroInfoDesc.getText().toString());
            }
        });
        binding.imHeroInfoAvatar.setOnClickListener(this);


        hero=Game.getInstance().getHeroManager().getHero();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.imHeroInfoAvatar.setOnClickListener(null);
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

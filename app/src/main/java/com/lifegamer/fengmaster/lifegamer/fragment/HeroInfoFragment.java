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
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HeroInfoFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.et_hero_info_desc)
    EditText editTextDesc;
    @BindView(R.id.et_hero_info_name)
    EditText editTextName;
    @BindView(R.id.et_hero_info_title)
    EditText editTextTitle;
    @BindView(R.id.cb_hero_info_desc)
    CheckBox checkBoxDesc;
    @BindView(R.id.cb_hero_info_name)
    CheckBox checkBoxName;
    @BindView(R.id.cb_heron_info_title)
    CheckBox checkBoxTitle;

    @BindView(R.id.im_hero_info_avatar)
    ImageView avatar;

    private Hero hero;


    public HeroInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hero_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewUtil.showSnack("修改完后把复选框的勾勾去掉才能保存哦!");
    }

    private void initView() {
        checkBoxName.setOnCheckedChangeListener(this);
        checkBoxTitle.setOnCheckedChangeListener(this);
        checkBoxDesc.setOnCheckedChangeListener(this);



        hero=Game.getInstance().getHeroManager().getHero();
        editTextName.setText(hero.getName());
        editTextDesc.setText(hero.getIntroduction());
        editTextTitle.setText(hero.getTitle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        checkBoxName.setOnCheckedChangeListener(null);
        checkBoxDesc.setOnCheckedChangeListener(null);
        checkBoxTitle.setOnCheckedChangeListener(null);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_hero_info_name:
                editTextName.setEnabled(b);
                if (!b){
                    hero.setName(editTextName.getText().toString());

                }
                break;
            case R.id.cb_heron_info_title:
                editTextTitle.setEnabled(b);
                if (!b){
                    hero.setTitle(editTextTitle.getText().toString());
                }
                break;
            case R.id.cb_hero_info_desc:
                editTextDesc.setEnabled(b);
                if (!b){
                    hero.setIntroduction(editTextDesc.getText().toString());
                }
                break;
        }
        if (!b){
            Game.getInstance().getHeroManager().updateHero(hero);
        }
    }


}

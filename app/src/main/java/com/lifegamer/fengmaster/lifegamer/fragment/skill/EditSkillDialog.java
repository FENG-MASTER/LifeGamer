package com.lifegamer.fengmaster.lifegamer.fragment.skill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditSkillBinding;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * Created by qianzise on 2017/10/13.
 */

public class EditSkillDialog extends DialogFragment {

    private Skill skill=new Skill();

    public EditSkillDialog(){
        setCancelable(false);
    }

    public void setSkill(Skill skill){
        this.skill=skill;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogEditSkillBinding binding=DialogEditSkillBinding.inflate(inflater);
        binding.setSkill(skill);
        return binding.getRoot();
    }
}

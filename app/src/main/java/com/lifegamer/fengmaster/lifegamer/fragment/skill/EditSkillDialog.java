package com.lifegamer.fengmaster.lifegamer.fragment.skill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditSkillBinding;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * Created by qianzise on 2017/10/13.
 *
 * 技能编辑对话框
 */

public class EditSkillDialog extends DialogFragment {

    private Skill skill = new Skill();

    private NegativeButtonClickListener negativeButtonClickListener;

    private PositiveButtonClickListener positiveButtonClickListener;

    public EditSkillDialog() {
        setCancelable(false);
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogEditSkillBinding binding = DialogEditSkillBinding.inflate(inflater);
        binding.setSkill(skill);


        binding.btDialogEditSkillOk.setOnClickListener(view -> {


            skill.setName(binding.etDialogEditSkillName.getText().toString());
            skill.setIntro(binding.etDialogEditSkillIntro.getText().toString());
            skill.setType(binding.etDialogEditSkillType.getText().toString());
            if (skill.getId()==0){
                //新建的skill
                Game.getInstance().getSkillManager().addSkill(skill);
            }else {
                Game.getInstance().getSkillManager().updateSkill(skill);
            }


            dismiss();
            if (positiveButtonClickListener != null) {
                positiveButtonClickListener.positiveButtonClick();
            }

        });
        binding.btDialogEditSkillNo.setOnClickListener(view -> {

            dismiss();
            if (negativeButtonClickListener != null) {
                negativeButtonClickListener.negativeButtonClick();
            }


        });

        return binding.getRoot();
    }

    public void setNegativeButtonClickListener(NegativeButtonClickListener listener) {
        this.negativeButtonClickListener = listener;
    }

    public void setPositiveButtonClickListener(PositiveButtonClickListener listener) {
        this.positiveButtonClickListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        negativeButtonClickListener = null;
        positiveButtonClickListener = null;
    }

    public interface NegativeButtonClickListener {
        void negativeButtonClick();
    }

    public interface PositiveButtonClickListener {
        void positiveButtonClick();
    }


}

package com.lifegamer.fengmaster.lifegamer.fragment.achievement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.AddAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.UpdateAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditAchievementBinding;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

import java.util.Date;

/**
 * Created by qianzise on 2017/10/22.
 */

public class EditAchievementDialog extends BaseDialogFragment implements View.OnClickListener {

    private DialogEditAchievementBinding binding;

    /**
     * 当前编辑的成就
     */
    private Achievement achievement=new Achievement();

    public void setBinding(DialogEditAchievementBinding binding) {
        this.binding = binding;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogEditAchievementBinding.inflate(inflater);
        binding.setAchievement(achievement);

        binding.btDialogEditAchievementOk.setOnClickListener(this);
        binding.btDialogEditAchievementNo.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_dialog_edit_achievement_ok:
                //确认
                save();
                dismiss();
                break;
            case R.id.bt_dialog_edit_achievement_no:
                //退出
                dismiss();
                break;
            default:
        }
    }

    private void save() {
        achievement.setName(binding.etDialogEditAchievementName.getText().toString());
        achievement.setDesc(binding.etDialogEditAchievementDesc.getText().toString());
        achievement.setType(binding.etDialogEditAchievementType.getText().toString());
        achievement.setGot(false);
        achievement.setCreateTime(new Date());
        achievement.setUpdateTime(new Date());
        achievement.setGainTime(new Date());

        if (achievement.getId()!=0){
            //更新
            Game.getInstance().getCommandManager().executeCommand(new UpdateAchievementCommand(achievement));
        }else {
            //新建
            Game.getInstance().getCommandManager().executeCommand(new AddAchievementCommand(achievement));
        }
    }
}

package com.lifegamer.fengmaster.lifegamer.fragment.reward;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.command.command.reward.AddRewardCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.reward.UpdateRewardCommand;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditRewardBinding;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseDialogFragment;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.wight.AvatarSelectDialog;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 奖励编辑对话框
 */

public class EditRewardDialog extends BaseDialogFragment implements View.OnClickListener, OnItemSelectListener<IAvatarManager.Avatar> {


    /**
     * 图标
     */
    private IAvatarManager.Avatar avatar;


    private RewardItem rewardItem;

    private DialogEditRewardBinding binding;

    public void setRewardItem(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogEditRewardBinding.inflate(inflater);
        binding.setReward(rewardItem);

        binding.btDialogEditRewardOk.setOnClickListener(this);
        binding.btDialogEditRewardNo.setOnClickListener(this);

        binding.switchDialogEditRewardNoForSale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.etDialogEditSkillCostLp.setText("-1");
                    binding.etDialogEditSkillCostLp.setEnabled(false);
                }else {
                    binding.etDialogEditSkillCostLp.setEnabled(true);
                }
            }
        });

        binding.switchDialogEditRewardNumInfinite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.etDialogEditSkillNum.setText("-1");
                    binding.etDialogEditSkillNum.setEnabled(false);
                }else {
                    binding.etDialogEditSkillNum.setEnabled(true);
                }
            }
        });

        binding.sivDialogEditRewardIcon.setOnClickListener(view -> {
            AvatarSelectDialog dialog=new AvatarSelectDialog();
            dialog.addItemSelectListener(EditRewardDialog.this);
            dialog.show(getFragmentManager(),"avatarSelect");
        });


        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_dialog_edit_reward_ok:
                save();
                dismiss();
                break;
            case R.id.bt_dialog_edit_reward_no:
                dismiss();
                break;
            default:
        }

    }

    private void save(){
        rewardItem.setName(binding.etDialogEditRewardName.getText().toString());
        rewardItem.setDesc(binding.etDialogEditSkillDesc.getText().toString());
        rewardItem.setAddToItem(binding.switchDialogEditRewardAddToItem.isChecked());
        rewardItem.setExpendable(binding.switchDialogEditRewardExpendable.isChecked());

        Editable cost = binding.etDialogEditSkillCostLp.getText();
        if (cost!=null&&!cost.toString().equals("")){
            rewardItem.setCostLP(Integer.valueOf(cost.toString()));
        }

        Editable num = binding.etDialogEditSkillNum.getText();
        if (num!=null&&!num.toString().equals("")){
            rewardItem.setQuantityAvailable(Integer.valueOf(num.toString()));
        }
        if (avatar!=null){
            rewardItem.setIcon(avatar.toString());
        }

        if (rewardItem.getId()!=0){
            //更新
            Game.getInstance().getCommandManager().executeCommand(new UpdateRewardCommand(rewardItem));
        }else {
            //新增
            Game.getInstance().getCommandManager().executeCommand(new AddRewardCommand(rewardItem));
        }
    }

    @Override
    public void onItemSelect(IAvatarManager.Avatar avatar) {
        //选择头像
        this.avatar=avatar;
        binding.sivDialogEditRewardIcon.setImageDrawable(avatar.getIcon());
    }
}

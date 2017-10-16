package com.lifegamer.fengmaster.lifegamer.fragment.skill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.AddSkillCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.UpdateSkillCommand;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditSkillBinding;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;
import com.lifegamer.fengmaster.lifegamer.wight.AvatarSelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.SqureImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/13.
 * <p>
 * 技能编辑对话框
 */

public class EditSkillDialog extends DialogFragment implements OnItemSelectListener<IAvatarManager.Avatar> {

    private Skill skill = new Skill();

    /**
     * 图标
     */
    private IAvatarManager.Avatar avatar;

    /**
     * 编辑内容 是否有错误
     */
    private boolean err = false;

    private NegativeButtonClickListener negativeButtonClickListener;

    private PositiveButtonClickListener positiveButtonClickListener;

    @BindView(R.id.siv_dialog_edit_skill_icon)
    SqureImageView icon;

    public EditSkillDialog() {
        //不可点击外部取消对话框
        setCancelable(false);
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogEditSkillBinding binding = DialogEditSkillBinding.inflate(inflater);

        ButterKnife.bind(this,binding.getRoot());

        binding.setSkill(skill);

        binding.etDialogEditSkillName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Stream.of(Game.getInstance().getSkillManager().getAllSkillName()).anyMatch(s -> !s.equals(skill.getName())&&s.equals(editable.toString()))) {
                    //重名!!
                    binding.tilDialogEditSkillName.setErrorEnabled(true);
                    binding.tilDialogEditSkillName.setError("技能重名!");
                    err = true;
                } else {
                    binding.tilDialogEditSkillName.setErrorEnabled(false);
                    err = false;
                }
            }
        });

        binding.btDialogEditSkillOk.setOnClickListener(view -> {
            if (err){
                return;
            }

            skill.setName(binding.etDialogEditSkillName.getText().toString());
            skill.setIntro(binding.etDialogEditSkillIntro.getText().toString());
            skill.setType(binding.etDialogEditSkillType.getText().toString());
            skill.setIcon(avatar!=null?avatar.toString():null);

            if (skill.getId() == 0) {
                //新建的skill
                Game.getInstance().getCommandManager().executeCommand(new AddSkillCommand(skill));
            } else {
                Game.getInstance().getCommandManager().executeCommand(new UpdateSkillCommand(skill));
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
        binding.sivDialogEditSkillIcon.setOnClickListener(view -> {
            AvatarSelectDialog dialog=new AvatarSelectDialog();
            dialog.addItemSelectListener(EditSkillDialog.this);
            dialog.show(getFragmentManager(),"avatarSelect");
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

    @Override
    public void onItemSelect(IAvatarManager.Avatar avatar) {
        //选择头像
        this.avatar=avatar;
        icon.setImageDrawable(avatar.getIcon());
    }

    public interface NegativeButtonClickListener {
        void negativeButtonClick();
    }

    public interface PositiveButtonClickListener {
        void positiveButtonClick();
    }


}

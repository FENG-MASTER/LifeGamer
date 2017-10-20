package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qianzise on 2017/10/16.
 *
 * 任务编辑对话框 奖励部分
 */

public class EditTaskRewardFragment extends EditTaskDialog.SaveableFragment {

    @BindView(R.id.ll_dialog_edit_task_time_finish_skill)
    LinearLayout skillView;
    @BindView(R.id.bt_dialog_edit_task_reward_add_skill)
    Button addSkill;

    @BindView(R.id.ll_dialog_edit_task_time_finish_item)
    LinearLayout itemView;
    @BindView(R.id.ll_dialog_edit_task_time_finish_achievement)
    LinearLayout achievementView;

    private Task task;

    /**
     * 任务完成获得的 技能列表
     */
    private Map<String,Integer> skills=new HashMap<>();

    public EditTaskRewardFragment setTask(Task task){
        this.task=task;
        return this;
    }

    public EditTaskRewardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_task_reward, container, false);
        ButterKnife.bind(this,view);

        initSkills();

        return view;
    }

    /**
     * 初始化 任务获得技能列表
     */
    private void initSkills(){
        skills=task.getSuccessSkills();
        for (Map.Entry<String, Integer> entry : skills.entrySet()) {
            newSkillView(entry.getKey(),entry.getValue());
        }
    }

    private void newSkillView(String name,int val){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_edit_task_reward_skill, skillView, false);

        TextView nameView= (TextView) view.findViewById(R.id.tv_item_dialog_edit_task_reward_skill_name);
        EditText valView= (EditText) view.findViewById(R.id.et_item_dialog_edit_task_reward_skill_val);

        nameView.setText(name);
        valView.setText(String.valueOf(val));
        valView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")&&TextUtils.isDigitsOnly(s.toString())){
                    skills.put(name,Integer.parseInt(s.toString()));
                }
            }
        });

        skillView.addView(view);

  }

    private void initAchievements(){
        task.getSuccessAchievements();
    }

    @Override
    public String getName() {
        return "奖励";
    }

    @Override
    void save() {

        task.setSuccessSkills(skills);

    }

    @OnClick(R.id.bt_dialog_edit_task_reward_add_skill)
    public void addSkill(View view){
        List<String> allSkillName = Game.getInstance().getSkillManager().getAllSkillName();

        new AlertDialog.Builder(getContext()).setSingleChoiceItems(allSkillName.toArray(new String[allSkillName.size()]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newSkillView(allSkillName.get(which),0);
                skills.put(allSkillName.get(which),0);
                dialog.dismiss();
            }
        }).create().show();


    }
}

package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.ItemReward;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qianzise on 2017/10/16.
 * <p>
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
    private Map<String, Integer> skills = new HashMap<>();

    /**
     * 任务完成获得的 成就列表
     */
    private List<AchievementReward> achievements = new LinkedList<>();

    /**
     * 任务完成获得的 物品列表
     */
    private List<ItemReward> itemRewards=new LinkedList<>();

    public EditTaskRewardFragment() {
    }

    public EditTaskRewardFragment setTask(Task task) {
        this.task = task;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_task_reward, container, false);
        ButterKnife.bind(this, view);

        initSkills();
        initAchievements();

        return view;
    }

    /**
     * 初始化 任务获得技能列表
     */
    private void initSkills() {
        skills.clear();
        skills.putAll(task.getSuccessSkills());
        for (Map.Entry<String, Integer> entry : skills.entrySet()) {
            newSkillView(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 初始化 任务获得成就列表
     */
    private void initAchievements() {
        achievements.clear();
        achievements.addAll(task.getSuccessAchievements());
        for (AchievementReward achievementReward : achievements) {
            addNewAchievementRewardView(achievementReward);
        }

    }

    /**
     * 初始化 任务获得物品列表
     */
    private void initItems(){
        itemRewards.clear();
        itemRewards.addAll(task.getSuccessItems());
        for (ItemReward reward : itemRewards) {
            addNewItemView(reward);
        }

    }

    /**
     * 新增一个物品奖励view
     * @param reward 奖励
     */
    private void addNewItemView(ItemReward reward) {

    }

    /**
     * 新增一个奖励技能的view
     *
     * @param name 技能名
     * @param val  xp数值
     */
    private void newSkillView(String name, int val) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_edit_task_reward_skill, skillView, false);

        TextView nameView = (TextView) view.findViewById(R.id.tv_item_dialog_edit_task_reward_skill_name);
        EditText valView = (EditText) view.findViewById(R.id.et_item_dialog_edit_task_reward_skill_val);
        ImageButton del = (ImageButton) view.findViewById(R.id.bt_dialog_edit_task_reward_skill_del);

        //删除技能奖励
        del.setOnClickListener(v -> {
            skills.remove(name);
            skillView.removeView(view);
        });

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
                if (!s.toString().equals("") && TextUtils.isDigitsOnly(s.toString())) {
                    skills.put(name, Integer.parseInt(s.toString()));
                }
            }
        });

        skillView.addView(view);

    }

    private void addNewAchievementRewardView(AchievementReward achievementReward) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_edit_task_reward_achievement, achievementView, false);
        ImageButton del = (ImageButton) view.findViewById(R.id.bt_dialog_edit_task_reward_achievement_del);
        TextView name = (TextView) view.findViewById(R.id.tv_item_dialog_edit_task_reward_achievement_name);
        EditText rate = (EditText) view.findViewById(R.id.et_item_dialog_edit_task_reward_achievement_rate);

        TextInputLayout ratel = (TextInputLayout) view.findViewById(R.id.tl_dialog_edit_task_reward_achievement_rate);


        //删除成就奖励
        del.setOnClickListener(v -> {
            AchievementReward rm = null;
            for (AchievementReward achievement : achievements) {
                if (achievement.equals(achievementReward)) {
                    rm = achievement;
                }
            }
            achievements.remove(rm);
            achievementView.removeView(view);
        });

        name.setText(achievementReward.getAchievement());
        rate.setText(String.valueOf(achievementReward.getProbability()));

        //检测输入数字只能在1-1000之间
        rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.toString().equals("")) {
                    ratel.setErrorEnabled(true);
                    ratel.setError("必须在1-1000之间");
                    return;
                }
                Integer integer = Integer.valueOf(s.toString());
                if (integer > 1000 || integer <= 0) {
                    ratel.setErrorEnabled(true);
                    ratel.setError("必须在1-1000之间");
                } else {
                    ratel.setErrorEnabled(false);
                }

                achievementReward.setProbability(integer);

            }
        });

        achievementView.addView(view);
    }

    @Override
    public String getName() {
        return "奖励";
    }

    @Override
    void save() {

        //保存技能奖励
        task.setSuccessSkills(skills);

        //保存成就奖励
        task.setSuccessAchievements(achievements);

    }


    /**
     * 点击新增奖励技能
     *
     * @param view view
     */
    @OnClick(R.id.bt_dialog_edit_task_reward_add_skill)
    public void addSkill(View view) {
        List<String> allSkillName = Stream.of(Game.getInstance().getSkillManager().getAllSkillName()).
                filterNot(value -> skills.containsKey(value)).//排除已经添加了的技能
                collect(Collectors.toList());
        if (allSkillName == null || allSkillName.isEmpty()) {
            //没有可用技能
            ViewUtil.showToast("没有技能可供选择");
            return;
        }

        new AlertDialog.Builder(getContext()).setSingleChoiceItems(allSkillName.toArray(new String[allSkillName.size()]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newSkillView(allSkillName.get(which), 0);
                skills.put(allSkillName.get(which), 0);
                dialog.dismiss();
            }
        }).create().show();


    }

    /**
     * 点击新增奖励技能
     *
     * @param view view
     */
    @OnClick(R.id.bt_dialog_edit_task_reward_add_achievement)
    public void addAchievement(View view) {
        if (Game.getInstance().getAchievementManager().getNoGetAchievment() == null) {
            //没有成就可选

            ViewUtil.showToast("没有成就可供选择");
            return;
        }
        List<String> achievementNames = Stream.of(Game.getInstance().getAchievementManager().getNoGetAchievment()).
                filterNot(value -> achievements.contains(new AchievementReward(value.getName(), 0))).//排除已经添加了的成就
                map(Achievement::getName).
                collect(Collectors.toList());
        if (achievementNames == null || achievementNames.isEmpty()) {
            //没有成就可选

            ViewUtil.showToast("没有成就可供选择");
            return;
        }

        //弹出选择框
        new AlertDialog.Builder(getContext()).setSingleChoiceItems(achievementNames.toArray(new String[achievementNames.size()]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AchievementReward reward = new AchievementReward(achievementNames.get(which), 1000);
                addNewAchievementRewardView(reward);
                achievements.add(reward);
                dialog.dismiss();
            }
        }).create().show();

    }
}

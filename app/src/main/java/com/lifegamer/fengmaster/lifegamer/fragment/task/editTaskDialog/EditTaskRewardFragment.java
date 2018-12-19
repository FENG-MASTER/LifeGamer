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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskRewardBinding;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SearchAndSelectDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qianzise on 2017/10/16.
 * <p>
 * 任务编辑对话框 奖励部分
 */

public class EditTaskRewardFragment extends EditTaskDialog.SaveableFragment {


    private DialogEditTaskRewardBinding binding;

    private Task task;

    /**
     * 任务完成获得的 技能列表
     */
    private Map<Long, Integer> skills = new HashMap<>();

    /**
     * 任务完成获得的 成就列表
     */
    private List<AchievementReward> achievements = new LinkedList<>();

    /**
     * 任务完成获得的 物品列表
     */
    private List<RandomItemReward> randomItemRewards =new LinkedList<>();

    public EditTaskRewardFragment() {
    }

    public EditTaskRewardFragment setTask(Task task) {
        this.task = task;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogEditTaskRewardBinding.inflate(inflater);
        View view = binding.getRoot();

        binding.setTask(task);

        ButterKnife.bind(this, view);

        initSkills();
        initAchievements();
        initItems();

        return view;
    }

    /**
     * 初始化 任务获得技能列表
     */
    private void initSkills() {
        skills.clear();
        if (task.getSuccessSkills()==null){
            return;
        }
        skills.putAll(task.getSuccessSkills());
        for (Map.Entry<Long, Integer> entry : skills.entrySet()) {
            newSkillView(Game.getInstance().getSkillManager().getSkill(entry.getKey()), entry.getValue());
        }
    }

    /**
     * 初始化 任务获得成就列表
     */
    private void initAchievements() {
        achievements.clear();
        if (task.getSuccessAchievements()==null){
            return;
        }
        achievements.addAll(task.getSuccessAchievements());
        for (AchievementReward achievementReward : achievements) {
            addNewAchievementRewardView(achievementReward);
        }

    }

    /**
     * 初始化 任务获得物品列表
     */
    private void initItems(){
        randomItemRewards.clear();
        if (task.getSuccessItems()==null){
            return;
        }
        randomItemRewards.addAll(task.getSuccessItems());
        for (RandomItemReward reward : randomItemRewards) {
            addNewItemView(reward);
        }

    }

    /**
     * 新增一个物品奖励view
     * @param reward 奖励
     */
    private void addNewItemView(RandomItemReward reward) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_edit_task_reward_item, binding.llDialogEditTaskTimeFinishItem, false);

        ImageButton del= (ImageButton) view.findViewById(R.id.bt_dialog_edit_task_reward_item_del);
        TextView name= (TextView) view.findViewById(R.id.tv_item_dialog_edit_task_reward_item_name);
        TextInputLayout ratel= (TextInputLayout) view.findViewById(R.id.tl_dialog_edit_task_reward_item_rate);
        EditText rate= (EditText) view.findViewById(R.id.et_item_dialog_edit_task_reward_item_rate);
        EditText num= (EditText) view.findViewById(R.id.et_item_dialog_edit_task_reward_item_num);

        RewardItem rewardItem = Game.getInstance().getRewardManager().getRewardItem(reward.getRewardID());
        name.setText(rewardItem.getName());
        rate.setText(String.valueOf(reward.getProbability()));
        num.setText(String.valueOf(reward.getNum()));


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomItemRewards.remove(reward);
                binding.llDialogEditTaskTimeFinishItem.removeView(view);
            }
        });

        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.toString().equals("")) {
                    return;
                }
                reward.setNum(Integer.valueOf(s.toString()));
            }
        });

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

                reward.setProbability(integer);

            }
        });

        binding.llDialogEditTaskTimeFinishItem.addView(view);


    }

    /**
     * 新增一个奖励技能的view
     *
     * @param skill 技能
     * @param val  xp数值
     */
    private void newSkillView(Skill skill, int val) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_edit_task_reward_skill, binding.llDialogEditTaskTimeFinishSkill, false);

        TextView nameView = (TextView) view.findViewById(R.id.tv_item_dialog_edit_task_reward_skill_name);
        EditText valView = (EditText) view.findViewById(R.id.et_item_dialog_edit_task_reward_skill_val);
        ImageButton del = (ImageButton) view.findViewById(R.id.bt_dialog_edit_task_reward_skill_del);

        //删除技能奖励
        del.setOnClickListener(v -> {
            skills.remove(skill.getId());
            binding.llDialogEditTaskTimeFinishSkill.removeView(view);
        });

        nameView.setText(skill.getName());
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
                    skills.put(skill.getId(), Integer.parseInt(s.toString()));
                }
            }
        });

        binding.llDialogEditTaskTimeFinishSkill.addView(view);

    }

    private void addNewAchievementRewardView(AchievementReward achievementReward) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_edit_task_reward_achievement, binding.llDialogEditTaskTimeFinishAchievement, false);
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
            binding.llDialogEditTaskTimeFinishAchievement.removeView(view);
        });

        name.setText(Game.getInstance().getAchievementManager().getAchievement(achievementReward.getAchievementID()).getName());
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

        binding.llDialogEditTaskTimeFinishAchievement.addView(view);
    }

    @Override
    public String getName() {
        return "奖励";
    }

    @Override
    boolean save() {

        //保存技能奖励
        task.setSuccessSkills(skills);

        //保存成就奖励
        task.setSuccessAchievements(achievements);

        //保存物品奖励
        task.setSuccessItems(randomItemRewards);

        //保存金币奖励数目
        task.setEarnLP(binding.etDialogEditTaskRewardEarnLp.getText().toString().equals("")?0:Integer.valueOf(binding.etDialogEditTaskRewardEarnLp.getText().toString()));

        return true;
    }


    /**
     * 点击新增奖励技能
     *
     * @param view view
     */
    @OnClick(R.id.bt_dialog_edit_task_reward_add_skill)
    public void addSkill(View view) {
        List<Skill> allSkill=Stream.of(Game.getInstance().getSkillManager().getAllSkill()).
                filterNot(value -> skills.containsKey(value.getId())).//排除已经添加了的技能
                collect(Collectors.toList());

        //显示用的名字列表
        List<String> allSkillName = Stream.of(allSkill).
                map(Skill::getName).
                collect(Collectors.toList());
        if (allSkillName == null || allSkillName.isEmpty()) {
            //没有可用技能
            ViewUtil.showToast("没有技能可供选择");
            return;
        }

        new AlertDialog.Builder(getContext()).setSingleChoiceItems(allSkillName.toArray(new String[allSkillName.size()]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newSkillView(allSkill.get(which), 0);
                skills.put(allSkill.get(which).getId(), 0);
                dialog.dismiss();
            }
        }).create().show();


    }

    /**
     * 点击新增物品奖励
     * @param view view
     */
    @OnClick(R.id.bt_dialog_edit_task_reward_item_add)
    public void addItem(View view){
        if (Game.getInstance().getRewardManager().getAllAvailableRewardItem() == null) {
            //没有奖励可选

            ViewUtil.showToast("没有奖励可供选择");
            return;
        }

        List<RewardItem> rewardItems=Game.getInstance().getRewardManager().getAllAvailableRewardItem();

        List<String> rewardsName = Stream.of(Game.getInstance().getRewardManager().getAllAvailableRewardItem()).
                map(RewardItem::getName).
                collect(Collectors.toList());
        if (rewardsName == null || rewardsName.isEmpty()) {
            //没有奖励可选

            ViewUtil.showToast("没有奖励可供选择");
            return;
        }

        SearchAndSelectDialog<RewardItem> dialog = new SearchAndSelectDialog<RewardItem>();
        dialog.setItemList(rewardItems).setItemKeyFunction(new Function<RewardItem, String>() {
            @Override
            public String apply(RewardItem rewardItem) {
                return rewardItem.getName();
            }
        });

        dialog.show(getFragmentManager(), "select");


//        //弹出选择框
//        new AlertDialog.Builder(getContext()).setSingleChoiceItems(rewardsName.toArray(new String[rewardsName.size()]), 0, (dialog, which) -> {
//            RandomItemReward reward=new RandomItemReward(rewardItems.get(which).getId(),1,1000);
//            addNewItemView(reward);
//            randomItemRewards.add(reward);
//            dialog.dismiss();
//        }).create().show();

    }

    /**
     * 点击新增奖励技能
     *
     * @param view view
     */
    @OnClick(R.id.bt_dialog_edit_task_reward_add_achievement)
    public void addAchievement(View view) {
        if (Game.getInstance().getAchievementManager().getAllNoGetAchievment() == null) {
            //没有成就可选

            ViewUtil.showToast("没有成就可供选择");
            return;
        }

        List<Achievement> allAchievements=
                Stream.of(Game.getInstance().getAchievementManager().getAllNoGetAchievment()).
                        //排除已经添加了的成就
                        filterNot(value -> achievements.contains(new AchievementReward(value.getId(), 0))).
                        collect(Collectors.toList());


        List<String> achievementNames = Stream.of(allAchievements).
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
                AchievementReward reward = new AchievementReward(allAchievements.get(which).getId(), 1000);
                addNewAchievementRewardView(reward);
                achievements.add(reward);
                dialog.dismiss();
            }
        }).create().show();

    }
}

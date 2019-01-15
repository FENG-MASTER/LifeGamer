package com.lifegamer.fengmaster.lifegamer.fragment.trigger;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by FengMaster on 19/01/14.
 */
public class EditTriggerListAdapter extends RecyclerView.Adapter<EditTriggerListAdapter.Holder> {

    /**
     * 技能种类标记
     */
    private static final int TYPE_SKILL = 1;
    /**
     * 物品种类标记
     */
    private static final int TYPE_ITEM = 2;
    /**
     * 成就种类标记
     */
    private static final int TYPE_ACHIEVEMENT = 3;

    private Trigger trigger;

    private TriggerInfo triggerInfo;

    /**
     * 触发器设定的成就列表
     */
    private Set<AchievementReward> achievements = new HashSet<>();

    /**
     * 触发器设定的物品列表
     */
    private Set<RandomItemReward> items = new HashSet<>();

    /**
     * 触发器设置的技能列表
     */
    private Map<Long, Integer> skills = new HashMap<>();

    public EditTriggerListAdapter(Trigger trigger) {
        this.trigger = trigger;
        triggerInfo = trigger.getTriggerInfo();
        skills.putAll(triggerInfo.getSkills());
        achievements.addAll(triggerInfo.getAchievements());
        items.addAll(triggerInfo.getItems());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SKILL:
                return new SkillHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dialog_edit_task_reward_skill, parent, false));
            case TYPE_ITEM:
                return new ItemHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dialog_edit_task_reward_item, parent, false));
            case TYPE_ACHIEVEMENT:
                return new AchievementHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_dialog_edit_task_reward_achievement, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int viewType = getItemViewType(position);
        holder.setPos(position);
        switch (viewType){
            case TYPE_SKILL:
                Map.Entry<Long, Integer> entry = Stream.of(skills).skip(position).findFirst().get();
                Skill skill = Game.getInstance().getSkillManager().getSkill(entry.getKey());
                ((SkillHolder)holder).setSkill(skill,entry.getValue());
                break;
            case TYPE_ITEM:
                RandomItemReward itemReward = Stream.of(items).skip(position - skills.size()).findFirst().get();
                ((ItemHolder)holder).setItem(itemReward);
                break;
            case TYPE_ACHIEVEMENT:
                AchievementReward achievementReward =Stream.of(achievements).skip(position - skills.size() - items.size()).findFirst().get();
                Achievement achievement = Game.getInstance().getAchievementManager().getAchievement(achievementReward.getAchievementID());
                ((AchievementHolder)holder).setAchievement(achievement,achievementReward.getProbability());
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        position=position+1;
        if (position <= skills.size()) {
            return TYPE_SKILL;
        } else if (position <= (items.size() + skills.size())) {
            return TYPE_ITEM;
        } else {
            return TYPE_ACHIEVEMENT;
        }
    }

    @Override
    public int getItemCount() {
        return skills.size() + items.size() + achievements.size();
    }


    /**
     * 技能holder
     */
    public class SkillHolder extends Holder implements TextWatcher {


        @BindView(R.id.tv_item_dialog_edit_task_reward_skill_name)
        public TextView skillName;

        @BindView(R.id.et_item_dialog_edit_task_reward_skill_val)
        public EditText skillXp;

        private Skill skill;

        public SkillHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        public void setSkill(Skill skill,int val){
            this.skill=skill;
            skillName.setText(skill.getName());
            skillXp.setText(String.valueOf(val));
            skillXp.addTextChangedListener(this);
        }

        @OnClick(R.id.bt_dialog_edit_task_reward_skill_del)
        public void del(View v) {
            //长按表示删除
            if (skill!=null){
                skills.remove(skill.getId());
            }
            skill=null;
            skillXp.removeTextChangedListener(this);
            notifyItemRemoved(pos);

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (skill!=null&&!s.toString().equals("") && s.length()!=0) {
                if (s.length()>1&&(s.subSequence(0,1).toString().equals("+")||s.subSequence(0,1).toString().equals("-"))){
                    skills.put(skill.getId(), Integer.parseInt(s.toString()));
                }else if (TextUtils.isDigitsOnly(s.toString())){
                    skills.put(skill.getId(), Integer.parseInt(s.toString()));
                }
            }
        }
    }

    /**
     * 物品holder
     */
    public class ItemHolder extends Holder {

        @BindView(R.id.tv_item_dialog_edit_task_reward_item_name)
        public TextView itemName;

        @BindView(R.id.et_item_dialog_edit_task_reward_item_num)
        public EditText itemNum;

        @BindView(R.id.et_item_dialog_edit_task_reward_item_rate)
        public EditText itemRate;

        @BindView(R.id.tl_dialog_edit_task_reward_item_rate)
        public TextInputLayout itemRateL;

        private TextWatcher numWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (randomItemReward==null||s == null || s.toString().equals("")) {
                    return;
                }
                if (s.length()>1&&(s.subSequence(0,1).toString().equals("+")||s.subSequence(0,1).toString().equals("-"))){
                    randomItemReward.setNum(Integer.valueOf(s.toString()));
                }else if (TextUtils.isDigitsOnly(s.toString())){
                    randomItemReward.setNum(Integer.valueOf(s.toString()));
                }
            }
        };

        private TextWatcher rateWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (randomItemReward==null){
                    return;
                }
                if (s == null || s.toString().equals("")) {
                    itemRateL.setErrorEnabled(true);
                    itemRateL.setError("必须在-1000到1000之间");
                    return;
                }
                Integer integer = Integer.valueOf(s.toString());
                if (integer > 1000 || integer < -1000) {
                    itemRateL.setErrorEnabled(true);
                    itemRateL.setError("必须在-1000到1000之间");
                } else {
                    itemRateL.setErrorEnabled(false);
                }

                randomItemReward.setProbability(integer);

            }
        };

        private RandomItemReward randomItemReward;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void setItem(RandomItemReward rewardItem){
            this.randomItemReward=rewardItem;
            RewardItem item = Game.getInstance().getRewardManager().getRewardItem(rewardItem.getRewardID());
            itemName.setText(item.getName());
            itemRate.setText(String.valueOf(rewardItem.getProbability()));
            itemNum.setText(String.valueOf(rewardItem.getNum()));

            itemNum.addTextChangedListener(numWatcher);

            itemRate.addTextChangedListener(rateWatcher);

        }

        @OnClick(R.id.bt_dialog_edit_task_reward_item_del)
        public void onLongClick(View v) {
            if (randomItemReward!=null){
                items.remove(randomItemReward);
            }
            randomItemReward=null;
            itemNum.removeTextChangedListener(numWatcher);
            itemRate.removeTextChangedListener(rateWatcher);
            notifyItemRemoved(pos);
        }
    }


    /**
     * 成就holder
     */
    public class AchievementHolder extends Holder implements TextWatcher {

        @BindView(R.id.bt_dialog_edit_task_reward_achievement_del)
        public ImageButton del;

        @BindView(R.id.tv_item_dialog_edit_task_reward_achievement_name)
        public TextView achievementName;

        @BindView(R.id.et_item_dialog_edit_task_reward_achievement_rate)
        public EditText achievementRate;

        @BindView(R.id.tl_dialog_edit_task_reward_achievement_rate)
        public TextInputLayout achievementRateL;

        private Achievement achievement;

        public AchievementHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setAchievement(Achievement achievement,int rate){
            this.achievement=achievement;
            achievementName.setText(achievement.getName());
            achievementRate.setText(String.valueOf(rate));
             //检测输入数字只能在1-1000之间
            achievementRate.addTextChangedListener(this);

        }

        @OnClick(R.id.bt_dialog_edit_task_reward_achievement_del)
        public void del(View v) {
            AchievementReward rm = null;
            if (achievement!=null){
                for (AchievementReward achievement : achievements) {
                    if (achievement.getAchievementID()==this.achievement.getId()) {
                        rm = achievement;
                    }
                }
                achievements.remove(rm);
                achievementRate.removeTextChangedListener(this);
            }
            this.achievement=null;
            notifyItemRemoved(pos);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (achievement==null){
                return;
            }
            if (s == null || s.toString().equals("")) {
                achievementRateL.setErrorEnabled(true);
                achievementRateL.setError("必须在1-1000之间");
                return;
            }
            Integer integer = Integer.valueOf(s.toString());
            if (integer > 1000 || integer <= 0) {
                achievementRateL.setErrorEnabled(true);
                achievementRateL.setError("必须在1-1000之间");
            } else {
                achievementRateL.setErrorEnabled(false);
            }
            achievements.add(new AchievementReward(achievement.getId(),integer));
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        protected int pos;

        public Holder(View itemView) {
            super(itemView);
        }

        public void setPos(int pos) {
            this.pos = pos;
        }
    }



    public List<AchievementReward> getAchievements() {
        ArrayList<AchievementReward> achievementRewards = new ArrayList<>();
        achievementRewards.addAll(achievements);
        return achievementRewards;
    }

    public List<RandomItemReward> getItems() {
        ArrayList<RandomItemReward> rewards = new ArrayList<>();
        rewards.addAll(items);
        return rewards;
    }

    public Map<Long, Integer> getSkills() {
        return skills;
    }


    public void addAchievement(AchievementReward achievementReward){
        achievements.add(achievementReward);
    }

    public void addItem(RandomItemReward itemReward){
        items.add(itemReward);
    }

    public void addSkill(Long skillId,int xp){
        skills.put(skillId,xp);
    }

}

package com.lifegamer.fengmaster.lifegamer.fragment.trigger;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;

import java.util.List;
import java.util.Map;


/**
 * Created by FengMaster on 19/01/14.
 */
public class EditTriggerListAdapter extends RecyclerView.Adapter<EditTriggerListAdapter.Holder> {

    /**
     * 技能种类标记
     */
    private static final int TYPE_SKILL=1;
    /**
     * 物品种类标记
     */
    private static final int TYPE_ITEM=2;
    /**
     * 成就种类标记
     */
    private static final int TYPE_ACHIEVEMENT=3;

    private Trigger trigger;

    private TriggerInfo triggerInfo;

    /**
     * 触发器设定的成就列表
     */
    private List<AchievementReward> achievements;

    /**
     * 触发器设定的物品列表
     */
    private List<RandomItemReward> items;

    /**
     * 触发器设置的技能列表
     */
    private Map<Long, Integer> skills;

    public EditTriggerListAdapter(Trigger trigger) {
        this.trigger = trigger;
        triggerInfo=trigger.getTriggerInfo();
        skills=triggerInfo.getSkills();
        achievements=triggerInfo.getAchievements();
        items=triggerInfo.getItems();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position<=skills.size()){
            return TYPE_SKILL;
        }else if (position<=(items.size()+skills.size())){
            return TYPE_ITEM;
        }else {
            return TYPE_ACHIEVEMENT;
        }
    }

    @Override
    public int getItemCount() {
        return skills.size()+items.size()+achievements.size();
    }


    public List<AchievementReward> getAchievements() {
        return achievements;
    }

    public List<RandomItemReward> getItems() {
        return items;
    }

    public Map<Long, Integer> getSkills() {
        return skills;
    }

    /**
     * 技能holder
     */
    public class SkillHolder extends Holder{

        public SkillHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 物品holder
     */
    public class ItemHolder extends Holder{

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * 成就holder
     */
    public class AchievementHolder extends Holder{

        public AchievementHolder(View itemView) {
            super(itemView);
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }



}

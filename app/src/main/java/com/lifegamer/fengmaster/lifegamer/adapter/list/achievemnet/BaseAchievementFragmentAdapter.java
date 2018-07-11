package com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/23.
 */

public abstract class BaseAchievementFragmentAdapter extends AbsBaseRecyclerViewAdapter<BaseAchievementFragmentAdapter.Holder, Achievement> {

    protected List<Achievement> achievements;

    public abstract void updateAchievements();

    private List<OnItemSelectListener<Achievement>> listeners=new ArrayList<>();


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (achievements==null){
            updateAchievements();
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_achievement, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setAchievement(achievements.get(position));
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<Achievement> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<Achievement> listener) {
        listeners.remove(listener);
    }

    public class Holder extends BindingHolder{

        private Achievement achievement;

        public void setAchievement(Achievement achievement) {
            this.achievement = achievement;
            setBinding(BR.achievement,achievement);
        }

        public Holder(View itemView) {
            super(itemView);

        }
    }

}

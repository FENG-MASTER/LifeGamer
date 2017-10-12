package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;

import com.lifegamer.fengmaster.lifegamer.databinding.ItemAllSkillBinding;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/10.
 */

public class AllSkillFragmentAdapter extends BaseRecyclerViewAdapter<AllSkillFragmentAdapter.Holder> {
    private List<Skill> list;

    private List<OnItemSelectListener<Skill>> listeners=new LinkedList<>();


    public AllSkillFragmentAdapter() {
        list= Game.getInstance().getSkillManager().getAllSkill();
        EventBus.getDefault().register(this);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_skill, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setBinding(BR.skill,list.get(position));
        holder.setSkill(list.get(position));
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void newSkill(NewSkillEvent event){
        list=Game.getInstance().getSkillManager().getAllSkill();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public String getName() {
        return "全部";
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener listener) {
        listeners.remove(listener);
    }

    private void notifyListener(Skill skill){
        for (OnItemSelectListener<Skill> listener : listeners) {
            listener.onItemSelect(skill);
        }
    }


    public class Holder extends BindingHolder implements View.OnClickListener {

        @BindView(R.id.ib_item_all_skill_more)
        ImageButton more;

        private Skill skill;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            more.setOnClickListener(this);
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }

        public Skill getSkill() {
            return skill;
        }

        @Override
        public void onClick(View view) {
            //请求显示选择框
            notifyListener(skill);
        }
    }

}

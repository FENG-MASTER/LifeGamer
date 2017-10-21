package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/14.
 */

public abstract class BaseSkillFragmentAdapter extends BaseRecyclerViewAdapter<BaseSkillFragmentAdapter.Holder, Skill> {
    protected List<Skill> showSkillList;

    /**
     * 选中技能 监听者
     */
    private List<OnItemSelectListener<Skill>> listeners = new LinkedList<>();

    public abstract void updateList();

    public BaseSkillFragmentAdapter() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (showSkillList==null){
            updateList();
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_skill, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setBinding(BR.skill, showSkillList.get(position));
        holder.setSkill(showSkillList.get(position));
    }

    @Override
    public int getItemCount() {
        return showSkillList.size();
    }

    /**
     * 从父RecyclerView中收回的时候调用
     * @param recyclerView 父
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        //内存泄漏
        EventBus.getDefault().unregister(this);
    }

    /**
     * 这个监听方法是为了由于新建技能,所有技能列表发生改变,重新更新数据
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void newSkill(NewSkillEvent event) {
        updateList();
        notifyDataSetChanged();
    }

    /**
     * 有技能被删除,需要更新
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void delSkill(DelSkillEvent event){
        updateList();
        notifyDataSetChanged();
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<Skill> listener) {
        listeners.add(listener);

    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<Skill> listener) {
        listeners.remove(listener);
    }

    private void notifyListener(Skill skill) {
        for (OnItemSelectListener<Skill> listener : listeners) {
            listener.onItemSelect(skill);
        }
    }

    public class Holder extends BindingHolder implements View.OnClickListener {


        private Skill skill;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public Skill getSkill() {
            return skill;
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }

        @Override
        public void onClick(View view) {
            //请求显示选择框
            notifyListener(skill);
        }
    }
}

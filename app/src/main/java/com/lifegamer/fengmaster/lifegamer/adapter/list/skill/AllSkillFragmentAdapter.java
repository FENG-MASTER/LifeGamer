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

import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.databinding.ItemAllSkillBinding;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
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
 * <p>
 * 显示所有技能的适配器
 */

public class AllSkillFragmentAdapter extends BaseRecyclerViewAdapter<AllSkillFragmentAdapter.Holder, Skill> {

    /**
     * 所有技能list
     */
    private List<Skill> list;

    /**
     * 选中技能 监听者
     */
    private List<OnItemSelectListener<Skill>> listeners = new LinkedList<>();


    public AllSkillFragmentAdapter() {
        //获取到所有技能
        list = Game.getInstance().getSkillManager().getAllSkill();
        EventBus.getDefault().register(this);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_skill, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setBinding(BR.skill, list.get(position));
        holder.setSkill(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
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
        list = Game.getInstance().getSkillManager().getAllSkill();
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void delSkill(DelSkillEvent event){
        list = Game.getInstance().getSkillManager().getAllSkill();
        notifyDataSetChanged();
    }

    @Override
    public String getName() {
        return "全部";
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

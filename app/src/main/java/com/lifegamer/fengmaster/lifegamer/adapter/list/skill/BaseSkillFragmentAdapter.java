package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import android.support.v7.widget.RecyclerView;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 基础 能力实现适配器
 */

public abstract class BaseSkillFragmentAdapter extends BaseRecyclerViewAdapter<Skill> {

    public BaseSkillFragmentAdapter() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (showList==null){
            updateAdapterList();
        }
    }


    @Override
    public int getItemLayoutID() {
        return R.layout.item_base_skill;
    }

    @Override
    public int getBindingItemID() {
        return BR.skill;
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
     * 这个监听方法是为了由于新建能力,所有能力列表发生改变,重新更新数据
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void newSkill(NewSkillEvent event) {
        updateAdapterList();
        notifyDataSetChanged();
    }

    /**
     * 有能力被删除,需要更新
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void delSkill(DelSkillEvent event){
        updateAdapterList();
        notifyDataSetChanged();
    }

}

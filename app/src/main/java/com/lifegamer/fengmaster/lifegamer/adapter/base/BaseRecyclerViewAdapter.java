package com.lifegamer.fengmaster.lifegamer.adapter.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;


import java.util.ArrayList;
import java.util.List;

/**
 * 基础适配器,集成了item被点击后弹窗接口
 * Created by FengMaster on 18/07/11.
 */
public abstract class BaseRecyclerViewAdapter<R> extends AbsBaseRecyclerViewAdapter<BaseRecyclerViewAdapter.BaseRecyclerViewHolder,R> {

    //所有需要显示的数据<泛型>,任务,成就等
    protected List<R> showList=null;

    protected List<OnItemSelectListener<R>> clickListeners=new ArrayList<>();

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutID(), parent, false);
        BaseRecyclerViewHolder t=new BaseRecyclerViewHolder(inflate);
        return t;
    }


    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseRecyclerViewHolder holder, int position) {
        holder.setObj(getBindingItemID(),showList.get(position));
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (showList==null){
            updateAdapterList();
        }
    }

    /**
     * 通知所有监听点击事件的监听者
     * @param r
     */
    protected void notifyClickListener(R r){
        Stream.of(clickListeners).forEach(rOnItemSelectListener -> rOnItemSelectListener.onItemSelect(r));
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<R> listener) {
        clickListeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<R> listener) {
        clickListeners.remove(listener);
    }



    /**
     * 更新显示内容
     */
    public abstract void updateShowList();

    public void updateAdapterList(){
        updateShowList();
        notifyDataSetChanged();
    }

    public boolean hasData(){
        updateAdapterList();
        return showList!=null&&!showList.isEmpty();
    }

    /**
     * 获取显示每个item的layoutID
     * @return layoutID
     */
    public abstract int getItemLayoutID();

    /**
     * 获取用于绑定layout对象的ID
     * @return 对象ID
     */
    public abstract int getBindingItemID();

    public class BaseRecyclerViewHolder extends BindingHolder implements View.OnClickListener {

        protected R obj;

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setObj(int id,R obj){
            this.obj=obj;
            setBinding(id,obj);
        }


        @Override
        public void onClick(View view) {
            notifyClickListener(obj);
        }
    }
}

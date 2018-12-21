package com.lifegamer.fengmaster.lifegamer.adapter.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/12.
 */

public class SelectDialogItemAdapter extends AbsBaseRecyclerViewAdapter<SelectDialogItemAdapter.Holder,SelectItem> {
    private List<SelectItem> items;

    private List<OnItemSelectListener<SelectItem>> listeners=new LinkedList<>();

    public SelectDialogItemAdapter(List<SelectItem> items) {
        this.items = items;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_adapter, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.icon.setImageResource(items.get(position).getIconRes());
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.select);
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener listener) {
        listeners.remove(listener);
    }

    private void notifyListener(SelectItem item){
        for (OnItemSelectListener<SelectItem> listener : listeners) {
            listener.onItemSelect(item);
        }
    }



    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.siv_item_select_icon)
        ImageView icon;
        @BindView(R.id.tv_item_select_name)
        TextView name;

        private SelectItem item;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            icon.setOnClickListener(this);

        }

        public SelectItem getItem() {
            return item;
        }

        public void setItem(SelectItem item) {
            this.item = item;
        }

        @Override
        public void onClick(View view) {
            notifyListener(item);
        }
    }
}

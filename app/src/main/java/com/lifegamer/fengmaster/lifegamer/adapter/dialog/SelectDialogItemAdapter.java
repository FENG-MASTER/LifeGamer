package com.lifegamer.fengmaster.lifegamer.adapter.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.SqureImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/12.
 */

public class SelectDialogItemAdapter extends RecyclerView.Adapter<SelectDialogItemAdapter.Holder> {
    private List<SelectDialog.SelectItem> items;

    public SelectDialogItemAdapter(List<SelectDialog.SelectItem> items) {
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.siv_item_select_icon)
        ImageView icon;
        @BindView(R.id.tv_item_select_name)
        TextView name;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

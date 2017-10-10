package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;

/**
 * Created by qianzise on 2017/10/10.
 */

public class AllSkillFragmentAdapter extends BaseRecyclerViewAdapter<AllSkillFragmentAdapter.Holder> {



    public AllSkillFragmentAdapter() {

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public String getName() {
        return "技能";
    }

    public static class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }

}

package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;

/**
 * Created by qianzise on 2017/10/10.
 */

public class TypeSkillFragmentAdapter extends BaseRecyclerViewAdapter<TypeSkillFragmentAdapter.Holder>{


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(parent);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public String getName() {
        return "厨艺";
    }

    public static class Holder extends RecyclerView.ViewHolder{

        public Holder(View itemView) {
            super(itemView);
        }
    }
}

package com.lifegamer.fengmaster.lifegamer.adapter.dialog;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 头像选择适配器
 */

public class AvatarSelectAdapter extends BaseRecyclerViewAdapter<AvatarSelectAdapter.Holder,IAvatarManager.Avatar> {

    private List<String> avatarNameList;

    private List<OnItemSelectListener<IAvatarManager.Avatar>> listeners=new ArrayList<>();

    public AvatarSelectAdapter(){
        avatarNameList=Game.getInstance().getAvatarManager().getAllDrawableName();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_avatar, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setAvatar(Game.getInstance().getAvatarManager().getAvatar(avatarNameList.get(position)));
    }

    @Override
    public int getItemCount() {
        return avatarNameList.size();
    }

    @Override
    public String getName() {
        return "头像";
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<IAvatarManager.Avatar> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<IAvatarManager.Avatar> listener) {
        listeners.remove(listener);
    }

    private void notifyListener(IAvatarManager.Avatar avatar){
        for (OnItemSelectListener<IAvatarManager.Avatar> listener : listeners) {
            listener.onItemSelect(avatar);
        }
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.siv_item_select_icon)
        ImageView icon;
        @BindView(R.id.tv_item_select_name)
        TextView name;

        private IAvatarManager.Avatar avatar;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            icon.setOnClickListener(this);

        }

        public IAvatarManager.Avatar getAvatar() {
            return avatar;
        }

        public void setAvatar(IAvatarManager.Avatar avatar) {
            this.avatar = avatar;
            name.setText(avatar.getName());
            icon.setImageDrawable(avatar.getIcon());
        }

        @Override
        public void onClick(View view) {
            notifyListener(avatar);
        }
    }



}

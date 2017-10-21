package com.lifegamer.fengmaster.lifegamer.wight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.ItemSelectObservable;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.dialog.AvatarSelectAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.dialog.SelectDialogItemAdapter;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 头像选择对话框
 */

public class AvatarSelectDialog extends DialogFragment implements OnItemSelectListener<IAvatarManager.Avatar>,ItemSelectObservable<IAvatarManager.Avatar> {

    private RecyclerView recyclerView;

    private List<OnItemSelectListener<IAvatarManager.Avatar>> listeners=new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.dialog_select_avatar, container, false);
        recyclerView= (RecyclerView) inflate;

        AvatarSelectAdapter adapter = new AvatarSelectAdapter();
        adapter.addItemSelectListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        return inflate;
    }

    @Override
    public void onItemSelect(IAvatarManager.Avatar avatar) {
        for (OnItemSelectListener<IAvatarManager.Avatar> listener : listeners) {
            listener.onItemSelect(avatar);
        }
        dismiss();
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<IAvatarManager.Avatar> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<IAvatarManager.Avatar> listener) {
        listeners.remove(listener);
    }
}

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
import com.lifegamer.fengmaster.lifegamer.adapter.dialog.SelectDialogItemAdapter;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/12.
 *
 * 操作选择对话框
 */

public class SelectDialog extends DialogFragment implements OnItemSelectListener<SelectItem>,ItemSelectObservable<SelectItem> {
    private List<SelectItem> itemList;
    private List<OnItemSelectListener<SelectItem>> listeners = new ArrayList<>();

    public SelectDialog setItems(List<SelectItem> items) {
        itemList = items;
        return this;
    }
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (itemList==null){
            try {
                throw new Exception("必须先调用setItems初始化数据");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        View inflate = inflater.inflate(R.layout.dialog_select_operate, container, false);
        recyclerView= (RecyclerView) inflate;

        SelectDialogItemAdapter selectDialogItemAdapter = new SelectDialogItemAdapter(itemList);

        selectDialogItemAdapter.addItemSelectListener(this);
        recyclerView.getMinimumHeight();
        recyclerView.setAdapter(selectDialogItemAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        return inflate;
    }

    @Override
    public void onItemSelect(SelectItem item) {
        for (OnItemSelectListener<SelectItem> listener : listeners) {
            listener.onItemSelect(item);
        }
        dismiss();
    }


    @Override
    public void addItemSelectListener(OnItemSelectListener<SelectItem> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<SelectItem> listener) {
        listeners.remove(listener);
    }
}

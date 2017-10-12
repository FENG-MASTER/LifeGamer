package com.lifegamer.fengmaster.lifegamer.wight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.dialog.SelectDialogItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/12.
 */

public class SelectDialog extends AppCompatDialogFragment {
    private List<SelectItem> itemList;
    private List<OnItemSelectListener> listeners = new ArrayList<>();

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
        View inflate = inflater.inflate(R.layout.dialog_select_adapter, container, false);
        recyclerView= (RecyclerView) inflate;
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(new SelectDialogItemAdapter(itemList));
        return inflate;
    }


    public interface OnItemSelectListener {
        void onItemSelect(int i, SelectItem item);
    }

    public static class SelectItem {

        private String name;
        private int iconRes;

        public SelectItem(String name, int iconRes) {
            this.name = name;
            this.iconRes = iconRes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIconRes() {
            return iconRes;
        }

        public void setIconRes(int iconRes) {
            this.iconRes = iconRes;
        }


    }
}

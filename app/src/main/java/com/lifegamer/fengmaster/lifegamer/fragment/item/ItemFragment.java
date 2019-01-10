package com.lifegamer.fengmaster.lifegamer.fragment.item;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.item.AllItemFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.RemoveSkillCommand;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.skill.EditSkillDialog;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品fragment
 *
 * 属于高层模块,负责整合所有物品相关适配器和相关子fragment
 */
public class ItemFragment extends BaseTabListFragment implements OnItemSelectListener {

    /**
     * 选中的物品
     */
    private Item selectItem;

    public ItemFragment() {
        AllItemFragmentAdapter allItemFragmentAdapter = new AllItemFragmentAdapter();
        allItemFragmentAdapter.addItemSelectListener(this);
        addAdapter(allItemFragmentAdapter);
    }

    @Override
    public void onActionButtonClick() {
        EditItemDialog dialog=new EditItemDialog();
        dialog.show(getChildFragmentManager(),"edit");
    }

    @Override
    public void onItemSelect(Object o) {
        if (o instanceof Item){
            onGameItemSelect((Item) o);
        }else if (o instanceof SelectItem){
            onSelectItemSelect((SelectItem) o);
        }
    }

    @SuppressWarnings("all")
    private void onGameItemSelect(Item item){
        selectItem=item;

        SelectDialog selectDialog = new SelectDialog();

        selectDialog.addItemSelectListener(this);

        List<SelectItem> list = new ArrayList<>();
        list.add(SelectItem.EDIT);
//        list.add(SelectItem.DELETE);
        list.add(SelectItem.NOTES);

        selectDialog.setItems(list).show(getFragmentManager(), "select");
    }

    private void onSelectItemSelect(SelectItem item){

        switch (item.getId()){
            case SelectItem.EDIT_ID:
                //编辑
                if (selectItem!=null){
                    EditItemDialog dialog=new EditItemDialog();
                    dialog.setItem(selectItem);
                    dialog.show(getFragmentManager(),"select");
                }
                break;

            default:

        }


    }


}

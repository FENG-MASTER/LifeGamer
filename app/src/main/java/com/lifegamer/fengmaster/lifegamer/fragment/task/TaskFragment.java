package com.lifegamer.fengmaster.lifegamer.fragment.task;


import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.AllTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog.EditTaskDialog;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务fragment
 *
 *  属于高层模块,负责整合所有任务相关适配器和相关子fragment
 */
public class TaskFragment extends BaseTabListFragment implements OnItemSelectListener {

    /**
     * 当前选中任务
     */
    private Task task;

    public TaskFragment(){
        AllTaskFragmentAdapter allTaskFragmentAdapter = new AllTaskFragmentAdapter();
        allTaskFragmentAdapter.addItemSelectListener(this);
        addAdapter(allTaskFragmentAdapter);
    }


    @Override
    public void onActionButtonClick() {
        EditTaskDialog dialog=new EditTaskDialog();
        dialog.show(getFragmentManager(),"taskEdit");
    }

    @Override
    public void onItemSelect(Object o) {
        if (o instanceof SelectItem){
            onSelectItemSelect((SelectItem) o);
        }else if(o instanceof Task){
            onTaskItemSelect((Task)o);
        }




    }

    private void onTaskItemSelect(Task task) {
        this.task=task;
        SelectDialog dialog=new SelectDialog();
        List<SelectItem> itemList=new ArrayList<>();
        itemList.add(SelectItem.FINISH);
        itemList.add(SelectItem.FAIL);
        itemList.add(SelectItem.EDIT);
        itemList.add(SelectItem.DELETE);
        dialog.setItems(itemList);
        dialog.addItemSelectListener(this);
        dialog.show(getFragmentManager(),"select");

    }

    private void onSelectItemSelect(SelectItem selectItem) {
        switch (selectItem.getId()){
            case SelectItem.EDIT_ID:
                EditTaskDialog dialog=new EditTaskDialog();
                dialog.setTask(task);
                dialog.show(getFragmentManager(),"taskEdit");
                break;
        }
    }
}

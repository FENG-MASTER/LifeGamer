package com.lifegamer.fengmaster.lifegamer.fragment.task;


import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.AllTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog.EditTaskDialog;
import com.lifegamer.fengmaster.lifegamer.model.Task;


public class TaskFragment extends BaseTabListFragment implements OnItemSelectListener<Task> {

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
    public void onItemSelect(Task task) {
        EditTaskDialog dialog=new EditTaskDialog();
        dialog.setTask(task);
        dialog.show(getFragmentManager(),"taskEdit");
    }
}

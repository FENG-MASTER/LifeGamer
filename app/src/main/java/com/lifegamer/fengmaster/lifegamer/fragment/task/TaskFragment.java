package com.lifegamer.fengmaster.lifegamer.fragment.task;


import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog.EditTaskDialog;


public class TaskFragment extends BaseTabListFragment {

    public TaskFragment(){
     //   addAdapter(new AllTaskFragmentAdapter());
    }


    @Override
    public void onActionButtonClick() {
        EditTaskDialog dialog=new EditTaskDialog();
        dialog.setTask(Game.getInstance().getTaskManager().getTask(4));
        dialog.show(getFragmentManager(),"taskEdit");

    }
}

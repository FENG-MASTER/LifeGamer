package com.lifegamer.fengmaster.lifegamer.fragment.task;


import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.AllTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.BaseTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.DailyTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.HourlyTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.MonthlyTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.TodayTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.TypeTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.WeeklyTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.YearlyTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.task.DeleteTaskCommend;
import com.lifegamer.fengmaster.lifegamer.command.command.task.FailTaskCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.task.FinishTaskCommand;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog.EditTaskDialog;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * 任务fragment
 * <p>
 * 属于高层模块,负责整合所有任务相关适配器和相关子fragment
 */
public class TaskFragment extends BaseTabListFragment implements OnItemSelectListener {

    /**
     * 当前选中任务
     */
    private Task task;

    public TaskFragment() {
        super();
        createTypeFragments();
    }

    /**
     * 生成按种类区分的任务fragment
     */
    private void createTypeFragments(){
        if (PreferenceUtil.checkIfShow(TypeTaskFragmentAdapter.class.getSimpleName())){
            List<String> skillType = Game.getInstance().getTaskManager().getAllTaskType();
            for (String type : skillType) {
                TypeTaskFragmentAdapter typeTaskFragmentAdapter = new TypeTaskFragmentAdapter(type);
                typeTaskFragmentAdapter.addItemSelectListener(this);
                addAdapter(typeTaskFragmentAdapter);
            }
        }
    }

    @Override
    public void onActionButtonClick() {
        EditTaskDialog dialog = new EditTaskDialog();
        dialog.show(getFragmentManager(), "taskEdit");
    }

    @Override
    public Class[] getAdapterClasses() {
        return new Class[]{AllTaskFragmentAdapter.class,TodayTaskFragmentAdapter.class, DailyTaskFragmentAdapter.class, HourlyTaskFragmentAdapter.class, WeeklyTaskFragmentAdapter.class, MonthlyTaskFragmentAdapter.class, YearlyTaskFragmentAdapter.class};
    }

    @Override
    public void onItemSelect(Object o) {
        if (o instanceof SelectItem) {
            onSelectItemSelect((SelectItem) o);
        } else if (o instanceof Task) {
            onTaskItemSelect((Task) o);
        }


    }

    private void onTaskItemSelect(Task task) {
        this.task = task;
        SelectDialog dialog = new SelectDialog();
        List<SelectItem> itemList = new ArrayList<>();
        itemList.add(SelectItem.FINISH);
        itemList.add(SelectItem.FAIL);
        itemList.add(SelectItem.EDIT);
        itemList.add(SelectItem.DELETE);
        dialog.setItems(itemList);
        dialog.addItemSelectListener(this);
        dialog.show(getFragmentManager(), "select");

    }

    private void onSelectItemSelect(SelectItem selectItem) {
        switch (selectItem.getId()) {
            case SelectItem.FINISH_ID:
                //完成任务
                finishTask(task);
                break;
            case SelectItem.EDIT_ID:
                // 编辑任务界面
                EditTaskDialog dialog = new EditTaskDialog();
                dialog.setTask(task);
                dialog.show(getFragmentManager(), "taskEdit");
                break;
            case SelectItem.DELETE_ID:
                deletTask(task);
                break;
            case SelectItem.FAIL_ID:
                failTask(task);
                break;
            default:

        }
    }

    private void finishTask(Task task) {
        Game.getInstance().getCommandManager().executeCommand(new FinishTaskCommand(task));
    }

    /**
     * 任务失败
     *
     * @param task
     */
    private void failTask(Task task) {
        Game.getInstance().getCommandManager().executeCommand(new FailTaskCommand(task));
    }


    /**
     * 执行删除任务指令
     *
     * @param task 任务对象
     */
    private void deletTask(Task task) {
        //最终删除命令
        Game.getInstance().getCommandManager().executeCommand(new DeleteTaskCommend(task));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void tabChange(NewTaskEvent newTaskEvent){
        notifyTabChange();
    }
}

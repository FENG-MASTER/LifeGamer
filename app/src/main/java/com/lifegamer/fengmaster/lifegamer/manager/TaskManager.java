package com.lifegamer.fengmaster.lifegamer.manager;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 */

public class TaskManager implements ITaskManager {

    /**
     * 缓存
     */
    private List<Task> taskList = new LinkedList<>();

    @Override
    public boolean addTask(Task task) {
        long id = Game.insert(task);
        if (id != 0) {
            task.setId(id);
            taskList.add(task);
            EventBus.getDefault().post(new NewTaskEvent(task));
        }
        return id != 0;
    }

    @Override
    public boolean removeTask(String task) {
        //删除缓存
        taskList.removeIf(task1 -> task1.getName().equals(task1));
        //删除数据库
        return Game.delete(Stream.of(taskList).
                filter(value -> value.getName().equals(task)).findFirst().get());
    }

    @Override
    public boolean removeTask(int taskID) {
        taskList.removeIf(task -> task.getId() == taskID);
        return Game.delete(Stream.of(taskList).
                filter(value -> value.getId() == taskID).findFirst().get());
    }

    @Override
    public boolean updateTask(Task task) {
        return Game.update(task);
    }

    @Override
    public void finishTask(String taskName) {


        Task task = Stream.of(taskList).filter(value -> value.getName().equals(taskName)).findFirst().get();

        if (task.getRepeatAvailableTime()==0){
            //无重复次数
            return;
        }
        //可重复次数-1
        task.setRepeatAvailableTime(task.getRepeatAvailableTime()-1);

        //完成次数+1
        task.setCompleteTimes(task.getCompleteTimes() + 1);

        //修改过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(task.getExpirationTime());

        switch (task.getRepeatType()){
            case Task.REP_CONTINUOUS:
                //重复的
                task.setExpirationTime(null);
                task.setRepeatInterval(0);
                task.setRepeatAvailableTime(-1);
                break;
            case Task.REP_DAILY:
                //X天
                int day=task.getRepeatInterval();
                calendar.add(Calendar.DAY_OF_YEAR,day);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_HOURLY:
                //X小时
                int hour=task.getRepeatInterval();
                calendar.add(Calendar.HOUR,hour);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_MONTHLY:
                //X月
                int mouth=task.getRepeatInterval();
                calendar.add(Calendar.MONTH,mouth);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_WEEKLY:
                //X周
                int week=task.getRepeatInterval();
                calendar.add(Calendar.WEEK_OF_YEAR,week);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_YEARLY:
                //X年
                int year=task.getRepeatInterval();
                calendar.add(Calendar.YEAR,year);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_ONCE:
                //一次性


            default:
                break;

        }


    }

    @Override
    public void undoFinishTask(String task) {

    }

    @Override
    public void failTask(String task) {

    }

    @Override
    public void undoFailTask(String task) {

    }

    @Override
    public List<Task> getAllTask() {
        return null;
    }

    @Override
    public List<Task> getTodayTask() {
        return null;
    }

    @Override
    public List<Task> getTomorrowTask() {
        return null;
    }

    @Override
    public List<Task> getAllUnFinishTask() {
        return null;
    }

    @Override
    public List<Task> getTodayUnFinishTask() {
        return null;
    }


}

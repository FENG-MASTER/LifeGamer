package com.lifegamer.fengmaster.lifegamer.manager;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 */

public class TaskManager implements ITaskManager{
    @Override
    public boolean addTask(Task task) {
        return Game.insert(task)!=0;
    }

    @Override
    public boolean removeTask(String task) {
        return false;
    }

    @Override
    public boolean removeTask(int taskID) {
        return false;
    }

    @Override
    public boolean updateTask(Task task) {
        return false;
    }

    @Override
    public void finishTask(String task) {

    }

    @Override
    public void failTask(String task) {

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

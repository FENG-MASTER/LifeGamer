package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Task;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 任务管理器接口
 */

public interface ITaskManager {

    /**
     * 添加新的任务
     * @param task 任务
     * @return 是否添加成功
     */
    boolean addTask(Task task);

    /**
     * 移除任务
     * @param task 任务名称
     * @return 是否成功
     */
    boolean removeTask(String task);

    /**
     * 移除任务
     * @param taskID 任务ID
     * @return 是否成功
     */
    boolean removeTask(int taskID);

    /**
     * 更新任务内容
     * @param task 任务
     * @return 是否成功
     */
    boolean updateTask(Task task);

    /**
     *完成任务
     * @param task 任务名称
     */
    void finishTask(String task);

    /**
     * 撤销完成任务
     * @param task 任务名称
     */
    void undoFinishTask(String task);

    /**
     * 任务失败
     * @param task 任务名称
     */
    void failTask(String task);

    /**
     * 撤销 任务失败
     * @param task 任务名
     */
    void undoFailTask(String task);


    /**
     * 获得任务信息
     * @param id 任务ID
     * @return 任务
     */
    Task getTask(int id);


    /**
     * 获得任务信息
     * @param name 任务名
     * @return 任务
     */
    Task getTask(String name);

    /**
     * 获得所有任务列表
     * @return 任务列表
     */
    List<Task> getAllTask();

    /**
     * 获得今天的任务列表
     * @return 任务列表
     */
    List<Task> getTodayTask();

    /**
     * 获取明天的任务列表
     * @return 任务列表
     */
    List<Task> getTomorrowTask();

    /**
     * 获得所有没有完成的任务列表
     * @return 任务列表
     */
    List<Task> getAllUnFinishTask();


    /**
     * 获取今天没有完成的任务列表
     * @return 任务列表
     */
    List<Task> getTodayUnFinishTask();



}

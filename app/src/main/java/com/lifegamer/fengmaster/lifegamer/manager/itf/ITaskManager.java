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
    boolean finishTask(String task);

    /**
     *完成任务
     * @param taskID 任务ID
     */
    boolean finishTask(long taskID);

    /**
     * 撤销完成任务
     * @param task 任务名称
     */
    boolean undoFinishTask(String task);

    /**
     * 撤销完成任务
     * @param taskID 任务ID
     */
    boolean undoFinishTask(long taskID);

    /**
     * 任务失败
     * @param task 任务名称
     */
    boolean failTask(String task);


    /**
     * 任务失败
     * @param taskID 任务ID
     */
    boolean failTask(long taskID);

    /**
     * 撤销 任务失败
     * @param task 任务名
     */
    boolean undoFailTask(String task);

    /**
     * 撤销 任务失败
     * @param taskId 任务ID
     */
    boolean undoFailTask(Long taskId);

    /**
     * 获得任务信息
     * @param id 任务ID
     * @return 任务
     */
    Task getTask(long id);


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

    /**
     * 获取指定分类的任务
     * @param type
     * @return
     */
    List<Task> getTaskByType(String type);

    /**
     * 获取任务所有分类
     * @return
     */
    List<String> getAllTaskType();


}

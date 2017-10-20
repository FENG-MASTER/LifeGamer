package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;
import android.util.SparseArray;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by qianzise on 2017/10/4.
 */

public class TaskManager implements ITaskManager {

    private DBHelper helper=DBHelper.getInstance();

    /**
     * 历史过期时间map
     */
    private SparseArray<Date> lastExpirationTimeMap=new SparseArray<>();

    /**
     * 缓存
     */
    private List<Task> taskList = new LinkedList<>();

    public TaskManager() {
        loadTaskFromSQL();
    }

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
        Task rt=null;
        for (Task t : taskList) {
            if (t.getName().equals(task)){
                rt=t;
            }
        }

        taskList.remove(rt);
        //删除数据库
        return Game.delete(Stream.of(taskList).
                filter(value -> value.getName().equals(task)).findFirst().get());
    }

    @Override
    public boolean removeTask(int taskID) {
        Task rt=null;
        for (Task task : taskList) {
            if (task.getId()==taskID){
                rt=task;
            }
        }

        taskList.remove(rt);
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

        //记录历史过期时间
        lastExpirationTimeMap.append((int)task.getId(),task.getExpirationTime());

        switch (task.getRepeatType()){
            case Task.REP_CONTINUOUS:
                //重复的
                task.setExpirationTime(null);
                task.setRepeatInterval(0);
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
                //默认也是一次性
            default:
                //一次性
                task.setExpirationTime(null);
                task.setRepeatInterval(0);
                task.setRepeatAvailableTime(0);
                break;

        }


    }

    @Override
    public void undoFinishTask(String taskName) {

        Task task = Stream.of(taskList).filter(value -> value.getName().equals(taskName)).findFirst().get();
        //完成次数-1
        task.setCompleteTimes(task.getCompleteTimes()-1);
        //过期时间恢复
        task.setExpirationTime(lastExpirationTimeMap.get((int)task.getId()));
        //可重复次数+1
        task.setRepeatAvailableTime(task.getRepeatAvailableTime()+1);
        //删除历史过期时间
        lastExpirationTimeMap.remove((int)task.getId());

    }

    @Override
    public void failTask(String task) {

    }

    @Override
    public void undoFailTask(String task) {

    }

    @Override
    public Task getTask(int id) {
        return Stream.of(taskList).filter(value -> value.getId()==id).findFirst().get();
    }

    @Override
    public Task getTask(String name) {
        return null;
    }

    @Override
    public List<Task> getAllTask() {
        return taskList;
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
        return Stream.of(taskList).filter(value -> value.getRepeatAvailableTime()!=0).collect(Collectors.toList());
    }

    @Override
    public List<Task> getTodayUnFinishTask() {
        return null;
    }

    /**
     * 加载数据库所有任务
     */
    private void loadTaskFromSQL(){
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_TASK, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Task task=getTaskFromCursor(cursor);
            taskList.add(task);
        }


    }

    private Task getTaskFromCursor(Cursor cursor){
        Task task=new Task();
        task.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        task.setName(cursor.getString(cursor.getColumnIndex("name")));
        task.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        task.setAutoFail(cursor.getInt(cursor.getColumnIndex("isAutoFail"))==1);
        task.setIcon(cursor.getString(cursor.getColumnIndex("icon")));

        task.setDifficulty(cursor.getInt(cursor.getColumnIndex("difficulty")));
        task.setFear(cursor.getInt(cursor.getColumnIndex("fear")));
        task.setUrgency(cursor.getInt(cursor.getColumnIndex("urgency")));

        task.setSuccessSkills(FormatUtil.str2SkillMap(cursor.getString(cursor.getColumnIndex("successSkills"))));
        task.setSuccessItems(FormatUtil.str2ItemRewardList(cursor.getString(cursor.getColumnIndex("successItems"))));
        task.setSuccessAchievements(FormatUtil.str2achievementRewardList(cursor.getString(cursor.getColumnIndex("successAchievements"))));

        task.setFailureSkills(FormatUtil.str2SkillMap(cursor.getString(cursor.getColumnIndex("failureSkills"))));
        task.setFailureItems(FormatUtil.str2ItemRewardList(cursor.getString(cursor.getColumnIndex("failureItems"))));
        task.setFailureAchievements(FormatUtil.str2achievementRewardList(cursor.getString(cursor.getColumnIndex("failureAchievements"))));


        task.setEarnLP(cursor.getInt(cursor.getColumnIndex("earnLP")));
        task.setLostLP(cursor.getInt(cursor.getColumnIndex("lostLP")));

        task.setRepeatType(cursor.getInt(cursor.getColumnIndex("repeatType")));
        task.setRepeatInterval(cursor.getInt(cursor.getColumnIndex("repeatInterval")));
        task.setRepeatAvailableTime(cursor.getInt(cursor.getColumnIndex("repeatAvailableTime")));

        String expirationTime = cursor.getString(cursor.getColumnIndex("expirationTime"));
        if (expirationTime!=null&&expirationTime.equals("")){
            try {
                task.setExpirationTime(SimpleDateFormat.getInstance().parse(expirationTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        if (createTime!=null&&createTime.equals("")){
            try {
                task.setCreateTime(SimpleDateFormat.getInstance().parse(createTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        String updateTime = cursor.getString(cursor.getColumnIndex("updateTime"));
        if (updateTime!=null&&updateTime.equals("")){
            try {
                task.setUpdateTime(SimpleDateFormat.getInstance().parse(updateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        task.setCompleteTimes(cursor.getInt(cursor.getColumnIndex("completeTimes")));
        task.setFailureTimes(cursor.getInt(cursor.getColumnIndex("failureTimes")));
        task.setPreTasks(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("preTasks"))));
        task.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));

        return task;

    }


}

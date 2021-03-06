package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;
import android.util.SparseArray;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.GotAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.LoseAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.SkillIncreaseCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.task.UpdateTaskCommand;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.GameBaseInitFinish;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.FailTaskEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.FinishTaskEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.UpdateTaskEvent;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.util.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by qianzise on 2017/10/4.
 */

public class TaskManager implements ITaskManager {

    private DBHelper helper = DBHelper.getInstance();

    /**
     * 历史过期时间map
     */
    private SparseArray<Date> lastExpirationTimeMap = new SparseArray<>();

    /**
     * 缓存
     */
    private List<Task> taskList = new LinkedList<>();

    public TaskManager() {
        loadTaskFromSQL();
        EventBus.getDefault().register(this);
    }

    /**
     * 加载数据库所有任务
     */
    private void loadTaskFromSQL() {
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_TASK, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.getFromCursor(cursor);
            taskList.add(task);
        }
        cursor.close();


    }



    /**
     * 添加任务
     *
     * @param task 任务
     * @return 是否成功
     */
    @Override
    public boolean addTask(Task task) {
        return _addTask(task);
    }

    @LogPoint(type = Log.TYPE.TASK,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT)
    private boolean _addTask(Task task){
        if (getTask(task.getId())==null){
            taskList.add(task);
            long id = Game.insert(task);
            return id != 0;
        }else {
            return false;
        }

    }

    /**
     * 删除任务
     *
     * @param task 任务名称
     * @return 是否成功
     */
    @Override
    public boolean removeTask(String task) {
        Task rt = Stream.of(taskList).
                filter(value -> value.getName().equals(task)).findFirst().get();
        return removeTask(rt);
    }

    /**
     * 删除任务
     *
     * @param taskID 任务ID
     * @return 是否成功
     */
    @Override
    public boolean removeTask(int taskID) {
        Task rt = Stream.of(taskList).
                filter(value -> value.getId() == taskID).findFirst().get();
        return removeTask(rt);
    }

    /**
     * 更新任务
     *
     * @param task 任务
     * @return 是否成功
     */
    @Override
    public boolean updateTask(Task task) {
        return _updateTask(task);
    }

    @LogPoint(type = Log.TYPE.TASK,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT)
    private boolean _updateTask(Task task){
        if (taskList.contains(task)) {
            //缓存有
            return Game.update(task);
        } else {
            Task t = Stream.of(taskList).
                    filter(value -> value.getId() == task.getId()).findFirst().get();
            if (t != null) {
                //存在相同id
                t.copyFrom(task);
                return updateTask(t);
            } else {
                return false;
            }
        }
    }

    /**
     * 完成任务
     *
     * @param taskName 任务名称
     * @return 是否成功
     */
    @Override
    public boolean finishTask(String taskName) {
        Task task = Stream.of(taskList).filter(value -> value.getName().equals(taskName)).findFirst().get();
        return _prefinishTask(task);
    }

    /**
     * 完成任务
     *
     * @param taskID 任务ID
     * @return 是否成功
     */
    @Override
    public boolean finishTask(long taskID) {
        Task task = Stream.of(taskList).filter(value -> value.getId() == taskID).findFirst().get();
        return _prefinishTask(task);
    }

    /**
     * 撤销完成任务
     *
     * @param taskName 任务名
     * @return 是否成功
     */
    @Override
    public boolean undoFinishTask(String taskName) {
        Task task = Stream.of(taskList).filter(value -> value.getName().equals(taskName)).findFirst().get();
        return undoFinishTask(task);
    }

    /**
     * 撤销完成任务
     *
     * @param taskID 任务ID
     * @return 是否成功
     */
    @Override
    public boolean undoFinishTask(long taskID) {
        Task task = Stream.of(taskList).filter(value -> value.getId() == taskID).findFirst().get();
        return undoFinishTask(task);
    }

    /**
     * 任务失败
     *
     * @param task 任务名称
     * @return 是否成功
     */
    @Override
    public boolean failTask(String task) {
        return failTask(getTask(task));
    }

    /**
     * 任务失败
     *
     * @param taskID 任务ID
     * @return 是否成功
     */
    @Override
    public boolean failTask(long taskID) {
        return failTask(getTask(taskID));
    }

    private boolean failTask(Task task) {
        if (task != null) {
            return  _failTask(task);
        } else {
            //null失败
            return false;
        }
    }

    @LogPoint(type = Log.TYPE.TASK,action = Log.ACTION.FAIL,property = Log.PROPERTY.TASK)
    private boolean _failTask(Task task){

        //失败次数+1
        task.setFailureTimes(task.getFailureTimes() + 1);

        //重新调度任务时间
        scheduleTaskTime(task);

        EventBus.getDefault().post(new FailTaskEvent(task));
        Game.update(task);

        return true;

    }

    @Override
    public boolean undoFailTask(String task) {
        return false;
    }

    @Override
    public boolean undoFailTask(Long taskId) {
        return false;
    }

    /**
     * 获取任务对象
     *
     * @param id 任务ID
     * @return 任务对象
     */
    @Override
    public Task getTask(long id) {
        if (id==0){
            return null;
        }else {
            Optional<Task> taskOptional = Stream.of(taskList).filter(value -> value.getId() == id).findFirst();
            if (taskOptional.isPresent()){
                return taskOptional.get();
            }else {
                return null;
            }
        }
    }

    /**
     * 获取任务对象
     *
     * @param name 任务名
     * @return 任务对象
     */
    @Override
    public Task getTask(String name) {
        return Stream.of(taskList).filter(value -> value.getName().equals(name)).findFirst().get();
    }

    /**
     * 获得所有任务
     *
     * @return 任务列表
     */
    @Override
    public List<Task> getAllTask() {
        return taskList;
    }

    /**
     * 获取今天需要完成的所有任务
     *
     * @return 任务列表
     */
    @Override
    public List<Task> getTodayTask() {
        return Stream.of(taskList).filter(value -> value.getExpirationTime().after(
                DateUtil.getStartOfToday().getTime()) &&
                value.getExpirationTime().before(
                        DateUtil.getEndOfToday().getTime())).collect(Collectors.toList());
    }

    /**
     * 获取明天要完成的所有任务
     *
     * @return 任务列表
     */
    @Override
    public List<Task> getTomorrowTask() {
        return Stream.of(taskList).filter(value -> value.getExpirationTime().after(
                DateUtil.getEndOfToday().getTime()) &&
                value.getExpirationTime().before(
                        DateUtil.getEndOfTomorrow().getTime())).collect(Collectors.toList());
    }

    /**
     * 获取所有没有完成的任务列表
     *
     * @return 任务列表
     */
    @Override
    public List<Task> getAllUnFinishTask() {
        return Stream.of(taskList).filter(value -> value.getRepeatAvailableTime() != 0).collect(Collectors.toList());
    }

    /**
     * 获取今天需要完成而没有完成的任务列表
     *
     * @return 任务列表
     */
    @Override
    public List<Task> getTodayUnFinishTask() {
        return Stream.of(getTodayTask()).filter(value -> value.getRepeatAvailableTime() != 0).collect(Collectors.toList());
    }

    @Override
    public List<Task> getTaskByType(String type) {
        return Stream.of(taskList).filter(value -> value.getType().equals(type)).toList();
    }

    @Override
    public List<String> getAllTaskType() {
        return Stream.of(taskList).map(task -> task.getType()).distinct().toList();
    }

    /**
     * 完成任务
     *
     * @param task 任务
     * @return 是否成功
     */
    private boolean _prefinishTask(Task task) {
        if (task == null) {
            return false;
        }
        if (task.getRepeatAvailableTime() == 0) {
            //无重复次数
            return false;
        }

        return _finshTask(task);
    }

    /**
     * @param task
     * @return
     */
    @LogPoint(type = Log.TYPE.TASK,action = Log.ACTION.FINISH,property = Log.PROPERTY.TASK)
    private boolean _finshTask(Task task){

        if (task.getRepeatAvailableTime() != -1) {
            //可重复次数-1
            task.setRepeatAvailableTime(task.getRepeatAvailableTime() - 1);
        }

        //完成次数+1
        task.setCompleteTimes(task.getCompleteTimes() + 1);



        //调度任务时间
        boolean b = scheduleTaskTime(task);
        //更新数据库
        Game.update(task);

        //发送任务完成事件,触发相关触发器,从而处理奖励相关
        /***
         * @see com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTriggerCondition
         * @see com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTimesTriggerCondition
         */
        EventBus.getDefault().post(new FinishTaskEvent(task));
        return b;
    }


    /**
     * 处理物品奖励
     * @param task
     * @param finish
     */
    private void handleItemReward(Task task, boolean finish) {

        List<RandomItemReward> itemRewards = finish ? task.getSuccessItems() : task.getFailureItems();
        for (RandomItemReward itemReward : itemRewards) {
            if (itemReward.isHit()){
                //获得相应物品
                Game.getInstance().getRewardManager().gainRewardItem((int)itemReward.getRewardID(),finish?itemReward.getNum():-itemReward.getNum());

            }
        }

    }

    /**
     * 处理能力奖励
     * @param task 任务
     * @param finish
     */
    private void handleSkillReward(Task task,boolean finish) {

        Map<Long, Integer> map = finish ? task.getSuccessSkills() : task.getFailureSkills();
        for(Map.Entry<Long,Integer> entry:map.entrySet()){
            Game.getInstance().getCommandManager().executeCommand(new SkillIncreaseCommand(entry.getKey(),finish?entry.getValue():-entry.getValue()));
        }

    }

    /**
     * 处理任务经验
     * @param task 任务
     */
    private void handleXP(Task task,boolean finish){
        if (finish){
            //完成任务
            if (task.getXp()!=0){
                Hero hero = Game.getInstance().getHeroManager().getHero();
                hero.addXp(task.getXp());
                Game.getInstance().getHeroManager().updateHero(hero);
            }
        }else {
            //任务失败,对经验无操作

        }

    }

    /**
     * 处理lp点数
     * @param task
     * @param finish
     */
    private void handleLifePoint(Task task,boolean finish){
        if (finish){
            if (task.getEarnLP()!=0){
                Game.getInstance().getHeroManager().getHero().getLifePoint().addPoint(task.getEarnLP());
            }
        }else {
            if (task.getLostLP()!=0){
                Game.getInstance().getHeroManager().getHero().getLifePoint().addPoint(-task.getLostLP());
            }

        }

    }


    /**
     * 处理成就相关奖励
     * @param task
     * @param finish
     */
    private void handleAchievement(Task task,boolean finish){
        if (finish){
            //任务完成
            List<AchievementReward> achievements = task.getSuccessAchievements();
            if (achievements!=null&&!achievements.isEmpty()){
                //有奖励成就
                for (AchievementReward achievement : achievements) {
                    if (achievement.isHit()){
                        Achievement am = Game.getInstance().getAchievementManager().getAchievement(achievement.getAchievementID());
                        if (am!=null&&!am.isGot()){
                            Game.getInstance().getCommandManager().executeCommand(new GotAchievementCommand(am));
                        }
                    }
                }

            }

        }else {
            //任务失败
            List<AchievementReward> failureAchievements = task.getFailureAchievements();
            if (failureAchievements!=null&&!failureAchievements.isEmpty()){
                //有惩罚成就
                for (AchievementReward achievement : failureAchievements) {
                    if (achievement.isHit()){
                        Achievement am = Game.getInstance().getAchievementManager().getAchievement(achievement.getAchievementID());
                        if (am!=null&&am.isGot()){
                            Game.getInstance().getCommandManager().executeCommand(new LoseAchievementCommand(am));
                        }
                    }
                }

            }


        }


    }

    /**
     * 重新调度任务时间
     *
     * @param task 任务
     * @return 是否成功
     */
    private boolean scheduleTaskTime(Task task) {
        if (task == null) {
            //没有对应任务,失败
            return false;
        }


        //修改过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(task.getExpirationTime());

        //记录历史过期时间
        lastExpirationTimeMap.append((int) task.getId(), task.getExpirationTime());

        switch (task.getRepeatType()) {
            case Task.REP_CONTINUOUS:
                //重复的
//                task.setExpirationTime(null);
                task.setRepeatInterval(0);
                task.setExpirationTime(Task.noDate);
                break;
            case Task.REP_DAILY:
                //X天
                int day = task.getRepeatInterval();
                calendar.add(Calendar.DAY_OF_YEAR, day);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_HOURLY:
                //X小时
                int hour = task.getRepeatInterval();
                calendar.add(Calendar.HOUR, hour);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_MONTHLY:
                //X月
                int mouth = task.getRepeatInterval();
                calendar.add(Calendar.MONTH, mouth);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_WEEKLY:
                //X周
                int week = task.getRepeatInterval();
                calendar.add(Calendar.WEEK_OF_YEAR, week);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_YEARLY:
                //X年
                int year = task.getRepeatInterval();
                calendar.add(Calendar.YEAR, year);

                task.setExpirationTime(calendar.getTime());

                break;
            case Task.REP_ONCE:
                //默认也是一次性
            default:
                //一次性
                task.setRepeatInterval(0);
                task.setRepeatAvailableTime(0);
                break;

        }
        return true;

    }


    /**
     * 撤销调度任务时间
     *
     * @param task 任务
     * @return 是否成功
     */
    private boolean undoScheduleTaskTime(Task task) {
        if (task == null || lastExpirationTimeMap.get((int) task.getId()) == null) {
            //没有对应任务,失败
            return false;
        }

        //过期时间恢复
        task.setExpirationTime(lastExpirationTimeMap.get((int) task.getId()));
        lastExpirationTimeMap.remove((int) task.getId());
        return true;
    }

    /**
     * 删除任务
     *
     * @param task 任务
     * @return 是否成功
     */
    @LogPoint(type = Log.TYPE.TASK,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT)
    private boolean removeTask(Task task) {
        if (task != null) {
            List<TriggerInfo> triggerInfos = task.getTriggerInfos();
            for (TriggerInfo triggerInfo : triggerInfos) {
                //依次删除所有相关触发器
                Game.getInstance().getTriggerManager().removeTrigger(triggerInfo.getId());
            }
            Game.getInstance().getTriggerManager().removeTrigger(task.getAutoFailTriggerInfo().getId());

            if (Game.delete(task)) {
                taskList.remove(task);
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * 撤销完成任务
     *
     * @param task 任务
     * @return 是否成功
     */
    private boolean undoFinishTask(Task task) {
        if (task != null) {
            //完成次数-1
            task.setCompleteTimes(task.getCompleteTimes() - 1);
            if (task.getRepeatAvailableTime()!=-1) {
                //如果不是无限次任务,则可重复次数+1
                task.setRepeatAvailableTime(task.getRepeatAvailableTime() + 1);
            }
            //恢复之前过期时间
            undoScheduleTaskTime(task);
            return updateTask(task);
        } else {
            return false;
        }

    }

    /**
     * 当有能力被删除的时候,需要相关任务中残留的相关能力ID
     * @param delSkillEvent
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void whenSkillRemove(DelSkillEvent delSkillEvent){
        for (Task task : taskList) {
            Map<Long, Integer> skills = task.getSuccessSkills();
            if (skills.keySet().contains(delSkillEvent.getSkillID())){
                skills.remove(delSkillEvent.getSkillID());
                Game.getInstance().getCommandManager().executeCommand(new UpdateTaskCommand(task));
            }
        }

    }




}

package com.lifegamer.fengmaster.lifegamer;

import com.lifegamer.fengmaster.lifegamer.command.CommandManager;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.log.UndoManger;
import com.lifegamer.fengmaster.lifegamer.manager.AchievementManager;
import com.lifegamer.fengmaster.lifegamer.manager.HeroManger;
import com.lifegamer.fengmaster.lifegamer.manager.ItemManager;
import com.lifegamer.fengmaster.lifegamer.log.LogManager;
import com.lifegamer.fengmaster.lifegamer.manager.RewardItemManager;
import com.lifegamer.fengmaster.lifegamer.manager.SkillManager;
import com.lifegamer.fengmaster.lifegamer.manager.TaskManager;
import com.lifegamer.fengmaster.lifegamer.manager.TriggerManager;
import com.lifegamer.fengmaster.lifegamer.manager.base.IconicsAvatarManager;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IAchievementManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IHeroManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ILogManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.INoteManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IRewardManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITriggerManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IUndoManager;

/**
 * Created by qianzise on 2017/10/6.
 */

public class Game {

    private static Game instance = new Game();

    public static Game getInstance(){
        return instance;
    }


    /**
     * 物品管理器
     */
    private IItemManager itemManager=new ItemManager();


    /**
     * 奖励物品管理器
     */
    private IRewardManager rewardManager=new RewardItemManager();
    /**
     * 成就管理器
     */
    private IAchievementManager achievementManager=new AchievementManager();
    /**
     * 能力管理器
     */
    private ISkillManager skillManager=new SkillManager();
    /**
     * 英雄管理器
     */
    private IHeroManager heroManager=new HeroManger();
    /**
     * 任务管理器
     */
    private ITaskManager taskManager=new TaskManager();
    /**
     * 笔记管理器
     */
    private INoteManager noteManager;
    /**
     * 图标管理器
     */
    private IAvatarManager avatarManager=new IconicsAvatarManager();


    /**
     * 命令管理器
     */
    private CommandManager commandManager=new CommandManager();

    /**
     * 日志管理器
     */
    private ILogManager logManager=new LogManager();

    /**
     * 撤销管理器
     */
    private IUndoManager undoManager=new UndoManger();


    /**
     * 触发器管理器
     */
    private ITriggerManager triggerManager=new TriggerManager();

    private Game() {
        heroManager.getHero();

    }

    public IRewardManager getRewardManager() {
        return rewardManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public IAvatarManager getAvatarManager() {
        return avatarManager;
    }

    public IHeroManager getHeroManager() {
        return heroManager;
    }

    public IItemManager getItemManager() {
        return itemManager;
    }

    public IAchievementManager getAchievementManager() {
        return achievementManager;
    }

    public ISkillManager getSkillManager() {
        return skillManager;
    }


    public ITaskManager getTaskManager() {
        return taskManager;
    }

    public INoteManager getNoteManager() {
        return noteManager;
    }

    public ILogManager getLogManager() {
        return logManager;
    }

    public IUndoManager getUndoManager() {
        return undoManager;
    }

    public ITriggerManager getTriggerManager() {
        return triggerManager;
    }

    public static boolean update(Updateable updateable){
        return updateable.update(DBHelper.getInstance().getWritableDatabase())!=0;
    }

    public static long insert(Insertable insertable){
        return insertable.insert(DBHelper.getInstance().getWritableDatabase());
    }

    public static boolean delete(Deleteable deleteable){
        return deleteable.delete(DBHelper.getInstance().getWritableDatabase())!=0;
    }




}

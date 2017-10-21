package com.lifegamer.fengmaster.lifegamer;

import com.lifegamer.fengmaster.lifegamer.command.CommandManager;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.manager.AchievementManager;
import com.lifegamer.fengmaster.lifegamer.manager.HeroManger;
import com.lifegamer.fengmaster.lifegamer.manager.SkillManager;
import com.lifegamer.fengmaster.lifegamer.manager.TaskManager;
import com.lifegamer.fengmaster.lifegamer.manager.base.DefaultAvatarManager;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IAchievementManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IHeroManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.INoteManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IWealthManager;
import com.lifegamer.fengmaster.lifegamer.model.Hero;

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
    private IItemManager itemManager;
    /**
     * 成就管理器
     */
    private IAchievementManager achievementManager=new AchievementManager();
    /**
     * 技能管理器
     */
    private ISkillManager skillManager=new SkillManager();
    /**
     * 财富管理器
     */
    private IWealthManager wealthManager;
    /**
     * 任务管理器
     */
    private ITaskManager taskManager=new TaskManager();
    /**
     * 笔记管理器
     */
    private INoteManager noteManager;

    /**
     * 英雄管理器
     */
    private IHeroManager heroManager=new HeroManger();

    /**
     * 图标管理器
     */
    private IAvatarManager avatarManager=new DefaultAvatarManager();


    /**
     * 命令管理器
     */
    private CommandManager commandManager=new CommandManager();

    private Game() {

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

    public IWealthManager getWealthManager() {
        return wealthManager;
    }

    public ITaskManager getTaskManager() {
        return taskManager;
    }

    public INoteManager getNoteManager() {
        return noteManager;
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

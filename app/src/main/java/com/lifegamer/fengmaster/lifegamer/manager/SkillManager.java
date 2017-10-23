package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;
import android.databinding.Observable;
import android.os.Build;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 本地技能管理模块
 * <p>
 * 模块本身存储一份所有技能的备份{@link SkillManager#skillList}
 */

public class SkillManager implements ISkillManager {


    private List<Skill> skillList = new ArrayList<>();

    private DBHelper helper = DBHelper.getInstance();

    public SkillManager() {
        getAllSkillFromSQL();
    }

    /**
     * 从数据库中读取所有数据
     */
    private void getAllSkillFromSQL() {
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_SKILL, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Skill skill = getSkillFromCursor(cursor);
            skillList.add(skill);
        }
    }

    /**
     * 从游标中生成对应的skill对象
     *
     * @param cursor 游标
     * @return skill
     */
    private Skill getSkillFromCursor(Cursor cursor) {
        Skill skill = new Skill();
        skill.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        skill.setXP(cursor.getInt(cursor.getColumnIndex("xp")));
        skill.setName(cursor.getString(cursor.getColumnIndex("name")));
        skill.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
        skill.setUpGradeXP(cursor.getInt(cursor.getColumnIndex("upGradeXP")));
        skill.setType(cursor.getString(cursor.getColumnIndex("type")));
        skill.setIntro(cursor.getString(cursor.getColumnIndex("intro")));
        skill.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
        skill.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));
        try {
            skill.setCreateTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("createTime"))));
            skill.setUpdateTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("updateTime"))));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return skill;
    }

    /**
     * 增加技能经验值
     * @param skillName 技能名字
     * @param xp        增加点数
     * @return 是否成功
     */
    @Override
    public boolean addSkillXp(String skillName, int xp) {

        Skill skill=getSkill(skillName);
        if (skill!=null){
            skill.addXP(xp);
            return updateSkill(skill);
        }else {
            return false;
        }
    }

    /**
     * 新建技能
     * @param skill 技能
     * @return 是否成功
     */
    @Override
    public boolean addSkill(Skill skill) {
        long id = skill.insert(helper.getWritableDatabase());
        if (id != 0) {
            skill.setId(id);
            skillList.add(skill);
            return true;
        }
        return false;
    }

    /**
     * 更新技能
     * @param skill 技能
     * @return 是否成功
     */
    @Override
    public boolean updateSkill(Skill skill) {

        if (skillList.contains(skill)) {
            //如果存在缓存里
            return Game.update(skill);
        } else {
            Skill s = getSkill(skill.getId());
            if (s != null) {
                //存在相同id
                //则更新缓存里的对象
                s.copyFrom(skill);
                return Game.update(s);
            }else {
                return false;
            }

        }
    }

    /**
     * @param name 技能名称
     * @return 技能
     */
    @Override
    public Skill getSkill(String name) {
        return Stream.of(skillList).filter(value -> value.getName().equals(name)).findFirst().get();
    }

    /**
     * @param id 技能id
     * @return 技能
     */
    @Override
    public Skill getSkill(long id) {
        return Stream.of(skillList).filter(value -> value.getId() == id).findFirst().get();
    }

    /**
     * 删除技能
     * @param name 技能名称
     * @return 是否成功
     */
    @Override
    public boolean removeSkill(String name) {
        Skill remove=getSkill(name);
        if (remove!=null){
            skillList.remove(remove);
            return Game.delete(remove);
        }else {
            return false;
        }

    }

    /**
     * 获得所有技能名称
     * @return 技能名称列表
     */
    @Override
    public List<String> getAllSkillName() {
        return Stream.of(skillList).map(Skill::getName).collect(Collectors.toList());
    }

    /**
     * 获得所有技能列表
     * @return 技能列表
     */
    @Override
    public List<Skill> getAllSkill() {
        return skillList;
    }

    /**
     * 获取 特定分类的技能名称列表
     * @param type 技能分类
     * @return 名称列表
     */
    @Override
    public List<String> getAllSkillName(String type) {
        return Stream.of(skillList).filter(value -> value.getType().equals(type)).map(Skill::getName).collect(Collectors.toList());
    }

    /**
     * 获取 特定分类的技能列表
     * @param type 技能分类
     * @return 列表
     */
    @Override
    public List<Skill> getAllSkill(String type) {
        return Stream.of(skillList).filter(value -> value.getType().equals(type)).collect(Collectors.toList());

    }

    /**
     * 获取技能所有种类
     * @return 种类列表
     */
    @Override
    public List<String> getAllSkillType() {
        return Stream.of(skillList).map(Skill::getType).distinct().collect(Collectors.toList());
    }
}

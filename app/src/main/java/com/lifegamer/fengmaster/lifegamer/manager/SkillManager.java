package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;
import android.databinding.Observable;
import android.os.Build;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;
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
 * 本地能力管理模块
 * <p>
 * 模块本身存储一份所有能力的备份{@link SkillManager#skillList}
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
            Skill skill = new Skill();
            skill.getFromCursor(cursor);
            skillList.add(skill);
        }
        cursor.close();
    }



    /**
     * 增加能力经验值
     * @param skillName 能力名字
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
     * 新建能力
     * @param skill 能力
     * @return 是否成功
     */
    @Override
    public boolean addSkill(Skill skill) {
        return _addSkill(skill);
    }

    @LogPoint(type = Log.TYPE.SKILL,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT)
    private boolean _addSkill(Skill skill){

        long id = skill.insert(helper.getWritableDatabase());
        if (id != 0) {
            skill.setId(id);
            skillList.add(skill);
            EventBus.getDefault().post(new NewSkillEvent(skill));
            return true;
        }
        return false;
    }

    /**
     * 更新能力
     * @param skill 能力
     * @return 是否成功
     */
    @Override
    public boolean updateSkill(Skill skill) {
        return _updateSkill(skill);
    }

    @LogPoint(type = Log.TYPE.SKILL,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT)
    private boolean _updateSkill(Skill skill){

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
     * @param name 能力名称
     * @return 能力
     */
    @Override
    public Skill getSkill(String name) {
        return Stream.of(skillList).filter(value -> value.getName().equals(name)).findFirst().get();
    }

    /**
     * @param id 能力id
     * @return 能力
     */
    @Override
    public Skill getSkill(long id) {
        Optional<Skill> first = Stream.of(skillList).filter(value -> value.getId() == id).findFirst();
        if (first.isPresent()){
            return first.get();
        }else {
            return null;
        }
    }

    /**
     * 删除能力
     * @param name 能力名称
     * @return 是否成功
     */
    @Override
    public boolean removeSkill(String name) {
        Skill remove=getSkill(name);
        if (remove!=null){
            return removeSkill(remove);
        }else {
            return false;
        }

    }

    @LogPoint(type = Log.TYPE.SKILL,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT)
    private boolean removeSkill(Skill skill){
        skillList.remove(skill);
        return Game.delete(skill);
    }

    /**
     * 获得所有能力名称
     * @return 能力名称列表
     */
    @Override
    public List<String> getAllSkillName() {
        return Stream.of(skillList).map(Skill::getName).collect(Collectors.toList());
    }

    /**
     * 获得所有能力列表
     * @return 能力列表
     */
    @Override
    public List<Skill> getAllSkill() {
        return skillList;
    }

    /**
     * 获取 特定分类的能力名称列表
     * @param type 能力分类
     * @return 名称列表
     */
    @Override
    public List<String> getAllSkillName(String type) {
        return Stream.of(skillList).filter(value -> value.getType().equals(type)).map(Skill::getName).collect(Collectors.toList());
    }

    /**
     * 获取 特定分类的能力列表
     * @param type 能力分类
     * @return 列表
     */
    @Override
    public List<Skill> getAllSkill(String type) {
        return Stream.of(skillList).filter(value -> value.getType().equals(type)).collect(Collectors.toList());

    }

    /**
     * 获取能力所有种类
     * @return 种类列表
     */
    @Override
    public List<String> getAllSkillType() {
        return Stream.of(skillList).map(Skill::getType).distinct().collect(Collectors.toList());
    }
}

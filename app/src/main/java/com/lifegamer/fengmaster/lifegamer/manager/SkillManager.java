package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qianzise on 2017/10/4.
 */

public class SkillManager implements ISkillManager{

    private DBHelper helper;

    Map<String,Skill> skillMap=new HashMap<>();

    public SkillManager() {
        helper=DBHelper.getInstance();
    }

    @Override
    public void addSkillPoint(String skillName, int point) {

    }

    @Override
    public boolean addSkill(Skill skill) {
        long id = skill.insert(helper.getWritableDatabase());
        if (id!=0){
            skill.setId(id);
            skillMap.put(skill.getName(),skill);
            EventBus.getDefault().post(new NewSkillEvent(skill));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSkill(Skill skill) {
        return false;
    }

    @Override
    public Skill getSkill(String name) {
        if (skillMap.containsKey(name)){
            //缓存有
            return skillMap.get(name);
        }else {
            //否则去数据库看看
            Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_SKILL, null, "name = ?", new String[]{name}, null, null, null);
            if (cursor!=null&&cursor.moveToNext()&&cursor.getCount()!=0){
                //有数据
                Skill skill = getSkillFromCursor(cursor,true);
                cursor.close();
                return skill;
            }
        }

        return null;
    }

    @Override
    public void removeSkill(String name) {

    }

    @Override
    public List<String> getAllSkillName() {
        return null;
    }

    @Override
    public List<Skill> getAllSkill() {
        getAllSkillFromSQL();
        return new ArrayList<>(skillMap.values());
    }

    @Override
    public List<String> getAllSkillName(String type) {
        return null;
    }

    @Override
    public List<Skill> getAllSkill(String type) {
        return null;
    }

    /**
     * 从游标中生成对应的skill对象
     * @param cursor 游标
     * @return skill
     */
    private Skill getSkillFromCursor(Cursor cursor,boolean update){
        Skill skill=new Skill();
        skill.setXP(cursor.getInt(cursor.getColumnIndex("xp")));
        skill.setName(cursor.getString(cursor.getColumnIndex("name")));
        skill.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
        skill.setUpGradeXP(cursor.getInt(cursor.getColumnIndex("upGradeXP")));
        skill.setName(cursor.getString(cursor.getColumnIndex("name")));
        skill.setName(cursor.getString(cursor.getColumnIndex("name")));
        skill.setName(cursor.getString(cursor.getColumnIndex("name")));
        skill.setName(cursor.getString(cursor.getColumnIndex("name")));
        if (update&&updateSkillFromSQL(skill)){
            //如果是更新的内容
            skill=getSkill(skill.getName());
        }

        return skill;
    }


    /**
     * 更新从sql来的skill数据
     * @param skill sql来的数据
     * @return true表示更新,false表示插入
     */
    private boolean updateSkillFromSQL(Skill skill){
        if (skillMap.containsKey(skill.getName())){
            //存在相应对象,需要更新,更新要求,不允许改变引用
            skillMap.get(skill.getName()).copyFrom(skill);
            return true;
        }else {
            //不存在,直接插入
            skillMap.put(skill.getName(),skill);
            return false;
        }
    }

    private void getAllSkillFromSQL(){
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_SKILL, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            getSkillFromCursor(cursor,true);
        }
    }
}

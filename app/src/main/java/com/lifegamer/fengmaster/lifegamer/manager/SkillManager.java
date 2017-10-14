package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;
import android.databinding.Observable;
import android.os.Build;

import com.annimon.stream.Stream;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 本地技能管理模块
 *
 * 模块本身存储一份所有技能的备份{@link SkillManager#skillMap}
 */

public class SkillManager implements ISkillManager {

    Map<String, Skill> skillMap = new HashMap<>();
    private DBHelper helper;

    public SkillManager() {
        helper = DBHelper.getInstance();
        getAllSkillFromSQL();
    }

    @Override
    public void addSkillXp(String skillName, int xp) {
        Skill skill = skillMap.get(skillName);
        if (skill!=null){
            skill.addXP(xp);
            updateSkill(skill);
        }
    }

    @Override
    public boolean addSkill(Skill skill) {
        long id = skill.insert(helper.getWritableDatabase());
        if (id != 0) {
            skill.setId(id);
            skillMap.put(skill.getName(), skill);
            EventBus.getDefault().post(new NewSkillEvent(skill));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSkill(Skill skill) {
        return Game.update(skill);
    }

    @Override
    public Skill getSkill(String name) {
        if (skillMap.containsKey(name)) {
            //缓存有
            return skillMap.get(name);
        } else {
            //否则去数据库看看
            Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_SKILL, null, "name = ?", new String[]{name}, null, null, null);
            if (cursor != null && cursor.moveToNext() && cursor.getCount() != 0) {
                //有数据
                Skill skill = getSkillFromCursor(cursor, true);
                cursor.close();
                return skill;
            }
        }

        return null;
    }

    @Override
    public void removeSkill(String name) {
        Skill remove = skillMap.remove(name);
        Game.delete(remove);
        EventBus.getDefault().post(new DelSkillEvent(name));
    }

    @Override
    public List<String> getAllSkillName() {
        return new ArrayList<>(skillMap.keySet());
    }

    @Override
    public List<Skill> getAllSkill() {
        return new ArrayList<>(skillMap.values());
    }

    @Override
    public List<String> getAllSkillName(String type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return skillMap.values().stream().
                    filter(skill -> type.equals(skill.getType())).
                    map(Skill::getName).
                    collect(Collectors.toList());
        }else {
            List<String> list = new LinkedList<>();
            Stream.of(skillMap.values()).
                    filter(skill -> type.equals(skill.getType())).
                    map(Skill::getName).
                    forEach(list::add);
            return list;
        }

    }

    @Override
    public List<Skill> getAllSkill(String type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return skillMap.values().stream().
                    filter(skill -> type.equals(skill.getType())).
                    collect(Collectors.toList());
        } else {
            List<Skill> list = new LinkedList<>();
            Stream.of(skillMap.values()).
                    filter(skill -> type.equals(skill.getType())).
                    forEach(list::add);
            return list;
        }
    }

    /**
     * 从游标中生成对应的skill对象
     *
     * @param cursor 游标
     * @return skill
     */
    private Skill getSkillFromCursor(Cursor cursor, boolean update) {
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


        if (update && updateSkillFromSQL(skill)) {
            //如果是更新的内容
            skill = getSkill(skill.getName());
        }

        return skill;
    }


    /**
     * 更新从sql来的skill数据
     *
     * @param skill sql来的数据
     * @return true表示更新, false表示插入
     */
    private boolean updateSkillFromSQL(Skill skill) {
        if (skillMap.containsKey(skill.getName())) {
            //存在相应对象,需要更新,更新要求,不允许改变引用
            skillMap.get(skill.getName()).copyFrom(skill);
            return true;
        } else {
            //不存在,直接插入
            skillMap.put(skill.getName(), skill);
            return false;
        }
    }

    private void getAllSkillFromSQL() {
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_SKILL, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Skill skill = getSkillFromCursor(cursor, true);
            skill.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if (propertyId== BR.name){

                    }
                }
            });
        }
    }
}

package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITriggerManager;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.model.Trigger;

import java.util.ArrayList;
import java.util.List;

/**
 * 触发器管理器
 * Created by FengMaster on 19/01/08.
 */
public class TriggerManager implements ITriggerManager {

    private List<Trigger> triggerList=new ArrayList<>();

    private DBHelper helper = DBHelper.getInstance();


    public TriggerManager() {
        getAllTriggerFromSQL();
    }

    /**
     * 从数据库中读取所有数据
     */
    private void getAllTriggerFromSQL() {
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_TRIGGER, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Trigger trigger=new Trigger();
            trigger.getFromCursor(cursor);
            triggerList.add(trigger);
        }
        cursor.close();
    }

    @Override
    public Trigger getTrigger(long id) {
        Optional<Trigger> first = Stream.of(triggerList).filter(value -> value.getId() == id).findFirst();
        return first.get();
    }

    @Override
    public boolean addTrigger(Trigger trigger) {
        if (getTrigger(trigger.getId())==null){
            //新的触发器
            long l = Game.insert(trigger);
            if (l!=0){
                trigger.setId(l);
                triggerList.add(trigger);
                return true;
            }else {
                return false;
            }

        }else {
            return updateTrigger(trigger);
        }
    }

    @Override
    public boolean updateTrigger(Trigger trigger) {

        if (triggerList.contains(trigger)) {
            //如果存在缓存里
            return Game.update(trigger);
        } else {
            Trigger t = getTrigger(trigger.getId());
            if (t != null) {
                //存在相同id
                //则更新缓存里的对象
                t.copyFrom(trigger);
                return Game.update(t);
            }else {
                return false;
            }

        }
    }


    @Override
    public boolean removeTrigger(Trigger trigger) {
        if (trigger==null){
            return false;
        }

        triggerList.remove(trigger);
        return Game.delete(trigger);

    }

    @Override
    public boolean removeTrigger(long triggerId) {
        Trigger trigger = getTrigger(triggerId);
        return removeTrigger(trigger);
    }
}

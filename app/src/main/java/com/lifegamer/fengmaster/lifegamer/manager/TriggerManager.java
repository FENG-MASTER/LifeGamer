package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITriggerManager;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;

import java.util.ArrayList;
import java.util.List;

/**
 * 触发器管理器
 * Created by FengMaster on 19/01/08.
 */
public class TriggerManager implements ITriggerManager {

    private List<Trigger> triggerList =new ArrayList<>();

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
            TriggerInfo triggerInfo =new TriggerInfo();
            triggerInfo.getFromCursor(cursor);
            Trigger trigger=new Trigger(triggerInfo);
            triggerList.add(trigger);
        }
        cursor.close();
    }

    @Override
    public Trigger getTrigger(long id) {
        Optional<Trigger> optional = Stream.of(triggerList).filter(value -> value.getTriggerInfo().getId() == id).findSingle();
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public Trigger addTrigger(TriggerInfo triggerInfo) {
        if (triggerInfo==null){
            return null;
        }

        //已经存在的ID,不应该新建
        if (triggerInfo.getId()!=0){
            return getTrigger(triggerInfo.getId());
        }

        long l = Game.insert(triggerInfo);
        if (l!=0){
            triggerInfo.setId(l);
        }

        Trigger trigger=new Trigger(triggerInfo);
        triggerList.add(trigger);
        return trigger;

    }

    @Override
    public boolean updateTrigger(Trigger trigger) {
        boolean b = triggerList.contains(trigger);
        if (b){
            //已经存在的触发器,更新
            return Game.update(trigger.getTriggerInfo());
        }else {
            return false;
        }
    }

    @Override
    public boolean removeTrigger(Trigger trigger) {
        if (trigger==null){
            return false;
        }
        boolean b = triggerList.contains(trigger);
        if (b){
            boolean delete = Game.delete(trigger.getTriggerInfo());
            if (delete){
                triggerList.remove(trigger);
                return true;
            }else {
                return false;
            }

        }else {
            return false;
        }

    }

    @Override
    public boolean removeTrigger(long triggerId) {
        Trigger trigger = getTrigger(triggerId);
        return removeTrigger(trigger);
    }

    @Override
    public List<Trigger> getTriggers(String type, String objId) {
        return null;
    }
}

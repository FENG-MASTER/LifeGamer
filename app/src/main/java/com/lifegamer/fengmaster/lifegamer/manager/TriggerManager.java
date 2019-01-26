package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
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
            Trigger trigger=newTrigger(triggerInfo);
        }
        cursor.close();
    }

    @Override
    public Trigger newTrigger(TriggerInfo triggerInfo) {
        Trigger trigger = new Trigger(triggerInfo);
        triggerList.add(trigger);
        return trigger;
    }

    @Override
    public Trigger getTrigger(long id) {
        if (id==0){
            return null;
        }
        Optional<Trigger> optional = Stream.of(triggerList).filter(value -> value.getTriggerInfo().getId() == id).findSingle();
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public TriggerInfo getTriggerInfo(long id) {
        Trigger trigger = getTrigger(id);
        if (trigger!=null){
            return trigger.getTriggerInfo();
        }else {
            return null;
        }
    }

    @Override
    public Trigger addTrigger(TriggerInfo triggerInfo) {
        if (triggerInfo==null){
            return null;
        }

        boolean b = addOrUpdate(triggerInfo);
        if (b){
            return Stream.of(triggerList).filter(value -> value.getTriggerInfo().getId()==triggerInfo.getId()).findFirst().get();
        }else {
            return null;
        }

    }

    @Override
    public boolean updateTrigger(Trigger trigger) {
        boolean b = triggerList.contains(trigger);
        if (b){
            //已经存在的触发器,更新
            return addOrUpdate(trigger.getTriggerInfo());
        }else {
            return false;
        }
    }

    @Override
    public boolean updateTriggerInfo(TriggerInfo triggerInfo) {
        Optional<TriggerInfo> first = Stream.of(triggerList).map(trigger -> trigger.getTriggerInfo()).filter(value -> value.equals(triggerInfo)).findFirst();
        if (first.isPresent()){
            //存在,则更新
            return Game.update(triggerInfo);
        }else {
            return false;
        }

    }

    private boolean addOrUpdate(TriggerInfo triggerInfo){
        //已经存在相同ID的触发器,认为是同一个触发器
        Trigger trigger = getTrigger(triggerInfo.getId());
        if (trigger!=null){
            return Game.update(trigger.getTriggerInfo());
        }else {
            Trigger newTrigger = newTrigger(triggerInfo);

            long l = Game.insert(triggerInfo);
            if (l!=0){
                triggerInfo.setId(l);
                return true;
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
    public List<Trigger> getTriggers(String type, long objId) {
        return Stream.of(triggerList).filter(value -> value.getTriggerInfo().getType().equals(type)).filter(value -> value.getTriggerInfo().getMainObjId()==objId).toList();
    }

    @Override
    public List<TriggerInfo> getTriggerInfos(String type, long objId) {
        return Stream.of(triggerList).filter(value -> value.getTriggerInfo().getType().equals(type)).filter(value -> value.getTriggerInfo().getMainObjId()==objId).map(trigger -> trigger.getTriggerInfo()).toList();
    }
}

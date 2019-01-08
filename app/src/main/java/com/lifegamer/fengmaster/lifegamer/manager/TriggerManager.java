package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITriggerManager;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 触发器管理器
 * Created by FengMaster on 19/01/08.
 */
public class TriggerManager implements ITriggerManager {

    private List<TriggerInfo> triggerInfoList =new ArrayList<>();

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
            triggerInfoList.add(triggerInfo);
        }
        cursor.close();
    }

    @Override
    public TriggerInfo getTrigger(long id) {
        Optional<TriggerInfo> first = Stream.of(triggerInfoList).filter(value -> value.getId() == id).findSingle();
        if (first.isPresent()){
            return first.get();
        }else {
            return null;
        }
    }

    @Override
    public boolean addTrigger(TriggerInfo triggerInfo) {
        if (getTrigger(triggerInfo.getId())==null){
            //新的触发器
            long l = Game.insert(triggerInfo);
            if (l!=0){
                triggerInfo.setId(l);
                triggerInfoList.add(triggerInfo);
                return true;
            }else {
                return false;
            }

        }else {
            return updateTrigger(triggerInfo);
        }
    }

    @Override
    public boolean updateTrigger(TriggerInfo triggerInfo) {

        if (triggerInfoList.contains(triggerInfo)) {
            //如果存在缓存里
            return Game.update(triggerInfo);
        } else {
            TriggerInfo t = getTrigger(triggerInfo.getId());
            if (t != null) {
                //存在相同id
                //则更新缓存里的对象
                t.copyFrom(triggerInfo);
                return Game.update(t);
            }else {
                return false;
            }

        }
    }


    @Override
    public boolean removeTrigger(TriggerInfo triggerInfo) {
        if (triggerInfo ==null){
            return false;
        }

        triggerInfoList.remove(triggerInfo);
        return Game.delete(triggerInfo);

    }

    @Override
    public boolean removeTrigger(long triggerId) {
        TriggerInfo triggerInfo = getTrigger(triggerId);
        return removeTrigger(triggerInfo);
    }
}

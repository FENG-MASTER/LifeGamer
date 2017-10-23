package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IRewardManager;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/21.
 * <p>
 * 本地奖励 管理器
 */

public class RewardItemManager implements IRewardManager {

    private DBHelper helper=DBHelper.getInstance();

    private List<RewardItem> rewardItems = new ArrayList<>();

    public RewardItemManager() {
        //载入数据
        loadRewardItemFromSQL();
    }

    @Override
    public RewardItem getRewardItem(long id) {
        return null;
    }

    @Override
    public RewardItem getRewardItem(String name) {
        return null;
    }

    @Override
    public boolean addRewardItem(RewardItem rewardItem) {
        long l = Game.insert(rewardItem);
        if (l!=0){
            rewardItem.setId(l);
            rewardItems.add(rewardItem);
        }
        return l!=0;
    }

    @Override
    public boolean updateRewardItem(RewardItem rewardItem) {
        return Game.update(rewardItem);
    }

    @Override
    public boolean removeRewardItem(long id) {
        return Game.delete(Stream.of(rewardItems).filter(value -> value.getId()==id).findFirst().get());
    }

    @Override
    public boolean gainRewardItem(String rewardItem) {
        return false;
    }

    @Override
    public boolean gainRewardItem(int rewardItemID) {
        return false;
    }

    @Override
    public List<RewardItem> getAllRewardItem() {
        return rewardItems;
    }

    @Override
    public List<RewardItem> getAllRewardItem(String type) {
        return null;
    }

    @Override
    public List<RewardItem> getAllAvailableRewardItem() {
        return rewardItems;
    }

    @Override
    public List<RewardItem> getAllAvailableRewardItem(String type) {
        return null;
    }

    @Override
    public List<String> getAllAvailableRewardItemName() {
        return null;
    }

    @Override
    public List<String> getAllAvailableRewardItemName(String type) {
        return null;
    }

    /**
     * 从数据库更新数据
     */
    private void loadRewardItemFromSQL(){

        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_REWARD, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            RewardItem item = getRewardItemFromCursor(cursor);
            rewardItems.add(item);
        }
        cursor.close();

    }


    /**
     * 从游标中提取 rewarditem
     * @param cursor 游标
     * @return rewarditem
     */
    private RewardItem getRewardItemFromCursor(Cursor cursor) {
        RewardItem rewardItem = new RewardItem();
        rewardItem.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        rewardItem.setName(cursor.getString(cursor.getColumnIndex("name")));
        rewardItem.setType(cursor.getString(cursor.getColumnIndex("type")));
        rewardItem.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
        rewardItem.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        rewardItem.setCostLP(cursor.getInt(cursor.getColumnIndex("costLP")));
        rewardItem.setCostLPIncrement(cursor.getInt(cursor.getColumnIndex("costLPIncrement")));
        rewardItem.setGainTimes(cursor.getInt(cursor.getColumnIndex("gainTimes")));
        rewardItem.setQuantityAvailable(cursor.getInt(cursor.getColumnIndex("quantityAvailable")));
        rewardItem.setAddToItem(cursor.getInt(cursor.getColumnIndex("addToItem")) == 1);
        rewardItem.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));

        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        if (createTime != null && !createTime.equals("")) {
            try {
                rewardItem.setCreateTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("createTime"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String updateTime = cursor.getString(cursor.getColumnIndex("updateTime"));
        if (updateTime != null && updateTime.equals("")) {
            try {
                rewardItem.setUpdateTime(SimpleDateFormat.getInstance().parse(updateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return rewardItem;
    }
}

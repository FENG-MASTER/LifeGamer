package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.CommandManager;
import com.lifegamer.fengmaster.lifegamer.command.command.item.AddItemCommand;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IRewardManager;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/21.
 * <p>
 * 本地奖励(商店) 管理器
 */

public class RewardItemManager implements IRewardManager {

    private DBHelper helper = DBHelper.getInstance();

    private List<RewardItem> rewardItems = new ArrayList<>();

    public RewardItemManager() {
        //载入数据
        loadRewardItemFromSQL();
    }

    /**
     * 根据id获取奖励
     *
     * @param id 奖励id
     * @return 奖励
     */
    @Override
    public RewardItem getRewardItem(long id) {
        return Stream.of(rewardItems).filter(value -> value.getId() == id).findFirst().get();
    }

    /**
     * 根据奖励名称获取奖励
     *
     * @param name 奖励名
     * @return 奖励
     */
    @Override
    public RewardItem getRewardItem(String name) {
        return Stream.of(rewardItems).filter(value -> value.getName().equals(name)).findFirst().get();
    }

    /**
     * 添加奖励
     *
     * @param rewardItem 奖励物品
     * @return 是否成功
     */
    @Override
    public boolean addRewardItem(RewardItem rewardItem) {
        long l = Game.insert(rewardItem);
        if (l != 0) {
            rewardItem.setId(l);
            rewardItems.add(rewardItem);
        }
        return l != 0;
    }

    /**
     * 更新奖励物品
     *
     * @param rewardItem 奖励物品
     * @return 是否成功
     */
    @Override
    public boolean updateRewardItem(RewardItem rewardItem) {
        if (rewardItems.contains(rewardItem)) {
            //缓存中有
            return Game.update(rewardItem);
        } else {
            RewardItem ri = getRewardItem(rewardItem.getId());
            if (ri != null) {
                //有相同id,更新
                ri.copyFrom(rewardItem);
                return Game.update(ri);
            } else {
                return false;
            }

        }
    }

    /**
     * 删除奖励物品
     *
     * @param id 奖励物品id
     * @return 是否成功
     */
    @Override
    public boolean removeRewardItem(long id) {
        RewardItem rewardItem = getRewardItem(id);
        if (rewardItem != null) {
            if (Game.delete(rewardItem)) {
                rewardItems.remove(rewardItem);
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

    /**
     * 获得奖励
     *
     * @param rewardItem 奖励
     * @return 是否成功
     */
    @Override
    public boolean gainRewardItem(String rewardItem) {
        RewardItem item = getRewardItem(rewardItem);
        return gainRewardItem(item);
    }

    @Override
    public boolean gainRewardItem(String rewardItem, int num, int probability) {
        return false;
    }

    /**
     * 获得奖励
     *
     * @param rewardItemID 奖励ID
     * @return 是否成功
     */
    @Override
    public boolean gainRewardItem(int rewardItemID) {
        RewardItem rewardItem = getRewardItem(rewardItemID);
        return gainRewardItem(rewardItem);
    }

    @Override
    public boolean gainRewardItem(int rewardItemID, int num, int probability) {
        return false;
    }

    @Override
    public boolean gainRewardItem(RandomItemReward randomItemReward) {
        RewardItem rewardItem=getRewardItem(randomItemReward.getRewardID());
        if (rewardItem != null) {
            if (rewardItem.getQuantityAvailable() == -1) {
                //无限次数

                //更新价格
                rewardItem.setCostLP(rewardItem.getCostLP()+rewardItem.getCostLPIncrement());
                Item item = new Item();
                item.setName(rewardItem.getName());
                item.setQuantity(randomItemReward.getNum());
                Game.getInstance().getCommandManager().executeCommand(new AddItemCommand(item));

                return true;
            } else if (rewardItem.getQuantityAvailable() > 0) {
                //或者还有次数
                rewardItem.setCostLP(rewardItem.getCostLP()+rewardItem.getCostLPIncrement());
                rewardItem.setQuantityAvailable(rewardItem.getQuantityAvailable()-1);
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }


    }

    @Override
    public boolean lostRewardItem(RewardItem rewardItem) {
        return false;
    }

    @Override
    public boolean buyRewardItem(int rewardItemID,int num) {
        return buyRewardItem(getRewardItem(rewardItemID),num);
    }

    @Override
    public boolean buyRewardItem(String rewardItem,int num) {
        return buyRewardItem(getRewardItem(rewardItem),num);
    }

    @Override
    public boolean returnRewardItem(RewardItem rewardItem) {
        return false;
    }

    /**
     * 购买奖励
     * @param rewardItem 奖励对象
     * @return 是否购买成功
     */
    private boolean buyRewardItem(RewardItem rewardItem,int num){
        int lpPoint = Game.getInstance().getHeroManager().getHero().getLifePoint().getLpPoint();
        if (lpPoint>=0&&rewardItem.getCostLP()<lpPoint){
            //有足够点数购买奖励
            if (gainRewardItem(rewardItem,num)){
                Game.getInstance().getHeroManager().getHero().getLifePoint().addPoint(-rewardItem.getCostLP());
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 获得奖励
     * @param rewardItem 奖励对象
     * @return 是否成功
     */
    private boolean gainRewardItem(RewardItem rewardItem,int num) {
        if (rewardItem != null) {
            if (rewardItem.getQuantityAvailable() == -1) {
                //无限次数

                //更新价格
                rewardItem.setCostLP(rewardItem.getCostLP()+rewardItem.getCostLPIncrement());
                Item item = null;
                if ((item=rewardItem.getItem())!=null){
                    //当前奖励会被添加到物品里
                    item.setQuantity(num);
                    Game.getInstance().getCommandManager().executeCommand(new AddItemCommand(item));
                }

                return true;
            } else if (rewardItem.getQuantityAvailable() > 0) {
                //或者还有次数
                rewardItem.setCostLP(rewardItem.getCostLP()+rewardItem.getCostLPIncrement());
                rewardItem.setQuantityAvailable(rewardItem.getQuantityAvailable()-1);
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }


    /**
     * 获得所有奖励
     * @return 奖励列表
     */
    @Override
    public List<RewardItem> getAllRewardItem() {
        return rewardItems;
    }

    /**
     * 获得特定分类的所有奖励
     * @param type 分类
     * @return 列表
     */
    @Override
    public List<RewardItem> getAllRewardItem(String type) {
        return Stream.of(rewardItems).filter(value -> value.getType().equals(type)).collect(Collectors.toList());
    }

    /**
     * 获得所有 可获得的奖励
     * @return 列表
     */
    @Override
    public List<RewardItem> getAllAvailableRewardItem() {
        return Stream.of(rewardItems).filterNot(value -> value.getQuantityAvailable()==0).collect(Collectors.toList());
    }

    /**
     * 获得所有 特定分类 里 可以获得 的奖励
     * @param type 分类
     * @return 列表
     */
    @Override
    public List<RewardItem> getAllAvailableRewardItem(String type) {
        return Stream.of(getAllAvailableRewardItem()).filter(value -> value.getType().equals(type)).collect(Collectors.toList());
    }

    /**
     * 获得所有 可获得的奖励名称
     * @return 名称列表
     */
    @Override
    public List<String> getAllAvailableRewardItemName() {
        return Stream.of(getAllAvailableRewardItem()).map(RewardItem::getName).collect(Collectors.toList());
    }

    /**
     * 获得所有 特定分类 的 可获得的奖励名称
     * @param type 分类
     * @return 名称列表
     */
    @Override
    public List<String> getAllAvailableRewardItemName(String type) {
        return Stream.of(getAllAvailableRewardItem(type)).map(RewardItem::getName).collect(Collectors.toList());

    }

    /**
     * 从数据库更新数据
     */
    private void loadRewardItemFromSQL() {

        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_REWARD, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            RewardItem item = getRewardItemFromCursor(cursor);
            rewardItems.add(item);
        }
        cursor.close();

    }


    /**
     * 从游标中提取 rewarditem
     *
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

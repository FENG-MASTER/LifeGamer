package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.CommandManager;
import com.lifegamer.fengmaster.lifegamer.command.command.item.AddItemCommand;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.GameBaseInitFinish;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IRewardManager;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public RewardItem getRewardItemByItemId(long id) {
        Optional<RewardItem> first = Stream.of(rewardItems).filter(value -> value.getItemId() == id).findFirst();
        if (first.isPresent()){
            return first.get();
        }else {
            return null;
        }
    }

    /**
     * 根据id获取奖励
     *
     * @param id 奖励id
     * @return 奖励
     */
    @Override
    public RewardItem getRewardItem(long id) {
        Optional<RewardItem> first = Stream.of(rewardItems).filter(value -> value.getId() == id).findFirst();
        if (first.isPresent()){
            return first.get();
        }else {
            return null;
        }
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
        return _addRewardItem(rewardItem);
    }

    @LogPoint(type = Log.TYPE.REWARDITEM,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT)
    private boolean _addRewardItem(RewardItem rewardItem){
        if (rewardItems.contains(rewardItem)){
            return false;
        }
        Item it = rewardItem.generateItem();
        if (!Game.getInstance().getItemManager().addItem(it)){
            //先添加物品,再添加奖励
            return false;
        }
        rewardItem.setItem(it);
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
        return _updateRewardItem(rewardItem);
    }

    @LogPoint(type = Log.TYPE.REWARDITEM,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT)
    private boolean _updateRewardItem(RewardItem rewardItem){
        if (rewardItems.contains(rewardItem)) {
            //缓存中有
            Item item = rewardItem.getItem2();
            if (item!=null){
                Game.getInstance().getItemManager().updateItem(item);
            }
            return Game.update(rewardItem);
        } else {
            RewardItem ri = getRewardItem(rewardItem.getId());
            if (ri != null) {
                //有相同id,更新
                ri.copyFrom(rewardItem);
                Item item = rewardItem.getItem2();
                if (item!=null){
                    Game.getInstance().getItemManager().updateItem(item);
                }
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
        return _removeRewardItem(rewardItem);
    }

    @LogPoint(type = Log.TYPE.REWARDITEM,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT)
    private boolean _removeRewardItem(RewardItem rewardItem){
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



    private boolean gainRewardItem(RewardItem item) {
        return false;
    }

    @Override
    public boolean gainRewardItem(String rewardItem, int num, int probability) {
        return false;
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
    public boolean lostRewardItem(RewardItem rewardItem,int num) {
        return false;
    }

    @Override
    public boolean lostRewardItem(int rewardItemID, int num) {
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
    @Override
    public boolean gainRewardItem(RewardItem rewardItem,int num) {
        if (rewardItem != null) {
            if (rewardItem.getQuantityAvailable() == -1) {
                //无限次数

                //更新价格
                rewardItem.setCostLP(rewardItem.getCostLP()+rewardItem.getCostLPIncrement());
                Item item = null;
                if ((item=rewardItem.getItem2())!=null){
                    //当前奖励会被添加到物品里
                    Item itemt=new Item();
                    itemt.copyFrom(item);

                    itemt.setQuantity(num);
                    Game.getInstance().getCommandManager().executeCommand(new AddItemCommand(itemt));
                }

                return true;
            } else if (rewardItem.getQuantityAvailable() > 0) {
                //或者还有次数
                rewardItem.setCostLP(rewardItem.getCostLP()+rewardItem.getCostLPIncrement());
                rewardItem.setQuantityAvailable(rewardItem.getQuantityAvailable()-1);

                Item item = null;
                if ((item=rewardItem.getItem2())!=null){
                    //当前奖励会被添加到物品里
                    Item itemt=new Item();
                    itemt.copyFrom(item);

                    itemt.setQuantity(num);
                    Game.getInstance().getCommandManager().executeCommand(new AddItemCommand(itemt));
                }


                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean gainRewardItem(int rewardItemID, int num) {
        RewardItem item = getRewardItem(rewardItemID);
        if (item!=null){
            return gainRewardItem(item,num);
        }
        return false;
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
            RewardItem item = new RewardItem();
            item.getFromCursor(cursor);
            rewardItems.add(item);
        }
        cursor.close();

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void gameInitFinsh(GameBaseInitFinish gameBaseInitFinish){
        Stream.of(rewardItems).filter(value -> value.getItem()==null).forEach(rewardItem -> rewardItem.setItem(Game.getInstance().getItemManager().getItem(rewardItem.getItemId())));
    }


}

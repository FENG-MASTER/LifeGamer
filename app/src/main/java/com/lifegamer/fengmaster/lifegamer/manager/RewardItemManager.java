package com.lifegamer.fengmaster.lifegamer.manager;

import com.lifegamer.fengmaster.lifegamer.manager.itf.IRewardManager;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/21.
 */

public class RewardItemManager implements IRewardManager {

    private List<RewardItem> rewardItems=new ArrayList<>();

    public RewardItemManager() {
        RewardItem rewardItem=new RewardItem();
        rewardItem.setName("奖励1");

        RewardItem rewardItem2=new RewardItem();
        rewardItem2.setName("奖励2");

        rewardItems.add(rewardItem);
        rewardItems.add(rewardItem2);
    }

    @Override
    public boolean addRewardItem(RewardItem rewardItem) {
        return false;
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
}

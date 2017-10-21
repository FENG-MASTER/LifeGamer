package com.lifegamer.fengmaster.lifegamer.manager;

import com.lifegamer.fengmaster.lifegamer.manager.itf.IAchievementManager;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 */

public class AchievementManager implements IAchievementManager{

    private List<Achievement> list=new ArrayList<>();

    public AchievementManager() {
        Achievement a1=new Achievement();
        a1.setName("成就1");

        Achievement a2=new Achievement();
        a2.setName("成就2");

        list.add(a1);
        list.add(a2);
    }

    @Override
    public void addAchievement(Achievement achievement) {

    }

    @Override
    public boolean removeAchievement(String name) {
        return false;
    }

    @Override
    public boolean removeAchievement(int id) {
        return false;
    }

    @Override
    public void gainAchievement(int id) {

    }

    @Override
    public void gainAchievement(String name) {

    }

    @Override
    public void gainAchievement(AchievementReward achievementReward) {

    }

    @Override
    public Achievement getAchievement(int id) {
        return null;
    }

    @Override
    public List<Achievement> getAllAchievement() {
        return null;
    }

    @Override
    public List<Achievement> getNoGetAchievment() {


        return null;
    }

    @Override
    public List<Achievement> getAllGotAchievement() {
        return null;
    }

    @Override
    public List<Achievement> getAllAchievement(String type) {
        return null;
    }

    @Override
    public List<Achievement> getNoGetAchievment(String type) {
        return null;
    }

    @Override
    public List<Achievement> getAllGotAchievement(String type) {
        return null;
    }

    @Override
    public void lostAchievement(int id) {

    }

    @Override
    public void lostAchievement(AchievementReward achievementReward) {

    }
}

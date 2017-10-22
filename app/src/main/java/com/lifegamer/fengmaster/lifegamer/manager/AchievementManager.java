package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IAchievementManager;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 本地成就管理器
 */

public class AchievementManager implements IAchievementManager{

    private List<Achievement> achievements =new ArrayList<>();

    private DBHelper helper=DBHelper.getInstance();

    public AchievementManager() {
        loadAchievementsFromSQL();
    }

    @Override
    public boolean addAchievement(Achievement achievement) {
        long l = Game.insert(achievement);
        if (l!=0){
            achievement.setId((int) l);
            achievements.add(achievement);
        }
        return l!=0;
    }

    @Override
    public boolean removeAchievement(String name) {
        return false;
    }

    @Override
    public boolean removeAchievement(long id) {
        return false;
    }

    @Override
    public boolean updateAchievement(Achievement achievement) {
        return Game.update(achievement);
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
        return achievements;
    }

    @Override
    public List<Achievement> getAllNoGetAchievment() {
        return Stream.of(achievements).filterNot(Achievement::isGot).collect(Collectors.toList());
    }

    @Override
    public List<Achievement> getAllGotAchievement() {
        return Stream.of(achievements).filterNot(Achievement::isGot).collect(Collectors.toList());

    }

    @Override
    public List<Achievement> getAllAchievement(String type) {
        return null;
    }

    @Override
    public List<Achievement> getAllNoGetAchievment(String type) {
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

    /**
     * 载入数据库数据
     */
    private void loadAchievementsFromSQL(){
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_ACHIEVEMENT, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            Achievement a = getAchievementFromCursor(cursor);
            achievements.add(a);
        }
    }


    /**
     * 从游标中读取成就
     * @param cursor 游标
     * @return 成就
     */
    private Achievement getAchievementFromCursor(Cursor cursor){
        Achievement achievement=new Achievement();
        achievement.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        achievement.setName(cursor.getString(cursor.getColumnIndex("name")));
        achievement.setType(cursor.getString(cursor.getColumnIndex("type")));
        achievement.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        achievement.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
        achievement.setGot(cursor.getInt(cursor.getColumnIndex("isGot"))==1);

        achievement.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));


        String gainTime = cursor.getString(cursor.getColumnIndex("gainTime"));
        if (gainTime != null && !gainTime.equals("")) {
            try {
                achievement.setGainTime(SimpleDateFormat.getInstance().parse(gainTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        if (createTime != null && !createTime.equals("")) {
            try {
                achievement.setCreateTime(SimpleDateFormat.getInstance().parse(createTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String updateTime = cursor.getString(cursor.getColumnIndex("updateTime"));
        if (updateTime != null && updateTime.equals("")) {
            try {
                achievement.setUpdateTime(SimpleDateFormat.getInstance().parse(updateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return achievement;
    }
}

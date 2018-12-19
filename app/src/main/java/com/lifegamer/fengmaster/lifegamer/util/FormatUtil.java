package com.lifegamer.fengmaster.lifegamer.util;

import android.text.format.DateFormat;

import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by qianzise on 2017/10/7.
 * <p>
 * 格式化相关工具类
 */

public class FormatUtil {

    /**
     * 整数型list转string
     *
     * @param list list
     * @return string形式
     */
    public static String list2Str(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Integer integer : list) {
            sb.append(integer + ",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }


    /**
     * string形式转换整数list
     *
     * @param str string形式
     * @return 整数list
     */
    public static List<Integer> str2List(String str) {
        if (str == null || str.equals("")) {
            return new ArrayList<>();
        }
        String[] split = str.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }

    /**
     * 图标string转详细资料
     *
     * @param avatarStr 图标string
     * @return 0号表示avatar管理器名, 1号表示avatar名称
     */
    public static String[] avatarStrFormat(String avatarStr) {
        if (avatarStr == null||avatarStr.equals("")) {
            return new String[2];
        }
        return avatarStr.split("#");
    }

    /**
     * skillmap转string形式
     *
     * @param skillMap skillmap key-能力名 val-能力增加xp
     * @return string形式
     */
    public static String skillMap2Str(Map<Long, Integer> skillMap) {
        if (skillMap == null||skillMap.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Long, Integer> entry : skillMap.entrySet()) {
            stringBuilder.append(entry.getKey()).append(",").append(entry.getValue()).append(";");
        }
        return stringBuilder.toString();
    }

    /**
     * string转skillmap形式
     *
     * @param str string形式
     * @return skillmap key-能力名 val-能力增加xp
     */
    public static Map<Long, Integer> str2SkillMap(String str) {
        if (str == null||str.equals("")) {
            return new HashMap<>();
        }
        Map<Long, Integer> map = new HashMap<>();
        String[] split = str.split(";");
        for (String ss : split) {
            String[] s = ss.split(",");
            map.put(Long.parseLong(s[0]), Integer.valueOf(s[1]));
        }
        return map;
    }

    public static String itemRewardList2Str(List<RandomItemReward> rewardList) {
        if (rewardList == null||rewardList.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (RandomItemReward randomItemReward : rewardList) {
            stringBuilder.append(randomItemReward.getRewardID()).append(",").
                    append(randomItemReward.getNum()).append(",").
                    append(randomItemReward.getProbability()).append(";");
        }

        return stringBuilder.toString();
    }

    public static List<RandomItemReward> str2ItemRewardList(String str) {
        if (str == null||str.equals("")) {
            return new ArrayList<>();
        }

        List<RandomItemReward> randomItemRewards = new ArrayList<>();

        String[] split = str.split(";");
        for (String ss : split) {
            String[] s = ss.split(",");
            randomItemRewards.add(new RandomItemReward(Long.valueOf(s[0]), Integer.valueOf(s[1]), Integer.valueOf(s[2])));
        }
        return randomItemRewards;
    }

    public static String achievementRewardList2Str(List<AchievementReward> list) {
        if (list == null||list.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (AchievementReward achievementReward : list) {
            stringBuilder.append(achievementReward.getAchievementID()).append(",").
                    append(achievementReward.getProbability()).append(";");
        }

        return stringBuilder.toString();
    }

    public static List<AchievementReward> str2achievementRewardList(String str){
        if (str==null||str.equals("")){
            return new ArrayList<>();
        }
        List<AchievementReward> list=new ArrayList<>();
        String[] split = str.split(";");
        for (String ss : split) {
            String[] s = ss.split(",");
            list.add(new AchievementReward(Long.valueOf(s[0]),Integer.valueOf(s[1])));
        }
        return list;
    }


    /**
     * 显示用string (非存储用格式)
     * @param date
     * @return
     */
    public static String date2Str(Date date){
        return DateFormat.format("EEEE,yyyy年MM月dd日,kk:mm",date).toString();
    }


    public static String date2BriefDesc(Date date){
        return DateFormat.format("dd号,kk:mm",date).toString();
    }

}

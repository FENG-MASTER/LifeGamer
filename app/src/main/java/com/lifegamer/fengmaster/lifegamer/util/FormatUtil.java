package com.lifegamer.fengmaster.lifegamer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/7.
 *
 * 格式化相关工具类
 */

public class FormatUtil {

    public static String list2Str(List<Integer> list){
        if (list==null||list.size()==0){
            return "";
        }
        StringBuilder sb=new StringBuilder();
        for (Integer integer : list) {
            sb.append(integer+",");
        }
        sb.deleteCharAt(sb.length());
        return sb.toString();
    }


    public static List<Integer> str2List(String str){
        if (str==null||str.length()==0){
            return new ArrayList<>();
        }
        String[] split = str.split(",");
        List<Integer> list=new ArrayList<>();
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }

    public static String[] avatarStrFormat(String avatarStr){
        return avatarStr.split("#");
    }
}

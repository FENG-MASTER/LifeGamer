package com.lifegamer.fengmaster.lifegamer.wight.model;

import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/13.
 */

public class SelectItem {

    //完成
    public static final int FINISH_ID =1;
    //编辑
    public static final int EDIT_ID=2;
    //删除
    public static final int DELETE_ID=3;
    //失败
    public static final int FAIL_ID=4;
    //笔记
    public static final int NOTES_ID=5;
    //获得
    public static final int GOT_ID=6;
    //失去
    public static final int LOSE_ID=7;






    public static final SelectItem FINISH = new SelectItem(FINISH_ID, "完成", R.drawable.ic_select_finish);
    public static final SelectItem EDIT = new SelectItem(EDIT_ID, "编辑", R.drawable.ic_select_edit);
    public static final SelectItem DELETE = new SelectItem(DELETE_ID, "删除", R.drawable.ic_select_delete);
    public static final SelectItem FAIL = new SelectItem(FAIL_ID, "失败", R.drawable.ic_select_fail);
    public static final SelectItem NOTES = new SelectItem(NOTES_ID, "笔记", R.drawable.ic_select_notes);
    public static final SelectItem GOT = new SelectItem(GOT_ID, "获得", R.drawable.ic_select_got);
    public static final SelectItem LOSE = new SelectItem(LOSE_ID, "失去", R.drawable.ic_select_lose);


    private int id;
    private String name;
    private int iconRes;

    public SelectItem(int id, String name, int iconRes) {
        this.id = id;
        this.name = name;
        this.iconRes = iconRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }


}
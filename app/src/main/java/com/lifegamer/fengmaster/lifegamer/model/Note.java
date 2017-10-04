package com.lifegamer.fengmaster.lifegamer.model;

import java.util.Date;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 笔记对象,暂时只支持文本格式的笔记
 */

public class Note {

    private int ID;

    private String text;

    private Date createTime;

    private Date updateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

package com.lifegamer.fengmaster.lifegamer.model;

import java.util.Date;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 成就实体类
 */

public class Achievement {

    /**
     * 成就ID
     */
    private int id;

    /**
     * 成就名称
     */
    private String name;

    /**
     * 成就图标
     */
    private String icon;

    /**
     * 成就详细描述
     */
    private String desc;

    /**
     * 是否已经获得
     */
    private boolean isGot;

    /**
     * 成就获得时间
     */
    private Date gainTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isGot() {
        return isGot;
    }

    public void setGot(boolean got) {
        isGot = got;
    }

    public Date getGainTime() {
        return gainTime;
    }

    public void setGainTime(Date gainTime) {
        this.gainTime = gainTime;
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

package com.lifegamer.fengmaster.lifegamer.model;

import java.util.Date;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 物品对象
 */
public class Item {

    /**
     * 物品ID
     */
    private int id;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品详细描述
     */
    private String desc;

    /**
     * 数量
     */
    private int quantity;

    /**
     * 物品图标
     */
    private String icon;

    /**
     * 是否属于消耗品
     */
    private boolean expendable;

    /**
     * 创建物品时间
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isExpendable() {
        return expendable;
    }

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
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

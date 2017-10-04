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
}

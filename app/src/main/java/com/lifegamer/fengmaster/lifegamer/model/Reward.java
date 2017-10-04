package com.lifegamer.fengmaster.lifegamer.model;

import java.util.Date;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 奖励实体类
 */

public class Reward {

    /**
     * 奖励ID
     */
    private int id;

    /**
     * 奖励名称
     */
    private String name;

    /**
     * 奖励所属种类
     */
    private String type;

    /**
     * 奖励详细描述信息
     */
    private String desc;

    /**
     * 获得奖励所需LP点数
     */
    private int costLP;

    /**
     * 剩余奖励数目 -1表示无限
     */
    private int quantityAvaiable;

    /**
     * 已经获得的次数
     */
    private int gainTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 奖励图标
     */
    private String icon;

    /**
     * 每次获得奖励后,奖励增加的LP价格
     */
    private int costLPIncrement;

    /**
     * 是否添加到物品
     */
    private boolean addToItem;

    public Item getItem(){
        if (!addToItem){
            return null;
        }
        //TODO :转换成物品
        return new Item();

    }


}

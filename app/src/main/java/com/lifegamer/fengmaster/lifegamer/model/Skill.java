package com.lifegamer.fengmaster.lifegamer.model;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 技能对象
 */
public class Skill {

    /**
     * 技能id
     */
    private int id;

    /**
     * 技能名字
     */
    private String name;

    /**
     * 技能当前的经验
     */
    private int XP;

    /**
     * 技能当前等级
     */
    private int level;

    /**
     * 技能所属种类
     */
    private String type;

    /**
     * 技能简介
     */
    private String intro;

    /**
     * 技能升级所需XP
     */
    private int upGradeXP;

    /**
     * 技能图标
     */
    private String icon;

    /**
     * 笔记ID
     */
    private List<Integer> notes;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpGradeXP() {
        return upGradeXP;
    }

    public void setUpGradeXP(int upGradeXP) {
        this.upGradeXP = upGradeXP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }


    @Override
    public boolean equals(Object obj) {
        return name.equals(((Skill)obj).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

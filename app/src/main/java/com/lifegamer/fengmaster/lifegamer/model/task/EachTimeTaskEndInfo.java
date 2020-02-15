package com.lifegamer.fengmaster.lifegamer.model.task;

/**
 * 每次完成任务时,额外信息
 * Created by FengMaster on 20/02/15.
 */
public class EachTimeTaskEndInfo {

    /**
     * 默认评分
     */
    private static final int DEF_SCORE=100;

    /**
     * 本次完成任务评分
     */
    private int score=DEF_SCORE;

    /**
     * 描述本次完成任务
     */
    private String desc;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

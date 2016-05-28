package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

/**
 *封装秒杀执行后的结果
 * Created by Luohong on 2016/5/28.
 */
public class SeckillExecution {

    private long seckillId;

    /**
     * 秒杀执行结果状态
     */
    private int state;

    /**
     * 状态表示
     */
    private String stateInfo;

    private SuccessKilled successKilled;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum){
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, String stateInfo){
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = stateInfo;
    }
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled){
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}

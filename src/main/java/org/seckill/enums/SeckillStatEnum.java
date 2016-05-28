package org.seckill.enums;

/**
 * Created by Luohong on 2016/5/28.
 */
public enum  SeckillStatEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改"),
    NOT_LOGIN(-4, "未登陆");

    private  int state;

    private String stateInfo;

    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStatEnum stateOf(int state){
        for (SeckillStatEnum statEnum : values()){
            if (statEnum.getState() == state)
                return  statEnum;
        }
        return null;
    }
}

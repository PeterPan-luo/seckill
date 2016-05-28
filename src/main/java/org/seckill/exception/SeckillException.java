package org.seckill.exception;

/**
 * 秒杀相关业务异常
 * Created by Luohong on 2016/5/28.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message){
        super(message);
    }

    public SeckillException(String message, Throwable e){
        super(message, e);
    }
}

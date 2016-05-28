package org.seckill.exception;

/**
 * Created by Luohong on 2016/5/28.
 */
public class SeckillCloseException extends  RuntimeException {

    public SeckillCloseException(String message){
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause){
        super(message, cause);
    }
}

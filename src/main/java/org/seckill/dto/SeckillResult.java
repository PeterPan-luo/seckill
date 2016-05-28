package org.seckill.dto;

import java.io.Serializable;

/**
 * Created by Luohong on 2016/5/28.
 */
//DTO:完成WEB层到Service层的数据传递
//所有的ajax请求的返回类型封装JSON结果
public class SeckillResult<T> implements Serializable{

    private T data;

    private boolean success;

    private String errorMsg;

    public SeckillResult(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public SeckillResult(boolean success, String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

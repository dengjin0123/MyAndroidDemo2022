package com.xiangke.http;

import java.util.List;

/**
 * 基础返回类
 * @author llw
 */
public class BaseResponse<T> {
    private static int SUCCESS_CODE = 200;//成功的code
    private int ret = -1;         //响应码
    private int errorCode = -1;
    private int total = 0;
    private String errorMsg;
    private String msg;             //提示信息
    private String message;             //提示信息
    private List<T> rows;       // 返回的具体数据
    private T obj;              // 返回的具体数据

    public boolean isSuccess(){
        return getRet() == SUCCESS_CODE;
    }
    public static int getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static void setSuccessCode(int successCode) {
        SUCCESS_CODE = successCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}

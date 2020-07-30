package com.shi.common.common;



import com.shi.common.enums.ResultEnum;

import java.io.Serializable;

import static com.shi.common.enums.ResultEnum.ERROR_CODE;
import static com.shi.common.enums.ResultEnum.SUCCESS_CODE;


/**
 * 返回统一数据结构
 *
 * @author shiyakun
 * @since 1.0
 */
public class R<T> implements Serializable {


    /**
     * 错误码
     */
    private int code;

    /**
     * 错误描述
     */
    private String msg;
    /**
     * 成功数据
     */
    private T data;

    private boolean success;

    private R() {
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    private R(int code, String msg, T data, boolean success) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    private R(int code, String msg,boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = null;
        this.success = success;
    }


    public static R createR() {
        R r = new R();
        return r;
    }

    public static R success() {
        R r = new R(SUCCESS_CODE.getCode(),SUCCESS_CODE.getMsg(),true);
        return r;
    }
    public static R success(Object data) {
        R r = new R(SUCCESS_CODE.getCode(),SUCCESS_CODE.getMsg(),data,true);
        return r;
    }

    public static R error() {
        R r = new R(ERROR_CODE.getCode(),ERROR_CODE.getMsg(),false);
        return r;
    }

    public static R result(int code,String msg, Object data,boolean success) {
        R r = new R(code,msg,data,success);
        return r;
    }
    public static R result(int code,String msg,boolean success) {
        R r = new R(code,msg,success);
        return r;
    }

    public static R result(ResultEnum resultEnum, boolean success) {
        R r = new R(resultEnum.getCode(), resultEnum.getMsg(),success);
        return r;
    }
    public static R result(ResultEnum resultEnum, Object data,boolean success) {
        R r = new R(resultEnum.getCode(), resultEnum.getMsg(),data,success);
        return r;
    }



    @Override
    public String toString() {
        return "Result{" +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}

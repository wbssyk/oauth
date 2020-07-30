package com.shi.common.enums;

/**
 * @author shiyakun
 */
public enum ResultEnum {

    /**
     * 参数不符合格式,状态码
     */
    PARAM_VALID_ERROR(50, "参数不符合格式"),

    /**
     * 参数不符合格式,状态码
     */
    TIME_FORMAT_ERROR(51, "时间格式错误"),
    /**
     * 成功状态码
     */
    SUCCESS_CODE(200, "成功"),
    /**
     * 不支持的请求方法 状态码
     */
    METHOD_NOT_SUPPORT(415, "不支持的请求方法"),
    /**
     * 错误 状态码
     */
    ERROR_CODE(500, "出现未知错误"),

    /**
     * token缺失
     */
    OAUTH_TOKEN_MISSING(2000, "token缺失"),

    /**
     * 非法的token
     */
    OAUTH_TOKEN_ILLEGAL(2001, "非法的token"),

    /**
     * 权限不足
     */
    OAUTH_TOKEN_DENIED(2002, "权限不足");

    /**
     * code码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

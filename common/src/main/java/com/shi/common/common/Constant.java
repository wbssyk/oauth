package com.shi.common.common;

/**
 * @author shiyakun
 * @Description TODO
 */
public interface Constant {

    /**
     * 错误日志标记
     */
    int ERROR = 0;
    /**
     * 行为日志标记
     */
    int ACTION = 1;
    /**
     * 其他日志标记
     */
    int OTHER = 2;

    /**
     * 知网在线教学 后台系统 日志 mongodb collection前缀
     */
    String KUSER_MONGO_COLL_PREFIX = "kuserlog";

    /**
     * 知网在线教学 后台系统 错误日志  kafka 主题
     */
    String KUSER_ERROR_LOG = "kuser-error";
    /**
     * 知网在线教学 后台系统 行为日志  kafka 主题
     */
    String KUSER_ACTION_LOG = "kuser-action";
    /**
     * 知网在线教学 后台系统 其他日志  kafka 主题
     */
    String KUSER_OTHER_LOG = "kuser-other";

}

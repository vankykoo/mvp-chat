package com.vanky.chat.common.constant;

/**
 * @author vanky
 * @create 2024/11/2 22:39
 */
public interface MsgStatusConstant {

    /**
     * 发送中
     */
    int SENDING = 1;

    /**
     * 消息已抵达
     */
    int ARRIVED = 2;

    /**
     * 消息已读
     */
    int READ = 3;

    /**
     * 消息发送失败
     */
    int FAIL = 4;

}

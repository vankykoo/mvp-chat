package com.vanky.chat.common.constant;

/**
 * @author vanky
 * @create 2024/11/2 21:38
 */
public interface MsgTypeConstant {

    /**
     * 登录消息
     */
    int LOGIN_MSG = 1;

    /**
     * 聊天消息
     */
    int CHAT_MSG = 2;

    /**
     * ACK消息
     */
    int ACK_MSG = 3;

    /**
     * 心跳-ping
     */
    int PING_MSG = 4;

    /**
     * 心跳-pong
     */
    int PONG_MSG = 5;

}

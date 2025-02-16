package com.vanky.chat.common.constant;

/**
 * @author vanky
 * @create 2024/11/3 14:51
 */
public interface RedisKeys {

    /**
     * 等待 ack
     */
    String WAIT_ACK_KEY = "wait_ack:";

    /**
     * 全局会话 -- computer
     */
    String COMPUTER_GLOBAL_SESSION = "computer_global_session:";

    /**
     * 全局会话 -- phone
     */
    String PHONE_GLOBAL_SESSION = "phone_global_session:";

    /**
     * 服务端已经接收过的消息
     */
    String SERVER_RECEIVED_MSG = "server_received_msg";

}

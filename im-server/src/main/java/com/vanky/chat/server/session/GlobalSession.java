package com.vanky.chat.server.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vanky
 * @create 2024/11/2 16:18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GlobalSession {

    /**
     * 服务端地址
     */
    private String host;

    /**
     * 服务端端口
     */
    private int port;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 客户端标识
     */
    private int clientTag;

    /**
     * 会话id
     */
    private String sessionId;

}

package com.vanky.chat.server.utils;

import com.vanky.chat.common.utils.RedisUtil;
import com.vanky.chat.server.session.GlobalSession;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.vanky.chat.common.constant.ClientTagConstant.PHONE;
import static com.vanky.chat.common.constant.RedisKeys.COMPUTER_GLOBAL_SESSION;
import static com.vanky.chat.common.constant.RedisKeys.PHONE_GLOBAL_SESSION;

/**
 * @author vanky
 * @create 2024/11/3 21:36
 */
@Component
public class SessionUtil {

    @Value("${netty.server.port}")
    private int port;

    @Value("${netty.server.host}")
    private String host;

    /**
     * 保存全局会话到 redis
     * @param userId
     * @param clientTag
     */
    public void saveGlobalSession(Long userId, int clientTag){
        GlobalSession globalSession = new GlobalSession(host,
                port, userId, clientTag, UUID.randomUUID().toString());

        String globalSessionCacheKey = COMPUTER_GLOBAL_SESSION + userId;

        if (clientTag == PHONE){
            globalSessionCacheKey = PHONE_GLOBAL_SESSION + userId;
        }

        RedisUtil.lput(globalSessionCacheKey, globalSession);
    }

}

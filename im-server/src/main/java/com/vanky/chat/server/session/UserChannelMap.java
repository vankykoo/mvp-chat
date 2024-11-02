package com.vanky.chat.server.session;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vanky
 * @create 2024/11/2 16:18
 */
public class UserChannelMap {

    private static ConcurrentHashMap<Long, NioSocketChannel> userChannelMap = new ConcurrentHashMap<>();

    public static void put(Long userId, NioSocketChannel channel){
        userChannelMap.put(userId, channel);
    }

    public static NioSocketChannel getChannelByUserId(Long userId){
        return userChannelMap.get(userId);
    }

    public static int size(){
        return userChannelMap.size();
    }
}

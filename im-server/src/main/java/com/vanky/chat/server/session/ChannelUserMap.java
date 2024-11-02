package com.vanky.chat.server.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vanky
 * @create 2024/11/2 16:18
 */
public class ChannelUserMap {

    private static ConcurrentHashMap<String, Long> channelUserMap = new ConcurrentHashMap<>();

    public static void put(String channelId, Long userId){
        channelUserMap.put(channelId, userId);
    }

    public static Long getUserIdByChannelId(String channelId){
        return channelUserMap.get(channelId);
    }

    public static int size(){
        return channelUserMap.size();
    }

}

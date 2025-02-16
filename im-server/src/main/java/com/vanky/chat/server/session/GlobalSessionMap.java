package com.vanky.chat.server.session;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vanky
 * @create 2024/11/2 16:41
 */
public class GlobalSessionMap {

    private static ConcurrentHashMap<Long, GlobalSession> globalSessionMap = new ConcurrentHashMap<>();

    public static void put(Long userId, GlobalSession globalSession){
        globalSessionMap.put(userId, globalSession);
    }

    public static GlobalSession getSessionByUserId(Long userId){
        return globalSessionMap.get(userId);
    }

    public static void remove(Long userId){
        globalSessionMap.remove(userId);
    }

    public static int size(){
        return globalSessionMap.size();
    }

}

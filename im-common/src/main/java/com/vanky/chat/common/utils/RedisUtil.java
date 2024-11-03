package com.vanky.chat.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author vanky
 * @create 2024/4/13 21:36
 */
public class RedisUtil {

    private static RedisTemplate<Object, Object> redisTemplate;

    static {
        RedisUtil.redisTemplate = SpringUtil.getBean("redisTemplate", RedisTemplate.class);
    }

    /**
     * 设置缓存，没有过期时间
     * @param key
     * @param value
     */
    public static void put(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存，有过期时间
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public static void put(String key, Object value, int time, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 获取缓存，并转化为对应的类
     * @param key
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T get(String key, Class<T> tClass){
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj == null){
            return null;
        }

        return toOneBean(obj, tClass);
    }

    /**
     * 获取多个值，并封装为集合
     * @param keys
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> List<T> multiGet(Collection<String> keys, Class<T> tClass){
        List<Object> objects = redisTemplate.opsForValue().multiGet(Arrays.asList(keys.toArray()));
        if (Objects.isNull(objects)){
            return new ArrayList<>();
        }

        return objects.stream().map(obj -> toOneBean(obj, tClass)).collect(Collectors.toList());
    }

    /**
     * 删除缓存
     * @param key
     */
    public static void del(String key){
        redisTemplate.delete(key);
    }

    /**
     * 删除多个缓存
     * @param key
     */
    public static void del(Collection<String> key){
        redisTemplate.delete(key);
    }

    /**
     * set 类型，设置缓存
     * @param key
     * @param value
     */
    public static void sput(String key, Object value){
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * set类型，获取缓存
     * @param key
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> Set<T> sget(String key, Class<T> tClass){
        Set<Object> members = redisTemplate.opsForSet().members(key);
        if (Objects.isNull(members)){
            return new HashSet<>();
        }

        return members.stream().map(member -> toOneBean(member, tClass)).collect(Collectors.toSet());
    }

    /**
     * 将缓存转化为指定类
     * @param obj
     * @param tClass
     * @return
     * @param <T>
     */
    static <T> T toOneBean(Object obj, Class<T> tClass){
        String jsonString = JSONObject.toJSONString(obj);

        return obj == null ? null : JSONObject.parseObject(jsonString, tClass);
    }

    /**
     * set 类型，删除缓存
     * @param cacheKey
     * @param userId
     */
    public static void sdel(String cacheKey, Long userId) {
        redisTemplate.opsForSet().remove(cacheKey, userId);
    }

    /**
     * 判断缓存是否存在
     * @param cacheKey
     * @return
     */
    public static boolean hasExisted(String cacheKey) {
        Object object = redisTemplate.opsForValue().get(cacheKey);
        return !Objects.isNull(object);
    }

    /**
     * hash相关操作
     */
    /**
     * hash类型，设置缓存
     * @param hashKey
     * @param key
     * @param value
     */
    public static void  hput(String hashKey, String key, Object value){
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    /**
     * hash类型，获取缓存
     * @param hashKey
     * @param key
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T hget(String hashKey, String key, Class<T> tClass){
        Object obj = redisTemplate.opsForHash().get(hashKey, key);
        return toOneBean(obj, tClass);
    }

    /**
     * 获取整个 hash 缓存
     * @param hashKey
     * @param valueClass
     * @return
     * @param <T>
     */
    public static <T> Map<String, T> hgetAll(String hashKey, Class<T> valueClass){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(hashKey);
        Map<String, T> res = new HashMap<>();
        entries.forEach((key, value) -> {
            res.put((String) key, toOneBean(value, valueClass));
        });

        return res;
    }

    /**
     * 删除 hash 的一个缓存
     * @param hashKey
     * @param key
     */
    public static void hdel(String hashKey, String key){
        redisTemplate.opsForHash().delete(hashKey, key);
    }

    /**
     * 删除 hash 内的多个缓存
     * @param hashKey
     * @param keys
     */
    public static void hdel(String hashKey, Collection<String> keys){
        redisTemplate.opsForHash().delete(hashKey, keys.toArray());
    }

    /**
     * List 左 put
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void lput(String key, T value){
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * List 右 get
     * @param key
     * @param tClass
     * @return
     * @param <T>
     */
    public static <T> T lget(String key, Class<T> tClass){
        return toOneBean(redisTemplate.opsForList().rightPop(key), tClass);
    }

    /**
     * List 的 长度
     * @param key
     * @return
     */
    public static Long lLength(String key){
        return redisTemplate.opsForList().size(key);
    }

}

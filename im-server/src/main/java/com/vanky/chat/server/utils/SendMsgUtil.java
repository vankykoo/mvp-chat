package com.vanky.chat.server.utils;

import com.vanky.chat.common.protobuf.BaseMsgProto;
import com.vanky.chat.common.utils.RedisUtil;
import com.vanky.chat.server.session.UserChannelMap;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.vanky.chat.common.constant.NumberConstant.WAIT_ACK_TIME;
import static com.vanky.chat.common.constant.RedisKeys.WAIT_ACK_KEY;

/**
 * 发送消息工具
 * @author vanky
 * @create 2024/11/3 14:06
 */
@Component
@Slf4j
public class SendMsgUtil {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 需要接收方返回 ack 消息
     */
    public void sendMsg4Ack(Long userId, BaseMsgProto.BaseMsg msg){
        // 1. 发送消息
        NioSocketChannel channel = UserChannelMap.getChannelByUserId(userId);
        channel.writeAndFlush(msg);

        // 2. 将消息保存到 redis
        String msgIdAndRetryTimes = msg.getId() + ":";
        String cacheKey = WAIT_ACK_KEY + msg.getId();
        if (RedisUtil.hasExisted(cacheKey)){
            //消息体已经存在了，说明这次是重发
            msgIdAndRetryTimes += "1";
        }else{
            //消息体不存在，说明是第一次发送
            msgIdAndRetryTimes += "0";

            RedisUtil.put(cacheKey, msg.toByteArray(), WAIT_ACK_TIME, TimeUnit.MINUTES);
        }

        // 3. 将消息id 和 重试次数存入 rabbitmq
        rabbitTemplate.convertAndSend("wait-msg-ack-exchange",
                "wait.msg.ack.private",
                msgIdAndRetryTimes);
        log.info("保存等待ack消息到延迟队列：{}", msgIdAndRetryTimes);
    }

    // 2.不需要接收方返回 ack 消息
    public void sendMsg(Long userId, BaseMsgProto.BaseMsg msg){
        // 发送消息
        NioSocketChannel channel = UserChannelMap.getChannelByUserId(userId);
        channel.writeAndFlush(msg);
    }
}

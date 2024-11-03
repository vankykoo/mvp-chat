package com.vanky.chat.server.mq;

import com.google.protobuf.InvalidProtocolBufferException;
import com.rabbitmq.client.Channel;
import com.vanky.chat.common.exception.MyException;
import com.vanky.chat.common.protobuf.BaseMsgProto;
import com.vanky.chat.common.utils.RedisUtil;
import com.vanky.chat.server.utils.SendMsgUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.vanky.chat.common.constant.ChatTypeConstant.PRIVATE;
import static com.vanky.chat.common.constant.MsgTypeConstant.CHAT_MSG;
import static com.vanky.chat.common.constant.RedisKeys.WAIT_ACK_KEY;

/**
 * @author vanky
 * @create 2024/11/3 15:18
 */
@Component
@Slf4j
public class WaitAckMqHandler {

    @Resource
    private SendMsgUtil sendMsgUtil;

    @RabbitListener(queues = {"msg.delay.queue"})
    public void WaitMsgAckDelayListener(Message message, Channel channel, String uniqueIdAndTimes) throws InvalidProtocolBufferException {
        log.info("消息进入死信队列：{}", uniqueIdAndTimes);

        //uniqueId : times
        String[] split = uniqueIdAndTimes.split(":");
        Long uniqueId = Long.parseLong(split[0]);

        //1.到redis中查看 detail 是否存在
        String cacheKey = WAIT_ACK_KEY + uniqueId;
        byte[] arr = RedisUtil.get(cacheKey, byte[].class);
        BaseMsgProto.BaseMsg baseMsg = BaseMsgProto.BaseMsg.parseFrom(arr);

        if (baseMsg != null){
            //2.如果存在，说明还没 收到/处理 ack
            int retryTime = Integer.parseInt(split[1]);
            if (retryTime == 1){
                throw new MyException.MessageSendException("重试发送多次消息失败！");
            }else {
                //重发
                resendMsg(baseMsg);
            }
        }
        //3.如果不存在，说明已经发送成功

        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 消息重发
     * @param baseMsg
     */
    private void resendMsg(BaseMsgProto.BaseMsg baseMsg){
        int msgType = baseMsg.getMsgType();
        int chatType = baseMsg.getChatType();

        if (msgType == CHAT_MSG){
            if (chatType == PRIVATE){
                //私聊
                sendMsgUtil.sendMsg4Ack(baseMsg.getToUserId(), baseMsg);
            }else{
                //群聊
            }
        }else{
            //离线消息
        }
    }

}

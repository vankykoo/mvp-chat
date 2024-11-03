package com.vanky.chat.server.processor;

import com.vanky.chat.common.protobuf.BaseMsgProto;
import com.vanky.chat.common.utils.MsgGenerator;
import com.vanky.chat.server.utils.SendMsgUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author vanky
 * @create 2024/11/3 15:58
 */
@Component
@Slf4j
public class PrivateMsgProcessor {

    @Resource
    private SendMsgUtil sendMsgUtil;

    /**
     * 处理私聊消息
     * @param msg
     */
    public void processPrivateMsg(BaseMsgProto.BaseMsg msg){
        // 1.判断是否为好友
        log.info("{} 与 {} 互为好友", msg.getFromUserId(), msg.getToUserId());

        // 2.消息入库（修改消息状态）
        log.info("消息入库成功");

        // 3.返回 ack 消息
        BaseMsgProto.BaseMsg ackMsg = MsgGenerator.generateAckMsg(msg);
        sendMsgUtil.sendMsg(msg.getFromUserId(), msg);

        // 4.消息推送
        sendMsgUtil.sendMsg4Ack(msg.getToUserId(), msg);
    }



}

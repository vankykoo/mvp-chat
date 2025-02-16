package com.vanky.chat.client.processor;

import com.vanky.chat.common.protobuf.BaseMsgProto;
import com.vanky.chat.common.utils.MsgGenerator;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author vanky
 * @create 2024/11/3 20:13
 */
public class ClientPrivateMsgProcessor {

    /**
     * 收到私聊消息
     * @param msg
     * @param channel
     */
    public void processPrivateMsg(BaseMsgProto.BaseMsg msg, NioSocketChannel channel){
        BaseMsgProto.BaseMsg ackMsg = MsgGenerator.generateAckMsg(msg);
        // 返回ack消息
        channel.writeAndFlush(ackMsg);
    }

}

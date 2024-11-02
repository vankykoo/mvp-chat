package com.vanky.chat.common.utils;

import com.google.protobuf.ByteString;
import com.vanky.chat.common.protobuf.BaseMsgProto;

import static com.vanky.chat.common.constant.MsgStatusConstant.SENDING;
import static com.vanky.chat.common.constant.MsgTypeConstant.ACK_MSG;

/**
 * @author vanky
 * @create 2024/11/2 22:25
 */
public class MsgGenerator {

    public static BaseMsgProto.BaseMsg generateAckMsg(BaseMsgProto.BaseMsg msg){
        long id = msg.getId();
        int msgType = msg.getMsgType();
        // 消息内容：【消息id - 消息类型】
        String content = id + "-" + msgType;

        BaseMsgProto.BaseMsg baseMsg = BaseMsgProto.BaseMsg.newBuilder()
                .setId(0L)
                .setUniqueId(0L)
                .setFromUserId(0L)
                .setToUserId(0L)
                .setMsgType(ACK_MSG)
                .setChatType(msg.getChatType())
                .setContent(ByteString.copyFromUtf8(content))
                .setStatus(SENDING)
                .build();

        return baseMsg;
    }

}

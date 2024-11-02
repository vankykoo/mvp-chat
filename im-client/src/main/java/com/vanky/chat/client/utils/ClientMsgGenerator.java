package com.vanky.chat.client.utils;

import com.vanky.chat.common.protobuf.BaseMsgProto;
import org.springframework.stereotype.Component;

import static com.vanky.chat.common.constant.MsgStatusConstant.SENDING;
import static com.vanky.chat.common.constant.MsgTypeConstant.LOGIN_MSG;

/**
 * @author vanky
 * @create 2024/11/2 22:49
 */
public class ClientMsgGenerator {

    /**
     * 生成登录消息
     * @param userId
     * @return
     */
    public BaseMsgProto.BaseMsg generateLoginMsg(Long userId){
        BaseMsgProto.BaseMsg baseMsg = BaseMsgProto.BaseMsg.newBuilder()
                .setId(12415L)  // todo 生成消息id
                .setUniqueId(0L)
                .setFromUserId(userId)
//                .setToUserId(0L)
                .setMsgType(LOGIN_MSG)
//                .setChatType(msg.getChatType())
//                .setContent()
                .setStatus(SENDING)
                .build();

        return baseMsg;
    }

}

package com.vanky.chat.server.processor;

import com.vanky.chat.common.protobuf.BaseMsgProto;
import com.vanky.chat.common.utils.MsgGenerator;
import com.vanky.chat.server.session.ChannelUserMap;
import com.vanky.chat.server.session.GlobalSession;
import com.vanky.chat.server.session.UserChannelMap;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * @author vanky
 * @create 2024/11/2 22:09
 */
public class LoginMsgProcessor {

    public void userLogin(BaseMsgProto.BaseMsg baseMsg, NioSocketChannel channel){
        Long userId = baseMsg.getFromUserId();
        // 1. 保存到本地服务器
        UserChannelMap.put(userId, channel);
        ChannelUserMap.put(channel.id().asLongText(), userId);

        //todo 2. 保存 GlobalSession 到 redis
        GlobalSession globalSession = new GlobalSession("localhost",
                20001, userId, UUID.randomUUID().toString());

        //todo 3. 在各群聊中维护自己的在线信息

        //todo 4. 推送在线的好友

        //todo 5. 给好友推送自己上线的信息

        // 6. 返回 ack 消息
        channel.writeAndFlush(MsgGenerator.generateAckMsg(baseMsg));
    }

}

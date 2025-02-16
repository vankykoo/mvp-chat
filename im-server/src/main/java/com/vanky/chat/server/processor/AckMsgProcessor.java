package com.vanky.chat.server.processor;

import com.vanky.chat.common.protobuf.BaseMsgProto;
import com.vanky.chat.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.vanky.chat.common.constant.MsgTypeConstant.CHAT_MSG;
import static com.vanky.chat.common.constant.RedisKeys.WAIT_ACK_KEY;

/**
 * @author vanky
 * @create 2024/11/3 20:18
 */
@Component
@Slf4j
public class AckMsgProcessor {

    public void processAckMsg(BaseMsgProto.BaseMsg msg){
        String content = msg.getContent().toStringUtf8();
        String[] strings = content.split("-");

        Long msgId = Long.parseLong(strings[0]);
        int msgType = Integer.parseInt(strings[1]);

        switch (msgType){
            case CHAT_MSG:
                processPrivateMsgAck(msgId);
                break;
        }
    }

    private void processPrivateMsgAck(Long msgId){
        // 删除 redis 重试消息缓存
        RedisUtil.del(WAIT_ACK_KEY + msgId);
    }

}

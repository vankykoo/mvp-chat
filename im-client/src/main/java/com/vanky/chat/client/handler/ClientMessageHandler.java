package com.vanky.chat.client.handler;

import com.vanky.chat.client.processor.ClientPrivateMsgProcessor;
import com.vanky.chat.common.protobuf.BaseMsgProto;

import static com.vanky.chat.common.constant.ChatTypeConstant.PRIVATE;
import static com.vanky.chat.common.constant.MsgTypeConstant.*;

import com.vanky.chat.common.utils.MsgGenerator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * 消息处理器
 * @author vanky
 * @create 2024/11/2 21:17
 */
@Slf4j
public class ClientMessageHandler extends SimpleChannelInboundHandler<BaseMsgProto.BaseMsg> {

    ClientPrivateMsgProcessor clientPrivateMsgProcessor = new ClientPrivateMsgProcessor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsgProto.BaseMsg msg) throws Exception {
        // 消息去重

        // 消息加密解密

        // 消息对应处理
        int msgType = msg.getMsgType();
        int chatType = msg.getChatType();
        NioSocketChannel channel = (NioSocketChannel) ctx.channel();

        switch (msgType){
            case ACK_MSG:
                log.info("接收到 ack 消息:{}", msg.getContent());
                break;
            case CHAT_MSG:
                if (chatType == PRIVATE){
                    clientPrivateMsgProcessor.processPrivateMsg(msg, channel);
                } else {

                }
                break;
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().id().asLongText() + "--连接服务端成功！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().id().asLongText() + "--与服务端断开连接！");
    }


}

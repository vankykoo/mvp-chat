package com.vanky.chat.server.handler;

import com.vanky.chat.common.protobuf.BaseMsgProto;

import static com.vanky.chat.common.constant.ChatTypeConstant.PRIVATE;
import static com.vanky.chat.common.constant.MsgTypeConstant.*;
import static com.vanky.chat.common.constant.RedisKeys.SERVER_RECEIVED_MSG;

import com.vanky.chat.common.utils.RedisUtil;
import com.vanky.chat.server.processor.AckMsgProcessor;
import com.vanky.chat.server.processor.LoginMsgProcessor;
import com.vanky.chat.server.processor.PrivateMsgProcessor;
import com.vanky.chat.server.session.ChannelUserMap;
import com.vanky.chat.server.session.GlobalSessionMap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author vanky
 * @create 2024/11/2 21:37
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class ServerMessageHandler extends SimpleChannelInboundHandler<BaseMsgProto.BaseMsg> {

    @Resource
    private PrivateMsgProcessor privateMsgProcessor;

    @Resource
    private AckMsgProcessor ackMsgProcessor;

    @Resource
    private LoginMsgProcessor loginMsgProcessor;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsgProto.BaseMsg msg) throws Exception {
        // 消息去重
        if (RedisUtil.sisMember(SERVER_RECEIVED_MSG, msg.getId())){
            log.error("消息已经处理过了：{}", msg.getId());
            return;
        }

        //todo 消息解密

        // 处理不同类型消息
        int msgType = msg.getMsgType();
        int chatType = msg.getChatType();
        NioSocketChannel nioSocketChannel = (NioSocketChannel) ctx.channel();

        switch (msgType){
            case LOGIN_MSG:
                loginMsgProcessor.userLogin(msg, nioSocketChannel);
                break;
            case CHAT_MSG:
                if (chatType == PRIVATE){
                    privateMsgProcessor.processPrivateMsg(msg);
                }
                break;
            case ACK_MSG:
                ackMsgProcessor.processAckMsg(msg);
                break;
        }

        // 记录消息，用于去重
        RedisUtil.sadd(SERVER_RECEIVED_MSG, msg.getId());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id() + "--客户端连接成功！");
        System.out.println("当前服务端连接数：" + (GlobalSessionMap.size() + 1));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id() + "--客户端断开连接！");

        GlobalSessionMap.remove(ChannelUserMap.getUserIdByChannelId(ctx.channel().id().toString()));
        System.out.println("当前服务端连接数：" + (GlobalSessionMap.size()));
    }
}

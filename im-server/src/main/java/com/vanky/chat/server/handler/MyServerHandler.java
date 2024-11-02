package com.vanky.chat.server.handler;

import com.vanky.chat.server.session.ChannelUserMap;
import com.vanky.chat.server.session.GlobalSession;
import com.vanky.chat.server.session.GlobalSessionMap;
import com.vanky.chat.server.session.UserChannelMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到消息：" + msg);

        // 获取用户id
        Long userId = Long.parseLong(msg);

        // 记录登录信息
        NioSocketChannel nioSocketChannel = (NioSocketChannel) ctx.channel();
        // 1. 存本地
        UserChannelMap.put(userId, nioSocketChannel);
        ChannelUserMap.put(nioSocketChannel.id().toString(), userId);

        // 2. 存redis
        System.out.println(new GlobalSession("localhost", 20001, userId, UUID.randomUUID().toString()));

        ctx.channel().writeAndFlush("收到了哥们：" + msg);
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
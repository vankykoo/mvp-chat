package com.vanky.chat.client.netty;

import com.vanky.chat.client.handler.ClientMessageHandler;
import com.vanky.chat.common.protobuf.BaseMsgProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author vanky
 * @create 2024/11/2 15:45
 */
public class NettyClient {

    private Bootstrap bootstrap;

    public NettyClient() {
        bootstrap = initBootstrap();
    }

    private Bootstrap initBootstrap(){

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //解码器
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(BaseMsgProto.BaseMsg.getDefaultInstance()));
                        //处理器
                        pipeline.addLast("clientMessageHandler", new ClientMessageHandler());
                        //编码器
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());
                    }
                });

        return bootstrap;
    }

    /**
     * 客户端与服务端建立连接，并保存用户与连接的映射信息
     * @param host
     * @param port
     * @param userId
     * @return
     */
    public NioSocketChannel connect(String host, Integer port, Long userId){
        try{
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            NioSocketChannel channel = (NioSocketChannel) channelFuture.channel();

            return channel;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.vanky.chat.client.msg;

import com.vanky.chat.client.netty.NettyClient;
import com.vanky.chat.client.utils.ClientMsgGenerator;
import com.vanky.chat.common.protobuf.BaseMsgProto;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author vanky
 * @create 2024/11/3 16:17
 */
@SpringBootTest
public class PrivateMsgTest {

    @Test
    public void sendPrivateMsg() throws Exception{
        NettyClient nettyClient = new NettyClient();
        NioSocketChannel channel = nettyClient.connect("localhost", 20001, 1L);

        NettyClient nettyClient2 = new NettyClient();
        NioSocketChannel channel2 = nettyClient2.connect("localhost", 20001, 2L);

        Thread.sleep(1000 * 5);

        ClientMsgGenerator clientMsgGenerator = new ClientMsgGenerator();
        BaseMsgProto.BaseMsg baseMsg = clientMsgGenerator.generatePrivateMsg(1L, 2L, "你好2号，我是1号！");

        channel.writeAndFlush(baseMsg);

        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

package com.vanky.chat.client.msg;

import com.vanky.chat.client.netty.NettyClient;
import com.vanky.chat.client.utils.ClientMsgGenerator;
import com.vanky.chat.common.protobuf.BaseMsgProto;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author vanky
 * @create 2024/11/2 23:00
 */
@SpringBootTest
public class LoginMsgTest {

    @Test
    public void sendLoginMsgTest(){
        NettyClient nettyClient = new NettyClient();
        NioSocketChannel channel = nettyClient.connect("localhost", 20001, 3938L);
        ClientMsgGenerator clientMsgGenerator = new ClientMsgGenerator();

        BaseMsgProto.BaseMsg baseMsg = clientMsgGenerator.generateLoginMsg(3938L);

        channel.writeAndFlush(baseMsg);

        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

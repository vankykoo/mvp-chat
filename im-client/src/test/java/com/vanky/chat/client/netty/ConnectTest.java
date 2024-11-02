package com.vanky.chat.client.netty;

import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author vanky
 * @create 2024/11/2 15:53
 */
@SpringBootTest
public class ConnectTest {

    @Test
    public void testStart(){
        NettyClient nettyClient = new NettyClient();
        NioSocketChannel channel = nettyClient.connect("localhost", 20001, 3938L);
        //channel.writeAndFlush("你好，我是3938");
        channel.writeAndFlush("3938");

        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

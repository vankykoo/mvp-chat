package com.vanky.chat.server.netty;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author vanky
 * @create 2024/11/2 15:55
 */
@SpringBootTest
public class StartTest {

    @Test
    public void testConnect(){
        NettyServer nettyServer = new NettyServer();
        nettyServer.run();
    }

}

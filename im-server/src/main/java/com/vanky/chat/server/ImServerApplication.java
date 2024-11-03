package com.vanky.chat.server;

import com.vanky.chat.server.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vanky.chat.server")
public class ImServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);

        NettyServer nettyServer = new NettyServer();
        nettyServer.run();
    }

}

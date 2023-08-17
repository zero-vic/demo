package com.hy.im.server.serve;

import com.hy.im.codec.decoder.MessageDecoder;
import com.hy.im.codec.encoder.MessageEncoder;
import com.hy.im.server.handle.HeartBeatHandler;
import com.hy.im.server.handle.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName ImTcpServer
 * description:
 * yao create 2023年08月14日
 * version: 1.0
 */
@Component
@Slf4j
public class ImTcpServer {

    @Value("${im.tcpPort}")
    private int port;
    @Value("${im.bossThreadSize}")
    private int bossThreadSize;
    @Value("${im.workThreadSize}")
    private int workThreadSize;


    @Autowired
    private TcpServerInitializer tcpServerInitializer;

    @PostConstruct
    public void start() throws InterruptedException {
        log.info("im tcp server starting----------------");
        NioEventLoopGroup boss = new NioEventLoopGroup(bossThreadSize);
        NioEventLoopGroup worker = new NioEventLoopGroup(workThreadSize);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    // 服务端可连接队列大小
                    .option(ChannelOption.SO_BACKLOG,10240)
                    // 允许重复使用本地地址和端口
                    .option(ChannelOption.SO_REUSEADDR,true)
                    // 是否禁用nagle算法 简单点说是否批量发送数据 true关闭 false开启。 开启的话可以减少一定的网络开销，但影响消息实时性
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    // 保活开关2h没有数据服务端会发送心跳包
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(tcpServerInitializer);
            serverBootstrap.bind(port);
//            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
//            channelFuture.channel().closeFuture().sync();
        } finally {
//            boss.shutdownGracefully();
//            worker.shutdownGracefully();
        }
        log.info("im tcp server started");
    }

}

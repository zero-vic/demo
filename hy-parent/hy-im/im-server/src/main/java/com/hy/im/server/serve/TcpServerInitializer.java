package com.hy.im.server.serve;

import com.hy.im.codec.decoder.MessageDecoder;
import com.hy.im.codec.encoder.MessageEncoder;
import com.hy.im.server.handle.HeartBeatHandler;
import com.hy.im.server.handle.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName TcpServerInitializer
 * description:
 * yao create 2023年08月14日
 * version: 1.0
 */
@Component
public class TcpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Value("${im.heartBeatTime}")
    private long heartBeatTime;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new MessageDecoder());
        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new IdleStateHandler(0,0,2));
        pipeline.addLast(new HeartBeatHandler(heartBeatTime));
        pipeline.addLast(nettyServerHandler);
    }
}

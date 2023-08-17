package com.hy.im.server.serve;

import com.hy.im.codec.decoder.WebSocketMessageDecoder;
import com.hy.im.codec.encoder.WebSocketMessageEncoder;
import com.hy.im.server.handle.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName WSServerInitializer
 * description:
 * yao create 2023年08月14日
 * version: 1.0
 */
@Component
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {


    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // websocket 基于http协议，所以要有http编解码器
        pipeline.addLast("http-codec", new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        // 几乎在netty中的编程，都会使用到此hanler
        pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new WebSocketMessageDecoder());
        pipeline.addLast(new WebSocketMessageEncoder());
        pipeline.addLast(nettyServerHandler);
    }
}

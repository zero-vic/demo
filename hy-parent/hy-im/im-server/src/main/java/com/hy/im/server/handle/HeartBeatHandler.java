package com.hy.im.server.handle;

import com.hy.im.common.constant.Constants;
import com.hy.im.server.utils.SessionSocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName HeartBeatHandler
 * description: 心跳检测handler
 * yao create 2023年06月28日
 * version: 1.0
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private Long heartBeatTime;

    public HeartBeatHandler(Long heartBeatTime){
        this.heartBeatTime = heartBeatTime;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE){
                log.debug("读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.debug("进入写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("读写空闲");
                Long lastReadTime = (Long) ctx.channel()
                        .attr(AttributeKey.valueOf(Constants.READ_TIME)).get();
                long now = System.currentTimeMillis();
                if(lastReadTime != null && now - lastReadTime > heartBeatTime){
                    //
                    log.info("离线用户session");
                    SessionSocketHolder.offlineUserSession((NioSocketChannel) ctx.channel());
                }
            }
        }


    }
}
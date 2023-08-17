package com.hy.im.server.reciver.process;

import com.hy.im.codec.proto.MessagePack;
import com.hy.im.server.utils.SessionSocketHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName BaseProcess
 * description: 消息处理基础抽象类
 * yao create 2023年06月30日
 * version: 1.0
 */
public abstract class BaseProcess {

    /**
     * 处理之前操作
     */
    public abstract void processBefore();

    /**
     * 消息处理
     * @param messagePack
     */
    public void process(MessagePack messagePack){
        processBefore();
        NioSocketChannel channel = SessionSocketHolder.get(messagePack.getAppId(), messagePack.getToId(), messagePack.getClientType(), messagePack.getImei(),messagePack.getSiteId());
        if(channel!=null){
            channel.writeAndFlush(messagePack);
        }
        processAfter();


    }
    /**
     * 处理之后操作
     */
    public abstract void processAfter();


}

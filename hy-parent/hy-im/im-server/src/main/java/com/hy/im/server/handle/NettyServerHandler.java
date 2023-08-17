package com.hy.im.server.handle;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hy.im.codec.pack.LoginPack;
import com.hy.im.codec.pack.user.LoginAckPack;
import com.hy.im.codec.proto.Message;
import com.hy.im.codec.proto.MessagePack;
import com.hy.im.common.constant.Constants;
import com.hy.im.common.constant.RedisConstants;
import com.hy.im.common.enums.ImConnectStatusEnum;
import com.hy.im.common.enums.command.GroupEventCommand;
import com.hy.im.common.enums.command.MessageCommand;
import com.hy.im.common.enums.command.SystemCommand;
import com.hy.im.common.model.UserClientDto;
import com.hy.im.common.model.UserSession;
import com.hy.im.common.model.message.CheckSendMessageReq;
import com.hy.im.server.publish.MqMessageProducer;
import com.hy.im.server.utils.SessionSocketHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * @ClassName NettyServerHandler
 * description:
 * yao create 2023年06月28日
 * version: 1.0
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    @Resource
    RedissonClient redissonClient;


    private static Integer brokerId = 1000;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Integer command = msg.getMessageHeader().getCommand();
        // 用户登陆的逻辑
        if (command == SystemCommand.LOGIN.getCommand()) {
            LoginPack loginPack = JSON.parseObject(JSON.toJSONString(msg.getMessagePack()), new TypeReference<LoginPack>() {
            }.getType());
            String userId = loginPack.getUserId();
            Integer appId = msg.getMessageHeader().getAppId();
            Integer clientType = msg.getMessageHeader().getClientType();
            String imei = msg.getMessageHeader().getImei();
            String clientImei = clientType + imei;
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(msg.getMessagePack()));
            String siteId = jsonObject.getString(Constants.SITE_ID);
            //为channel设置属性
            ctx.channel().attr(AttributeKey.valueOf(Constants.USER_ID)).set(userId);
            ctx.channel().attr(AttributeKey.valueOf(Constants.APP_ID)).set(appId);
            ctx.channel().attr(AttributeKey.valueOf(Constants.CLIENT_TYPE)).set(clientType);
            ctx.channel().attr(AttributeKey.valueOf(Constants.IMEI)).set(imei);
            ctx.channel().attr(AttributeKey.valueOf(Constants.SITE_ID)).set(siteId);
            ctx.channel().attr(AttributeKey.valueOf(Constants.CLIENT_IMEI)).set(clientImei);

            //存session
            UserSession userSession = new UserSession();
            userSession.setAppId(msg.getMessageHeader().getAppId());
            userSession.setClientType(msg.getMessageHeader().getClientType());
            userSession.setUserId(loginPack.getUserId());
            userSession.setConnectState(ImConnectStatusEnum.ONLINE_STATUS.getCode());
            userSession.setBrokerId(brokerId);
            userSession.setImei(msg.getMessageHeader().getImei());
            userSession.setSiteId(siteId);

            try {
                InetAddress localHost = InetAddress.getLocalHost();
                userSession.setBrokerHost(localHost.getHostAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }


            // 存到redis
            String userSessionKey = RedisConstants.USER_SESSION_CONSTANTS + userId;;
            RMap<String, String> map = redissonClient.getMap(userSessionKey);
            map.put(clientType + ":" + imei, JSONObject.toJSONString(userSession));
            // 存channel
            SessionSocketHolder.put(appId, userId, clientType, (NioSocketChannel) ctx.channel(), imei, siteId);


            // 使用redis的发布订阅模式来通知用户上线, 实现多端登陆
            UserClientDto dto = new UserClientDto();
            dto.setAppId(appId);
            dto.setClientType(clientType);
            dto.setUserId(userId);
            dto.setImei(imei);
            dto.setSiteId(siteId);
            RTopic topic = redissonClient.getTopic(RedisConstants.USER_LOGIN_CHANNEL);
            topic.publish(JSON.toJSONString(dto));
            //  状态变更发送给逻辑层
//            UserStatusChangeNotifyPack userStatusChangeNotifyPack = new UserStatusChangeNotifyPack();
//            userStatusChangeNotifyPack.setAppId(msg.getMessageHeader().getAppId());
//            userStatusChangeNotifyPack.setUserId(loginPack.getUserId());
//            userStatusChangeNotifyPack.setStatus(ImConnectStatusEnum.ONLINE_STATUS.getCode());
//            MqMessageProducer.sendMessage(userStatusChangeNotifyPack,msg.getMessageHeader(), UserEventCommand.USER_ONLINE_STATUS_CHANGE.getCommand());

            //  回复ack给登录方
            MessagePack<LoginAckPack> loginSuccess = new MessagePack<>();
            LoginAckPack loginAckPack = new LoginAckPack();
            loginAckPack.setUserId(loginPack.getUserId());
            loginSuccess.setCommand(SystemCommand.LOGIN_ACK.getCommand());
            loginSuccess.setData(loginAckPack);
            loginSuccess.setImei(msg.getMessageHeader().getImei());
            loginSuccess.setAppId(msg.getMessageHeader().getAppId());
            loginSuccess.setSiteId(siteId);
            ctx.channel().writeAndFlush(loginSuccess);


        } else if (command == SystemCommand.LOGOUT.getCommand()) {
            // 登出
            // 删除usersession 删除redis
            SessionSocketHolder.removeUserSession((NioSocketChannel) ctx.channel());
            //   通知逻辑层已下线

        } else if (command == SystemCommand.PING.getCommand()) {
            // 心跳检测 处理
            // 添加读取时间
            ctx.channel().attr(AttributeKey.valueOf(Constants.READ_TIME)).set(System.currentTimeMillis());
        } else if (command == MessageCommand.MSG_P2P.getCommand() ||
                command == GroupEventCommand.MSG_GROUP.getCommand()) {
            try {
                MqMessageProducer.sendMessage(msg, command);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("tcp 校验聊天信息 出现异常:{}", e.getMessage());
            }
        } else if (command == MessageCommand.MSG_VISITOR.getCommand()) {
            // 访客消息
            try {
                String toId = "";
                CheckSendMessageReq req = new CheckSendMessageReq();
                req.setAppId(msg.getMessageHeader().getAppId());
                req.setCommand(msg.getMessageHeader().getCommand());
                JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(msg.getMessagePack()));
                String fromId = jsonObject.getString("fromId");
                toId = jsonObject.getString("toId");
                log.info("访客消息 toId:{},formId:{},msg:{}", toId, fromId, jsonObject.toJSONString());
                req.setToId(toId);
                req.setFromId(fromId);


                log.info("msg：{},command:{}", msg, command);
                MqMessageProducer.sendMessage(msg, command);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("tcp 校验聊天信息 出现异常:{}", e.getMessage());
            }
        } else {
            // 消息发送给逻辑层
            MqMessageProducer.sendMessage(msg, command);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);

    }
}
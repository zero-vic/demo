## 原生Java使用RabbitMQ

### 1.相关依赖

```xml
<dependencies>
	<dependency>
            <groupId>com.rabbitmq</groupId>
            <artifactId>amqp-client</artifactId>
            <version>5.6.0</version>
     </dependency>
     <dependency>
             <groupId>org.apache.commons</groupId>
             <artifactId>commons-lang3</artifactId>
             <version>3.12.0</version>
      </dependency>
</dependencies>
```

### 2.工具类

```java
/**
 * @ClassName MqFactory
 * description: mq
 * yao create 2023年06月29日
 * version: 1.0
 */
public class MqFactory {

    private static ConnectionFactory factory = null;

    private static ConcurrentHashMap<String,Channel> channelMap = new ConcurrentHashMap<>();

    private static Connection getConnection() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        return connection;
    }


    public static Channel getChannel(String channelName) throws IOException, TimeoutException {
        Channel channel = channelMap.get(channelName);
        if(channel == null){
            channel = getConnection().createChannel();
            channelMap.put(channelName,channel);
        }
        return channel;
    }

    /**
     * 初始化
     * @param config
     */
    public static void init(BootstrapConfig.Rabbitmq config){
        if(factory == null){
            factory = new ConnectionFactory();
            factory.setHost(config.getHost());
            factory.setPort(config.getPort());
            factory.setUsername(config.getUserName());
            factory.setPassword(config.getPassword());
            factory.setVirtualHost(config.getVirtualHost());
        }
    }
}
```

### 3.常用方法

#### 交换机

> **exchangeDeclare** 方法:
>
> 声明一个交换机

```java
public AMQP.Exchange.DeclareOk exchangeDeclare(
    String exchange, BuiltinExchangeType type, boolean durable,
    boolean autoDelete, boolean internal, Map<String, Object> arguments); 

```

>各个参数的意义：
>
>exchange：交换机名称
>
>type：交换机类型。常见的如 fanout、direct、topic
>
>durable：设置是否持久化。 durable 设置为 true 表示持久化
>
>autoDelete：设置是否自动删除。 autoDelete 设置为 true 表示自动删除
>
>internal：设置是否是内置的。如果设置为 true ，则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式。默认为 false。
>
>arguments：其他一些结构化参数，如 x-message-ttl、 x-dead-letter-exchange、x-dead-letter-routing-key等 



> **exchangeDeclarePassive** 方法：
>
> 用来检测相应的交换器是否存在。如果存在则正常返回；如果不存在则抛出异常 404 channel exception ，同时 Channel 也会被关闭。

```java
AMQP.Exchange.DeclareOk exchangeDeclarePassive(String exchange) throws IOException;
```

> exchange：交换机名称



> **exchangeDelete**:
>
> 删除交换机

```java
AMQImpl.Exchange.DeleteOk exchangeDelete(String exchange, boolean ifUnused);
```

> exchange：交换机名称
>
> ifUnused：用来设置是否在交换器没有被使用的情况下删除。如果 `ifUnused`设置为 `true` ，则只有在此交换器没有被使用的情况下才会被删除。如果设置 false，也就是说这个交换器不论在什么情况下都要被删除。即使交换机不存在也不会报错

#### 队列

> queueDeclare:
>
> 声明一个队列

```JAVA
AMQP.Queue.DeclareOk queueDeclare();
AMQImpl.Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments);
```

不带任何参数的 queueDeclare 方法默认创建一个由 RabbitMQ 命名的（类似这种amq.gen-S9h... 的名称，这种队列也称之为匿名队列)、排他的、自动删除的、非持久化的队列

> queue：队列的名称
>
> durable：设置是否持久化。为 true 则设置队列为持久化。持久化的队列会存盘，在服务器重启的时候可以保证不丢失相关信息
>
> exclusive：设置是否排他。为 true 则设置队列为排他的。如果一个队列被声明为排他队列，该队列仅对首次声明它的连接可见，并在连接断开时自动删除。这里需要注意三点：排他队列是基于连接( Connection )可见的，同一个连接的不同信道(Channel)是可以同时访问同一连接创建的排他队列。“首次”是指如果一个连接己经声明了排他队列，其他连接是不允许建立同名的排他队列的，这一个与普通队列不同，即使该队列是持久化的，一旦连接关闭或者客户端退出，该排他队列都会被自动删除。
>
> autoDelete：设置是否自动删除。为 true 则设置队列为自动删除。自动删除的前提是：至少有一个消费者连接到这个队列，之后所有与这个队列连接的消费者都断开时，才会自动删除
>
> arguments：设置队列的其他一些参数，如 x-message-ttl、 x-dead-letter-exchange、x-dead-letter-routing-key等



> **queueDeclarePassive**:
>
> 用来检测相应的队列是否存在。 如果存在正常返回 ，如果不存在则抛出异常： 404 channel exception ，同时Channel 也会被关闭。

```JAVA
AMQImpl.Queue.DeclareOk queueDeclarePassive(String queue);
```

> queue：队列，名称



> **queueDelete**:
>
> 删除队列

```java
AMQImpl.Queue.DeleteOk queueDelete(String queue);
AMQImpl.Queue.DeleteOk queueDelete(String queue, boolean ifUnused, boolean ifEmpty);
```

> queue：队列名称
>
> ifUnused：如果 ifUnused设置为 true ，则只有在此队列在没有被使用的情况下才会被删除，
>
> ifEmpty：ifEmpty设置为 true，表示在队列为空（队列里面没有任何消息堆积）的情况下才能够删除。



> **queuePurge**:
>
> 清除给定队列的消息

```java
AMQImpl.Queue.PurgeOk queuePurge(String queue) ;
```

#### 绑定与解绑

**queueBind**

> 将队列和交换机绑定

```java
AMQImpl.Queue.BindOk queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments);
```

> queue：队列
>
> exchange：交换机
>
> routingKey：用来绑定队列和交换器的路由键
>
> arguments：定义绑定的一些参数

**queueUnbind**

> 将队列和交换器解除绑定

```java
AMQImpl.Queue.UnbindOk queueUnbind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
```

**exchangeBind**

> 将交换器与交换器绑定

```java
AMQImpl.Exchange.BindOk exchangeBind(String destination, String source, String routingKey, Map<String, Object> arguments);
```

> destination：目标交换机
>
> source：源交换机
>
> routingKey：路由键
>
> arguments：额外参数

**exchangeUnbind**

> 将交换器与交换器解除绑定

```java
AMQImpl.Exchange.UnbindOk exchangeUnbind(String destination, String source, String routingKey, Map<String, Object> arguments);
```

#### 发送消息

**basicPublish**

```java
public void basicPublish(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body);
public void basicPublish(String exchange, String routingKey, boolean mandatory, AMQP.BasicProperties props, byte[] body);
public void basicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate, AMQP.BasicProperties props, byte[] body);
```

> exchange：交换机的名称，指明消息需要发送到哪个交换机中，如果设置为空字符串，则消息会被发送到 RabbitMQ 默认的交换器中。
>
> routingKey：路由键，交换机根据路由键将消息存储到相应的队列之中
>
> mandatory：当 mandatory 参数设为 true 时，交换器无法根据自身的类型和路由键找到一个符合条件的队列时，那么 RabbitMQ 会调用 Basic.Return 命令将消息返回给生产者。当 mandatory 参数设置为 false 时，出现上述情形，则消息直接被丢弃
>
> immediate：当 immediate 参数设为 true 时，如果交换器在将消息路由到队列时发现队列上并不存在任何消费者，那么这条消息将不会存入队列中。当与路由键匹配的所有队列都没有消费者时，该消息会通过 Basic.Return 返回至生产者。
>
> props： 消息的基本属性集，其包含 14 个属性成员
>
> body：消息体（ payload ），真正需要发送的消息

props：

```
public static class BasicProperties extends com.rabbitmq.client.impl.AMQBasicProperties {
        private String contentType; //消息类型如(text/plain)
        private String contentEncoding; //字符编码
        private Map<String,Object> headers; //请求头信息
        private Integer deliveryMode;  //消息的投递模式
        private Integer priority;  //优先级
        private String correlationId; //用来关联RPC的请求和响应。
        private String replyTo;  //一般用来命名一个回调queue。
        private String expiration;  //过期时间
        private String messageId;//消息表示符 用于标示消息
        private Date timestamp; //消息发送的时间戳
        private String type;  //消息类型
        private String userId;  //连接到mq的用户名
        private String appId;  //消息的应用程序的表示符  比如你的计算机名称
        private String clusterId;
}
```

#### 消费消息

> RabbitMQ 的消费消息模式分两种：推（ Push ）模式和拉（ Pull ）模式。推模式采用 `Basic.Consume` 进行消费，而拉模式则是调用 Basic.Get 进行消费。

**推模式**

```java
String basicConsume(String queue , Consumer callback); 

String basicConsume(String queue , boolean autoAck, Consumer callback);

String basicConsume(String queue , boolean autoAck , Map<String , Object> 
arguments, Consumer callback); 

String basicConsume(String queue , boolean autoAck , String consumerTag, 
Consumer callback); 

String basicConsume(String queue , boolean autoAck , String consumerTag, 
boolean noLocal , boolean exclusive , Map<String Object> arguments, 
Consumer callback);

```

> queue：队列
>
> autoAck：设置是否自动确认，如果设置 autoAck 为 false ，那么需要显示调用 channel.basicAck 来确认消息己被成功接收。
>
> consumerTag：消费者标签，用来区分多个消费者。
>
> noLocal：设置为 true， 则表示不能将同一个 Connection 中生产者发送的消息传送给这个 Connection 中的消费者。
>
> exclusive：设置是否排他
>
> arguments：设置消费者的其他参数
>
> callback：设置消费者的回调函数。用来处理 RabbitMQ 推送过来的消息。比如DefaultConsumer，使用时需要客户端重写其中的方法。

示例：

```java
channel.basicConsume(RabbitConstants.MESSAGE_SERVICE_2_IM+brokerId,false,new DefaultConsumer(channel){
       @Override
       public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        //  处理消息服务发来的消息
        try {
        String msg = new String(body);
        log.info(" 处理逻辑层发来的消息！ msg: {} ",msg);
        log.info(msg);
      
        channel.basicAck(envelope.getDeliveryTag(),false);
        } catch (IOException e) {
        e.printStackTrace();
        log.error("消息处理异常：{}",e.getMessage());
        channel.basicNack(envelope.getDeliveryTag(),false,false);
        }

       }
});
```

**拉模式**

> 通过 `channel.basicGet` 方法可以单条地获取消息，其返回值是 `GetResponse`。`Channel` 类的 `basicGet` 方法没有其他重载方法。

```java
 GetResponse basicGet(String queue, boolean autoAck);
```

> queue：队列
>
> autoAck：设置 autoAck 为 false ，那么需要显示调用 `channel.basicAck` 来确认消息己被成功接收。

#### 消费端确认与接受

**basicReject**

> Basic.Reject命令一次只能拒绝一条消息，如果想要批量拒绝消息，则可以使用 Basic.Nack 这个命令。

```java
void basicReject(long deliveryTag, boolean requeue);
```

> deliveryTag：消息的编号
>
> requeue：参数设置为 true，则 `RabbitMQ` 会重新将这条消息存入 队列，以便可以发送给下一个订阅的消费者；如果 `requeue` 参数设置为 false，则 `RabbitMQ` 会立即把消息从队列中移除。

**basicNack**

```java
void basicNack(long deliveryTag, boolean multiple, boolean requeue);
```

> deliveryTag：消息的编号
>
> requeue：参数设置为 true，则 `RabbitMQ` 会重新将这条消息存入 队列，以便可以发送给下一个订阅的消费者；如果 `requeue` 参数设置为 false，则 `RabbitMQ` 会立即把消息从队列中移除。
>
> multiple：设置为 false 时，则表示拒绝编号为 `deliveryTag` 的这一条消息，这时候 `basicNack` 方法和 `basicReject` 方法一样， multiple 参数设置为 true 则表示拒绝小于 `deliveryTag` 编号之前所有未被当前消费者确认的消息。

**basicRecover**

> 是否恢复消息到队列
>
> 这个 `channel.basicRecover`方法用来请求 `RabbitMQ` 重新发送还未被确认的消息

```java
Basic.RecoverOk basicRecover() throws IOException;

Basic.RecoverOk basicRecover(boolean requeue) throws IOException;
```

>requeue：`requeue` 参数设置为 true ，则未被确认的消息会被重新加入到队列中，并且尽可能的将消息投递给其他消费者进行消费，而不是自己再次消费。如果，`requeue` 参数设置为 false ，消息会被重新投递给自己。默认情况下，如果不设置 `requeue` 这个参数默认为 true。

**basicAck**

```java
void basicAck(long deliveryTag, boolean multiple) throws IOException;
```

> deliveryTag：消息的编号
>
> multiple：设置为 false 时，则表示确认消费编号为 `deliveryTag` 的这一条消息，该参数为 true 时，则可以一次性确认消费小于等于 `deliveryTag` 值的所有消息。
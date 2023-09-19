## docker安装rabbitmq

### 1.拉取镜像

```
docker pull rabbitmq:3.12-management
```

### 2.创建并运行容器

```
docker run --name rabbitmq --restart=always -p 5672:5672 -p 15672:15672 -d rabbitmq:3.12-management
```

### 3.管理页面测试

ip:15672

默认账号密码：guest/guest

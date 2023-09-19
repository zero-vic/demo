## docker安装nacos

### 1.拉取镜像

```
docker pull nacos/nacos-server:v2.1.0
```

### 2.新建挂载目录

```
# 创建logs目录
mkdir -p /yao/nacos/logs/
 
# 创建配置文件目录
mkdir -p /yao/nacos/conf/
```

### 3.修改目录权限

```
chmod 777 /yao/nacos/logs
chmod 777 /yao/nacos/conf
```

### 4.启动nacos

```
docker run --name nacos2.1.0 -p 8848:8848 -p 9848:9848 -p 9849:9849 --privileged=true --restart=always -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -e PREFER_HOST_MODE=hostname -d nacos/nacos-server:v2.1.0
```

### 5.复制nacos配置

```
docker cp nacos2.1.0:/home/nacos/conf  /yao/nacos
```

### 6.停止及删除nacos容器

```
#停止容器
dokcer stop 容器Id

#删除容器
dokcer rm -f 容器Id
```

### 7.启动nacos并挂载文件

```
docker run --name nacos2.1.0 \
-p 8848:8848 \
-p 9848:9848 \
-p 9849:9849 \
--privileged=true \
--restart=always \
-e JVM_XMS=256m \
-e JVM_XMX=256m \
-e MODE=standalone \
-e PREFER_HOST_MODE=hostname \
-v /nacos/logs:/home/nacos/logs \
-v /yao/nacos/conf:/home/nacos/conf \
-d nacos/nacos-server:v2.1.0
```

### 8.测试

```
ip:8848/nacos
```


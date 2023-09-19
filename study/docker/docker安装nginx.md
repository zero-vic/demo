## docker安装nginx

### 1.拉取nginx镜像

```
docker pull nginx
```

### 2.创建nginx配置文件挂载目录

```
mkdir -p /home/nginx/conf
mkdir -p /home/nginx/log
mkdir -p /home/nginx/html
```

容器中的nginx.conf文件和conf.d文件夹复制到宿主机

```
docker run --name nginx -p 80:80 -d nginx
# 将容器nginx.conf文件复制到宿主机
docker cp nginx:/etc/nginx/nginx.conf /home/nginx/conf/nginx.conf
# 将容器conf.d文件夹下内容复制到宿主机
docker cp nginx:/etc/nginx/conf.d /home/nginx/conf/conf.d
# 将容器中的html文件夹复制到宿主机
docker cp nginx:/usr/share/nginx/html /home/nginx/
```

### 3.关闭nginx容器

```
#找到nginx的容器id
docker ps -a 
#停止容器
docker stop id
#删除容器
docker rm id
#删除正在运行的容器
docker rm -f id
```

### 4.运行容器

```
docker run \
-p 80:80 \
--name nginx \
-v /home/nginx/conf/nginx.conf:/etc/nginx/nginx.conf \
-v /home/nginx/conf/conf.d:/etc/nginx/conf.d \
-v /home/nginx/log:/var/log/nginx \
-v /home/nginx/html:/usr/share/nginx/html \
-d nginx
```

### 5.测试

访问主机ip

![image-20230907171438957](assets/image-20230907171438957.png)
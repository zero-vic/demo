## Linux常用命令

### 解压文件

```shell
tar -zxvf filename.tar.gz
```

### 查看端口情况

```shell
--查看全部端口占用情况
netstat -anp
--查看8080端口占用
netstat -anp | grep 8080
```

### 杀死进程

```shell
kill -9 PID
```


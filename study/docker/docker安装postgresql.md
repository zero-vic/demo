## docker安装postgresql

### 1.拉取指定版本镜像

```
docker pull postgres:13.11
```

### 2.运行容器

```
docker run --name postgres --restart=always -e POSTGRES_PASSWORD=123456 -p 5432:5432 -d postgres:13.11
```

### 3.进入容器

```
docker exec -it 2d1 /bin/bash
```

### 4.链接pgsql

```
 psql -h 127.0.0.1 -U postgres
```

### 5.查看版本

```
select version();
```


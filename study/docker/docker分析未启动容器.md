## docker分析未启动容器结构

容器启动的我们可以使用以下命令进入容器去查看分析

```
docker exec -it 容器id /bin/bash
```

当容器启动失败怎么办呢，我们又想去查看容器启动时生成的日志或者文件

我可以可以把容器直接导出解压分析

```
docker export 容器名 > filename.tar
```


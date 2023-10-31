# Centos安装docker

## 1.安装docker管理工具

```
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

## 2.设置仓库

```
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

## 3.查看docker版本列表

```
yum list docker-ce --showduplicates | sort -r
```

## 4.安装docker

```
sudo yum install docker-ce-24.0.6
```

## 5.启动并加入开机自启

```
sudo systemctl start docker
sudo systemctl status docker
sudo systemctl enable docker
```

## 6.查看docker版本

```
docker -v
```


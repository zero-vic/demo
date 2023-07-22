## jenkins

jenkins官网:https://www.jenkins.io

### 安装要求

```
- 机器要求：
  - 256 MB 内存，建议大于 512 MB
  - 10 GB 的硬盘空间（用于 Jenkins 和 Docker 镜像）
- 需要安装以下软件：
  - Java 8 ( JRE 或者 JDK 都可以)
  - Docker
```

### 安装JDK

1 检索可用包

```
yum search java|grep jdk
```

2 安装

```
yum install java-1.8.0-openjdk
```

### 安装jenkins

1下载jenkins

https://www.jenkins.io/download/

2打开终端进入到下载目录

3运行命令（默认端口8080）

```
java -jar jenkins.war --httpPort=8080
```

初始化后的密码：

首次启动war包会在`/root/.jenkins`生成配置文件

```
Jenkins initial setup is required. An admin user has been created and a password generated.
Please use the following password to proceed to installation:

2f3bbce29c2f477fab3e09a5e1b0f633

This may also be found at: /root/.jenkins/secrets/initialAdminPassword

```

4.访问ip:8080激活jenkins

5.安装官方推荐的插件

6.创建第一个管理员用户

![image-20230528010249875](assets/image-20230528010249875.png)

### 安装插件

![image-20230528011635418](assets/image-20230528011635418.png)

![image-20230528011716871](assets/image-20230528011716871.png)

#### 安装maven插件

![image-20230528012025449](assets/image-20230528012025449.png)

![image-20230528012424204](assets/image-20230528012424204.png)

![image-20230528015848158](assets/image-20230528015848158.png)

安装svn插件

![image-20230529003543910](assets/image-20230529003543910.png)

等待安装完成

![image-20230528012500725](assets/image-20230528012500725.png)

### 配置工具

![image-20230528224648754](assets/image-20230528224648754.png)

#### maven配置

1.配置setting文件

setting 文件阿里源

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<!--
 | This is the configuration file for Maven. It can be specified at two levels:
 |
 |  1. User Level. This settings.xml file provides configuration for a single user,
 |                 and is normally provided in ${user.home}/.m2/settings.xml.
 |
 |                 NOTE: This location can be overridden with the CLI option:
 |
 |                 -s /path/to/user/settings.xml
 |
 |  2. Global Level. This settings.xml file provides configuration for all Maven
 |                 users on a machine (assuming they're all using the same Maven
 |                 installation). It's normally provided in
 |                 ${maven.conf}/settings.xml.
 |
 |                 NOTE: This location can be overridden with the CLI option:
 |
 |                 -gs /path/to/global/settings.xml
 |
 | The sections in this sample file are intended to give you a running start at
 | getting the most out of your Maven installation. Where appropriate, the default
 | values (values used when the setting is not specified) are provided.
 |
 |-->
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <!-- localRepository
   | The path to the local repository maven will use to store artifacts.
   |
   | Default: ${user.home}/.m2/repository
  <localRepository>/path/to/local/repo</localRepository>
  -->
  <localRepository>${user.home}/.m2/repository</localRepository>
  <!-- interactiveMode
   | This will determine whether maven prompts you when it needs input. If set to false,
   | maven will use a sensible default value, perhaps based on some other setting, for
   | the parameter in question.
   |
   | Default: true
  <interactiveMode>true</interactiveMode>
  -->

  <!-- offline
   | Determines whether maven should attempt to connect to the network when executing a build.
   | This will have an effect on artifact downloads, artifact deployment, and others.
   |
   | Default: false
  <offline>false</offline>
  -->

  <!-- pluginGroups
   | This is a list of additional group identifiers that will be searched when resolving plugins by their prefix, i.e.
   | when invoking a command line like "mvn prefix:goal". Maven will automatically add the group identifiers
   | "org.apache.maven.plugins" and "org.codehaus.mojo" if these are not already contained in the list.
   |-->
  <pluginGroups>
    <!-- pluginGroup
     | Specifies a further group identifier to use for plugin lookup.
    <pluginGroup>com.your.plugins</pluginGroup>
    -->
    <pluginGroup>org.mortbay.jetty</pluginGroup>
  </pluginGroups>

  <!-- proxies
   | This is a list of proxies which can be used on this machine to connect to the network.
   | Unless otherwise specified (by system property or command-line switch), the first proxy
   | specification in this list marked as active will be used.
   |-->
  <proxies>
    <!-- proxy
     | Specification for one proxy, to be used in connecting to the network.
     |
    <proxy>
      <id>optional</id>
      <active>true</active>
      <protocol>http</protocol>
      <username>proxyuser</username>
      <password>proxypass</password>
      <host>proxy.host.net</host>
      <port>80</port>
      <nonProxyHosts>local.net|some.host.com</nonProxyHosts>
    </proxy>
    -->
  </proxies>

  <!-- servers
   | This is a list of authentication profiles, keyed by the server-id used within the system.
   | Authentication profiles can be used whenever maven must make a connection to a remote server.
   |-->
  <servers>
    <!-- server
     | Specifies the authentication information to use when connecting to a particular server, identified by
     | a unique name within the system (referred to by the 'id' attribute below).
     | 
     | NOTE: You should either specify username/password OR privateKey/passphrase, since these pairings are 
     |       used together.
     |
    <server>
      <id>deploymentRepo</id>
      <username>repouser</username>
      <password>repopwd</password>
    </server>
    -->
    
    <!-- Another sample, using keys to authenticate.
    <server>
      <id>siteServer</id>
      <privateKey>/path/to/private/key</privateKey>
      <passphrase>optional; leave empty if not used.</passphrase>
    </server>
    -->
    <server>
        <id>releases</id>
        <username>ali</username>
        <password>ali</password>
      </server>
      <server>
        <id>Snapshots</id>
        <username>ali</username>
        <password>ali</password>
      </server>
  </servers>

  <!-- mirrors
   | This is a list of mirrors to be used in downloading artifacts from remote repositories.
   |
   | It works like this: a POM may declare a repository to use in resolving certain artifacts.
   | However, this repository may have problems with heavy traffic at times, so people have mirrored
   | it to several places.
   |
   | That repository definition will have a unique id, so we can create a mirror reference for that
   | repository, to be used as an alternate download site. The mirror site will be the preferred
   | server for that repository.
   |-->
  <mirrors>
    <!-- mirror
     | Specifies a repository mirror site to use instead of a given repository. The repository that
     | this mirror serves has an ID that matches the mirrorOf element of this mirror. IDs are used
     | for inheritance and direct lookup purposes, and must be unique across the set of mirrors.
     |
    <mirror>
      <id>mirrorId</id>
      <mirrorOf>repositoryId</mirrorOf>
      <name>Human Readable Name for this Mirror.</name>
      <url>http://my.repository.com/repo/path</url>
    </mirror>
     -->
    <mirror>
      <!--This sends everything else to /public -->
      <id>nexus</id>
      <mirrorOf>*</mirrorOf> 
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </mirror>
    <mirror>
      <!--This is used to direct the public snapshots repo in the 
          profile below over to a different nexus group -->
      <id>nexus-public-snapshots</id>
      <mirrorOf>public-snapshots</mirrorOf> 
      <url>http://maven.aliyun.com/nexus/content/repositories/snapshots/</url>
    </mirror>
    <mirror>
      <!--This is used to direct the public snapshots repo in the 
          profile below over to a different nexus group -->
      <id>nexus-public-snapshots1</id>
      <mirrorOf>public-snapshots1</mirrorOf> 
      <url>https://artifacts.alfresco.com/nexus/content/repositories/public/</url>
    </mirror>
  </mirrors>

  <!-- profiles
   | This is a list of profiles which can be activated in a variety of ways, and which can modify
   | the build process. Profiles provided in the settings.xml are intended to provide local machine-
   | specific paths and repository locations which allow the build to work in the local environment.
   |
   | For example, if you have an integration testing plugin - like cactus - that needs to know where
   | your Tomcat instance is installed, you can provide a variable here such that the variable is
   | dereferenced during the build process to configure the cactus plugin.
   |
   | As noted above, profiles can be activated in a variety of ways. One way - the activeProfiles
   | section of this document (settings.xml) - will be discussed later. Another way essentially
   | relies on the detection of a system property, either matching a particular value for the property,
   | or merely testing its existence. Profiles can also be activated by JDK version prefix, where a
   | value of '1.4' might activate a profile when the build is executed on a JDK version of '1.4.2_07'.
   | Finally, the list of active profiles can be specified directly from the command line.
   |
   | NOTE: For profiles defined in the settings.xml, you are restricted to specifying only artifact
   |       repositories, plugin repositories, and free-form properties to be used as configuration
   |       variables for plugins in the POM.
   |
   |-->
   <profiles> 
    <profile>
      <id>development</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>http://central</url>
          <releases><enabled>true</enabled><updatePolicy>always</updatePolicy></releases>
          <snapshots><enabled>true</enabled><updatePolicy>always</updatePolicy></snapshots>
        </repository>
      </repositories>
     <pluginRepositories>
        <pluginRepository>
          <id>central</id>
          <url>http://central</url>
          <releases><enabled>true</enabled><updatePolicy>always</updatePolicy></releases>
          <snapshots><enabled>true</enabled><updatePolicy>always</updatePolicy></snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
    <profile>
      <!--this profile will allow snapshots to be searched when activated-->
      <id>public-snapshots</id>
      <repositories>
        <repository>
          <id>public-snapshots</id>
          <url>http://public-snapshots</url>
          <releases><enabled>false</enabled></releases>
          <snapshots><enabled>true</enabled><updatePolicy>always</updatePolicy></snapshots>
        </repository>
      </repositories>
     <pluginRepositories>
        <pluginRepository>
          <id>public-snapshots</id>
          <url>http://public-snapshots</url>
          <releases><enabled>false</enabled></releases>
          <snapshots><enabled>true</enabled><updatePolicy>always</updatePolicy></snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
 
   <activeProfiles>
    <activeProfile>development</activeProfile>
    <activeProfile>public-snapshots</activeProfile>
   </activeProfiles>

  <!-- activeProfiles
   | List of profiles that are active for all builds.
   |
  <activeProfiles>
    <activeProfile>alwaysActiveProfile</activeProfile>
    <activeProfile>anotherAlwaysActiveProfile</activeProfile>
  </activeProfiles>
  -->
</settings>
```

![](assets/image-20230528225311907.png)

配置maven路径

![image-20230528225647347](assets/image-20230528225647347.png)

#### 配置jdk

![image-20230529003144985](assets/image-20230529003144985.png)

### 新建一个项目

1.新建item

![image-20230528012638288](assets/image-20230528012638288.png)

2.构建一个maven项目

![image-20230528012747704](assets/image-20230528012747704.png)

3.在svn添加项目地址

![image-20230529005517459](assets/image-20230529005517459.png)

4.设置pom

![image-20230529005937674](assets/image-20230529005937674.png)

5.点击构建

![image-20230529020018714](assets/image-20230529020018714.png)6.构建成功

![image-20230529015848801](assets/image-20230529015848801.png)

7.找到jar包并运行看是否有问题

![image-20230529020140290](assets/image-20230529020140290.png)

### 构建好后直接发送到测试服务器上并运行

1.在系统配置里配置publish server

![image-20230529021055910](assets/image-20230529021055910.png)

2.新增一台 ssh server

![image-20230529021248206](assets/image-20230529021248206.png)

![image-20230529021453674](assets/image-20230529021453674.png)

3.测试能否联通

![image-20230529021645223](assets/image-20230529021645223.png)

4.配置项目

![image-20230529020844194](assets/image-20230529020844194.png)

![image-20230529023740007](assets/image-20230529023740007.png)

构建测试

![image-20230529031349624](assets/image-20230529031349624.png)

### 解决jenkins exec command 不执行的问题

新增如下两行代码

![image-20230529031538103](assets/image-20230529031538103.png)

```bash
echo 'JAVA_HOME is $JAVA_HOME'
echo 'PATH is $PATH'
##begin
source /etc/profile
BUILD_ID=dontKillMe
##end
nohup java -jar /root/test/jenkins-demo-0.0.1-SNAPSHOT.jar > mylog.log 2>&1 &
```

### 邮箱授权码

``` 
wzlctuctonhfehga
```

### 配置邮箱接受构建通知

1.系统配置里配置发件者地址

![image-20230603202721931](assets/image-20230603202721931-16858647324861.png)

2.配置SMTP服务器地址

![image-20230603204155117](assets/image-20230603204155117-16858647324862.png)

![image-20230603203434026](assets/image-20230603203434026-16858647324863.png)

配置主题和内容

![image-20230603203542588](assets/image-20230603203542588-16858647324874.png)

配置触发器

![image-20230603203645033](assets/image-20230603203645033-16858647324875.png)

配置邮件通知

![image-20230603204232380](assets/image-20230603204232380-16858647324876.png)

配置项目邮件通知

![image-20230603210134443](assets/image-20230603210134443-16858647324877.png)

配置触发器，以及要通知的邮箱

![image-20230603210357964](assets/image-20230603210357964-16858647324878.png)

### 配置运行前清理

![image-20230603210919000](assets/image-20230603210919000-16858647324879.png)

配置要执行的脚本文件

![image-20230603211019947](assets/image-20230603211019947-168586473248710.png)

编写脚本文件

```shell
#!/bin/bash

#删除历史数据
rm -rf test

appname=$1
#获取传入的参数
echo "arg:$1"


#获取正在运行的jar包pid
pid=`ps -ef | grep $1 | grep 'java -jar' | awk '{printf $2}'`

echo $pid

#如果pid为空，提示一下，否则，执行kill命令
if [ -z $pid ];
#使用-z 做空值判断
        then
                echo "$appname not started"

        else
               kill -9 $pid
                echo "$appname stoping...."

check=`ps -ef | grep -w $pid | grep java`
if [ -z $check ];

        then
                echo "$appname pid:$pid is stop"
        else
                echo "$appname stop failed"

fi


fi
```

根据脚本重新配置

![image-20230603211536917](assets/image-20230603211536917-168586473248711.png)

### 几种构建方式

- 快照依赖构建/Build whenever a SNAPSHOT dependency is built
  - 当依赖的快照被构建时执行本job
- 触发远程构建 (例如,使用脚本)
  - 远程调用本job的restapi时执行本job
- job依赖构建/Build after other projects are built
  - 当依赖的job被构建时执行本job
- 定时构建/Build periodically
  - 使用cron表达式定时构建本job
- 向GitHub提交代码时触发Jenkins自动构建/GitHub hook trigger for GITScm polling
  - Github-WebHook出发时构建本job
- 定期检查代码变更/Poll SCM
  - 使用cron表达式定时检查代码变更，变更后构建本job





## 流水线 pipeline

流水线既能作为任务的本身，也能作为Jenkinsfile

使用流水线可以让我们的任务从ui手动操作，转换为代码化，像docker的dockerfile一样，从shell命令到配置文件，更适合大型项目，可以让团队其他开发者同时参与进来，同时也可以编辑开发Jenkinswebui不能完成的更复杂的构建逻辑，作为开发者可读性也更好。





### 完整语法

5个必备的组成部分

```
pipeline：整条流水线
agent：指定执行器
stages：所有阶段
stage：某一阶段，可有多个
steps：阶段内的每一步，可执行命令
```



### 测试脚本

#### 基础框架

```
pipeline {
    agent any

    stages {
        stage('拉取代码') {
            steps {
            
                echo '拉取代码完成'
               
            }

        }
        stage('执行构建') {
            steps {
                echo '执行构建完成'


            }

        }
    }
    
    post {
        
        always {
            
            echo "完成"
            
        }
        
        failure {
            
            echo "失败"
        }
    }
}
```

#### 阶段视图 Stage View

#### blue ocean可视化界面

全新的流水线控制ui，可重复执行某阶段代码

插件中心搜索blue ocean安装即可

#### post

流水线完成后可执行的任务

- always 无论流水线或者阶段的完成状态。
- changed 只有当流水线或者阶段完成状态与之前不同时。
- failure 只有当流水线或者阶段状态为"failure"运行。
- success 只有当流水线或者阶段状态为"success"运行。
- unstable 只有当流水线或者阶段状态为"unstable"运行。例如：测试失败。
- aborted 只有当流水线或者阶段状态为"aborted "运行。例如：手动取消。

#### agent

可以指定执行节点

label 指定运行job的节点标签

any 不指定，由Jenkins分配

```
pipeline {
    agent {
        node {
            label "jenkins-02"
        }
        
    }

    stages {
        stage('拉取代码') {
            steps {
          
                sh """
                    sleep 10
                            
                   """

                echo '拉取代码完成'
               
            }

        }
        stage('执行构建') {
            steps {
                echo '执行构建完成'


            }

        }
    }
    
    post {
        
        always {
            
            echo "完成"
            
        }
        
        failure {
            
            echo "失败"
        }
    }
}

```



### pipeline中执行自动化构建

```
pipeline {
    agent any

    tools {
        
        maven "maven3"
        
    }
    stages {
        stage("拉取代码") {
            steps {
                
                
                git branch: 'main', credentialsId: 'gitlab', url: 'http://192.168.44.103/root/java-project.git'
                echo '拉取成功'
            }
        }

        stage("执行构建") {
            steps {
                
            //    sh "mvn --version"
                sh """ 
                cd demo-1
                
                mvn clean package
                """
                
                echo '构建完成'
            }

        }
        
        
        stage("clean test server"){
            
            steps{
                
sshPublisher(publishers: [sshPublisherDesc(configName: 'testserver', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '''rm -rf *

docker stop demo
docker rm demo
docker rmi demo
''', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '/root')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }
        }
        
        
        
        
        
        stage("发送jar包到测试服务器") {
            steps {
                
                sshPublisher(publishers: [sshPublisherDesc(configName: 'testserver', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/jarfile', remoteDirectorySDF: false, removePrefix: 'demo-1/target', sourceFiles: '**/demo*.jar'), sshTransfer(cleanRemote: false, excludes: '', execCommand: '''docker build -t demo .
docker run -d -p 8080:8080 --name demo demo''', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/', remoteDirectorySDF: false, removePrefix: 'demo-1/docker', sourceFiles: 'demo-1/docker/dockerfile')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
                
                
                echo 'jar send over!'
            }

        }

    }
}

```



#### 声明式流水线

好处

- 更像是在Jenkins web ui中的操作
- 可读性比较高
- 可以使用blue ocean自动生成
- 支持语法检查

坏处

- 代码逻辑能力比脚本式弱，不能完成特别复杂的任务	



#### 脚本式流水线

好处

- 更少的代码和弱规范要求
- 更灵活的自定义代码操作
- 不受约束，可以构建特别复杂的工作流和流水线

坏处

- 读写对编程要求比较高
- 比声明式流水线代码更复杂

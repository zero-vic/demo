## ShardingSphere

Apache ShardingSphere 是一款开源的分布式数据库生态项目，由 JDBC 和 Proxy 两款产品组成。其核心采用微内核+可插拔架构，通过插件开放扩展功能。它提供多源异构数据库增强平台，进而围绕其上层构建生态。

官网地址:https://shardingsphere.apache.org/index_zh.html

文档地址:https://shardingsphere.apache.org/document/5.2.0/cn/overview/

### ShardingSphere-JDBC

ShardingSphere-JDBC 定位为轻量级 Java 框架，在 Java 的 JDBC 层提供的额外服务。 它使用客户端直连数据库，以 jar 包形式提供服务，无需额外部署和依赖，可理解为增强版的 JDBC 驱动，完全兼容 JDBC 和各种 ORM 框架。

- 适用于任何基于 JDBC 的 ORM 框架，如：JPA, Hibernate, Mybatis, Spring JDBC Template 或直接使用 JDBC；
- 支持任何第三方的数据库连接池，如：DBCP, C3P0, BoneCP, HikariCP 等；
- 支持任意实现 JDBC 规范的数据库，目前支持 MySQL，PostgreSQL，Oracle，SQLServer 以及任何可使用 JDBC 访问的数据库。

![image-20230601090229895](assets\image-20230601090229895.png)

### ShardingSphere-Proxy

ShardingSphere-Proxy 定位为透明化的数据库代理端，通过实现数据库二进制协议，对异构语言提供支持。 目前提供 MySQL 和 PostgreSQL 协议，透明化数据库操作，对 DBA 更加友好。

- 向应用程序完全透明，可直接当做 MySQL/PostgreSQL 使用；
- 兼容 MariaDB 等基于 MySQL 协议的数据库，以及 openGauss 等基于 PostgreSQL 协议的数据库；
- 适用于任何兼容 MySQL/PostgreSQL 协议的的客户端，如：MySQL Command Client, MySQL Workbench, Navicat 等.

![image-20230601090345732](assets\image-20230601090345732.png)

### 逻辑表

> 逻辑表是指具有相同数据结构的水平拆分表的逻辑名称
>
> 比如我们将订单表`t_user` 分表拆分成 `t_user_0` ··· `t_user_9`等10张表，这时我们的数据库中已经不存在 `t_user`这张表，取而代之的是若干的`t_user_n`表
>
> 此时`t_user`就是这些拆分表的逻辑表

### 真实表

> 真实表就是数据库中真实存在的物理表比如`t_user_0`

### 广播表

> 广播表是一类特殊的表，其表结构和数据在所有分片数据源中均完全一致。与拆分表相比，广播表的数据量较小、更新频率较低，通常用于字典表或配置表等场景。由于其在所有节点上都有副本，因此可以大大降低`JOIN`关联查询的网络开销，提高查询效率

**广播表的特点**：

- 在所有分片数据源中，广播表的数据完全一致。因此，对广播表的操作（如插入、更新和删除）会实时在每个分片数据源中执行一遍，以保证数据的一致性。
- 对于广播表的查询操作，仅需要在任意一个分片数据源中执行一次即可。
- 与任何其他表进行JOIN操作都是可行的，因为由于广播表的数据在所有节点上均一致，所以可以访问到任何一个节点上的相同数据。

### 单表

>单表指所有的分片数据源中仅唯一存在的表（没有分片的表），适用于数据量不大且无需分片的表。



## ShardingSphere JDBC 读写分离

### 配置读写分离

```xml
spring:
  shardingsphere:
    #    mode:
    #      type: Standalone # 运行模式配置 可选配置：Standalone、Cluster
    #      repository:
    #        type: File # 持久化仓库类型
    #        props:
    #          path: .shardingsphere # 持久化仓库所需属性
    #      overwrite: false # 是否使用本地配置覆盖持久化配置
    datasource:
      names: ds_0, ds_1, ds_2 # 配置真实数据源
      ds_0: # 配置第 1 个数据源
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: org.postgresql.Driver
        jdbc-url: jdbc:postgresql://localhost:5432/test
        username: postgres
        password: 123456
      ds_1: # 配置第 2 个数据源
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: org.postgresql.Driver
        jdbc-url: jdbc:postgresql://localhost:5432/test2
        username: postgres
        password: 123456
      ds_2: # 配置第 3 个数据源
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: org.postgresql.Driver
        jdbc-url: jdbc:postgresql://localhost:5432/test3
        username: postgres
        password: 123456
    props:
      sql:
        show: true # 开启sql打印

    rules:
      readwrite-splitting: # 读写分离配置
        data-sources:
          readwrite_ds: # 读写分离逻辑数据源名称
            static-strategy: # 读写分离类型
              write-data-source-name: ds_0 # 写库数据源配置
              read-data-source-names: # 读库数据源名称，多个从数据源用逗号分隔
                - ds_1
                - ds_2
            load-balancer-name: random # 负载均衡算法名称
        load-balancers: #负载均衡配置
          random:
            type: RANDOM # 负载均衡类型





mybatis-plus:
  configuration:
    map-underscore-to-camel-case: false # 关闭驼峰命名规则
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

### 编写测试类

```java
	// 测试写入主库
	@Test
    void contextLoads() {
        User user = new User();
        user.setUsername("testYao");
        userService.save(user);
    }
	// 测试读从库 负载均衡
    @Test
    void findContextLoads(){
        Long id = 111L;
        User user = userService.getById(id);
        System.out.println(user);
    }
```

### 事务测试

> 为了保证主从库间的事务一致性，避免跨服务的分布式事务，ShardingSphere-JDBC的`主从模型中，事务中的数据读写均用主库`
>
> 不添加@Transactional：insert对主库操作，select对从库操作
>
> 添加@Transactional：则insert和select均对主库操作
>
> **注意：**在JUnit环境下的@Transactional注解，默认情况下就会对事务进行回滚（即使在没加注解@Rollback，也会对事务回滚）

```java
    @Test
    @Transactional
    void findContextLoads(){
        Long id = 1664516855462125569L;
        User user = userService.getById(id);
        System.out.println(user);
    }
```

### 负载均衡算法

>ShardingSphere 内置提供了多种负载均衡算法，具体包括了轮询算法、随机访问算法和权重访问算法。内置算法也提供了扩展方式，用户可以基于 SPI 接口实现符合自己业务需要的负载均衡算法。

算法文档地址：

https://shardingsphere.apache.org/document/5.2.0/cn/user-manual/common-config/builtin-algorithm/load-balance/

## SpringBoot+mybatisplus整合ShardingSphere-JDBC

### 版本说明

```
springboot：2.7.0
mybatisplus：3.3.0
shardingsphere：5.2.0	
mysql：8.0
```

### 需求说明

> 根据日期进行进行水平分表

### 添加依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.hy</groupId>
    <artifactId>demo-shardingsphere</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo-shardingsphere</name>
    <description>demo-shardingsphere</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-web</artifactId>-->
<!--        </dependency>-->
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
            <version>5.2.0</version>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.0</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.5.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.7.0</version>
            </plugin>
        </plugins>
    </build>

</project>

```

### 创建数据表

> 多创建几张相同的数据表月份不同即可
>
> **表的数量要根据分片的规则来创建，否则查询会出错**

```sql
CREATE TABLE `t_user_2023_01` (
  `uid` bigint NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
```

### 创建实体

```java
@TableName("t_user")
@Data
public class User implements Serializable {
    @TableId(value = "uid",type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("user_name")
    private String userName;
    @TableField("create_time")
    private LocalDateTime cTime;
}
```

### 创建mapper

```java
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```

### 创建接口以及实现类

```java
public interface IUserService extends IService<User> {
}

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
```

### 配置mybatisplus分页插件

```java
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     * 
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
```



### 编写配置文件

```yaml


spring:
  shardingsphere:
#    mode:
#      type: Standalone # 运行模式配置 可选配置：Standalone、Cluster
#      repository:
#        type: File # 持久化仓库类型
#        props:
#          path: .shardingsphere # 持久化仓库所需属性
#      overwrite: false # 是否使用本地配置覆盖持久化配置
    datasource:
      names: ds_0, ds_1 # 配置真实数据源
      ds_0: # 配置第 1 个数据源
        type: com.zaxxer.hikari.HikariDataSource
        driverClassName: com.mysql.cj.jdbc.Driver
        jdbcUrl: jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8
        username: root
        password: 123456
      ds_1: # 配置第 2 个数据源
        type: com.zaxxer.hikari.HikariDataSource
        driverClassName: com.mysql.cj.jdbc.Driver
        jdbcUrl: jdbc:mysql://localhost:3306/test2?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8
        username: root
        password: 123456
    props:
      sql:
        show: true # 开启sql打印

    rules:
      sharding:
        sharding-algorithms: # 分片算法配置
          database-inline:
            type: INLINE #算法类型
            props:
              #  分片算法属性配置
              algorithm-expression: ds_$->{tid % 2}
          t_user_inline:
            type: INLINE
            props:
              algorithm-expression: sys_user_0
          time_inline:  # 时间范围分片算法配置
            type: INTERVAL
            props:
              datetime-pattern: "yyyy-MM-dd HH:mm:ss" #分片键的时间戳格式，必须遵循 Java DateTimeFormatter 的格式。例如：yyyy-MM-dd HH:mm:ss
              sharding-suffix-pattern: "yyyy_MM" #分片数据源或真实表的后缀格式，必须遵循 Java DateTimeFormatter 的格式
              datetime-lower: "2023-01-01 00:00:00"#时间分片下界值
              datetime-upper: "2023-10-30 00:00:00"#时间分片上界值
              datetime-interval-amount: 1 #分片键时间间隔，超过该时间间隔将进入下一分片
              datetime-interval-unit: MONTHS #分片键时间间隔单位，必须遵循 Java ChronoUnit 的枚举值。例如：MONTHS
        default-key-generate-strategy: # 默认分布式序列策略
          column: id
          key-generator-name: snowflake
        key-generators: # 分布式序列算法配置
          snowflake:
            type: SNOWFLAKE
            props:
              worker-id: 1
        tables: # 标准分片表配置 sys_user_0 表的分库配置
          sys_user_0:
            actual-data-nodes: ds_$->{0..1}.sys_user_0
            database-strategy: # 分库策略
              standard:
                sharding-column: tid # 分片列名称
                sharding-algorithm-name: database-inline  # 分片算法名称
            table-strategy:  # 分表策略
              standard:
                # 分表列名称
                sharding-column: tid
                # 分表算法名称
                sharding-algorithm-name: t_user_inline
            keyGeneratorColumnName: tid
            key-generate-strategy: #分布式序列策略配置
              column: tid # 分布式序列列名称
              key-generator-name: snowflake # 分布式序列算法名称
              props:  # 雪花算法的worker-id  机器为标识 0-1024
                worker-id: 1
          t_user: # t_user 水平分表配置
            logicTable: t_user # 逻辑表 
            # 行列表达式 使用的 Groovy 语法
            actual-data-nodes: ds_0.t_user_${2022..2030}_${(1..12).collect{t ->t.toString().padLeft(2,'0')}}
            table-strategy:
              standard:
                sharding-column: create_time # 拆分列
                sharding-algorithm-name: time_inline # 配置算法名
            key-generate-strategy: # 主键生成策略
              column: uid  
              key-generator-name: snowflake

mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

```

### 编写测试类

```java
@SpringBootTest
class DemoShardingsphereApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {
        for (int i = 0; i <10;i++ ) {
                User user = new User();
                LocalDateTime localDateTime = LocalDateTime.now().plusMonths(3);
                user.setCTime(localDateTime);
                user.setUserName("sharding"+i);
                userService.save(user);

           }
    }
}
```

### 运行测试

> 可以多运行几次，不用月份数据看看是否插入到对应的数据表中

## SpringBoot+MybatisPlus整合动态数据源和shardingSphere JDBC

### 版本说明

```
springboot：2.7.0
mybatisplus：3.3.0
shardingsphere：5.2.0	
mysql：8.0
dynamic-datasource：3.6.1
```

### 添加依赖

```xml
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
            <version>5.2.0</version>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.0</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.5.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>3.6.1</version>
        </dependency>
```

### 配置mybatisplus分页插件

```java
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return MybatisPlusInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
```

### 多数据源和Sharding jdbc的配置

```java
@Configuration
public class MyDataSourceConfiguration {
    
    @Autowired
    private DynamicDataSourceProperties properties;
    
    @Autowired
    private DataSource shardingSphereDataSource;

    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new AbstractDataSourceProvider() {
            @Override
            public Map<String, DataSource> loadDataSources() {
                Map<String, DataSource> dataSourceMap = new HashMap<>();
                //把shardingSphereDataSource 加入多数据源，到时候使用的时候就可以@DS("shardingSphere")
                dataSourceMap.put("shardingSphere", shardingSphereDataSource);
                return dataSourceMap;
            }
        };
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrict(properties.getStrict());
        dataSource.setStrategy(properties.getStrategy());
        dataSource.setP6spy(properties.getP6spy());
        dataSource.setSeata(properties.getSeata());
        return dataSource;
    }

}
```

### 创建数据表

> 在两库分别创建实体表
>
> 还有分片的表

```sql
# 测试多数据源的表
CREATE TABLE "public"."t_test" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  CONSTRAINT "t_test_pkey" PRIMARY KEY ("id")
)
# 测试分片的表
CREATE TABLE "public"."t_user_2023_01" (
  "u_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(255) COLLATE "pg_catalog"."default",
  CONSTRAINT "t_user_2023_01_pkey" PRIMARY KEY ("u_id")
)
```

### 创建实体

```java
@TableName("t_user")
@Data
public class User implements Serializable {
    @TableId(value = "uid",type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("user_name")
    private String userName;
    @TableField("create_time")
    private LocalDateTime cTime;
}

@TableName("t_test")
@Data
public class Test {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("name")
    private String name;

}
```

### 创建mapper

```java
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```

### 创建接口以及实体类

> 这里是使用的@DS注解来切换数据源的，注解的值是数据源的名字

```java
public interface ITestService extends IService<Test> {

    public Test getMasterTest(Long id);
    public Test getSlaveTest(Long id);
}

@Service
@DS("master")
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {
    @Override
    public Test getMasterTest(Long id) {
        return this.getById(id);
    }

    @Override
    @DS("test")
    public Test getSlaveTest(Long id) {
        return this.getById(id);
    }
}
public interface IUserService extends IService<User> {
    IPage<User> getList(Long pageNum,Long pageSize);
}

@Service
@DS("shardingSphere")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public IPage<User> getList(Long pageNum, Long pageSize) {
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().gt(User::getCTime, LocalDateTime.now().plusDays(-20));
        return this.page(page,queryWrapper);
    }
}
```

### 编写配置文件

```yaml
spring:
  datasource:
    dynamic:
      primary: master  #设置默认的数据源或者数据源组,默认值即为master
      strict: false #严格匹配数据源,默认false. true未匹配到指定数据源时抛异常,false使用默认数据源
      datasource:
        master:
          type: com.zaxxer.hikari.HikariDataSource
          driver-class-name: org.postgresql.Driver
          url: jdbc:postgresql://localhost:5432/test
          username: postgres
          password: 123456
        test:
          type: com.zaxxer.hikari.HikariDataSource
          driver-class-name: org.postgresql.Driver
          url: jdbc:postgresql://localhost:5432/test2
          username: postgres
          password: 123456
  shardingsphere:
#    mode:
#      type: Standalone # 运行模式配置 可选配置：Standalone、Cluster
#      repository:
#        type: File # 持久化仓库类型
#        props:
#          path: .shardingsphere # 持久化仓库所需属性
#      overwrite: false # 是否使用本地配置覆盖持久化配置
    datasource:
      names: ds_0, ds_1 # 配置真实数据源
      ds_0: # 配置第 1 个数据源
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: org.postgresql.Driver
        url: jdbc:postgresql://localhost:5432/test
        username: postgres
        password: 123456
      ds_1: # 配置第 2 个数据源
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: org.postgresql.Driver
        url: jdbc:postgresql://localhost:5432/test2
        username: postgres
        password: 123456
    props:
      sql:
        show: true # 开启sql打印

    rules:
      sharding:
        sharding-algorithms: # 分片算法配置
          database-inline:
            type: INLINE #算法类型
            props:
              #  分片算法属性配置
              algorithm-expression: ds_$->{tid % 2}
          t_user_inline:
            type: INLINE
            props:
              algorithm-expression: sys_user_0
          time_inline:  # 时间范围分片算法配置
            type: INTERVAL
            props:
              datetime-pattern: "yyyy-MM-dd HH:mm:ss" #分片键的时间戳格式，必须遵循 Java DateTimeFormatter 的格式。例如：yyyy-MM-dd HH:mm:ss
              sharding-suffix-pattern: "yyyy_MM" #分片数据源或真实表的后缀格式，必须遵循 Java DateTimeFormatter 的格式
              datetime-lower: "2023-05-01 00:00:00"#时间分片下界值
              datetime-upper: "2023-10-30 00:00:00"#时间分片上界值
              datetime-interval-amount: 1 #分片键时间间隔，超过该时间间隔将进入下一分片
              datetime-interval-unit: MONTHS #分片键时间间隔单位，必须遵循 Java ChronoUnit 的枚举值。例如：MONTHS
        default-key-generate-strategy: # 默认分布式序列策略
          column: id
          key-generator-name: snowflake
        key-generators: # 分布式序列算法配置
          snowflake:
            type: SNOWFLAKE
            props:
              worker-id: 1
        tables: # 标准分片表配置
          sys_user_0:
            actual-data-nodes: ds_$->{0..1}.sys_user_0
            database-strategy: # 分库策略
              standard:
                sharding-column: tid # 分片列名称
                sharding-algorithm-name: database-inline  # 分片算法名称
            table-strategy:  # 分表策略
              standard:
                # 分表列名称
                sharding-column: tid
                # 分表算法名称
                sharding-algorithm-name: t_user_inline
            keyGeneratorColumnName: tid
            key-generate-strategy: #分布式序列策略配置
              column: tid # 分布式序列列名称
              key-generator-name: snowflake # 分布式序列算法名称
              props:  # 雪花算法的worker-id  机器为标识 0-1024
                worker-id: 1
          t_user:
            logicTable: t_user
            actual-data-nodes: ds_0.t_user_2023_${(1..12).collect{t ->t.toString().padLeft(2,'0')}}
            table-strategy:
              standard:
                sharding-column: create_time
                sharding-algorithm-name: time_inline
            key-generate-strategy:
              column: uid
              key-generator-name: snowflake
## mybatis sql日志打印
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
logging:
  level:
    com.baomidou: debug
    org.apache.shardingsphere: debug

```

### 编写测试类

```java
// 测试切换数据源
@Test
void dynamicContext(){
    Long id = 123456L;
    com.hy.demo.entity.Test masterTest = testService.getMasterTest(id);
    com.hy.demo.entity.Test slaveTest = testService.getSlaveTest(id);
    System.out.println("___________________________________");
    System.out.println(masterTest);
    System.out.println(slaveTest);
}
//测试分页
@Test
void shardingPage(){
    IPage<User> page = userService.getList(1L, 10L);
    System.out.println("___________________________________");
    System.out.println("--------------------"+page.getRecords());
}
//测试分片
@Test
void addSharding(){
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    for (int i = 0; i <5;i++ ) {
        User user = new User();
        LocalDateTime localDateTime = LocalDateTime.now().plusMonths(-1);
        user.setCTime(localDateTime);
        user.setUserName("shardingTe"+i);
        userService.save(user);
    }
}
```

### 测试结果

```
dynamicContext()
___________________________________
Test(id=123456, name=master)
Test(id=123456, name=test2)

shardingPage()
___________________________________
--------------------[User(id=1664091977902776321, userName=sharding0, cTime=2023-06-01T10:10:19.473), User(id=1664092270707085313,

```

![image-20230602094915668](assets\image-20230602094915668.png)
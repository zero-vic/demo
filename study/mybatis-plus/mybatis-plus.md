## MyBatis-plus简介

官网地址：https://baomidou.com/

MyBatis-Plus (opens new window)（简称 MP）是一个 MyBatis (opens new window)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

## MyBatis-plus特性

- 无侵入：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- 损耗小：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
- 强大的 CRUD 操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- 支持 Lambda 形式调用：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
- 支持多种数据库：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer2005、SQLServer 等多种数据库
- 支持主键自动生成：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
- 支持 XML 热加载：Mapper 对应的 XML 支持热加载，对于简单的 CRUD 操作，甚至可以无 XML 启动
- 支持 ActiveRecord 模式：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
- 支持自定义全局通用操作：支持全局通用方法注入（ Write once, use anywhere ）
- 支持关键词自动转义：支持数据库关键词（order、key…）自动转义，还可自定义关键词
- 内置代码生成器：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
- 内置分页插件：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
- 内置性能分析插件：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
- 内置全局拦截插件：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作
- 内置 Sql 注入剥离器：支持 Sql 注入剥离，有效预防 Sql 注入攻击

## Mybatis-plus入门案例

数据表

```sql
CREATE TABLE `t_user` (
  `u_id` bigint NOT NULL,
  `user_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
```

创建一个基础的springboot工程

pom依赖

```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
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
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.3.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.31</version>
        </dependency>
```

配置文件

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456

mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

实体类

```java
@Data
@TableName("t_user")
public class User {
    @TableId(value = "u_id",type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("user_name")
    private String username;
    @TableField("create_time")
    private LocalDateTime createTime;

}
```

mapper

```java
public interface UserMapper extends BaseMapper<User> {
}
```

启动类

```java
@SpringBootApplication
@MapperScan("com.example.demomybatisplus.user")
public class DemoMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisPlusApplication.class, args);
    }

}
```

测试类

```java
@SpringBootTest
class DemoMybatisPlusApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        System.out.println(users);
    }

}
```

测试结果语句

```
==>  Preparing: SELECT u_id AS id,user_name,create_time FROM t_user
==> Parameters: 
<==    Columns: id, user_name, create_time
<==        Row: 1554556561262, yao, null
<==        Row: 1663730446828609537, yao  0, null
```

## Mybatis-plus增删查改

添加

```java
        User  user = new User();
        user.setUsername("yao-test");
        user.setCreateTime(LocalDateTime.now());
        int id = userMapper.insert(user);
        System.out.println(user);
// 这里使用了主键自动生成策略
// 插入之后id回自动回填
User(id=1687725496733626369, username=yao-test, createTime=2023-08-05T15:21:29.471)
```

更新

```
// 根据条件进行更新数据 
userMapper.update(user,new QueryWrapper<>());
// 根据实体的id进行更新
userMapper.updateById(user);
```

删除

```java
        // 根据queryWrapper 来删除
        userMapper.delete(new QueryWrapper<>());
        // 根据id来删除
        userMapper.deleteById(id);
        // 根据id批量删除
        userMapper.deleteBatchIds(Arrays.asList(1L,2L));
        // 根据实体删除
        userMapper.deleteById(user);
        // 根据map构造条件删除
        userMapper.deleteByMap((Map<String, Object>) new HashMap<>().put("u_id",1L));
```

查询

分页查询的时候需要配置分页插件，后面再讲

```java
        // 根据构造条件查询一条记录
        userMapper.selectOne(new QueryWrapper<>());
        // 查询所有记录
        userMapper.selectList(new QueryWrapper<>());
        // 根据构造条件查询一条记录
        userMapper.selectById(id);
        // 分页查询
        long pageNum = 1L;
        long pageSize = 10L;
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>());
```

## Mybatis-plus主键生成策略

当前版本是3.5的版本常用的是ASSIGN_ID（雪花算法）ASSIGN_UUID（UUID）

在实体类的@TableId注解中指定id生成类型

```java
@Data
@TableName("t_user")
public class User {
    @TableId(value = "u_id",type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("user_name")
    private String username;
    @TableField("create_time")
    private LocalDateTime createTime;

}
```

## Myabtis-plus条件构造器

> Wrapper ： 条件构造抽象类，最顶端父类。
> 	AbstractWrapper ： 用于查询条件封装，生成 sql 的 where 条件。
> 		QueryWrapper ：Entity 对象封装操作类，不是用lambda语法
> 		UpdateWrapper ： Update 条件封装，用于Entity对象更新操作。
> 		AbstractLambdaWrapper ： Lambda 语法使用 Wrapper统一处理解析 lambda 获取 column。
> 		LambdaQueryWrapper ：看名称也能明白就是用于Lambda语法使用的查询Wrapper
> 		LambdaUpdateWrapper ： Lambda 更新封装Wrapp

```
eq("name", "老王")--->name = '老王'  等于
ne("name", "老王")--->name <> '老王' 不等于
gt("age", 18)--->age > 18 			大于
ge("age", 18)--->age >= 18			大于等于
lt("age", 18)--->age < 18			小于
le("age", 18)--->age <= 18			小于等于
between("age", 18, 30)--->age between 18 and 30
notBetween("age", 18, 30)--->age not between 18 and 30
// like
like("name", "王")--->name like '%王%'
notLike("name", "王")--->name not like '%王%'
likeLeft("name", "王")--->name like '%王'
likeRight("name", "王")--->name like '王%'
notLikeLeft("name", "王")--->name not like '%王'
notLikeRight("name", "王")--->name not like '王%'
// is null
isNull("name")--->name is null
isNotNull("name")--->name is not null
// in
in("age",{1,2,3})--->age in (1,2,3)
notIn("age",{1,2,3})--->age not in (1,2,3)
notIn("age", 1, 2, 3)--->age not in (1,2,3)
// insql
inSql("age", "1,2,3,4,5,6")--->age in (1,2,3,4,5,6)
inSql("id", "select id from table where id < 3")--->id in (select id from table where id < 3)
notInSql("age", "1,2,3,4,5,6")--->age not in (1,2,3,4,5,6)
notInSql("id", "select id from table where id < 3")--->id not in (select id from table where id < 3)
// group by
groupBy("id", "name")--->group by id,name
// order by
orderByAsc("id", "name")--->order by id ASC,name ASC
orderByDesc("id", "name")--->order by id DESC,name DESC
orderBy(boolean condition, boolean isAsc, R... columns)
// having
having("sum(age) > 10")--->having sum(age) > 10
having("sum(age) > {0}", 11)--->having sum(age) > 11
// 拼接or
eq("id",1).or().eq("name","老王")--->id = 1 or name = '老王'
//嵌套or
or(i -> i.eq("name", "李白").ne("status", "活着"))--->or (name = '李白' and status <> '活着')
// 嵌套and
and(i -> i.eq("name", "李白").ne("status", "活着"))--->and (name = '李白' and status <> '活着')
//正常嵌套 不带 AND 或者 OR
nested(i -> i.eq("name", "李白").ne("status", "活着"))--->(name = '李白' and status <> '活着')
```

**拼接sql**

apply

```java
apply(String applySql, Object... params)
apply(boolean condition, String applySql, Object... params)
```

注意事项:

该方法可用于数据库**函数** 动态入参的`params`对应前面`applySql`内部的`{index}`部分.这样是不会有sql注入风险的,反之会有!

```
例: apply("id = 1")--->id = 1
例: apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
例: apply("date_format(dateColumn,'%Y-%m-%d') = {0}", "2008-08-08")--->date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'")
#last
```

last

```
last(String lastSql)
last(boolean condition, String lastSql)
```

注意事项:

无视优化规则直接拼接到 sql 的最后

只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用

```
例: last("limit 1")
```

exists

```
exists("select id from table where age = 1")--->exists (select id from table where age = 1)
```

notExists

```
notExists("select id from table where age = 1")--->not exists (select id from table where age = 1)
```

select

设置查询字段

```
select("id", "name", "age")
```

set

```
例: set("name", "老李头")
例: set("name", "")--->数据库字段值变为空字符串
例: set("name", null)--->数据库字段值变为nul
```

setSql

设置 SET 部分 SQL

```
setSql("name = '老李头'")
```

## Mybatis-plus lambda式

以构造一个查询id，username，createtime，条件为id = 1L为例

```
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(User::getId,User::getUsername,User::getCreateTime)
                .eq(User::getId,1L);
```


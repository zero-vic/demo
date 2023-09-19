## PostgreSQL对空值的处理

### NULLIF函数

> NULLIF(var1,var2)
>
> 如果var1和var2相等则返回null，不相等则返回var1

例如：

```sql
select nullif(1,null);  结果：1
select nullif(null,1);  结果：null
select nullif(1,1);     结果：null
select nullif(1,0);     结果：1
```

### COALESCE函数

> COALESCE函数是返回参数中的第一个非null的值，它要求参数中至少有一个是非null的，如果参数都是null会报错.
>
> 不对空串有效

例如

```
SELECT COALESCE(null,1);      结果:1
SELECT COALESCE(null,'11',1); 结果:11
```

处理结果集中的null和空字符

例如：吧父id为null或者为空的数据变成0

```sql
SELECT COALESCE(NULLIF(parentid,''),'0') parentId from sys_menu
```


# SqlSugar入门CRUD

创建一个.net core 7 webapi项目

## 01.安装SqlSugar

使用NuGet 安装SqlSugarCore

## 02.CRUD

新建个User实体

```c#
[SugarTable("t_user")]
public class User
{
	public User()
	{
	}
	public User(int Tid, string Name, int Age) {
		this.Tid = Tid;
		this.Name = Name;
		this.Age = Age;
	}
	[SugarColumn(IsPrimaryKey = true)]
	public int Tid { get; set; }
	public string? Name { get; set; }
	public int? Age { get; set; }

}
```

新建个SqlConnection

配置数据库，写一些crud的操作

```C#
using SqlSugar;

namespace demo
{
    class SqlConnection
    {
        SqlSugarClient db = new SqlSugarClient(new ConnectionConfig { 
            DbType = DbType.PostgreSQL,
            ConnectionString = "PORT=5432;DATABASE=ssm;HOST=10.32.128.113;PASSWORD=123456;USER ID=postgres",
            IsAutoCloseConnection = true    
        });

        public List<User> getAll() 
        {
            return db.Queryable<User>().ToList();
        }

        public void insertUser(User user)
        {
            db.Insertable(user).ExecuteCommand();
        }

        public void updateUser(User user)
        {
            db.Updateable(user).ExecuteCommand();
        }

        public void deleteUser(int id)
        {
            db.Deleteable<User>().Where("tid = @tid", new { tid = id}).ExecuteCommand();
        }

         
    }
}
```

新建个TestController

```C#
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace demo.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TestController : ControllerBase
    {

        [HttpGet("hello")]
        public string hello() { 
            return "hello world";
        }

        [HttpGet("getUsers")]
        public List<User> GetUsers()
        {
            return new SqlConnection().getAll();
        }

        [HttpPost("add")]
        public String insertUser(User user)
        {

            new SqlConnection().insertUser(user);
            return "ok";
        }

        [HttpPut("update")]
        public String updateUser(User user)
        {
            new SqlConnection().updateUser(user);
            return "ok";
        }

        [HttpDelete("delete")]
        public string deleteUser(int id)
        {
            new SqlConnection().deleteUser(id);
            return "ok";
        }
    }
}
```

在swgger之中测试
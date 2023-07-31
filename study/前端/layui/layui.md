## Layui 解析地址的参数

以`/page/role/edit.html?id=123`为例在layer打开的页面获取id

```js
  var url = layui.url();
  var id = url.search.id;
```

## Layui 处理多选框(checkbox)的值

需要处理成的数据格式 1,2,3 或者 2,3

```html
<div class="layui-form-item">
     <input id="typeId" type="hidden" name="types" class="layui-input">
     <label class="layui-form-label">菜单类型</label>
     <div class="layui-input-block">
          <input type="checkbox" name="type" title="类型1" value="1" lay-filter="types">
          <input type="checkbox" name="type" title="类型2" value="2" lay-filter="types">
          <input type="checkbox" name="type" title="类型3" value="3" lay-filter="types">
      </div>
</div>
```

处理代码：

```js
    // 监听复选框 处理多选的值 1,2,3
	// checkbox的types 对应的是 lay-filter="types
    form.on('checkbox(types)', function (data) {
      var types = "";
      var type_arr = [];
      $('input[type="checkbox"]:checked').each(function () {
        type_arr.push($(this).val());
      });
      types = type_arr.join();
      $('#typeId').val(types);
    });
```

## Layui多选框(checkbox)数据回显

后端给我的数据格式为 1,2,3 或者 2,3

```html
      <!-- 表单代码 -->
      <div class="layui-form-item">
        <input id="typeId" type="hidden" name="types" class="layui-input">
        <label class="layui-form-label">菜单类型</label>
        <div class="layui-input-block">
          <input type="checkbox" name="type" title="类型1" value="1" lay-filter="types">
          <input type="checkbox" name="type" title="类型2" value="2" lay-filter="types">
          <input type="checkbox" name="type" title="类型3" value="3" lay-filter="types">
        </div>
      </div>
```

js处理

```js
    $.post(url, { id: id }, function (data) {
      if (data.code == 200) {

        //处理多选框 checkbox
        let type_arr = data.data.types.split(",");
        $.each(type_arr, (index, v) => {
          let node = $(`input[type="checkbox"][name="type"][value="${v}"]`);
          if (node && node.length) {
            node[0].checked = true;
          }
        })
      }
    }, 'json');
```

## Layui给表单赋值

表单代码

```html
 <form class="layui-form  layui-form-pane" id="actionform" action="" lay-filter="editform">
  <div class="layui-form-item">
        <label class="layui-form-label">菜单名称</label>
        <div class="layui-input-block">
          <input type="text" id="mName" name="name" autocomplete="off" placeholder="请输入" class="layui-input">
        </div>
      </div>
 </form>
 
```

js处理

```js
    $.post(url, { id: id }, function (data) {
      if (data.code == 200) {
        // 给表单元素赋值
        form.val('editform', {
          "name": data.data.name,
        })
      }
    }, 'json');
```

## Layui数据表格的单选框错位问题

适当改变单选框的样式

```css
    <style>
        .laytable-cell-radio {
            padding: 15px 15px 0 15px;
        }
    </style>
```

## Layui使用下拉树（dtree）

gitee地址：https://gitee.com/miniwatermelon/DTreeHelper

帮助手册地址：https://www.wisdomelon.com/DTreeHelper/

**渲染下拉树**

```html
<!-- html代码 -->
<div class="layui-form-item">
        <input id="menuId" type="hidden" name="parentid" class="layui-input">
        <label class="layui-form-label">父菜单</label>
        <div class="layui-input-block">
          <ul id="selTree1" class="dtree" data-id="0"></ul>
        </div>
</div>
```



```js
layui.extend({
    dtree: '/static/layui_ext/dtree/dtree'   // {/}的意思即代表采用自有路径，即不跟随 base 路径
  }).use(['form', 'layedit', 'laydate', 'dtree'], function () {
    form = layui.form
      , layer = layui.layer
      , layedit = layui.layedit
      , dtree = layui.dtree;

    dtree.renderSelect({
      elem: "#selTree1",
      width: "100%", // 指定树的宽度
      method: "POST",
      url: "url",
      // url: "./1.json",
      icon: "2",  //修改二级图标样式
      line: true,  // 显示树线
      skin: "laySimple",  // laySimple主题风格
      initLevel: "1",  //默认展开层级为1
      dataStyle: "layuiStyle",  //使用layui风格的数据格式
      dataFormat: "list",  //配置data的风格为list
      response: { message: "msg", statusCode: 200 },  //修改response中返回数据的定义
      none: "正在加载数据...",
      selectInputName: {
        nodeId: "nodeId",
        context: "context"
      }
    });
    
    // 选中赋值
    dtree.on('node("selTree1")', function (obj) {
      var param = dtree.selectVal("selTree1");
      $("#menuId").val(param.nodeId);
    });
  });

```

**回显数据**

```html
<!-- html代码 -->
<div class="layui-form-item">
        <label class="layui-form-label">父菜单</label>
        <div class="layui-input-block">
          <ul id="selTree1" class="dtree" data-id="0" data-value=""></ul>
        </div>
 </div>
```

```js
	var pid = '123';//要回显数据的id
    dtree.renderSelect({
      elem: "#selTree1",
      width: "100%", // 指定树的宽度
      method: "POST",
      url: "http://127.0.0.1:8082/system/menu/GetTreeList",
      // url: "./1.json",
      icon: "2",  //修改二级图标样式
      line: true,  // 显示树线
      skin: "laySimple",  // laySimple主题风格
      initLevel: "1",  //默认展开层级为1
      dataStyle: "layuiStyle",  //使用layui风格的数据格式
      dataFormat: "list",  //配置data的风格为list
      response: { message: "msg", statusCode: 200 },  //修改response中返回数据的定义
      done: function (res, $ul, first) {
        if (first) {
            //传入回显数据id
          dtree.selectVal("selTree1", pid); // 如不传第二个参数，则树会自动根据当前选中节点回显值
        }
      },
      none: "正在加载数据...",
      selectInputName: {
        nodeId: "nodeId",
        context: "context"
      }
    });
```

## layui使用树列表（treetable）

> layui版本2.8.11内置树表组件了
>
> 由于我的版本不是2.8以上的，所以使用社区开源的树表组件（treetable）
>
> 一般是用在多层级的表格，比如菜单管理

gitee:https://gitee.com/ele-admin/treetable-lay

简单使用

```html
      <table id="demoTreeTb"></table>
```

```js
    layui.use(['layer', 'form', 'util', 'treeTable'], function () {
        var util = layui.util;
        var form = layui.form;
        var treeTable = layui.treeTable;
        renderTable = function () {
            // 渲染表格
            insTb = treeTable.render({
                elem: '#demoTreeTb',
                url: 'url',
                method: 'post',
                // url: './menus.json',
                toolbar: '#toolbarDemo',
                defaultToolbar: false,
                height: 'full-200',
                where: {
                    condition: $("#condition").val()
                },
                tree: {
                    iconIndex: 2,
                    isPidData: true,
                    idName: 'tid',
                    pidName: 'parentid'
                },
                cols: [
                    [
                        { type: 'numbers' },
                        { type: 'checkbox' },
                        { field: 'name', title: '菜单名称', minWidth: 165 },
                        { field: 'path', title: '路径', minWidth: 165 },
                        { field: 'pathkey', title: '菜单地址key' },
                        {
                            title: '菜单图标', align: 'center', hide: true,
                            templet: '<p><i class="layui-icon {{d.menuIcon}}"></i></p>'
                        },
                        { field: 'powercode', title: '权限标识' },
                        { field: 'orders', title: '排序序号' },
                        { field: 'target', title: '跳转方式' },
                        {
                            field: 'models', title: '权限类型',
                            templet: function (res) {
                                if (res.models == 1) {
                                    return "页面";
                                } else if (res.models == 2) {
                                    return "按钮";
                                } else if (res.models == 3) {
                                    return "数据";
                                }
                                return "";
                            }
                        },
                        {
                            field: 'types', title: '菜单类型',
                            templet: function (res) {

                                if (res.types == undefined) {
                                    return "";
                                }
                                return res.types.replace('1', '高投').replace('2', '咨询造价').replace('3', '设计');
                            }
                        },

                        // {title: '类型', templet: '<p>{{d.isMenu?"菜单":"按钮"}}</p>', align: 'center', width: 60},

                        { align: 'center', toolbar: '#tbBar', title: '操作', width: 120 }
                    ]
                ],
                style: 'margin-top:10;'
            });
        }
        renderTable();

        // 工具列点击事件
        treeTable.on('tool(demoTreeTb)', function (obj) {
            var event = obj.event;
            if (event === 'del') {
                del(obj.data.tid);             
            } else if (event === 'edit') {
                edit(obj.data.tid,obj.data.parentid);
            }
        });

        // 头部工具栏点击事件
        treeTable.on('toolbar(demoTreeTb)', function (obj) {
            switch (obj.event) {
                case 'add':
                    add();
                    break;
                case 'delete':
                    layer.msg('删除');
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
                case 'LAYTABLE_TIPS':
                    layer.msg('提示');
                    break;
            }
        });

       

        // 重载
        // $('#btnReload').click(function () {
        //     insTb.reload();
        // });
        // $('#btnRefresh').click(function () {
        //     insTb.refresh();
        // });


        $('#searchBtn').on('click', function () {
            renderTable();
            // insTb.refresh();
        });


    });

```

## Layui layer父拿子页面的表单数据

父页面js

```
 function add() {
        parent.layer.open({
            type: 2,
            shift: 2,
            title: "新增",
            area: ['600px', '500px'],
            fix: true,
            maxmin: false, //禁用最大最小化按钮
            content: '/page/menu/add.html', //子页面
            btn: ["保存", "取消"],
            btn1: function (index, layero) {
            	// 调用子页面的方法
                var result = $(layero).find("iframe")[0].contentWindow.GetInfo();
				// 处理子页面有无传参数 
                var flag =  paramIsBlank(result);
                if (!flag) {
                    $.post("url", result, function (data) {
                        console.log(data);
                        if (data.code = "200") {
                            parent.layer.close(index);
                            parent.layer.msg(data.message);
                            insTb.refresh();
                        }
                        else {
                            parent.layer.msg(data.message);
                        }
                    }, 'json');

                }
            },
            btn2: function (index, layero) {

            },
            end: function () {

            }
        });
    }
    // 判断iframe 表单页面传的传的参数是否为空 
function paramIsBlank(param){
    var strArr = param.split('&');
    var res = []; 
    for(var i = 0;i<strArr.length;i++){
        var str = strArr[i].split('=');
        if(str[1]!="" && str[1] !=undefined){
            return false;
        }
    }
    return true;    
}
```

子页面

```js
 function GetInfo() {
    return $('#actionform').serialize();
 }
```


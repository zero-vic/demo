## 什么是vue？

> Vue 是一套用于构建用户界面的**渐进式框架**。与其它大型框架不同的是，Vue 被设计为可以自底向上逐层应用。Vue 的核心库只关注视图层，不仅易于上手，还便于与第三方库或既有项目整合。

官网：https://cn.vuejs.org/

## Vue安装方式

CDN引入

```
// 开发环境版本, 包含了帮助的命令行警告
<srcipt src='https://cdn.jsdeliver.net/npm/vue/dist/vue.js'></script>

// 生产环境版本, 优化了尺寸和速度
<srcipt src='https://cdn.jsdeliver.net/npm/vue'></script>

```

独立版本

> 官网下载，直接在script标签中引入即可

## Vue基础语法

**文本插值 {{ }}**

Mustache语法

```
{{ msg }}
```

**v-html**

用于输出html代码

**v-bind**

作用: 动态绑定属性
简写: :

**v-once**

表示元素和组件只渲染一次, **不会随着数据的改变而变化**

**v-text**

和插值语法类似

**v-pre**

用于跳过这个元素和它子元素的编译过程, 用于显示原本的Mustache语法

**v-cloak**

在某些情况下, 我们浏览器可能会直接显示出未编译的Mustache标签

**v-model**

实现数据的双向绑定

**条件判断**

- v-if
- v-else
- v-else-if

**v-show**

是否展示

v-show和v-if的区别

> v-if 不会渲染到dom去，每次都会重新渲染
>
> v-show: dom增加一个行内样式display: none,只挂载一次

**v-on**

作用: 绑定事件监听
简写: @
写法:

- 没有参数的情况下, 可以不写(); 如果方法本身有一个参数, 会默认将原生事件event参数传递进去
- 如果传入某个参数, 同时需要event时, 可以通过$event传入时间

**修饰符**

.stop （阻止时间冒泡）

.prevent （阻止默认事件）

.native (监听组件根元素的原生事件)

.once (只触发一次回调)

**v-for遍历**

```
// 遍历过程中, 没有使用索引值
<li v-for="item in names">{{item}}</li>

// 遍历过程中, 获取索引值
<li v-for="(item, index) in names">{{index + 1}} - {{item}}</li>

// 遍历对象的时候, 只有一个值, 活得的是value

// 获取对象的key和value 
<li v-for="(value, key) in names">{{value}} - {{key}}</li>

// 获取对象的key和value和index 
<li v-for="(value, key, index) in names">{{value}} - {{key}} - {{index}}</li>

```

**修饰符**
**lazy修饰符**

前景: v-model默认是在input事件中实时同步输入框的数据的 (容易同步的过于频繁 )

作用: 可以让数据只有在失去焦点或回车时才会更新

**number修饰符**

前景: 默认情况下, 在输入框中无论输入字母还是数字, 都会被当做字符串类型进行处理
作用: 当做数字类型进行处理
**trim修饰符**

前景: 输入的内容首位容易有空格
作用: 可以过滤掉内容左右两边的空格

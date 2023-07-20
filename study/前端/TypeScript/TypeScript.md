## [什么是TypeScript?]()

> TypeScript 是 JavaScript 的一个超集，支持 ECMAScript 6 标准，它可以编译成纯javaScript，可以运行在任何浏览器上。

## 安装TypeScript

```
npm install -g typescript
```

安装完成后我们可以使用 **tsc** 命令来执行 TypeScript 的相关代码

```
tsc -v
Version 5.1.6
```

新建一个app.ts文件

```tsx
var message = "Hello World";
console.log(message);
```

编译ts文件,就会生成一个对应的js文件

```
tsc app.ts
```

使用node来执行下

```
node app.js
Hello World
```

## TypeScript基础类型

**1.数据类型**

```tsx
// 字符串类型
let str: string = "yao";
// 数字类型
let num: number = 777;
// 布尔类型
let flag: boolean = false;
// 数组类型 在元素类型后面加上[] 或者使用数组泛型
let arr: number[] = [1,2,3];
let arr2: Array<number>= [1,2,3];
// 元祖 用来表示已知的数量和类型的数组，各元素类型不用相同
let x: [string,number] = ['xxx',231];
// 枚举 用来定义数值集合
enum Color {Red, Green, Blue};
```

**2.任意类型**

> 任意值是 TypeScript 针对编程时类型不明确的变量使用的一种数据类型

```
let x: any = 1;    // 数字类型
x = 'I am who I am';    // 字符串类型
x = false;    // 布尔类型
```

**3.Null 和 Undefined**

null

> 在 JavaScript 中 null 表示 "什么都没有"。
>
> null是一个只有一个值的特殊类型。表示一个空对象引用。
>
> 用 typeof 检测 null 返回是 object。

undefined

> 在 JavaScript 中, undefined 是一个没有设置值的变量。
>
> typeof 一个没有值的变量会返回 undefined。
>
> Null 和 Undefined 是其他任何类型（包括 void）的子类型，可以赋值给其它类型，如数字类型，此时，赋值后的类型会变成 null 或 undefined

**4.never类型**

> never 是其它类型（包括 null 和 undefined）的子类型，代表从不会出现的值。这意味着声明为 never 类型的变量只能被 never 类型所赋值，在函数中它通常表现为抛出异常或无法执行到终止点（例如无限循环）

## TypeScript类

定义类的关键字是`class`，类可以包含一下几个模块：

> 字段：类里面声明的变量
>
> 构造函数：类实例化的时候调用，可以为类分配内存
>
> 方法：对象要执行的操作

```tsx
class Person {
    // 字段
    age:number;
    name:string;
    // 构造函数
    constructor(age:number,name:string){
        this.age = age;
        this.name = name;
    }
    // 方法
    printPerson():void{
        console.log(this.age+":"+this.name);
    }
}
// 类的实例化
let p = new Person(18,"yao");
// 访问类的字段
let age = p.age;
// 类方法的调用
p.printPerson();
```

### 类的继承

> 类的集成使用 `extends`关键字,子类除了不能继承父类的私有成员(方法和属性)和构造函数，其他的都可以继承。
>
> 类只能继承一个类，不能继承多个类，换种话说，只能有一个爸爸

```tsx
class Root { 
   str:string; 
} 
 
class Child extends Root {} 
class Leaf extends Child {} // 多重继承，继承了 Child 和 Root 类
 
var obj = new Leaf(); 
obj.str ="hello" 
console.log(obj.str)
```

### 类的方法重写

> 类继承后，子类可以对父类的方法重新定义，这个过程称之为方法的重写。
>
> 其中 super 关键字是对父类的直接引用，该关键字可以引用父类的属性和方法。

```tsx
class PrinterClass { 
   doPrint():void {
      console.log("父类的 doPrint() 方法。") 
   } 
} 
 
class StringPrinter extends PrinterClass { 
   doPrint():void { 
      super.doPrint() // 调用父类的函数
      console.log("子类的 doPrint()方法。")
   } 
}
```

### static 关键字

> static 关键字用于定义类的数据成员（属性和方法）为静态的，静态成员可以直接通过类名调用。

```tsx
class Person {
    // 静态字段
    static age:number;
    static name1: string;
   
    // 静态方法
    static printPerson():void{
        console.log(Person.age);
    }
}
// 给静态属性赋值
Person.age = 19;
//调用静态方法
Person.printPerson();


```

### instanceof 运算符

> instanceof 运算符用于判断对象是否是指定的类型，如果是返回 true，否则返回 false。

```tsx
class Person{ } 
var obj = new Person() 
var isPerson = obj instanceof Person; 
console.log("obj 对象是 Person 类实例化来的吗？ " + isPerson);
```

### 访问控制修饰符

TypeScript 中，可以使用访问控制符来保护对类、变量、方法和构造方法的访问。TypeScript 支持 3 种不同的访问权限。

- **public（默认）** : 公有，可以在任何地方被访问。
- **protected** : 受保护，可以被其自身以及其子类访问。
- **private** : 私有，只能被其定义所在的类访问。

### 抽象类

> 不需要实例化，一般当做父类定义公共的抽象方法，使用`abstract`关键字修饰

```tsx
// 抽象类
abstract class A {
   public name: string;
   constructor(name:string){
    this.name = name;
   }

   print():string{
    return this.name;
   }
   // 抽象方法
   abstract getName():string;
}

class B extends A {
    constructor(){
        super("yao");
    }
    getName(): string {
        return this.getName();
    }
    
}
let b = new B();
```



## TypeScript接口

接口是一系列抽象方法的声明，是一些方法特征的集合，这些方法都应该是抽象的，需要由具体的类去实现，然后第三方就可以通过这组抽象方法调用，让具体的类执行具体的方法。

> 使用`interface`关键字来定义一个接口

```tsx
// 定义接口
interface IPerson{
    // 定义变量
    id:string;
    // 定义抽象方法
    hello:()=>string;
}
```

### 类和接口

> 类可以实现接口，使用关键字 `implements`

```tsx
// 定义接口
interface IPerson{
    // 定义变量
    id:string;
    // 定义抽象方法
    hello:()=>string;
}
// 实现接口
class Person implements IPerson {
    id: string;
    // 静态字段
    static age:number;
    static name1: string;
    constructor(id:string){
        this.id = id
    }
    hello():string{
        return "hello"
    }
    // 静态方法
    static printPerson():void{
        console.log(Person.age);
    }
}
```

### 接口继承

> 接口继承就是说接口可以通过其他接口来扩展自己。
>
> 接口可以继承多个接口。
>
> 继承使用关键字 **extends**。

```tsx
Child_interface_name extends super_interface_name
Child_interface_name extends super_interface1_name, super_interface2_name,…,super_interfaceN_name
```

### 可选属性 使用?操作符

> *可选属性的含义是该属性可以不存在* 使用?修饰

```tsx
interface IPerson{
    // 定义变量
    id:string,
    name?:string;
}
var person:IPerson = {
    id: "11"
}

```

### 任意属性 [propName: string]

> **一旦定义了任意属性，那么确定属性和可选属性的类型都必须是它的类型的子集**

```tsx
//在这个例子当中我们看到接口中并没有定义C但是并没有报错
//应为我们定义了[propName: string]: any;
//允许添加新的任意属性
interface IPerson {
    b?:string,
    a:string,
    [propName: string]: any;
}
 
const person:IPerson  = {
    a:"213",
    c:"123"
}

```

### 只读属性 readonly

> readonly 只读属性是不允许被赋值的只能读取

```tsx
//这样写是会报错的
//应为a是只读的不允许重新赋值
interface IPerson {
    b?: string,
    readonly a: string,
    [propName: string]: any;
}
 
const person: IPerson = {
    a: "213",
    c: "123"
}
 
person.a = 123

```

## TypeScript函数

> 使用`function`来定义一个函数

```tsx
function function_name()
{
    // 执行代码
}

```

### 带返回值的函数

语法格式：

```tsx
function function_name():return_type { 
    // 语句
    return value; 
}
```

示例：

```tsx
function test2():string{
    return "hello";
}
```

### 带参数的函数

```tsx
// 必须传参数的函数
function test3(x:number,y:number):number{
    return x+y;
}

// 可选参数的函数 参数使用?标识则可以不传
function test4(a:string,b?:string):string{
    if(b){
        return a+B;
    }
    return a;
}
// 默认参数的函数 如果不传入该参数的值，则使用默认参数
function test5(a:string,b:string = "sb"){
    return a+B;
}

// 剩余参数的函数 不知道要传入多少个参数，在最后一个参数位置使用，将不确定参数以数组的形式接收
function test6(...nums:number[]){
    var i:number;   
    var sum:number = 0; 
    
    for(i = 0;i<nums.length;i++) { 
       sum = sum + nums[i]; 
    } 
    console.log("和为：",sum) 
}
```

### 匿名函数

> 没有函数名的函数

```tsx
// 不带参数
var msg = function(){
    return "msg";
}
console.log(msg());
// 带参数
var msg2 = function(msg:string){
    return msg;
}
console.log(msg2("hello"));
```

#### 匿名函数自调用

```tsx
(function () { 
    var x = "Hello!!";   
    console.log(x)     
 })()
```

### 构造函数

> 使用 JavaScript 内置的构造函数 Function() 来定义函数

语法格式：

```
var res = new Function ([arg1[, arg2[, ...argN]],] functionBody)
参数、函数体
```

```tsx
var myFunction = new Function("a", "b", "return a * b"); 
var x = myFunction(4, 3); 
console.log(x);
```

### Lambda 函数

```tsx
var msg3 = (msg:string) => { return msg+"hello"};
console.log(msg3("yao")); 
```

### 函数重载

> 重载是方法名字相同，而参数不同，返回类型可以相同也可以不同。
>
> 如果参数类型不同，则参数类型应设置为 **any**。
>
> 参数数量不同你可以将不同的参数设置为可选。

```tsx
function fn(params: number): void
 
function fn(params: string, params2: number): void
 
function fn(params: any, params2?: any): void {
 
    console.log(params)
 
    console.log(params2)
 
}
```

## TypeScript联合类型

> 可以通过管道(|)将变量设置多种类型，赋值时可以根据设置的类型来赋值
>
> 只能赋值设置的类型，不能赋值其他类型

```tsx
var val:string|number 
val = 12 
console.log("数字为 "+ val) 
val = "" 
console.log("字符串为 " + val)
```

联合类型数组

```tsx
var arr:number[]|string[]; 
var i:number; 
arr = [1,2,4] 
console.log("**数字数组**")  
 
for(i = 0;i<arr.length;i++) { 
   console.log(arr[i]) 
}  
 
arr = ["a","b","c"] 
console.log("**字符串数组**")  
 
for(i = 0;i<arr.length;i++) { 
   console.log(arr[i]) 
}
```

### 交叉类型

> 多种类型的集合，联合对象将具有所联合类型的所有成员

```typescript
interface People {
  age: number,
  height： number
}
interface Man{
  sex: string
}
// 通过单个&连接起来, 表示同时满足
const xiaoman = (man: People & Man) => {
  console.log(man.age)
  console.log(man.height)
  console.log(man.sex)
}
xiaoman({age: 18,height: 180,sex: 'male'});

```

## TypeScript类型断言

> TypeScript类型断言是一个编译时语法，用于告诉编译器用户比编译器更加确定变量的类型，进而解除编译错误，类型断言有点类似于其他语言的类型转换，但它没有运行时的影响，只是在编译阶段起作用。所以，即使通过类型断言解除了编译错误，也不会影响运行错误。

**用法：**

```
值 as 类型 
<类型>值
```

**基本用法**

```typescript
let value: any = "this is a string";
let length: number = (value as string).length;
```

**非空断言**

> 当我们确认某个值不可能为 undefined 或 null 时，可以在变量后面加上一个 !。

```typescript
function test7(value: string|undefined|null){
    const str: string = value; // 错误 value 可能为 undefined 和 null
    const str1: string = value!; //ok
    const length: number = value.length; // 错误 value 可能为 undefined 和 null
    const length1: number = value!.length; //ok
}
```

**确认赋值断言**

> 确定赋值断言即允许在实例属性和变量声明后面放置一个 ! 号，从而告诉编译器该属性一定会被明确的赋值

```typescript
// 未加断言
let x: number;
initialize();
console.log(2 * x); // 错误，x在赋值之前被调用

function initialize() {
  x = 10;
}
// 加上断言
let x!: number;
initialize();
console.log(2 * x); // ok

function initialize() {
  x = 10;
}
```

**总结**：

- 联合类型可以被断言为其中一个类型
- 父类可以被断言为子类
- 任何类型都可以被断言为 any
- any 可以被断言为任何类型
- 当我们确认某个值不可能为 undefined 或 null 时，可以在变量后面加上一个 “!”。

## TypeScript枚举类型

### 数字枚举

数字枚举默认是0开始的

```typescript
// 例如 红绿蓝 Red = 0 Green = 1 Blue= 2 分别代表红色0 绿色为1 蓝色为2
enum Types {
    Red,
    Green,
    Blue
}
console.log(Types[0]) // Red
// 这样写就可以实现应为ts定义的枚举中的每一个组员默认都是从0开始的所以也就是
enum Types{
   Red = 0,
   Green = 1,
   BLue = 2
}
//默认就是从0开始的 可以不写值

// 增长枚举
enum Types{
   Red = 1,
   Green,
   BLue
}
// 如上，我们定义了一个数字枚举， Red使用初始化为 1。 其余的成员会从 1开始自动增长。 换句话说， Type.Red的值为 1， Green为 2， Blue为 3。

```

### 字符串枚举

> 在一个字符串枚举里，每个成员都必须用字符串字面量，或另外一个字符串枚举成员进行初始化

```typescript
enum Types{
   Red = 'red',
   Green = 'green',
   BLue = 'blue'
}
```

### 异构枚举

> 枚举可以混合字符串和数字成员

```typescript
enum Types{
   No = "No",
   Yes = 1,
}
```

const 修饰枚举

> let 和 var 都是不允许的声明只能使用const
>
> 大多数情况下，枚举是十分有效的方案。 然而在某些情况下需求很严格。 为了避免在额外生成的代码上的开销和额外的非直接的对枚举成员的访问，我们可以使用 const枚举。 常量枚举通过在枚举上使用 const修饰符来定义
>
> const 声明的枚举会被编译成常量
>
> 普通声明的枚举编译完后是个对象

```typescript
const enum Types{
   No = "No",
   Yes = 1,
}

```

## TypeScript元组

> 元组中允许存储不同类型的元素，元组可以作为参数传递给函数。

```typescript
// 创建一个元组并初始化
var param = [18,"yao",false];
// 先声明再初始化
var param = [];
param[0] = 18;
param[1] = "yao"; 

```

> 访问元组是通过下标来的，默认从0开始，与数组一样

**新增/删除元组元素**

- push() 向元组添加元素，添加在最后面。
- pop() 从元组中移除元素（最后一个），并返回移除的元素。

**更新元组**

```typescript
var param = [18,"yao",false];
param[0] = 19;
```

**解构元组**

> 把元组元素赋值给变量

```typescript
var a =[10,"Runoob"] 
var [b,c] = a 
console.log( b )    
console.log( c )
```

## TypeScript Map对象

> Map 对象保存键值对，并且能够记住键的原始插入顺序。

**Map的创建**

```
let myMap = new Map();
```

**Map相关函数和属性**

- **map.clear()** – 移除 Map 对象的所有键/值对 。
- **map.set()** – 设置键值对，返回该 Map 对象。
- **map.get()** – 返回键对应的值，如果不存在，则返回 undefined。
- **map.has()** – 返回一个布尔值，用于判断 Map 中是否包含键对应的值。
- **map.delete()** – 删除 Map 中的元素，删除成功返回 true，失败返回 false。
- **map.size** – 返回 Map 对象键/值对的数量。
- **map.keys()** - 返回一个 Iterator 对象， 包含了 Map 对象中每个元素的键 。
- **map.values()** – 返回一个新的Iterator对象，包含了Map对象中每个元素的值 。

**迭代Map**

> Map 对象中的元素是按顺序插入的，我们可以迭代 Map 对象，每一次迭代返回 [key, value] 数组。
>
> TypeScript使用 **for...of** 来实现迭代	

```typescript
let myMap = new Map();
 
myMap.set("a", 1);
myMap.set("b", 2);
myMap.set("c", 3);
 
// 迭代 Map 中的 key
for (let key of myMap.keys()) {
    console.log(key);                  
}
 
// 迭代 Map 中的 value
for (let value of myMap.values()) {
    console.log(value);                 
}
 
// 迭代 Map 中的 key => value
for (let entry of myMap.entries()) {
    console.log(entry[0], entry[1]);   
}
 
// 使用对象解析
for (let [key, value] of myMap) {
    console.log(key, value);            
}
```

## TypeScript keyof

> 该操作符可以用于获取某种 类型 的所有键，其返回类型是 联合类型

作用interface时，将其属性名全部解构出来

```typescript
interface IPerson {
  name: string,
  age: number,
  sex: 0 | 1
}

type P1 = keyof IPerson
interface ITest {
  key: P1
}
let test:ITest = {
  key: "name" // name | age | sex
}

```

作用数组时，将它生成的对象属性当联合类型返回

```typescript
type P1 = keyof Array<string>[]

interface ITest {
  key: P1
}

let test:ITest = {
  key: "pop" // length | concat | ...
}
alert(test.key)
```

作用于类时，效果类似于对象，它将类生成的对象属性，当联合类型返回。

```typescript

class Person {
  name: string
  age: number
  sex: 0 | 1

  constructor(name: string, age: number, sex: 0 | 1) {
    this.name = name
    this.age = age
    this.sex = sex
  }

  getName(): string {
    return this.name
  }
  setName(name: string): void {
    this.name = name
  }
}

type K1 = keyof Person

interface ITest {
  key: K1
}
let test: ITest = {
  key: "age" // age | name | sex | setName | getName
}

```



## TypeScript 泛型

函数泛型

```typescript
function get(a: number, b: number): Array<number> {
    return [a, b];
}
get(1,2)
// 泛型优化
// 语法为函数名字后面跟一个<参数名> 参数名可以随便写 例如我这儿写了T
function get1<T>(a: T, b: T): Array<T> {
    return [a, b];
}
get1<number>(1,2);
// 我们也可以使用不同的泛型参数名，只要在数量上和使用方式上能对应上就可以。
function Sub<T,U>(a:T,b:U):Array<T|U> {
    const params:Array<T|U> = [a,b]
    return params
}
 
 
Sub<Boolean,number>(false,1)
// 定义接口泛型
interface MyInter<T> {
   (arg: T): T
}
 
function fn<T>(arg: T): T {
   return arg
}
 
let result: MyInter<number> = fn
 
result(123)


```

泛型约束

> 我们期望在一个泛型的变量上面，获取其`length`参数，但是，有的数据类型是没有`length`属性的

```typescript
interface Len {
   length:number
}
 
function getLegnth<T extends Len>(arg:T) {
  return arg.length
}
 
getLegnth<string>('123')

```

使用keyof约束对象

> 其中使用了TS泛型和泛型约束。首先定义了T类型并使用extends关键字继承object类型的子类型，然后使用keyof操作符获取T类型的所有键，它的返回 类型是联合 类型，最后利用extends关键字约束 K类型必须为keyof T联合类型的子类型

```typescript
function prop<T, K extends keyof T>(obj: T, key: K) {
   return obj[key]
}
 

let o = { a: 1, b: 2, c: 3 }
 
prop(o, 'a') 
prop(o, 'd') //此时就会报错发现找不到

```

## TypeScript 三斜线指令

> 三斜线指令是包含单个XML标签的单行注释。 注释的内容会做为编译器指令使用。 
>
> 三斜线指令仅可放在包含它的文件的最顶端。 
>
> 一个三斜线指令的前面只能出现单行或多行注释，这包括其它的三斜线指令。 如果它们出现在一个语句或声明之后，那么它们会被当做普通的单行注释，并且不具有特殊的涵义。
>
> 三斜线引用告诉编译器在编译过程中要引入的额外的文件。
>
> 你也可以把它理解能import，它可以告诉编译器在编译过程中要引入的额外的文件

```
/// <reference path="..." />指令是三斜线指令中最常见的一种。 它用于声明文件间的 依赖。
```

## TypeScript 模块

> TypeScript 模块的设计理念是可以更换的组织代码。

> 模块是在其自身的作用域里执行，并不是在全局作用域，这意味着定义在模块里面的变量、函数和类等在模块外部是不可见的，除非明确地使用 export 导出它们。类似地，我们必须通过 import 导入其他模块导出的变量、函数、类等。
>
> 两个模块之间的关系是通过在文件级别上使用 import 和 export 建立的

**export**

模块导出使用关键字 **export** 关键字

```
// 文件名 : SomeInterface.ts 
export interface SomeInterface { 
   // 代码部分
}
```

**import**

要在另外一个文件使用该模块就需要使用 **import** 关键字来导入

```
import someInterfaceRef = require("./SomeInterface");
```



## TypeScript内置对象

### **`Boolean`、Number、`string`、`RegExp`、`Date`、`Error`**

```tsx
let b: Boolean = new Boolean(1)
let n: Number = new Number(true)
let s: String = new String('zs')
let d: Date = new Date()
let r: RegExp = /^1/
let e: Error = new Error('error!')
```

### DOM 和 BOM 的内置对象 

```tsx
// Document、HTMLElement、Event、NodeList 等
let body: HTMLElement = document.body;
let allDiv: NodeList = document.querySelectorAll('div');
//读取div 这种需要类型断言 或者加个判断应为读不到返回null
let div:HTMLElement = document.querySelector('div') as HTMLDivElement
document.addEventListener('click', function (e: MouseEvent) {
    
});
//dom元素的映射表
interface HTMLElementTagNameMap {
    "a": HTMLAnchorElement;
    "abbr": HTMLElement;
    "address": HTMLElement;
    "applet": HTMLAppletElement;
    "area": HTMLAreaElement;
    "article": HTMLElement;
    "aside": HTMLElement;
    "audio": HTMLAudioElement;
    "b": HTMLElement;
    "base": HTMLBaseElement;
    "bdi": HTMLElement;
    "bdo": HTMLElement;
    "blockquote": HTMLQuoteElement;
    "body": HTMLBodyElement;
    "br": HTMLBRElement;
    "button": HTMLButtonElement;
    "canvas": HTMLCanvasElement;
    "caption": HTMLTableCaptionElement;
    "cite": HTMLElement;
    "code": HTMLElement;
    "col": HTMLTableColElement;
    "colgroup": HTMLTableColElement;
    "data": HTMLDataElement;
    "datalist": HTMLDataListElement;
    "dd": HTMLElement;
    "del": HTMLModElement;
    "details": HTMLDetailsElement;
    "dfn": HTMLElement;
    "dialog": HTMLDialogElement;
    "dir": HTMLDirectoryElement;
    "div": HTMLDivElement;
    "dl": HTMLDListElement;
    "dt": HTMLElement;
    "em": HTMLElement;
    "embed": HTMLEmbedElement;
    "fieldset": HTMLFieldSetElement;
    "figcaption": HTMLElement;
    "figure": HTMLElement;
    "font": HTMLFontElement;
    "footer": HTMLElement;
    "form": HTMLFormElement;
    "frame": HTMLFrameElement;
    "frameset": HTMLFrameSetElement;
    "h1": HTMLHeadingElement;
    "h2": HTMLHeadingElement;
    "h3": HTMLHeadingElement;
    "h4": HTMLHeadingElement;
    "h5": HTMLHeadingElement;
    "h6": HTMLHeadingElement;
    "head": HTMLHeadElement;
    "header": HTMLElement;
    "hgroup": HTMLElement;
    "hr": HTMLHRElement;
    "html": HTMLHtmlElement;
    "i": HTMLElement;
    "iframe": HTMLIFrameElement;
    "img": HTMLImageElement;
    "input": HTMLInputElement;
    "ins": HTMLModElement;
    "kbd": HTMLElement;
    "label": HTMLLabelElement;
    "legend": HTMLLegendElement;
    "li": HTMLLIElement;
    "link": HTMLLinkElement;
    "main": HTMLElement;
    "map": HTMLMapElement;
    "mark": HTMLElement;
    "marquee": HTMLMarqueeElement;
    "menu": HTMLMenuElement;
    "meta": HTMLMetaElement;
    "meter": HTMLMeterElement;
    "nav": HTMLElement;
    "noscript": HTMLElement;
    "object": HTMLObjectElement;
    "ol": HTMLOListElement;
    "optgroup": HTMLOptGroupElement;
    "option": HTMLOptionElement;
    "output": HTMLOutputElement;
    "p": HTMLParagraphElement;
    "param": HTMLParamElement;
    "picture": HTMLPictureElement;
    "pre": HTMLPreElement;
    "progress": HTMLProgressElement;
    "q": HTMLQuoteElement;
    "rp": HTMLElement;
    "rt": HTMLElement;
    "ruby": HTMLElement;
    "s": HTMLElement;
    "samp": HTMLElement;
    "script": HTMLScriptElement;
    "section": HTMLElement;
    "select": HTMLSelectElement;
    "slot": HTMLSlotElement;
    "small": HTMLElement;
    "source": HTMLSourceElement;
    "span": HTMLSpanElement;
    "strong": HTMLElement;
    "style": HTMLStyleElement;
    "sub": HTMLElement;
    "summary": HTMLElement;
    "sup": HTMLElement;
    "table": HTMLTableElement;
    "tbody": HTMLTableSectionElement;
    "td": HTMLTableDataCellElement;
    "template": HTMLTemplateElement;
    "textarea": HTMLTextAreaElement;
    "tfoot": HTMLTableSectionElement;
    "th": HTMLTableHeaderCellElement;
    "thead": HTMLTableSectionElement;
    "time": HTMLTimeElement;
    "title": HTMLTitleElement;
    "tr": HTMLTableRowElement;
    "track": HTMLTrackElement;
    "u": HTMLElement;
    "ul": HTMLUListElement;
    "var": HTMLElement;
    "video": HTMLVideoElement;
    "wbr": HTMLElement;
}

```

#### 生成html页面示例

```tsx
		let showMsgDiv = document.createElement("div");
        showMsgDiv.setAttribute("id", "showMsgDiv");
        let sendDiv = document.createElement("div");
        let sendTxt = document.createElement("textarea");
        sendTxt.setAttribute("rows", "10");
        sendTxt.setAttribute("cols", "30");
        sendTxt.setAttribute("id", "sendMsgBox");
        sendTxt.textContent = "txt";
        let sendBtn = document.createElement("button");
        sendDiv.appendChild(sendTxt);
        sendDiv.appendChild(sendBtn);
        sendBtn.textContent = "sendMsgBtn";
        document.body.appendChild(showMsgDiv);
        document.body.appendChild(sendDiv);

        let sendMsgBox = document.getElementById("sendMsgBox");
        function showMsg(msg: string) {
            let showMsgBox = document.getElementById("showMsgDiv");
            let subDiv = document.createElement("div");
            subDiv.innerHTML = "<div><i>" + msg + "</i><br></div>";
            if(showMsgBox!=null){
                showMsgBox.appendChild(subDiv);
            }
            
        }
        sendBtn.onclick = function () {
            let msg = (sendMsgBox as HTMLTextAreaElement).value;
            console.log(msg)
            // showMsg(msg);
            // kefu.sendTxtMsg(msg,"lld");
        }
```

### 定义Promise

```tsx
// 如果我们不指定返回的类型TS是推断不出来返回的是什么类型
//  函数定义返回promise 语法规则:Promise<T> 类型 
function promise():Promise<number>{
    return new Promise<number>((resolve, reject)=>{
		resolve(1)
    })
}

```

## tsconfig.json配置文件

```json
"compilerOptions": {
  "incremental": true, // TS编译器在第一次编译之后会生成一个存储编译信息的文件，第二次编译会在第一次的基础上进行增量编译，可以提高编译的速度
  "tsBuildInfoFile": "./buildFile", // 增量编译文件的存储位置
  "diagnostics": true, // 打印诊断信息 
  "target": "ES5", // 目标语言的版本 指定编译js 的版本例如es5 es6
  "module": "CommonJS", // 生成代码的模板标准 默认common.js 可选es6模式 amd umd 等
  "outFile": "./app.js", // 将多个相互依赖的文件生成一个文件，可以用在AMD模块中，即开启时应设置"module": "AMD",
  "lib": ["DOM", "ES2015", "ScriptHost", "ES2019.Array"], // TS需要引用的库，即声明文件，es5 默认引用dom、es5、scripthost,如需要使用es的高级版本特性，通常都需要配置，如es8的数组新特性需要引入"ES2019.Array",
  "allowJS": true, // 允许编译器编译JS，JSX文件
  "checkJs": true, // 允许在JS文件中报错，通常与allowJS一起使用
  "outDir": "./dist", // 指定输出目录
  "rootDir": "./", // 指定输出文件目录(用于输出)，用于控制输出目录结构 编译文件的目录
  "declaration": true, // 生成声明文件，开启后会自动生成声明文件
  "declarationDir": "./file", // 指定生成声明文件存放目录
  "emitDeclarationOnly": true, // 只生成声明文件，而不会生成js文件
  "sourceMap": true, // 生成目标文件的sourceMap文件 代码源文件
  "inlineSourceMap": true, // 生成目标文件的inline SourceMap，inline SourceMap会包含在生成的js文件中
  "declarationMap": true, // 为声明文件生成sourceMap
  "typeRoots": [], // 声明文件目录，默认时node_modules/@types
  "types": [], // 加载的声明文件包
  "removeComments":true, // 删除注释  是否在编译过程中删除文件中的注释
  "noEmit": true, // 不输出文件,即编译后不会生成任何js文件
  "noEmitOnError": true, // 发送错误时不输出任何文件
  "noEmitHelpers": true, // 不生成helper函数，减小体积，需要额外安装，常配合importHelpers一起使用
  "importHelpers": true, // 通过tslib引入helper函数，文件必须是模块
  "downlevelIteration": true, // 降级遍历器实现，如果目标源是es3/5，那么遍历器会有降级的实现
  "strict": true, // 开启所有严格的类型检查
  "alwaysStrict": true, // 在代码中注入'use strict'
  "noImplicitAny": true, // 不允许隐式的any类型
  "strictNullChecks": true, // 不允许把null、undefined赋值给其他类型的变量
  "strictFunctionTypes": true, // 不允许函数参数双向协变
  "strictPropertyInitialization": true, // 类的实例属性必须初始化
  "strictBindCallApply": true, // 严格的bind/call/apply检查
  "noImplicitThis": true, // 不允许this有隐式的any类型
  "noUnusedLocals": true, // 检查只声明、未使用的局部变量(只提示不报错)
  "noUnusedParameters": true, // 检查未使用的函数参数(只提示不报错)
  "noFallthroughCasesInSwitch": true, // 防止switch语句贯穿(即如果没有break语句后面不会执行)
  "noImplicitReturns": true, //每个分支都会有返回值
  "esModuleInterop": true, // 允许export=导出，由import from 导入
  "allowUmdGlobalAccess": true, // 允许在模块中全局变量的方式访问umd模块
  "moduleResolution": "node", // 模块解析策略，ts默认用node的解析策略，即相对的方式导入
  "baseUrl": "./", // 解析非相对模块的基地址，默认是当前目录
  "paths": { // 路径映射，相对于baseUrl
    // 如使用jq时不想使用默认版本，而需要手动指定版本，可进行如下配置
    "jquery": ["node_modules/jquery/dist/jquery.min.js"]
  },
  "rootDirs": ["src","out"], // 将多个目录放在一个虚拟目录下，用于运行时，即编译后引入文件的位置可能发生变化，这也设置可以虚拟src和out在同一个目录下，不用再去改变路径也不会报错
  "listEmittedFiles": true, // 打印输出文件
  "listFiles": true// 打印编译的文件(包括引用的声明文件)
}
 
// 指定一个匹配列表（属于自动指定该路径下的所有ts相关文件）指定编译文件默认是编译当前目录下所有的ts文件
"include": [
   "src/**/*"
],
// 指定一个排除列表（include的反向操作）指定排除的文件
 "exclude": [
   "demo.ts"
],
// 指定哪些文件使用该配置（属于手动一个个指定文件）
 "files": [
   "demo.ts"
]

```


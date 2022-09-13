# 活字格操作 JSON

[JSON](https://www.json.org/json-zh.html)

[使用 JSON - 学习 Web 开发 | MDN (mozilla.org)](https://developer.mozilla.org/zh-CN/docs/Learn/JavaScript/Objects/JSON)



## JSON 序列化和反序列化

序列化：JSON 对象 -> JSON 字符串；

反序列化：JSON 字符串 -> JSON 对象。

[帮你打开活字格更神奇的功能：九十四，JSON反序列化命令实战应用 - 活字格专区 - 专题教程 - 葡萄城产品技术社区 (grapecity.com.cn)](https://gcdn.grapecity.com.cn/forum.php?mod=viewthread&tid=92688&highlight=json)



## 读取 JSON 对象中的内容

[帮你打开活字格更神奇的功能：九十四，JSON反序列化命令实战应用 - 活字格专区 - 专题教程 - 葡萄城产品技术社区 (grapecity.com.cn)](https://gcdn.grapecity.com.cn/forum.php?mod=viewthread&tid=92688&highlight=json)

如何获取一个 JSON 字符串：

- 静态文本；
- HTTP 请求返回（[JSON API免费接口-BeJSON.com](https://www.bejson.com/knownjson/webInterface/)）；
- 返回命令（返回命令会自动将 JSON 对象（如果是）进行序列化）。

如何获取一个 JSON 对象：

- 执行 SQL 命令返回的结果为 JSON 对象，且为一个数组；
- JSON 反序列化命名序列化 JSON 字符串。

**可以将 JSON 对象设置到一个变量名中，通过 "变量名." 操作符访问 JSON 属性，对于数组则可以使用循环命令**，这个操作适用于 JSON 对象，不适用 JSON 字符串，可以借此来判断数据是 JSON 字符串还是 JSON 对象。



导入 JSON 到单元格命令和导入 JSON 到表格命令可以将 JSON 属性对应到对应表格列或单元格上。具体使用见：[JSON数据源 - 葡萄城市场 (grapecity.com.cn)](https://marketplace.grapecity.com.cn/ApplicationDetails?productID=SP2104270001&productDetailID=D2206270021&tabName=Tabs_detail)

## 构造 JSON 数据（字符串）

使用 HTTP 请求，勾选 JSON 序列化。

[帮你打开活字格更神奇的功能：九十六、服务端命令构建主子表的JSON结构 - 活字格专区 - 专题教程 - 葡萄城产品技术社区 (grapecity.com.cn)](https://gcdn.grapecity.com.cn/forum.php?mod=viewthread&tid=133536&highlight=json)

使用返回命令。





### 表格数据转 JSON 字符串

[表格转 JSON 功能。 - 活字格专区 - 产品需求 - 葡萄城产品技术社区 (grapecity.com.cn)](https://gcdn.grapecity.com.cn/forum.php?mod=viewthread&tid=96382)

创建一个服务端命令，参数类型为数组。页面调用服务端命令将表格数据传递过去，服务端命令直接返回传递的参数，相当于把表格数据序列化成 JSON 了。



利用 JS。

[表格转 JSON - 活字格专区 - 求助中心 - 葡萄城产品技术社区 (grapecity.com.cn)](https://gcdn.grapecity.com.cn/showtopic-141258-1-1.html)




### 业务场景

[设计模式之美-争哥-原型模式](https://time.geekbang.org/column/article/200786)

1. 数据库中存储了大约 10 万条“搜索关键词”信息，每条信息包含关键词、关键词被搜索的次数、信息最近被更新的时间等。系统 A 在启动的时候会加载这份数据到内存中，用于处理某些其他的业务需求。为了方便快速地查找某个关键词对应的信息，我们给关键词建立一个散列表索引。
2. 系统 B 用来分析业务日志，统计出关键字被搜索的指标，并定期（比如间隔 10 分钟）更新表中的内容。为了保证系统 A 中数据的实时性（不一定非常实时，但数据也不能太旧），系统 A 需要定期根据数据库中的数据，更新内存中的索引和数据。
3. 任何时刻，系统 A 中的所有数据都必须是同一个版本的，要么都是版本 a，要么都是版本 b，不能有的是版本 a，有的是版本 b。那刚刚的更新方式就不能满足这个要求了。除此之外，我们还要求：在更新内存数据的时候，系统 A 不能处于不可用状态，也就是不能停机更新数据。
### 附录

1. 关键字存储表结构
   
   | 关键字   | 次数  | 更新时间戳 |
   | :------- | :----- | :---------- |
   | 算法    | 2089  | 154850764  |
   | 设计模式 | 1938  | 154850767  |
   | 小争哥   | 13098 | 154850760  |
   
   
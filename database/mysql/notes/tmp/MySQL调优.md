# MySQL 调优

## 总览

## 性能监控

### show profile

- 初步使用

  此工具默认是禁用的，可以通过服务器变量在绘画级别动态的修改，如使用 mysql-cli 开启一个连接，使用以下命令设置开启：	

  ```
  set profiling=1;
  ```

  当设置完成之后，在服务器上执行的所有语句，都会测量其耗费的时间和一些其他状态数据，使用以下命令查看执行 SQL 执行情况：

  ```
  show profiles; // 查看多条 SQL 语句执行情况
  show profile;  // 查看最近一条 SQL 语句执行详细情况
  show profile for query 1;  //  查看指定 ID SQL 语句执行情况
  ```

- show profile 语法结构

  ```
  SHOW PROFILE [type [, type] ... ]
      [FOR QUERY n]
      [LIMIT row_count [OFFSET offset]]
  
  type: {
      ALL
    | BLOCK IO
    | CONTEXT SWITCHES
    | CPU
    | IPC
    | MEMORY
    | PAGE FAULTS
    | SOURCE
    | SWAPS
  }
  ```

  type 用来指定需要查询的信息，其分别代表：

  - ALL：显示所有性能信息。
  - BLOCK IO：显示块 IO 操作的次数。
  - CONTEXT SWITCHES：显示上下文切换次数，被动和主动。
  - CPU：显示用户 CPU 时间、系统 CPU 时间。
  - IPC：显示发送和接受的消息数量。
  - MEMORY：暂未实现
  - PAGE FAULTS：显示页错误数量。
  - SOURCE：显示源码中的函数名称与位置。
  - SWAPS：显示 swap 的次数。

### Performance Schema

MySQL 的 performance schema 用于监控 MySQL Server 在一个较低级别的运行过程中的资源消耗、资源等待等情况，在 MySQL 5.7版本中，默认开启。

```
查看 performance_schema 的属性

mysql> SHOW VARIABLES LIKE 'performance_schema';
+--------------------+-------+
| Variable_name      | Value |
+--------------------+-------+
| performance_schema | ON    |
+--------------------+-------+
1 row in set (0.01 sec)

在配置文件中修改 performance_schema 的属性值，on 表示开启，off 表示关闭
[mysqld]
performance_schema=ON

切换数据库
use performance_schema;

查看当前数据库下的所有表,会看到有很多表存储着相关的信息
show tables;
```

### show processlist

使用 show processlist 查看连接的线程个数，来观察是否有大量线程处于不正常的状态或者其他不正常的特征。

## 设计优化

### 数据类型优化

**尽量使用可以正确存储数据的最小数据类型**：更小的数据类型通常更快，因为它们占用更少的磁盘、内存和 CPU 缓存，并且处理时需要的 CPU 周期更少，但是要确保没有低估需要存储的值的范围，如果无法确认哪个数据类型，就选择你认为不会超过范围的最小类型。

**尽量使用简单的数据类型**：简单数据类型的操作通常需要更少的 CPU 周期，例如：

- 整型比字符操作代价更低，因为字符集和校对规则是字符比较比整型比较更复杂；
- 使用 MySQL 自建类型而不是字符串来存储日期和时间；
- 用整型存储 IP 地址。

**尽量避免 null**：如果查询中包含可为 null 的列，对 MySQL 来说很难优化，因为可为 null的 列使得索引、索引统计和值比较都更加复杂，坦白来说，通常情况下 null 的列改为 not null 带来的性能提升比较小，但是应该尽量避免设计成可为 null 的列。

**具体到某个类型**：

- 整型：可以使用的几种整数类型，TINYINT，SMALLINT，MEDIUMINT，INT，BIGINT 分别使用 8，16，24，32，64 位存储空间，尽量使用满足需求的最小数据类型。

- 字符和字符串：

  - char 长度固定，即每条数据占用等长字节空间，最大长度是 255 个字符，会自动删除末尾的空格，适合用在身份证号、手机号、MD5摘要等定长字符串。

  - varchar 长度可变，可以设置最大长度，最大空间是 65535 个字节，但在使用时尽量选取满足需求的最小长度，varchar(n) 额外使用一个字节来存储 n 的大小，当 n>255 时需要增加一个字节用来保存长度。适合用在存储长度可变的内容，但存储内容很少更新的场景，因为每次更新后都会重算并使用额外存储空间保存长度。

    // TODO ...

    varchar 的存储结构。

    varchar 索引长度限制

    varchar 长度限制

  - text 不设置长度，当不知道属性的最大长度时，适合用 text。

  按照查询速度：char>varchar>text

- 时间：

  - datetime，占用 8 个字节，与时区无关，可保存到毫秒，时间范围 1000-01-01到 9999-12-31。
  - timestamp，占用 4 个字节，时间范围 1970-01-01 到 2038-01-19，精确到秒，采用整形存储，依赖数据库设置的时区。
  - date，占用 3 个字节，时间范围 1000-01-01到 9999-12-31。

- 枚举类型：

  有时可以使用枚举类代替常用的字符串类型，MySQL 存储枚举类型会非常紧凑，会根据列表值的数据压缩到一个或两个字节中，MySQL 在内部会将每个值在列表中的位置保存为整数，并且在表的 .frm 文件中保存“数字-字符串”映射关系。

  使用枚举类型：

  ```sql
  create table enum_test(e enum('fish','apple','dog') not null);
  
  insert into enum_test(e) values('fish'),('dog'),('apple');
  
  select e+0 from enum_test;
  ```

### 合理使用范式和反范式

范式是符合某一种级别的关系模式的集合，表示一个关系内部各属性之间的联系的合理化程度，拿关系型数据库来说，可以理解为一张表的结构所符合某种设计标准的级别。数据库范式是层层递进的，如符合第二范式的必定符合第一范式。

- 第一范式（1NF）：要求数据库表的每一列都是不可分割的原子数据项。拆列。
- 第二范式（2NF）：在第一范式的基础上，消除部分依赖，确保数据库表中的每一列都和主键相关，而不能只与主键的某一部分相关（主要针对联合主键而言）。和主键没有关系的列拆出去，拆表。
- 第三范式（3NF）：在第二范式的基础上，消除传递依赖，确保数据表中的每一列数据都和主键直接相关，而不能间接相关。和主键没有直接关系的拆除去，拆表。

合理的使用范式，但不要过度设计，有时候可以冗余数据，可以避免连表查询，增加查询效率，但使用冗余时要注意要确保冗余数据的一致性，确保全部冗余字段的更新。

```
Note:
视图和物化视图：
视图其实就是提前组织好的一个查询语句，方便用来做查询操作，每次查询视图其实就是执行提前写好的查询语句。
Oracle 中有物化视图的概念，即会将视图的查询结果持久化，但当源数据有更新时，物化视图需要更新，可以选择在源数据修改提交后更新物化视图的数据，也可以选择延迟到擦查询时再更新物化视图，简单来讲，物化视图需要通过更新来保证数据一致性。
```

### 主键选择

- 代理主键：与业务无关，无意义的数字序列或 UUID。

- 自然主键：事物属性中的唯一表示。

  推荐使用代理主键，代理主键和业务无关，因此更容易维护；使用代理主键，通过一种通用主键管理方式，可以减少维护主键代码编写数量，减少系统总体拥有成本。

### 字符集的选择

- 纯拉丁字符能表示的内容，没必要选择 latin1 之外的其他字符编码，因为这会节省大量的存储空间。

- MySQL 的数据类型可以精确到字段，所以当我们需要大型数据库中存放多字节数据的时候，可以通过对不同表不同字段使用不同的数据类型来较大程度减小数据存储量。

  ```
  Note：
  MySQL 的 UTF-8 和 UTF8MB4？
  MySQL 在 5.5.3 之后增加了这个 utf8mb4 的编码，其中 mb4 就是 most bytes 4 的意思，专门用来兼容四字节的 unicode。utf8mb4 是 utf8 的超集，如果以前使用的编码集时 utf8 的话，除了将编码改为 utf8mb4 外不需要做其他转换。
  MySQL 的 utf8 最多支持 3 个字节，如果遇到 4 字节的宽字符就会插入异常了。
  ```

### 存储引擎的选择

![](../images/MySQL存储引擎.png)

## 执行计划

使用 explain + SQL 查看 SQL 语句的执行计划。

### 信息说明

|    Column     |                    Meaning                     |
| :-----------: | :--------------------------------------------: |
|      id       |            The `SELECT` identifier             |
|  select_type  |               The `SELECT` type                |
|     table     |          The table for the output row          |
|  partitions   |            The matching partitions             |
|     type      |                 The join type                  |
| possible_keys |         The possible indexes to choose         |
|      key      |           The index actually chosen            |
|    key_len    |          The length of the chosen key          |
|      ref      |       The columns compared to the index        |
|     rows      |        Estimate of rows to be examined         |
|   filtered    | Percentage of rows filtered by table condition |
|     extra     |             Additional information             |

### id

select 查询的序列号，用来表示查询中执行顺序，序号越大越先执行，序号相同从上到下依次执行。

### select_type

用来区分查询类型。

|     select_type      |                           Meaning                            |
| :------------------: | :----------------------------------------------------------: |
|        SIMPLE        |        Simple SELECT (not using UNION or subqueries)         |
|       PRIMARY        |                       Outermost SELECT                       |
|        UNION         |         Second or later SELECT statement in a UNION          |
|   DEPENDENT UNION    | Second or later SELECT statement in a UNION, dependent on outer query |
|     UNION RESULT     |                      Result of a UNION.                      |
|       SUBQUERY       |                   First SELECT in subquery                   |
|  DEPENDENT SUBQUERY  |      First SELECT in subquery, dependent on outer query      |
|       DERIVED        |                        Derived table                         |
| UNCACHEABLE SUBQUERY | A subquery for which the result cannot be cached and must be re-evaluated for each row of the outer query |
|  UNCACHEABLE UNION   | The second or later select in a UNION that belongs to an uncacheable subquery (see UNCACHEABLE SUBQUERY) |

// TODO ... 实践

例子：

```sql
--SIMPLE：简单的查询，不包含子查询和 union。
explain select * from emp;

--PRIMARY：查询中若包含任何复杂的子查询，最外层查询则被标记为 Primary。
explain select staname,ename supname from (select ename staname,mgr from emp) t join emp on t.mgr=emp.empno ;

--UNION：UNION 后的查询。
explain select * from emp where deptno = 10 union select * from emp where sal >2000;

--DEPENDENT UNION：跟 UNION 类似，此处的 depentent 表示联合结果会受外部表影响。
explain select * from emp e where e.empno  in ( select empno from emp where deptno = 10 union select empno from emp where sal >2000)

--UNION RESULT：联合查询的结果。
explain select * from emp where deptno = 10 union select * from emp where sal >2000;

--SUBQUERY: 在 SELECT 或者 WHERE 子句中包含子查询。
explain select * from emp where sal > (select avg(sal) from emp) ;

--DEPENDENT SUBQUERY：SUBQUERY 的子查询要受到外部表查询的影响。
explain select * from emp e where e.deptno in (select distinct deptno from dept);

--DERIVED: FROM 子句中出现的子查询，也叫做派生类。
explain select staname,ename supname from (select ename staname,mgr from emp) t join emp on t.mgr=emp.empno ;

--UNCACHEABLE SUBQUERY：表示使用子查询的结果不能被缓存。
explain select * from emp where empno = (select empno from emp where deptno=@@sort_buffer_size);
```

### table

对应行正在访问哪一个表的表名或者别名，也可能是临时表或者 UNION 合并结果集：

- 如果是具体的表名，则表明从实际的物理表中获取数据，当然也可以是表的别名。

- 表名是 derivedN 的形式，表示使用了 id 为 N 的查询产生的衍生表

- 当有 union result 的时候，表名是 union n1,n2 等的形式，n1,n2 表示参与 union 的 id。

例子：TODO ...

### partitions

分区匹配。

### type

type 显示的是访问类型，访问类型表示访问数据的方式，最容易想的是全表扫描，直接暴力的遍历一张表去寻找需要的数据，效率非常低下，访问的类型有很多，效率从最好到最坏依次是：

```
system > const > eq_ref > ref > fulltext > ref_or_null > index_merge > unique_subquery > index_subquery > range > index > ALL
```

一般情况下，得保证查询至少达到 range 级别，最好能达到 ref。

例子：

```sql
--all：全表扫描，一般情况下出现这样的 sql 语句而且数据量比较大的话那么就需要进行优化。
explain select * from emp;

--index：全索引扫描，这个比 all 的效率要好，主要有两种情况，一种是当前的查询时覆盖索引，即我们需要的数据在索引中就可以索取，或者是使用了索引进行排序，这样就避免数据的重排序。
explain  select empno from emp;

--range：表示利用索引查询的时候限制了范围，在指定范围内进行查询，这样避免了全索引扫描，适用的操作符： =, <>, >, >=, <, <=, IS NULL, BETWEEN, LIKE, or IN()。
explain select * from emp where empno between 7000 and 7500;

--index_subquery：利用索引来关联子查询，不再扫描全表。
explain select * from emp where emp.job in (select job from t_job);

--unique_subquery：该连接类型类似与 index_subquery，使用的是唯一索引。
explain select * from emp e where e.deptno in (select distinct deptno from dept);

--ref_or_null：对于某个字段即需要关联条件，也需要 null 值的情况下，查询优化器会选择这种访问方式。
explain select * from emp e where  e.mgr is null or e.mgr=7369;

--ref：使用了非唯一性索引进行数据的查找。
create index idx_3 on emp(deptno);
explain select * from emp e,dept d where e.deptno =d.deptno;

--eq_ref：使用唯一性索引进行数据查找。
explain select * from emp,emp2 where emp.empno = emp2.empno;

--const：至多有一个匹配行。
explain select * from emp where empno = 7369;
```

### possible_keys

显示可能应用在这张表中的索引，一个或多个，查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询实际使用。

### key

实际使用的索引，如果为 null，表示没有使用索引，查询中若使用了覆盖索引，则该索引和查询的 select 字段重叠。

### key_len

表示索引中使用的字节数，通过该列计算查询中使用的索引的长度。在不损失精确性的情况下，长度越短越好显示的是索引字段的最大长度，并非实际使用长度。

### ref

显示哪些列或常量被用于查找索引列上的值，显示表的列或 const。

```sql
explain select * from emp,dept where emp.deptno = dept.deptno and emp.deptno = 10;
```

### rows

根据表的统计信息及索引使用情况，大致估算出找出所需记录需要读取的行数，此参数很重要，反应了 SQL 扫描的数据量，在完成目的的情况下越少越好。

### filtered

返回结果的行数占读取行数的百分比，值越大越好。

### extra

包含不适合在其他列中显示但十分重要的额外信息，常见的值如下：

```sql
--using filesort：说明无法利用索引进行排序，只能利用排序算法进行排序，会有额外的消耗。
explain select * from emp order by sal;

--using temporary：建立临时表来保存中间结果，查询完成之后把临时表删除。
explain select ename,count(*) from emp where deptno = 10 group by ename;

--using index：使用了覆盖索引。
explain select deptno,count(*) from emp group by deptno limit 10;

--using where：where 子句用于限制哪一行。
explain select * from t_user where id = 1;

--using join buffer：使用连接缓存，情况没有模拟出来

--impossible where：where 子句的结果总是 false。
explain select * from emp where empno = 7469;
```



- null: 没有用到额外的附加条件

- Using filesort：查询语句中包含了oder by 语句，索引无法完成排序，数据量小的时候在内存中完成排序，否者在磁盘中完成排序。

- Using temporary：使用到了临时表。

- Using index：使用覆盖索引进行数据返回（Innodb 引擎才存在），不会进行回表查找。

- Using where：查询语句中存在 where 范围查找，会回表查找数据。

- Using where Using index：覆盖索引中存在范围查找，但是也不会进行回表，直接在索引上就能拿到数据。

- Using index condition：跟 Using where using index 有一些差别，它的查找用到了联合索引，查找的数据有不在索引中的字段，所以会进行回表查找数据。

  索引下推

## 索引优化

### 索引基本知识

#### 索引优点

- 大大减少了服务器需要扫描的数据量。
- 帮助服务器避免排序和临时表，索引已经排好顺序了。
- 将随机 IO 变成顺序 IO。

#### 索引的用处

- 快速查找匹配 WHERE 子句的行。
- 查找特定索引列的 min 或 max 值。
- 如果排序或分组时在可用索引的最左前缀上完成的，则对表进行排序和分组。
- 在某些情况下，可以优化查询以检索值而无需查询数据行。

#### 索引的分类

主键索引、唯一索引、普通索引、全文索引、组合索引。

#### 索引数据结构

#### 索引一些概念

- 回表：使用普通索引检索数据，最终需要回到主键索引拿去完整数据，这一过程叫做回表。

- 覆盖索引：

  我们知道**普通索引的 B+ 树叶子节点存储的是主键的值**，若我们通过普通索引查询的数据只包含**主键和索引本身字段**，则通过普通索引就能拿到需要的字段信息，无需回表，这一现象被称为覆盖索引。

  覆盖索引只需要通过索引就能够取得需要的全部数据，无需回表，减少了 IO 次数和量级，此外由于索引是按照列值的顺序存储的，覆盖索引很适合范围查询。

- 最左前缀原则：

  联合索引先根据第一个字段排序，如果第一个字段有相同的，就按照第二个字段排序。最左前缀原则是指，**最左前缀可以是联合索引的最左 N 个字段，也可以是字符串索引的最左 M 个字符**，满足这个条件的就可以使用索引，不满足则不走索引，如模糊查询 `like 'K%'`  可以走索引，而  `like '%K'` 不能走索引。

  利用最左前缀原则可以指导我们设计联合索引的顺序：

  - 第一原则是，如果通过调整联合索引字段顺序，可以少维护一个索引，那么这个顺序往往就是需要优先考虑采用的。此外，将选择性更好的列放在联合索引的前面的位置，因为选择性更高的列意味着每次利用索引可以过滤更多的数据。
  - 第二个原则考虑空间，使用联合索引无法满足需求，需要将为联合缩影中的字段单独创建索引，那么选择占用空间小的字段。

- 索引下推：

  当我们使用索引作为过滤条件，同时也使用了一般列作为过滤条件，那么整个检索的顺序应该是这样的：
  
  - 先通过索引过滤出满足条件的数据。
  - 再将满足索引条件的一个一个回表，拿到全部数据和另外一个条件做对比和过滤。
  
  索引下推是指，在以上描述的过程中，若索引为联合索引，普通的列是组合联合索引中的某个字段，但不满足最左端原则，所以不会走联合索引，那么可以在索引遍历过程中，对索引中包含的字段先做判断，直接过滤掉不满足条件的记录，减少回表次数（即不满足普通列条件的不会回表，只对满足条件的进行回表操作），这一过程叫做索引下推。例如：
  
  ```
  Note:
  MySQL 5.6 之前没有索引下推机制。
  ```

#### 索引匹配方式

- 全值匹配：全值匹配指的是和索引中的所有列进行匹配。
- 匹配最左前缀：只匹配前面的几列。
- 匹配列前缀：可以匹配某一列的值的开头部分。
- 匹配范围值：可以查找某一个范围的数据。
- 精确匹配某一列并范围匹配另外的列：可以查询第一列的全部和第二列的部分。
- 覆盖索引，不用回表：查询的时候只需要访问索引，不需要访问数据行，本质上就是覆盖索引。

### 哈希索引

Memory 存储引擎使用哈希索引。

### 组合索引

组合索引或叫做联合索引的数据组织形式，数据以每一行索引字段为一组，排顺方式为：先按照第一个索引字段排序，第一个字段值相同的再按照第二个字段排序，依次类推。

// TODO ...

### 聚簇索引和非聚簇索引

聚簇索引指数据行跟相邻的键值紧凑的存储在一起，非聚簇索引则指数据文件跟索引文件分开存放。

页分裂和页合并。

### 

### 索引优化

- OLAP OLTP

- 基数（Cardinality）统计和关联

  基数属性

  HyperLogLog 

  查看基数：

  - 基数：select count(distinct name) from user;
  - 查看表的索引信息，其中 Cardinality 列就是该索引列的基数信息，Cardinality 是通过采样统计出来的基数值，是个估计值。

  - 选择性：select count(distinct name) /count(*) from user;

- 当使用索引列进行查询的时候尽量不要使用表达式，把计算放到业务层而不是数据库层。

  ```sql
  select actor_id from actor where actor_id=4;
  select actor_id from actor where actor_id+1=5;
  ```

- 尽量使用主键查询，而不是其他索引，因为主键查询不会触发回表查询，所以表应该尽量建立主键。

- 前缀索引：对字符类型字段的前几个字符建立索引。

  有时候需要索引很长的字符串，这会让索引变的大且慢，通常情况下可以使用某个列开始的部分字符串，这样大大的节约索引空间。前缀索引虽然节约了索引空间，但可能带来索引的可选择性降低的问题。

  索引的可选择性：不重复的索引值和数据表记录总数的比值，索引的选择性越高则查询效率越高，因为选择性更高的索引可以在查找的时候过滤掉更多的行。

- 使用索引来排序和分组

  索引本身是有序的，如果可以使用索引来排序会大大节省排序时间。

  MySQL 有两种方式可以生成有序的结果，通过排序操作或者按索引顺序扫描，如果 explain 出来的 type 列的值为 index，则说明 MySQL 使用了索引扫描来做排序。

  使用索引排序的前提条件。

  > 只有当索引的列顺序和 ORDER BY 子句的顺序完全一致，并且所有列的排序方向（倒序或正序）都一样时，MySQL 才能够使用索引来对结果做排序。如果查询需要关联多张表，则只有当 ORDER BY 子句引用的字段全部为第一个表时，才能使用索引做排序。也需要满足索引的最左前缀要求（例外：前导列为常量的时候）。——《高性能MySQL》

  更多可参考：[MySQL利用索引排序实践 - SegmentFault 思否](https://segmentfault.com/a/1190000014205981)

- union all、in、or 都能够使用索引，如果它们的执行结果相同，推荐使用 in。如：

  ```
  explain select * from actor where actor_id = 1 union all select * from actor where actor_id = 2;
  explain select * from actor where actor_id in (1,2);
  explain select * from actor where actor_id = 1 or actor_id =2;
  ```

- 强制类型转换会全表扫描，如使用字符类型当作数字类型列的过滤条件。

  ```
  explain select * from user where id=13800001234; 
  explain select * from user where id='13800001234';
  ```

- 更新十分频繁，索引选择性低的列不适合建立索引，区分度可以使用 count(distinct(列名))/count(*) 来计算，一般区分度在 80% 以上的时候就可以建立索引，索引选择性越低，通过索引能够过滤的值就越少，不能完全发挥索引精确查询快的优势。

- 能使用 limit 的时候尽量使用 limit，比如知道了只会查询一条数据，使用 limit 能够再一定程度上避免全表扫描，limit 1，一条满足直接返回。

## 索引监控

show status like 'Handler_read%';

- Handler_read_first：读取索引第一个条目的次数。
- Handler_read_key：通过index获取数据的次数。
- Handler_read_last：读取索引最后一个条目的次数。
- Handler_read_next：通过索引读取下一条数据的次数。
- Handler_read_next：通过索引读取下一条数据的次数。
- Handler_read_prev：通过索引读取上一条数据的次数。
- Handler_read_rnd_next：从数据节点读取下一条数据的次数。

## 查询优化

### 优化数据量访问

查询性能低下的原因是查询的数据量太多，所以每次查询我们都应该尽量只查询我们需要的数据，主要体现在以下几个方面：

- 查询时指定明确查询的列，尤其时在多表关联的情况下。
- 查询时如果指导需要的行数，添加 limit 指定查询的行数。
- 重复查询相同的数据，对于不怎么变化，却要经常访问的数据，可以添加缓存已得到更高的查询效率。

### 语法解析和预处理

MySQL 通过关键字将 SQL 语句进行解析，并生成一颗解析树，MySQL 解析器将进行语法规则验证和解析查询，例如验证使用使用了错误的关键字或者顺序是否正确等等，预处理器会进一步检查解析树是否合法，例如表名和列名是否存在，是否有歧义，还会验证权限等等。

calcite SQL 语法解析

### 查询优化器

当语法树没有问题之后，相应的要由优化器将其转成执行计划，一条查询语句可以使用非常多的执行方式，最后都可以得到对应的结果，但是不同的执行方式带来的效率是不同的，优化器的最主要目的就是要选择最有效的执行计划。MySQL 使用的是基于成本的优化器，在优化的时候会尝试预测一个查询使用某种查询计划时候的成本，并选择其中成本最小的一个。除了基于成本优化之外还有基于规则优化。

- 查询上次查询需要的数据页

  ```sql
  select count(*) from film_actor;
  show status like 'last_query_cost';
  ```

- 优化策略

  - 静态优化：直接对解析树进行分析，并完成优化。

  - 动态优化：动态优化与查询的上下文有关，也可能跟取值、索引对应的行数有关。

    MySQL 对查询的静态优化只需要一次，但对动态优化在每次执行时都需要重新评估。

- 优化器的优化类型

  - 重新定义关联表的顺序，数据表的关联并不总是按照在查询中指定的顺序进行，决定关联顺序时优化器很重要的功能。
  - 将外连接转化成内连接，内连接的效率要高于外连接。内连接，内连接查询操作列出与连接条件匹配的数据行；外连接，返回到查询结果集合中的不仅包含符合连接条件的行，而且还包括左表(左外连接或左连接))、右表(右外连接或右连接)或两个边接表(全外连接)中的所有数据行。
  - 优化 count()、min()、max()，索引时有序的，索引两端的就对应最小和最大值。
  - 等值传播，如果两个列的值通过等式关联，那么 MySQL 能够把其中一个列的 where 条件传递到另一个上。

- 排序优化。

  - 两次传输排序

    第一次数据读取是将需要排序的字段读取出来，然后进行排序，第二次是将排好序的结果按照需要去读取数据行。这种方式效率比较低，原因是第二次读取数据的时候因为已经排好序，需要去读取所有记录而此时更多的是随机 IO，读取数据成本会比较高。两次传输的优势，在排序的时候存储尽可能少的数据，让排序缓冲区可以尽可能多的容纳行数来进行排序操作。

  - 单次传输排序

    先读取查询所需要的所有列，然后再根据给定列进行排序，最后直接返回排序结果，此方式只需要一次顺序 IO 读取所有的数据，而无须任何的随机 IO，问题在于查询的列特别多的时候，会占用大量的存储空间，无法存储大量的数据。

  当需要排序的列的总大小超过 `max_length_for_sort_data` 定义的字节，MySQL 会选择双次排序，反之使用单次排序，此参数的值可以设置。

### 关联查询

### 关联算法

- Simple Nested-Loop Join 简单嵌套循环连接

  ![](../images/SimpleNestedLoopJoin.png)

- Index Nested-Loop Join 索引嵌套循环连接

  ![](../images/IndexedNestedLoopJoin.png)

- Block Nested-Loop Join  块索引嵌套连接

  ​	![](../images/BlockNestedLoopJoin.png)

Join Buffer 默认大小时 256KB

- 内连接和外连接

  内连接，内连接查询操作列出与连接条件匹配的数据行；

  外连接，返回到查询结果集合中的不仅包含符合连接条件的行，而且还包括左表(左外连接或左连接))、右表(右外连接或右连接)或两个边接表(全外连接)中的所有数据行。

- join 后用 on 和 where

  以左连接为例，使用 left join 时，on 后的条件仅仅用来判断连接是否成立的条件，若成立，连接上右表对应数据，不成立，连接 null；若使用 where 则表示对连接的后的结果进行过滤。总的来说，on 时对连接过程中，连接是否匹配的条件处理，where 则时在连接结束后对结果集进行过滤。

### 特定查询优化

// TODO ...

- count

  - count(*)、count(1)、count(id) 

    `COUNT(expr)` ，返回 SELECT 语句检索的行中 expr 的值不为 NULL 的数量，结果是一个`BIGINT `值。其中 `COUNT(常量)` 和 `COUNT(*)` 表示的是直接查询符合条件的数据库表的行数，而 `COUNT(列名)` 表示的是查询符合条件的列的值**不为 NULL **的行数。对于 `COUNT(常量)` 和 `COUNT(*)`，MySQL 的优化是完全一样的，根本不存在谁更快， `COUNT(列名)` 行全表扫描，判断指定字段的值是否为 NULL，不为 NULL 则累加，性能要比前两者慢。

  - MyISAM 对 count(*) 的优化 

    MyISAM 做了一个简单的优化，把表的总行数单独记录下来，如果执行 `count(*)` 时可以直接返回，**前提是不能有 where 条件**。MyISAM 是表级锁，不会有并发的行操作，所以查到的结果是准确的。

  - InnoDB 对  count(*) 的优化 

    从 MySQL 8.0.13 开始，如果没有额外的子句(如 WHERE 或 GROUP BY)，**MySQL 会优先选择最小的非聚簇索引来扫表。**

- exist 和 in

  in 使用内表驱动，先查内表的结果，再和外表做笛卡尔乘积，然后按照条件筛选。

  exist 使用外表驱动，先查外表，再循环查询内表，根据筛选条件留下满足的记录。

  内表较小适合用 in，外表较小适合用 exist。

- 优化关联查询
  
- 确保 on 或者 using 子句中的列上有索引，在创建索引的时候就要考虑到关联的顺序。详细来讲，当表 A 和表 B 使用列 C 关联的时候，如果优化器的关联顺序是B、A，那么就不需要再B表的对应列上建上索引，因为 B 表列上的索引并没有用上，没有用到的索引只会带来额外的负担，一般情况下来说，只需要在关联顺序中的第二个表的相应列上创建索引。
  
- 优化 limit 分页

  在很多应用场景中我们需要将数据进行分页，一般会使用 limit 加上偏移量的方法实现，同时加上合适的 order by 的子句，如果这种方式有索引的帮助，效率通常不错，否则的化需要进行大量的文件排序操作，还有一种情况，当偏移量非常大的时候，前面的大部分数据都会被抛弃，这样的代价太高。要优化这种查询的话，要么是在页面中限制分页的数量，要么优化大偏移量的性能，比如使用尽可能地使用覆盖索引，而不是查询所有的列。如下所示：

  ```sql
  explain select film_id,description from film order by title limit 50,5
  explain select film.film_id,film.description from film inner join (select film_id from film order by title limit 50,5) as lim using(film_id);
  ```

- 优化 union 

  除非确实需要服务器消除重复的行，否则一定要使用 union all，因此没有 all 关键字，MySQL 会在查询的时候给临时表加上 distinct 的关键字，这个操作的代价很高。

- 用户自定义变量

  // TODO ...

## 分区表

对于用户而言，分区表是一个独立的逻辑表，但是底层是由多个物理子表组成。分区表对于用户而言是一个完全封装底层实现的黑盒子，对用户而言是透明的，从文件系统中可以看到多个使用 `#` 分隔命名的表文件。

MySQL  在创建表时使用 partition by 子句定义每个分区存放的数据，在执行查询的时候，优化器会根据分区定义过滤那些没有我们需要数据的分区，这样查询就无须扫描所有分区。

分区的主要目的是将数据安好一个较粗的力度分在不同的表中，这样可以将相关的数据存放在一起。

### 分区表的应用场景

- 表非常大以至于无法全部都放在内存中，或者只在表的最后部分有热点数据，其他均是历史数据。
- 分区表的数据更容易维护，体现在，批量删除大量数据可以使用清除整个分区的方式，可以对一个独立分区进行优化、检查、修复等操作。
- 分区表的数据可以分布在不同的物理设备上，从而高效地利用多个硬件设备。

### 分区表的限制

一个表最多只能有 1024 个分区，在 5.7 版本的时候可以支持 8196 个分区。如果分区字段中有主键或者唯一索引的列，那么所有主键列和唯一索引列都必须包含进来。

### 分区表的底层原理

分区表由多个相关的底层表实现，这个底层表也是由句柄对象标识，我们可以直接访问各个分区。存储引擎管理分区的各个底层表和管理普通表一样（所有的底层表都必须使用相同的存储引擎），从存储引擎的角度来看，底层表和普通表没有任何不同，比如分区表的索引只是在各个底层表上各自加上一个完全相同的索引，不需要考虑分区的概念。

分区表的操作按照以下的操作逻辑进行：

- **select查询**

  当查询一个分区表的时候，分区层先打开并锁住所有的底层表，优化器先判断是否可以过滤部分分区，然后再访问各个分区的数据。

- **insert操作**

  当写入一条记录的时候，分区层先打开并锁住所有的底层表，然后确定哪个分区接受这条记录，再将记录写入对应底层表。

- **delete操作**

  当删除一条记录时，分区层先打开并锁住所有的底层表，然后确定数据对应的分区，最后对相应底层表进行删除操作。

- **update操作**

  当更新一条记录时，分区层先打开并锁住所有的底层表，MySQL 先确定需要更新的记录再哪个分区，然后取出数据并更新，再判断更新后的数据应该再哪个分区，最后对底层表进行写入操作，并对源数据所在的底层表进行删除操作。

### 分区的类型

分区的类型，即如何分区。

- 范围分区：根据列值在给定范围内将行分配给分区。

  ```sql
  CREATE TABLE members (
      firstname VARCHAR(25) NOT NULL,
      lastname VARCHAR(25) NOT NULL,
      username VARCHAR(16) NOT NULL,
      email VARCHAR(35),
      joined DATE NOT NULL
  )
  PARTITION BY RANGE( YEAR(joined) ) (
      PARTITION p0 VALUES LESS THAN (1960),
      PARTITION p1 VALUES LESS THAN (1970),
      PARTITION p2 VALUES LESS THAN (1980),
      PARTITION p3 VALUES LESS THAN (1990),
      PARTITION p4 VALUES LESS THAN MAXVALUE
  );
  ```

- 列表分区：类似于按范围分区，区别在于列表分区是基于列值一个离散值集合来分配分区。

  ```sql
  CREATE TABLE employees (
      id INT NOT NULL,
      fname VARCHAR(30),
      lname VARCHAR(30),
      hired DATE NOT NULL DEFAULT '1970-01-01',
      separated DATE NOT NULL DEFAULT '9999-12-31',
      job_code INT,
      store_id INT
  )
  PARTITION BY LIST(store_id) (
      PARTITION pNorth VALUES IN (3,5,6,9,17),
      PARTITION pEast VALUES IN (1,2,10,11,19,20),
      PARTITION pWest VALUES IN (4,12,13,14,18),
      PARTITION pCentral VALUES IN (7,8,15,16)
  );
  ```

- HASH 分区：使用用户指定的 HASH 函数，并将得到的 HASH 值按照分区数量取模来分配分区。

  ```sql
  CREATE TABLE employees (
      id INT NOT NULL,
      fname VARCHAR(30),
      lname VARCHAR(30),
      hired DATE NOT NULL DEFAULT '1970-01-01',
      separated DATE NOT NULL DEFAULT '9999-12-31',
      job_code INT,
      store_id INT
  )
  PARTITION BY HASH(store_id)
  PARTITIONS 4;
  ```

- KEY 分区：和 HASH 分区不同的是 HASH 函数不在有用户指定，而是由 MySQL Server 指定，并将得到的 HASH 值按照分区数量取模来分配分区。

  ```sql
  CREATE TABLE k1 (
      id INT NOT NULL PRIMARY KEY,
      name VARCHAR(20)
  )
  PARTITION BY KEY()
  PARTITIONS 2;
  
  CREATE TABLE k1 (
      id INT NOT NULL,
      name VARCHAR(20),
      UNIQUE KEY (id)
  )
  PARTITION BY KEY()
  PARTITIONS 2;
  
  CREATE TABLE tm1 (
      s1 CHAR(32) PRIMARY KEY
  )
  PARTITION BY KEY(s1)
  PARTITIONS 10;
  ```

## 服务器参数设置

### 常规参数

- datadir=/var/lib/mysql：数据文件存放的目录。
- socket=/var/lib/mysql/mysql.sock：mysql.socket 表示 server 和 client 在同一台服务器，并且使用 localhost 进行连接，就会使用 socket 进行连接。
- pid_file=/var/lib/mysql/mysql.pid：存储 pid。
- port=3306：服务的端口号。
- default_storage_engine=InnoDB：默认存储引擎
- skip-grant-tables：当忘记用户名密码的时候，可以在 MySQL 配置文件中配置该参数，跳过权限表验证，不需要密码即可登录。

### 字符集参数

- character_set_client：客户端数据的字符集。

- character_set_connection：MySQL 处理客户端发来的信息时，会把这些数据转换成连接的字符集格式。

- character_set_results：MySQL 发送给客户端的结果集所用的字符集。

- character_set_database：数据库默认的字符集。

- character_set_server：MySQL Server 默认字符集。

### 连接参数

- max_connections：MySQL 的最大连接数，如果数据库的并发连接请求比较大，应该调高该值。
- max_user_connections：限制每个用户的连接个数。
- back_log：MySQL 能够暂存的连接数量，当 MySQL 的线程在一个很短时间内得到非常多的连接请求时，就会起作用，如果 MySQL 的连接数量达到max_connections 时，新的请求会被存储在堆栈中，等待某一个连接释放资源，如果等待连接的数量超过 back_log，则不再接受连接资源。
- wait_timeout：MySQL 在关闭一个非交互的连接之前需要等待的时长。
- interactive_timeout：MySQL 再关闭一个交互连接之前需要等待的秒数。

### 日志参数

- log_error：指定错误日志文件名称，用于记录服务器在运行中发生任何严重错误时的相关信息。

- log_bin：指定二进制日志文件名称，用于记录对数据造成更改的所有查询语句。

- binlog_do_db：指定记录更新二进制日志的数据库。

- binlog_ignore_db：指定不记录更新二进制日志的数据库。

- sync_binlog：指定多少次写日志后同步磁盘。

- general_log：是否开启查询日志记录。

- general_log_file：指定查询日志文件名。

- slow_query_log：是否开启慢查询日志记录。

- slow_query_log_file：指定慢查询日志文件名称，用于记录耗时比较长的查询语句。

- long_query_time：设置慢查询的时间，超过这个时间的查询语句才会当作慢查询记录。

- log_slow_admin_statements：是否将管理语句写入慢查询日志。

### 缓存参数

- key_buffer_size：索引缓存区的大小（只对 MySIAM 起作用）。

- query cache

  - query_cache_size：查询缓存的大小。

  - query_cache_limit：超出此大小的查询将不被缓存。

  - query_cache_min_res_unit：缓存块最小大小。

  - query_cache_type：缓存类型，0 表示禁用；1 表示将缓存所有结果，除非 sq l语句中使用 sql_no_cache 禁用查询缓存，；2 表示只缓存 select 语句中通过 sql_cache 指定需要缓存的查询。

    ```sql
    show status like '%Qcache%';查看缓存的相关属性
    Qcache_free_blocks：缓存中相邻内存块的个数，如果值比较大，那么查询缓存中碎片比较多。
    Qcache_free_memory：查询缓存中剩余的内存大小。
    Qcache_hits：表示有多少此命中缓存。
    Qcache_inserts：表示多少次未命中而插入。
    Qcache_lowmen_prunes：多少条 query 因为内存不足而被移除 cache。
    Qcache_queries_in_cache：当前 cache 中缓存的 query 数量。
    Qcache_total_blocks：当前 cache 中 block 的数量。
    ```

- sort_buffer_size：每个需要排序的线程分派该大小的缓冲区。

- max_allowed_packet=32M：限制 server 接受的数据包大小。

- join_buffer_size=2M：表示关联缓存的大小。

- thread_cache_size：
  - Threads_cached：代表当前此时此刻线程缓存中有多少空闲线程。
  - Threads_connected：代表当前已建立连接的数量。
  - Threads_created：代表最近一次服务启动，已创建现成的数量，如果该值比较大，那么服务器会一直再创建线程。
  - Threads_running：代表当前激活的线程数。

### InnoDB

- innodb_buffer_pool_size：该参数指定大小的内存来缓冲数据和索引，最大可以设置为物理内存的 80%。

- innodb_flush_log_at_trx_commit：主要控制 innodb 将 log buffer 中的数据写入日志文件并 flush 磁盘的时间点，值分别为 0，1，2，这个参数设置成 1 的时候，表示每次事务的 redo log 都直接持久化到磁盘。

- innodb_thread_concurrency：设置 innodb 线程的并发数，默认为 0 表示不受限制，如果要设置建议跟服务器的 cpu 核心数一致或者是 cpu 核心数的两倍。

- innodb_log_buffer_size：确定日志文件所用的内存大小，以 M 为单位。

- innodb_log_file_size：确定数据日志文件的大小，以 M 为单位。

- innodb_log_files_in_group：以循环方式将日志文件写到多个文件中。

- read_buffer_size：MySQL 读入缓冲区大小，对表进行顺序扫描的请求将分配到一个读入缓冲区。

- read_rnd_buffer_size：MySQL 随机读的缓冲区大小。

- innodb_file_per_table：确定为每张表分配一个新的文件。

## 总结

### 参考链接

连老师的课。
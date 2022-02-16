# Java 编码规范

编码规范可以让团队在编码风格上形成共识，统一的编码风格有利于代码在团队之间阅读和维护，减少沟通成本。这里基于 Checkstyle 校验规则制定了编码规范，并且提供了对应的 Checkstyle 配置文件。

规范制定参考了：

- [Blinkfox Java 编程风格指南](https://blinkfox.github.io/java-style/)；
- [Apache ShenYu Code Conduct · Apache ShenYu](https://shenyu.apache.org/zh/community/code-conduct/)。

## 常规

### 编码格式

编码格式需要为 `UTF-8`。

### 制表符（[FileTabCharacter](https://checkstyle.sourceforge.io/config_whitespace.html#FileTabCharacter)）

文件中不能有制表符（`'\t'`）。

### 文件行数（[FileLength](https://checkstyle.sourceforge.io/config_sizes.html#FileLength)）

单个文件行数最多不能超过 2000，单个文件行数过长不利于代码的阅读和维护，可考虑将过长的文件分割成多个单独的小文件。

### 单行字符数（[LineLength](https://checkstyle.sourceforge.io/config_sizes.html#LineLength)）

单行字符数量不能超过 120，且忽略含有 package、import、http 等关键字的行。

### 换行符（[NewlineAtEndOfFile](https://checkstyle.sourceforge.io/config_misc.html#NewlineAtEndOfFile)）

文件内容换行符号统一为 `LF(Unix and macOS \n)`，并且**文件要以空行结尾**。

IDEA 设置文件换行符：

```
File -> Settings -> Editor -> Code Sytle -> Line Separator
```

IDEA 修改文件换行符：

```
选择要修改的文件或=目录 -> File -> File Properties -> Line Separators -> LF
```

### 单行匹配（[RegexpSingleline](https://checkstyle.sourceforge.io/config_regexp.html#RegexpSingleline)）

文件中不能使用 `System.out.println()`。

## 命名规约（[Naming Conventions](https://checkstyle.sourceforge.io/config_naming.html)）

命名规范。

### 包名（[PackageName](https://checkstyle.sourceforge.io/config_naming.html#PackageName)）

**包名全部小写，连续的单词只是简单地连接起来，不使用下划线**。

例如：使用 `com.example.helloworld`，而不是 `com.example.helloWorld` 或者 `com.example.hello-world`。

### 类型名称（[TypeName](https://checkstyle.sourceforge.io/config_naming.html#TypeName)）

类名、接口名、枚举类名、注解名都以大驼峰（`UpperCamelCase`）风格编写。

### 方法名称（[MethodName](https://checkstyle.sourceforge.io/config_naming.html#MethodName)）

方法名称采用驼峰法则，首字符小写。

### 泛型名称

接口，类，方法泛型名以单个的大写字母命名。

### 常量名称（[ConstantName](https://checkstyle.sourceforge.io/config_naming.html#ConstantName)）

常量名的命名模式为 `CONSTANT_CASE`，字母全部大写，使用下划线来分隔单词，且常量的名字通常是名词或名词短语。

每个常量都是一个静态 `final` 字段，其内容是不可变的，且没有可检测的副作用。这包括原始类型、字符串、不可变类型和不可变类型的不可变集合。如果任何一个实例的观测状态是可变的，则它肯定不会是一个常量。**不可变对象不一定是常量**。

```java
// 下面是常量
static final int NUMBER = 5;
static final ImmutableList<String> NAMES = ImmutableList.of("Ed", "Ann");
static final ImmutableMap<String, Integer> AGES = ImmutableMap.of("Ed", 35, "Ann", 32);
static final Joiner COMMA_JOINER = Joiner.on(','); // because Joiner is immutable
static final SomeMutableType[] EMPTY_ARRAY = {};
enum SomeEnum { ENUM_CONSTANT }

// 下面的情况不是常量
static String nonFinal = "non-final";
final String nonStatic = "non-static";
static final Set<String> mutableCollection = new HashSet<String>();
static final ImmutableSet<SomeMutableType> mutableElements = ImmutableSet.of(mutable);
static final ImmutableMap<String, SomeMutableType> mutableValues =
    ImmutableMap.of("Ed", mutableInstance, "Ann", mutableInstance2);
static final Logger logger = Logger.getLogger(MyClass.getName());
static final String[] nonEmptyArray = {"these", "can", "change"};
```

### 变量名称

变量命名要符合驼峰法则，且首字母小写。

### 大写字母缩写（[AbbreviationAsWordInName](https://checkstyle.sourceforge.io/config_naming.html#AbbreviationAsWordInName)）

对于一些使用大写字母缩写命名的标识符，名称中的缩写（连续大写字母）长度不能超过 6。

## 长度规格（[Size Violations](https://checkstyle.sourceforge.io/config_sizes.html)）

代码行数要求。

### 匿名内部类（[AnonInnerLength](https://checkstyle.sourceforge.io/config_sizes.html#AnonInnerLength)）

匿名内部类代码行数不能超过 20 行。

### 方法（[MethodLength](https://checkstyle.sourceforge.io/config_sizes.html#OuterTypeNumber)）

方法代码行数不能超过 60 行。

### 外部类（[OuterTypeNumber](https://checkstyle.sourceforge.io/config_sizes.html#OuterTypeNumber)）

一个 `.java` 文件只允许有一个外部类。

## 空白（[Whitespace](https://checkstyle.sourceforge.io/config_whitespace.html)）

代码中空格使用规范。

### 方法参数（[MethodParamPad](https://checkstyle.sourceforge.io/config_whitespace.html#MethodParamPad)）

方法名称和参数左括号之间不能有空格，如：

```java
public class Test {
  public Test() { // OK
    super(); // OK
  }

  public Test (int aParam) { // Violation - '(' is preceded with whitespace
    super (); // Violation - '(' is preceded with whitespace
  }

  public void method() {} // OK

  public void methodWithVeryLongName
    () {} // Violation - '(' is preceded with whitespace
}
```

### 括号（[ParenPad](https://checkstyle.sourceforge.io/config_whitespace.html#ParenPad)）

小括号两边的空格要求，即左括号右边第一个字符不能是空格，右括号左边最后一个字符不能是空格，如下：

```java
class Foo {

  int n;

  public void fun() {  // OK
    bar( 1);  // violation, space after left parenthesis
  }

  public void bar(int k ) {  // violation, space before right parenthesis
    while (k > 0) {  // OK
    }

    Test obj = new Test(k);  // OK
  }
}
```

### 运算符换行（[OperatorWrap](https://checkstyle.sourceforge.io/config_whitespace.html#OperatorWrap)）

运算符换行要在出现在下一行的行首。

```java
String s = "Hello" +
    "World"; // violation, '+' should be on new line

String s = "Hello" 
    + "World"; // OK
```

### 其他符号换行（[SeparatorWrap](https://checkstyle.sourceforge.io/config_whitespace.html#SeparatorWrap)）

- 在英文点号 `'.'` 处换行，且英文点号需在下一行的行首，即 `'nl'`；
- 在方法引用双冒号 `'::'` 处换行，且双冒号需在下一行的行首，即 `'nl'`。
- 在英文逗号 `','` 处换行，且英文逗号需在上一行的行尾，即 `'eol'`；
- 在省略号 `'...'` 处换行，且省略号需在上一行的行尾，即 `'eol'`；
- 在数组中括号 `'[]'` 处换行，且中括号需在上一行的行尾，即 `'eol'`；

### 符号前空白（[NoWhitespaceBefore](https://checkstyle.sourceforge.io/config_whitespace.html#NoWhitespaceBefore)）

一些符号前不能有空格，如 `++`：

```java
int foo;
foo ++; // violation, whitespace before '++' is not allowed
foo++; // OK
for (int i = 0 ; i < 5; i++) {}  // violation
           // ^ whitespace before ';' is not allowed
```

### 空语句块（[WhitespaceAround](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround)）

空构造方法，空方法，空注解，空循环等的大括号中必须有空白，通常是空行。如：

```java
public void test() {} // violation

public void test() {  // OK
    
}                    
```

### 符号后空格（[WhitespaceAfter](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAfter)）

比如用来分割方法参数的 `','` 之后需要有一个空格。

### 模块分割（[EmptyLineSeparator](https://checkstyle.sourceforge.io/config_whitespace.html#EmptyLineSeparator)）

不同的模块之间是需要有空行进行分割，模块包括：包语句、导入语句、静态导入、类定义、方法定义等等，模块之间需要用**一个空行**分割。

具体规则：

- 不同属性之间可以不用空行分割，属性和方法，方法和方法直接需要一个空行分割，且用来分割的空行有且只能有一行；
- 类声明后与下面的变量或方法之间需要空一行，类末尾部分的右大括号上行代码之间不需要空行；

- 方法体内部若需要分割代码逻辑，用来分割不同逻辑代码之间的空行只能有一行。

## 包导入规则（[Imports](https://checkstyle.sourceforge.io/config_imports.html)）

### `import *`（[AvoidStarImport](https://checkstyle.sourceforge.io/config_imports.html#AvoidStarImport)）

不能使用 `import * `。

IDEA `import * ` 数量设置：

```
File -> Settings -> Editor -> Code Style -> Java -> Imports
增加 Class count to use import with '*' 和 Names count to use static import with '*' 的数量。
```

### 禁止导入（[IllegalImport](https://checkstyle.sourceforge.io/config_imports.html#IllegalImport)）

禁止导入所有 sun.* 包，因为包含直接调用 sun.* 包的程序“不能保证在所有 Java 兼容平台上都能正常工作。

### 冗余导入（[RedundantImport](https://checkstyle.sourceforge.io/config_imports.html#RedundantImport)）

不允许冗余导入，比如显式地导入了 `java.lang` 下的包。

### 没有使用导入（[UnusedImports](https://checkstyle.sourceforge.io/config_imports.html#UnusedImports)）

不能导入没有使用过的包。

IDEA 自动导入设置：

```
File -> Settings -> General -> Auto Import
选中 Optimize imports on the fly 和 Add unambiguous imports on the fly
```

IDEA 导入包快捷键：`Crtl + Alt + O`。

### 导入包的顺序（[CustomImportOrder](https://checkstyle.sourceforge.io/config_imports.html#CustomImportOrder)）

按 ASCII 码顺序对导入包进行分组排序。这里将导入的包分成了四组，安照先后顺序分别为：静态导入，Java 类库（java.* 或 javax.*）导入、第三方 Jar 导入和项目 Jar 导入。

IDEA 导入包排序设置：

```
File –> Settings –> Editor –> Code Style –> Java –> Imports -> Import Layout

调整顺序如下所示：
import static all other imports
<blank line>
import java.*
import javax.*
<blank line>
import all other imports
<blank line>
import com.example.* // com.example 为项目相关公司域名
```

checkstyle 配置

```xml
<module name="CustomImportOrder">
    <property name="sortImportsInGroupAlphabetically" value="true"/>
    <property name="separateLineBetweenGroups" value="true"/>
    <property name="customImportOrderRules"  value="STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###SPECIAL_IMPORTS"/>
    <property name="specialImportsRegExp" value="^com.example\."/>
</module>
```

效果如下：

```java
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.example.project.A;
import com.example.project.service.B;
```

## 注解（[Annotations](https://checkstyle.sourceforge.io/config_annotation.html)）

### 注解位置（[AnnotationLocation](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationLocation)）

注解必须独立成行（局部变量注解除外），包括：类，接口，枚举，方法等处的注解定义。

### 注解样式（[AnnotationUseStyle](https://checkstyle.sourceforge.io/config_annotation.html#AnnotationUseStyle)）

注解样式需要符合规范。

### Override 注解（[MissingOverride](https://checkstyle.sourceforge.io/config_annotation.html#MissingOverride)）

重写父类方法或方法实现时必须要使用 `@Overwrite` 注解。

### Deprecated 注解（[MissingDeprecated](https://checkstyle.sourceforge.io/config_annotation.html#MissingDeprecated)）

对于废弃的代码，必须使用 `@Deprecated ` 注解，并且附上 `@deprecated Javadoc` 注释。

### 抑制警告（[SuppressWarnings](https://checkstyle.sourceforge.io/config_annotation.html#SuppressWarnings)）

## 修饰符（[Modifiers](https://checkstyle.sourceforge.io/config_modifier.html)）

### 修饰符顺序（[ModifierOrder](https://checkstyle.sourceforge.io/config_modifier.html#ModifierOrder)）

修饰符的顺序需符合 Java 语言规范，规范的顺序应该是：

1. `public`
2. `protected`
3. `private`
4. `abstract`
5. `default`
6. `static`
7. `sealed`
8. `non-sealed`
9. `final`
10. `transient`
11. `volatile`
12. `synchronized`
13. `native`
14. `strictfp`

### 冗余的修饰符（[RedundantModifier](https://checkstyle.sourceforge.io/config_modifier.html#RedundantModifier)）

禁止使用冗余的修饰符，比如在接口方法前使用 `public abstract static` 修饰。

### 需显示指明的修饰符（[ClassMemberImpliedModifier](https://checkstyle.sourceforge.io/config_modifier.html#ClassMemberImpliedModifier)）

比如嵌套在类内部的枚举类，接口，或者记录类，需要使用 static 修饰。

> 注：记录类是从 Java 14 开始引入，用来定义不可变对象，更多可参考 [记录类 | 廖雪峰的官方网站 (liaoxuefeng.com)](https://www.liaoxuefeng.com/wiki/1252599548343744/1331429187256353)。

## 编码细节（[Coding](https://checkstyle.sourceforge.io/config_coding.html)）

### 数组声名（[ArrayTrailingComma](https://checkstyle.sourceforge.io/config_coding.html#ArrayTrailingComma)）

### 大括号初始化器（AvoidDoubleBraceInitialization）

禁止使用大括号初始化器，如：

```java
Set<String> countries = new HashSet<String>();                
countries.add("A");
countries.add("B");
countries.add("C");

// 可以使用大括号初始化器，可以将容器的创建和初始化结合在一条语句中，如下：
Set<String> countries = new HashSet<String>() {
    {
        add("India");
        add("USSR");
        add("USA");
    }
};
```

> 关于大括号初始化器更多可参考：[Java Double Brace Initialization | Baeldung](https://www.baeldung.com/java-double-brace-initialization)

如果你使用 Java 9 以上的 JDK 版本，你可以使用以下方法替代大括号初始化器：

```java
List<String> list = List.of("A", "B", "C");
Set<String> set = Set.of("A", "B", "C");
```

### [CovariantEquals](https://checkstyle.sourceforge.io/config_coding.html#CovariantEquals)

### 声明顺序（[DeclarationOrder](https://checkstyle.sourceforge.io/config_coding.html#DeclarationOrder)）

类、接口内部的声名顺序需符合 [Java 规范](https://checkstyle.org/styleguides/sun-code-conventions-19990420/CodeConventions.doc2.html#a1852)，正确的声名顺序应该为：

1. 类（静态）变量，并且按照 `public -> protected -> default -> private` 这样的顺序排列；
2. 实例变量，并且按照 `public -> protected -> default -> private` 这样的顺序排列；
3. 构造方法；
4. 其他方法。

此外对于方法声名顺序，同一个类中被调用的方法应该紧接着调用方法的下方声名，便于代码的阅读。

### [DefaultComesLast](https://checkstyle.sourceforge.io/config_coding.html#DefaultComesLast)

### [EqualsAvoidNull](https://checkstyle.sourceforge.io/config_coding.html#EqualsAvoidNull)

### [EqualsHashCode](https://checkstyle.sourceforge.io/config_coding.html#EqualsHashCode)

### [FallThrough](https://checkstyle.sourceforge.io/config_coding.html#FallThrough)

### final 局部变量（[FinalLocalVariable](https://checkstyle.sourceforge.io/config_coding.html#FinalLocalVariable)）

局部变量如果在后续使用过程中没有被更改，需要声名为 final。

### [IllegalCatch](https://checkstyle.sourceforge.io/config_coding.html#IllegalCatch)

catch 语句中不能捕获 Error、Exception、RuntimeException、Throwable、java.lang.Error、java.lang.Exception、java.lang.RuntimeException、java.lang.Throwable 对象。

### [IllegalInstantiation](https://checkstyle.sourceforge.io/config_coding.html#IllegalInstantiation)

对于某些类，最好通过工厂方法创建实例而不是调用构造函数。

一个简单的例子是 java.lang.Boolean 类。 出于性能原因，最好使用预定义的常量 TRUE 和 FALSE。 构造函数调用应替换为对 Boolean.valueOf() 的调用。

### [IllegalThrows](https://checkstyle.sourceforge.io/config_coding.html#IllegalThrows)

不能抛出 Error、RuntimeException、Throwable、java.lang.Error、java.lang.RuntimeException、java.lang.Throwable 异常或错误。

### [IllegalTokenText](https://checkstyle.sourceforge.io/config_coding.html#IllegalTokenText)

### [IllegalType](https://checkstyle.sourceforge.io/config_coding.html#IllegalType)

### [MissingSwitchDefault](https://checkstyle.sourceforge.io/config_coding.html#MissingSwitchDefault)

swith 语句需要在最后使用 default 语句。

### [ModifiedControlVariable](https://checkstyle.sourceforge.io/config_coding.html#ModifiedControlVariable)

### [MultipleStringLiterals](https://checkstyle.sourceforge.io/config_coding.html#MultipleStringLiterals)

代码中不能出现相同的字面量字符串。

### [MultipleVariableDeclarations](https://checkstyle.sourceforge.io/config_coding.html#MultipleVariableDeclarations)

每个变量声明需要在其自己的语句中并在其自己的行上。

### for 循环嵌套（[NestedForDepth](https://checkstyle.sourceforge.io/config_coding.html#NestedForDepth)）

for 嵌套不能过深，最多三层循环。

```java
for(int i=0; i<10; i++) {
    for(int j=0; j<i; j++) {
        for(int k=0; k<j; k++) {
            for(int l=0; l<k; l++) { // violation, max allowed nested loop number is 2
            }
        }
    }
}

for(int i=0; i<10; i++) {
    for(int j=0; j<i; j++) {
        for(int k=0; k<j; k++) { // ok
        }
    }
}
```

### if 嵌套（[NestedIfDepth](https://checkstyle.sourceforge.io/config_coding.html#NestedIfDepth)）

if 嵌套不能过深，最多三层 if，如：

```java
if (true) {  // violation, max allowed nested if number is 2
   if (true) {
      if (true) {
         if (true) {} // OK
         else {}
      }
   }
}

if (true) {
   if (true) {
       if (true) {} // OK
       else {}
   }
}
```

对于 if 优先使用卫语句。

> 注：卫语句可用于子进程的提前退出（early exit），这是结构化程序设计的一种常见偏离，可删除一层嵌套使得代码更扁平：把 `if guard { ... }` 替代为：`if not guard: return; ...`
>
> 更多可参考：[卫语句 (juejin.cn)](https://juejin.cn/post/6844903764336312333)

### [NestedTryDepth](https://checkstyle.sourceforge.io/config_coding.html#NestedTryDepth)

try catch 语句最多两层，如：

```java
try { 
    try {
        try { // violation, current depth is 2, default max allowed depth is 1
        } catch (Exception e) {
        }
    } catch (Exception e) {
    }
} catch (Exception e) {
}

try {
    try { // OK, current depth is 1, default max allowed depth is also 1
    } catch (Exception e) {
    }
} catch (Exception e) {
}
```

### [NoClone](https://checkstyle.sourceforge.io/config_coding.html#NoClone)

### [NoFinalizer](https://checkstyle.sourceforge.io/config_coding.html#NoFinalizer)

不能重写对象的 finalize 方法。

> finalize 方法在 GC（垃圾回收器）决定回收一个不被其他对象引用的对象时调用。子类覆写 finalize 方法来处置系统资源或是负责清除操作。
>
> 更多可参考：[finalize() | 简书 (jianshu.com)](https://www.jianshu.com/p/aa7ae747ff25)

不建议使用重新 finalize 方法，具体原因可参考 `Effective Java: Programming Language Guide Third Edition by Joshua Bloch, §8`。

### 重载方法（[OverloadMethodsDeclarationOrder](https://checkstyle.sourceforge.io/config_coding.html#OverloadMethodsDeclarationOrder)）

重载的方法需要在一起声明。

### [PackageDeclaration](https://checkstyle.sourceforge.io/config_coding.html#PackageDeclaration)

### [ParameterAssignment](https://checkstyle.sourceforge.io/config_coding.html#ParameterAssignment)

不能够在函数内部修改本身参数并返回：

```java
class MyClass {
  int methodOne(int parameter) {
    if (parameter <= 0 ) {
      throw new IllegalArgumentException("A positive value is expected");
    }
    parameter -= 2;  // violation
    return parameter;
  }

  int methodTwo(int parameter) {
    if (parameter <= 0 ) {
      throw new IllegalArgumentException("A positive value is expected");
    }
    int local = parameter;
    local -= 2;  // OK
    return local;
  }
}
```

尽量减少参数输出的使用，信息通过函数参数输入，通过返回值输出，而不是通过参数。

> 以下内容来自代码整洁之道：
>
> 输出参数就是指将参数传递进函数中后，修改参数的内容，最后再使用修改后的参数。使用输出参数会让人难以理解。读函数时，我们惯于认为信息通过参数输入函数，通过返回值从函数中输出。因此我们不太期待信息通过参数输出。因此如果要对输入参数进行转换，转换结果就应该体现在返回值上，即使只是简单地返回输入参数也好。

```java
public void test() {
    // 返回值输出
    final Map<String, Object> map = getMap();
    
    // 参数输出
    final Map<String, Object> map2 = new HashMap();
	getMap(map2);
}

private Map<String, Object> getMap() {
    Map<String, Object> map = new HashMap<>();
    // put something into map
    return map;
}	

private void getMap(final Map<String, Object> map) { // 输出参数
	// put something into map
}
```

### [OneStatementPerLine](https://checkstyle.sourceforge.io/config_coding.html#OneStatementPerLine)

每行应该只有一行用分号结尾的语句。

### 简化布尔表达式（[SimplifyBooleanExpression](https://checkstyle.sourceforge.io/config_coding.html#SimplifyBooleanExpression)）

布尔表达式不能过于复杂，可以声名具有含义的变量来表达布尔值，是的代码更容易被阅读。

> 布尔的短路
>
> 布尔运算的一个重要特点是短路运算。如果一个布尔运算的表达式能提前确定结果，则后续的计算不再执行，直接返回结果。
>
> 比如：false && b，true || b，都会造成短路现象。
>
> 如果声明的布尔类型的变量在布尔运算进行赋值，短路是否会不会执行赋值运算相关代码，如：
>
> 

### [SimplifyBooleanReturn](https://checkstyle.sourceforge.io/config_coding.html#SimplifyBooleanReturn)

简化布尔返回，如下：

```java
if (valid()) {
  return false;
} else {
  return true;
}

// 直接返回会更好
return !valid();
```

### [StringLiteralEquality](https://checkstyle.sourceforge.io/config_coding.html#StringLiteralEquality)

String 对象的比较不能用 “==“，应该使用 equals 方法。

### [SuperClone](https://checkstyle.sourceforge.io/config_coding.html#SuperClone)

### [SuperFinalize](https://checkstyle.sourceforge.io/config_coding.html#SuperFinalize)

### 多余的括号（[UnnecessaryParentheses](https://checkstyle.sourceforge.io/config_coding.html#UnnecessaryParentheses)）

避免使用多余的括号。

### 多余的分号

避免在类的结尾使用多余的分号，更多可参考：[UnnecessarySemicolonAfterOuterTypeDeclaration](https://checkstyle.sourceforge.io/config_coding.html#UnnecessarySemicolonAfterOuterTypeDeclaration)。

```java
class A {

   class Nested {

   }; // OK, nested type declarations are ignored
}; // violation

interface B {

}; // violation

enum C {

}; // violation
```

避免在类的成员后使用多余的分号，更多可参考：[UnnecessarySemicolonAfterTypeMemberDeclaration](https://checkstyle.sourceforge.io/config_coding.html#UnnecessarySemicolonAfterTypeMemberDeclaration)。

```java
class A {
    ; // violation, standalone semicolon
    {}; // violation, extra semicolon after init block
    static {}; // violation, extra semicolon after static init block
    A(){}; // violation, extra semicolon after constructor definition
    void method() {}; // violation, extra semicolon after method definition
    int field = 10;; // violation, extra semicolon after field declaration

    {
        ; // no violation, it is empty statement inside init block
    }

    static {
        ; // no violation, it is empty statement inside static init block
    }

    void anotherMethod() {
        ; // no violation, it is empty statement
        if(true); // no violation, it is empty statement
    }
}
```

避免在枚举类内部使用多余的分号，只枚举常量，不需要分号，更多可参考：[UnnecessarySemicolonInEnumeration](https://checkstyle.sourceforge.io/config_coding.html#UnnecessarySemicolonInEnumeration)。

```java
enum One {
    A,B; // violation
}
enum Two {
    A,B,; // violation
}
enum Three {
    A,B(); // violation
}
enum Four {
    A,B{}; // violation
}
enum Five {
    A,
    B
    ; // violation
}


enum Normal {
    A,
    B,
    ; // required ";", no violation
    Normal(){}
}
enum NoSemicolon {
    A, B // only enum constants, no semicolon required
}
```

避免在 try-catch-resources 中多余的分号，更多可参考：[UnnecessarySemicolonInTryWithResources](https://checkstyle.sourceforge.io/config_coding.html#UnnecessarySemicolonInTryWithResources)。

```java
class A {
    void method() throws IOException {
        try(Reader r1 = new PipedReader();){} // violation
        try(Reader r4 = new PipedReader();Reader r5 = new PipedReader()
        ;){} // violation
        try(Reader r6 = new PipedReader();
            Reader r7
                   = new PipedReader();
        ){}
    }
}
```

### [VariableDeclarationUsageDistance](https://checkstyle.sourceforge.io/config_coding.html#VariableDeclarationUsageDistance)

## 空语句块（[Block Checks](https://checkstyle.sourceforge.io/config_blocks.html)）

### 空语句块（[EmptyBlock](https://checkstyle.sourceforge.io/config_blocks.html#EmptyBlock)）

不允许有空语句块，包括：while、do、for、if 等等。

### 空 catch 块（[EmptyCatchBlock](https://checkstyle.sourceforge.io/config_blocks.html#EmptyCatchBlock)）

不允许有空的 catch 语句块。

### 不允许使用嵌套语句块（[AvoidNestedBlocks](https://checkstyle.sourceforge.io/config_blocks.html#AvoidNestedBlocks)）

代码中不允许使用嵌套语句块。如：

```java
public void foo() {
  int myInteger = 0;
  {                      // violation
    myInteger = 2;
  }
  System.out.println("myInteger = " + myInteger);

  switch (a) {
    case 1:
      {                    // violation
        System.out.println("Case 1");
        break;
      }
    case 2:
      System.out.println("Case 2");     // OK
      break;
  }
}
```

### 使用大括号（[NeedBraces](https://checkstyle.sourceforge.io/config_blocks.html#NeedBraces)）

需要使用双大括号，包括：while、do、for、if、else 等。

### 左大括号（[LeftCurly](https://checkstyle.sourceforge.io/config_blocks.html#LeftCurly)）

 需要使用左大括号 `'{'` 及之前的代码须在一行，包括：类定义、Lambda、for、finally、if 等等。

### 右大括号（[RightCurly](https://checkstyle.sourceforge.io/config_blocks.html#RightCurly)）

需要使用右大括号 `'}'` 及之后的代码须在同一行，包括：try、catch、finally、if、else、do 等等。

## Javadoc Comments

### 注解顺序（[AtclauseOrder](https://checkstyle.sourceforge.io/config_javadoc.html#AtclauseOrder)）

Javadoc 注解标记的先后顺序，推荐的注解顺序如下所示：

```java
/**
* Some javadoc. // OK
*
* @param Some javadoc. // OK
* @return Some javadoc. // OK
* @throws Some javadoc. // OK
* @deprecated Some javadoc. // OK
*/
```

### [NonEmptyAtclauseDescription](https://checkstyle.sourceforge.io/config_javadoc.html#NonEmptyAtclauseDescription)

Javadoc 注解标记需要有说明内容。

### [JavadocParagraph](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocParagraph)

Javadoc 中的段落需要符合规范。

### [JavadocStyle](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocStyle)

需要使用正确格式的 Javadoc 注释。

### [JavadocTagContinuationIndentation](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocTagContinuationIndentation)

Javadoc 中 '@' 注解标签的缩进，换行时需要缩进 4 格。

### [SingleLineJavadoc](https://checkstyle.sourceforge.io/config_javadoc.html#SingleLineJavadoc)

单行的 Javadoc，不能忽略行内标签。

### [SummaryJavadoc](https://checkstyle.sourceforge.io/config_javadoc.html#SummaryJavadoc)

 Javadoc 中摘要语句片段不能有不建议使用的短语，摘要需以英文点号 `'.'` 结尾。

### [JavadocMethod](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocMethod)

Javadoc 方法注释，对于 public 和  protected，不能缺失 @param 和 @return 的注解标记。

## 杂项（[Miscellaneous](https://checkstyle.sourceforge.io/config_misc.html)）

### [ArrayTypeStyle](https://checkstyle.sourceforge.io/config_misc.html#ArrayTypeStyle)

需强制使用 Java 风格，即中括号紧跟在类型后面，如：`'String[] arr'`。

### [UpperEll](https://checkstyle.sourceforge.io/config_misc.html#UpperEll)

在初始化长整型的变量时，末尾需要添加 `L`，如：

```java
class Test {
  long var1 = 508987; // OK
  long var2 = 508987l; // violation
  long var3 = 508987L; // OK
}
```

### [AvoidEscapedUnicodeCharacters](https://checkstyle.sourceforge.io/config_misc.html#AvoidEscapedUnicodeCharacters)

避免转义的 Unicode 字符，允许各种转义字符和对其进行尾部注释。

### [FinalParameters](https://checkstyle.sourceforge.io/config_misc.html#FinalParameters)

方法、构造函数、catch 和 for-each 块的参数需被声名为 final。 不检查接口、抽象和本地方法，因为final 关键字对接口、抽象和本地方法参数没有意义，因为没有可以修改参数的代码。

对于声名的局部变量，在使用过程中没有改变的话，也应声名为 final。

在方法的算法执行期间更改参数的值可能会造成混淆，应该避免。 让 Java 编译器阻止这种编码风格的一个好方法是将参数声明为 final。

### [Indentation](https://checkstyle.sourceforge.io/config_misc.html#Indentation)

基础缩进是 4 个空格，抛出异常和换行是 8 个空格。

### [TodoComment](https://checkstyle.sourceforge.io/config_misc.html#TodoComment)

对 todo 进行相关说明。

```java
i++; // TODO: do differently in future   // violation
i++; // todo: do differently in future   // OK
```

## Checkstyle 配置文件

下面给出 Checkstype 配置文件，配置文件的配置项顺序基本上和文档里罗列的规则是一直的，所以关于下面配置文件中各个配置项的含义可对照文档以了解其配置规则的含义，也可以点击文档中给出的配置项链接去详细了解，配置文件如下所示：

```xml
<?xml version="1.0"?>

<!DOCTYPE module PUBLIC "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
        "http://checkstyle.sourceforge.net/dtds/configuration_1_3.dtd">
<module name="Checker">
    <property name="charset" value="UTF-8"/>
    <property name="severity" value="error"/>
    <property name="fileExtensions" value="java, properties, xml"/>

    <module name="FileTabCharacter">
        <property name="eachLine" value="true"/>
    </module>
    <module name="FileLength">
        <property name="max" value="2000"/>
    </module>
    <module name="NewlineAtEndOfFile">
        <property name="lineSeparator" value="lf"/>
    </module>
    <module name="RegexpSingleline">
        <property name="format" value="System\.out\.println"/>
        <property name="message" value="Prohibit invoking System.out.println in source code !"/>
    </module>
    <module name="LineLength">
        <property name="fileExtensions" value="java"/>
        <property name="max" value="120"/>
        <property name="ignorePattern" value="^package.*|^import.*|a href|href|http://|https://|ftp://"/>
    </module>

    <module name="TreeWalker">
        <!-- Naming Conventions -->
        <module name="PackageName">
            <property name="format" value="^[a-z]+(\.[a-z][a-z0-9]*)*$"/>
        </module>
        <module name="TypeName"/>
        <module name="MethodName"/>
        <module name="InterfaceTypeParameterName"/>
        <module name="ClassTypeParameterName"/>
        <module name="MethodTypeParameterName"/>
        <module name="ConstantName"/>
        <module name="StaticVariableName"/>
        <module name="MemberName"/>
        <module name="LocalVariableName"/>
        <module name="LocalFinalVariableName"/>
        <module name="ParameterName"/>
        <module name="CatchParameterName"/>
        <module name="AbbreviationAsWordInName">
            <property name="allowedAbbreviationLength" value="6"/>
        </module>
        <!-- Size Violations -->
        <module name="AnonInnerLength"/>
        <module name="MethodLength"/>
        <module name="OuterTypeNumber"/>
        <!-- Whitespace -->
        <module name="MethodParamPad"/>
        <module name="ParenPad"/>
        <module name="OperatorWrap"/>
        <module name="SeparatorWrap">
            <property name="id" value="SeparatorWrapDot"/>
            <property name="tokens" value="DOT"/>
            <property name="option" value="nl"/>
        </module>
        <module name="SeparatorWrap">
            <property name="id" value="SeparatorWrapMethodRef"/>
            <property name="tokens" value="METHOD_REF"/>
            <property name="option" value="nl"/>
        </module>
        <module name="SeparatorWrap">
            <property name="id" value="SeparatorWrapComma"/>
            <property name="tokens" value="COMMA"/>
            <property name="option" value="EOL"/>
        </module>
        <module name="SeparatorWrap">
            <property name="id" value="SeparatorWrapEllipsis"/>
            <property name="tokens" value="ELLIPSIS"/>
            <property name="option" value="EOL"/>
        </module>
        <module name="SeparatorWrap">
            <property name="id" value="SeparatorWrapArrayDeclarator"/>
            <property name="tokens" value="ARRAY_DECLARATOR"/>
            <property name="option" value="EOL"/>
        </module>
        <module name="NoWhitespaceBefore"/>
        <module name="WhitespaceAround"/>
        <module name="WhitespaceAfter"/>
        <module name="EmptyLineSeparator">
            <property name="allowNoEmptyLineBetweenFields" value="true"/>
            <property name="allowMultipleEmptyLines" value="false"/>
            <property name="allowMultipleEmptyLinesInsideClassMembers" value="false"/>
        </module>
        <!-- Imports -->
        <module name="AvoidStarImport"/>
        <module name="IllegalImport"/>
        <module name="RedundantImport"/>
        <module name="UnusedImports"/>
        <module name="CustomImportOrder">
            <property name="sortImportsInGroupAlphabetically" value="true"/>
            <property name="separateLineBetweenGroups" value="true"/>
            <property name="customImportOrderRules"
                      value="STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###SPECIAL_IMPORTS"/>
            <property name="specialImportsRegExp" value="^com.rhymesun\."/>
        </module>
        <!-- Annotations -->
        <module name="AnnotationLocation">
            <property name="id" value="AnnotationLocationMostCases"/>
            <property name="tokens"
                      value="CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, RECORD_DEF, COMPACT_CTOR_DEF"/>
        </module>
        <module name="AnnotationLocation">
            <property name="id" value="AnnotationLocationVariables"/>
            <property name="tokens" value="VARIABLE_DEF"/>
            <property name="allowSamelineMultipleAnnotations" value="true"/>
        </module>
        <module name="AnnotationUseStyle"/>
        <module name="MissingOverride"/>
        <module name="MissingDeprecated"/>
        <module name="SuppressWarnings"/>
        <!-- Modifiers -->
        <module name="ModifierOrder"/>
        <module name="RedundantModifier"/>
        <module name="ClassMemberImpliedModifier" />
        <!-- Coding -->
        <module name="ArrayTrailingComma"/>
        <module name="AvoidDoubleBraceInitialization"/>
        <module name="CovariantEquals"/>
        <module name="DeclarationOrder"/>
        <module name="DefaultComesLast"/>
        <module name="OverloadMethodsDeclarationOrder"/>
        <module name="EqualsAvoidNull"/>
        <module name="EqualsHashCode"/>
        <module name="FallThrough"/>
        <module name="FinalLocalVariable"/>
        <module name="IllegalCatch"/>
        <module name="IllegalInstantiation"/>
        <module name="IllegalThrows"/>
        <module name="IllegalTokenText">
            <property name="tokens" value="STRING_LITERAL, CHAR_LITERAL"/>
            <property name="format"
                      value="\\u00(09|0(a|A)|0(c|C)|0(d|D)|22|27|5(C|c))|\\(0(10|11|12|14|15|42|47)|134)"/>
            <property name="message"
                      value="Consider using special escape sequence instead of octal value or Unicode escaped value."/>
        </module>
        <module name="IllegalType">
            <property name="tokens" value="METHOD_DEF,PARAMETER_DEF,VARIABLE_DEF"/>
        </module>
        <module name="MissingSwitchDefault"/>
        <module name="ModifiedControlVariable"/>
        <module name="MultipleVariableDeclarations"/>
        <module name="MultipleStringLiterals"/>
        <module name="NestedForDepth">
            <property name="max" value="2"/>
        </module>
        <module name="NestedIfDepth">
            <property name="max" value="2"/>
        </module>
        <module name="NestedTryDepth"/>
        <module name="NoClone"/>
        <module name="NoFinalizer"/>
        <module name="OneStatementPerLine"/>
        <module name="PackageDeclaration"/>
        <module name="ParameterAssignment"/>
        <module name="SimplifyBooleanExpression"/>
        <module name="SimplifyBooleanReturn"/>
        <module name="StringLiteralEquality"/>
        <module name="SuperClone"/>
        <module name="SuperFinalize"/>
        <module name="UnnecessaryParentheses"/>
        <module name="UnnecessarySemicolonAfterOuterTypeDeclaration"/>
        <module name="UnnecessarySemicolonAfterTypeMemberDeclaration"/>
        <module name="UnnecessarySemicolonInEnumeration"/>
        <module name="UnnecessarySemicolonInTryWithResources"/>
        <module name="VariableDeclarationUsageDistance"/>
        <!-- Block Checks -->
        <module name="EmptyBlock"/>
        <module name="EmptyCatchBlock">
            <property name="exceptionVariableName" value="expected|ignore"/>
        </module>
        <module name="AvoidNestedBlocks"/>
        <module name="NeedBraces"/>
        <module name="LeftCurly"/>
        <module name="RightCurly"/>
        <!-- Javadoc Comments -->
        <module name="AtclauseOrder">
            <property name="tagOrder" value="@param, @return, @throws, @deprecated, @author"/>
        </module>
        <module name="NonEmptyAtclauseDescription"/>
        <module name="JavadocParagraph"/>
        <module name="JavadocStyle"/>
        <module name="JavadocTagContinuationIndentation"/>
        <module name="SingleLineJavadoc"/>
        <module name="SummaryJavadoc"/>
        <module name="JavadocMethod">
            <property name="accessModifiers" value="protected"/>
            <property name="tokens" value="METHOD_DEF, ANNOTATION_FIELD_DEF"/>
        </module>
        <!-- Miscellaneous -->
        <module name="ArrayTypeStyle"/>
        <module name="UpperEll"/>
        <module name="AvoidEscapedUnicodeCharacters">
            <property name="allowEscapesForControlCharacters" value="true"/>
            <property name="allowByTailComment" value="true"/>
            <property name="allowNonPrintableEscapes" value="true"/>
        </module>
        <module name="FinalParameters"/>
        <module name="Indentation"/>
        <module name="TodoComment"/>
    </module>
</module>
```

项目中添加 Maven 依赖，指定 checkstyle 配置文件，在 Maven validate 生命阶段会执行 checkstyle 检查，Maven 依赖如下：

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>${maven.checkstyle.plugin.version}</version>
    <dependencies>
        <dependency>
            <groupId>com.puppycrawl.tools</groupId>
            <artifactId>checkstyle</artifactId>
            <version>${checkstyle.version}</version>
        </dependency>
    </dependencies>
    <configuration>
        <configLocation>journey-checks.xml</configLocation>
        <encoding>UTF-8</encoding>
        <consoleOutput>true</consoleOutput>
        <failsOnError>true</failsOnError>
    </configuration>
    <executions>
        <execution>
            <id>validate</id>
            <phase>validate</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### IDEA checkstyle 插件

1. 安装 CheckStyle-IDEA 插件；

2. 导入 checkstyle 配置：

   ```
   File -> Settings -> Tools -> CheckStyle -> Configuration File
   点击 + 导入上述创建好的配置文件
   ```

3. 在 IDEA 中点开 `CheckStyle` 窗口，选中之前添加的 checkstyle 配置，之后就可以对某个具体类、模块或项目进行检查了。

## 总结

本文给出了基于 Checkstyle 校验项的编码规范，借助于 Checkstyle 插件，在编码过程中可以自动提示一些不符合编码规范的编码行为。CheckStyle 还有更多其它的校验项，这里使用的仅为其中一部分，项目可以基于本文提出的配置文件增加或减少配置项，约定好最适合自己编码规范。

### 参考链接

- [Blinkfox Java 编程风格指南](https://blinkfox.github.io/java-style)

- [Apache ShenYu Code Conduct · Apache ShenYu](https://shenyu.apache.org/zh/community/code-conduct/)

- [Checkstyle – Configuration (sourceforge.io)](https://checkstyle.sourceforge.io/config.html)


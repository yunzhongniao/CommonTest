# CommonTest
代码片段
<!-- common-test-menu -->
- [Content](#content)
  * [1 spring boot](#1-spring-boot)
  * [2 swagger ui](#2-swagger-ui)
  * [3 word处理](#3-word--)
    + [docx4j](#docx4j)
    + [poi](#poi)
    + [poi-tpl](#poi-tpl)
      - [chart 设置关联信息](#chart-------)
  * [4 load property source](#4-load-property-source)
    + [变量类型](#----)
  * [5 optional](#5-optional)
  * [6 Junit5](#6-junit5)
  * [7 mybatis](#7-mybatis)
  * [8 文件操作](#8-----)
  * [9 python invoke](#9-python-invoke)
  * [10 shell](#10-shell)
    + [how to use command ip](#how-to-use-command-ip)
  * [11 多线程](#11-多线程)
    + [线程池管理](#线程池管理)
    + [ThreadPoolFactory](#ThreadPoolFactory)
  * [12 集合](#12-集合)
    + [list array转换](#list-array转换)
    + [线程安全的集合](#线程安全的集合)

<!-- common-test-menu -->

# Content
目录生成地址：https://ecotrust-canada.github.io/markdown-toc/
## 1 spring boot 

## 2 swagger ui
版本：swagger ui2  
pom.xml引入依赖  
配置文件：org.yunzhong.CommonTest.SwaggerConfig  

## 3 word处理
### docx4j
source path: org.yunzhong.CommonTest.controller.docx4j  
demo apis:  
- print docx archive  
- replace main documnet  
- replace chart  
- replace table  

### poi

### poi-tpl

#### chart 设置关联信息
wps设置：点击图表，右侧出现的浮框选择"设置图表区域格式"-》右侧出现的配置框中，在顶端的下拉框中选择"图表区"-》选择“大小与属性”-》填写“可选文字”的"标题"，格式为：{{chartname}}。  
代码中变量Map，以chartname作为key。  

## 4 load property source
### 变量类型
类型包括：sql-single, sql-obj, sql-multi, static, SpEL  
sql-single:单个返回结果的单个数据。  例如当前日期。  
sql-obj：单行返回结果。例如学生信息，列：idnum，name，sex，age，...。可以通过param.property访问。  
sql-multi：多行返回结果。表格，图表的数据格式。  
static: 常量  
SpEL: Spring Expression Language   

## 5 optional
仿照Optional，支持多个属性的多级null校验  
实现类：org.yunzhong.CommonTest.util.OptionalUtil  

## 6 Junit5
demo类：OptionalUtilTest  
demo的注解：@DisplayName, @BeforeAll, @AfterAll, @Nested, @Test
assert方法：org.junit.jupiter.api.Assertions


## 7 mybatis


## 8 文件操作
### 将流转成String的几种方法
org.yunzhong.CommonTest.util.stream.InputStreamToString

## 9 python invoke
可以依赖jpython，直接在java中运行python脚本,需要引入jpython。但是这种方法无法调用系统中pip安装的库。  

```xml
<dependency>
    <groupId>org.python</groupId>
    <artifactId>jython-standalone</artifactId>
    <version>2.7.0</version>
</dependency>
```
推荐直接用java的Runtime.getRuntime()：  
org.yunzhong.CommonTest.util.PythonCommonInvoker

## 10 shell
### how to use command ip
shell中的ip命令，而不是平时说的ip地址。
ip命令入门：shell/printIP.sh

## 11 多线程
ExecutorManager

### 线程池管理
- 尽量不要使用Executors建立线程池。
- 线程池的各个概念，参考ExecutorManagerDemo
- 创建的线程要赋予有意义的名字。
### ThreadPoolFactory
- 环境需要多个线程池。不同的业务用不同的线程池管理，防止互相之间的影响。
- 整体线程信息输出。线程总数，等待队列总数。
- 线程池的销毁。
- 线程池的创建。每个线程池都有自己的名字，其下的线程也会使用这个名字，因此要使用英文。  
系统有默认的线程池，名称为default_pool，用户可以直接使用。  
- 线程池的配置。系统默认采用配置文件中的参数。用户也可以自己指定配置参数创建线程池。

## 12 集合
### list array转换
CollectionUtils
### 线程安全的集合
ConcurrentCollections
#### blockingQueue
- 放入数据：
* - offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,  
　　　　则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
* - offer(E o, long timeout, TimeUnit unit),可以设定等待的时间，如果在指定的时间内，还不能往队列中  
　　　　加入BlockingQueue，则返回失败。
* - put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断  
　　　　直到BlockingQueue里面有空间再继续.
- 获取数据：
* - poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,  
　　　　取不到时返回null;
* - poll(long timeout, TimeUnit unit)：从BlockingQueue取出一个队首的对象，如果在指定时间内，  
　　　　队列一旦有数据可取，则立即返回队列中的数据。否则知道时间超时还没有数据可取，返回失败。  
* - take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到  
　　　　BlockingQueue有新的数据被加入; 
* - drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数），   
　　　　通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。  
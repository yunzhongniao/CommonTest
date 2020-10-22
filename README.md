# CommonTest

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
### how to use command ip(shell中的ip命令，而不是平时说的ip地址)
ip命令入门
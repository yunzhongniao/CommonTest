# CommonTest

## 1 spring boot 

## 2 swagger ui

## 3 docx4j
source path: org.yunzhong.CommonTest.controller.docx4j  
demo apis:  
- print docx archive  
- replace main documnet  
- replace chart  
- replace table  

## 4 poi

## 5 poi-tpl

### chart 设置关联信息
wps设置：点击图表，右侧出现的浮框选择"设置图表区域格式"-》右侧出现的配置框中，在顶端的下拉框中选择"图表区"-》选择“大小与属性”-》填写“可选文字”的"标题"，格式为：{{chartname}}。  
代码中变量Map，以chartname作为key。  

## 6 load property source
### 变量类型
类型包括：sql-single, sql-obj, sql-multi, static, SpEL  
sql-single:单个返回结果的单个数据。  例如当前日期。  
sql-obj：单行返回结果。例如学生信息，列：idnum，name，sex，age，...。可以通过param.property访问。  
sql-multi：多行返回结果。表格，图表的数据格式。  
static: 常量  
SpEL: Spring Expression Language   
# 概述

# 系统文件操作(nio)
Paths, Files操作替换File操作。
## 文件系统文件操作
### 文件路径拼接
URI, URL
#### URI:
格式：{scheme}://{user}@{host}:{port}{path}?{query}#{fragment}  
demo:http://yunzhong@yunzhong.com:8080/yunzhong/eat?when=afternoon#meat
输出：

```
Authority = yunzhong@yunzhong.com:8080
Fragment = meat
Host = yunzhong.com
Path = /yunzhong/eat
Port = 8080
Query = when=afternoon
Scheme = http
Scheme-specific part = //yunzhong@yunzhong.com:8080/yunzhong/eat?when=afternoon
User Info = yunzhong
URI is absolute: true
URI is opaque: false
```
java demo: URITester.java

#### URL
是URI的一个子集，一般用于网络上。  
java demo:URLTester.java  

#### Path路径拼接

URL转成Path：

```java
    Path path = Paths.get(resource.toURI());
```
URI可以直接作为参数传递给Path。  
Path还可以拼接多级路径，不需要用户处理"/","\"等问题。  

```java
    @Test
    public void testAppend() {
        System.out.println(Paths.get("D:/temp", "hello/world", "files").toString());
        System.out.println(Paths.get("D:/temp", "hello\\world", "files").toString());
        System.out.println(Paths.get("D:/temp", "hello", "world", "files").toString());
        
        Path path = Paths.get("D:/temp");
        Path resolve = path.resolve("hello").resolve("world").resolve("files");
        System.out.println(resolve.toString());
    }
```

### 文件夹操作
FileSystemUtil包含了所有的文件操作demo。
- 文件夹下的遍历操作
一种方式是使用Files.walkFileTree接口。这个接口遍历目录下所有的文件和文件夹。接口的参数需要传入FileVisitor，提供了文件、文件夹的操作接口。  
一种方式是使用Files.newDirectoryStream。这个接口只打印第一层的文件和文件夹。另外，因为是打开了一个stream，需要在finally中close stream。比较好的是用户可以自己定义过滤条件，支持“*”等通配符。  
最后一种方式，是采用File.listFiles列举所有的第一层文件。  
- 创建文件夹操作
文件夹的创建分为两个接口：Files.createDirectory和Files.createDirectories。  
Files.createDirectory只能创建一级目录。比如说已经存在了“/home/yunzhong”,可以创建“/home/yunzhong/temp”,但是不可以创建“/home/yunzhong/temp/firstDir”。  
Files.createDirectories则是会将路径上不存在的文件夹都创建出来，相当于是Files.createDirectory的更便利的封装。  
- 删除文件夹
删除的文件夹，必须没有子文件或者子文件夹，所以不管怎么操作都需要先删除文件夹的内容，再删除文件夹。  
可以采用Files.walkFileTree进行删除。也可以通过递归的方式删除文件夹。  

### 文件操作
实现类：FileSystemUtil  
#### 小文件操作
一次性读取到内存。  

#### 异步文件操作
AsynchronousFileChannel进行文件的读写。实现起来很复杂，一般不要用了。  

## classpath文件操作
classpath可以通过classloader进行定位。

```java
	FolderSystemUtilTest.class.getClassLoader().getResource("file-root");
```
定位之后，可以转成Path。

```java
        URL resource = FileSystemUtilTest.class.getClassLoader().getResource("file-root/second1/second1.txt");
        Path path = Paths.get(resource.toURI());
```

# jar内部文件加载

## JarFile

## URL加载

```java
public String readFromJar(String jarPath, String file) throws IOException {
        JarURLConnection jarURLConnection=null;
        try {
            URL fileUrl=ParseUtil.fileToEncodedURL(new File(jarPath));
            URL jarUrl=new URL("jar", "", -1, fileUrl + "!/");
            URL moduleUrl = new URL(jarUrl, ParseUtil.encodePath(file, false));
            jarURLConnection = (JarURLConnection)moduleUrl.openConnection();
            return IOUtils.toString(jarURLConnection.getInputStream(),"UTF-8");
        } catch (IOException e) {
            throw e;
        } finally {
            if (jarURLConnection!=null){
                try {
                    jarURLConnection.getJarFile().close();
                } catch (IOException ignore) {
                }
            }
        }
    }
```

## ClassLoader

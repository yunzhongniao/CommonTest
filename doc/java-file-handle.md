# 概述

# 系统文件操作(nio)
Paths, Files
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

### 文件夹操作
createDirectories
createDirectory
remove
### 文件操作
增删改查
## classpath文件操作

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

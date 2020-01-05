# gmail
电商项目
SpringBoot集成

1.devtool与dubbobu不兼容，因为类加载器的不同而导致dubbo远程调用反序列化的结果出现类不匹配的异常。

官方文档：
By default, any open project in your IDE will be loaded using the 
“restart” classloader, and any regular .jar file will be loaded using 
the “base” classloader. If you work on a multi-module project, and not each module is imported into your IDE, you may need to customize 
things. To do this you can create a 
META-INF/spring-devtools.properties file.
The spring-devtools.properties file can contain restart.exclude. and 
restart.include. prefixed properties. The include elements are items 
that should be pulled up into the “restart” classloader, and the 
exclude elements are items that should be pushed down into the “base” 
classloader. The value of the property is a regex pattern that will be 
applied to the classpath.

2.mybatis-plus逆向生成的mapper中带泛型的方法与dubbo不兼容。解决：在provider端自己的mapper中新增一个不带泛型的方法去调用继承父类的带泛型的方法。
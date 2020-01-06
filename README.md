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

3.mybatis-plus逆向生成的mapper中的分页必须添加一个拦截器

    /**
     * mybatis-plus分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

4.logback整合logstash：(1)导jar包;(2)编写配置文件

        <!-- logback 整合logstash的jar包-->
        <dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>5.3</version>
        </dependency>


5.编写切面

[校验切面]: gmall-admin-web/src/main/java/com/joe/gmall/admin/aop/DataValidAspect.java "校验切面"

(1)导入切面场景

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>

(2)编写切面

	 1）、@Aspect
	 2）、切入点表达式
	 3）、通知
			前置通知：方法执行之前触发
			后置通知：方法执行之后触发
			返回通知：方法正常返回之后触发
			异常通知：方法出现异常触发

		正常执行：   前置通知==>返回通知==>后置通知
		异常执行：   前置通知==>异常通知==>后置通知

			环绕通知：4合1；拦截方法的执行

6.全局异常处理

[异常处理]: gmall-admin-web/src/main/java/com/joe/gmall/admin/aop/GlobalExceptionHandler.java "异常处理"


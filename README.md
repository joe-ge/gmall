# gmal电商项目
SpringBoot集成

1.devtool与dubbo不兼容，反序列化的结果会出现类匹配异常。

官方文档：

2.mybatis-plus逆向生成的mapper中带泛型的方法与dubbo不兼容。

解决：在provider端自己的mapper中新增一个不带泛型的方法去调用继承父类的带泛型的方法。

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

[校验切面](gmall-admin-web/src/main/java/com/joe/gmall/admin/aop/DataValidAspect.java "校验切面") 

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

[异常处理](gmall-admin-web/src/main/java/com/joe/gmall/admin/aop/GlobalExceptionHandler.java "异常处理")

7.SpEL表达式 CacheEnable key的拼接

前缀定义：
`public static final String CATEGORY_MENU_CACHE_KEY = "'sys_category_pid'+";`

key中拼接：`@Cacheable(cacheNames = SysCacheConstant.MENU_CACHE_NAME,key = SysCacheConstant.CATEGORY_MENU_CACHE_KEY + "#p0")`

8.自定义RedisCacheConfiguration

[RedisCacheConfig](gmall-pms/src/main/java/com/joe/gmall/pms/config/RedisCacheConfig.java)

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

9.oss对象存储&阿里云跨域访问

10.dubbo针对某个服务或者某个方法单独配置超时时间和重试次数

注解中：（注意：用了注解之后，不管有没有在注解中自定义配置，下面其它的配置的都不生效）

    @Reference(version = "1.0" ,parameters = {
            "saveProduct.timeout","5000",
            "saveProduct.retries","0"
    })
    private ProductService productService;

properties中：

`dubbo.reference.com.joe.gmall.pms.service.saveProduct.saveProduct.timeout=5000`
`dubbo.reference.com.joe.gmall.pms.service.saveProduct.saveProduct.retries=0`

xml配置文件中，记得在启动类上添加@ImportResource(locations = "classpath:dubbo-consumer.xml")

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd ">
    <!--    <dubbo:application name="gmall-admin-web" />-->
    <!--    <dubbo:registry protocol="zookeeper" address="zookeeper://zk.registry.com:2181" />-->
    <!--    <dubbo:protocol name="dubbo" port="20895" />-->
        <dubbo:reference interface="com.joe.gmall.pms.service.saveProduct" id="productService">
            <dubbo:method name="saveProduct" timeout="2000" retries="0"></dubbo:method>
        </dubbo:reference>
    </beans>
    
11.ThreadLocal的使用:在多线程中保存各自线程中的共享变量，相当于
Map<Thread,T> map;使用当前线程(Thread.currentThread())作为key.

12.Transaction的使用：

（1）注解类上开启@EnableTransactionManagement

（2）

        @Transactional(propagation = Propagation.REQUIRED,
               rollbackFor = Exception.class ,/*By default, a transaction will be rolling back on {@link RuntimeException}
                                                and {@link Error} but not on checked exceptions (business exceptions)
                                                公司一般都指定为Exception.class */
               noRollbackFor = FileNotFoundException.class /*指定可回滚中的不回滚情景 */
       )       
（3）Demo的内部事务无法真正的实现，原因：事务是基于aop的，然而这种写法A,B,C都是同一个代理对象，不管里面的事务为何种传播行为，他们统一用的事务都外面那个。总结：代理对象.方法()才可以加上事务。

    public class DemoA{
         @Transactional
         methodA(){
             methodB();
             methodC();
         }
         @Transactional
         methodB(){};
         @Transactional
         methodC(){};
    }
最终解决办法：Spring给出的办法是aop代理自个对象。

使用：

①导入spring-boot-starter-aop

②启动类上开启暴露代理对象`@EnableAspectJAutoProxy(exposeProxy = true) `

③在类中使用`ProductServiceImpl proxy = (ProductServiceImpl) AopContext.currentProxy();`

[详见](gmall-pms/src/main/java/com/joe/gmall/pms/service/impl/ProductServiceImpl.java)
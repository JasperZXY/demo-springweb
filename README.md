# Quick Start
- `git clone git@github.com:ruanweiqq/demo-springweb.git`
- `cd demo-springweb`
- demo-springweb-web中的doc有一份db.sql，执行这份SQL，TODO或内存数据库
- 默认的配置环境是dev，需要确认dev.properties中的配置信息是否正确，特别是数据库的配置
- 执行下面三个命令
- `mvn clean install`
- `cd demo-springweb-web`
- `mvn jetty:run`
- 用户管理页面 http://127.0.0.1:8080/springweb-web/user/list.html


# 关于日志
- 本项目用的日志框架是log4j2
- 官方文档：http://logging.apache.org/log4j/2.x/manual/configuration.html
- 日志可以根据不同环境进行配置，默认打在`/data/logs/demo-springweb`目录下
- 日志Rollover配置的是100M一次，日志名类似2018-05-22_1.log、2018-05-22_2.log
- 日志输出格式如下

```
时间 线程名 日志级别(WARN=W, DEBUG=D, ERROR=E, TRACE=T, INFO=I) 类名[行号] - 日志信息

17:08:53.552 [main] D BeanValidationBeanPostProcessor[86] - postProcessBeforeInitialization==================307org.ruanwei.demo.user.web.interceptor.MyDeferredResultProcessingInterceptor#0#1=org.ruanwei.demo.user.web.interceptor.MyDeferredResultProcessingInterceptor@1031c1a0
17:08:53.552 [main] D BeanValidationBeanPostProcessor[136] - postProcessAfterInitialization==================308org.ruanwei.demo.user.web.interceptor.MyDeferredResultProcessingInterceptor#0#1=org.ruanwei.demo.user.web.interceptor.MyDeferredResultProcessingInterceptor@1031c1a0
17:08:53.553 [main] D BeanValidationBeanPostProcessor[86] - postProcessBeforeInitialization==================309(inner bean)#6d5508a5=org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter@519b0f00
17:08:53.553 [main] D BeanValidationBeanPostProcessor[89] - handlerAdapter==================org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter@519b0f00
17:08:53.554 [main] I RequestMappingHandlerAdapter[568] - Looking for @ControllerAdvice: WebApplicationContext for namespace 'springweb-web-servlet': startup date [Tue May 22 17:08:52 CST 2018]; parent: Root WebApplicationContext
17:08:53.556 [main] I RequestMappingHandlerAdapter[585] - Detected @ModelAttribute methods in myControllerAdvice
17:08:53.570 [main] I RequestMappingHandlerAdapter[592] - Detected @InitBinder methods in myControllerAdvice
17:08:53.570 [main] I RequestMappingHandlerAdapter[604] - Detected ResponseBodyAdvice bean in myControllerAdvice
17:08:53.573 [main] I RequestMappingHandlerAdapter[604] - Detected ResponseBodyAdvice bean in myResponseBodyAdvice
```


---
# 参数
## 参数校验
- 文档参考 https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/htmlsingle/#validation
- 引用的jar包`hibernate-validator`
- 可使用`javax.validation.Valid`注解或`org.springframework.validation.annotation.Validated`注解
- Validated支持分组校验，Valid不支持分组校验，不指定分组的话，默认是`javax.validation.groups.Default`
- 由于指定分组校验后，分组没有匹配上的是不会进行校验，所以建议指定分组后，加上默认分组`javax.validation.groups.Default`
- 检验不通过，抛出的异常为`org.springframework.validation.BindException`、`ConstraintViolationException`

参数校验国际化支持
- 关键类`org.springframework.validation.beanvalidation.LocalValidatorFactoryBean`、`org.springframework.context.support.ReloadableResourceBundleMessageSource`
- 特别注意的点：xml中的配置要加上“classpath:”
- message的指定格式用“{xxx}”


# 数据格式化返回
基本的数据格式  
success：是否调用成功；code：错误返回码；message：错误信息；data：返回的数据

单个数据的返回
```json
{
    success: true,
    code: 0,
    message: null,
    data: {
        id: 1,
        name: "ruanwei",
        gender: 0,
        age: 28
    }
}
```

分页结果返回
```json
{
    success: true,
    code: 0,
    message: null,
    curPage: 2,
    pageSize: 1,
    count: 3,
    list:[
      {
        id: 2,
        name: "zxy",
        gender: 0,
        age: 0
      }
    ]
}
```

发生错误的情况
```json
{
    success: false,
    code: 101,
    message: "该用户不存在",
    data: null
}
```

样例api
- http://127.0.0.1:8080/springweb-web/user/1.json
- http://127.0.0.1:8080/springweb-web/user/list.json?curPage=2&pageSize=1
- http://127.0.0.1:8080/springweb-web/user/-1.json


# 异常拦截
目前对三种异常做了特定的拦截，其他异常都归了系统类异常，代码见UserController2.error()，MyControllerAdvice.handleSpringException()  
接口如下
- http://127.0.0.1:8080/springweb-web/testException/1
- http://127.0.0.1:8080/springweb-web/testException/9

定义的异常：参数异常、业务异常(包括登录态异常)、系统异常

最终返回的数据如下
```json
{
    success: false,
    code: 1002,
    message: "xxx"
}
```


# 参数校验
- 文档参考 https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/htmlsingle/#validation
- 引用的jar包`hibernate-validator`
- 可使用`javax.validation.Valid`注解或`org.springframework.validation.annotation.Validated`注解
- Validated支持分组校验，Valid不支持分组校验，不指定分组的话，默认是`javax.validation.groups.Default`
- 由于指定分组校验后，分组没有匹配上的是不会进行校验，所以建议指定分组后，加上默认分组`javax.validation.groups.Default`
- 检验不通过，抛出的异常为`org.springframework.validation.BindException`、`ConstraintViolationException`
- `${validatedValue}`可以获取被校验的值，`{min}`等可获取注解如`@size`上配置的值

参数校验国际化支持
- 关键类`org.springframework.validation.beanvalidation.LocalValidatorFactoryBean`、`org.springframework.context.support.ReloadableResourceBundleMessageSource`
- 特别注意的点：xml中的配置要加上“classpath:”
- message的指定格式用“{xxx}”


# 内容协商
- 三种实现方式
    - 使用扩展名（.json）
    - 使用参数(format=json)
    - 使用http request header的Accept
- https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/htmlsingle/#mvc-multiple-representations
- org.springframework.web.accept.ContentNegotiationManagerFactoryBean
- ContentNegotiatingViewResolver
- contentNegotiationManager


# demo-springweb
Spring Web Demo

- [ ] 容器配置，Java的配置方式
- [ ] MVC配置，Java方式的配置为主，xml配置为辅
- [ ] maven配置
    - [ ] 打包成tomcat的运行用的包，一次性打包两个项目
    - [ ] demo的bom，模仿spring-bom
    - [ ] 是否支持pluginManager
    - [ ] 依赖的jar包分类注释
- [ ] URL路径匹配，支持自定义的
- [ ] 处理拦截
    - [ ] servlet filter
    - [ ] HandlerInterceptor
    - [ ] WebRequestInterceptor
- [ ] 异常，ExceptionHandler，error-page
- [ ] 视图，内容协商，根据不同的扩展名返回不同的数据
    - [ ] 生成json的两种方式，JsonView、Message Converter
- [ ] 国际化支持，LocalResolver
- [ ] ThemeResolver
- [ ] 文件上传，两种方式
- [ ] 视图控制器
    - [ ] json
    - [ ] html
    - [ ] pdf
    - [ ] view-controller、UrlFilenameViewController
    - [ ] 自定义视图
- [ ] RequestMapping，这个不用，要强制get跟post，两个页面之间跳转传参
- [ ] RequestBody,ResponseBody
- [ ] 自定义参数解析和返回值
    - [ ] HttpMessageConverter
    - [ ] ResponseBodyAdvice，可以修改返回的数据，jsonp
- [ ] PushBuilder，支持Http2
- [ ] InitBinder
- [ ] 参数校验，带消息，自定义校验器消息，错误消息国际化
- [ ] 异常的处理支持内容协商
- [ ] ControllerAdvice
- [ ] 异步请求处理，支持两种异步方法
- [ ] HTTP Streaming
- [ ] WebSocket
- [ ] HTTP 缓存
    - [ ] ResourceHttpRequestHandler 静态资源
    - [ ] Controller 缓存
- [ ] RestTemplate
- [ ] 类型转换

# TODO
- 数据绑定：转换（）、校验（分组）
- 异常统一处理，异常信息国际化
- 内容协商（异常）
- 统一输入输出格式
- 正确有效的拦截器
- 清理maven配置和依赖
- 增加spring基于Java的配置
- 清理日志
- 增加servlet基于Java的配置
- 增加WebFlux项目
- 报名统一到org.ruanwei.demo.springweb.user
- 补充ListenableFuture<V>, java.util.concurrent.CompletionStage<V>, java.util.concurrent.CompletableFuture<V>
Alternative to DeferredResult as a convenience for example when an underlying service returns one of those.
- 补充Reactive types
Alternative to `DeferredResult with multi-value streams (e.g. Flux, Observable) collected to a List.
For streaming scenarios — .e.g. text/event-stream, application/json+stream, SseEmitter and ResponseBodyEmitter are used instead, where ServletOutputStream blocking I/O is performed on a Spring MVC managed thread and back pressure applied against the completion of each write.
- 补充javax.servlet.http.PushBuilder
Servlet 4.0 push builder API for programmatic HTTP/2 resource pushes. Note that per Servlet spec, the injected PushBuilder instance can be null if the client does not support that HTTP/2 feature.
- 几个主要的Filter
AbstractRequestLoggingFilter
ForwardedHeaderFilter
HiddenHttpMethodFilter
HttpPutFormContentFilter
ResourceUrlEncodingFilter
- 参考MethodValidationPostProcessor/BeanValidationPostProcessor使用AOP来实现非Web层的校验https://my.oschina.net/FengJ/blog/223727
- PDF报错，在没有data数据的情况下，调用close会报no page，但是框架却没有拦截下来
- 自动扫描的inclued和exclude
- @ConvertGroup、@GroupSequence、@ScriptAssert、@SupportedValidationTarget、EL表达式${validatedValue}
- 验证错误消息和国际化消息
- 打包成tomcat的运行用的包，一次性打包两个项目
- demo的bom，模仿spring-bom
- 是否支持pluginManager

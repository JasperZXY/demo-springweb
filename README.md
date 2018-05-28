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


# 数据格式化返回
基本的数据格式  
success：是否调用成功；code：错误返回码；message：错误信息；data：返回的数据
```json
{
    success: true,
    code: 0,
    message: null,
    data: {
    }

}
```

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
- http://127.0.0.1:8080/springweb-web/user2/1
- http://127.0.0.1:8080/springweb-web/user2/list?curPage=2&pageSize=1
- http://127.0.0.1:8080/springweb-web/user2/999


# 异常拦截
目前对三种异常做了特定的拦截，其他异常都归了系统类异常，代码见UserController2.error()，MyControllerAdvice.handleSpringException()  
接口如下
- http://127.0.0.1:8080/springweb-web/user2/error/1
- http://127.0.0.1:8080/springweb-web/user2/error/2
- http://127.0.0.1:8080/springweb-web/user2/error/3
- http://127.0.0.1:8080/springweb-web/user2/error/5

定义的异常：参数异常、登录态失效异常、业务异常、其他系统异常

最终返回的数据如下
```json
{
    success: false,
    code: 1002,
    message: "xxx"
}
```


# demo-springweb
Spring Web Demo
<pre>

1.包名更换
2.增加Reactive的Flux的Mono
3.补充基于Java的初始化和配置
4.补充ListenableFuture<V>, java.util.concurrent.CompletionStage<V>, java.util.concurrent.CompletableFuture<V>
Alternative to DeferredResult as a convenience for example when an underlying service returns one of those.
5.补充Reactive types — Reactor, RxJava, or others via ReactiveAdapterRegistry
Alternative to `DeferredResult with multi-value streams (e.g. Flux, Observable) collected to a List.
For streaming scenarios — .e.g. text/event-stream, application/json+stream, SseEmitter and ResponseBodyEmitter are used instead, where ServletOutputStream blocking I/O is performed on a Spring MVC managed thread and back pressure applied against the completion of each write.
6.补充javax.servlet.http.PushBuilder
Servlet 4.0 push builder API for programmatic HTTP/2 resource pushes. Note that per Servlet spec, the injected PushBuilder instance can be null if the client does not support that HTTP/2 feature.
7.几个主要的Filter
AbstractRequestLoggingFilter
ForwardedHeaderFilter
HiddenHttpMethodFilter
HttpPutFormContentFilter
ResourceUrlEncodingFilter
8.参考MethodValidationPostProcessor/BeanValidationPostProcessor使用AOP来实现非Web层的校验
9.PDF报错
10.自动扫描的inclued和exclude
11.@ConvertGroup、@GroupSequence、@ScriptAssert、@SupportedValidationTarget、EL表达式${validatedValue}
12.验证错误消息和国际化消息

https://my.oschina.net/FengJ/blog/223727

</pre>


钟献耀

阮威


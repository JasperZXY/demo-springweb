# Quick Start
- demo-springweb-web中的doc有一份db.sql，执行这份SQL
- 默认的配置环境是dev，需要确认dev.properties中的配置信息是否正确，特别是数据库的配置
- 执行下面三个命令
- `mvn clean compile`
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
目前对三种异常做了特定的拦截，其他异常都归了系统类异常，代码见
UserController2.error()，MyControllerAdvice.handleSpringException()  
接口如下
- http://127.0.0.1:8080/springweb-web/user2/error/1
- http://127.0.0.1:8080/springweb-web/user2/error/2
- http://127.0.0.1:8080/springweb-web/user2/error/3
- http://127.0.0.1:8080/springweb-web/user2/error/5
```
@ExceptionHandler(Exception.class) // handled by ExceptionHandlerExceptionResolver
@ResponseBody
public Object handleSpringException(Throwable e, HttpServletRequest request, HttpServletResponse response) throws Exception {
    logger.error("handleSpringException===================" + request.getRequestURL(), e);
    BaseResult result = new BaseResult();
    if (e instanceof InvalidArgumentException) {
        result.setError(1001, "参数异常：" + e.getMessage());
    } else if (e instanceof InvalidLogicException) {
        result.setError(1002, "登录异常：" + e.getMessage());
    } else if (e instanceof RemoteAccessException) {
        RemoteAccessException re = (RemoteAccessException)e;
        result.setError(re.getCode(), re.getMessage());
    } else {
        result.setError(1003, "服务器繁忙，请稍后重试！");
    }
    return result;
}
```

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


A.HandlerMapping：Map a request to a handler along with a list of HandlerInterceptor for pre- and post-processing.
    1.BeanNameUrlHandlerMapping(Default Implementation)
    2.RequestMappingHandlerMapping(Default Implementation)：use @RequestMapping in @Controller(HandlerMethod).
    3.SimpleUrlHandlerMapping：use Controller接口,并遵循其工作流
    ControllerClassNameHandlerMapping：貌似去掉了   

B.HandlerAdapter：Help the DispatcherServlet to invoke a handler mapped to a request regardless of how the handler is actually invoked.the main purpose is to shield the DispatcherServlet from such details. 
    HttpRequestHandlerAdapter(Default Implementation)：use HttpRequestHandler接口(DefaultServletHttpRequestHandler)
    SimpleControllerHandlerAdapter(Default Implementation)：对应SimpleUrlHandlerMapping.
    RequestMappingHandlerAdapter(Default Implementation)：对应RequestMappingHandlerMapping.
    AbstractHandlerMethodAdapter use HandlerMethod
    SimpleServletHandlerAdapter use Servlet接口

C.HandlerExceptionResolver：Strategy to resolve exceptions possibly mapping them to handlers, or to HTML error views, or other.
    ExceptionHandlerExceptionResolver(Default Implementation)：@ExceptionHandler
    ResponseStatusExceptionResolver(Default Implementation)：@ResponseStatus
    DefaultHandlerExceptionResolver(Default Implementation)：
    SimpleMappingExceptionResolver
MessageCodesResolver
    DefaultMessageCodesResolver

D.ViewResolver：Resolve logical String-based view names returned from a handler to an actual View to render to the response with. 
    BeanNameViewResolver
    XmlViewResolver
    ResourceBundleViewResolver
    UrlBasedViewResolver:
    InternalResourceViewResolver(Default Implementation)
    GroovyMarkupViewResolver
    FreeMarkerViewResolver
    ScriptTemplateViewResolver
    TilesViewResolver
    XsltViewResolver   
    ContentNegotiatingViewResolver
E.RequestToViewNameTranslator
    DefaultRequestToViewNameTranslator

F.LocaleResolver：Resolve the Locale a client is using and possibly their time zone, in order to be able to offer internationalized views. 
    AcceptHeaderLocaleResolver(Default Implementation)
    FixedLocaleResolver
    SessionLocaleResolver
    CookieLocaleResolver

G.ThemeResolver：Resolve themes your web application can use, for example, to offer personalized layouts. 
    FixedThemeResolver(Default Implementation)
    SessionThemeResolver
    CookieThemeResolver

H.MultipartResolver：Abstraction for parsing a multi-part request (e.g. browser form file upload) with the help of some multipart parsing library.
    CommonsMultipartResolver：based on Commons FileUpload
    StandardServletMultipartResolver：based on Servlet 3.0

I.FlashMapManager：Store and retrieve the "input" and the "output" FlashMap that can be used to pass attributes from one request to another, usually across a redirect. 
    SessionFlashMapManager(Default Implementation)
org.springframework.web.HttpRequestHandler:
    DefaultServletHttpRequestHandler  <mvc:default-servlet-handler>
    ResourceHttpRequestHandler  <mvc:resources>
    WebSocketHttpRequestHandler  <websocket:handlers>


HandlerInterceptor/WebRequestInterceptor
  1/7.preHandle  [mainThread]
     after handler determined by HandlerMapping, before invoked by HandlerAdapter.
  8.postHandle  [mainThread]
     after handler invoked by HandlerAdapter，before view rendered by DispatcherServlet.
  9.afterCompletion  [mainThread]
     after view rendered by DispatcherServlet,this is the completion of request processing.
AsyncHandlerInterceptor
  3.afterConcurrentHandlingStarted  [mainThread]        
     after handler started asynchronous request handling.
CallableProcessingInterceptor/DeferredResultProcessingInterceptor
  2.beforeConcurrentHandling  [mainThread]
     before the start of concurrent handling.
  4.preProcess  [asyncThread]
     after the start of concurrent handling，before the actual invocation of the Callable.
  5.postProcess  [asyncThread]
     after the Callable has produced a result.
  4.1.handleTimeout  [mainThread]
  4.2.handleError  [mainThread]
  6.afterCompletion  [mainThread]
AsyncWebRequestInterceptor


application/x-www-form-urlencoded
multipart/form-data

Last-Modified/If-Modified-Since/If-Unmodified-Since 304
Etag/If-None-Match 304
Expires 时间点
Cache-Control 时间段

@Validated @Valid
getForDay(@PathVariable @DateTimeFormat(iso=ISO.DATE) Date day, @NumberFormat double num)
NamespaceHandler

@ResponseStatus
java.util.Map / org.springframework.ui.Model / org.springframework.ui.ModelMap

错误码： <%=request.getAttribute("javax.servlet.error.status_code")%> <br>
 信息： <%=request.getAttribute("javax.servlet.error.message")%> <br>
 异常： <%=request.getAttribute("javax.servlet.error.exception_type")%> <br>
</pre>

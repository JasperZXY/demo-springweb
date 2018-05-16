# Quick Start
- demo-springweb-web中的doc有一份db.sql，执行这份SQL
- 默认的配置环境是dev，需要确认dev.properties中的配置信息是否正确，特别是数据库的配置
- 执行下面三个命令
- `mvn clean compile`
- `cd demo-springweb-web`
- `mvn jetty:run`
- 用户管理页面 http://127.0.0.1:8080/springweb-web/user/list.html


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

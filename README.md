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

package org.ruanwei.demo.user.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.web.Page;
import org.ruanwei.demo.user.entity.User;
import org.ruanwei.demo.user.service.UserService;
import org.ruanwei.demo.user.web.databind.UserForm;
import org.ruanwei.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * 本文件演示： <li>基于HttpMessageConverter(@ResponseBody)进行渲染的Controller. <li>支持异步.
 * 
 * @author ruanwei
 */
@RestController
@RequestMapping(path = "/user/rest/")
public class UserRestController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private UserService userService;

	// TODO:按照统一的返回类型，返回rest格式，列表，对象，映射
	@GetMapping(path = "list", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<User> list(@Valid @NotNull UserForm userForm, Page page) {
		logger.debug("list=" + userForm + page);

		// add your code here.

		User user = BeanUtils.copy(userForm, User.class);
		long totalRecord = userService.count(user);
		page.setTotalRecord(totalRecord);

		user.setStart(page.getPageSize() * (page.getCurPage() - 1));
		user.setOffset(page.getPageSize());

		List<User> list = userService.list4Page(user);

		return list;
	}
	
	@GetMapping(path = "{uid}")
	public User get(@PathVariable("uid") @Min(0) int id) {
		logger.debug("get=" + id);

		// add your code here.

		User user = getUser0(id);

		return user;
	}

	// produce the return value from a Spring MVC managed thread
	@GetMapping(path = "async1/{id}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public Callable<User> async1(@PathVariable Integer id) {
		logger.debug("async1=" + id);

		Callable<User> call = () -> {
			logger.debug("Callable.call()=");
			User user = null;
			try {
				logger.debug("sleep(2000)=");
				Thread.sleep(2000);
				logger.debug("sleep(2000)=");
				user = userService.getUser(id);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				// add your code here.
				// throw new
				// WebException(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			return user;
		};

		return call;
	}

	// produce the return value from a Spring MVC managed thread
	@GetMapping(path = "async2/{id}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public WebAsyncTask<User> async2(@PathVariable Integer id) {
		logger.debug("async2=" + id);

		WebAsyncTask<User> asyncTask = new WebAsyncTask<User>(5000L,
				"myThreadPool", () -> {
					logger.debug("Callable.call()=");
					User user = null;
					try {
						logger.debug("sleep(2000)=");
						Thread.sleep(2000);
						logger.debug("sleep(2000)=");
						user = userService.getUser(id);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						// add your code here.
						// throw new
						// WebException(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			return user;
		});

		asyncTask.onCompletion(() -> logger.debug("onCompletion="));
		asyncTask.onTimeout(() -> {
			logger.error("onTimeout=");
			return null;
		});
		asyncTask.onError(() -> {
			logger.error("onError=");
			return null;
		});

		return asyncTask;
	}

	// produce the return value from any thread such as a JMS message, a
	// scheduled task, etc.
	@GetMapping(path = "async3/{id}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public DeferredResult<User> async3(@PathVariable Integer id)
			throws Exception {
		logger.debug("async3=" + id);

		DeferredResult<User> deferredResult = new DeferredResult<User>();
		deferredResult.onCompletion(() -> logger.debug("onCompletion="));
		deferredResult.onTimeout(() -> logger.error("onTimeout="));
		deferredResult
				.onError(throwable -> logger.error("onError=", throwable));

		// In some other thread...
		new Thread(() -> {
			logger.debug("Runnable.run()=");
			try {
				logger.debug("sleep(2000)=");
				Thread.sleep(2000);
				logger.debug("sleep(2000)=");
				User user = userService.getUser(id);
				deferredResult.setResult(user);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				// add your code here.
				deferredResult.setErrorResult(e);
			}
		}).start();

		return deferredResult;
	}

	// ListenableFuture<V>/CompletableFuture<V>/CompletionStage<V>/WebSocket/HTTP2
	@GetMapping(path = "async4/{id}", produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ListenableFuture<User> async4(@PathVariable Integer id)
			throws Exception {
		logger.debug("async4=" + id);

		ListenableFuture<User> listenableFuture = userService.getUser0(id);
		listenableFuture.addCallback((user -> logger
				.debug("SuccessCallback.onSuccess=" + user)),
				throwable -> logger.error("FailureCallback.onFailure=",
						throwable));

		return listenableFuture;
	}

	// produce the return value from any thread such as a JMS message, a
	// scheduled task, etc.
	// HTTP Streaming through HttpMessageConverter
	@GetMapping(path = "streaming/{id}")
	public ResponseBodyEmitter streaming(@PathVariable Integer id) {
		logger.debug("streaming=");

		ResponseBodyEmitter emitter = new ResponseBodyEmitter(); // 可以不需要@ResponseBody
		emitter.onCompletion(() -> logger.debug("onCompletion="));
		emitter.onTimeout(() -> logger.debug("onTimeout="));
		emitter.onError(throwable -> logger.error("onError=", throwable));

		// In some other thread...
		new Thread(() -> {
			logger.debug("Runnable.run()=");
			try {
				User user = userService.getUser(id);
				for (int i = 0; i < 3; i++) {
					logger.debug("sleep(1000)=");
					Thread.sleep(1000);
					emitter.send(user, MediaType.APPLICATION_JSON_UTF8);
				}
				emitter.complete();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				// add your code here.
				emitter.completeWithError(e);
			}
		}).start();

		return emitter;
	}

	// produce the return value from any thread such as a JMS message, a
	// scheduled task, etc.
	// HTTP Streaming With Server-Sent Events
	@GetMapping(path = "sse/{id}")
	public SseEmitter sse(@PathVariable Integer id) {
		logger.debug("sse=");

		SseEmitter emitter = new SseEmitter(); // 可以不需要 @ResponseBody
		emitter.onCompletion(() -> logger.debug("onCompletion="));
		emitter.onTimeout(() -> logger.debug("onTimeout="));
		emitter.onError(throwable -> logger.error("onError=", throwable));

		// In some other thread...
		new Thread(() -> {
			logger.debug("Runnable.run()=");
			try {
				User user = userService.getUser(id);
				// 注意超时
				for (int i = 0; i < 3; i++) {
					logger.debug("sleep(1000)=");
					Thread.sleep(1000);
					emitter.send(user, MediaType.APPLICATION_JSON_UTF8);
				}
				emitter.complete();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				// add your code here.
				emitter.completeWithError(e);
			}
		}).start();

		return emitter;
	}

	// HTTP Streaming Directly To The OutputStream
	@GetMapping(path = "download")
	public StreamingResponseBody download() {
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				// write...
			}
		};
	}
	
	private User getUser0(Integer id) {
		logger.debug("getUser0=" + id);

		User user = userService.getUser(id);
		logger.debug("1 jdbc======" + user);
		// user = userService.getUser2(id);
		// logger.debug("2 hessian======" + user.toString());
		// user = userService.getUser3(id);
		// logger.debug("3 rmi======" + user.toString());
		// user = userService.getUser4(id);
		// logger.debug("4 http invoker======" + user.toString());
		// user = userService.getUser5(id);
		// logger.debug("5 jms======" + user.toString());
		// user = userService.getUser6(id);
		// logger.debug("6 dubbo======" + user.toString());
		return user;
	}

}

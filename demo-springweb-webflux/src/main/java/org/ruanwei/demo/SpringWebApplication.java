package org.ruanwei.demo;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.ipc.netty.http.server.HttpServer;

public class SpringWebApplication {

	public static void main(String[] args) {
		WebHandler webHandler = new DispatcherHandler();
		HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler(webHandler)
				.build();

		runWithNetty(httpHandler);

	}

	private static void runWithNetty(HttpHandler httpHandler) {
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(
				httpHandler);
		HttpServer.create("localhost", 8081).newHandler(adapter).block();
	}

	private static void runWithUndertow(HttpHandler httpHandler) {
	}

	private static void runWithTomcat(HttpHandler httpHandler) {
	}
}

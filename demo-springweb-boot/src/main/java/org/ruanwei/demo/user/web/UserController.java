package org.ruanwei.demo.user.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user/")
public class UserController {

	@GetMapping("hello")
	public Mono<String> sayHelloWorld() {
		return Mono.just("Hello World");
	}
}

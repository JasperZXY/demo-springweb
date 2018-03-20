package org.ruanwei.demo.user.web.databind;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Author {

	@AliasFor("name")
	String value() default "";

	@AliasFor("value")
	String name() default "";

	String defaultValue() default "youzan_meiye";
}

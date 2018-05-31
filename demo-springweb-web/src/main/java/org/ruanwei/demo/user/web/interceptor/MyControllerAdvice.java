package org.ruanwei.demo.user.web.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.InvalidArgumentException;
import org.ruanwei.core.InvalidLogicException;
import org.ruanwei.core.ServiceException;
import org.ruanwei.core.web.BaseResult;
import org.ruanwei.util.Counter;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Classes annotated with @ControllerAdvice or @Controller can contain
 * 
 * @ExceptionHandler,@InitBinder, and @ModelAttribute annotated methods, and
 *                                these methods will apply to @RequestMapping
 *                                methods across all controller hierarchies as
 *                                opposed to the controller hierarchy within
 *                                which they are declared.<br/>
 * 
 *                                For controllers relying on view resolution,
 *                                JSONP is automatically enabled when the
 *                                request has a query parameter named jsonp or
 *                                callback. Those names can be customized
 *                                through jsonpParameterNames property.
 * 
 * @author ruanwei
 *
 */

@ControllerAdvice
// @RestControllerAdvice
public class MyControllerAdvice extends AbstractJsonpResponseBodyAdvice { // extends
																			// ResponseEntityExceptionHandler
	private static final Logger logger = LogManager.getLogger();

	// TODO:在XML文件中声明的没有生效，不知道为什么
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	// indicates the JSONP query parameter name
	public MyControllerAdvice() {
		super("callback");
		logger.debug("MyControllerAdvice==================" + Counter.getCount());
	}

	/**
	 * 1.Through Spring’s WebDataBinder, you can:
	 * <li>use @InitBinder-annotated methods within your controller,
	 * <li>use @InitBinder methods within an @ControllerAdvice class,
	 * <li>or provide a custom WebBindingInitializer for
	 * RequestMappingHandlerAdapter.
	 * 
	 * 2.Registering Formatters with the FormattingConversionService.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest webRequest) {
		logger.debug("initBinder================== req=" + webRequest);
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// dateFormat.setLenient(false);
		// binder.registerCustomEditor(Date.class, new
		// CustomDateEditor(dateFormat, false));
		// binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
		// binder.addValidators(validators);
	}

	@ModelAttribute("cities")
	public List<String> modelAttribute(Model model) {
		logger.debug("modelAttribute==================");
		List<String> cityList = new ArrayList<String>();
		cityList.add("a");
		cityList.add("b");
		cityList.add("c");
		model.addAttribute("cities2", cityList);
		return cityList;
	}

	// SimpleMappingExceptionResolver
	
	@ExceptionHandler(Exception.class) // handled by ExceptionHandlerExceptionResolver
//	@ResponseStatus // handled by ResponseStatusExceptionResolver
	@ResponseBody
	public Object handleSpringException(Throwable e, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.error("handleSpringException===================" + request.getRequestURL(), e);
		BaseResult result = new BaseResult();
		if (e instanceof InvalidArgumentException) {
			result.setError(1001, "参数异常：" + e.getMessage());
		}else if (e instanceof InvalidLogicException) {
			result.setError(1002, "登录异常：" + e.getMessage());
		}else if (e instanceof ServiceException) {
			ServiceException re = (ServiceException)e;
			result.setError(re.getCode(), re.getMessage());
		}else {
			result.setError(1003, "服务器繁忙，请稍后重试！");
		}
		return result;

		// 1.From BeanValidationBeanPostProcessor/MethodValidationPostProcessor.
//		if (e instanceof ConstraintViolationException) { // ValidationException
//			throw new InvalidArgumentException("ConstraintViolationException", e);
//		} // 2.From @Valid @RequestBody
//		else if (e instanceof MethodArgumentNotValidException) {
//			throw new InvalidArgumentException("MethodArgumentNotValidException", e);
//		} // 3.BindException by default(if no BindingResult argument).
//		else if (e instanceof BindException) {
//			throw new InvalidArgumentException("BindException", e);
//		} // 4. From Web Layer.
//		else if (e instanceof WebException) {
//			throw new InvalidLogicException("WebException", e);
//		} // 5. From Service Layer.
//		else if (e instanceof ServiceException) {
//			throw new InvalidLogicException("ServiceException", e);
//		} // 6. From Integration Layer(DataAccess).
//		else if (e instanceof DataAccessException) {
//			throw new InvalidStateException("DataAccessException", e);
//		} // 7. From Integration Layer(Remoting).
//		else if (e instanceof RemoteAccessException) {
//			throw new InvalidStateException("RemoteAccessException", e);
//		} // 8.From Unknown.
//		else if (e instanceof NullPointerException) {
//			throw new IllegalStateException("NullPointerException", e);
//		} // 9.From Unknown except NPE.
//		else if (e instanceof RuntimeException) {
//			throw new IllegalStateException("RuntimeException", e);
//		} // ServletException-ServletRequestBindingException:
//			// MissingPathVariableException/MissingServletRequestParameterException/UnsatisfiedServletRequestParameterException
//		else if (e instanceof ServletException) { // JasperException
//			throw new IllegalStateException("ServletException", e);
//		} else {
//			throw new IllegalStateException("IllegalStateException", e);
//		}
	}
}

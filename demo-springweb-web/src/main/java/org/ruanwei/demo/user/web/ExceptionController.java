package org.ruanwei.demo.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.DataAccessException;
import org.ruanwei.core.RemoteAccessException;
import org.ruanwei.core.ServiceException;
import org.ruanwei.core.WebException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionController {
	private static final Logger logger = LogManager.getLogger();

	@GetMapping("testException/{type}")
	public void testException(@PathVariable int type) throws Throwable {
		logger.debug("testException=" + type);
		if (type == 1) {
			// throw new WebException("Web Exception, type=",
			// HttpStatus.BAD_REQUEST);
		} else if (type == 2) {
			throw new ServiceException("Service Exception, type=", type);
		} else if (type == 3) {
			throw new DataAccessException("Data Access Exception, type=", type);
		} else if (type == 4) {
			throw new RemoteAccessException("Remote Access Exception, type=", type);
		} else if (type == 5) {
			throw new NullPointerException("NullPointer Exception, type=" + type);
		} else if (type == 6) {
			throw new RuntimeException("Runtime(Unchecked) Exception, type=" + type);
		} else if (type == 7) {
			throw new IOException("IOException, type=" + type);
		} else if (type == 8) {
			throw new Exception("(Checked) Exception, type=" + type);
		} else if (type == 9) {
			throw new Error("Error, type=" + type);
		} else {
			throw new Throwable("Throwable, type=" + type);
		}
	}

	// 1.来自Servlet容器的异常：包含404和500(例如JSP异常)
	// 2.来自Spring的异常：包含用户在框架中抛出的异常(例如@ExceptionHandler)
	@RequestMapping(path = "/error", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Map<String, Object> error(HttpServletRequest request) {
		logger.error("error");

		String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");

		int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
		String message = (String) request.getAttribute("javax.servlet.error.message");

		Class<?> exceptionType = (Class<?>) request.getAttribute("javax.servlet.error.exception_type");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		logger.error("servletName=" + servletName + " requestUri=" + requestUri + " statusCode=" + statusCode
				+ " message=[" + message + "]" + " exceptionType=" + exceptionType, throwable);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", -1);
		map.put("reason", message);
		map.put("statusCode", statusCode);

		return map;
	}

	@GetMapping(path = "/error")
	public String error2(HttpServletRequest request, Model model) {
		logger.error("error2");

		Object statusCode = request.getAttribute("javax.servlet.error.status_code");
		Object message = request.getAttribute("javax.servlet.error.message");
		model.addAttribute("code", statusCode);
		model.addAttribute("reason", message);
		logger.error("statusCode=" + statusCode + " message=" + message);

		return "" + statusCode;
	}
}

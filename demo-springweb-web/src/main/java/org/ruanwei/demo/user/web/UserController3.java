package org.ruanwei.demo.user.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user3/")
public class UserController3 {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * where can the User instance come from? There are several options: <li>It
	 * may already be in the model due to use of @SessionAttributes. <li>It may
	 * already be in the model due to an @ModelAttribute method in the same
	 * controller. <li>It may be retrieved based on a URI template variable and
	 * type converter. <li>It may be instantiated using its default constructor.
	 */
	@GetMapping("getAttribute/{id}")
	public String processSubmit(@PathVariable("id") String id,
			@ModelAttribute("cities") List<String> cityList,
			@ModelAttribute("cities2") List<String> cityList2,
			BindingResult result) {
		logger.debug("processSubmit=" + cityList + cityList2);

		return "";
	}
}

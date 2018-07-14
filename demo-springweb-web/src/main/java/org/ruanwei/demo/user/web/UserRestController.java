package org.ruanwei.demo.user.web;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.web.Page;
import org.ruanwei.core.web.PagingResult;
import org.ruanwei.core.web.Result;
import org.ruanwei.demo.user.entity.User;
import org.ruanwei.demo.user.service.UserService;
import org.ruanwei.demo.user.web.databind.JsonParam;
import org.ruanwei.demo.user.web.databind.UserForm;
import org.ruanwei.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本文件演示：
 * <li>基于HttpMessageConverter(@ResponseBody)进行渲染的Controller. 
 * <li>支持内容协商.
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
	public PagingResult<User> list(@Valid @NotNull @JsonParam UserForm userForm, Page page) {
		logger.debug("list=" + userForm + page);

		// add your code here.

		User user = BeanUtils.copy(userForm, User.class);
		long totalRecord = userService.count(user);
		page.setTotalRecord(totalRecord);

		user.setStart(page.getPageSize() * (page.getCurPage() - 1));
		user.setOffset(page.getPageSize());

		List<User> list = userService.list4Page(user);

		return PagingResult.bulider().list(list).count(totalRecord).build();
	}
	
	@GetMapping(path = "{uid}")
	public Result<User> get(@PathVariable("uid") @Min(0) int id) {
		logger.debug("get=" + id);

		// add your code here.

		User user = getUser0(id);

		return Result.bulider().data(user).build();
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

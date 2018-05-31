package org.ruanwei.demo.user.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Part;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.web.Page;
import org.ruanwei.demo.user.entity.User;
import org.ruanwei.demo.user.service.UserService;
import org.ruanwei.demo.user.web.databind.UserForm;
import org.ruanwei.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author ruanwei
 */
@Validated
@Controller
@RequestMapping(path = "/user/")
public class UserController {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private UserService userService;

	@GetMapping(path = "/list")
	public String list(@Valid @NotNull UserForm userForm, Page page, Model model) {
		logger.debug("list=" + userForm + page);

		// add your code here.

		User user = BeanUtils.copy(userForm, User.class);
		long totalRecord = userService.count(user);
		page.setTotalRecord(totalRecord);

		user.setStart(page.getPageSize() * (page.getCurPage() - 1));
		user.setOffset(page.getPageSize());

		// user.setAge(-1);
		List<User> list = userService.list4Page(user);

		model.addAttribute("list", list);

		return "user/user_list";
	}

	@GetMapping(path = "add")
	public String add() {
		logger.debug("add=");

		// add your code here.

		return "user/user_add";
	}

	@PostMapping(path = "doAdd")
	public String doAdd(@Valid @NotNull UserForm userForm, RedirectAttributes attr) {
		logger.debug("doAdd=" + userForm);

		// add your code here.

		User user = BeanUtils.copy(userForm, User.class);

		userService.add(user);

		// RedirectAttribute and FlashAttribute
		attr.addAttribute("key1", "value1");// /user/list.html?key1=value1
		attr.addFlashAttribute("key2", "value2");// 放到session中然后删除

		// return "forward:/user/list.html"
		return "redirect:/user/list.html";
	}

	// content negotiation using ContentNegotiatingViewResolver.
	@GetMapping(path = "{uid}")
	public String edit(@PathVariable("uid") @Min(0) int id, Model model) {
		logger.debug("edit=" + id);

		// add your code here.

		User user = getUser0(id);
		model.addAttribute("user", user);
		// model.addAttribute(JsonView.class.getName(),User.WithoutPageingView.class);

		return "user/user_edit";
	}

	// content negotiation using
	// HttpMessageConverter(mediaTypes和defaultContentType)
	@GetMapping(path = "{uid}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_PDF_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE,
			MediaType.APPLICATION_RSS_XML_VALUE })
	@ResponseBody
	@JsonView(User.WithPageingView.class)
	public User edit2(@PathVariable("uid") @Min(0) int id) {
		logger.debug("edit2=" + id);

		// add your code here.

		User user = userService.getUser(id);
		return user;
	}

	@PostMapping(path = "doEdit")
	public String doEdit(@Valid @NotNull UserForm userForm) {
		logger.debug("doEdit=" + userForm);

		// add your code here.

		User user = BeanUtils.copy(userForm, User.class);
		userService.edit(user);

		return "redirect:/user/list.html";
	}

	// URL模板路径支持通配符和占位符，其中变量支持正则表达式，变量名默认与方法参数名一致
	@PostMapping(path = "delete/{id}")
	public String delete(@PathVariable @Min(0) int id) {
		logger.debug("delete=" + id);

		// add your code here.

		userService.deleteUser(id);

		return "redirect:/user/list.html";
	}

	@PostMapping(path = "batchDelete")
	public String batchDelete(@RequestParam("ids") @Size(max = 10) String[] ids) {
		logger.debug("batchDelete=" + Arrays.toString(ids));

		// add your code here.

		userService.batchDeleteUser(ids);

		return "redirect:/user/list.html";
	}

	// Using a MultipartResolver with Commons FileUpload.
	@PostMapping(path = "upload")
	public String upload(@RequestParam("name") String name, @RequestParam("file") CommonsMultipartFile file,
			@Valid @RequestPart("file") MultipartFile file2, BindingResult bindingResult,
			MultipartHttpServletRequest request) {
		logger.debug("upload=" + name);
		if (!file.isEmpty()) {
			File mFile = new File(new Date().getTime() + ".jpg");
			try {
				byte[] bytes = file.getBytes();
				// store the bytes somewhere
				file.getFileItem().write(mFile);
			} catch (Exception e) {
				logger.error(e.getMessage() + e.getCause().getMessage(), e);
				// throw new WebException(e.getMessage(),
				// HttpStatus.BAD_REQUEST);
				// add your code here.
			}
			return "redirect:uploadSuccess";
		}
		return "redirect:uploadFailure";
	}

	// Using a MultipartResolver with Servlet 3.0.
	@PostMapping(path = "upload2")
	public String upload2(@RequestParam("name") String name, @RequestParam("file") Part file,
			MultipartHttpServletRequest request) {
		logger.debug("upload2=" + name);
		File mFile = new File(new Date().getTime() + ".jpg");
		try {
			InputStream inputStream = file.getInputStream();
			// store bytes from uploaded file somewhere
			file.write(mFile.getAbsolutePath());
		} catch (IOException e) {
			logger.error(e.getMessage() + e.getCause().getMessage(), e);
			// throw new WebException(e.getMessage(), HttpStatus.BAD_REQUEST);
			// add your code here.
		}
		return "redirect:uploadSuccess";
	}

	@GetMapping(path = "/list2")
	public String list2(@Valid UserForm userForm, BindingResult bindingResult, Page page, Model model) {
		logger.debug("list2=" + userForm);

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			if (fieldError != null) {
				logger.error(fieldError.getRejectedValue() + " is invalid for " + fieldError.getField());
				model.addAttribute("errorMessage",
						fieldError.getRejectedValue() + " is invalid for " + fieldError.getField());
				// throw new
				// WebException(fieldError.getDefaultMessage(),HttpStatus.BAD_REQUEST);
			}
		}

		// add your code here.

		User user = BeanUtils.copy(userForm, User.class);
		long totalRecord = userService.count(user);
		page.setTotalRecord(totalRecord);

		user.setStart(page.getPageSize() * (page.getCurPage() - 1));
		user.setOffset(page.getPageSize());
		List<User> list = userService.list4Page(user);

		model.addAttribute("list", list);

		return "user/user_list";
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

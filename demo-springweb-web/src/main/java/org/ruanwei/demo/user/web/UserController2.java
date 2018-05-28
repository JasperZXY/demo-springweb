package org.ruanwei.demo.user.web;

import org.ruanwei.core.InvalidArgumentException;
import org.ruanwei.core.InvalidLogicException;
import org.ruanwei.core.ServiceException;
import org.ruanwei.core.web.Page;
import org.ruanwei.core.web.PaginatorResult;
import org.ruanwei.core.web.PlainResult;
import org.ruanwei.demo.user.entity.User;
import org.ruanwei.demo.user.service.UserService;
import org.ruanwei.demo.user.web.databind.UserForm;
import org.ruanwei.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 演示数据返回格式
 */
@Validated
@Controller
@RequestMapping(path = "/user2/")
public class UserController2 {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/list")
    @ResponseBody
    public PaginatorResult<User> list(@Valid @NotNull UserForm userForm, Page page) {

        User user = BeanUtils.copy(userForm, User.class);
        long totalRecord = userService.count(user);
        page.setTotalRecord(totalRecord);

        user.setStart(page.getPageSize() * (page.getCurPage() - 1));
        user.setOffset(page.getPageSize());

        List<User> list = userService.list4Page(user);

        return new PaginatorResult<>(page, totalRecord, list);
    }


    @GetMapping(path = "{uid}")
    @ResponseBody
    public PlainResult<User> get(@PathVariable("uid") @Min(0) int id) {
        User user = userService.getUser(id);
        PlainResult<User> plainResult = new PlainResult<>();
        if (user == null) {
            plainResult.setError(101, "该用户不存在");
        }else {
            plainResult.setData(user);
        }
        return plainResult;
    }

    @GetMapping(path = "/error/{type}")
    @ResponseBody
    public PlainResult<User> error(@PathVariable Integer type) throws Exception {
        if (type == null) {
            throw new NullPointerException();
        }
        switch (type) {
            case 1:
                throw new InvalidArgumentException("不支持的参数类型");
            case 2:
                throw new InvalidLogicException("登录态失效");
            case 3:
                throw new ServiceException("获取用户数据失败", 100001);
        }
        return new PlainResult<>(new User("test", type));
    }

}

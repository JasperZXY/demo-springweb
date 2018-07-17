package org.ruanwei.demo.user.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.ruanwei.demo.user.dao.entity.User;
import org.springframework.util.concurrent.ListenableFuture;

public interface UserService {

	public List<User> list4Page(@Valid @NotNull User user);

	public long count(User user);

	public List<User> find(User user);

	public User getUser(long id);
	
	public ListenableFuture<User> getUser0(long id);

	public User getUser2(long id);

	public User getUser3(long id);

	public User getUser4(long id);

	public User getUser5(long id);

	public User getUser6(long id);

	public void edit(User user);

	public void add(User user);

	public void deleteUser(long id);

	public void batchDeleteUser(String[] ids);

}

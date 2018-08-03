package org.ruanwei.demo.remoting.user.service;

import org.ruanwei.demo.remoting.user.entity.User;
import org.ruanwei.demo.springframework.web.core.RemoteAccessException;

public interface UserBurlapService {

	public User getUser(long id) throws RemoteAccessException;

	public void editUser(User user) throws RemoteAccessException;

}

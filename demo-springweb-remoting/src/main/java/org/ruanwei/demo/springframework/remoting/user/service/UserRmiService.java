package org.ruanwei.demo.springframework.remoting.user.service;

import org.ruanwei.demo.springframework.remoting.user.entity.User;
import org.ruanwei.demo.springframework.web.core.RemoteAccessException;

public interface UserRmiService {

	public User getUser(long id) throws RemoteAccessException;
}

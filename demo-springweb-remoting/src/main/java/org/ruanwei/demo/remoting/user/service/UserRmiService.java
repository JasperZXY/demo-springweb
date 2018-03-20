package org.ruanwei.demo.remoting.user.service;

import org.ruanwei.core.RemoteAccessException;
import org.ruanwei.demo.remoting.user.entity.User;

public interface UserRmiService {

	public User getUser(long id) throws RemoteAccessException;
}

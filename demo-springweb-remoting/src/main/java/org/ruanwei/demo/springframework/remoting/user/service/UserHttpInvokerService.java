package org.ruanwei.demo.springframework.remoting.user.service;

import org.ruanwei.demo.core.exception.RemoteAccessException;
import org.ruanwei.demo.springframework.remoting.user.entity.UserResp;

public interface UserHttpInvokerService {

	public UserResp getUser(long id) throws RemoteAccessException;
}

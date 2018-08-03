package org.ruanwei.demo.springframework.remoting.user.service;

import org.ruanwei.demo.springframework.remoting.user.entity.UserResp;
import org.ruanwei.demo.springframework.web.core.RemoteAccessException;

public interface UserJmsService {

	public UserResp getUser(long id) throws RemoteAccessException;
}

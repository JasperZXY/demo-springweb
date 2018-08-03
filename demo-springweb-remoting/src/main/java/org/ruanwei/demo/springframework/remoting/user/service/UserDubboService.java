package org.ruanwei.demo.springframework.remoting.user.service;

import org.ruanwei.demo.springframework.remoting.user.entity.UserResp;
import org.ruanwei.demo.springframework.web.core.RemoteAccessException;

public interface UserDubboService {

	public UserResp getUser(long id) throws RemoteAccessException;

}

package org.ruanwei.demo.user.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.remoting.user.entity.User;
import org.ruanwei.demo.remoting.user.service.UserDubboService;
import org.ruanwei.demo.springframework.web.core.RemoteAccessException;
import org.ruanwei.demo.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userDubboService")
public class UserDubboServiceImpl implements UserDubboService {
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private UserDao userDao;


	@Override
	public User getUser(long id) throws RemoteAccessException {
		try {
			logger.debug("getUser(long id)" + id);

			// add your code here.

			User user = userDao.findById(id);
			return user;
		} catch (Exception e) {
			// add your code here.
			logger.error(e.getMessage(), e);
			throw new RemoteAccessException(e, -3);
		}
	}
}

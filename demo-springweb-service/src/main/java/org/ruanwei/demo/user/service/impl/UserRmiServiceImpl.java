package org.ruanwei.demo.user.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.DataAccessException;
import org.ruanwei.core.RemoteAccessException;
import org.ruanwei.demo.remoting.user.entity.User;
import org.ruanwei.demo.remoting.user.service.UserRmiService;
import org.ruanwei.demo.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userRmiService")
public class UserRmiServiceImpl implements UserRmiService {
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
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new RemoteAccessException(e, -3);
		}
	}
}

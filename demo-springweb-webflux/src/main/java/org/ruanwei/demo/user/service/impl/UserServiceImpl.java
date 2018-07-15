package org.ruanwei.demo.user.service.impl;

import java.util.List;

import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.DataAccessException;
import org.ruanwei.core.RemoteAccessException;
import org.ruanwei.core.ServiceException;
import org.ruanwei.demo.remoting.user.service.UserDubboService;
import org.ruanwei.demo.remoting.user.service.UserHessianService;
import org.ruanwei.demo.remoting.user.service.UserHttpInvokerService;
import org.ruanwei.demo.remoting.user.service.UserJmsService;
import org.ruanwei.demo.remoting.user.service.UserRmiService;
import org.ruanwei.demo.user.dao.UserDao;
import org.ruanwei.demo.user.entity.User;
import org.ruanwei.demo.user.service.UserService;
import org.ruanwei.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.annotation.Validated;

@Validated
@Service("userService")
//@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger logger = LogManager.getLogger();

	//@Autowired
	private UserHessianService userHessianService;

	//@Autowired
	private UserHttpInvokerService userHttpInvokerService;

	//@Autowired
	private UserRmiService userRmiService;

	//@Autowired
	private UserJmsService userJmsService;
	
	//@Autowired
	//@Resource
	private UserDubboService userDubboService;

	@Autowired
	private UserDao userDao;

	//private JdbcTemplate jdbcTemplate;

	// @Autowired
	//private RedisTemplate<String, String> redisTemplate;

	// @Resource(name = "redisTemplate")
	//private ListOperations<String, String> listOps;

	@Required
	@Autowired
	public void setDataSource(DataSource dataSource) {
		//this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<User> list4Page(@Valid @NotNull User user) {
		logger.debug("list4Page user==================" + user);
		List<User> userList;
		try {
			// add your code here.
			userList = userDao.list4page(user);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return userList;
	}

	@Override
	public long count(User user) {
		logger.debug("count user==================" + user);
		long count;
		try {
			// add your code here.
			count = userDao.countByExample(user);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return count;
	}

	@Override
	public List<User> find(User user) {
		logger.debug("find user==================" + user);
		List<User> list;
		try {
			// add your code here.
			list = userDao.findByExample(user);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return list;
	}

	@Override
	public User getUser(long id) {
		logger.debug("getUser id==================" + id);
		User user;
		try {
			// add your code here.
			user = userDao.findById(id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return user;
	}
	
	@Override
	@Async
	public ListenableFuture<User> getUser0(long id) {
		logger.debug("getUser0 id==================" + id);
		User user;
		try {
			// add your code here.
			user = userDao.findById(id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return new AsyncResult<User>(user);
	}

	@Override
	public User getUser2(long id) {
		logger.debug("getUser2 id==================" + id);
		User user;
		try {
			// add your code here.
			org.ruanwei.demo.remoting.user.entity.User u = userHessianService
					.getUser(id);
			user = BeanUtils.copy(u, User.class);
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return user;
	}

	@Override
	public User getUser3(long id) {
		logger.debug("getUser3 id==================" + id);
		User user;
		try {
			// add your code here.
			org.ruanwei.demo.remoting.user.entity.User u = userHttpInvokerService
					.getUser(id);
			user = BeanUtils.copy(u, User.class);
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return user;
	}

	@Override
	public User getUser4(long id) {
		logger.debug("getUser4 id==================" + id);
		User user;
		try {
			// add your code here.
			org.ruanwei.demo.remoting.user.entity.User u = userRmiService
					.getUser(id);
			user = BeanUtils.copy(u, User.class);
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return user;
	}

	@Override
	public User getUser5(long id) {
		logger.debug("getUser5 id==================" + id);
		User user;
		try {
			// add your code here.
			org.ruanwei.demo.remoting.user.entity.User u = userJmsService
					.getUser(id);
			user = BeanUtils.copy(u, User.class);
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return user;
	}
	
	@Override
	public User getUser6(long id) {
		logger.debug("getUser6 id==================" + id);
		User user;
		try {
			// add your code here.
			org.ruanwei.demo.remoting.user.entity.User u = userDubboService
					.getUser(id);
			user = BeanUtils.copy(u, User.class);
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
		return user;
	}

	@Override
	public void edit(User user) {
		logger.debug("edit user==================" + user);
		try {
			// add your code here.
			userDao.update(user);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
	}

	@Override
	public void add(User user) {
		logger.debug("add user==================" + user);
		try {
			// add your code here.
			userDao.save(user);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
	}

	@Override
	public void deleteUser(long id) {
		logger.debug("deleteUser id==================" + id);
		try {
			// add your code here.
			userDao.delete(id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
	}

	@Override
	public void batchDeleteUser(String[] ids) {
		logger.debug("batchDeleteUser ids==================" + ids);
		try {
			// add your code here.
			userDao.batchDelete(ids);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			// add your code here.
			throw new ServiceException(e, -2);
		}
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring/test.xml" });
		context.registerShutdownHook();

		logger.info("======================================================================================");
		UserDubboService userDubboService = context.getBean("userDubboService",
				UserDubboService.class);
		try {
			logger.info("================================="+userDubboService.getUser(1));
			Thread.currentThread().join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}

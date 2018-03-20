package org.ruanwei.demo.user.dao;

import java.util.List;

import org.ruanwei.core.DataAccessException;
import org.ruanwei.demo.remoting.user.entity.User;

public interface UserDao {
	public List<User> list4page(User instance) throws DataAccessException;

	public List<User> findByExample(User instance) throws DataAccessException;

	public long countByExample(User instance) throws DataAccessException;

	public User findById(long id) throws DataAccessException;

	public void save(User instance) throws DataAccessException;

	public void update(User instance) throws DataAccessException;

	public void delete(long id) throws DataAccessException;

	public void batchDelete(String[] ids) throws DataAccessException;

}
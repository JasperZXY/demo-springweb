package org.ruanwei.demo.user.web.databind;

import java.text.ParseException;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.core.InvalidArgumentException;
import org.ruanwei.demo.user.entity.User;
import org.ruanwei.util.Counter;
import org.springframework.format.Formatter;

/**
 * 
 * @author Administrator
 *
 */
final class MyUserFormatter implements Formatter<User> {
	private static final Logger logger = LogManager.getLogger();

	public MyUserFormatter() {
		logger.info("MyUserFormatter==================" + Counter.getCount());
	}

	@Override
	public String print(User user, Locale locale) {
		logger.info("print==================" + Counter.getCount()+" user="+user);
		if (user == null) {
			return "";
		}
		return user.toString();
	}

	@Override
	public User parse(String text, Locale locale) throws ParseException {
		logger.info("parse==================" + Counter.getCount()+" text="+text);
		if (text.length() == 0) {
			return null;
		}
		String[] kv = text.split("/");
		if (kv.length < 2) {
			throw new InvalidArgumentException("没找到分隔符/");
		}
		return new User(kv[0], Integer.parseInt(kv[1]));
	}

}

package org.ruanwei.demo.user.web.databind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.user.entity.User;
import org.ruanwei.util.Counter;
import org.springframework.core.convert.converter.Converter;

/**
 * Take care to ensure that your Converter implementation is thread-safe.
 * 
 * @author Administrator
 *
 */
final class MyStringToUserConverter implements Converter<String, User> {
	private static final Logger logger = LogManager.getLogger();

	public MyStringToUserConverter() {
		logger.info("MyStringToUserConverter()==================" + Counter.getCount());
	}

	@Override
	public User convert(String source) {
		logger.info("convert(String source)==================" + Counter.getCount()+" source=" + source);
		if (source.length() == 0) {
			return null;
		}
		String[] kv = source.split("/");
		return new User(kv[0], Integer.parseInt(kv[1]));
	}

}
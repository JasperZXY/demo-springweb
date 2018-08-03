package org.ruanwei.demo.core.databind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.user.dao.entity.UserEntity;
import org.ruanwei.demo.user.dao.entity.UserEntity;
import org.ruanwei.demo.user.web.command.UserForm;
import org.ruanwei.util.Counter;
import org.springframework.core.convert.converter.Converter;

/**
 * Take care to ensure that your Converter implementation is thread-safe.
 * 
 * @author Administrator
 *
 */
final class StringToUserConverter implements Converter<String, UserForm> {
	private static final Logger logger = LogManager.getLogger();

	public StringToUserConverter() {
		logger.info("MyStringToUserConverter()==================" + Counter.getCount());
	}

	@Override
	public UserForm convert(String source) {
		logger.info("convert(String source)==================" + Counter.getCount()+" source=" + source);
		if (source.length() == 0) {
			return null;
		}
		String[] kv = source.split("/");
		return new UserForm(kv[0], Integer.parseInt(kv[1]));
	}

}

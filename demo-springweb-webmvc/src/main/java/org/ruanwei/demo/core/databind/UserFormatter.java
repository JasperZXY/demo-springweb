package org.ruanwei.demo.core.databind;

import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ruanwei.demo.user.dao.entity.UserEntity;
import org.springframework.format.Formatter;

/**
 * 
 * @author Administrator
 *
 */
public final class UserFormatter implements Formatter<UserEntity> {
	private static Log log = LogFactory.getLog(UserFormatter.class);
	
	private String delimiter;

	public UserFormatter() {
		log.info("PeopleFormatter()");
		this.delimiter = "/";
	}
	
	public UserFormatter(String delimiter) {
		log.info("PeopleFormatter(String delimiter)" + delimiter);
		this.delimiter = delimiter;
	}

	@Override
	public String print(UserEntity people, Locale locale) {
		log.info("print(People people, Locale locale) " + people);
		if (people == null) {
			return "People";
		}
		return people.toString();
	}

	@Override
	public UserEntity parse(String text, Locale locale) throws ParseException {
		log.info("parse(String text, Locale locale) " + text);
		if (text == null || text.isEmpty()) {
			return null;
		}
		String[] kv = text.split(delimiter);
		return new UserEntity(kv[0], Integer.parseInt(kv[1]));
	}

}

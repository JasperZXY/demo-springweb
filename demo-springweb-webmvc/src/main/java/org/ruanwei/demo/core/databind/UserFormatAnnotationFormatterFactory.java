package org.ruanwei.demo.core.databind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ruanwei.demo.core.databind.UserFormat.Separator;
import org.ruanwei.demo.remoting.user.entity.User;
import org.ruanwei.demo.user.dao.entity.UserEntity;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

/**
 * 
 * @author Administrator
 *
 */
public final class UserFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<UserFormat> {
	private static Log log = LogFactory.getLog(UserFormatAnnotationFormatterFactory.class);

	public UserFormatAnnotationFormatterFactory() {
		log.info("PeopleFormatAnnotationFormatterFactory()");
	}

	@Override
	public Set<Class<?>> getFieldTypes() {
		log.info("getFieldTypes()");
		return new HashSet<Class<?>>(Arrays.asList(new Class<?>[] { User.class }));
	}

	@Override
	public Printer<UserEntity> getPrinter(UserFormat annotation, Class<?> fieldType) {
		log.info("getPrinter(MyPeopleFormat annotation, Class<?> fieldType)");
		return configureFormatterFrom(annotation, fieldType);
	}

	@Override
	public Parser<UserEntity> getParser(UserFormat annotation, Class<?> fieldType) {
		log.info("getParser(MyPeopleFormat annotation, Class<?> fieldType)");
		return configureFormatterFrom(annotation, fieldType);
	}

	private Formatter<UserEntity> configureFormatterFrom(UserFormat annotation, Class<?> fieldType) {
		Separator separator = annotation.separator();
		if (separator == Separator.SLASH) {
			return new UserFormatter("/");
		} else if (separator == Separator.COMMA) {
			return new UserFormatter(",");
		} else {
			return new UserFormatter("/");
		}
	}

}

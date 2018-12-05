package com.stosz.tms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class NullMarshallerListener extends javax.xml.bind.Marshaller.Listener {

	public static final Logger logger = LoggerFactory.getLogger(NullMarshallerListener.class);

	@Override
	public void beforeMarshal(Object source) {
		Field[] fields = source.getClass().getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				if (f.get(source) == null) {
					if (f.getType() == String.class) {
						f.set(source, "");
					} else if (f.getType() == Integer.class) {
						f.set(source, 0);
					} else if (f.getType() == Double.class) {
						f.set(source, 0d);
					} else if (f.getType() == BigDecimal.class) {
						f.set(source, new BigDecimal(0));
					}
				}
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
}

package com.stosz.plat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.util.StringUtils;

public class JaxbDateSerializer extends XmlAdapter<String, Date> {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date unmarshal(String v) throws Exception {
		if (StringUtils.hasText(v)) {
			return sdf.parse(v);
		}
		return null;
	}

	@Override
	public String marshal(Date v) throws Exception {
		if (v != null) {
			return sdf.format(v);
		}
		return null;
	}

}

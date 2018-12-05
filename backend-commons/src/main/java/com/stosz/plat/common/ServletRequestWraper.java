package com.stosz.plat.common;

import com.stosz.plat.utils.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServletRequestWraper extends HttpServletRequestWrapper {

	private HashMap<String, String> localHashMap;

	public ServletRequestWraper(HttpServletRequest request) {
		super(request);
		localHashMap = new HashMap<>();
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null && localHashMap.containsKey(name)) {
			return localHashMap.get(name);
		}
		return value;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Map<String, String[]> paramMap = this.getParameterMap();
		return Collections.enumeration(paramMap.keySet());
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> paramMap = super.getParameterMap();
		if (ArrayUtils.isEmpty(localHashMap)) {
			return paramMap;
		}
		HashMap<String, String[]> newParamMap = new HashMap<>(paramMap);
		for (String key : localHashMap.keySet()) {
			String value = localHashMap.get(key);
			String[] values = newParamMap.get(key);
			if (values == null) {
				values = new String[] { value };
				newParamMap.put(key, values);
			} else {
				String[] newArray = new String[values.length + 1];
				System.arraycopy(values, 0, newArray, 0, values.length);
				newArray[values.length] = value;
				newParamMap.put(key, newArray);
			}
		}
		return newParamMap;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] array = super.getParameterValues(name);
		if (ArrayUtils.isEmpty(localHashMap) || !localHashMap.containsKey(name)) {
			return array;
		}
		String value = localHashMap.get(name);
		if (array == null) {
			return new String[] { value };
		}
		String[] newArray = new String[array.length + 1];
		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = value;
		return newArray;

	}

	public void addParameter(String param, String value) {
		localHashMap.put(param, value);
	}

}

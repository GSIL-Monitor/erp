package com.stosz.tms.dto;

import java.util.LinkedHashMap;

public class Parameter<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 7119730391152931556L;

	public String getString(String key) {
		if (this.containsKey(key)) {
			V value = this.get(key);
			if (value == null) {
				return null;
			}
			if (value instanceof String) {
				return (String) value;
			}
			return String.valueOf(value);
		}
		return null;
	}

	public Integer getInteger(String key) {
		if (this.containsKey(key)) {
			V value = this.get(key);
			if (value == null) {
				return null;
			}
			if (value instanceof Integer) {
				return (Integer) value;
			}
			return Integer.valueOf(String.valueOf(value));
		}
		return null;
	}

	public Long getLong(String key) {
		if (this.containsKey(key)) {
			V value = this.get(key);
			if (value == null) {
				return null;
			}
			if (value instanceof Long) {
				return (Long) value;
			}
			return Long.valueOf(String.valueOf(value));
		}
		return null;
	}

	public Double getDouble(String key) {
		if (this.containsKey(key)) {
			V value = this.get(key);
			if (value == null) {
				return null;
			}
			if (value instanceof Double) {
				return (Double) value;
			}
			return Double.valueOf(String.valueOf(value));
		}
		return null;
	}

	public Double getDouble(String key, Double defaultValue) {
		Double value = this.getDouble(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public Long getLong(String key, Long defaultValue) {
		Long value = this.getLong(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public Integer getInteger(String key, Integer defaultValue) {
		Integer value = this.getInteger(key);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public <T> T getObject(String key) {
		if (this.containsKey(key)) {
			V value = this.get(key);
			if (value == null) {
				return null;
			}
			return (T) value;
		}
		return null;
	}

}

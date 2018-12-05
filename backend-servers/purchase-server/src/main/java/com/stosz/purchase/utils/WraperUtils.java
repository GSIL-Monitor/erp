package com.stosz.purchase.utils;

public class WraperUtils {

	private static ThreadLocal<Object> dest = new ThreadLocal<Object>();

	public static <T> void set(T value) {
		dest.set(value);
	}

	public static <T> T get() {
		return (T) dest.get();
	}
}

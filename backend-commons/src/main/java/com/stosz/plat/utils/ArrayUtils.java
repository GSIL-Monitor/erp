package com.stosz.plat.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class ArrayUtils {
	
	
	public static <T> boolean isEmpty(T [] array) {
		if(array==null) {
			return true;
		}else if(array.length<=0) {
			return true;
		}
		return false;
	}
	
	public static <T> boolean isNotEmpty(T [] array) {
		return !isEmpty(array);
	}

	public static <K, V> boolean isNotEmpty(Map<K, V> map) {
		if (map == null)
			return false;
		if (map.isEmpty())
			return false;
		return true;
	}
	
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return !isNotEmpty(map);
	}

	public static <T> boolean isEmpty(Collection<T> list) {
		if (list == null)
			return true;
		if (list.isEmpty())
			return true;
		return false;
	}

	public static <T> boolean isNotEmpty(Collection<T> list) {
		return !isEmpty(list);
	}

	public static <T> T getEntity(Function<T, T> function) {
		return function.apply(null);
	}

	public static <T> String join(Collection<T> list, String splitter) {
		if (ArrayUtils.isEmpty(list)) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (T item : list) {
			if (builder.length() != 0) {
				builder.append(splitter);
			}
			if (item != null) {
				builder.append(item);
			}
		}
		return builder.toString();
	}

	public static int[] listToArray(Collection<Integer> list) {
		int[] array = new int[list.size()];
		int count = 0;
		for (Integer integer : list) {
			array[count] = integer;
			count++;
		}
		return array;
	}
}
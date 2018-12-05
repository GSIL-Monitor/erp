package com.stosz.tms.service;

import com.stosz.tms.dto.Parameter;

public interface IRecursiveProcessor<R, T> {

	public R recursiveHandle(T instance, Parameter<String, Object> params);
}

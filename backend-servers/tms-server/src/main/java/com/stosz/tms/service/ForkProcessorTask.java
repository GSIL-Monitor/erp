package com.stosz.tms.service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.tms.dto.Parameter;

public class ForkProcessorTask<R, T> implements Runnable {

	private CountDownLatch countDownLatch;

	private IRecursiveProcessor<R, T> recursiveProcessor;

	private List<T> taskList;

	private Parameter<String, Object> params;

	public ForkProcessorTask(List<T> taskList, CountDownLatch countDownLatch, IRecursiveProcessor<R, T> recursiveProcessor,
			Parameter<String, Object> params) {
		this.taskList = taskList;
		this.countDownLatch = countDownLatch;
		this.recursiveProcessor = recursiveProcessor;
		this.params = params;
	}
	
	public ForkProcessorTask(List<T> taskList, CountDownLatch countDownLatch, IRecursiveProcessor<R, T> recursiveProcessor) {
		this.taskList = taskList;
		this.countDownLatch = countDownLatch;
		this.recursiveProcessor = recursiveProcessor;
	}

	@Override
	public void run() {
		if (ArrayUtils.isNotEmpty(taskList)) {
			for (T item : taskList) {
				recursiveProcessor.recursiveHandle(item, params);
			}
			countDownLatch.countDown();
		}
	}

}

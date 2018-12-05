package com.stosz.tms.service.track.impl;

import com.stosz.plat.common.ResultBean;
import com.stosz.plat.common.SpringContextHolder;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.enums.TrackApiTypeEnum;
import com.stosz.tms.model.TrackingTaskBlackcatDetail;
import com.stosz.tms.parse.startegy.AbstractParseStartegy;
import com.stosz.tms.service.TrackingStatusClassifyService;
import com.stosz.tms.service.TrackingTaskBlackcatDetailService;
import com.stosz.tms.service.TrackingTaskService;
import com.stosz.tms.service.track.AbstractTrackHandler;
import com.stosz.tms.task.BlackCatTrackTaskImpl;
import com.stosz.tms.vo.TrackStatusClassifyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 黑猫物流轨迹查询
 * @author feiheping
 * @version [1.0,2017年12月9日]
 */
@Component
public class BlackCatTrackHandler extends AbstractTrackHandler {

	private Logger logger = LoggerFactory.getLogger(CxcTrackHandler.class);

	public BlackCatTrackHandler(@Qualifier("blackCatParseStartegy") AbstractParseStartegy parseStartegy) {
		super(parseStartegy);
	}

	@Resource
	TrackingTaskService trackingTaskService;

	@Autowired
	private TrackingTaskBlackcatDetailService blackcatDetailService;

	@Resource(name = "blackCatClassifyThreadPool")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	// 轨迹文件位置
	@Value("${track.filepath.blackcat}")
	private String filepath;

	@Autowired
	private TrackingStatusClassifyService statusClassifyService;

	private static int MAX_THREAD = 5;

	public void captureTransTrack() {
		// 2.获取轨迹文件
		List<File> fileList = this.getFileList(filepath);
		if (ArrayUtils.isNotEmpty(fileList)) {
			// 3.将文件解析为标准对象
			for (File file : fileList) {
				try {
					logger.info("captureTransTrack() 解析文件 fileName={}", file.getName());
					ResultBean<List<TrackingTaskBlackcatDetail>> resultBean = analyzeByFile(file);
					if (ResultBean.OK.equals(resultBean.getCode())) {
						List<TrackingTaskBlackcatDetail> taskDetails = resultBean.getItem();
						if (ArrayUtils.isNotEmpty(taskDetails)) {
							blackcatDetailService.addList(taskDetails);
						}
						// 解析没有异常则更改文件为备份文件
						file.renameTo(new File(StringUtil.concat(file.getAbsolutePath(), ".bak")));
					}
					resultBean = null;
					logger.info("captureTransTrack() 文件解析成功 fileName={}", file.getName());
				} catch (Exception e) {
					logger.info("captureTransTrack() Exception={}", e);
				}
			}
		} else {
			logger.info("captureTransTrack() 黑猫轨迹文件不存在");
		}
		logger.info("captureTransTrack() 开始执行黑猫数据归类任务");

		// 获取到黑猫的物流归类基础数据
		Map<String, TrackStatusClassifyVo> statusClassifyList = statusClassifyService.getClassifyMapByShippingCode(TrackApiTypeEnum.BLACKCAT.code());
		int rowCount = trackingTaskService.getTrackTaskCount(TrackApiTypeEnum.BLACKCAT.code());
		if (rowCount > 0) {
			int targetCount = (int) Math.ceil(rowCount / (double) MAX_THREAD);
			CountDownLatch countDownLatch = new CountDownLatch(MAX_THREAD);
			for (int i = 0; i < MAX_THREAD; i++) {
				BlackCatTrackTaskImpl blackCatTrackTaskImpl = SpringContextHolder.getBean(BlackCatTrackTaskImpl.class);
				int beginIndex = i * targetCount;
				int endIndex = (i + 1) * targetCount;
				endIndex = endIndex > rowCount ? rowCount : endIndex;
				blackCatTrackTaskImpl.setBeginIndex(beginIndex);
				blackCatTrackTaskImpl.setEndIndex(endIndex);

				blackCatTrackTaskImpl.setStatusClassifyList(statusClassifyList);
				blackCatTrackTaskImpl.setCountDownLatch(countDownLatch);
				threadPoolTaskExecutor.submit(blackCatTrackTaskImpl);
			}
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
		} else {
			logger.info("captureTransTrack() 未找到需要归类的数据");
		}
	}

	private ResultBean<List<TrackingTaskBlackcatDetail>> analyzeByFile(File file) {
		ResultBean<List<TrackingTaskBlackcatDetail>> resultBean = new ResultBean<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Big5_HKSCS"))) {
			List<TrackingTaskBlackcatDetail> taskDetails = new ArrayList<>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] lineSplit = line.split("\\|");
				if (lineSplit.length < 7) {
					continue;
				}
				String trackNo = lineSplit[0];
				String location = lineSplit[2];
				Date trackTime = DateUtils.str2Date(lineSplit[3], "yyyyMMddHHmmss");
				String trackStatus = lineSplit[5];
				String trackRecord = lineSplit[6].split(" ")[0];
				TrackingTaskBlackcatDetail trackingTaskDetail = new TrackingTaskBlackcatDetail(trackNo, trackStatus, trackTime, location,
						trackRecord);
				trackingTaskDetail.setCreateAt(new Date());
				trackingTaskDetail.setFileName(file.getName());

				taskDetails.add(trackingTaskDetail);
			}
			resultBean.setItem(taskDetails);
			resultBean.setCode(ResultBean.OK);
		} catch (Exception e) {
			logger.trace("analyzeInStorageByFile Exception={}", e);
			resultBean.setCode(ResultBean.FAIL);
		}
		return resultBean;
	}

	public TrackApiTypeEnum getCode() {
		return TrackApiTypeEnum.BLACKCAT;
	}

	/**
	 * 获取需解析的黑猫文件
	 * @param dirPath
	 * @return
	 */
	private List<File> getFileList(String dirPath) {
		File file = new File(dirPath);
		File[] files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return false;
				}
				String filePath = file.getAbsolutePath();
				if (filePath.endsWith(".SOD") || filePath.endsWith(".NOD")) {
					return true;
				}
				return false;
			}
		});
		if (ArrayUtils.isEmpty(files)) {
			return null;
		}
		List<File> fileList = Stream.of(files).sorted((src, target) -> {
			long srcValue = src.lastModified();
			long targetValue = target.lastModified();
			if (srcValue > targetValue) {
				return 1;
			} else if (srcValue < targetValue) {
				return -1;
			}
			return 0;
		}).collect(Collectors.toList());
		return fileList;
	}

}

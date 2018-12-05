package com.stosz.tms.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.stosz.plat.utils.EncryptUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.mapper.ShippingParcelMapper;
import com.stosz.tms.mapper.TrackingTaskDetailMapper;
import com.stosz.tms.mapper.TrackingTaskMapper;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.TrackingTask;
import com.stosz.tms.model.TrackingTaskDetail;

@Service
public class TrackingTaskImportDataService {

	@Resource
	private ShippingParcelMapper shippingParcelMapper;

	@Resource
	private TrackingTaskMapper taskMapper;

	@Resource
	private TrackingTaskDetailMapper taskDetailMapper;

	@Transactional(transactionManager = "tmsTransactionManager", rollbackFor = Exception.class)
	public void importData(ShippingParcel shippingParcel, TrackingTask trackingTask, TrackingTaskDetail taskDetail) {
		Assert.isTrue(shippingParcelMapper.updateSelective(shippingParcel) > 0, "更新包裹信息失败");

		if (trackingTask.getId() != null) {
			Assert.isTrue(taskMapper.updateSelective(trackingTask) > 0, "更新物流任务信息失败");
		} else {
			Assert.isTrue(taskMapper.add(trackingTask) > 0, "保存物流任务信息失败");
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String orignl = StringUtil.concat(trackingTask.getId(), trackingTask.getTrackNo(), trackingTask.getTrackStatus(),
				df.format(trackingTask.getTrackStatusTime()));
		taskDetail.setTrackingTaskId(Long.valueOf(trackingTask.getId().toString()));
		taskDetail.setHashValue(EncryptUtils.md5(orignl));

		Assert.isTrue(taskDetailMapper.add(taskDetail) > 0, "保存物流轨迹失败");

	}
	


}

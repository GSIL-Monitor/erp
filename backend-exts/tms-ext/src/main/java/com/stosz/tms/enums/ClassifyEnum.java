package com.stosz.tms.enums;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.stosz.plat.utils.IEnum;

public enum ClassifyEnum implements IEnum {

	NOTCLASSIFY("-1", "无需归类"), UNKNOWN("0", "未知状态"), NOTONLINE("1", "未上线"), SALESRETURN("2", "退货"), DELIVERYFAILURE("3", "派送失败"), REJECTION("4",
			"拒收"), PROBLEMSHIPMENT("5", "问题件"), PENDING("6", "配送中"), RECEIVE("7", "已签收");

	private String code;

	private String name;

	ClassifyEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String display() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ClassifyEnum getEnum(String code) {
		for (ClassifyEnum classifyEnum : ClassifyEnum.values()) {
			if (classifyEnum.getCode().equals(code)) {
				return classifyEnum;
			}
		}
		return null;
	}

	public static ClassifyEnum getEnumByName(String name) {
		for (ClassifyEnum classifyEnum : ClassifyEnum.values()) {
			if (classifyEnum.getName().equals(name)) {
				return classifyEnum;
			}
		}
		return null;
	}

	/**
	 * 定义归类状态，能变更为哪些状态
	 */
	public static Map<String, HashSet<String>> classifyReverseMap = getClassifyReverse();

	/**
	 * 定义哪些物流状态，更改轨迹主表的状态为已完成
	 */
	public static Set<String> completeClassifySet = Sets.newHashSet(RECEIVE.code);

	public static Map<String, HashSet<String>> getClassifyReverse() {
		Map<String, HashSet<String>> hashMap = new HashMap<String, HashSet<String>>();

		//
		// 未上线 (配送中 已签收 问题件 拒收 派送失败 退货)
		//
		// 配送中 (已签收 拒收 问题件 派送失败 退货)
		//
		// 已签收()
		//
		// 拒收 (已签收，退货)
		//
		// 问题件 (已签收 派送失败 拒收 退货 )
		//
		// 派送失败
		//
		// 退货

		// 未上线 (配送中 已签收 问题件 拒收 派送失败 退货)
		hashMap.put(ClassifyEnum.NOTONLINE.code,
				Sets.newHashSet(PENDING.code, RECEIVE.code, PROBLEMSHIPMENT.code, REJECTION.code, DELIVERYFAILURE.code, SALESRETURN.code));

		// 配送中 (已签收 拒收 问题件 派送失败 退货)
		hashMap.put(ClassifyEnum.PENDING.code,
				Sets.newHashSet(RECEIVE.code, REJECTION.code, PROBLEMSHIPMENT.code, DELIVERYFAILURE.code, SALESRETURN.code));

		HashSet<String> emptySet = new HashSet<>();
		// 已签收()
		hashMap.put(ClassifyEnum.RECEIVE.code, emptySet);

		// 拒收 (已签收，退货)
		hashMap.put(ClassifyEnum.REJECTION.code, Sets.newHashSet(RECEIVE.code, SALESRETURN.code));

		// 问题件 (已签收 派送失败 拒收 退货 )
		hashMap.put(ClassifyEnum.PROBLEMSHIPMENT.code, Sets.newHashSet(RECEIVE.code, DELIVERYFAILURE.code, REJECTION.code, SALESRETURN.code));

		// 派送失败
		hashMap.put(DELIVERYFAILURE.code, emptySet);

		// 退货
		hashMap.put(SALESRETURN.code, emptySet);

		return hashMap;
	}
}

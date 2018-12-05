package com.stosz.tms.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.stosz.plat.utils.ArrayUtils;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.tms.model.ShippingStoreRel;
import com.stosz.tms.model.ShippingWay;

public class ShippingStoreLabel {

	private ShippingStoreRel shippingStoreRel;

	private Set<Integer> forbiddenLables;// 禁运的物流标签ID

	private Set<Integer> generalLabels;// 普货可配的物流标签ID

	private Set<Integer> specialLabels;// 特货可配的物流标签ID

	private static String forbiddenType = "0";

	private static String generalType = "1";

	private static String specialType = "2";

	public ShippingStoreLabel() {
		this.forbiddenLables = new HashSet<>();
		this.generalLabels = new HashSet<>();
		this.specialLabels = new HashSet<>();
	}

	public ShippingStoreRel getShippingStoreRel() {
		return shippingStoreRel;
	}

	public void setShippingStoreRel(ShippingStoreRel shippingStoreRel) {
		this.shippingStoreRel = shippingStoreRel;
	}

	public Set<Integer> getForbiddenLables() {
		return forbiddenLables;
	}

	public void setForbiddenLables(Set<Integer> forbiddenLables) {
		this.forbiddenLables = forbiddenLables;
	}

	public Set<Integer> getGeneralLabels() {
		return generalLabels;
	}

	public void setGeneralLabels(Set<Integer> generalLabels) {
		this.generalLabels = generalLabels;
	}

	public Set<Integer> getSpecialLabels() {
		return specialLabels;
	}

	public void setSpecialLabels(Set<Integer> specialLabels) {
		this.specialLabels = specialLabels;
	}

	public static ShippingStoreLabel groupLabel(ShippingStoreRel shippingStoreRel, List<LabelObject> LabelObjects) {
		ShippingStoreLabel shippingStoreLabel = new ShippingStoreLabel();
		if (ArrayUtils.isNotEmpty(LabelObjects)) {
			for (LabelObject label : LabelObjects) {
				if (forbiddenType.equals(label.getExtendValue())) {
					shippingStoreLabel.forbiddenLables.add(label.getLabelId());
				} else if (generalType.equals(label.getExtendValue())) {
					shippingStoreLabel.generalLabels.add(label.getLabelId());
				} else if (specialType.equals(label.getExtendValue())) {
					shippingStoreLabel.specialLabels.add(label.getLabelId());
				}
			}
		}
		shippingStoreLabel.setShippingStoreRel(shippingStoreRel);
		return shippingStoreLabel;
	}
}

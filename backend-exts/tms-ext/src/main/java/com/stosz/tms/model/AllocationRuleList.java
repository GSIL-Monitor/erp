package com.stosz.tms.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rules" })
@XmlRootElement(name = "rules")
public class AllocationRuleList {

	@XmlElement(name = "rule")
	private List<AllocationRule> rules;

	public List<AllocationRule> getRules() {
		return rules;
	}

	public void setRules(List<AllocationRule> rules) {
		this.rules = rules;
	}
	
	
	

}

//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.06.20 时间 05:33:17 PM CST
//


package com.stosz.fsm.xml;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * anonymous complex type的 Java 类。
 * 
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="States">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="State" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="Event" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="DstState" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="TargetState" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Checker" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Start" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="End" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="StateGroups">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="StateGroup" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="States" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Event" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="DstState" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="before" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="trigger" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="before" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="trigger" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="EntityName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="EntityDao" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ParentEntityName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"states", "stateGroups"})
@XmlRootElement(name = "FSM")
public class FsmRootNode {

	@XmlElement(name = "States", required = true)
	protected FsmRootNode.States		states;
	@XmlElement(name = "StateGroups", required = true)
	protected FsmRootNode.StateGroups	stateGroups	= new FsmRootNode.StateGroups();
	@XmlAttribute(name = "EntityName", required = true)
	protected String					entityName;
	@XmlAttribute(name = "EntityDao", required = true)
	protected String					entityDao;
	@XmlAttribute(name = "ParentEntityName", required = true)
	protected String					parentEntityName;

	/**
	 * 获取states属性的值。
	 *
	 * @return possible object is {@link FsmRootNode.States }
	 *
	 */
	public FsmRootNode.States getStates() {
		return states;
	}

	/**
	 * 设置states属性的值。
	 *
	 * @param value allowed object is {@link FsmRootNode.States }
	 *
	 */
	public void setStates(FsmRootNode.States value) {
		this.states = value;
	}

	/**
	 * 获取stateGroups属性的值。
	 *
	 * @return possible object is {@link FsmRootNode.StateGroups }
	 *
	 */
	public FsmRootNode.StateGroups getStateGroups() {
		return stateGroups;
	}

	/**
	 * 设置stateGroups属性的值。
	 *
	 * @param value allowed object is {@link FsmRootNode.StateGroups }
	 *
	 */
	public void setStateGroups(FsmRootNode.StateGroups value) {
		this.stateGroups = value;
	}

	/**
	 * 获取entityName属性的值。
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getEntityName() {
		return entityName.trim();
	}

	/**
	 * 设置entityName属性的值。
	 *
	 * @param value allowed object is {@link String }
	 *
	 */
	public void setEntityName(String value) {
		this.entityName = value;
	}

	/**
	 * 获取entityDao属性的值。
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getEntityDao() {
		return entityDao;
	}

	/**
	 * 设置entityDao属性的值。
	 *
	 * @param value allowed object is {@link String }
	 *
	 */
	public void setEntityDao(String value) {
		this.entityDao = value;
	}

	/**
	 * 获取parentEntityName属性的值。
	 *
	 * @return possible object is {@link String }
	 *
	 */
	public String getParentEntityName() {
		return parentEntityName.trim();
	}

	/**
	 * 设置parentEntityName属性的值。
	 *
	 * @param value allowed object is {@link String }
	 *
	 */
	public void setParentEntityName(String value) {
		this.parentEntityName = value;
	}


	/**
	 * <p>
	 * anonymous complex type的 Java 类。
	 *
	 * <p>
	 * 以下模式片段指定包含在此类中的预期内容。
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="StateGroup" maxOccurs="unbounded">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="States" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="Event" minOccurs="0">
	 *                     &lt;complexType>
	 *                       &lt;complexContent>
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                           &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="DstState" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="before" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="trigger" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                         &lt;/restriction>
	 *                       &lt;/complexContent>
	 *                     &lt;/complexType>
	 *                   &lt;/element>
	 *                 &lt;/sequence>
	 *                 &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="before" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="trigger" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 *
	 *
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"stateGroup"})
	public static class StateGroups {

		@XmlElement(name = "StateGroup", required = true)
		protected List<StateGroupNode>	stateGroup	= new ArrayList<StateGroupNode>();

		/**
		 * Gets the value of the stateGroup property.
		 *
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
		 * modification you make to the returned list will be present inside the JAXB object. This
		 * is why there is not a <CODE>set</CODE> method for the stateGroup property.
		 *
		 * <p>
		 * For example, to add a new item, do as follows:
		 *
		 * <pre>
		 * getStateGroup().add(newItem);
		 * </pre>
		 *
		 *
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link FsmRootNode.StateGroups.StateGroupNode }
		 * 
		 * 
		 */
		public List<StateGroupNode> getStateGroup() {
			if (stateGroup == null) {
				stateGroup = new ArrayList<StateGroupNode>();
			}
			return this.stateGroup;
		}



	}


	/**
	 * <p>
	 * anonymous complex type的 Java 类。
	 * 
	 * <p>
	 * 以下模式片段指定包含在此类中的预期内容。
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="State" maxOccurs="unbounded">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence minOccurs="0">
	 *                   &lt;element name="Event" maxOccurs="unbounded">
	 *                     &lt;complexType>
	 *                       &lt;complexContent>
	 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                           &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="DstState" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                           &lt;attribute name="TargetState" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                         &lt;/restriction>
	 *                       &lt;/complexContent>
	 *                     &lt;/complexType>
	 *                   &lt;/element>
	 *                   &lt;element name="Checker" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
	 *                 &lt;/sequence>
	 *                 &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *                 &lt;attribute name="Start" type="{http://www.w3.org/2001/XMLSchema}boolean" />
	 *                 &lt;attribute name="End" type="{http://www.w3.org/2001/XMLSchema}boolean" />
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {"state"})
	public static class States {

		@XmlElement(name = "State", required = true)
		protected List<StateNode>	state;

		/**
		 * Gets the value of the state property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
		 * modification you make to the returned list will be present inside the JAXB object. This
		 * is why there is not a <CODE>set</CODE> method for the state property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getState().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link StateNode }
		 * 
		 * 
		 */
		public List<StateNode> getState() {
			if (state == null) {
				state = new ArrayList<StateNode>();
			}
			return this.state;
		}



	}

}

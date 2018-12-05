package com.stosz.fsm.xml;

import javax.xml.bind.JAXBElement;
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
 *         &lt;element name="States" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Event" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="DstState" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="before" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="trigger" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="before" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="after" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="trigger" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"states", "event"})
public class StateGroupNode {

	@XmlElement(name = "States", required = true)
	protected String			states;
	@XmlElement(name = "Event")
	protected List<EventNode>	event;
	@XmlAttribute(name = "Name", required = true)
	protected String			name;
	@XmlAttribute(name = "Description", required = true)
	protected String			description;
	@XmlAttribute(name = "Before")
	protected String			before	= "";
	@XmlAttribute(name = "After")
	protected String			after	= "";
	@XmlAttribute(name = "Trigger")
	protected String			trigger	= "";
	@XmlAttribute(name = "Start")
	protected Boolean			start;
	@XmlAttribute(name = "End")
	protected Boolean			end;

	/**
	 * 获取states属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public List<String> getStates() {
		List<String> result = new ArrayList<String>();
		if (states == null || states.trim().length() <= 0) {
			return result;
		}

		String[] buf = this.states.split(",");
		for (String string : buf) {
			result.add(string.trim());
		}
		return result;
	}

	/**
	 * 设置states属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setStates(String value) {
		this.states = value;
	}

	/**
	 * Gets the value of the content property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
	 * modification you make to the returned list will be present inside the JAXB object. This is
	 * why there is not a <CODE>set</CODE> method for the content property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getContent().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link JAXBElement }{@code <}
	 * {@link Object }{@code >} {@link String } {@link JAXBElement }{@code <}
	 * {@link FsmRootNode.States.StateNode.EventNode }{@code >}
	 * 
	 * 
	 */
	public List<EventNode> getEvent() {
		if (event == null) {
			event = new ArrayList<EventNode>();
		}
		return this.event;
	}

	/**
	 * 获取name属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name.trim();
	}

	/**
	 * 设置name属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * 获取description属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription() {
		return description.trim();
	}

	/**
	 * 设置description属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * 获取before属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBefore() {
		return before;
	}

	/**
	 * 设置before属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setBefore(String value) {
		this.before = value;
	}

	/**
	 * 获取after属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAfter() {
		return after;
	}

	/**
	 * 设置after属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setAfter(String value) {
		this.after = value;
	}

	/**
	 * 获取trigger属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTrigger() {
		return trigger;
	}

	/**
	 * 设置trigger属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTrigger(String value) {
		this.trigger = value;
	}

	/**
	 * 获取start属性的值。
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isStart() {
		return start;
	}

	/**
	 * 设置start属性的值。
	 * 
	 * @param value allowed object is {@link Boolean }
	 * 
	 */
	public void setStart(Boolean value) {
		this.start = value;
	}

	/**
	 * 获取end属性的值。
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isEnd() {
		return end;
	}

	/**
	 * 设置end属性的值。
	 * 
	 * @param value allowed object is {@link Boolean }
	 * 
	 */
	public void setEnd(Boolean value) {
		this.end = value;
	}

	/**
	 * 拷贝Event以及Handle信息
	 * 
	 * StateGroup的BeforeHandle最先执行 StateGroup的AfterHandle最后执行 StateGroup的TriggerHandle最先执行
	 *
	 * @param stateGroup
	 * @return void
	 * @throws
	 */
	public void extend(StateGroupNode stateGroup) {
		if (stateGroup.before != null) {
			this.before = stateGroup.before + "," + this.before;
		}
		if (stateGroup.after != null) {
			this.after = this.after + "," + stateGroup.after;
		}
		if (stateGroup.trigger != null) {
			this.trigger = stateGroup.trigger + "," + this.trigger;
		}

		for (EventNode event : stateGroup.getEvent()) {
			this.getEvent().add(event.clone());
		}
	}


}

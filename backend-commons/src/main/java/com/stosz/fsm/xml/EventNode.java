package com.stosz.fsm.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


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
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DstState" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TargetState" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class EventNode implements Cloneable {

	@XmlAttribute(name = "Name", required = true)
	protected String	name;
	@XmlAttribute(name = "Description", required = true)
	protected String	description;
	@XmlAttribute(name = "DstState")
	protected String	dstState;
	@XmlAttribute(name = "Before")
	protected String	before	= "";
	@XmlAttribute(name = "After")
	protected String	after	= "";
	@XmlAttribute(name = "Trigger")
	protected String	trigger	= "";

	protected String	srcState;

	/**
	 * 获取name属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
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
		return description;
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
	 * 获取srcState属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSrcState() {
		return srcState;
	}

	/**
	 * 设置srcState属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSrcState(String value) {
		this.srcState = value;
	}

	/**
	 * 获取dstState属性的值。
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDstState() {
		return dstState;
	}

	/**
	 * 设置dstState属性的值。
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDstState(String value) {
		this.dstState = value;
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
	 * 拷贝Handle信息
	 * 
	 * State的BeforeHandle最先执行 State的AfterHandle最后执行 State的TriggerHandle最先执行
	 *
	 * @param state
	 * @return void
	 * @throws
	 */
	public void extend(StateNode state) {
		if (state.before != null) {
			this.before = state.before + "," + this.before;
		}
		if (state.after != null) {
			this.after = this.after + "," + state.after;
		}
		if (state.trigger != null) {
			this.trigger = state.trigger + "," + this.trigger;
		}

	}

	@Override
	public EventNode clone() {
		EventNode result = new EventNode();
		result.setName(this.getName());
		result.setDescription(this.getDescription());
		result.setSrcState(this.getSrcState());
		result.setDstState(this.getDstState());
		result.setBefore(this.getBefore());
		result.setAfter(this.getAfter());
		result.setTrigger(this.getTrigger());
		return result;
	}

	/**
	 * 拷贝Handle信息
	 * 
	 * @param state
	 * @return void
	 * @throws
	 */
	public void extend(EventNode event) {
		if (event.before != null) {
			this.before = this.before + "," + event.before;
		}
		if (event.after != null) {
			this.after = event.after + "," + this.after;
		}
		if (event.trigger != null) {
			this.trigger = this.trigger + "," + event.trigger;
		}
	}
}

//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.06.20 时间 06:32:57 PM CST
//


package com.stosz.fsm.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the generated package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName	_FSMStatesStateChecker_QNAME	= new QName("", "Checker");
	private final static QName	_FSMStatesStateEvent_QNAME		= new QName("", "Event");

	/**
     * Create a new ObjectFactory that can be used to save new instances of schema derived classes
     * for package: generated
     *
     */
    public ObjectFactory() {
    }

	/**
	 * Create an instance of {@link FsmRootNode }
	 * 
	 */
	public FsmRootNode createFSM() {
		return new FsmRootNode();
	}

	/**
	 * Create an instance of {@link FsmRootNode.StateGroups }
	 * 
	 */
	public FsmRootNode.StateGroups createFSMStateGroups() {
		return new FsmRootNode.StateGroups();
	}

	/**
	 * Create an instance of {@link FsmRootNode.StateGroups.StateGroupNode }
	 * 
	 */
	public StateGroupNode createFSMStateGroupsStateGroup() {
		return new StateGroupNode();
	}

	/**
	 * Create an instance of {@link FsmRootNode.States }
	 * 
	 */
	public FsmRootNode.States createFSMStates() {
		return new FsmRootNode.States();
	}

	/**
	 * Create an instance of {@link FsmRootNode.States.StateNode }
	 * 
	 */
	public StateNode createFSMStatesState() {
		return new StateNode();
	}

	/**
	 * Create an instance of {@link FsmRootNode.StateGroups.EventNode }
	 * 
	 */
	public EventNode createFSMStateGroupsStateGroupEvent() {
		return new EventNode();
	}

	/**
	 * Create an instance of {@link EventNode }
	 * 
	 */
	public EventNode createFSMStatesStateEvent() {
		return new EventNode();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "Checker", scope = StateNode.class)
	public JAXBElement<Object> createFSMStatesStateChecker(Object value) {
		return new JAXBElement<Object>(_FSMStatesStateChecker_QNAME, Object.class, StateNode.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link EventNode }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "", name = "Event", scope = StateNode.class)
	public JAXBElement<EventNode> createFSMStatesStateEvent(EventNode value) {
		return new JAXBElement<EventNode>(_FSMStatesStateEvent_QNAME, EventNode.class, StateNode.class, value);
	}

}

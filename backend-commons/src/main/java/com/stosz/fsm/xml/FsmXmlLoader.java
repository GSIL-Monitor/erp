package com.stosz.fsm.xml;

import com.stosz.fsm.FsmStateContext;
import com.stosz.fsm.exception.FsmException;
import com.stosz.fsm.model.EventModel;
import com.stosz.fsm.model.FsmTemplateModel;
import com.stosz.plat.utils.FileUtils;
import com.stosz.plat.utils.ResourcesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.net.URL;
import java.util.*;

/**
 * FsmXmlService
 *
 * @ClassName FsmXmlService
 * @author shrek
 * @version 1.0
 */
public class FsmXmlLoader {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * main test.py
	 *
	 * @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) throws JAXBException {

		FsmStateContext fsc = new FsmStateContext();
		FsmXmlLoader loader = new FsmXmlLoader();

		loader.loadFsmStateContent(fsc);
	}

	public void loadFsmStateContent(FsmStateContext fsc) {
		loadFsmStateContent(fsc,"fsm");
	}

	/**
	 * loadFsmStateContent 加载FsmStateContent, 从*Fsm.xml状态机配置文件
	 *
	 * @param fsc
	 * @return void
	 * @throws
	 */
	public void loadFsmStateContent(FsmStateContext fsc , String resourcePath) {
		try {
			if (resourcePath == null) {
				resourcePath = "fsm";
			}
			// 读取Resource
			URL url = ResourcesUtils.getResource(resourcePath);
			if (url == null) {
				logger.warn("状态机配置读取失败, 读取位置:src/main/resources/{}, 如果不使用状态机请忽略",resourcePath);
				return;
			}
			String pathName = url.getFile();
			
			logger.info("状态机读取配置目录 {}", pathName);
			File rootPath = new File(pathName);
			if (pathName == null || rootPath == null) {
				String msg = String.format("状态机读取配置目录classpath:fsm失败, 请确认该目录是否存在");
				logger.warn(msg);
				return;
			}

			if (rootPath.isFile()) {
				loadFile(rootPath, fsc);
			}else {
				// 遍历配置目录
				File[] files = rootPath.listFiles();
				for (File file : files) {
					loadFile(rootPath, fsc);
				}
			}

		} catch (Exception e) {
			logger.error("加载FsmStateContent失败", e);
			throw new FsmException("加载FsmStateContent失败, " + e.getMessage());
		}
	}

	private void loadFile(File file,FsmStateContext fsc){
		String fileName = file.getAbsolutePath();
		if (fileName.endsWith(".xsd") == true) {
			// 忽略*.xsd文件
			return;
		}
		if (fileName.endsWith(".xml") == false) {
			logger.warn("状态机忽略该文件, 配置目录classpath:fsm下 : {}", fileName);
		}

		logger.info("状态机读取配置文件, {}", fileName);
		String xml = FileUtils.readFile(fileName);

		// XML转换为FsmRootNode
		FsmRootNode root = convert(xml);

		// check数据, 失败抛异常
		check(root);

		// 获取Event节点的Map
		Map<String, EventNode> eventMap = getEventMap(root);

		// 装配状态机Template
		FsmTemplateModel fsmTemplate = new FsmTemplateModel(root.getEntityName(), root.getParentEntityName());
		for (EventNode event : eventMap.values()) {
			// event
			EventModel model = new EventModel(root.getEntityName(), event);
			fsmTemplate.addEvent(model);
		}

		// 获取StateGroup节点的subState Map<StateGroup, List<State>>
		Map<String, List<String>> groupStateMapping = getSubStateNameMap(root);
		fsmTemplate.setGroupStateMapping(groupStateMapping);

		// 输出log
		fsmTemplate.printLog();

		fsc.addFsmTemplate(fsmTemplate);
	}

	/**
	 * 获取Event节点的Map Map<StateName_EventName, EventNode>
	 *
	 * @param root
	 * @return Map<String,EventNode>
	 * @throws
	 */
	private Map<String, EventNode> getEventMap(FsmRootNode root) {
		try {
			Map<String, StateNode> statesMap = getStatesMap(root);
			Map<String, StateGroupNode> stateGroupMap = getStateGroupMap(root);
			
			Map<String, List<String>> subStateNameMap = getSubStateNameMap(root);
			
			logger.info("StateGroup的states数据");
			for (String stateGroupName : subStateNameMap.keySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append("StateGroup[").append(stateGroupName).append("] contain states : ");
				for (String subState : subStateNameMap.get(stateGroupName)) {
					sb.append(subState + ", ");
				}
				logger.info(sb.toString());
			}
			
			// 使StateGroup节点的handle信息 复制给 State节点
			for (String groupName : stateGroupMap.keySet()) {
				StateGroupNode group = stateGroupMap.get(groupName);
				List<String> stateNameList = subStateNameMap.get(groupName);
				for (String stateName : stateNameList) {
					StateNode state = statesMap.get(stateName);
					state.extend(group);
				}
			}
			
			Map<String, EventNode> result = new LinkedHashMap<String, EventNode>();
			// 使State节点的handle信息 复制给 Event节点
			for (String stateName : statesMap.keySet()) {
				StateNode state = statesMap.get(stateName);
				List<EventNode> eventList = state.getEvent();
				for (EventNode event : eventList) {
					
					event.setSrcState(stateName);
					
					if (!statesMap.containsKey(event.getDstState())) {
						// DstState未定义
						String msg = String.format("状态机[%s] Event[%s]节点的DstState[%s]未配置, 请检查配置文件", root.getEntityName(), event.getName(), event.getDstState());
						logger.error(msg);
						throw new FsmException(msg);
					}
					
					String key = stateName + "_" + event.getName();
					if (result.containsKey(key)) {
						EventNode buf = result.get(key);
						if (!event.getDstState().equals(buf.getDstState())) {
							// SrcState + Event 有且仅有一个 DstState
							String msg =
									String.format("状态机[%s] State[%s]节点的Event[%s]节点存在多个不同的DstState[%s]与[%s], 请检查配置文件", root.getEntityName(), stateName, event.getName(), event.getDstState(), buf.getDstState());
							logger.error(msg);
							throw new FsmException(msg);
						}
						buf.extend(event);
					} else {
//						event.extend(state);
//						result.put(key, event);
					}
					String dstStateName = event.getDstState();
					StateNode dstState = statesMap.get(dstStateName);
					event.extend(dstState);
					result.put(key, event);
					
				}
			}
			
			return result;
		} catch (Exception e) {
			logger.error("获取Event节点的Map失败" + root.getEntityName(), e);
			throw new FsmException("获取Event节点的Map失败, " + e.getMessage());
		}
	}

	/**
	 * XML转换为FsmRootNode
	 *
	 * @param xml
	 * @return FsmRootNode
	 * @throws
	 */
	private FsmRootNode convert(String xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(FsmRootNode.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			FsmRootNode root = (FsmRootNode) unmarshaller.unmarshal(reader);

			return root;
		} catch (Exception e) {
			logger.error("XML转换为FsmRootNode, xml=" + xml, e);
			throw new FsmException("XML转换为FsmRootNode失败, " + e.getMessage());
		}
	}

	/**
	 * check
	 *
	 * @param root
	 * @return boolean
	 * @throws
	 */
	public boolean check(FsmRootNode root) {
		try {
			// ######


			// check xml的文件名 等于 root节点的EntityName属性 #######
			// Name属性为空
			// 为end节点, 无Event
			//


			return true;
		} catch (Exception e) {
			logger.error("check fsm fail, root=" + root, e);
			throw new FsmException("check fsm fail, " + e.getMessage());
		}
	}


	/**
	 * 获取State节点的Map Map<StateName, StateNode>
	 *
	 * @param root
	 * @return Map<String,StateNode>
	 * @throws
	 */
	private Map<String, StateNode> getStatesMap(FsmRootNode root) {
		try {
			// iterate state
			Map<String, StateNode> stateMap = new LinkedHashMap<String, StateNode>();
			List<StateNode> stateList = root.getStates().getState();
			for (StateNode state : stateList) {
				stateMap.put(state.getName(), state);
			}

			return stateMap;
		} catch (Exception e) {
			logger.error("获取State的Map, root=" + root, e);
			throw new FsmException("获取State的Map, " + e.getMessage());
		}
	}


	/**
	 * 获取StateGroup节点的Map Map<StateGroupName, StateGroupsNode>
	 *
	 * @param root
	 * @return Map<String,StateNode>
	 * @throws
	 */
	private Map<String, StateGroupNode> getStateGroupMap(FsmRootNode root) {
		try {
			// iterate StateGroup
			Map<String, StateGroupNode> stateGroupMap = new LinkedHashMap<String, StateGroupNode>();
			List<StateGroupNode> stateGroupList = root.getStateGroups().getStateGroup();
			for (StateGroupNode stateGroup : stateGroupList) {
				stateGroupMap.put(stateGroup.getName(), stateGroup);
			}

			return stateGroupMap;
		} catch (Exception e) {
			logger.error("获取stateGroup的Map, root=" + root, e);
			throw new FsmException("获取stateGroup的Map, " + e.getMessage());
		}
	}

	/**
	 * 获取StateGroup节点的subState Map<StateGroup, List<State>>
	 *
	 * @param root
	 * @return Map<String,List<String>>
	 * @throws
	 */
	private Map<String, List<String>> getSubStateNameMap(FsmRootNode root) {

		try {
			Map<String, StateGroupNode> map = new LinkedHashMap<String, StateGroupNode>();

			List<StateGroupNode> stateGroupList = root.getStateGroups().getStateGroup();
			List<StateNode> stateList = root.getStates().getState();

			// 读取state
			for (StateNode state : stateList) {
				map.put(state.getName(), state);
			}
			// 读取stateGroup
			for (StateGroupNode StateGroup : stateGroupList) {
				map.put(StateGroup.getName(), StateGroup);
			}

			// result
			Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();

			for (StateGroupNode stateGroup : stateGroupList) {

				if (stateGroup.getStates().size() <= 0) {
					// stateGroup是否包含sub state
					String msg = String.format("StateGroups[%s]的States节点不可以为空, 请检查配置文件", stateGroup.getName());
					logger.error(msg);
					throw new FsmException(msg);
				}

				Set<String> visitPath = new HashSet<String>();
				List<StateGroupNode> list = getStateGroupList(stateGroup, map, visitPath);

				List<String> subStateNameList = new ArrayList<String>();
				for (StateGroupNode subState : list) {
					subStateNameList.add(subState.getName());
				}
				result.put(stateGroup.getName(), subStateNameList);
			}

			return result;
		} catch (Exception e) {
			logger.error("获取StateGroup的subState err, root=" + root, e);
			throw new FsmException("获取StateGroup的subState err, " + e.getMessage());
		}
	}

	/**
	 * 递归方法 获取StateGroup节点的subState
	 *
	 * @param stateGroup stateGroup节点
	 * @param stateGroupMap 全部state与stateGroup节点的Map
	 * @param visitPath 访问路径, 记录递归调用的stateGroup名, 防止无限循环引用
	 * @return List<StateGroupNode>
	 * @throws
	 */
	private List<StateGroupNode> getStateGroupList(StateGroupNode stateGroup, Map<String, StateGroupNode> stateGroupMap, Set<String> visitPath) {

		List<StateGroupNode> result = new ArrayList<StateGroupNode>();

		List<String> subStateList = stateGroup.getStates();
		if (subStateList == null || subStateList.size() <= 0) {
			// state type
			result.add(stateGroup);
			return result;
		} else {

			if (visitPath.contains(stateGroup.getName())) {
				// 循环引用, 可以考虑抛异常 或 continue忽略
				String msg = String.format("StateGroups的States节点存在无限循环引用, StateGroups[%s], 请检查配置文件, 如果忽略该错误可返回一个空List", stateGroup.getName());
				logger.error(msg);
				throw new FsmException(msg);
			}

			// state group type
			for (String subStateName : subStateList) {
				StateGroupNode subStateGroup = stateGroupMap.get(subStateName);
				if (subStateGroup == null) {
					// stateGroupMap是否存在
					String msg = String.format("StateGroups[%s]的States节点, 未识别的状态名%s, 请检查配置文件", stateGroup.getName(), subStateName);
					logger.error(msg);
					throw new FsmException(msg);
				}

				List<StateGroupNode> buf = getStateGroupList(subStateGroup, stateGroupMap, visitPath);
				result.addAll(buf);
				visitPath.add(stateGroup.getName());
			}
		}

		return result;
	}



}

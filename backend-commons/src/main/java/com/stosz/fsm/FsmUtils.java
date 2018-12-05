package com.stosz.fsm;

/**
 * 状态机工具类
 *
 * @ClassName FsmUtils
 * @author shrek
 * @version 1.0
 */
public abstract class FsmUtils {

	/**
	 * 获取状态机类型名
	 *
	 * instance
	 * @return String	
	 * @throws
	 */
	public static String getFsmName(IFsmInstance fsmInstance) {
		
		String fsmName = fsmInstance.getClass().getSimpleName();
		if (fsmName.contains("$")) {
			// Spring的代理类, 按如下逻辑
			Class<?> superClass = fsmInstance.getClass().getSuperclass();
			if (superClass != null && fsmName.startsWith(superClass.getSimpleName())) {
				return superClass.getSimpleName();
			}
		}
		
		// 一般逻辑
		return fsmName;
	}
}

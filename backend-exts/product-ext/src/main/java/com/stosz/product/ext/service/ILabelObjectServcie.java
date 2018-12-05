package com.stosz.product.ext.service;

import java.util.List;
import java.util.Map;

import com.stosz.plat.enums.LabelTypeEnum;
import com.stosz.product.ext.model.Label;
import com.stosz.product.ext.model.LabelObject;

/**
 * 标签service
 * @author feiheping
 * @version [1.0,2017年12月19日]
 */
public interface ILabelObjectServcie {

	String url = "/product/remote/ILabelObjectServcie";

	/**
	 * 新增标签
	 * @param LabelObjects
	 */
	public void addLabel(List<LabelObject> LabelObjects);

	/**
	 * 获取对象的所有标签
	 * @param objectId 对象ID
	 * @param objectType 对象的类型
	 * @return
	 */
	public List<LabelObject> queryLabelObject(Integer objectId, LabelTypeEnum laTypeEnum);

	/**
	 * 获取到一组对象的标签
	 * @param objectIds 对象Ids
	 * @param laTypeEnum 标签类型
	 * @return
	 */
	public Map<Integer, List<LabelObject>> queryListLabelObject(List<Integer> objectIds, LabelTypeEnum laTypeEnum);

	/**
	 * 获取对象的所有标签,带出标签的name label属性
	 * @param objectId 对象ID
	 * @param objectType 对象的类型
	 * @return
	 */
	public List<LabelObject> queryLabelObjectAttr(Integer objectId, String objectType);

	/**
	 * 删除对象的所有标签
	 * @param objectId 对象ID
	 * @param objectType 对象类型
	 * @return
	 */
	public int deleteLabel(Integer objectId, String objectType);

	/**
	 * 获取到父键下的所有标签  目前只支持检索出一层,可以递归
	 * @param parentKeyword
	 * @return
	 */
	public List<Label> queryLabelByKey(String parentKeyword);
}

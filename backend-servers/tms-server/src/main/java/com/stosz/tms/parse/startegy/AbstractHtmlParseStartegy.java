package com.stosz.tms.parse.startegy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

public abstract class AbstractHtmlParseStartegy extends AbstractParseStartegy {

	/**
	 * 如果元素是table
	 * 可以匹配到表头的索引位，然后根据索引获取行数据
	 * @param elements
	 * @param params
	 * @return
	 */
	protected int[] matchElementIndex(Elements elements, String... params) {
		Assert.isTrue(params.length > 0, "查找的元素不能为空");
		int[] seachIndexs = new int[params.length];
		HashMap<String, Integer> indexMap = new HashMap<>();
		for (int i = 0; i < elements.size(); i++) {
			String elementName = elements.get(i).text();
			indexMap.put(elementName.toLowerCase(), i);
		}
		for (int i = 0; i < params.length; i++) {
			String param = params[i].toLowerCase();
			if (indexMap.get(param) != null)
				seachIndexs[i] = indexMap.get(param);
			else
				seachIndexs[i] = -1;
		}
		return seachIndexs;
	}

	/**
	 * 获取到table的数据
	 * @param tableElement
	 * @param discardCount  跳过的列，针对table里面有跨列的数据
	 * @param headIndex  表头索引位置
	 * @return
	 */
	protected List<LinkedHashMap<String, String>> getTableData(Element tableElement, int discardCount, int headIndex) {
		List<LinkedHashMap<String, String>> linkedHashMaps = new ArrayList<>();
		Elements trackElements = tableElement.getElementsByTag("tr");
		if (trackElements.size() > 2) {
			Elements headElements = trackElements.get(headIndex).getElementsByTag("td");
			Map<Integer, String> nameAlias = new HashMap<>();
			for (int i = discardCount; i < headElements.size(); i++) {
				String headName = headElements.get(i).text().trim().toLowerCase();
				nameAlias.put(i - discardCount, headName);
			}
			for (int x = headIndex + 1; x < trackElements.size(); x++) {
				Elements dataElements = trackElements.get(x).getElementsByTag("td");
				LinkedHashMap<String, String> itemMap = new LinkedHashMap<>();
				for (int t = 0; t < dataElements.size(); t++) {
					if (nameAlias.size() != dataElements.size() && t < discardCount) {
						continue;
					}
					String value = dataElements.get(t).text();
					if (nameAlias.size() != dataElements.size()) {
						String headName = nameAlias.get(t - discardCount);
						itemMap.put(headName, value);
					} else {
						String headName = nameAlias.get(t);
						itemMap.put(headName, value);
					}

				}
				linkedHashMaps.add(itemMap);
			}
		}
		return linkedHashMaps;
	}

	/**
	 * 获取Table元素的数据，
	 * @param tableElement
	 * @param headIndex 表头索引位置
	 * @return
	 */
	protected List<LinkedHashMap<String, String>> getTableData(Element tableElement, int headIndex) {
		return this.getTableData(tableElement, 0, headIndex);
	}
}

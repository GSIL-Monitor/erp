package com.stosz.plat.engine;

import com.stosz.plat.common.ServletRequestWraper;
import com.stosz.plat.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

/**
 * pageIndex  当前页
 * pageSize   每页显示条数
 * @author feiheping
 * @version [1.0,2017年12月4日]
 */
public class PagingFilter implements Filter {

	private static final String pageIndexName = "page";

	private static final String pageSizeName = "limit";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Set<String> nameSet = request.getParameterMap().keySet();
		if (!nameSet.contains(pageIndexName) || !nameSet.contains(pageSizeName)) {
			chain.doFilter(request, response);
			return;
		}
		String pageIndex = request.getParameter(pageIndexName);
		String pageSize = request.getParameter(pageSizeName);

		int pageIndexValue = StringUtil.strToInt(pageIndex, 1);
		int pageSizeValue = StringUtil.strToInt(pageSize, 1);
		pageIndexValue = pageIndexValue <= 0 ? 1 : pageIndexValue;

		ServletRequestWraper requestWraper = new ServletRequestWraper((HttpServletRequest) request);
		requestWraper.addParameter("start", String.valueOf((pageIndexValue - 1) * pageSizeValue));
		requestWraper.addParameter("limit", String.valueOf(pageSizeValue));
		chain.doFilter(requestWraper, response);

	}

	@Override
	public void destroy() {

	}

}

package com.jinmengzhu.groupdining.util;

import javax.servlet.http.HttpServletRequest;

import com.jinmengzhu.groupdining.domain.PagerModel;

public class PageUtil {

	@SuppressWarnings("rawtypes")
	public static PagerModel getPage(HttpServletRequest request) {	
		String pageSizeStr = (String)request.getParameter("pageSize");
		String pageIndexStr = (String)request.getParameter("pageIndex");
		int pageSize = (pageSizeStr == null ? 10 : Integer.parseInt(pageSizeStr));
		int pageIndex = (pageIndexStr == null ? 0 : Integer.parseInt(pageIndexStr));
		PagerModel pageInfo = new PagerModel();
		pageInfo.setPageSize(pageSize);
		pageInfo.setPageNumber(pageIndex);
		return pageInfo;
	}

}

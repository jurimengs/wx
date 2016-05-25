package com.org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.annotations.Init;
import com.org.common.Pager;
import com.org.interfaces.controller.CommonController;
import com.org.services.busi.ChannelService;
import com.org.servlet.SmpHttpServlet;
import com.org.utils.BeanUtils;

@Init
public class DreamsController extends SmpHttpServlet implements CommonController{
	private static final long serialVersionUID = 8632438856419845609L;

	public void wish(HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
		log.info("wish。。。" );
		//HttpSession session = request.getSession(true);
		ChannelService channelService = (ChannelService) BeanUtils.getBean("channelService");

		Pager pager = new Pager();
		String currentPage = request.getParameter("currentPage");
		if(StringUtils.isEmpty(currentPage)) {
			currentPage = "1";
		}
		pager.setCurrentPage(Integer.valueOf(currentPage));
		
		JSONArray testimonialsArray = channelService.getTestimonialsByChannelId(null, pager);
		// 假设查询到的永远只有100条数据，每列分25条数据
		request.setAttribute("testimonialsArray", testimonialsArray);
		
		this.redirect("/index.jsp", response);
		return;
	}
	
	private Log log = LogFactory.getLog(DreamsController.class);

	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
}

package com.org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.annotations.Init;
import com.org.interfaces.controller.CommonController;
import com.org.services.busi.ChannelService;
import com.org.services.busi.CommemorateService;
import com.org.servlet.SmpHttpServlet;
import com.org.utils.BeanUtils;
import com.org.utils.PropertyUtil;

@Init
public class ToTopController extends SmpHttpServlet implements CommonController{
	private static final long serialVersionUID = 2156792239072761671L;

	public ToTopController(){
		
	}
	
	public void toTop(HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
		log.info("toTop。。。" );
		//HttpSession session = request.getSession();
		
		String topTimesGoal = PropertyUtil.getValue("business", "topTimesGoal");
		ChannelService channelService = (ChannelService) BeanUtils.getBean("channelService");
		String t_limit = "10";
		JSONArray testimonialsArray = channelService.getTestimonialsByChannelId(null, t_limit);
		
		// 纪念板的第一个
		CommemorateService commemorateService = (CommemorateService) BeanUtils.getBean("commemorateService");
		JSONArray commemorateArray = commemorateService.getCurrentCommemorate("1", topTimesGoal);
		if(commemorateArray.size() > 0){
			request.setAttribute("commemorate", commemorateArray.getJSONObject(0));
		}
		
		// 假设查询到的永远只有100条数据，每列分25条数据
		request.setAttribute("testimonialsArray", testimonialsArray);
		
		this.forward("/home.jsp", request, response);
		return;
	}
	
	private Log log = LogFactory.getLog(ToTopController.class);

	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) {
		
	}
}

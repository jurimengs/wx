package com.org.controller.wx;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.annotations.Init;
import com.org.common.CommonConstant;
import com.org.interfaces.controller.CommonController;
import com.org.interfaces.rute.Business;
import com.org.rute.RuteAdapter;
import com.org.servlet.SmpHttpServlet;
import com.org.threads.RuteThreadPool;
import com.org.util.CT;
import com.org.utils.DateUtil;
import com.org.utils.XmlUtils;
import com.org.wx.utils.WxUtil;

@Init
public class WxController extends SmpHttpServlet implements CommonController{
	private static final long serialVersionUID = 2156792239072761671L;
	private Log log = LogFactory.getLog(WxController.class);

	public WxController(){
	}
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("token: " + this.getParamMap(request));
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		boolean signResult = WxUtil.checkSignature(signature, timestamp, nonce);
		if(!signResult) {
			log.info("验签错误");
			return;
		}
		String echostr = request.getParameter("echostr");
		if(StringUtils.isNotEmpty(echostr)) {
			// 表示是首次验签
			this.write(echostr, CT.ENCODE_UTF8, response);
			return;
		}
		
		JSONObject xmlJson = XmlUtils.getDocumentFromRequest(request);
		log.info("收到微信服务器的消息：xmlJson=====> " + xmlJson);
		
		String result = dealBusiness(xmlJson);
		this.write(result, CommonConstant.UTF8, response);
		return;
	}
	
	/**
	 * 本地测试。
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void rutetest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO TEst
		JSONObject xmlJson = JSONObject.fromObject("{\"ToUserName\":\"gh_b4c1774a1ef7\",\"FromUserName\":\"osp6swrNZiWtEuTy-Gj1cBVA1l38\",\"CreateTime\":\"1458533297\",\"MsgType\":\"text\",\"Content\":\"。\",\"MsgId\":\"6264352811146407227\"}");
		log.info("收到微信服务器的消息：xmlJson=====> " + xmlJson);
		
		String result = dealBusiness(xmlJson);
		this.write(result, CommonConstant.UTF8, response);
		return;
	}
	
	private String dealBusiness(JSONObject xmlJson){
		Business<String> event = RuteAdapter.adapter(xmlJson);
		String dateStr = DateUtil.getyyyyMMddHHmmss();
		try {
			Future<String> result = RuteThreadPool.submit(event);
			if(result != null) {
				return result.get(4, TimeUnit.SECONDS);// 4秒后超时
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.info(dateStr + "--> dealBusiness InterruptedException: " + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			log.info(dateStr + "--> dealBusiness ExecutionException: " + e.getMessage());
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return WxUtil.replyStr("信息处理超时，请联系管理员，错误时间：" + dateStr, xmlJson);
		}
		return WxUtil.replyStr("系统出现异常，请联系管理员，错误时间：" + dateStr, xmlJson);
	}
	
	public void initMenu(HttpServletRequest request, HttpServletResponse response) {
		String resMsg = WxUtil.createMenu();
		log.info("initMenu ====> " + resMsg);
		this.write(resMsg, CommonConstant.UTF8, response);
		return;
	}
	
/*
	// TODO 测试用的
	public void getCacheToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = WxUtil.getToken();
		String timestamp = String.valueOf(StringUtil.getTimestamp()); // 必填，生成签名的时间戳
		String nonceStr = UUID.randomUUID().toString(); // 必填，签名，见附录1
		String url = request.getRequestURL().toString();
		String signature = WxUtil.localSign(timestamp, nonceStr, url); // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("nonceStr", nonceStr);
		request.setAttribute("url", url);
		request.setAttribute("signature", signature);
		request.setAttribute("cacheToken", token);
		this.forward("/www/html/wxtest.jsp", request, response);
		return;
	}
	
	public void addPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String iconUrl ="http://mmbiz.qpic.cn/mmbiz/7SgQeGs3O4Bcv3hZtP6gf6NtuWJxcDwvNtO57VN5IqPPlyTnkHr3ic18tYDL9yp7Fefh7PNgAfQ8RXUxgAK5PCA/0?wx_fmt=jpeg";
		iconUrl = URLEncoder.encode(iconUrl, "UTF-8");
		String title ="主标题";
		String description ="副标题";
		String pageUrl ="http://www.rsbk.cc/mall/shake.jsp";
		String comment ="";
		JSONObject param = new JSONObject();
		param.put("icon_url", iconUrl);
		param.put("comment", comment);
		param.put("page_url", pageUrl);
		param.put("description", description);
		param.put("title", title);

		String url = "https://api.weixin.qq.com/shakearound/page/add?access_token=".concat(WxUtil.getToken());
		HttpTool http = new HttpUtil();
		JSONObject res = http.wxHttpsPost(param, url);
		log.info("添加摇一摇页面：" + res.toString());
		this.write(res, CommonConstant.UTF8, response);
		return;
	}
	
	
	public void getMertialsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject param = new JSONObject();
		param.put("type", "image"); // 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news
		param.put("offset", "0"); // 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
		param.put("count", "20"); // 返回素材的数量，取值在1到20之间

		String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=".concat(WxUtil.getToken());
		HttpTool http = new HttpUtil();
		JSONObject res = http.wxHttpsPost(param, url);
		log.info("getMertialsList：" + res.toString());
		this.write(res, CommonConstant.UTF8, response);
		return;
	}
	
	public void deleteBottomMenu(HttpServletRequest request, HttpServletResponse response) {
		WxUtil.deleteBottomMenu();
		//this.forward("/www/html/wxtest.jsp", request, response);
		return;
	}
	
	*//**
	 * 申请开通摇一摇
	 * @param request
	 * @param response
	 *//*
	public void applicationYao(HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject param = new JSONObject();
		param.put("name", "jurimengs");
		param.put("phone_number", "17749753878");
		param.put("email", "17749753878@qq.com");
		param.put("industry_id", "1204"); // 机构组织     其他组织     不需要资质文件 
		param.put("qualification_cert_urls", new JSONArray()); // 机构组织     其他组织     不需要资质文件 
		param.put("apply_reason", "test"); // 机构组织     其他组织     不需要资质文件 
		
		String url = "https://api.weixin.qq.com/shakearound/account/register?access_token=".concat(WxUtil.getToken());
		HttpTool http = new HttpUtil();
		JSONObject res = http.wxHttpsPost(param, url);
		log.info("申请开通摇一摇：" + res.toString());
		return;
	}
	
	*//**
	 * 申请开通摇一摇 查询审核状态
	 * @param request
	 * @param response
	 *//*
	public void applicationYaoResult(HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject param = new JSONObject();
		param.put("name", "jurimengs");
		param.put("phone_number", "17749753878");
		param.put("email", "17749753878@qq.com");
		param.put("industry_id", "1204"); // 机构组织     其他组织     不需要资质文件 
		param.put("qualification_cert_urls", new JSONArray()); // 机构组织     其他组织     不需要资质文件 
		param.put("apply_reason", "test"); // 机构组织     其他组织     不需要资质文件 
		
		String url = "https://api.weixin.qq.com/shakearound/account/auditstatus?access_token=".concat(WxUtil.getToken());
		HttpTool http = new HttpUtil();
		JSONObject res = http.wxHttpsGet(null, url);
		res.put("backup", "审核状态。0：审核未通过、1：审核中、2：审核已通过；审核会在三个工作日内完成");
		log.info("查询审核状态：" + res.toString());
		// 0：审核未通过、1：审核中、2：审核已通过；审核会在三个工作日内完成
		try {
			this.write(res, CommonConstant.UTF8, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	*/
	public void post(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
}

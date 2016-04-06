package com.org.wx.utils;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.caches.Memcache;
import com.org.common.CommonConstant;
import com.org.queues.MessageSendTask;
import com.org.queues.QueueContainer;
import com.org.threads.ＭessageThreadPool;
import com.org.threads.business.SendMessageThread;
import com.org.utils.HttpTool;
import com.org.utils.HttpUtil;
import com.org.utils.PropertyUtil;

/**
 * 客服消息管理
 * 总管消息封装、消息发送
 * service message send manager interface 
 * @author Administrator
 *
 */
public class MessageUtil {
	private static String url = PropertyUtil.getValue("wx", "wx_send_message_by_service");
	private static Log log = LogFactory.getLog(MessageUtil.class);
	protected MessageUtil(){}

	/**
	 * 发送消息给指定用户
	 * @param content 最原始的文本
	 * @param openid 
	 * @return
	 */
	public static JSONObject sendToOne(String content, String openid){
		// paramContent json体，包含了用户openid、content的
		JSONObject paramContent = getTextMessageJson(content);
		paramContent.put("touser", openid);

		// 调用客服接口发送消息 
		String urlTemp = url.concat(Memcache.getInstance().getValue(CommonConstant.WX_TOKEN_KEY));
		HttpTool http = new HttpUtil();
		JSONObject returns = http.wxHttpsPost(paramContent, urlTemp);
		log.info("pushMessage returns====> " + returns);

		return returns;
	}

	/**
	 * 通过队列发送
	 * @param content
	 * @param openidList
	 */
	public static void sendToMultiByQueue(String content, List<String> openidList){
		JSONObject paramContent = getTextMessageJson(content);
		for (int i = 0; i < openidList.size(); i++) {
			if(StringUtils.isNotEmpty(openidList.get(i))){
				QueueContainer.addTask(new MessageSendTask(paramContent, openidList.get(i)));
			}
		}
	}

	/**
	 * 通过线程池发送
	 * @param content
	 * @param openidList
	 */
	public static void sendToMultiByThreadPool(String content, List<String> openidList){
		JSONObject paramContent = getTextMessageJson(content);
		ＭessageThreadPool.execute(new SendMessageThread(paramContent, openidList));
	}
	
	/**
	 * 编辑文本消息模板
	 * @param content
	 * @return
	 */
	public static JSONObject getTextMessageJson(String content){
		JSONObject contentTemp = new JSONObject();
		contentTemp.put("content", content);
		
		JSONObject paramContent = new JSONObject();
		paramContent.put("msgtype", "text");
		paramContent.put("text", contentTemp);
		return paramContent;
	}
	
	/**
	 * 编辑图片消息模板
	 * @param mediaId 
	 * @return
	 */
	public static JSONObject getImageMessageJson(String mediaId){
		JSONObject imageTemp = new JSONObject();
		imageTemp.put("media_id", mediaId);
		
		JSONObject paramContent = new JSONObject();
		paramContent.put("msgtype", "image");
		paramContent.put("image", imageTemp);
		
		return paramContent;
	}
	
	/**
	 * 编辑图文消息模板
	 * 只能发送10条（什么意思？每个人只能发送10条？）
	 * @param articles : 
	 * "articles": [
		         {
		             "title":"Happy Day",
		             "description":"Is Really A Happy Day",
		             "url":"URL",
		             "picurl":"PIC_URL"
		         },
		         {
		             "title":"Happy Day",
		             "description":"Is Really A Happy Day",
		             "url":"URL",
		             "picurl":"PIC_URL"
		         }
	    ]
	 * @return
	 */
	public static JSONObject getNewsMessageJson(JSONArray articles){
		/*{
		    "msgtype":"news",
		    "news":{
		        "articles": [
		         {
		             "title":"Happy Day",
		             "description":"Is Really A Happy Day",
		             "url":"URL",
		             "picurl":"PIC_URL"
		         },
		         {
		             "title":"Happy Day",
		             "description":"Is Really A Happy Day",
		             "url":"URL",
		             "picurl":"PIC_URL"
		         }
		         ]
		    }
		}*/
		
		JSONObject news = new JSONObject();
		news.put("articles", articles);
		
		JSONObject paramContent = new JSONObject();
		paramContent.put("msgtype", "news");
		paramContent.put("news", news);
		return paramContent;
	}
}

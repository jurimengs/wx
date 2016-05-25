package com.org.services.busi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.org.annotations.Init;
import com.org.common.CommonConstant;
import com.org.common.Pager;
import com.org.container.CommonContainer;
import com.org.dao.CommonDao;
import com.org.utils.BeanUtils;

/**
 * 通用
 * @author Administrator
 *
 */
@Init
public class ChannelService {
	private final static List<String> secretColumn = new ArrayList<String>();
	// 查询置顶的
	private String getTestimonialsByChannelId_from_to = "";
	
	static {
		secretColumn.add("id");
	}
	
	
	public JSONArray getTestimonialsByChannelId(String channelId, Pager pager){
		
		Integer pageCount = pager.getPageCount();
		Integer start = (pager.getCurrentPage() - 1) * pageCount;
		
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		Map<Integer , Object> params = new HashMap<Integer, Object>();
		Map<Integer , Object> countparams = new HashMap<Integer, Object>();
		String getTestimonialsByChannelId_limit ;
		String countsql ;
		
		if(StringUtils.isEmpty(channelId)){
			params = new HashMap<Integer, Object>();
			params.put(1, start);
			params.put(2, pageCount);
			getTestimonialsByChannelId_limit = "select (select count(1) from kol_comment c where c.testimonials_id = a.id) comment_counts, a.*, b.file_path from kol_testimonials a left join kol_testimonials_files b on a.file_id=b.id where a.is_top is null order by a.id desc limit ?, ?";
			
			countparams = new HashMap<Integer, Object>();
			countsql = "select count(1) as totalCount from kol_testimonials where is_top is null";
		} else {
			params = new HashMap<Integer, Object>();
			params.put(1, channelId);
			params.put(2, start);
			params.put(3, pageCount);
			getTestimonialsByChannelId_limit = "select (select count(1) from kol_comment c where c.testimonials_id = a.id) comment_counts, a.*, b.file_path from kol_testimonials a left join kol_testimonials_files b on a.file_id=b.id where channel_id = ? and a.is_top is null order by a.id desc limit ?, ?";

			countparams = new HashMap<Integer, Object>();
			countparams.put(1, channelId);
			countsql = "select count(1) as totalCount from kol_testimonials where channel_id = ? and is_top is null";
			
		}
		JSONArray testimonials = commonDao.queryJSONArray(getTestimonialsByChannelId_limit, params, secretColumn);
		JSONArray topTestimonials = getTopedTestimonialsByChannelId(channelId);
		testimonials.addAll(0, topTestimonials);
		
		// 求总数
		Integer totalCount = commonDao.queryCount(countsql, countparams, "totalCount");
		pager.setTotalCount(totalCount);
		return testimonials;
	}
	
	/**
	 * 查置顶帖
	 * @param channelId
	 * @param limit
	 * @return
	 */
	public JSONArray getTopedTestimonialsByChannelId(String channelId){
		// 先看看内存中有没有存放数据
		Object dataTemp = CommonContainer.getData(CommonConstant.TOP_TESTIMONIAL+channelId);
		JSONArray testimonials = null;
		if(dataTemp != null){
			testimonials = (JSONArray)dataTemp;
			return testimonials;
		}
		String getTopedTestimonialsByChannelId_limit = "select (select count(1) from kol_comment c where c.testimonials_id = a.id) comment_counts, a.*, b.file_path from kol_testimonials a left join kol_testimonials_files b on a.file_id=b.id where channel_id = ? and a.is_top='0' order by a.id desc limit 3";
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		Map<Integer , Object> params = new HashMap<Integer, Object>();
		params.put(1, channelId);
		if(StringUtils.isEmpty(channelId)){
			params = new HashMap<Integer, Object>();
			getTopedTestimonialsByChannelId_limit = "select (select count(1) from kol_comment c where c.testimonials_id = a.id) comment_counts, a.*, b.file_path from kol_testimonials a left join kol_testimonials_files b on a.file_id=b.id where a.is_top='0' order by a.id desc limit 3";
		}
		testimonials = commonDao.queryJSONArray(getTopedTestimonialsByChannelId_limit, params, secretColumn);
		CommonContainer.saveData(CommonConstant.TOP_TESTIMONIAL+channelId, testimonials);
		return testimonials;
	}
	
	/**
	 * 分页查询 TODO
	 * @param channelId
	 * @param limitFrom
	 * @param limitTo
	 * @return
	 */
	public JSONArray getTestimonialsByChannelId(String channelId, String limitFrom, String limitTo){
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		Map<Integer , Object> params = new HashMap<Integer, Object>();
		params.put(1, channelId);
		JSONArray testimonials = commonDao.queryJSONArray(getTestimonialsByChannelId_from_to, params, secretColumn);
		return testimonials;
	}
}

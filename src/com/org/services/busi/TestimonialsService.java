package com.org.services.busi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.org.annotations.Init;
import com.org.dao.CommonDao;
import com.org.util.DateUtil;
import com.org.utils.BeanUtils;

/**
 * @author Administrator
 *
 */
@Init
public class TestimonialsService {
	private final String sql_insert = "insert into kol_testimonials (user_id, contents, create_date, update_date, channel_id, title, file_id) values (?,?,?,?,?,?,?)";
	private final String sql_getById = "select a.*, b.file_path from kol_testimonials a left join kol_testimonials_files b on a.file_id=b.id where a.id = ?";
	private final String sql_getByTopId = "select a.*, b.file_path from kol_testimonials_totop a left join kol_testimonials_files b on a.file_id=b.id where a.id = ?";
	private final static List<String> secretColumn = new ArrayList<String>();
	static {
		secretColumn.add("id");
	}
	
	public synchronized JSONObject saveContents(String userId, String contents, String channelId, String title, String fileId){
		String createDate = DateUtil.getDate(DateUtil.DATE_FORMAT_SHORT_DATE);
		
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		Map<Integer , Object> params = new HashMap<Integer, Object>();
		params.put(1, Integer.valueOf(userId));
		params.put(2, contents);
		params.put(3, createDate);
		params.put(4, createDate);
		if (StringUtils.isEmpty(channelId)) {
			channelId = "0";
		}
		params.put(5, Integer.valueOf(channelId));
		params.put(6, title);
		params.put(7, fileId);
		
		JSONObject res = new JSONObject();
		commonDao.addSingle(sql_insert, params);
		return res;
	}
	
	public JSONObject getTestimonialById(String id){
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		Map<Integer , Object> params = new HashMap<Integer, Object>();
		params.put(1, Integer.valueOf(id));
		JSONObject testimonial = new JSONObject();
		testimonial = commonDao.queryJSONObject(sql_getById, params, null);
		return testimonial;
	}
	
	public JSONObject getTopTestimonialById(String id){
		CommonDao commonDao = (CommonDao)BeanUtils.getBean("commonDao");;
		Map<Integer , Object> params = new HashMap<Integer, Object>();
		params.put(1, Integer.valueOf(id));
		JSONObject testimonial = new JSONObject();
		testimonial = commonDao.queryJSONObject(sql_getByTopId, params, secretColumn);
		return testimonial;
	}
}

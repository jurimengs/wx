package com.org.utils;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class DomainQuery{

	@Test
	public void doQueryAll() {
		List<String> list = new ArrayList<String>();
		list = new ArrayList<String>();
//		list.add("ttt-v");
//		list.add("tt-v");
//		list.add("tt-vv");
//		list.add("t-and-v");
//		list.add("t-vv");
//		list.add("ttvv");
		list.add("tvfns");
		list.add("fnstv");
		
//		list.add("arsher");
//		list.add("anquangou");
//		list.add("anquanchi");
//		list.add("anxingchi");
		for (int i = 0; i < list.size(); i++) {
			queryAim(list.get(i));
		}
	}

	public void queryAim(String aim) {
		HttpUtil u  = new HttpUtil();
		String[] types = {".com",".cc",".cn"};
		for (int i = 0; i < types.length; i++) {
			String url = "http://checkdomain.xinnet.com/domainCheck?callbackparam=&searchRandom=0&prefix="+aim+"&suffix=" +types[i];
			String res = u.httpGet(url, "utf-8");
			if(StringUtils.isNotEmpty(res)) {
				String temp = res.replaceAll("\\(", "").replaceAll("\\)", "");
				//System.out.println(temp);
				JSONArray arrTemp = JSONArray.fromObject(temp);
				try {
					JSONArray result = arrTemp.getJSONObject(0).getJSONArray("result");
					JSONObject jsonTemp = result.getJSONObject(0);
					JSONArray yesArray = jsonTemp.getJSONArray("yes");
					//JSONObject noArray = jsonTemp.getJSONObject("no");
					if(yesArray != null && !yesArray.isEmpty()) {
						JSONObject yes = yesArray.getJSONObject(0);
						if(yes != null && !yes.isEmpty()) {
							System.out.println("可注册："+aim+types[i]);
						} else {
							System.out.println("不可注册："+aim+types[i]);
						}
					} else {
						System.out.println("不可注册："+aim+types[i]);
					}
				} catch (Exception e) {
					System.out.println("不可注册："+aim+types[i] + " e: " +e.getMessage());
				}
			}
		}
	
	}
	
	public List<String> getAllAim() {
		String str = "abcdefghijklmnopqrstuvwxyz";
		char a[] = str.toCharArray(); 
		List<String> all = new ArrayList<String>(); 
		for (int i = 0; i < a.length; i++) {
			//System.out.println(a[i]);
			List<String> temp = createArray(a,i);
			all.addAll(temp);
		}
		return all;
	}
	
	public List<String> createArray(char a[], int c) {
		List<String> arr = new ArrayList<String>();
		for (int i = 0; i < a.length; i++) {
			if(i!=c){
				arr.add(a[c] +""+ a[i]);
				//System.out.println(a[c] +""+ a[i]);
			}
		}
		return arr;
	}
	
	
}

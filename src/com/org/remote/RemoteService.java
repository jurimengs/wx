package com.org.remote;

/**
 * ����Զ������
 * @author Administrator
 *
 */
public interface RemoteService {

	/**
	 * @param request json �ṹ������
	 * 
	 * @return response json �ṹ������
	 */
	public String access(String request);
	
}
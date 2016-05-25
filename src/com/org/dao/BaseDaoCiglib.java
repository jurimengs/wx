package com.org.dao;

import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.cglib.beans.BeanMap;

import com.org.util.StringUtil;
import com.org.utils.BeanUtils;
import com.org.utils.ByteUtil;
import com.org.utils.DesUtil;

public class BaseDaoCiglib {
	
	/**
	 * 获取connection，同时关闭自动提交
	 * @return
	 */
	protected java.sql.Connection getConnection(){
		java.sql.Connection conn = HikaricpMysqlDataSource.getInstance().getConnection();
		return conn;
	}


	/**
	 * jdbc 存在中文乱码
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	protected java.sql.Connection getJDBCConnection() {
		try {
			Class.forName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
			String url="jdbc:mysql://114.80.215.196:3306/sq_zhouman10?user=sq_zhouman10&password=zhouman10";
			java.sql.Connection conn = DriverManager.getConnection(url);
			conn.setAutoCommit(false);
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params 没有可填null
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> queryList(Class<T> entityClass, String sql, Map<Integer, Object> params) {
		
		java.sql.Connection connection = null;
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			connection = getConnection();
			ps = connection.prepareStatement(sql);
			if(params != null) {
				setStatmentParams(ps, params);
			}
			rs = ps.executeQuery();
			
			T entity = null;
			Map<String, Object> res = null;
			while (rs.next()) {
				// 一个指针对应一个entity
				entity = BeanUtils.createBeanJdk(entityClass);
				res = rsToMap(rs);
				BeanMap.create(entity).putAll(res);
				list.add(entity);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				releaseAll(rs, ps, connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public <T> T querySingle(String sql, Map<Integer, Object> params, Class<T> entityClass) {
		java.sql.Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		T entity = null;
		try{
			connection = getConnection();
			ps = connection.prepareStatement(sql);
			setStatmentParams(ps, params);
			rs = ps.executeQuery();
			entity = BeanUtils.createBeanJdk(entityClass);
			while(rs.next()){
				Map<String, Object> res = rsToMap(rs);
				BeanMap.create(entity).putAll(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				releaseAll(rs, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
		return entity;
	}
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @param collumToUpper 是否遵守驼峰
	 * @param secretColumn 是否有加密列的需求
	 * @return
	 * @throws SQLException
	 */
	public JSONArray queryList(String sql, Map<Integer, Object> params, boolean collumToUpper, List<String> secretColumn) throws SQLException{
		JSONArray list = new JSONArray();
		java.sql.Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			connection = getConnection();
			ps = connection.prepareStatement(sql);
			if(params != null){
				setStatmentParams(ps, params);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			// 列数
			int columnCounts = rsmd.getColumnCount();
			//
			JSONObject jo = null;
			String key = "";
			String value = null;
			while (rs.next()) {
				jo = new JSONObject();
				for (int i = 1; i <= columnCounts; i++) {
					//  转实例名
					key = StringUtil.toEntityName(rsmd.getColumnName(i), collumToUpper);
					value = (rs.getObject(i) == null) ? "" : rs.getObject(i).toString();
					if(secretColumn != null && secretColumn.contains(key)){
						// 如果这列需要加密 
						byte[] valueByte;
						try {
							valueByte = DesUtil.encryptMode(value.getBytes("UTF-8"));
							// 从数据库取出前， 先执行加密
							value = ByteUtil.bytes2HexStr(valueByte);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							
						}
						
					}
					jo.put(key, value);
				}
				list.add(jo);
			}
		}finally{
			releaseAll(rs, ps, connection);
		}			
		return list;		
	}
	
	public Integer queryCount(String sql, Map<Integer, Object> params, String key){
		java.sql.Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		Integer returnInteger = null;
		try{
			connection = getConnection();
			ps = connection.prepareStatement(sql);
			setStatmentParams(ps, params);
			rs = ps.executeQuery();
			while(rs.next()){
				returnInteger = rs.getInt(key);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				releaseAll(rs, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return returnInteger;
	}

	private void releaseAll(ResultSet rs, PreparedStatement ps,
			java.sql.Connection connection) throws SQLException {
		if(rs != null){
			rs.close();
		}
		if(ps != null){
			ps.close();
		}
		if(connection != null){
			connection.close();
		}
	}

	protected JSONArray queryList(String sql, Map<Integer, Object> params, List<String> secretColumn) throws SQLException{
		return queryList(sql, params, true, secretColumn);		
	}
	
	protected void setStatmentParams(PreparedStatement ps, Map<Integer, Object> params) throws SQLException{
		for (Iterator<Integer> iterator = params.keySet().iterator(); iterator
				.hasNext();) {
			Integer key = iterator.next();
			ps.setObject(key, params.get(key));
		}
	}

	protected static Map<String, Object> rsToMap(ResultSet rs) throws SQLException {
		
		ResultSetMetaData rsmd = rs.getMetaData();
		// 列数
		int columnCounts = rsmd.getColumnCount();
		
		Map<String, Object> map = new HashMap<String, Object>();
		String key = null;
		Object value = null;
		for (int i = 1; i <= columnCounts; i++) {
			key = rsmd.getColumnName(i);
			// 转驼峰
			key = StringUtil.toEntityName(key, true);
			// 首字母大写
			//key = String.valueOf(key.charAt(0)).toUpperCase() + key.substring(1);
			value = rs.getObject(i);
			map.put(key, value);
		}
		return map;
	}

}

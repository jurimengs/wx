package com.org.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.org.annotations.Init;
import com.org.exception.SvcException;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

@Init
public class CommonDao extends BaseDaoCiglib {
	
	/**
	 * @param sql
	 * @param params
	 * @param secretColumn 加密字段列表。指定的列将被加密
	 * @return
	 * @throws SvcException
	 */
	public JSONObject queryJSONObject(String sql, Map<Integer, Object> params, List<String> secretColumn) {
		JSONObject jo = null;
		try {
			JSONArray list = queryList(sql, params, secretColumn);
			if (list.size() > 1) {
				System.out.println("Common Dao : result counts more than single");
				return list.getJSONObject(0);
			}
			if (list.size() <= 0) {
				return null;
			}
			return list.getJSONObject(0);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return jo;
	}

	public JSONArray queryJSONArray(String sql, Map<Integer, Object> params, List<String> secretColumn) {
		JSONArray list = new JSONArray();
		try {
			list = queryList(sql, params, secretColumn);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return list;
	}

	public JSONArray queryJSONArray(String sql, Map<Integer, Object> params) {
		JSONArray list = new JSONArray();
		try {
			list = queryList(sql, params, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 需要加密某列的查询
	 * @param sql
	 * @param secretColumn 需要对值进行加密的列名
	 * @return
	 */
	public JSONArray queryJSONArray(String sql, List<String> secretColumn) {
		JSONArray list = null;
		try {
			list = queryList(sql, null, secretColumn);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * 需要加密某列的查询
	 * @param sql
	 * @return
	 */
	public JSONArray queryJSONArray(String sql) {
		JSONArray list = null;
		try {
			list = queryList(sql, null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 *            ?,?,? {1:"...", 2:"...", 3:"..."}
	 * @return
	 * @return
	 * @throws SQLException
	 */
	public boolean addSingle(String sql, Map<Integer, Object> params) {
		java.sql.Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			setStatmentParams(ps, params);
			ps.execute();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	public boolean update(String sql, Map<Integer, Object> params)  {
		java.sql.Connection conn = getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			setStatmentParams(ps, params);
			ps.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 事务插入。建议不大于10000条
	 * 参考backup文件，关于表不支持事务的解决方案
	 * @param sql
	 * @param paramsList
	 */
	public boolean transactionInsert(String sql, List<Map<Integer, Object>> paramsList){
		java.sql.Connection conn = getConnection();
		
		PreparedStatement ps = null;
		try {
			for (int i = 0; i < paramsList.size(); i++) {
				ps = conn.prepareStatement(sql);
				setStatmentParams(ps, paramsList.get(i));
				ps.executeUpdate();
			}
			conn.commit();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
			
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return false;
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//private Log log = LogFactory.getLog(CommonDao.class);
}

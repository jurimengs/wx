package com.org.remote;


// TODO 
public class RemoteServiceImpl implements RemoteService {
	//private final static DataSourceContainer dsc = DataSourceContainer.getInstance();
	//private Log log = LogFactory.getLog(RemoteServiceImpl.class);

	@Override
	public String access(String request) {
		// һ����������Դ

		
		//String identityFlag = requestJson.getString("identityFlag");
		// ������ݣ�·�ɵ�ָ�������ݿ�
		//Connection con = null;
//		if(identityFlag.equals("monitor")){
//			con = dsc.getConnection(CommonConstant.DB_MONGO);
//		} else {
//			con = dsc.getConnection(CommonConstant.DB_HIKARICP);
//		}
		
		// ����ִ�в�ѯ
		//JSONObject result = executeQuery(requestJson, con);
		
		// ������������
//		return result.toString();
		return null;
	}
}

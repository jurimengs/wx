package com.org.wx.utils;

import java.math.BigDecimal;
import java.util.Random;

public class SuanMing {
    private static int yy[] = {0, 6, 8, 7, 5, 15, 6, 16, 15, 7, 9, 12, 10,
        7, 15, 6, 5, 14, 14, 9, 7, 7, 9, 12, 8,
        7, 13, 5, 14, 5, 9, 17, 5, 7, 12, 8, 8,
        6, 19, 6, 8, 16, 10, 6, 12, 9, 6, 7, 12,
        5, 9, 8, 7, 8, 15, 9, 16, 8, 8, 19, 12};
    private static int mm[] = {0, 6, 7, 18, 9, 5, 16, 9, 15, 18, 8, 9, 5};
    private static int dd[] = {0, 5, 10, 8, 15, 16, 15, 8, 16, 8, 16, 19, 17, 8, 17, 10,
        8, 9, 18, 5, 15, 10, 9, 8, 9, 15, 18, 7, 8, 16, 6};
    private static int hh[] = {0, 0, 6, 7, 10, 9, 16, 10, 8, 8, 9, 6, 6, 16};

    /**
     * 算命有多少钱
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 时
     * @return
     */
    public static String jiaZhi(int year, int month, int day, int hour) {
        int zong, zong1, zong2, n;
        if (hour % 2 == 0) {
            n = (hour + 2) / 2;
        } else {
            n = (hour + 3) / 2;
        }
        zong = yy[(year - 1821) % 60 + 1] + mm[month] + dd[day] + hh[n];
        zong1 = zong % 10;
        zong2 = zong / 10;

        System.out.print("你的命有" + zong2 + "两" + zong1 + "钱!\n\n");
        return "你的命有" + zong2 + "两" + zong1 + "钱!\n\n";
    }
    
    /**
     * 根据位置算时运
     * @param latitude 经度
     * @param longitude 纬度
     * @return
     */
    public static String byLocation(String latitude, String longitude) {
    	Random r = new Random();
    	StringBuffer sb = new StringBuffer();
    	
    	BigDecimal bRandomDouble = new BigDecimal(r.nextDouble() * 100);
    	double randomDouble = bRandomDouble.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
    	
    	BigDecimal blatitude = new BigDecimal(latitude); 
    	double dlatitude=   blatitude.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
    	BigDecimal blongitude = new BigDecimal(longitude); 
    	double dlongitude=   blongitude.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
    	
    	if(randomDouble > dlatitude) {
    		if(randomDouble > dlongitude) {
    			sb.append("面向南方");
    		} else if(randomDouble < dlongitude) {
    			sb.append("面向北方");
    		} else if(randomDouble == dlongitude) {
    			sb.append("超级大运A级");
    		}
    		sb.append("，会有好事发生");
    	} else if(randomDouble < dlatitude){
    		if(randomDouble > dlongitude) {
    			sb.append("面向东方");
    		} else if(randomDouble < dlongitude) {
    			sb.append("面向西方");
    		} else if(randomDouble == dlongitude) {
    			sb.append("超级大运B级");
    		}
    		sb.append("，会天降横材");
    	} else{
    		if(randomDouble > dlatitude) {
    			sb.append("你已中了超级大运，如果面向东方走三步， 定会掉下水井");
    		} else if(randomDouble < dlongitude) {
    			sb.append("你已中了超级大运，如果面向天空走三步， 定会中鸟屎");
    		} else {
    			sb.append("本世纪能遇到这个巧合的人，不可能超过三个，去拯救世界吧");
    		}
    	}
    	
    	return sb.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	Random r = new Random();
    	for (int i = 0; i < 200; i++) {
    		BigDecimal a = new BigDecimal(r.nextDouble() * 100);
        	double da = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
    		BigDecimal b = new BigDecimal(r.nextDouble() * 100);
        	double db = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        	System.out.println(da + "  " + db + ": " + byLocation(""+da, ""+db));
		}
    }

}



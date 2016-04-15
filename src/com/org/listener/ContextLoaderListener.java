package com.org.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.web.context.ContextLoader;





import com.org.annotations.ClassContext;
import com.org.caches.CacheManager;
import com.org.container.CommonContainer;
import com.org.dao.HikaricpMysqlDataSource;
//import com.org.queues.QueueExecutor;
import com.org.utils.PropertyUtil;
import com.org.wx.utils.WxUtil;

public class ContextLoaderListener implements ServletContextListener{

	private static Log log = LogFactory.getLog(ContextLoaderListener.class);
	
	//private ContextLoader contextLoader;
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// 取消timer
		WxUtil.cancelAutoRun();
		destroyJDBCDrivers();
        destroySpecifyThreads();
		
		//
		/*if (this.contextLoader != null) {
			this.contextLoader.closeWebApplicationContext(arg0.getServletContext());
			//KestrelSub.getInstance().stop();
		}*/
	}
	
/*	protected ContextLoader createContextLoader() {
		return new ContextLoader();
	}

	public ContextLoader getContextLoader() {
		return contextLoader;
	}*/

	/**
	 * 加载顺序有严格要求，不可乱
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext servletContext =	arg0.getServletContext();
	    PropertyUtil.initProperties(servletContext);
		log.info("1. ==============> 配置文件 加载完成...");

		/* 6.init spring context */
		//this.contextLoader = createContextLoader();
		//WebApplicationContext context = this.contextLoader.initWebApplicationContext(servletContext);
		//this.contextLoader.initWebApplicationContext(servletContext);
		// 缓存 servletContext
		CommonContainer.saveContext(servletContext);
		log.info("2. ==============> servletContext 初始化完成...");
		
	    // 加载bean
	    ClassContext.getInstance().init();
		log.info("3. ==============> ClassContext 加载完成...");
		
		HikaricpMysqlDataSource.getInstance().init();
	    /*10. start socket server */
//	    IServerSocket serverSocket = (IServerSocket)sc.getBean(CT.SPRING_BEANNAME_JNIOSERVERSOCKET);//SPRING_BEANNAME_JNIOSERVERSOCKET,SPRING_BEANNAME_SERVERSOCKET
//	    serverSocket.start();
		
		/*11. start kestrel message */
		//KestrelSub.getInstance().start();
		
		/*11. test data 
			MongoDbTestServcie mongodbSvc = (MongoDbTestServcie)sc.getBean("MongoDbTestSvc");
			mongodbSvc.test();
		*/
		
	    // 开启定时获取token任务
	    WxUtil.autoRun();
		log.info("4. ==============> 定时获取微信token任务启动...");
		
	    // 初始化聊天室房间信息
	    // RoomContainer.initRoom();
	    
	    // 初始化缓存信息
	    CacheManager.getInstance().initAllContainer();
		log.info("5. ==============> 缓存任务完成...");
	    
	    // 启动队列消化线程
	    //QueueExecutor.start();
		/*12. init guice with mybatis */
		/*log.info("Integrate Smp With Guice Container Begin....");
		sc.initGuice(GuiceHelper.createInjector(new SmpMyBatisModule(CT.SMP_GUICE_DSTYPE_C3P0,CT.SMP_GUICE_MYBATIS_EID)));
		log.info("Integrate Smp With Guice Container End....");
		
		try {
			TestSvc testSvc = (TestSvc)SmpContextUtils.getSmpContext().getInstance(TestSvc.class);
			testSvc.test(StringUtil.getUUIDValue());	
		} catch (SmpException e) {
			e.printStackTrace();
		}*/
		// 初始化数据源
		//DataSourceContainer.getInstance().initAllDataSource();
	    
	}
	
    private void destroySpecifyThreads() {
        final Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread thread : threads) {
            if (needManualDestroy(thread)) {
                synchronized (this) {
                    try {
                        thread.interrupt();
                        log.info(String.format("Destroy  %s successful", thread));
                    } catch (Exception e) {
                    	e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean needManualDestroy(Thread thread) {
        final String threadName = thread.getName();
        for (String manualDestroyThreadIdentifier : MANUAL_DESTROY_THREAD_IDENTIFIERS) {
            if (threadName.contains(manualDestroyThreadIdentifier)) {
                return true;
            }
        }
        return false;
    }

    private void destroyJDBCDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver;
        while (drivers.hasMoreElements()) {
            driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.info(String.format("Deregister JDBC driver %s successful", driver));
            } catch (SQLException e) {
            	log.info(String.format("Deregister JDBC driver %s error", driver), e);
            }
        }
    }
    public static final List<String> MANUAL_DESTROY_THREAD_IDENTIFIERS = Arrays.asList("Thread-1", "Hikari Housekeeping Timer", "HikariCP connection filler", "MySQL Statement Cancellation Timer", "HikariCP connection filler");
}

//package com.org.queues;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
///**
// * ����ִ����, ���ڴ�������е�����
// * @author zhou_man
// * 2016��3��15��
// */
//public class QueueExecutor implements Runnable{
//	private static QueueExecutor queueExecutor;
//	private static Log log = LogFactory.getLog(QueueExecutor.class);
//	private QueueExecutor(){}
//	
//	public static void start(){
//		if(queueExecutor == null) {
//			queueExecutor = new QueueExecutor();
//		}
//		log.info("QueueExecutor ����");
//		new Thread(queueExecutor).start();
//	}
//
//	@Override
//	public void run() {
//		while(true) {
//			// ������ 
//			// ����һ����ѭ���м���
//			// ����������ȡ�����ʱ�������ѡ��getTask()�м���
//			//synchronized (QueueContainer.queue) {
//				// ������п�������
//				MessageSendTask task = QueueContainer.getTask();
//				if(task != null) {
//					log.info("QueueExecutor �������񣽣�������"+task.toString());
//					task.sendByService();
//				}
//			//}
//		}
//	}
//	
//	
//}

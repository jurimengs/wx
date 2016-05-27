package com.org.queues;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhou_man
 */
public class QueueContainer{
	private static Log log = LogFactory.getLog(QueueContainer.class);
	private QueueContainer(){}
	protected static LinkedBlockingQueue<MessageSendTask> queue = new LinkedBlockingQueue<MessageSendTask>();

	/**
	 * @param task
	 */
	public static void addTask(MessageSendTask task){
		try {
			log.info("QueueContainer "+task.toString());
			queue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param task
	 */
	public static MessageSendTask getTask(){
		try {
			//synchronized(queue){
				return queue.take();
			//}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

package com.org.annotations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.utils.PropertyUtil;

/**
 * 预加载类的上下文
 * TODO 此类的一些截取的过程，可以尝试用正则表达式来完成　
 * @author zhou_man
 *
 * 2016年3月24日
 */
public class ClassContext {
	private static Log log = LogFactory.getLog(ClassContext.class);
	private static String aimStr = PropertyUtil.getValue("auto_scan", "autoscan");
	private static Map<String, Object> container = new HashMap<String, Object>();
	private static ClassContext cc = null;
	private ClassContext(){}
	
	public static ClassContext getInstance(){
		if(cc == null) {
			cc = new ClassContext();
		}
		return cc;
	}
	
    public void init(){
    	String[] strs = aimStr.split(",");
    	for (int i = 0; i < strs.length; i++) {
    		findAllClassFilesAndInitToContainer(strs[i]);
		}
    }
    
    /**
     * 
     * @param aimpath 目录路径、可能存在子目录，子目录也会处理
     * @return
     */
    private void findAllClassFilesAndInitToContainer(String aimpath){
    	String phyPath = ClassContext.class.getClassLoader().getResource("").getPath();

		//String phyPath = "/WEB-INF/classes/";
		
		
		String aimpathTemp = aimpath.replaceAll("\\.", "/");
		
		File file = new File(phyPath + aimpathTemp);
		log.info("findAllClassFilesAndInitToContainer 读取目标路径: " + file.getPath());
		List<File> fileList = new ArrayList<File>();
		// 递归文件夹,　得到fileList
		findAllClassFiles(file, fileList);
		log.info("findAllClassFilesAndInitToContainer 读取目标class数量: " + fileList.size());
		// 递归后，得到文件list, 再根据文件list　得到Classlist
		initClassToContainer(fileList, aimpath);
    }
    
    private void initClassToContainer(List<File> fileList, String aimpathTemp) {
    	if(fileList != null && fileList.size() >= 0) {
    		try {
    			String fileNameTemp = null;
	    		for (File f : fileList) {
	    			fileNameTemp = complainFilename(f.getAbsolutePath(), aimpathTemp);
    				loadOrNot(fileNameTemp);
	    		}
    		} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
    	}
	}
    
	private void loadOrNot(String fileNameTemp) throws ClassNotFoundException {
		
		Class<?> classTemp = ClassContext.class.getClassLoader().loadClass(fileNameTemp);
		
		//Class<?> classTemp = Class.forName(fileNameTemp);
		if(classTemp.getAnnotation(Init.class) != null) {
			try {
				String classTempName = classTemp.getSimpleName();
				classTempName = classTempName.replaceFirst(classTempName.substring(0, 1),classTempName.substring(0, 1).toLowerCase()) ;
				container.put(classTempName, classTemp.newInstance());
				log.info("容器初始化" + classTempName + ": " + fileNameTemp + "");
			} catch (InstantiationException e) {
				log.error(classTemp.getName() + "实例化失败:InstantiationException:" + e.getMessage());
			} catch (IllegalAccessException e) {
				log.error(classTemp.getName() + "实例化失败:IllegalAccessException: " + e.getMessage());
			}
		}
	}

	private String complainFilename(String fileNameTemp, String aimpathTemp){
		fileNameTemp = fileNameTemp.replaceAll("/", "\\.");
		fileNameTemp = fileNameTemp.substring(fileNameTemp.indexOf(aimpathTemp));
		fileNameTemp = fileNameTemp.substring(0, fileNameTemp.lastIndexOf("."));
		return fileNameTemp;
    }

	private void findAllClassFiles(File file, List<File> list){
    	if(file.isDirectory()) {
    		File[] classes = file.listFiles();
    		File temp = null;
    		for (int i = 0; i < classes.length; i++) {
    			temp = classes[i];
    			findAllClassFiles(temp, list);
			}
    	} else if(file.isFile()){
    		list.add(file);
    	}
    }
	
	/**
	 * 
	 * @param objectName 与类名同名
	 * @return
	 */
	public Object getObject(String objectName){
		return container.get(objectName);
	}
}

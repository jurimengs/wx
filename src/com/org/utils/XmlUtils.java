package com.org.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * XML工具类
 */
public class XmlUtils {
	private static Log log = LogFactory.getLog(XmlUtils.class);
	
	public static JSONObject getDocumentFromRequest(HttpServletRequest request) throws IOException {
		Document doc = XmlUtils.read(request.getInputStream());
		JSONObject xmlJson = XmlUtils.documentToJSON(doc);
		return xmlJson;
	}
	
	public static JSONObject documentToJSON(Document doc){
		JSONObject jsonObject = new JSONObject();
		if(doc != null){
			Element root = doc.getRootElement();
			List<?> childList = root.getChildren();
			Element e = null;
			for (int i = 0; i < childList.size(); i++) {
				e = (Element) childList.get(i);
				jsonObject.put(e.getName(), e.getText()); 
			}
		}
		return jsonObject;
	}

	/**
	 * @param xmlContent xml格式的字符串
	 */
	public static Document read(String xmlContent,String encode){
		ByteArrayInputStream in;
		try {
			in = new ByteArrayInputStream(xmlContent.getBytes(encode));
			return read(in);
		} catch (UnsupportedEncodingException e) {
			log.info("字符串编码错误" + e.getMessage());
		}
	     return null;
	}
	
	/**
	 * @param xmlByte xml格式字节
	 * 
	 */
	public static Document read(byte[] xmlByte){
		ByteArrayInputStream in = new ByteArrayInputStream(xmlByte);
		return read(in);
	}
	
	/**
	 * @param file xml文件
	 */
	public static Document read(File file){
		try {
			FileInputStream in = new FileInputStream(file);
			return read(in);
		} catch (FileNotFoundException e) {
			log.info("没有找到指定的文件" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param xmlDoc  Document
	 * @param encoding 字符串编码
	 * @return 转化为xml字符串
	 */
	public static String toXmlContent(Document xmlDoc, String encoding) { 
	        ByteArrayOutputStream byteRep = new ByteArrayOutputStream(); 
	        Format format = Format.getPrettyFormat(); 
	        format.setEncoding(encoding); 
	        XMLOutputter docWriter = new XMLOutputter(format); 
	        try {
				docWriter.output(xmlDoc, byteRep);
			} catch (IOException e) {
				log.info("xml格式转化错误" + e.getMessage());
			} 
	        return byteRep.toString(); 
	 } 
	
	public static Document read(InputStream in){
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			 doc = sb.build(in);
		} catch (JDOMException e) {
			log.info("jdom解析错误" + e.getMessage());
		} catch (IOException e) {
			log.info("读取xml数据流操作错误" + e.getMessage());
		} 
		return doc;
	}
}

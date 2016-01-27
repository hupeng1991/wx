/**
 * 
 */
package com.hp.wx.until;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author luohc
 * <p> 上传下载工具类，使用单例实现  </p>
 * <p> 1.解析上传配置文件 
 * </p>
 */
public class UploadUtil {
	//upload.xml相对于web根目录的路径
	private final static String UPLOAD_FILE_PATH = "upload.xml";
	private static UploadUtil util;
	private Map<String,String> uploadParam;
	private String rootPath;
	
	private UploadUtil(String rootPath){
		this.rootPath = rootPath;
		uploadParam = getUploadParams();
	}
	/**
	 * @return 键值对形式储存的配置文件参数
	 * <p>解析upload.xml配置文件为键值对形式</p>
	 * */
	public Map<String,String> getUploadParams(){
		Map<String,String> uploadParams = new HashMap<String, String>();
		DocumentBuilder builder = null;
		Document uploadDoc = null;
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();
			uploadDoc = builder.parse(new File(rootPath+"/"+UPLOAD_FILE_PATH));
			uploadParams.put("allow-doc", getContextByTag(uploadDoc, "allow-doc"));
			uploadParams.put("allow-image", getContextByTag(uploadDoc, "allow-image"));
			uploadParams.put("allow-size", getContextByTag(uploadDoc, "allow-size"));
			uploadParams.put("allow-num", getContextByTag(uploadDoc, "allow-num"));
			uploadParams.put("filePath", getContextByTag(uploadDoc, "filePath"));
			uploadParams.put("isModule", getContextByTag(uploadDoc, "isModule"));
			uploadParams.put("isFunction", getContextByTag(uploadDoc, "isFunction"));
			uploadParams.put("isYear", getContextByTag(uploadDoc, "isYear"));
			uploadParams.put("isMonth", getContextByTag(uploadDoc, "isMonth"));
			uploadParams.put("isDay", getContextByTag(uploadDoc, "isDay"));
			uploadParams.put("isRecover", getContextByTag(uploadDoc, "isRecover"));
			uploadParams.put("runtimes", getContextByTag(uploadDoc, "runtimes"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return uploadParams;
	}
	
	/**
	 * @param uploadDoc 文档
	 * @param tagName 标签名
	 * @return 标签值
	 * <p>根据给定的标签名获取标签的值</p>
	 */
	public String getContextByTag(Document uploadDoc,String tagName){
		return ((Element)(uploadDoc.getElementsByTagName(tagName).item(0))).getNodeValue();
	}
	
	public Map<String,String> getUploadParam() {
		return uploadParam;
	}
	
	
	/**
	 * @param rootPath 项目绝对路径
	 * @return 工具类实例
	 * <p>单例实现</p>
	 */
	public synchronized static UploadUtil getInstance(String rootPath){
		if(util == null){
			util = new UploadUtil(rootPath);
		}
		return util;
	}
}

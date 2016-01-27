package com.hp.wx.until;

import org.springframework.context.ApplicationContext;
/**
 *  常量设置类
 * @author:phu
 * 2015-12-15
*/
public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "account";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SEPARATOR=",sd,";                        //统一分隔符
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	public static final String SESSION_userpds = "userpds";			
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String SYSNAME = "config/SYSNAME.txt";	//系统名称路径
	public static final String EMAIL = "config/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1 = "config/SMS1.txt";			//短信账户配置路径1
	public static final String FWATERM = "config/FWATERM.txt";	//文字水印配置路径
	public static final String IWATERM = "config/IWATERM.txt";	//图片水印配置路径
	public static final String FILEPATHIMG = "webapp/images/demo/";	//图片上传路径
	public static final String FILEPATHFILE = "webapp/uploadFiles/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";	//不对匹配该值的访问路径拦截（正则）
	
	
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	public static final String EXCEPTION_CODE="exception_code";//异常接收编码
	public static final String DEFAULT_HEAD="defaulthead.png";//默认用户头像
    public static int PAGE=10;//每页条数
}

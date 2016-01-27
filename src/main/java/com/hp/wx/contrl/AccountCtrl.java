package com.hp.wx.contrl;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hp.wx.entity.Account;
import com.hp.wx.entity.Page;
import com.hp.wx.exception.WXServiceException;
import com.hp.wx.serviceinterface.IAccountService;
import com.hp.wx.state.AccountType;
import com.hp.wx.until.Const;
import com.hp.wx.until.FileUpload;
import com.hp.wx.until.MD5;
import com.hp.wx.until.PageData;
import com.hp.wx.until.PathUtil;
import com.hp.wx.until.Tools;
import com.hp.wx.until.UuidUtil;

@Controller
public class AccountCtrl extends BaseController{

	@Resource(name="AccountServiceImpl")
	private IAccountService accountService;
	
	@RequestMapping("/tologin")
	public ModelAndView tologin()throws Exception{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/signin.html");
		return mv;
	}
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public PageData login(){
		PageData rtnpd=new PageData();
		try{
			PageData pd=this.getPageData();
			if(Tools.isEmpty(pd.getString("acc_name"))){
				rtnpd.put(Const.EXCEPTION_CODE, "账号不能为空！");
				return rtnpd;
			}
			if(Tools.isEmpty(pd.getString("acc_password"))){
				rtnpd.put(Const.EXCEPTION_CODE, "密码不能为空！");
				return rtnpd;
			}
			pd.put("eamil",pd.getString("acc_name"));
			PageData account=accountService.findAccountByName(pd);
			if(account!=null){
				if(!account.getString("acc_password").equals(MD5.md5(pd.getString("acc_password")))){
					rtnpd.put(Const.EXCEPTION_CODE, "密码不正确！");
					return rtnpd;
				}
			}else{
				rtnpd.put(Const.EXCEPTION_CODE, "账号不存在！");
				return rtnpd;
			}
			//保存到会话
			getRequest().getSession().setAttribute(Const.SESSION_USER,account.convertToBean(Account.class));
		}catch(WXServiceException e){
			rtnpd.put(Const.EXCEPTION_CODE, e.getMessage());
			logger.error(e.toString());
		}catch(Exception e){
			rtnpd.put(Const.EXCEPTION_CODE, "登录异常，请重试！");
			logger.error(e.toString());
		}
		return rtnpd;
	}
	/**
	 * 去到注册页面
	 * @return
	 */
	@RequestMapping("/tosignup")
	public ModelAndView tosignup()throws Exception{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/signup.html");
		return mv;
	}
	/**
	 * 注册账号
	 * @return
	 */
	@RequestMapping("/signup")
	@ResponseBody
	public PageData signup(){
		PageData rtnpd=new PageData();
		try{
			PageData pd=this.getPageData();
			pd.put("head_img", Const.DEFAULT_HEAD);//默认头像
			pd.put("type_code", AccountType.GUEST.getType_code());
			accountService.saveAccount(pd);
		}catch(WXServiceException e){
			rtnpd.put(Const.EXCEPTION_CODE, e.getMessage());
			logger.error(e.toString());
		}catch(Exception e){
			rtnpd.put(Const.EXCEPTION_CODE, "注册出现异常，请重试！");
			logger.error(e.toString());
		}
		return rtnpd;
	}
	
	/**
	 * 去到个人信息页面
	 * @return
	 */
	@RequestMapping("/toPersonal_info")
	public ModelAndView toPersonal_info(){
		ModelAndView mv=new ModelAndView();
		mv.addObject("account", getAccount());
		mv.setViewName("/account/personal-info.html");
		mv.addObject("basePath", PathUtil.PathAddress()+"images/account_header/");
		return mv;
	}
	
	/**
	 * 保存个人账号信息
	 * @return
	 */
	@RequestMapping("/savePernalInfo")
	@ResponseBody
	public PageData savePernalInfo(){
		PageData rtnpd=new PageData();
		try{
			PageData pd=this.getPageData();
			accountService.updateAccount(pd);
			Account account=getAccount();
			account.setHead_img(pd.getString("head_img"));
			getRequest().getSession().setAttribute(Const.SESSION_USER, account);
		}catch(WXServiceException e){
			rtnpd.put(Const.EXCEPTION_CODE, e.getMessage());
			logger.error(e.toString());
		}catch(Exception e){
			rtnpd.put(Const.EXCEPTION_CODE, e.toString());
			logger.error(e.toString());
		}
		return rtnpd;
	}
	
	/**
	 * 上传头像
	 * @param file
	 * @param acc_no
	 * @return
	 */
	@RequestMapping("/saveHeadImg")
	@ResponseBody
	public PageData saveHeadImg(@RequestParam(value="head_img",required=false) MultipartFile file,
			@RequestParam(value="acc_no",required=false) String acc_no,@RequestParam(value="preimg") String preimg)throws Exception{
		PageData rtnpd=new PageData();
		try{
			String filePath = PathUtil.getClassResources() + "webapp/images/account_header/";		//头像上传路径
			PageData pd=new PageData();
			pd.put("acc_no", acc_no);
			pd.put("head_img", FileUpload.fileUp(file, filePath, UuidUtil.get32UUID()));
			pd.put("isuncheck_flag", "Y");
			accountService.updateAccount(pd);
			//更新会话的账号
			Account account=getAccount();
			account.setHead_img(pd.getString("head_img"));
			getRequest().getSession().setAttribute(Const.SESSION_USER, account);
			//删除原来的头像文件
			if(!Tools.isEmpty(preimg)&&!Const.DEFAULT_HEAD.equals(preimg)){
				File refile=new File(filePath+preimg);
				if(refile.exists()){
					refile.delete();
				}
			}
			rtnpd.put("head_img", pd.getString("head_img"));
		}catch(WXServiceException e){
			rtnpd.put(Const.EXCEPTION_CODE, e.getMessage());
			logger.error(e.toString(),e);
		}catch(Exception e){
			rtnpd.put(Const.EXCEPTION_CODE, "上传头像异常");
			logger.error(e.toString(),e);
		}
		return rtnpd;
	}
	
	/***
	 * 获取账户列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/account-list")
	public ModelAndView findAccountList(Page page)throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		pd.put("type_code", AccountType.GUEST.getType_code());
		page.setPd(pd);
		mv.addObject("accountlist", accountService.findAccountlistPage(page));
		mv.addObject("basePath", PathUtil.PathAddress()+"images/account_header/");
		mv.setViewName("/account/account-list.html");
		return mv;
	}
}

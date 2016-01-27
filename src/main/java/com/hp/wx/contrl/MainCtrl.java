package com.hp.wx.contrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hp.wx.until.CheckSignature;
import com.hp.wx.until.PageData;


@Controller
public class MainCtrl extends BaseController{

	@RequestMapping("/main")
	public String main(){
		return "/main.html";
	}
	@RequestMapping("/index")
	public String index(){
		return "/index.html";
	}
	/***
	 * 校验进入服务器入口
	 * @return
	 */
	@RequestMapping("/myplat")
	@ResponseBody
	public String myplat(){
		logger.info("MainCtrl myplat... ");
		PageData trnpd=new PageData();
		PageData pd=this.getPageData();
		String timestamp=pd.getString("timestamp");
		String nonce=pd.getString("nonce");
		String echostr=pd.getString("echostr");
		String signature=pd.getString("signature");
		logger.info("timestamp:"+timestamp+",nonce:"+nonce+",echostr:"+echostr+",signature:"+signature);
		try {
			
			boolean result=CheckSignature.check(timestamp, nonce, signature,echostr);
			if(result){
				logger.info("验证成功！");
			}else{
				logger.info("验证失败！");
			}
			trnpd.put("echostr", echostr);
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		
		return echostr;
	}

	/**
	 * 打开即时通讯窗口
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tochat")
	public ModelAndView tochat()throws Exception{
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/chat.html");
		return mv;
	}
}

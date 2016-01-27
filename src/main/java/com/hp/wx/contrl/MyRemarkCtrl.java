package com.hp.wx.contrl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hp.wx.entity.Account;
import com.hp.wx.entity.Page;
import com.hp.wx.exception.WXServiceException;
import com.hp.wx.serviceinterface.IMyRemarkService;
import com.hp.wx.until.Const;
import com.hp.wx.until.PageData;
import com.hp.wx.until.Tools;

@Controller
public class MyRemarkCtrl extends BaseController {

	@Resource(name="MyRemarkServiceImpl")
	private IMyRemarkService myRemarkServiceImpl;
	
	/**
	 * 获取备注信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remark-list")
	public ModelAndView findMyRemarkList(Page page)throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		pd.put("acc_no", getAccount().getAcc_no());
		page.setPd(pd);
		List<PageData> list=myRemarkServiceImpl.findRemarkByCondition(page);
		mv.addObject("myremarklist", list==null?new ArrayList<PageData>():list);
		mv.setViewName("/myremark/remark-list.html");
		return mv;
	}
	
	/**
	 * 去到编辑备注信息页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toModifyRemark")
	public ModelAndView toModifyRemark()throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		PageData remark=new PageData();
		if(!Tools.isEmpty(pd.getString("remark_no"))){
			remark=myRemarkServiceImpl.getMyRemarkByNo(pd);
		}
		mv.addObject("remark", remark);
		mv.setViewName("/myremark/modify.html");
		return mv;
	}
	
	/***
	 * 保存添加或修改的备注
	 * @return
	 */
	@RequestMapping("/saveRemark")
	public PageData saveRemark(){
		PageData rtnpd=new PageData();
		try{
			PageData remark=this.getPageData();
			if(!Tools.isEmpty(remark.getString("remark_no"))){
				myRemarkServiceImpl.updateMyRemark(remark);
			}else{
				Account account=getAccount();
				remark.put("acc_no", account.getAcc_no());
				remark.put("acc_name", account.getAcc_name());
				myRemarkServiceImpl.saveMyRemark(remark);
			}
		}catch(WXServiceException e){
			rtnpd.put(Const.EXCEPTION_CODE, e.getMessage());
			logger.error(e.toString());
		}catch(Exception e){
			rtnpd.put(Const.EXCEPTION_CODE, "保存异常，请重试！");
			logger.error(e.toString());
		}
		return rtnpd;
	}
	
	/**
	 * 删除备注
	 * @return
	 */
	@RequestMapping("/deleteRemark")
	public PageData deleteRemark(){
		PageData rtnpd=new PageData();
		try{
			myRemarkServiceImpl.deleteMyRemark(this.getPageData());
		}catch(WXServiceException e){
			rtnpd.put(Const.EXCEPTION_CODE, e.getMessage());
		}catch(Exception e){
			rtnpd.put(Const.EXCEPTION_CODE,"删除异常，请重试！");
		}
		return rtnpd;
	}
}

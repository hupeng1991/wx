package com.hp.wx.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hp.wx.entity.Page;
import com.hp.wx.exception.WXServiceException;
import com.hp.wx.serviceinterface.IMyRemarkService;
import com.hp.wx.until.DateUtil;
import com.hp.wx.until.PageData;
import com.hp.wx.until.Tools;

@Service("MyRemarkServiceImpl")
public class MyRemarkServiceImpl extends BaseServiceImpl implements IMyRemarkService{

	/**
	 * 校验备注的基本信息
	 * @param pd
	 * @throws Exception
	 */
	private void checkRemark(PageData pd)throws Exception{
		
		if(Tools.isEmpty(pd.getString("title"))){
			throw new WXServiceException("主题不能为空！");
		}
		if(pd.getString("title").length()>200){
			throw new WXServiceException("主题长度过长！！");
		}
		
	}
	/***
	 * 新增备注
	 * @param pd
	 * @throws Exception
	 */
	public void saveMyRemark(PageData pd)throws Exception{
		checkRemark(pd);
		pd.put("create_time", DateUtil.getTime());
		daoSupport.save("MyRemarkMapper.saveRemark", pd);
	}
	/***
	 * 根据ID获取备注信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMyRemarkByNo(PageData pd)throws Exception{
		if(Tools.isEmpty(pd.getString("remark_no"))){
			throw new WXServiceException("参数：remark_no不能为空！");
		}
		
		return (PageData)daoSupport.findForObject("MyRemarkMapper.findRemarkByNO", pd);
	}
	
	/**
	 * 修改备注信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateMyRemark(PageData pd)throws Exception{
		if(Tools.isEmpty(pd.getString("remark_no"))){
			throw new WXServiceException("参数：remark_no不能为空！");
		}
		checkRemark(pd);
		daoSupport.update("MyRemarkMapper.updateRemark", pd);
	}
	
	/**
	 * 获取备注列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findRemarkByCondition(Page page)throws Exception{
		
		return (List<PageData>)daoSupport.findForList("MyRemarkMapper.findRemarklistPage", page);
	}
	
	/**
	 * 删除备注信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteMyRemark(PageData pd)throws Exception{
		
		if(Tools.isEmpty(pd.getString("remark_no"))){
			throw new WXServiceException("参数：remark_no不能为空！");
		}
		daoSupport.delete("MyRemarkMapper.deleteRemark", pd);
	}
}

package com.hp.wx.serviceinterface;

import java.util.List;

import com.hp.wx.entity.Page;
import com.hp.wx.until.PageData;

public interface IMyRemarkService {
	/***
	 * 新增备注
	 * @param pd
	 * @throws Exception
	 */
	public void saveMyRemark(PageData pd)throws Exception;
	/***
	 * 根据ID获取备注信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getMyRemarkByNo(PageData pd)throws Exception;
	
	/**
	 * 修改备注信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateMyRemark(PageData pd)throws Exception;
	
	/**
	 * 获取备注列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findRemarkByCondition(Page page)throws Exception;
	
	/**
	 * 删除备注信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteMyRemark(PageData pd)throws Exception;
}

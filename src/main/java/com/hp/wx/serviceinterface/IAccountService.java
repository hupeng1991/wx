package com.hp.wx.serviceinterface;

import java.util.List;

import com.hp.wx.entity.Page;
import com.hp.wx.until.PageData;

public interface IAccountService {

	/**
	 * 新增用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveAccount(PageData pd)throws Exception;
	
	/**
	 * 删除账号（修改是否删除状态）
	 * @param pd 参数：acc_no
	 * @throws Exception
	 */
	public void deleteAccount(PageData pd)throws Exception;
	/**
	 * 根据主键获取账号
	 * @param pd 参数：acc_no
	 * @return
	 * @throws Exception
	 */
	public PageData findAccountByNO(PageData pd)throws Exception;
	/**
	 * 修改账号
	 * @param pd
	 * @throws Exception
	 */
	public void updateAccount(PageData pd)throws Exception;
	/***
	 * 根据账号名或者邮箱查找
	 * @param pd 参数：acc_name、email
	 * @return
	 * @throws Exception
	 */
	public PageData findAccountByName(PageData pd)throws Exception;
	
	/**
	 * 根据条件查找账号
	 * @param page 参数：type_code、acc_name
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAccountByCondition(PageData pd) throws Exception;
	/***
	 * 查询账号列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAccountlistPage(Page page) throws Exception;
}

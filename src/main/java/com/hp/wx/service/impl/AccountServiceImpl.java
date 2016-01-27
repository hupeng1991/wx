package com.hp.wx.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hp.wx.entity.Page;
import com.hp.wx.exception.WXControllerException;
import com.hp.wx.exception.WXServiceException;
import com.hp.wx.serviceinterface.IAccountService;
import com.hp.wx.until.MD5;
import com.hp.wx.until.PageData;
import com.hp.wx.until.Tools;

@Service("AccountServiceImpl")
public class AccountServiceImpl extends BaseServiceImpl implements IAccountService{

	/**
	 * 新增用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveAccount(PageData pd)throws Exception{
		checkAccount(pd);
		pd.put("acc_password", MD5.md5(pd.getString("acc_password")));
		pd.put("isdeleted", "N");
		daoSupport.save("AccountMapper.saveAccount", pd);
	}
	
	/**
	 * 删除账号（修改是否删除状态）
	 * @param pd 参数：acc_no
	 * @throws Exception
	 */
	public void deleteAccount(PageData pd)throws Exception{
		if(Tools.isEmpty(pd.getString("acc_no"))){
			throw new WXControllerException("参数：用户ID为空！");
		}
		daoSupport.update("AccountMapper.deleteAccount",pd);
	}
	/**
	 * 根据主键获取账号
	 * @param pd 参数：acc_no
	 * @return
	 * @throws Exception
	 */
	public PageData findAccountByNO(PageData pd)throws Exception{
		if(Tools.isEmpty(pd.getString("acc_no"))){
			throw new WXControllerException("参数：账号ID为空！");
		}
		return (PageData)daoSupport.findForObject("AccountMapper.findAccountByNO",pd);
	}
	/**
	 * 检验用户名和邮箱是否存在
	 * @param pd
	 * @throws Exception
	 */
	private void checkAccount(PageData pd)throws Exception{
		
		if(pd.getString("acc_name").length()>20){
			throw new WXServiceException("账号字符长度大于20！");
		}
		PageData parampd=new PageData();
		parampd.put("acc_name", pd.getString("acc_name"));
		parampd.put("email", "");
		PageData account=findAccountByName(parampd);
		if(account!=null&&(pd.get("acc_no")==null||account.getInt("acc_no")!=pd.getInt("acc_no"))){
			throw new WXServiceException("该账号已经存在！");
		}
		parampd.put("acc_name", "");
		parampd.put("email", pd.getString("email"));
		account=findAccountByName(parampd);
		if(findAccountByName(parampd)!=null&&(pd.get("acc_no")==null||account.getInt("acc_no")!=pd.getInt("acc_no"))){
			throw new WXServiceException("该邮箱已经存在注册！");
		}
	} 
	/**
	 * 修改账号
	 * @param pd
	 * @throws Exception
	 */
	public void updateAccount(PageData pd)throws Exception{
		if(Tools.isEmpty(pd.getString("acc_no"))){
			throw new WXControllerException("参数：用户ID为空！");
		}
		if(!"Y".equals(pd.getString("isuncheck_flag"))){
			//是否修改密码
			if("Y".equals(pd.getString("isupdatepassword"))){
				pd.put("acc_password", MD5.md5(pd.getString("acc_password")));
			}else{
				pd.remove("acc_password");
			}
			checkAccount(pd);
		}
		daoSupport.update("AccountMapper.updateAccount",pd);
	}
	/***
	 * 根据账号名或者邮箱查找
	 * @param pd 参数：acc_name、email
	 * @return
	 * @throws Exception
	 */
	public PageData findAccountByName(PageData pd)throws Exception{
		if(Tools.isEmpty(pd.getString("acc_name"))&&Tools.isEmpty(pd.getString("email"))){
			throw new WXServiceException("参数：用户名和邮箱不能同时为空！");
		}
		return (PageData)daoSupport.findForObject("AccountMapper.findAccountByName",pd);
	}
	
	/**
	 * 根据条件查找账号
	 * @param page 参数：type_code、acc_name
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAccountByCondition(PageData pd) throws Exception{
		
		return (List<PageData>)daoSupport.findForList("AccountMapper.findAccountByCondition",pd);
	}
	
	/***
	 * 查询账号列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAccountlistPage(Page page) throws Exception{
		
		return (List<PageData>)daoSupport.findForList("AccountMapper.findAccountlistPage",page);
	}
}

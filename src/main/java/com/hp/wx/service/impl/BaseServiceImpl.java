package com.hp.wx.service.impl;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.wx.until.ConfigChangeHandle;

public class BaseServiceImpl {
public Logger logger=LoggerFactory.getLogger(BaseServiceImpl.class);
    
	@Resource
	protected com.hp.wx.dao.DaoSupport daoSupport;

	
	public Object save(String str, Object obj) throws Exception {
		return this.daoSupport.save(str, obj);
	}

	
	public Object update(String str, Object obj) throws Exception {
		return this.daoSupport.update(str, obj);
	}

	
	public Object delete(String str, Object obj) throws Exception {
		return this.daoSupport.delete(str, obj);
	}

	
	public Object findForObject(String str, Object obj) throws Exception {
		return this.daoSupport.findForObject(str, obj);
	}

	
	public Object findForList(String str, Object obj) throws Exception {
		  return this.daoSupport.findForList(str, obj);
	}

	
	public Object findForMap(String sql, Object obj, String key, String value) throws Exception {
		return this.daoSupport.findForMap(sql, obj, key, value);
	}
	
	/**
	 * 比较两个对象，把新对象的值赋值给原对象
	 * @param newmap 新对象
	 * @param oldmap 原对象
	 */
	public void newCopyForOld(Map newmap,Map oldmap){
		//把新值赋值给旧的对象
		if(newmap==null||oldmap==null){
			oldmap=newmap;
			return;
		}
		Iterator iter=newmap.keySet().iterator();
		while(iter.hasNext()){
			Object obj=iter.next();
			oldmap.put(obj, newmap.get(obj));
		}
	}
	
}

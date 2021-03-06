package com.jeecms.cms.manager.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.dao.main.CmsDepartmentDao;
import com.jeecms.cms.entity.assist.CmsGuestbookCtg;
import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.CmsDepartment;
import com.jeecms.cms.entity.main.CmsUser;
import com.jeecms.cms.manager.assist.CmsGuestbookCtgMng;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.CmsDepartmentMng;
import com.jeecms.cms.manager.main.CmsUserMng;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;

@Service
@Transactional
public class CmsDepartmentMngImpl implements CmsDepartmentMng {
	
	@Transactional(readOnly = true)
	public List<CmsDepartment> getList(){
		return dao.getList();
	}
	
	@Transactional(readOnly = true)
	public Pagination getPage(String name, int pageNo,
			int pageSize) {
		return dao.getPage( name, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public CmsDepartment findById(Integer id) {
		CmsDepartment entity = dao.findById(id);
		return entity;
	}

	public CmsDepartment save(CmsDepartment bean) {
		dao.save(bean);
		return bean;
	}
	
	public CmsDepartment save(CmsDepartment bean,Integer channelIds[],Integer[]ctgIds) {
		dao.save(bean);
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				bean.addToChannels(channel);
			}
		}
		if(ctgIds!=null){
			CmsGuestbookCtg ctg;
			for(Integer cid:ctgIds){
				ctg=guestBookCtgMng.findById(cid);
				bean.addToGuestBookCtgs(ctg);
			}
		}
		return bean;
	}

	public CmsDepartment update(CmsDepartment bean) {
		Updater<CmsDepartment> updater = new Updater<CmsDepartment>(bean);
		CmsDepartment entity = dao.updateByUpdater(updater);
		return entity;
	}
	
	public CmsDepartment update(CmsDepartment bean,Integer channelIds[],Integer[]ctgIds) {
		Updater<CmsDepartment> updater = new Updater<CmsDepartment>(bean);
		CmsDepartment entity = dao.updateByUpdater(updater);
		entity.getChannels().clear();
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				entity.addToChannels(channel);
			}
		}
		entity.getGuestBookCtgs().clear();
		if(ctgIds!=null){
			CmsGuestbookCtg ctg;
			for(Integer cid:ctgIds){
				ctg=guestBookCtgMng.findById(cid);
				entity.addToGuestBookCtgs(ctg);
			}
		}
		return entity;
	}
	public CmsDepartment deleteMembers(CmsDepartment bean,Integer userIds[]){
		Updater<CmsDepartment> updater = new Updater<CmsDepartment>(bean);
		CmsDepartment entity = dao.updateByUpdater(updater);
		if (userIds != null) {
			CmsUser user;
			for (Integer uid : userIds) {
				user = userMng.findById(uid);
				entity.delFromUsers(user);
			}
		}
		return entity;
	}

	public void updatePriority(Integer[] ids, Integer[] priorities) {
		if (ids == null || priorities == null || ids.length <= 0
				|| ids.length != priorities.length) {
			return;
		}
		CmsDepartment department;
		for (int i = 0, len = ids.length; i < len; i++) {
			department = findById(ids[i]);
			department.setPriority(priorities[i]);
		}
	}

	public CmsDepartment deleteById(Integer id) {
		CmsDepartment bean = dao.deleteById(id);
		return bean;
	}

	public CmsDepartment[] deleteByIds(Integer[] ids) {
		CmsDepartment[] beans = new CmsDepartment[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	public boolean nameExist(String name) {
		return dao.findByName(name)!=null;
	}

	private CmsDepartmentDao dao;
	
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private CmsGuestbookCtgMng guestBookCtgMng;
	@Autowired
	private CmsUserMng userMng;

	@Autowired
	public void setDao(CmsDepartmentDao dao) {
		this.dao = dao;
	}


}
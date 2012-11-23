package com.jeecms.cms.manager.main;

import java.util.List;

import com.jeecms.cms.entity.main.CmsDepartment;
import com.jeecms.common.page.Pagination;

public interface CmsDepartmentMng {

	public List<CmsDepartment> getList();
	
	public Pagination getPage( String name, int pageNo,int pageSize);

	public CmsDepartment findById(Integer id);

	public CmsDepartment save(CmsDepartment bean);
	
	public CmsDepartment save(CmsDepartment bean,Integer channelIds[],Integer[]ctgIds);

	public CmsDepartment update(CmsDepartment bean);
	
	public CmsDepartment update(CmsDepartment bean,Integer channelIds[],Integer[]ctgIds);
	
	public CmsDepartment deleteMembers(CmsDepartment bean,Integer userIds[]);

	public void updatePriority(Integer[] ids, Integer[] priorities);

	public CmsDepartment deleteById(Integer id);
	
	public CmsDepartment[] deleteByIds(Integer ids[]);

	public boolean nameExist(String name);


}
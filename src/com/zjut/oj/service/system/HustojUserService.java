package com.zjut.oj.service.system;

import java.util.List;

import com.zjut.oj.entity.system.HustojUserBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface HustojUserService
{
    int[] insert(HustojUserBean hustojUserBean) throws ApplicationException;
    
    void delete(HustojUserBean hustojUserBean) throws ApplicationException;
    
    int update(HustojUserBean hustojUserBean) throws ApplicationException;
    
    HustojUserBean findById(Integer id) throws ApplicationException;
    
    List<HustojUserBean> exportHustojUser(HustojUserBean hustojUserBean) throws ApplicationException;
    
    PageQueryResult queryForPage(HustojUserBean hustojUserBean, PageQueryResult pageQueryResult) throws ApplicationException;
    
    int transMail(HustojUserBean hustojUserBean) throws ApplicationException;

	/**
	 * ≤Œ ˝º”√‹
	 * @param hustojUserBean
	 * @return
	 */
	String genSignLink(HustojUserBean hustojUserBean) throws ApplicationException;
}
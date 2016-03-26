package com.zjut.oj.service.system;

import java.util.List;

import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface MailInfoService
{
    int insert(MailInfoBean mailInfoBean) throws ApplicationException;
    
    void delete(MailInfoBean mailInfoBean) throws ApplicationException;
    
    void update(MailInfoBean mailInfoBean) throws ApplicationException;
    
    MailInfoBean findById(Integer id) throws ApplicationException;
    
    List<MailInfoBean> queryForList(MailInfoBean mailInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(MailInfoBean mailInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
    
	void transMail(MailInfoBean mailInfoBean);
}
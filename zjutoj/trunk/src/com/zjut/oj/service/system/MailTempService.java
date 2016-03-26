package com.zjut.oj.service.system;

import java.util.List;

import com.zjut.oj.entity.system.MailTempBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface MailTempService
{
    int insert(MailTempBean mailTempBean) throws ApplicationException;
    
    void delete(MailTempBean mailTempBean) throws ApplicationException;
    
    void update(MailTempBean mailTempBean) throws ApplicationException;
    
    MailTempBean findById(Integer id) throws ApplicationException;
    
    List<MailTempBean> queryForList(MailTempBean mailTempBean) throws ApplicationException;
    
    PageQueryResult queryForPage(MailTempBean mailTempBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
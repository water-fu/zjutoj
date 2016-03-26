package com.zjut.oj.service.match;

import java.util.List;

import com.zjut.oj.entity.match.LangInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface LangInfoService
{
    int insert(LangInfoBean langInfoBean) throws ApplicationException;
    
    void delete(LangInfoBean langInfoBean) throws ApplicationException;
    
    void update(LangInfoBean langInfoBean) throws ApplicationException;
    
    LangInfoBean findById(Integer id) throws ApplicationException;
    
    List<LangInfoBean> queryForList(LangInfoBean langInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(LangInfoBean langInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
package com.zjut.oj.service.match;

import java.util.List;

import com.zjut.oj.entity.match.ContestUserBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface ContestUserService
{
    int insert(ContestUserBean contestUserBean) throws ApplicationException;
    
    void delete(ContestUserBean contestUserBean) throws ApplicationException;
    
    void update(ContestUserBean contestUserBean) throws ApplicationException;
    
    ContestUserBean findById(Integer id) throws ApplicationException;
    
    List<ContestUserBean> queryForList(ContestUserBean contestUserBean) throws ApplicationException;
    
    PageQueryResult queryForPage(ContestUserBean contestUserBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
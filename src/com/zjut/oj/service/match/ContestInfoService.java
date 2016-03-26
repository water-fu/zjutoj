package com.zjut.oj.service.match;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zjut.oj.entity.match.ContestInfoBean;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface ContestInfoService
{
    int insert(ContestInfoBean contestInfoBean) throws ApplicationException;
    
    void delete(ContestInfoBean contestInfoBean) throws ApplicationException;
    
    void update(ContestInfoBean contestInfoBean) throws ApplicationException;
    
    ContestInfoBean findById(Integer id) throws ApplicationException;
    
    List<ContestInfoBean> queryForList(ContestInfoBean contestInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(ContestInfoBean contestInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
    
    void status(ContestInfoBean contestInfoBean) throws ApplicationException;
    
    PageQueryResult queryProblemForPage(ProblemInfoBean problemInfoBean, PageQueryResult pageQueryResult, HttpServletRequest request) throws ApplicationException;
    
    PageQueryResult queryUserForPage(UserInfoBean userInfoBean, PageQueryResult pageQueryResult, HttpServletRequest request) throws ApplicationException;
}
package com.zjut.oj.service.study;

import java.util.List;

import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface ProblemInfoService
{
    int insert(ProblemInfoBean problemInfoBean) throws ApplicationException;
    
    void delete(ProblemInfoBean problemInfoBean) throws ApplicationException;
    
    void update(ProblemInfoBean problemInfoBean) throws ApplicationException;
    
    ProblemInfoBean findById(Integer id) throws ApplicationException;
    
    List<ProblemInfoBean> queryForList(ProblemInfoBean problemInfoBean) throws ApplicationException;

    PageQueryResult queryForPage(ProblemInfoBean problemInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
package com.zjut.oj.service.study;

import java.util.List;

import com.zjut.oj.entity.study.ProblemAttachBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface ProblemAttachService
{
    int insert(ProblemAttachBean problemAttachBean) throws ApplicationException;
    
    void insert(List<ProblemAttachBean> problemAttachBeans) throws ApplicationException;
    
    void delete(ProblemAttachBean problemAttachBean) throws ApplicationException;
    
    void update(ProblemAttachBean problemAttachBean) throws ApplicationException;
    
    ProblemAttachBean findById(Integer id) throws ApplicationException;
    
    List<ProblemAttachBean> queryForList(ProblemAttachBean problemAttachBean) throws ApplicationException;
    
    PageQueryResult queryForPage(ProblemAttachBean problemAttachBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
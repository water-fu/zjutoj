package com.zjut.oj.service.study;

import java.util.List;

import com.zjut.oj.entity.study.ProblemContentBean;
import com.zjut.oj.util.ApplicationException;

public interface ProblemContentService
{
    int insert(ProblemContentBean problemContentBean) throws ApplicationException;
    
    void delete(ProblemContentBean problemContentBean) throws ApplicationException;
    
    void update(ProblemContentBean problemContentBean) throws ApplicationException;
    
    ProblemContentBean findById(Integer id) throws ApplicationException;
    
    List<ProblemContentBean> queryForList(ProblemContentBean problemContentBean) throws ApplicationException;
}
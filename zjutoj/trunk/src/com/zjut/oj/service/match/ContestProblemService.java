package com.zjut.oj.service.match;

import java.util.List;

import com.zjut.oj.entity.match.ContestProblemBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface ContestProblemService
{
    int insert(ContestProblemBean contestProblemBean) throws ApplicationException;
    
    void delete(ContestProblemBean contestProblemBean) throws ApplicationException;
    
    void update(ContestProblemBean contestProblemBean) throws ApplicationException;
    
    ContestProblemBean findById(Integer id) throws ApplicationException;
    
    List<ContestProblemBean> queryForList(ContestProblemBean contestProblemBean) throws ApplicationException;
    
    PageQueryResult queryForPage(ContestProblemBean contestProblemBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
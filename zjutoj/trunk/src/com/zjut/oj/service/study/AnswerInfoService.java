package com.zjut.oj.service.study;

import java.util.List;

import com.zjut.oj.entity.study.AnswerInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface AnswerInfoService
{
    int insert(AnswerInfoBean answerInfoBean) throws ApplicationException;
    
    void delete(AnswerInfoBean answerInfoBean) throws ApplicationException;
    
    void update(AnswerInfoBean answerInfoBean) throws ApplicationException;
    
    AnswerInfoBean findById(Integer id) throws ApplicationException;
    
    List<AnswerInfoBean> queryForList(AnswerInfoBean answerInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(AnswerInfoBean answerInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
    
    void show(AnswerInfoBean answerInfoBean) throws ApplicationException;
}
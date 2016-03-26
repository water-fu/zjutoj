package com.zjut.oj.service.study;

import java.util.List;

import com.zjut.oj.entity.study.CommentInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface CommentInfoService
{
    int insert(CommentInfoBean commentInfoBean) throws ApplicationException;
    
    void delete(CommentInfoBean commentInfoBean) throws ApplicationException;
    
    void update(CommentInfoBean commentInfoBean) throws ApplicationException;
    
    CommentInfoBean findById(Integer id) throws ApplicationException;
    
    List<CommentInfoBean> queryForList(CommentInfoBean commentInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(CommentInfoBean commentInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
    
    PageQueryResult queryChildForPage(CommentInfoBean commentInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
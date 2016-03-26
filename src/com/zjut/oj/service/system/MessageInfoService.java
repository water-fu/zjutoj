package com.zjut.oj.service.system;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.system.MessageInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

@Service
@Transactional
public interface MessageInfoService
{
    int insert(MessageInfoBean messageInfoBean) throws ApplicationException;
    
    void delete(MessageInfoBean messageInfoBean) throws ApplicationException;
    
    void update(MessageInfoBean messageInfoBean) throws ApplicationException;
    
    MessageInfoBean detail(MessageInfoBean messageInfoBean) throws ApplicationException;
    
    MessageInfoBean findById(Integer id) throws ApplicationException;
    
    List<MessageInfoBean> queryForList(MessageInfoBean messageInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(MessageInfoBean messageInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
    
    List<PageQueryResult> initIndexPage() throws ApplicationException;
}
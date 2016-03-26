package com.zjut.oj.service.system;

import com.zjut.oj.entity.system.MessageAttachBean;
import com.zjut.oj.util.ApplicationException;

public interface MessageAttachService
{
    int insert(MessageAttachBean messageAttachBean) throws ApplicationException;
    
    void delete(MessageAttachBean messageAttachBean) throws ApplicationException;
    
    void update(MessageAttachBean messageAttachBean) throws ApplicationException;
    
    MessageAttachBean findById(Integer id) throws ApplicationException;
}
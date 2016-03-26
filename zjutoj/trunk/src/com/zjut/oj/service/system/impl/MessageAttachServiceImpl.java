package com.zjut.oj.service.system.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.system.MessageAttachBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.service.system.MessageAttachService;

@Service("messageAttachService")
@Transactional
public class MessageAttachServiceImpl extends ServiceSupport implements MessageAttachService
{
    public int insert(MessageAttachBean messageAttachBean) throws ApplicationException{
    	return messageAttachDao.insert(messageAttachBean);
    }
    
    public void delete(MessageAttachBean messageAttachBean) throws ApplicationException{
    	messageAttachDao.delete(messageAttachBean);
    }
    
    public void update(MessageAttachBean messageAttachBean) throws ApplicationException{
    	messageAttachDao.update(messageAttachBean);
    }
    
    public MessageAttachBean findById(Integer id) throws ApplicationException{
    	return messageAttachDao.findById(id);
    }
}
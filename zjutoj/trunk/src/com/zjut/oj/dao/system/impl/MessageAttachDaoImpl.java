package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.system.MessageAttachBean;
import com.zjut.oj.dao.system.MessageAttachDao;
import com.zjut.oj.common.DaoSupport;

@Repository("messageAttachDao")
public class MessageAttachDaoImpl extends DaoSupport<MessageAttachBean> implements MessageAttachDao
{
}

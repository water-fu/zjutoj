package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.system.MessageInfoBean;
import com.zjut.oj.dao.system.MessageInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("messageInfoDao")
public class MessageInfoDaoImpl extends DaoSupport<MessageInfoBean> implements MessageInfoDao
{
}

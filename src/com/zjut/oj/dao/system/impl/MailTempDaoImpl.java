package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.system.MailTempBean;
import com.zjut.oj.dao.system.MailTempDao;
import com.zjut.oj.common.DaoSupport;

@Repository("mailTempDao")
public class MailTempDaoImpl extends DaoSupport<MailTempBean> implements MailTempDao
{
}

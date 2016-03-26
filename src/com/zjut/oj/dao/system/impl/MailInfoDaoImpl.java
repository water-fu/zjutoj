package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.dao.system.MailInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("mailInfoDao")
public class MailInfoDaoImpl extends DaoSupport<MailInfoBean> implements MailInfoDao
{
	@Override
	public void evict(MailInfoBean mailInfoBean) throws ApplicationException {
		getHibernateTemplate().evict(mailInfoBean);
	}
}

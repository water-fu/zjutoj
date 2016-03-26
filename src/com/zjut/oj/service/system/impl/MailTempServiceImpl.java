package com.zjut.oj.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.system.MailTempBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.service.system.MailTempService;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("mailTempService")
@Transactional
public class MailTempServiceImpl extends ServiceSupport implements MailTempService
{
	@Override
	public int insert(MailTempBean mailTempBean) throws ApplicationException{
		return mailTempDao.insert(mailTempBean);
	}

	@Override
	public void delete(MailTempBean mailTempBean) throws ApplicationException{
		mailTempDao.delete(mailTempBean);
	}

	@Override
	public void update(MailTempBean mailTempBean) throws ApplicationException{
		mailTempDao.update(mailTempBean);
	}

	@Override
	public MailTempBean findById(Integer id) throws ApplicationException{
		return mailTempDao.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MailTempBean> queryForList(MailTempBean mailTempBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(MailTempBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<MailTempBean> list = this.mailTempDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(MailTempBean mailTempBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(MailTempBean.class, "a");
		pageQueryResult = this.mailTempDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
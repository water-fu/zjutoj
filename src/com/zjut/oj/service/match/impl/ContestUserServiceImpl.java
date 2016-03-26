package com.zjut.oj.service.match.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.entity.match.ContestUserBean;
import com.zjut.oj.service.match.ContestUserService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@SuppressWarnings("unchecked")
@Service("contestUserService")
@Transactional
public class ContestUserServiceImpl extends ServiceSupport implements ContestUserService
{
	@Override
	public int insert(ContestUserBean contestUserBean) throws ApplicationException{
		return contestUserDao.insert(contestUserBean);
	}

	@Override
	public void delete(ContestUserBean contestUserBean) throws ApplicationException{
		QueryHelper queryHelper = new QueryHelper(ContestUserBean.class, "a");
		queryHelper.addCondition("a.contestId=?", contestUserBean.getContestId());
		queryHelper.addCondition("a.userInfo=?", contestUserBean.getUserInfo());
		List<ContestUserBean> list = contestUserDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("取消的竞赛用户不存在，请刷新页面后再试");
		}
		ContestUserBean contestUserBeanData = list.get(0);

		contestUserDao.delete(contestUserBeanData);
	}

	@Override
	public void update(ContestUserBean contestUserBean) throws ApplicationException{
		contestUserDao.update(contestUserBean);
	}

	@Override
	public ContestUserBean findById(Integer id) throws ApplicationException{
		return contestUserDao.findById(id);
	}

	@Override
	public List<ContestUserBean> queryForList(ContestUserBean contestUserBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ContestUserBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<ContestUserBean> list = this.contestUserDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(ContestUserBean contestUserBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ContestUserBean.class, "a");
		pageQueryResult = this.contestUserDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
package com.zjut.oj.service.match.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.match.ContestProblemBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.service.match.ContestProblemService;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@SuppressWarnings("unchecked")
@Service("contestProblemService")
@Transactional
public class ContestProblemServiceImpl extends ServiceSupport implements ContestProblemService
{
	@Override
	public int insert(ContestProblemBean contestProblemBean) throws ApplicationException{
		return contestProblemDao.insert(contestProblemBean);
	}

	@Override
	public void delete(ContestProblemBean contestProblemBean) throws ApplicationException{
		QueryHelper queryHelper = new QueryHelper(ContestProblemBean.class, "a");
		queryHelper.addCondition("a.contestId=?", contestProblemBean.getContestId());
		queryHelper.addCondition("a.problemInfoBean=?", contestProblemBean.getProblemInfoBean());
		List<ContestProblemBean> list = contestProblemDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("取消的竞赛试题不存在，请刷新页面后再试");
		}
		ContestProblemBean contestProblemBeanData = list.get(0);

		contestProblemDao.delete(contestProblemBeanData);
	}

	@Override
	public void update(ContestProblemBean contestProblemBean) throws ApplicationException{
		contestProblemDao.update(contestProblemBean);
	}

	@Override
	public ContestProblemBean findById(Integer id) throws ApplicationException{
		return contestProblemDao.findById(id);
	}

	@Override
	public List<ContestProblemBean> queryForList(ContestProblemBean contestProblemBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ContestProblemBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<ContestProblemBean> list = this.contestProblemDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(ContestProblemBean contestProblemBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ContestProblemBean.class, "a");
		pageQueryResult = this.contestProblemDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
package com.zjut.oj.service.match.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.match.ContestInfoBean;
import com.zjut.oj.entity.match.ContestProblemBean;
import com.zjut.oj.entity.match.ContestUserBean;
import com.zjut.oj.entity.match.LangInfoBean;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.service.match.ContestInfoService;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@SuppressWarnings("unchecked")
@Service("contestInfoService")
@Transactional
public class ContestInfoServiceImpl extends ServiceSupport implements ContestInfoService
{
	@Override
	public int insert(ContestInfoBean contestInfoBean) throws ApplicationException{
		QueryHelper queryHelper = new QueryHelper(ContestInfoBean.class, "a");
		queryHelper.addCondition("contestTitle = ?", contestInfoBean.getContestTitle());
		List<ContestInfoBean> list = contestInfoDao.queryForList(queryHelper);
		if(!list.isEmpty()) {
			throw new ApplicationException("竞赛标题已经存在");
		}

		int contestId = contestInfoDao.insert(contestInfoBean);

		for(LangInfoBean langInfoBean : contestInfoBean.getLangInfos()) {
			langInfoBean.setContestId(contestId);
			this.langInfoDao.insert(langInfoBean);
		}

		return contestId;
	}

	@Override
	public void delete(ContestInfoBean contestInfoBean) throws ApplicationException{
		ContestInfoBean contestInfoBeanData = contestInfoDao.findById(contestInfoBean.getContestId());

		//删除竞赛题目
		List<ContestProblemBean> contestProblemBeans = new ArrayList<ContestProblemBean>(contestInfoBeanData.getProblemInfos());
		for (ContestProblemBean contestProblemBean : contestProblemBeans) {
			contestProblemDao.delete(contestProblemBean);
		}
		//删除竞赛用户
		List<ContestUserBean> contestUserBeans = new ArrayList<ContestUserBean>(contestInfoBeanData.getUserInfos());
		for (ContestUserBean contestUserBean : contestUserBeans) {
			contestUserDao.delete(contestUserBean);
		}
		//删除竞赛语言
		List<LangInfoBean> langInfoBeans = new ArrayList<LangInfoBean>(contestInfoBeanData.getLangInfos());
		for (LangInfoBean langInfoBean : langInfoBeans) {
			langInfoDao.delete(langInfoBean);
		}
		//删除竞赛信息
		contestInfoDao.delete(contestInfoBeanData);
	}

	@Override
	public void update(ContestInfoBean contestInfoBean) throws ApplicationException{
		QueryHelper queryHelper = new QueryHelper(ContestInfoBean.class, "a");
		queryHelper.addCondition("contestTitle = ?", contestInfoBean.getContestTitle());
		queryHelper.addCondition("contestId != ?", contestInfoBean.getContestId());
		List<ContestInfoBean> list = contestInfoDao.queryForList(queryHelper);
		if(!list.isEmpty()) {
			throw new ApplicationException("竞赛标题已经存在");
		}

		ContestInfoBean contestInfoBeanData = contestInfoDao.findById(contestInfoBean.getContestId());
		//删除竞赛语言
		List<LangInfoBean> langInfoBeans = new ArrayList<LangInfoBean>(contestInfoBeanData.getLangInfos());
		for (LangInfoBean langInfoBean : langInfoBeans) {
			langInfoDao.delete(langInfoBean);
		}

		for(LangInfoBean langInfoBean : contestInfoBean.getLangInfos()) {
			langInfoBean.setContestId(contestInfoBeanData.getContestId());
			this.langInfoDao.insert(langInfoBean);
		}
		contestInfoBeanData.setContestTitle(contestInfoBean.getContestTitle());
		contestInfoBeanData.setContestLevel(contestInfoBean.getContestLevel());
		contestInfoBeanData.setContestDesc(contestInfoBean.getContestDesc());
		contestInfoBeanData.setStartTime(contestInfoBean.getStartTime());
		contestInfoBeanData.setEndTime(contestInfoBean.getEndTime());

		contestInfoDao.update(contestInfoBeanData);
	}

	@Override
	public ContestInfoBean findById(Integer id) throws ApplicationException{
		return contestInfoDao.findById(id);
	}

	@Override
	public List<ContestInfoBean> queryForList(ContestInfoBean contestInfoBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ContestInfoBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<ContestInfoBean> list = this.contestInfoDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(ContestInfoBean contestInfoBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ContestInfoBean.class, "a");
		pageQueryResult = this.contestInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	@Override
	public void status(ContestInfoBean contestInfoBean) throws ApplicationException {
		//校验竞赛没有试题不允许启用
		QueryHelper queryHelper = new QueryHelper(ContestProblemBean.class, "a");
		queryHelper.addCondition("contestId = ?", contestInfoBean.getContestId());
		List<ContestProblemBean> list = contestProblemDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("竞赛中试题列表为空，无法启用");
		}

		ContestInfoBean contestInfoBeanData = this.contestInfoDao.findById(contestInfoBean.getContestId());
		if(contestInfoBeanData.getContestStatus() == SystemConstantType.CONTEST_STATUS_YES) {
			contestInfoBeanData.setContestStatus(SystemConstantType.CONTEST_STATUS_NO);
		}
		else if(contestInfoBeanData.getContestStatus() == SystemConstantType.CONTEST_STATUS_NO) {
			contestInfoBeanData.setContestStatus(SystemConstantType.CONTEST_STATUS_YES);
		}
		this.contestInfoDao.update(contestInfoBeanData);
	}

	@Override
	public PageQueryResult queryProblemForPage(ProblemInfoBean problemInfoBean,
											   PageQueryResult pageQueryResult, HttpServletRequest request) throws ApplicationException {
		String contestId = request.getParameter("contestId");
		if(contestId == null || "".equals(contestId)) {
			throw new ApplicationException("竞赛编号为空，请联系系统管理员");
		}
		String isSelected = request.getParameter("selected");
		if(isSelected == null && "".equals(isSelected)) {
			isSelected = "0";
		}

		QueryHelper queryHelper = new QueryHelper(ProblemInfoBean.class, "a");
		StringBuffer sb = new StringBuffer();
		if(isSelected.equals("0")) {
			sb.append(" not ");
		}
		sb.append(" exists (from ContestProblemBean b where b.problemInfoBean = a and b.contestId = ?)");
		queryHelper.addCondition(sb.toString(), Integer.valueOf(contestId));
		queryHelper.addCondition("isShow = ?", SystemConstantType.IS_SHOW_YES);
		queryHelper.addCondition(problemInfoBean.getProblemTitle() != null, "problemTitle like ?", "%"+problemInfoBean.getProblemTitle()+"%");
		queryHelper.addCondition(problemInfoBean.getProblemId() != 0, "problemId = ?", problemInfoBean.getProblemId());
		queryHelper.addCondition(problemInfoBean.getProblemType() != null, "problemType like ?", "%"+problemInfoBean.getProblemType()+"%");
		queryHelper.addOrderProperty("problemId", true);
		pageQueryResult = this.problemInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	@Override
	public PageQueryResult queryUserForPage(UserInfoBean userInfoBean,
											PageQueryResult pageQueryResult, HttpServletRequest request) throws ApplicationException {
		String contestId = request.getParameter("contestId");
		if(contestId == null || "".equals(contestId)) {
			throw new ApplicationException("竞赛编号为空，请联系系统管理员");
		}
		String isSelected = request.getParameter("selected");
		if(isSelected == null && "".equals(isSelected)) {
			isSelected = "0";
		}

		QueryHelper queryHelper = new QueryHelper(UserInfoBean.class, "a");
		StringBuffer sb = new StringBuffer();
		if(isSelected.equals("0")) {
			sb.append(" not ");
		}
		sb.append(" exists (from ContestUserBean b where b.userInfo = a and b.contestId = ?)");
		queryHelper.addCondition(sb.toString(), Integer.valueOf(contestId));
		//queryHelper.addCondition("userType = ?", 1);
		queryHelper.addCondition(userInfoBean.getLoginSign() != null, "loginSign like ?", "%"+userInfoBean.getLoginSign()+"%");
		queryHelper.addOrderProperty("userId", true);
		pageQueryResult = this.userInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
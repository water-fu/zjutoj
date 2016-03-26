package com.zjut.oj.service.study.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.cache.TypeInfoCache;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.study.ProblemContentBean;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.entity.study.TypeInfoBean;
import com.zjut.oj.service.study.ProblemInfoService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("problemInfoService")
@Transactional
public class ProblemInfoServiceImpl extends ServiceSupport implements ProblemInfoService
{
	@SuppressWarnings("unchecked")
	@Override
	public int insert(ProblemInfoBean problemInfoBean) throws ApplicationException{
		QueryHelper queryHelper = new QueryHelper(ProblemInfoBean.class, "a");
		queryHelper.addCondition("problemTitle=?", problemInfoBean.getProblemTitle());
		List<ProblemInfoBean> list = this.problemInfoDao.queryForList(queryHelper);
		if(!list.isEmpty()) {
			throw new ApplicationException("试题标题已经存在");
		}
		problemInfoBean.setProblemLevel("");
		problemInfoBean.setIsShow(SystemConstantType.IS_SHOW_NOT);

		//保存试题内容
		int problemId = problemInfoDao.insert(problemInfoBean);
		ProblemContentBean problemContentBean = problemInfoBean.getProblemContentBean();
		problemContentBean.setProblemId(problemId);
		this.problemContentDao.insert(problemContentBean);

		//保存试题类型
		QueryHelper typeQueryHelper = new QueryHelper(TypeInfoBean.class, "a");
		typeQueryHelper.addCondition("typeName = ?", problemInfoBean.getProblemType());
		List<TypeInfoBean> typeInfoList = this.typeInfoDao.queryForList(typeQueryHelper);
		if(typeInfoList.isEmpty()) {
			TypeInfoBean typeInfoBean = new TypeInfoBean();
			typeInfoBean.setTypeName(problemInfoBean.getProblemType());
			int typeId = this.typeInfoDao.insert(typeInfoBean);
			TypeInfoCache.getInstance().put(typeId, typeInfoBean.getTypeName());
		}

		return problemId;
	}

	@Override
	public void delete(ProblemInfoBean problemInfoBean) throws ApplicationException{
		ProblemInfoBean problemInfoBeanData = this.problemInfoDao.findById(problemInfoBean.getProblemId());
		if(problemInfoBeanData.getProblemId() == 0) {
			throw new ApplicationException("删除的题目不存在");
		}
		problemInfoDao.delete(problemInfoBeanData);
		ProblemContentBean problemContentBean = this.problemContentDao.findById(problemInfoBean.getProblemId());
		if(problemContentBean.getProblemId() != 0) {
			this.problemContentDao.delete(problemContentBean);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(ProblemInfoBean problemInfoBean) throws ApplicationException{
		ProblemInfoBean problemInfoBeanData = this.problemInfoDao.findById(problemInfoBean.getProblemId());
		problemInfoBeanData.setProblemTitle(problemInfoBean.getProblemTitle());
		problemInfoBeanData.setTimeLimit(problemInfoBean.getTimeLimit());
		problemInfoBeanData.setMemLimit(problemInfoBean.getMemLimit());
		problemInfoBeanData.setProblemType(problemInfoBean.getProblemType());

		ProblemContentBean problemContentBeanData = problemInfoBeanData.getProblemContentBean();
		problemContentBeanData.setProblemDesc(problemInfoBean.getProblemContentBean().getProblemDesc());
		problemContentBeanData.setProblemInput(problemInfoBean.getProblemContentBean().getProblemInput());
		problemContentBeanData.setProblemOutput(problemInfoBean.getProblemContentBean().getProblemOutput());
		problemContentBeanData.setExampleInput(problemInfoBean.getProblemContentBean().getExampleInput());
		problemContentBeanData.setExampleOutput(problemInfoBean.getProblemContentBean().getExampleOutput());

		//保存试题信息
		problemInfoDao.update(problemInfoBeanData);
		//保存试题内容
		problemContentDao.update(problemContentBeanData);

		//保存试题类型
		QueryHelper typeQueryHelper = new QueryHelper(TypeInfoBean.class, "a");
		typeQueryHelper.addCondition("typeName = ?", problemInfoBean.getProblemType());
		List<TypeInfoBean> typeInfoList = this.typeInfoDao.queryForList(typeQueryHelper);
		if(typeInfoList.isEmpty()) {
			TypeInfoBean typeInfoBean = new TypeInfoBean();
			typeInfoBean.setTypeName(problemInfoBean.getProblemType());
			int typeId = this.typeInfoDao.insert(typeInfoBean);
			TypeInfoCache.getInstance().put(typeId, typeInfoBean.getTypeName());
		}
	}

	@Override
	public ProblemInfoBean findById(Integer id) throws ApplicationException{
		return problemInfoDao.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProblemInfoBean> queryForList(ProblemInfoBean problemInfoBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ProblemInfoBean.class, "a");
		queryHelper.addCondition("isShow=?", 1);
		queryHelper.addOrderProperty("operDate", false);
		List<ProblemInfoBean> list = this.problemInfoDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(ProblemInfoBean problemInfoBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ProblemInfoBean.class, "a");
		queryHelper.addCondition(problemInfoBean.getIsShow() != -1, "isShow = ?", problemInfoBean.getIsShow());
		queryHelper.addCondition(problemInfoBean.getProblemTitle() != null, "problemTitle like ?", "%"+problemInfoBean.getProblemTitle()+"%");
		queryHelper.addCondition(problemInfoBean.getProblemId() != 0, "problemId = ?", problemInfoBean.getProblemId());
		queryHelper.addCondition(problemInfoBean.getProblemType() != null, "problemType like ?", "%"+problemInfoBean.getProblemType()+"%");
		queryHelper.addOrderProperty("problemId", true);
		pageQueryResult = this.problemInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
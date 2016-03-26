package com.zjut.oj.service.study.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.study.AnswerInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.service.study.AnswerInfoService;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("answerInfoService")
@Transactional
public class AnswerInfoServiceImpl extends ServiceSupport implements AnswerInfoService
{
	@Override
	public int insert(AnswerInfoBean answerInfoBean) throws ApplicationException{
		//试题默认是不显示 
		answerInfoBean.setIsShow(SystemConstantType.IS_SHOW_NOT);
		return answerInfoDao.insert(answerInfoBean);
	}

	@Override
	public void delete(AnswerInfoBean answerInfoBean) throws ApplicationException{
		answerInfoBean = answerInfoDao.findById(answerInfoBean.getAnswerId());
		if(answerInfoBean.getIsShow() == SystemConstantType.IS_SHOW_YES) {
			throw new ApplicationException("题解信息已经审核通过，不能删除");
		}
		answerInfoDao.delete(answerInfoBean);
	}

	@Override
	public void update(AnswerInfoBean answerInfoBean) throws ApplicationException{
		AnswerInfoBean answerInfoBeanData = answerInfoDao.findById(answerInfoBean.getAnswerId());
		answerInfoBeanData.setProblemInfoBean(answerInfoBean.getProblemInfoBean());
		answerInfoBeanData.setAnswerDesc(answerInfoBean.getAnswerDesc());
		answerInfoDao.update(answerInfoBeanData);
	}

	@Override
	public AnswerInfoBean findById(Integer id) throws ApplicationException{
		return answerInfoDao.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AnswerInfoBean> queryForList(AnswerInfoBean answerInfoBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(AnswerInfoBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<AnswerInfoBean> list = this.answerInfoDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(AnswerInfoBean answerInfoBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(AnswerInfoBean.class, "a");
		if(answerInfoBean.getUserInfoBean() != null) {
			queryHelper.addCondition("userInfoBean.userId=?", answerInfoBean.getUserInfoBean().getUserId());
		}else {
			queryHelper.addCondition(answerInfoBean.getIsShow() != -1, "isShow=?", answerInfoBean.getIsShow());
		}
		pageQueryResult = this.answerInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	@Override
	public void show(AnswerInfoBean answerInfoBean) throws ApplicationException {
		AnswerInfoBean answerInfoBeanData = answerInfoDao.findById(answerInfoBean.getAnswerId());

		answerInfoBeanData.setIsShow(answerInfoBean.getIsShow());
		answerInfoDao.update(answerInfoBeanData);
	}
}
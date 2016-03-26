package com.zjut.oj.service.study.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.study.ProblemContentBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.service.study.ProblemContentService;
import com.zjut.oj.util.QueryHelper;

@Service("problemContentService")
@Transactional
public class ProblemContentServiceImpl extends ServiceSupport implements ProblemContentService
{
	@Override
    public int insert(ProblemContentBean problemContentBean) throws ApplicationException{
    	return problemContentDao.insert(problemContentBean);
    }
    
    @Override
    public void delete(ProblemContentBean problemContentBean) throws ApplicationException{
    	problemContentDao.delete(problemContentBean);
    }
    
    @Override
    public void update(ProblemContentBean problemContentBean) throws ApplicationException{
    	problemContentDao.update(problemContentBean);
    }
    
    @Override
    public ProblemContentBean findById(Integer id) throws ApplicationException{
    	return problemContentDao.findById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<ProblemContentBean> queryForList(ProblemContentBean problemContentBean) throws ApplicationException {
    	QueryHelper queryHelper = new QueryHelper(ProblemContentBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<ProblemContentBean> list = this.problemContentDao.queryForList(queryHelper);
		return list;
    }
}
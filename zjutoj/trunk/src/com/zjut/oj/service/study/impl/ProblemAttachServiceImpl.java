package com.zjut.oj.service.study.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.study.ProblemAttachBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.service.study.ProblemAttachService;
import com.zjut.oj.util.FileUtil;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("problemAttachService")
@Transactional
public class ProblemAttachServiceImpl extends ServiceSupport implements ProblemAttachService
{
	@Override
    public int insert(ProblemAttachBean problemAttachBean) throws ApplicationException{
    	return problemAttachDao.insert(problemAttachBean);
    }
	
	@Override
	public void insert(List<ProblemAttachBean> problemAttachBeans) throws ApplicationException {
		String baseurl = SystemParamCache.getInstance().get("baseurl").toString();
		for(ProblemAttachBean problemAttachBean : problemAttachBeans) {
			String url = FileUtil.fileUpdate(problemAttachBean.getAttachFile(), problemAttachBean.getProblemId()+"", 
					problemAttachBean.getAttachName(), "problem/"+problemAttachBean.getProblemId(), baseurl);
			problemAttachBean.setAttachUrl(url);
			problemAttachDao.insert(problemAttachBean);
		}
	}
    
    @Override
    public void delete(ProblemAttachBean problemAttachBean) throws ApplicationException{
    	problemAttachDao.delete(problemAttachBean);
    }
    
    @Override
    public void update(ProblemAttachBean problemAttachBean) throws ApplicationException{
    	problemAttachDao.update(problemAttachBean);
    }
    
    @Override
    public ProblemAttachBean findById(Integer id) throws ApplicationException{
    	return problemAttachDao.findById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<ProblemAttachBean> queryForList(ProblemAttachBean problemAttachBean) throws ApplicationException {
    	QueryHelper queryHelper = new QueryHelper(ProblemAttachBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<ProblemAttachBean> list = this.problemAttachDao.queryForList(queryHelper);
		return list;
    }
    
    @Override
	public PageQueryResult queryForPage(ProblemAttachBean problemAttachBean,
			PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(ProblemAttachBean.class, "a");
		pageQueryResult = this.problemAttachDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
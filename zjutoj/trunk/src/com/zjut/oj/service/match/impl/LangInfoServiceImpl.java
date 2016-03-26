package com.zjut.oj.service.match.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.match.LangInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.service.match.LangInfoService;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("langInfoService")
@Transactional
public class LangInfoServiceImpl extends ServiceSupport implements LangInfoService
{
	@Override
    public int insert(LangInfoBean langInfoBean) throws ApplicationException{
    	return langInfoDao.insert(langInfoBean);
    }
    
    @Override
    public void delete(LangInfoBean langInfoBean) throws ApplicationException{
    	langInfoDao.delete(langInfoBean);
    }
    
    @Override
    public void update(LangInfoBean langInfoBean) throws ApplicationException{
    	langInfoDao.update(langInfoBean);
    }
    
    @Override
    public LangInfoBean findById(Integer id) throws ApplicationException{
    	return langInfoDao.findById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<LangInfoBean> queryForList(LangInfoBean langInfoBean) throws ApplicationException {
    	QueryHelper queryHelper = new QueryHelper(LangInfoBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<LangInfoBean> list = this.langInfoDao.queryForList(queryHelper);
		return list;
    }
    
    @Override
	public PageQueryResult queryForPage(LangInfoBean langInfoBean,
			PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(LangInfoBean.class, "a");
		pageQueryResult = this.langInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
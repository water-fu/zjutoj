package com.zjut.oj.service.study.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.study.TypeInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.service.study.TypeInfoService;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("typeInfoService")
@Transactional
public class TypeInfoServiceImpl extends ServiceSupport implements TypeInfoService
{
	@Override
    public int insert(TypeInfoBean typeInfoBean) throws ApplicationException{
    	return typeInfoDao.insert(typeInfoBean);
    }
    
    @Override
    public void delete(TypeInfoBean typeInfoBean) throws ApplicationException{
    	typeInfoDao.delete(typeInfoBean);
    }
    
    @Override
    public void update(TypeInfoBean typeInfoBean) throws ApplicationException{
    	typeInfoDao.update(typeInfoBean);
    }
    
    @Override
    public TypeInfoBean findById(Integer id) throws ApplicationException{
    	return typeInfoDao.findById(id);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<TypeInfoBean> queryForList(TypeInfoBean typeInfoBean) throws ApplicationException {
    	QueryHelper queryHelper = new QueryHelper(TypeInfoBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<TypeInfoBean> list = this.typeInfoDao.queryForList(queryHelper);
		return list;
    }
    
    @Override
	public PageQueryResult queryForPage(TypeInfoBean typeInfoBean,
			PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(TypeInfoBean.class, "a");
		pageQueryResult = this.typeInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}
}
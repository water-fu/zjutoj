package com.zjut.oj.service.study;

import java.util.List;

import com.zjut.oj.entity.study.TypeInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface TypeInfoService
{
    int insert(TypeInfoBean typeInfoBean) throws ApplicationException;
    
    void delete(TypeInfoBean typeInfoBean) throws ApplicationException;
    
    void update(TypeInfoBean typeInfoBean) throws ApplicationException;
    
    TypeInfoBean findById(Integer id) throws ApplicationException;
    
    List<TypeInfoBean> queryForList(TypeInfoBean typeInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(TypeInfoBean typeInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
}
package com.zjut.oj.service.system;

import java.util.List;

import com.zjut.oj.entity.system.HustojInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface HustojInfoService
{
    int insert(HustojInfoBean hustojInfoBean) throws ApplicationException;
    
    void delete(HustojInfoBean hustojInfoBean) throws ApplicationException;
    
    void update(HustojInfoBean hustojInfoBean) throws ApplicationException;
    
    HustojInfoBean findById(Integer id) throws ApplicationException;
    
    List<HustojInfoBean> queryForList(HustojInfoBean hustojInfoBean) throws ApplicationException;
    
    PageQueryResult queryForPage(HustojInfoBean hustojInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
    
    HustojInfoBean matchSign(String mid) throws ApplicationException;
}
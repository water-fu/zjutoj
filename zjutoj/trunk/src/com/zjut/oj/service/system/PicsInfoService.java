package com.zjut.oj.service.system;

import java.util.List;

import com.zjut.oj.entity.system.PicsInfoBean;
import com.zjut.oj.util.ApplicationException;

public interface PicsInfoService
{
    void insert(List<PicsInfoBean> picsInfoList, String deleteAttachIds) throws ApplicationException;
    
    void delete(PicsInfoBean picsInfoBean) throws ApplicationException;
    
    void update(PicsInfoBean picsInfoBean) throws ApplicationException;
    
    PicsInfoBean findById(Integer id) throws ApplicationException;
    
    List<PicsInfoBean> queryForList(PicsInfoBean picsInfoBean) throws ApplicationException;
}
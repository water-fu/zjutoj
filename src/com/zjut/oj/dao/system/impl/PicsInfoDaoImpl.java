package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.system.PicsInfoBean;
import com.zjut.oj.dao.system.PicsInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("picsInfoDao")
public class PicsInfoDaoImpl extends DaoSupport<PicsInfoBean> implements PicsInfoDao
{
}

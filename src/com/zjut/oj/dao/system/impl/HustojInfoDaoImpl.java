package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.system.HustojInfoBean;
import com.zjut.oj.dao.system.HustojInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("hustojInfoDao")
public class HustojInfoDaoImpl extends DaoSupport<HustojInfoBean> implements HustojInfoDao
{
}

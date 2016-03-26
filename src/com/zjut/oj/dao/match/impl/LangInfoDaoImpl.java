package com.zjut.oj.dao.match.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.match.LangInfoBean;
import com.zjut.oj.dao.match.LangInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("langInfoDao")
public class LangInfoDaoImpl extends DaoSupport<LangInfoBean> implements LangInfoDao
{
}

package com.zjut.oj.dao.study.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.dao.study.ProblemInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("problemInfoDao")
public class ProblemInfoDaoImpl extends DaoSupport<ProblemInfoBean> implements ProblemInfoDao
{
}

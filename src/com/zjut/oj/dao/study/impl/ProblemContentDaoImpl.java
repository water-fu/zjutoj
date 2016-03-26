package com.zjut.oj.dao.study.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.study.ProblemContentBean;
import com.zjut.oj.dao.study.ProblemContentDao;
import com.zjut.oj.common.DaoSupport;

@Repository("problemContentDao")
public class ProblemContentDaoImpl extends DaoSupport<ProblemContentBean> implements ProblemContentDao
{
}

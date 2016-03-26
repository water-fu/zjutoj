package com.zjut.oj.dao.match.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.match.ContestProblemBean;
import com.zjut.oj.dao.match.ContestProblemDao;
import com.zjut.oj.common.DaoSupport;

@Repository("contestProblemDao")
public class ContestProblemDaoImpl extends DaoSupport<ContestProblemBean> implements ContestProblemDao
{
}

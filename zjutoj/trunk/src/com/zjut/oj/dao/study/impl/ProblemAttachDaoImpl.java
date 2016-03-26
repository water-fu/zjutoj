package com.zjut.oj.dao.study.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.study.ProblemAttachBean;
import com.zjut.oj.dao.study.ProblemAttachDao;
import com.zjut.oj.common.DaoSupport;

@Repository("problemAttachDao")
public class ProblemAttachDaoImpl extends DaoSupport<ProblemAttachBean> implements ProblemAttachDao
{
}

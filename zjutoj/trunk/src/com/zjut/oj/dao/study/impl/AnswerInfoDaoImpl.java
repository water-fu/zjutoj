package com.zjut.oj.dao.study.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.study.AnswerInfoBean;
import com.zjut.oj.dao.study.AnswerInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("answerInfoDao")
public class AnswerInfoDaoImpl extends DaoSupport<AnswerInfoBean> implements AnswerInfoDao
{
}

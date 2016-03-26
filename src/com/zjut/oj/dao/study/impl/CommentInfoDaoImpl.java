package com.zjut.oj.dao.study.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.study.CommentInfoBean;
import com.zjut.oj.dao.study.CommentInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("commentInfoDao")
public class CommentInfoDaoImpl extends DaoSupport<CommentInfoBean> implements CommentInfoDao
{
}

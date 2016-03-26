package com.zjut.oj.dao.study.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.study.TypeInfoBean;
import com.zjut.oj.dao.study.TypeInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("typeInfoDao")
public class TypeInfoDaoImpl extends DaoSupport<TypeInfoBean> implements TypeInfoDao
{
}

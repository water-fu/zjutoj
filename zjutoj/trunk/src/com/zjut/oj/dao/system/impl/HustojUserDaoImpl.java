package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.system.HustojUserBean;
import com.zjut.oj.dao.system.HustojUserDao;
import com.zjut.oj.common.DaoSupport;

@Repository("hustojUserDao")
public class HustojUserDaoImpl extends DaoSupport<HustojUserBean> implements HustojUserDao
{
}

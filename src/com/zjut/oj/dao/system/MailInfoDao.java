package com.zjut.oj.dao.system;

import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.common.IDaoSupport;

public interface MailInfoDao extends IDaoSupport<MailInfoBean>
{
	void evict(MailInfoBean mailInfoBean) throws ApplicationException;
}

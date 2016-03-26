/**
 * Tianque.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.zjut.oj.service.system.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SysContext;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.HustojUserBean;
import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.entity.system.MailTempBean;
import com.zjut.oj.service.system.HustojFinalUserService;
import com.zjut.oj.service.system.MailInfoService;
import com.zjut.oj.thread.SendMailThread;
import com.zjut.oj.thread.ThreadPool;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.QueryHelper;

/**
 * 总体说明
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: HustojFinalUserServiceImpl.java,v 0.1 2015-3-26 下午8:30:55 Exp $
 */
@Service("hustojFinalUserService")
@Transactional
public class HustojFinalUserServiceImpl extends ServiceSupport implements HustojFinalUserService {

	@Resource
	private MailInfoService mailInfoService;

	@SuppressWarnings("unchecked")
	@Override
	public void importExcelDataSave(HttpServletRequest request, Object[] obj)
			throws ApplicationException {
		String matchId = request.getParameter("matchId");

		QueryHelper queryHelper = new QueryHelper();
		queryHelper.createClass(HustojUserBean.class, "a");
//		queryHelper.addCondition("a.matchAccount=?", obj[1].toString());
		queryHelper.addCondition("a.userNo=?", obj[0].toString());
		queryHelper.addCondition("a.matchId=?", Integer.parseInt(matchId));
		List<HustojUserBean> list = hustojUserDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			return;
//			throw new ApplicationException("无此账号数据");
		}

		HustojUserBean hustojUserBeanData = list.get(0);

		// 邮件模版
		queryHelper = new QueryHelper();
		queryHelper.createClass(MailTempBean.class, "a");
		queryHelper.addCondition("a.tempType = ?", 3);
		List<MailTempBean> list1 = mailTempDao.queryForList(queryHelper);
		if(list1.isEmpty()) {
			throw new ApplicationException("请设置邮件模版");
		}
		MailTempBean mailTempBean = list1.get(0);

		// 插入邮件信息表
		MailInfoBean mailInfoBean = new MailInfoBean();
		mailInfoBean.setSendAccount(SystemParamCache.getInstance().get("mail_sendAccount").toString());
		mailInfoBean.setAcceptAccount(hustojUserBeanData.getUserMail());
		mailInfoBean.setMailTitle(mailTempBean.getTempTitle());
		mailInfoBean.setMailContent(mailTempBean.getTempContent());
		mailInfoBean.setHustojUserBean(hustojUserBeanData);
		mailInfoBean.setSendState(SystemConstantType.SEND_STATE_WAIT);

		int mailId = mailInfoDao.insert(mailInfoBean);
		mailInfoBean.setMailId(mailId);

		SendMailThread sendMailThread = new SendMailThread();
		// 启动发送邮件线程
		sendMailThread.init(mailInfoService, mailInfoBean, SysContext.getRequest() , 1);
		Thread thread = new Thread(sendMailThread);
		ThreadPool.getInstance().execute(thread);
	}
}

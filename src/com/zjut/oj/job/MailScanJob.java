/**
 * Tianque.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.zjut.oj.job;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.service.system.MailInfoService;
import com.zjut.oj.thread.SendMailThread;
import com.zjut.oj.thread.ThreadPool;
import com.zjut.oj.util.QueryHelper;
import com.zjut.oj.util.RequestUtil;

/**
 * 总体说明
 * 	对邮件表进行扫描
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: MailScanJob.java,v 0.1 2015-3-31 上午10:11:58 Exp $
 */
@Service("mailScanJob")
public class MailScanJob extends ServiceSupport {

	private final Logger logger = LoggerFactory.getLogger(MailScanJob.class);

	@Resource
	private MailInfoService mailInfoService;

	/**
	 * 发送邮件
	 */
	@SuppressWarnings("unchecked")
	public void mailSend() {
		logger.info("邮件发送扫描开始");

		QueryHelper queryHelper = new QueryHelper(MailInfoBean.class, "a");
		queryHelper.addCondition("a.sendState in (?, ?)", 0, 2);
		List<MailInfoBean> list = mailInfoDao.queryForList(queryHelper);



		// 遍历邮件表，并发送邮件
		for (MailInfoBean mailInfoBean : list) {
			SendMailThread sendMailThread = new SendMailThread();
			// 初始化是否成功，成功才进行发送
			boolean isInit = sendMailThread.init(mailInfoService, mailInfoBean, RequestUtil.getRequest(), 1);
			if(isInit) {
				Thread thread = new Thread(sendMailThread);
				ThreadPool.getInstance().execute(thread);
			}
		}
		logger.info("邮件发送扫描结束");
	}
}

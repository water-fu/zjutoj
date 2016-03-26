/**
 * Tianque.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.zjut.oj.thread;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjut.oj.common.SysContext;
import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.service.system.MailInfoService;

/**
 * 总体说明
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: SendMailThread.java,v 0.1 2015-3-18 下午8:57:08 Exp $
 */
public class SendMailThread implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(SendMailThread.class);

	// 保存正在运行的记录
	private static Map<Integer, MailInfoBean> threadMap = new HashMap<Integer, MailInfoBean>();

	private MailInfoService mailInfoService;
	private MailInfoBean mailInfoBean;
	private HttpServletRequest request;
	@SuppressWarnings("unused")
	private int sort = 0;

	/**
	 * 初始化线程参数，如果执行列表中正在发送，则不发送邮件，初始化失败
	 *
	 * @param mailInfoService
	 * @param mailInfoBean
	 * @param request
	 * @param sort
	 * @return
	 */
	public boolean init(MailInfoService mailInfoService, MailInfoBean mailInfoBean, HttpServletRequest request, int sort) {
		this.mailInfoService = mailInfoService;
		this.mailInfoBean = mailInfoBean;
		this.request = request;
		this.sort = sort;

		if(!threadMap.containsKey(mailInfoBean.getMailId())) {
			System.out.println("发送邮件主键" + mailInfoBean.getMailId());
			threadMap.put(mailInfoBean.getMailId(), mailInfoBean);
			return true;
		}

		return false;
	}

	/**
	 * 邮件发送线程启动
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(1000 * 60);
			initRequest();

			mailInfoService.transMail(mailInfoBean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// 执行完成清楚当前记录
		threadMap.remove(mailInfoBean.getMailId());
	}

	/**
	 * 初始化request
	 */
	private void initRequest() {
		SysContext.setRequest(request);
	}

}

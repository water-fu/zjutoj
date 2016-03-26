package com.zjut.oj.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;

import com.zjut.oj.dao.match.ContestInfoDao;
import com.zjut.oj.dao.match.ContestProblemDao;
import com.zjut.oj.dao.match.ContestUserDao;
import com.zjut.oj.dao.match.LangInfoDao;
import com.zjut.oj.dao.study.AnswerInfoDao;
import com.zjut.oj.dao.study.CommentInfoDao;
import com.zjut.oj.dao.study.ProblemAttachDao;
import com.zjut.oj.dao.study.ProblemContentDao;
import com.zjut.oj.dao.study.ProblemInfoDao;
import com.zjut.oj.dao.study.TypeInfoDao;
import com.zjut.oj.dao.system.HustojInfoDao;
import com.zjut.oj.dao.system.HustojUserDao;
import com.zjut.oj.dao.system.LoginInfoDao;
import com.zjut.oj.dao.system.MailInfoDao;
import com.zjut.oj.dao.system.MailTempDao;
import com.zjut.oj.dao.system.MessageAttachDao;
import com.zjut.oj.dao.system.MessageInfoDao;
import com.zjut.oj.dao.system.PicsInfoDao;
import com.zjut.oj.dao.system.UserInfoDao;
import com.zjut.oj.util.ApplicationException;


public class ServiceSupport {

	//邮件
	@Resource
	public JavaMailSender javaMailSender;

	//system模块
	@Resource
	public UserInfoDao userInfoDao;
	@Resource
	public MessageInfoDao messageInfoDao;
	@Resource
	public MessageAttachDao messageAttachDao;
	@Resource
	public PicsInfoDao picsInfoDao;
	@Resource
	public LoginInfoDao loginInfoDao;
	@Resource
	public TypeInfoDao typeInfoDao;
	@Resource
	public HustojUserDao hustojUserDao;
	@Resource
	public HustojInfoDao hustojInfoDao;
	@Resource
	public MailTempDao mailTempDao;
	@Resource
	public MailInfoDao mailInfoDao;

	//study模块
	@Resource
	public ProblemInfoDao problemInfoDao;
	@Resource
	public ProblemContentDao problemContentDao;
	@Resource
	public ProblemAttachDao problemAttachDao;
	@Resource
	public CommentInfoDao commentInfoDao;
	@Resource
	public AnswerInfoDao answerInfoDao;

	//match模块
	@Resource
	public ContestInfoDao contestInfoDao;
	@Resource
	public ContestProblemDao contestProblemDao;
	@Resource
	public ContestUserDao contestUserDao;
	@Resource
	public LangInfoDao langInfoDao;

	//excel数据导入
	public void importExcelDataSave(HttpServletRequest request, Object[] obj) throws ApplicationException {

	}
}

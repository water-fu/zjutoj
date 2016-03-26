package com.zjut.oj.service.system.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.DaoSupport;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SysContext;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.HustojInfoBean;
import com.zjut.oj.entity.system.HustojUserBean;
import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.entity.system.MailTempBean;
import com.zjut.oj.service.system.HustojUserService;
import com.zjut.oj.service.system.MailInfoService;
import com.zjut.oj.thread.SendMailThread;
import com.zjut.oj.thread.ThreadPool;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;
import com.zjut.oj.util.encrypt.EncryptFactory;
import com.zjut.oj.util.encrypt.IEncrypt;

@Service("hustojUserService")
@Transactional
public class HustojUserServiceImpl extends ServiceSupport implements HustojUserService
{
	@Resource
	private MailInfoService mailInfoService;
	private Log log = LogFactory.getLog(HustojUserServiceImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public int[] insert(HustojUserBean hustojUserBean) throws ApplicationException{

		//校验学号唯一性
		QueryHelper queryHelperOj = new QueryHelper();
		queryHelperOj.createClass(HustojUserBean.class, "a");
		queryHelperOj.addCondition("a.userNo=?", hustojUserBean.getUserNo());
		queryHelperOj.addCondition("a.matchId=?", hustojUserBean.getMatchId());
		List<HustojUserBean> listCon = hustojUserDao.queryForList(queryHelperOj);
		if(!listCon.isEmpty()) {
			throw new ApplicationException("该学号已经报名");
		}

		//校验邮箱唯一性
		queryHelperOj = new QueryHelper();
		queryHelperOj.createClass(HustojUserBean.class, "a");
		queryHelperOj.addCondition("a.userMail=?", hustojUserBean.getUserMail());
		queryHelperOj.addCondition("a.matchId=?", hustojUserBean.getMatchId());
		listCon = hustojUserDao.queryForList(queryHelperOj);
		if(!listCon.isEmpty()) {
			throw new ApplicationException("该邮箱已经报名");
		}

		QueryHelper queryHelper = new QueryHelper();
		queryHelper.createClass(MailTempBean.class, "a");
		queryHelper.addCondition("a.tempType = ?", 1);
		List<MailTempBean> list = mailTempDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("请设置邮件模版");
		}
		MailTempBean mailTempBean = list.get(0);

		//插入发送邮件内容
		MailInfoBean mailInfoBean = new MailInfoBean();

		String sendAccount = SystemParamCache.getInstance().get("mail_sendAccount").toString();
		mailInfoBean.setSendAccount(sendAccount);
		mailInfoBean.setAcceptAccount(hustojUserBean.getUserMail());
		mailInfoBean.setMailTitle(mailTempBean.getTempTitle());
		mailInfoBean.setMailContent(mailTempBean.getTempContent());
		mailInfoBean.setHustojUserBean(hustojUserBean);
		mailInfoBean.setSendState(SystemConstantType.SEND_STATE_WAIT);

		int userId = hustojUserDao.insert(hustojUserBean);
		hustojUserBean.setUserId(userId);
		int mailId = mailInfoDao.insert(mailInfoBean);

		return new int[]{userId, mailId};
	}

	@Override
	public void delete(HustojUserBean hustojUserBean) throws ApplicationException{
		hustojUserDao.delete(hustojUserBean);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int update(HustojUserBean hustojUserBean) throws ApplicationException{

		//校验学号唯一性
		QueryHelper queryHelperOj = new QueryHelper();
		queryHelperOj.createClass(HustojUserBean.class, "a");
		queryHelperOj.addCondition("a.userNo=?", hustojUserBean.getUserNo());
		queryHelperOj.addCondition("a.matchId=?", hustojUserBean.getMatchId());
		queryHelperOj.addCondition("a.userId!=?", hustojUserBean.getUserId());
		List<HustojUserBean> listCon = hustojUserDao.queryForList(queryHelperOj);
		if(!listCon.isEmpty()) {
			throw new ApplicationException("该学号已经报名");
		}

		//校验邮箱唯一性
		queryHelperOj = new QueryHelper();
		queryHelperOj.createClass(HustojUserBean.class, "a");
		queryHelperOj.addCondition("a.userMail=?", hustojUserBean.getUserMail());
		queryHelperOj.addCondition("a.matchId=?", hustojUserBean.getMatchId());
		queryHelperOj.addCondition("a.userId!=?", hustojUserBean.getUserId());
		listCon = hustojUserDao.queryForList(queryHelperOj);
		if(!listCon.isEmpty()) {
			throw new ApplicationException("该邮箱已经报名");
		}

		QueryHelper queryHelper = new QueryHelper();
		queryHelper.createClass(MailTempBean.class, "a");
		queryHelper.addCondition("a.tempType = ?", 1);
		List<MailTempBean> list = mailTempDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("请设置邮件模版");
		}
		MailTempBean mailTempBean = list.get(0);

		//插入发送邮件内容
		MailInfoBean mailInfoBean = new MailInfoBean();

		String sendAccount = SystemParamCache.getInstance().get("mail_sendAccount").toString();
		mailInfoBean.setSendAccount(sendAccount);
		mailInfoBean.setAcceptAccount(hustojUserBean.getUserMail());
		mailInfoBean.setMailTitle(mailTempBean.getTempTitle());
		mailInfoBean.setMailContent(mailTempBean.getTempContent());
		mailInfoBean.setHustojUserBean(hustojUserBean);

		int mailId = mailInfoDao.insert(mailInfoBean);

		HustojUserBean hustojUserBeanData = hustojUserDao.findById(hustojUserBean.getUserId());
		hustojUserBeanData.setUserMail(hustojUserBean.getUserMail());

		hustojUserDao.update(hustojUserBeanData);

		return mailId;
	}

	@Override
	public HustojUserBean findById(Integer id) throws ApplicationException{
		return hustojUserDao.findById(id);
	}

	/**
	 * Excel数据导入查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HustojUserBean> exportHustojUser(HustojUserBean hustojUserBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(HustojUserBean.class, "a");
		queryHelper.addCondition("a.matchId=?", hustojUserBean.getMatchId());
		queryHelper.addOrderProperty("userNo", true);
		queryHelper.addOrderProperty("userId", true);
		List<HustojUserBean> list = this.hustojUserDao.queryForList(queryHelper);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageQueryResult queryForPage(HustojUserBean hustojUserBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		// 验证该比赛是否存在
		QueryHelper queryHelper = new QueryHelper();
		queryHelper.createClass(HustojInfoBean.class, "a");
		queryHelper.addCondition("a.matchId=?", hustojUserBean.getMatchId());
		List<HustojInfoBean> list = this.hustojInfoDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("竞赛编号[" + hustojUserBean.getMatchId() + "]的竞赛不存在");
		}

		queryHelper = new QueryHelper(HustojUserBean.class, "a");
		queryHelper.addCondition("a.matchId = ?", hustojUserBean.getMatchId());
		queryHelper.addOrderProperty("userNo", true);
		queryHelper.addOrderProperty("userId", true);
		pageQueryResult = this.hustojUserDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	/**
	 * 重新发送邮件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int transMail(HustojUserBean hustojUserBean) throws ApplicationException {
		hustojUserBean = hustojUserDao.findById(hustojUserBean.getUserId());

		QueryHelper queryHelper = new QueryHelper();
		queryHelper.createClass(MailTempBean.class, "a");
		queryHelper.addCondition("a.tempType = ?", 1);
		List<MailTempBean> list = mailTempDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("请设置邮件模版");
		}
		MailTempBean mailTempBean = list.get(0);

		//插入发送邮件内容
		MailInfoBean mailInfoBean = new MailInfoBean();

		String sendAccount = SystemParamCache.getInstance().get("mail_sendAccount").toString();
		mailInfoBean.setSendAccount(sendAccount);
		mailInfoBean.setAcceptAccount(hustojUserBean.getUserMail());
		mailInfoBean.setMailTitle(mailTempBean.getTempTitle());
		mailInfoBean.setMailContent(mailTempBean.getTempContent());
		mailInfoBean.setHustojUserBean(hustojUserBean);

		int mailId = mailInfoDao.insert(mailInfoBean);

		return mailId;
	}

	/**
	 * 数据导入
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void importExcelDataSave(HttpServletRequest request, Object[] obj)
			throws ApplicationException {
		HustojUserBean hustojUserBean = (HustojUserBean) obj[0];
		String matchId = request.getParameter("matchId");
		if(matchId == null && "".equals(matchId)) {
			throw new ApplicationException("竞赛编号为空");
		}

		QueryHelper queryHelper = new QueryHelper();
		queryHelper.createClass(HustojUserBean.class, "a");
		queryHelper.addCondition("a.userNo=?", hustojUserBean.getUserNo());
		queryHelper.addCondition("a.matchId=?", matchId);
		List<HustojUserBean> list = hustojUserDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("无此学号数据");
		}

		HustojUserBean hustojUserBeanData = list.get(0);
		hustojUserBeanData.setMatchAccount(hustojUserBean.getMatchAccount());
		hustojUserBeanData.setMatchPassword(hustojUserBean.getMatchPassword());

		hustojUserDao.update(hustojUserBeanData);

		// 邮件模版
		queryHelper = new QueryHelper();
		queryHelper.createClass(MailTempBean.class, "a");
		queryHelper.addCondition("a.tempType = ?", 2);
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
		sendMailThread.init(mailInfoService, mailInfoBean, SysContext.getRequest() , Integer.parseInt(obj[1].toString()));
		Thread thread = new Thread(sendMailThread);
		ThreadPool.getInstance().execute(thread);
	}

	/**
	 *
	 */
	@Override
	public String genSignLink(HustojUserBean hustojUserBean) throws ApplicationException {
		IEncrypt iEncrypt = EncryptFactory.getInstance("AESEncrypt");
		String param = iEncrypt.encodePassword(hustojUserBean.getMatchId()+"", SystemConstantType.PASS_SALT);

		return param;
	}
}
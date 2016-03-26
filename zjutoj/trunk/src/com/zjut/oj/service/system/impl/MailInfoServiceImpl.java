package com.zjut.oj.service.system.impl;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.HustojUserBean;
import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.service.system.MailInfoService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Service("mailInfoService")
@Transactional
public class MailInfoServiceImpl extends ServiceSupport implements MailInfoService
{
	private final Logger logger = LoggerFactory.getLogger(MailInfoServiceImpl.class);

	@Override
	public int insert(MailInfoBean mailInfoBean) throws ApplicationException{
		return mailInfoDao.insert(mailInfoBean);
	}

	@Override
	public void delete(MailInfoBean mailInfoBean) throws ApplicationException{
		mailInfoDao.delete(mailInfoBean);
	}

	@Override
	public void update(MailInfoBean mailInfoBean) throws ApplicationException{
		mailInfoDao.update(mailInfoBean);
	}

	@Override
	public MailInfoBean findById(Integer id) throws ApplicationException{
		return mailInfoDao.findById(id);
	}

	/**
	 * 获取未发送成功的邮件列表
	 * @see com.zjut.oj.service.system.MailInfoService#queryForList(com.zjut.oj.entity.system.MailInfoBean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MailInfoBean> queryForList(MailInfoBean mailInfoBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(MailInfoBean.class, "a");
		queryHelper.addCondition("a.sendState in (?, ?)", SystemConstantType.SEND_STATE_WAIT, SystemConstantType.SEND_STATE_ERROR);

		List<MailInfoBean> list = this.mailInfoDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(MailInfoBean mailInfoBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(MailInfoBean.class, "a");
		pageQueryResult = this.mailInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	/**
	 * 发送邮件
	 */
	@Override
	public void transMail(MailInfoBean mailInfoBean) {
		int userId = mailInfoBean.getHustojUserBean().getUserId();
		HustojUserBean hustojUserBean = hustojUserDao.findById(userId);
		mailInfoBean.setHustojUserBean(hustojUserBean);

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			messageHelper.setTo(mailInfoBean.getAcceptAccount());
			messageHelper.setFrom(mailInfoBean.getSendAccount());

			String title = mailInfoBean.getMailTitle();
			if(mailInfoBean.getHustojUserBean().getUserName() != null) {
				title = title.replace("${userName}", mailInfoBean.getHustojUserBean().getUserName());
			}
			if(mailInfoBean.getHustojUserBean().getUserMail() != null) {
				title = title.replace("${mail}", mailInfoBean.getHustojUserBean().getUserMail());
			}

			if(mailInfoBean.getHustojUserBean().getMatchAccount() != null) {
				title = title.replace("${matchAccount}", mailInfoBean.getHustojUserBean().getMatchAccount());
			}

			if(mailInfoBean.getHustojUserBean().getMatchPassword() != null) {
				title = title.replace("${matchPassword}", mailInfoBean.getHustojUserBean().getMatchPassword());
			}
			messageHelper.setSubject(title);

			String content = mailInfoBean.getMailContent();
			if(mailInfoBean.getHustojUserBean().getUserName() != null) {
				content = content.replace("${userName}", mailInfoBean.getHustojUserBean().getUserName());
			}

			if(mailInfoBean.getHustojUserBean().getUserMail() != null) {
				content = content.replace("${mail}", mailInfoBean.getHustojUserBean().getUserMail());
			}

			if(mailInfoBean.getHustojUserBean().getMatchAccount() != null) {
				content = content.replace("${matchAccount}", mailInfoBean.getHustojUserBean().getMatchAccount());
			}

			if(mailInfoBean.getHustojUserBean().getMatchPassword() != null) {
				content = content.replace("${matchPassword}", mailInfoBean.getHustojUserBean().getMatchPassword());
			}
			messageHelper.setText(content, true);

			javaMailSender.send(mimeMessage);
			//发送成功
			mailInfoBean.setSendState(SystemConstantType.SEND_STATE_SUCC);
			mailInfoDao.update(mailInfoBean);
		} catch (Exception e) {
			//发送失败
			mailInfoBean.setSendState(SystemConstantType.SEND_STATE_ERROR);
			mailInfoDao.update(mailInfoBean);

			logger.error("发送邮件异常", e);
		}
	}
}
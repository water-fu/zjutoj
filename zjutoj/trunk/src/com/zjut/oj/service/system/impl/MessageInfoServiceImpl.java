package com.zjut.oj.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.MessageAttachBean;
import com.zjut.oj.entity.system.MessageInfoBean;
import com.zjut.oj.service.system.MessageInfoService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.FileUtil;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Transactional
@Service("messageInfoService")
public class MessageInfoServiceImpl extends ServiceSupport implements MessageInfoService
{
	public int insert(MessageInfoBean messageInfoBean) throws ApplicationException{
		//插入消息
		messageInfoBean.setMessageContent(messageInfoBean.getMessageContent().trim());
		messageInfoBean.setCreateDate(new Date());
		int messageId = messageInfoDao.insert(messageInfoBean);
		messageInfoBean.setMessageId(messageId);
		//插入附件
		insertMessageAttach(messageInfoBean);
		return messageId;
	}

	public void delete(MessageInfoBean messageInfoBean) throws ApplicationException{
		messageInfoBean = messageInfoDao.findById(messageInfoBean.getMessageId());
		if(messageInfoBean.getMessageId() == 0) {
			return;
		}
		for(MessageAttachBean messageAttachBean : messageInfoBean.getMessageAttchs()) {
			messageAttachDao.delete(messageAttachBean);
		}
		messageInfoDao.delete(messageInfoBean);
	}

	public void update(MessageInfoBean messageInfoBean) throws ApplicationException{
		MessageInfoBean messageInfoBeanData = this.messageInfoDao.findById(messageInfoBean.getMessageId());
		//更新消息
		messageInfoBeanData.setMessageContent(messageInfoBean.getMessageContent().trim());
		messageInfoBeanData.setMessageTitle(messageInfoBean.getMessageTitle().trim());
		messageInfoDao.update(messageInfoBeanData);
		//插入附件
		insertMessageAttach(messageInfoBean);
		//删除附件
		String deleteMessageId = messageInfoBean.getDeleteAttachIds();
		if(deleteMessageId != null && !deleteMessageId.equals("")) {
			String[] ids = deleteMessageId.split(",");
			for(String id : ids) {
				if(!id.equals("")) {
					MessageAttachBean messageAttachBean = messageAttachDao.findById(Integer.parseInt(id));
					messageAttachDao.delete(messageAttachBean);
				}
			}
		}
	}

	public MessageInfoBean detail(MessageInfoBean messageInfoBean) throws ApplicationException {
		if(messageInfoBean.getMessageId() ==0 &&
				(messageInfoBean.getMessageType() == SystemConstantType.MESSAGE_TYPE_ACMINTRO
						|| messageInfoBean.getMessageType() == SystemConstantType.MESSAGE_TYPE_TEAM
						|| messageInfoBean.getMessageType() == SystemConstantType.MESSAGE_TYPE_SCORE)) {
			List<MessageInfoBean> list = this.queryForList(messageInfoBean);
			if(!list.isEmpty()) {
				messageInfoBean = list.get(0);
			}
		}else {
			messageInfoBean = this.findById(messageInfoBean.getMessageId());
		}
		if(messageInfoBean.getMessageId() > 0) {
			messageInfoBean.setViewCount(messageInfoBean.getViewCount()+1);
			this.messageInfoDao.update(messageInfoBean);
		}

		return messageInfoBean;
	}

	public MessageInfoBean findById(Integer id) throws ApplicationException{
		return messageInfoDao.findById(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MessageInfoBean> queryForList(MessageInfoBean messageInfoBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(MessageInfoBean.class, "a");
		queryHelper.addCondition(messageInfoBean.getMessageType()!=0, "messageType = ?", new Object[]{messageInfoBean.getMessageType()});
		queryHelper.addCondition(messageInfoBean.getMessageTitle() != null, "messageTitle like ?", new Object[]{"%"+messageInfoBean.getMessageTitle()+"%"});
		queryHelper.addOrderProperty("createDate", false);
		List<MessageInfoBean> list = this.messageInfoDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(MessageInfoBean messageInfoBean, PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(MessageInfoBean.class, "a");
		queryHelper.addCondition(messageInfoBean.getMessageType()!=0, "messageType = ?", new Object[]{messageInfoBean.getMessageType()});
		queryHelper.addCondition(messageInfoBean.getMessageTitle() != null, "messageTitle like ?", new Object[]{"%"+messageInfoBean.getMessageTitle()+"%"});
		queryHelper.addOrderProperty("createDate", false);
		pageQueryResult = this.messageInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	/**
	 * 初始化首页
	 */
	@Override
	public List<PageQueryResult> initIndexPage() throws ApplicationException {
		List<PageQueryResult> resultList = new ArrayList<PageQueryResult>();


		//messageType: 1-通知  2-赛事简介  3-ACM简介 4-最近赛事
		PageQueryResult pageQueryResult = new PageQueryResult();
		pageQueryResult.setPageSize(6);
		QueryHelper queryHelper = new QueryHelper(MessageInfoBean.class, "a");
		queryHelper.addCondition("messageType = ?", new Object[]{SystemConstantType.MESSAGE_TYPE_NOTICE});
		queryHelper.addOrderProperty("createDate", false);
		pageQueryResult = this.messageInfoDao.queryForPage(pageQueryResult, queryHelper);
		resultList.add(pageQueryResult);

		//messageType: 1-通知  2-赛事简介  3-ACM简介 4-最近赛事
		pageQueryResult = new PageQueryResult();
		pageQueryResult.setPageSize(6);
		queryHelper = new QueryHelper(MessageInfoBean.class, "a");
		queryHelper.addCondition("messageType = ?", new Object[]{SystemConstantType.MESSAGE_TYPE_MATCHINTRO});
		queryHelper.addOrderProperty("createDate", false);
		pageQueryResult = this.messageInfoDao.queryForPage(pageQueryResult, queryHelper);
		resultList.add(pageQueryResult);

		//messageType: 1-通知  2-赛事简介  3-ACM简介 4-最近赛事
		pageQueryResult = new PageQueryResult();
		pageQueryResult.setPageSize(6);
		queryHelper = new QueryHelper(MessageInfoBean.class, "a");
		queryHelper.addCondition("messageType = ?", new Object[]{SystemConstantType.MESSAGE_TYPE_ACMINTRO});
		queryHelper.addOrderProperty("createDate", false);
		pageQueryResult = this.messageInfoDao.queryForPage(pageQueryResult, queryHelper);
		resultList.add(pageQueryResult);

		//messageType: 1-通知  2-赛事简介  3-ACM简介 4-最近赛事
		pageQueryResult = new PageQueryResult();
		pageQueryResult.setPageSize(6);
		queryHelper = new QueryHelper(MessageInfoBean.class, "a");
		queryHelper.addCondition("messageType = ?", new Object[]{SystemConstantType.MESSAGE_TYPE_LASTMATCH});
		queryHelper.addOrderProperty("createDate", false);
		pageQueryResult = this.messageInfoDao.queryForPage(pageQueryResult, queryHelper);
		resultList.add(pageQueryResult);

		return resultList;
	}

	//对附件进行增加或修改
	private void insertMessageAttach(MessageInfoBean messageInfoBean) throws ApplicationException {
		int messageId = messageInfoBean.getMessageId();

		String baseurl = SystemParamCache.getInstance().get("baseurl").toString();
		//插入附件
		List<MessageAttachBean> list = new ArrayList<MessageAttachBean>(messageInfoBean.getMessageAttchs());
		for(MessageAttachBean messageAttachBean : list) {
			//文件上传
			String url = FileUtil.fileUpdate(messageAttachBean.getFile(), messageId+"", messageAttachBean.getAttachName(), "message", baseurl);

			messageAttachBean.setMessageId(messageId);
			messageAttachBean.setAttachUrl(url);

			messageAttachDao.insert(messageAttachBean);
		}
	}
}
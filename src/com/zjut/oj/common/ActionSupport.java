package com.zjut.oj.common;

import javax.annotation.Resource;

import com.zjut.oj.service.match.ContestInfoService;
import com.zjut.oj.service.match.ContestProblemService;
import com.zjut.oj.service.match.ContestUserService;
import com.zjut.oj.service.match.LangInfoService;
import com.zjut.oj.service.study.AnswerInfoService;
import com.zjut.oj.service.study.CommentInfoService;
import com.zjut.oj.service.study.ProblemAttachService;
import com.zjut.oj.service.study.ProblemContentService;
import com.zjut.oj.service.study.ProblemInfoService;
import com.zjut.oj.service.study.TypeInfoService;
import com.zjut.oj.service.system.HustojInfoService;
import com.zjut.oj.service.system.HustojUserService;
import com.zjut.oj.service.system.LoginInfoService;
import com.zjut.oj.service.system.MailInfoService;
import com.zjut.oj.service.system.MailTempService;
import com.zjut.oj.service.system.MessageAttachService;
import com.zjut.oj.service.system.MessageInfoService;
import com.zjut.oj.service.system.PicsInfoService;
import com.zjut.oj.service.system.UserInfoService;
import com.zjut.oj.util.PageQueryResult;


public class ActionSupport extends DispatchAction {
	/**
	 * Action公共类
	 */
	private static final long serialVersionUID = 388414491066246561L;

	protected PageQueryResult pageQueryResult = new PageQueryResult();

	//system模块
	@Resource
	public UserInfoService userInfoService;
	@Resource
	public MessageInfoService messageInfoService;
	@Resource
	public MessageAttachService messageAttachService;
	@Resource
	public PicsInfoService picsInfoService;
	@Resource
	public LoginInfoService loginInfoService;
	@Resource
	public TypeInfoService typeInfoService;
	@Resource
	public HustojUserService hustojUserService;
	@Resource
	public HustojInfoService hustojInfoService;
	@Resource
	public MailTempService mailTempService;
	@Resource
	public MailInfoService mailInfoService;

	//study模块
	@Resource
	public ProblemInfoService problemInfoService;
	@Resource
	public ProblemContentService problemContentService;
	@Resource
	public ProblemAttachService problemAttachService;
	@Resource
	public CommentInfoService commentInfoService;
	@Resource
	public AnswerInfoService answerInfoService;

	//match模块
	@Resource
	public ContestProblemService contestProblemService;
	@Resource
	public ContestUserService contestUserService;
	@Resource
	public ContestInfoService contestInfoService;
	@Resource
	public LangInfoService langInfoService;

	public PageQueryResult getPageQueryResult() {
		return pageQueryResult;
	}

	public void setPageQueryResult(PageQueryResult pageQueryResult) {
		this.pageQueryResult = pageQueryResult;
	}
}

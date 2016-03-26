package com.zjut.oj.action.study;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.common.SessionControl;
import com.zjut.oj.entity.study.AnswerInfoBean;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class AnswerInfoAction extends ActionSupport implements ModelDriven<AnswerInfoBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private AnswerInfoBean answerInfoBean = new AnswerInfoBean();

	@Override
	public AnswerInfoBean getModel()
	{
		return answerInfoBean;
	}

	/**
	 * 题解列表
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String list(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String pageNo = request.getParameter("pageNo");
		if(pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}
		String searchType = request.getParameter("searchType");
		if(searchType != null && searchType.equals("1")) {
			if(SessionControl.getCurUserDetail(request) == null) {
				return "loginUI";
			}
			answerInfoBean.setUserInfoBean(SessionControl.getCurUserDetail(request).getUser());
		}else {
			searchType = "-1";
		}
		answerInfoBean.setIsShow(1);
		pageQueryResult = answerInfoService.queryForPage(answerInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("searchType", searchType);
		return responseResult("list");
	}

	/**
	 * 修改页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String modifyUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		answerInfoBean = answerInfoService.findById(answerInfoBean.getAnswerId());
		this.putValueStack("answerInfoBean", answerInfoBean);

		List<ProblemInfoBean> list = this.problemInfoService.queryForList(new ProblemInfoBean());
		List<String> problemIdList = new ArrayList<String>();
		for (ProblemInfoBean problemInfoBean : list) {
			problemIdList.add("【" + problemInfoBean.getProblemId() + "】" + problemInfoBean.getProblemTitle());
		}
		this.putValueStack("problemIdList", problemIdList);

		return responseResult("saveUI");
	}

	/**
	 * 新增界面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String saveUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		List<ProblemInfoBean> list = this.problemInfoService.queryForList(new ProblemInfoBean());
		List<String> problemIdList = new ArrayList<String>();
		for (ProblemInfoBean problemInfoBean : list) {
			problemIdList.add("【" + problemInfoBean.getProblemId() + "】" + problemInfoBean.getProblemTitle());
		}
		this.putValueStack("problemIdList", problemIdList);

		return responseResult("saveUI");
	}

	/**
	 * 保存
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String save(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		//新增
		if(answerInfoBean.getAnswerId() == 0) {
			int answerId = this.answerInfoService.insert(answerInfoBean);
			answerInfoBean.setAnswerId(answerId);
		}else {
			this.answerInfoService.update(answerInfoBean);
		}

		return responseResult("save", answerInfoBean.getAnswerId());
	}

	/**
	 * 删除
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		answerInfoService.delete(answerInfoBean);
		return responseResult("delete");
	}

	/**
	 * 详细页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String detail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		answerInfoBean = answerInfoService.findById(answerInfoBean.getAnswerId());
		this.putValueStack("answerInfoBean", answerInfoBean);
		return responseResult("detail");
	}

	/**
	 * 题解列表
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String mlist(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String pageNo = request.getParameter("pageNo");
		if(pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}
		String searchType = request.getParameter("searchType");
		if(searchType != null && !searchType.equals("-1")) {
			answerInfoBean.setIsShow(Integer.parseInt(searchType));
		}else {
			answerInfoBean.setIsShow(-1);
			searchType = "-1";
		}
		pageQueryResult = answerInfoService.queryForPage(answerInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("searchType", searchType);
		return responseResult("mlist");
	}

	/**
	 * 详细页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String mdetail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		answerInfoBean = answerInfoService.findById(answerInfoBean.getAnswerId());
		this.putValueStack("answerInfoBean", answerInfoBean);
		return responseResult("mdetail");
	}

	/**
	 * 修改页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String mmodifyUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		answerInfoBean = answerInfoService.findById(answerInfoBean.getAnswerId());
		this.putValueStack("answerInfoBean", answerInfoBean);

		List<ProblemInfoBean> list = this.problemInfoService.queryForList(new ProblemInfoBean());
		List<String> problemIdList = new ArrayList<String>();
		for (ProblemInfoBean problemInfoBean : list) {
			problemIdList.add("【" + problemInfoBean.getProblemId() + "】" + problemInfoBean.getProblemTitle());
		}
		this.putValueStack("problemIdList", problemIdList);

		return responseResult("msaveUI");
	}

	/**
	 * 新增界面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String msaveUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		List<ProblemInfoBean> list = this.problemInfoService.queryForList(new ProblemInfoBean());
		List<String> problemIdList = new ArrayList<String>();
		for (ProblemInfoBean problemInfoBean : list) {
			problemIdList.add("【" + problemInfoBean.getProblemId() + "】" + problemInfoBean.getProblemTitle());
		}
		this.putValueStack("problemIdList", problemIdList);

		return responseResult("msaveUI");
	}

	/**
	 * 审核
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String show(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		this.answerInfoService.show(answerInfoBean);
		return responseResult("show");
	}
}
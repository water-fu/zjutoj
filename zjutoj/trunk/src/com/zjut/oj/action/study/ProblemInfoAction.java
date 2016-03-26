package com.zjut.oj.action.study;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.cache.TypeInfoCache;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.common.SessionControl;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class ProblemInfoAction extends ActionSupport implements ModelDriven<ProblemInfoBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private ProblemInfoBean problemInfoBean = new ProblemInfoBean();

	@Override
	public ProblemInfoBean getModel()
	{
		return problemInfoBean;
	}

	/**
	 * 用户使用列表页面
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
		problemInfoBean.setIsShow(SystemConstantType.IS_SHOW_YES);
		pageQueryResult = this.problemInfoService.queryForPage(problemInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("searchBean", problemInfoBean);
		this.putValueStack("typeList", TypeInfoCache.getInstance().getSelectData());
		return responseResult("list");
	}

	/**
	 * 用户使用详细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String detail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		problemInfoBean = this.problemInfoService.findById(problemInfoBean.getProblemId());
		this.putValueStack("problemInfoBean", problemInfoBean);
		return responseResult("detail");
	}

	/**
	 * 后台维护列表信息
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
		pageQueryResult = this.problemInfoService.queryForPage(problemInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("searchBean", problemInfoBean);
		this.putValueStack("typeList", TypeInfoCache.getInstance().getSelectData());
		return responseResult("mlist");
	}

	/**
	 * 后台维护详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String mdetail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		problemInfoBean = this.problemInfoService.findById(problemInfoBean.getProblemId());
		this.putValueStack("problemInfoBean", problemInfoBean);
		return responseResult("mdetail");
	}

	/**
	 * 新增界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String saveUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		this.putValueStack("titleName", "新增试题信息");
		this.putValueStack("typeList", TypeInfoCache.getInstance().getSelectData());
		return responseResult("editUI");
	}

	/**
	 * 修改界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String modifyUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		problemInfoBean = this.problemInfoService.findById(problemInfoBean.getProblemId());
		this.putValueStack("problemInfoBean", problemInfoBean);
		this.putValueStack("titleName", "修改试题信息");
		this.putValueStack("typeList", TypeInfoCache.getInstance().getSelectData());
		return responseResult("editUI");
	}

	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String save(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		//新增
		if(problemInfoBean.getProblemId() == 0) {
			problemInfoBean.setProblemUserBean(SessionControl.getCurUserDetail(request).getUser());
			int problemId = this.problemInfoService.insert(problemInfoBean);
			problemInfoBean.setProblemId(problemId);
		}else {
			this.problemInfoService.update(problemInfoBean);
		}

		return responseResult("save", problemInfoBean.getProblemId());
	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		this.problemInfoService.delete(problemInfoBean);
		return responseResult("delete");
	}

	/**
	 * 变更状态
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String status(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String flag = request.getParameter("flag");
		if(flag == null) {
			throw new ApplicationException("系统错误，请联系系统管理员");
		}
		ProblemInfoBean problemInfoBean = this.problemInfoService.findById(this.problemInfoBean.getProblemId());
		problemInfoBean.setIsShow(Integer.parseInt(flag));
		this.problemInfoService.update(problemInfoBean);

		return responseResult("status");
	}

	/**
	 * 题目输入输出附件
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String data(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ProblemInfoBean problemInfoBean = this.problemInfoService.findById(this.problemInfoBean.getProblemId());
		this.putValueStack("problemInfoBean", problemInfoBean);
		return responseResult("data");
	}

	/**
	 * 竞赛信息中，试题信息的详细界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String cpDetail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		problemInfoBean = this.problemInfoService.findById(problemInfoBean.getProblemId());
		this.putValueStack("problemInfoBean", problemInfoBean);
		return responseResult("cpdetail");
	}
}
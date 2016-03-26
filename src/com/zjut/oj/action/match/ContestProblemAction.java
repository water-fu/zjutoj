package com.zjut.oj.action.match;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.entity.match.ContestProblemBean;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ContestProblemAction extends ActionSupport implements ModelDriven<ContestProblemBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private ContestProblemBean contestProblemBean = new ContestProblemBean();

	@Override
	public ContestProblemBean getModel()
	{
		return contestProblemBean;
	}

	/**
	 * 竞赛试题保存
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String save(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String problemId = request.getParameter("problemId");
		if(problemId == null || "".equals(problemId)) {
			throw new ApplicationException("试题编号为空");
		}
		ProblemInfoBean problemInfoBean = new ProblemInfoBean();
		problemInfoBean.setProblemId(Integer.valueOf(problemId));
		contestProblemBean.setProblemInfoBean(problemInfoBean);
		this.contestProblemService.insert(contestProblemBean);

		return responseResult("save", problemInfoBean.getProblemId());
	}

	/**
	 * 竞赛试题删除
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String problemId = request.getParameter("problemId");
		if(problemId == null || "".equals(problemId)) {
			throw new ApplicationException("试题编号为空");
		}
		ProblemInfoBean problemInfoBean = new ProblemInfoBean();
		problemInfoBean.setProblemId(Integer.valueOf(problemId));
		contestProblemBean.setProblemInfoBean(problemInfoBean);
		this.contestProblemService.delete(contestProblemBean);

		return responseResult("delete", problemInfoBean.getProblemId());
	}
}
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
import com.zjut.oj.entity.study.CommentInfoBean;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class CommentInfoAction extends ActionSupport implements ModelDriven<CommentInfoBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private CommentInfoBean commentInfoBean = new CommentInfoBean();

	@Override
	public CommentInfoBean getModel()
	{
		return commentInfoBean;
	}

	/**
	 * 主题列表
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
		pageQueryResult = this.commentInfoService.queryForPage(commentInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		return responseResult("list");
	}

	/**
	 * 评论列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String detail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		CommentInfoBean commentInfoBean = this.commentInfoService.findById(this.commentInfoBean.getCommentId());
		String pageNo = request.getParameter("pageNo");
		if(pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}
		pageQueryResult = this.commentInfoService.queryChildForPage(commentInfoBean, pageQueryResult);

		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("commentInfoBean", commentInfoBean);
		return responseResult("detail");
	}

	/**
	 * 新增评论
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String save(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		CommentInfoBean commentInfoBeanData = new CommentInfoBean();
		if(this.commentInfoBean.getCommentId() != 0) {
			CommentInfoBean commentInfoBeanCond = this.commentInfoService.findById(this.commentInfoBean.getCommentId());
			commentInfoBeanData.setCommentDesc(this.commentInfoBean.getCommentDesc());
			commentInfoBeanData.setParentId(commentInfoBeanCond.getCommentId());
			commentInfoBeanData.setProblemInfoBean(commentInfoBeanCond.getProblemInfoBean());
		}else {
			commentInfoBeanData.setProblemInfoBean(this.commentInfoBean.getProblemInfoBean());
			commentInfoBeanData.setCommentDesc(this.commentInfoBean.getCommentDesc());
			commentInfoBeanData.setParentId(null);
		}

		int commentId = commentInfoService.insert(commentInfoBeanData);
		return responseResult("save", commentId);
	}

	/**
	 * 新增主题页面
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
}
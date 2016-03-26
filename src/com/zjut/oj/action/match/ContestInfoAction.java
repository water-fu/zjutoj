package com.zjut.oj.action.match;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.cache.TypeInfoCache;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.entity.match.ContestInfoBean;
import com.zjut.oj.entity.match.LangInfoBean;
import com.zjut.oj.entity.study.ProblemInfoBean;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.Common;

@Controller
@Scope("prototype")
public class ContestInfoAction extends ActionSupport implements ModelDriven<ContestInfoBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private ContestInfoBean contestInfoBean = new ContestInfoBean();
	private ProblemInfoBean problemInfoBean = new ProblemInfoBean();
	private UserInfoBean userInfoBean = new UserInfoBean();

	@Override
	public ContestInfoBean getModel()
	{
		return contestInfoBean;
	}

	/**
	 * 竞赛信息
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
		pageQueryResult = this.contestInfoService.queryForPage(contestInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		return responseResult("list");
	}

	/**
	 * 详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String detail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		contestInfoBean = this.contestInfoService.findById(contestInfoBean.getContestId());
		this.putValueStack("contestInfoBean", contestInfoBean);
		return responseResult("detail");
	}

	/**
	 * 后台维护竞赛信息
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
		pageQueryResult = this.contestInfoService.queryForPage(contestInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
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
		contestInfoBean = this.contestInfoService.findById(contestInfoBean.getContestId());
		this.putValueStack("contestInfoBean", contestInfoBean);
		return responseResult("mdetail");
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
		contestInfoBean = this.contestInfoService.findById(contestInfoBean.getContestId());
		this.putValueStack("contestInfoBean", contestInfoBean);
		this.putValueStack("titleName", "修改竞赛信息");
		return responseResult("editUI");
	}

	/**
	 *
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String saveUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		this.putValueStack("titleName", "新增竞赛信息");
		return responseResult("editUI");
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
		this.contestInfoService.delete(contestInfoBean);
		return responseResult("delete");
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
		String language = request.getParameter("contestLanguage");
		if(language == null || language.equals("")) {
			throw new ApplicationException("请选择竞赛语言");
		}
		String[] languages = language.trim().split(",");
		Set<LangInfoBean> langInfoBeans = new HashSet<LangInfoBean>();
		for (String lang : languages) {
			LangInfoBean langInfoBean = new LangInfoBean();
			langInfoBean.setLanguageName(lang.trim());
			langInfoBeans.add(langInfoBean);
		}
		this.contestInfoBean.setLangInfos(langInfoBeans);

		String startTime = request.getParameter("startTimeIn");
		if(startTime == null || startTime.equals("")) {
			throw new ApplicationException("请输入开始时间");
		}
		String endTime = request.getParameter("endTimeIn");
		if(endTime == null || endTime.equals("")) {
			throw new ApplicationException("请输入结束时间");
		}
		this.contestInfoBean.setStartTime(Common.getStringToDate(startTime, Common.DATE_FORMAT_STRING));
		this.contestInfoBean.setEndTime(Common.getStringToDate(endTime, Common.DATE_FORMAT_STRING));

		if(this.contestInfoBean.getContestId() == 0) {
			int contestId = this.contestInfoService.insert(this.contestInfoBean);
			this.contestInfoBean.setContestId(contestId);
		}else {
			this.contestInfoService.update(contestInfoBean);
		}

		return responseResult("save", this.contestInfoBean.getContestId());
	}

	/**
	 * 状态变更，启用/禁用
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String status(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		this.contestInfoService.status(contestInfoBean);
		return responseResult("status");
	}

	/**
	 * 获取当前时间
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String currentTime(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = sdf.format(new Date());

		return responseResult("time", str);
	}

	/**
	 * 试题选择
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String problemList(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String pageNo = request.getParameter("pageNo");
		if(pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}
		String isSelected = request.getParameter("selected");
		if(isSelected == null || "".equals("selected")) {
			isSelected = "0";
		}
		if(isSelected.equals("0")) {
			this.putValueStack("titleName", "可选试题列表");
		}else {
			this.putValueStack("titleName", "已选试题列表");
		}
		pageQueryResult = this.contestInfoService.queryProblemForPage(problemInfoBean, pageQueryResult, request);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("searchBean", problemInfoBean);
		this.putValueStack("typeList", TypeInfoCache.getInstance().getSelectData());
		this.putValueStack("selected", request.getParameter("selected"));
		this.putValueStack("contestInfoBean", contestInfoBean);
		return responseResult("problemList");
	}

	/**
	 * 试题选择
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String userList(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String pageNo = request.getParameter("pageNo");
		if(pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}
		String isSelected = request.getParameter("selected");
		if(isSelected == null || "".equals("selected")) {
			isSelected = "0";
		}
		if(isSelected.equals("0")) {
			this.putValueStack("titleName", "可选用户列表");
		}else {
			this.putValueStack("titleName", "已选用户列表");
		}
		pageQueryResult = this.contestInfoService.queryUserForPage(userInfoBean, pageQueryResult, request);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("searchBean", userInfoBean);
		this.putValueStack("selected", request.getParameter("selected"));
		this.putValueStack("contestInfoBean", contestInfoBean);
		return responseResult("userList");
	}

	public ProblemInfoBean getProblemInfoBean() {
		return problemInfoBean;
	}

	public void setProblemInfoBean(ProblemInfoBean problemInfoBean) {
		this.problemInfoBean = problemInfoBean;
	}

	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}
}
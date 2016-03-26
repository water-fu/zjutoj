package com.zjut.oj.action.match;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.entity.match.ContestUserBean;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class ContestUserAction extends ActionSupport implements ModelDriven<ContestUserBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private ContestUserBean contestUserBean = new ContestUserBean();

	@Override
	public ContestUserBean getModel()
	{
		return contestUserBean;
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
		String userId = request.getParameter("userId");
		if(userId == null || "".equals(userId)) {
			throw new ApplicationException("用户编号为空");
		}
		UserInfoBean userInfoBean = new UserInfoBean();
		userInfoBean.setUserId(Integer.valueOf(userId));
		contestUserBean.setUserInfo(userInfoBean);
		this.contestUserService.insert(contestUserBean);

		return responseResult("save", userInfoBean.getUserId());
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
		String userId = request.getParameter("userId");
		if(userId == null || "".equals(userId)) {
			throw new ApplicationException("用户编号为空");
		}
		UserInfoBean userInfoBean = new UserInfoBean();
		userInfoBean.setUserId(Integer.valueOf(userId));
		contestUserBean.setUserInfo(userInfoBean);
		this.contestUserService.delete(contestUserBean);

		return responseResult("delete", userInfoBean.getUserId());
	}
}
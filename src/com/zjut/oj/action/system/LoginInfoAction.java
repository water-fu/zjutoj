package com.zjut.oj.action.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.SessionControl;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.MenuInfoBean;
import com.zjut.oj.entity.system.RoleInfoBean;
import com.zjut.oj.entity.system.RolePrivBean;
import com.zjut.oj.entity.system.UserDetails;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.entity.system.UserRoleBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class LoginInfoAction extends ActionSupport implements ModelDriven<UserInfoBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private UserInfoBean userInfoBean = new UserInfoBean();

	@Override
	public UserInfoBean getModel()
	{
		return userInfoBean;
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return "loginUI";
	}

	public String login(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		UserInfoBean userInfoBean = this.loginInfoService.login(this.userInfoBean);
		//用户权限
		List<String> list = new ArrayList<String>();
		for(UserRoleBean userRoleBean : userInfoBean.getUserRoles()) {
			RoleInfoBean roleInfoBean = userRoleBean.getRoleInfoBean();
			for(RolePrivBean rolePrivBean : roleInfoBean.getRolePrivs()) {
				MenuInfoBean menuInfoBean = rolePrivBean.getMenuInfoBean();
				list.add(menuInfoBean.getMenuSign());
			}
		}

		UserDetails userDetails = new UserDetails();
		userDetails.setUser(userInfoBean);
		userDetails.setUserPrivList(list);

		SessionControl.setAttribute(request, SystemConstantType.USER_DETAILS, userDetails);
		return "login";
	}

	public String logout(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		SessionControl.removeAttribute(request, SystemConstantType.USER_DETAILS);
		return "logout";
	}
}
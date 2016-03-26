package com.zjut.oj.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class UserInfoAction extends ActionSupport implements ModelDriven<UserInfoBean>
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

    public String browse(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        this.userInfoService.queryForPage(userInfoBean, pageQueryResult);
        return SUCCESS;
    }
}
package com.zjut.oj.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.entity.system.MailInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class MailInfoAction extends ActionSupport implements ModelDriven<MailInfoBean>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * ModelDriven的机制：ValueStack
     */
    private MailInfoBean mailInfoBean = new MailInfoBean();

    @Override
    public MailInfoBean getModel()
    {
        return mailInfoBean;
    }

    public String xxxx(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

        return responseResult("");
    }
}
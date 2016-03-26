package com.zjut.oj.action.match;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.entity.match.LangInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class LangInfoAction extends ActionSupport implements ModelDriven<LangInfoBean>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * ModelDriven的机制：ValueStack
     */
    private LangInfoBean langInfoBean = new LangInfoBean();

    @Override
    public LangInfoBean getModel()
    {
        return langInfoBean;
    }

    public String xxxx(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

        return responseResult("");
    }
}
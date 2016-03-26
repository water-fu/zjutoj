package com.zjut.oj.action.study;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.entity.study.ProblemContentBean;
import com.zjut.oj.util.ApplicationException;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class ProblemContentAction extends ActionSupport implements ModelDriven<ProblemContentBean>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * ModelDriven的机制：ValueStack
     */
    private ProblemContentBean problemContentBean = new ProblemContentBean();

    @Override
    public ProblemContentBean getModel()
    {
        return problemContentBean;
    }

    public String xxxx(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

        return "";
    }
}
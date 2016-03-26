package com.zjut.oj.action.study;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.entity.study.TypeInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class TypeInfoAction extends ActionSupport implements ModelDriven<TypeInfoBean>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * ModelDriven的机制：ValueStack
     */
    private TypeInfoBean typeInfoBean = new TypeInfoBean();

    @Override
    public TypeInfoBean getModel()
    {
        return typeInfoBean;
    }

    public String xxxx(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

        return "";
    }
}
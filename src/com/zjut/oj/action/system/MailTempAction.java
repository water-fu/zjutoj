package com.zjut.oj.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.entity.system.MailTempBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class MailTempAction extends ActionSupport implements ModelDriven<MailTempBean>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * ModelDriven的机制：ValueStack
     */
    private MailTempBean mailTempBean = new MailTempBean();

    @Override
    public MailTempBean getModel()
    {
        return mailTempBean;
    }

    /**
     * 列表数据
     *
     * @param request
     * @param response
     * @return
     * @throws ApplicationException
     */
    @LoginFilter(needLogin = true)
    public String mlist(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        String pageNo = request.getParameter("pageNo");
        if(pageNo != null && !pageNo.equals("")) {
            pageQueryResult.setPageNo(Integer.parseInt(pageNo));
        }
        pageQueryResult = this.mailTempService.queryForPage(mailTempBean, pageQueryResult);
        this.putValueStack("pageQueryResult", pageQueryResult);
        return responseResult("mlist");
    }

    /**
     * 新增界面
     * @param request
     * @param response
     * @return
     * @throws ApplicationException
     */
    @LoginFilter(needLogin=true)
    public String saveUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        return responseResult("editUI");
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
        mailTempBean = this.mailTempService.findById(mailTempBean.getTempId());
        this.putValueStack("mailTempBean", mailTempBean);
        return responseResult("editUI");
    }

    /**
     * 保存
     * @param request
     * @param response
     * @return
     * @throws ApplicationException
     */
    @LoginFilter(needLogin=true)
    public String save(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        //新增或更新
        if(mailTempBean.getTempId() == 0) {
            int tempId = this.mailTempService.insert(mailTempBean);
            mailTempBean.setTempId(tempId);
        }else {
            this.mailTempService.update(mailTempBean);
        }

        return responseResult("save", mailTempBean.getTempId());
    }

    /**
     * 详细页面
     *
     * @param request
     * @param response
     * @return
     * @throws ApplicationException
     */
    @LoginFilter(needLogin = true)
    public String mdetail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        mailTempBean = this.mailTempService.findById(mailTempBean.getTempId());
        putValueStack("mailTempBean", mailTempBean);

        return responseResult("mdetail");
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     * @return
     * @throws ApplicationException
     */
    @LoginFilter(needLogin = true)
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        this.mailTempService.delete(mailTempBean);

        return responseResult("delete");
    }
}
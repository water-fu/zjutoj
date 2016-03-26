package com.zjut.oj.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.entity.system.HustojInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.Common;

@Controller
@Scope("prototype")
public class HustojInfoAction extends ActionSupport implements ModelDriven<HustojInfoBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private HustojInfoBean hustojInfoBean = new HustojInfoBean();

	@Override
	public HustojInfoBean getModel()
	{
		return hustojInfoBean;
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

		pageQueryResult = this.hustojInfoService.queryForPage(hustojInfoBean, pageQueryResult);
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
		hustojInfoBean = this.hustojInfoService.findById(hustojInfoBean.getMatchId());
		this.putValueStack("hustojInfoBean", hustojInfoBean);
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
		String signFrom = request.getParameter("signFromIn");
		if(signFrom == null || signFrom.equals("")) {
			throw new ApplicationException("请输入开始时间");
		}
		String signEnd = request.getParameter("signEndIn");
		if(signEnd == null || signEnd.equals("")) {
			throw new ApplicationException("请输入结束时间");
		}
		this.hustojInfoBean.setSignFrom(Common.getStringToDate(signFrom, Common.DATE_FORMAT_STRING));
		this.hustojInfoBean.setSignEnd(Common.getStringToDate(signEnd, Common.DATE_FORMAT_STRING));
        
		//新增或更新
		if(hustojInfoBean.getMatchId() == 0) {
			int matchId = this.hustojInfoService.insert(hustojInfoBean);
			hustojInfoBean.setMatchId(matchId);
		}else {
			this.hustojInfoService.update(hustojInfoBean);
		}
        
		return responseResult("save", hustojInfoBean.getMatchId());
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
		this.hustojInfoService.delete(hustojInfoBean);

		return responseResult("delete");
	}
}
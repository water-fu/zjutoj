package com.zjut.oj.action.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.cache.SchoolInfoCache;
import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.common.excel.ExcelExportCommon;
import com.zjut.oj.entity.system.HustojInfoBean;
import com.zjut.oj.entity.system.HustojUserBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class HustojUserAction extends ActionSupport implements ModelDriven<HustojUserBean> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(HustojUserAction.class);

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private HustojUserBean hustojUserBean = new HustojUserBean();

	@Override
	public HustojUserBean getModel() {
		return hustojUserBean;
	}

	/**
	 * 竞赛申请页面
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String saveUI(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		String mId = request.getParameter("mId");
		HustojInfoBean hustojInfoBean = this.hustojInfoService.matchSign(mId);
		putValueStack("hustojInfoBean", hustojInfoBean);
		putValueStack("schoolList", SchoolInfoCache.getInstance().getSelectData());

		return responseResult("editUI");
	}

	/**
	 * 竞赛保存
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		int[] ids = hustojUserService.insert(hustojUserBean);
		hustojUserBean.setUserId(ids[0]);
		try {
			mailInfoService.transMail(mailInfoService.findById(ids[1]));
		} catch (Exception e) {
			logger.error("邮件发送失败，失败原因：" + e.getMessage(), e);
		}

		return responseResult("save", ids[0]);
	}

	/**
	 * 保存成功跳转
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String success(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		hustojUserBean = this.hustojUserService.findById(hustojUserBean.getUserId());
		HustojInfoBean hustojInfoBean = this.hustojInfoService.findById(hustojUserBean.getMatchId());
		putValueStack("hustojUserBean", hustojUserBean);
		putValueStack("hustojInfoBean", hustojInfoBean);

		return responseResult("success");
	}

	/**
	 * 竞赛修改
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		int mailId = hustojUserService.update(hustojUserBean);

		try {
			mailInfoService.transMail(mailInfoService.findById(mailId));
		} catch (Exception e) {
			logger.error("邮件发送失败，失败原因：" + e.getMessage(), e);
		}

		return responseResult("save", hustojUserBean.getUserId());
	}

	/**
	 * 发送邮件
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String transMail(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		int mailId = hustojUserService.transMail(hustojUserBean);

		try {
			mailInfoService.transMail(mailInfoService.findById(mailId));
		} catch (Exception e) {
			logger.error("邮件发送失败，失败原因：" + e.getMessage(), e);
		}

		return responseResult("mail");
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
	public String mlist(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		String pageNo = request.getParameter("pageNo");
		if (pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}

		pageQueryResult = this.hustojUserService.queryForPage(hustojUserBean, pageQueryResult);

		HustojInfoBean hustojInfoBean = this.hustojInfoService
				.findById(hustojUserBean.getMatchId());
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("hustojInfoBean", hustojInfoBean);
		return responseResult("mlist");
	}

	/**
	 * 导出
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin = true)
	public void exportData(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		// 列模型
		List<String> columnList = new ArrayList<String>();
		// 数据
		List<List<String>> dataList = new ArrayList<List<String>>();

		List<HustojUserBean> list = this.hustojUserService.exportHustojUser(hustojUserBean);
		// 列表数据
		for (HustojUserBean hustojUserBeanData : list) {
			List<String> data = new ArrayList<String>();
			data.add(hustojUserBeanData.getUserNo());
			data.add(hustojUserBeanData.getUserName());
			data.add(hustojUserBeanData.getUserSchool());
			data.add(hustojUserBeanData.getUserClass());
			data.add(hustojUserBeanData.getUserMail());
			data.add(hustojUserBeanData.getUserTel());
			data.add(hustojUserBeanData.getUserExp());
			dataList.add(data);
		}

		// 列表表头
		columnList.add("0|学号");
		columnList.add("0|姓名");
		columnList.add("0|学院");
		columnList.add("0|班级");
		columnList.add("0|邮箱");
		columnList.add("0|联系电话");
		columnList.add("0|获奖经历");
		HustojInfoBean hustojInfoBean = this.hustojInfoService
				.findById(hustojUserBean.getMatchId());

		String filePath = SystemParamCache.getInstance().get("baseurl").toString() + "/excel";
		ExcelExportCommon.export(columnList, dataList, filePath, hustojInfoBean.getMatchTitle()
				+ "用户列表.xls");
		ExcelExportCommon.downLoad(request, response, filePath, hustojInfoBean.getMatchTitle()
				+ "用户列表.xls");
	}

	/**
	 * 生成报名链接
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin = true)
	public String genSignLink(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		String param = this.hustojUserService.genSignLink(hustojUserBean);

		String url = "/system/hustojUserAction.do?method=saveUI&mId=" + param;
		return responseResult("genSignLink", url);
	}
}
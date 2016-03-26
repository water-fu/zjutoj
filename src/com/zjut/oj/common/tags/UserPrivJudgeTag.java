package com.zjut.oj.common.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.zjut.oj.common.SessionControl;
import com.zjut.oj.common.SysContext;
import com.zjut.oj.entity.system.UserDetails;

public class UserPrivJudgeTag extends TagSupport {

	/**
	 * 用户权限判定
	 */
	private static final long serialVersionUID = 1L;

	private String menuSign;

	public String getMenuSign() {
		return menuSign;
	}

	public void setMenuSign(String menuSign) {
		this.menuSign = menuSign;
	}

	@Override
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		if(menuSign == null || "".equals(menuSign)) {
			return Tag.SKIP_BODY;
		}
		UserDetails userDetails = SessionControl.getCurUserDetail(SysContext.getRequest());
		if(userDetails == null) {
			return Tag.SKIP_BODY;
		}

		List<String> userPrivList = userDetails.getUserPrivList();
		if(userPrivList.contains(menuSign.trim())) {
			return Tag.EVAL_BODY_INCLUDE;
		}

		return Tag.SKIP_BODY;
	}
}

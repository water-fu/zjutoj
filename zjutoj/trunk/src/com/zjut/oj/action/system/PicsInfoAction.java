package com.zjut.oj.action.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.entity.system.PicsInfoBean;
import com.zjut.oj.util.ApplicationException;

@Controller
@Scope("prototype")
public class PicsInfoAction extends ActionSupport implements ModelDriven<PicsInfoBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//图片附件
	private List<File> image;
	private List<String> imageFileName;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private PicsInfoBean picsInfoBean = new PicsInfoBean();

	@Override
	public PicsInfoBean getModel()
	{
		return picsInfoBean;
	}

	/**
	 * 首页图片修改
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String saveUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		List<PicsInfoBean> picsInfoList = picsInfoService.queryForList(picsInfoBean);
		this.putValueStack("picsInfoList", picsInfoList);
		return responseResult("editUI");
	}

	/**
	 * 保存首页图片
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String save(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String[] links = request.getParameterValues("linkUrl");

		List<PicsInfoBean> list = new ArrayList<PicsInfoBean>();
		if(image != null) {
			//图片附件
			for(int i = 0; i < image.size(); i++) {
				if(image.get(i) == null) {
					continue;
				}
				PicsInfoBean picsInfoBean = new PicsInfoBean();
				picsInfoBean.setPicTitle(imageFileName.get(i));
				picsInfoBean.setFile(image.get(i));
				picsInfoBean.setLinkUrl(links[i].trim());

				list.add(picsInfoBean);
			}
		}

		//需要删除的附件ID
		String deleteAttachIds = request.getParameter("deletedAttachIds");

		this.picsInfoService.insert(list, deleteAttachIds);
		return responseResult("save");
	}

	public List<File> getImage() {
		return image;
	}

	public void setImage(List<File> image) {
		this.image = image;
	}

	public List<String> getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}
}
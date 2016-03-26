package com.zjut.oj.action.study;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.entity.study.ProblemAttachBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.FileUtil;

@Controller
@Scope("prototype")
public class ProblemAttachAction extends ActionSupport implements ModelDriven<ProblemAttachBean>
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//下载附件
	private List<File> file;
	private List<String> fileFileName;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private ProblemAttachBean problemAttachBean = new ProblemAttachBean();

	@Override
	public ProblemAttachBean getModel()
	{
		return problemAttachBean;
	}

	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		this.problemAttachService.delete(problemAttachBean);
		return responseResult("delete");
	}

	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String uploadUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return responseResult("uploadUI");
	}

	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String upload(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		List<ProblemAttachBean> list = new ArrayList<ProblemAttachBean>();
		for(int i = 0; i < file.size(); i++) {
			ProblemAttachBean problemAttachBean = new ProblemAttachBean();
			problemAttachBean.setProblemId(this.problemAttachBean.getProblemId());
			problemAttachBean.setAttachName(fileFileName.get(i));
			problemAttachBean.setAttachFile(file.get(i));
			list.add(problemAttachBean);
		}
		this.problemAttachService.insert(list);

		return responseResult("upload");
	}

	/**
	 * 修改文件内容
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String modifyUI(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		ProblemAttachBean problemAttachBean = this.problemAttachService.findById(this.problemAttachBean.getAttachId());
		String baseurl = SystemParamCache.getInstance().get("baseurl").toString();
		String fileData = FileUtil.readFileData(baseurl + problemAttachBean.getAttachUrl());
		this.putValueStack("fileData", fileData);
		return responseResult("modifyUI");
	}

	/**
	 * 修改文件内容
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String save(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String fileData = request.getParameter("fileData");
		ProblemAttachBean problemAttachBean = this.problemAttachService.findById(this.problemAttachBean.getAttachId());
		String baseurl = SystemParamCache.getInstance().get("baseurl").toString();

		FileUtil.wirteFileData(baseurl + problemAttachBean.getAttachUrl(), fileData);
		return responseResult("save", problemAttachBean.getProblemId());
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}
}
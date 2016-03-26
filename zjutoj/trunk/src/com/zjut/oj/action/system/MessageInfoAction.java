package com.zjut.oj.action.system;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.ActionSupport;
import com.zjut.oj.common.LoginFilter;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.MessageAttachBean;
import com.zjut.oj.entity.system.MessageInfoBean;
import com.zjut.oj.entity.system.PicsInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.FileUtil;
import com.zjut.oj.util.PageQueryResult;

@Controller
@Scope("prototype")
public class MessageInfoAction extends ActionSupport implements ModelDriven<MessageInfoBean>
{
	/**
	 * 消息管理
	 */
	private static final long serialVersionUID = 1L;

	//图片附件
	private List<File> image;
	private List<String> imageFileName;

	//下载附件
	private List<File> file;
	private List<String> fileFileName;

	/**
	 * ModelDriven的机制：ValueStack
	 */
	private MessageInfoBean messageInfoBean = new MessageInfoBean();

	@Override
	public MessageInfoBean getModel()
	{
		return messageInfoBean;
	}

	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String initIndexPage(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		List<PageQueryResult> resultList = this.messageInfoService.initIndexPage();
		List<PicsInfoBean> picsInfoList = this.picsInfoService.queryForList(new PicsInfoBean());
		this.putValueStack("resultList", resultList);
		this.putValueStack("picsInfoList", picsInfoList);

		return responseResult("index");
	}

	/**
	 * 用户浏览消息列表页
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String list(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String pageNo = request.getParameter("pageNo");
		if(pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}
//    	if(messageInfoBean.getMessageType() == 0) {
//    		//默认是通知通告
//    		messageInfoBean.setMessageType(SystemConstantType.MESSAGE_TYPE_NOTICE);
//    	}
		pageQueryResult = this.messageInfoService.queryForPage(messageInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("typeName", this.messageTypeToModuleName(messageInfoBean.getMessageType()));
		this.putValueStack("messageInfoBean", messageInfoBean);
		return responseResult("list");
	}

	/**
	 * 用户浏览消息详细页
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	public String detail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		MessageInfoBean messageInfoBean = this.messageInfoService.detail(this.messageInfoBean);
		this.putValueStack("messageInfoBean", messageInfoBean);
		return responseResult("detail");
	}

	/**
	 * 后台管理消息列表页
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String mlist(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String pageNo = request.getParameter("pageNo");
		if(pageNo != null && !pageNo.equals("")) {
			pageQueryResult.setPageNo(Integer.parseInt(pageNo));
		}
//    	if(messageInfoBean.getMessageType() == 0) {
//    		//默认是通知通告
//    		messageInfoBean.setMessageType(SystemConstantType.MESSAGE_TYPE_NOTICE);
//    	}
		pageQueryResult = this.messageInfoService.queryForPage(messageInfoBean, pageQueryResult);
		this.putValueStack("pageQueryResult", pageQueryResult);
		this.putValueStack("typeName", this.messageTypeToModuleName(messageInfoBean.getMessageType()));
		this.putValueStack("moduleSign", this.messageTypeToModuleSign(messageInfoBean.getMessageType()));
		return responseResult("mlist");
	}

	/**
	 * 用户浏览消息详细页
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String mdetail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		messageInfoBean = this.messageInfoService.findById(messageInfoBean.getMessageId());
		this.putValueStack("messageInfoBean", messageInfoBean);
		String moduleSign = messageTypeToModuleSign(messageInfoBean.getMessageType());
		this.putValueStack("moduleSign", moduleSign);
		return responseResult("mdetail");
	}

	/**
	 * 消息删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 */
	@LoginFilter(needLogin=true)
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		this.messageInfoService.delete(messageInfoBean);
		return responseResult("delete");
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
		this.putValueStack("typeName", "新增"+this.messageTypeToModuleName(messageInfoBean.getMessageType()));
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
		messageInfoBean = this.messageInfoService.findById(messageInfoBean.getMessageId());
		this.putValueStack("messageInfoBean", messageInfoBean);
		this.putValueStack("typeName", "修改"+this.messageTypeToModuleName(messageInfoBean.getMessageType()));
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
		List<MessageAttachBean> list = new ArrayList<MessageAttachBean>();
		if(image != null) {
			//图片附件
			for(int i = 0; i < image.size(); i++) {
				if(image.get(i) == null) {
					continue;
				}
				MessageAttachBean messageAttachBean = new MessageAttachBean();
				messageAttachBean.setAttachType(SystemConstantType.MESSAGE_ATTACH_IMAGE);
				messageAttachBean.setAttachName(imageFileName.get(i));
				messageAttachBean.setFile(image.get(i));

				list.add(messageAttachBean);
			}
		}
		if(file != null) {
			//文件附件
			for(int i = 0; i < file.size(); i++) {
				if(file.get(i) == null) {
					continue;
				}
				MessageAttachBean messageAttachBean = new MessageAttachBean();
				messageAttachBean.setAttachType(SystemConstantType.MESSAGE_ATTACH_FILE);
				messageAttachBean.setAttachName(fileFileName.get(i));
				messageAttachBean.setFile(file.get(i));

				list.add(messageAttachBean);
			}
		}
		messageInfoBean.setMessageAttchs(new HashSet<MessageAttachBean>(list));

		//需要删除的附件ID
		String deleteAttachIds = request.getParameter("deletedAttachIds");
		messageInfoBean.setDeleteAttachIds(deleteAttachIds);

		//新增或更新
		if(messageInfoBean.getMessageId() == 0) {
			int messageId = this.messageInfoService.insert(messageInfoBean);
			messageInfoBean.setMessageId(messageId);
		}else {
			this.messageInfoService.update(messageInfoBean);
		}

		return responseResult("save", messageInfoBean.getMessageId());
	}

	/**
	 * 显示图片
	 * @param request
	 * @param response
	 * @throws ApplicationException
	 */
	public void showImage(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String url = request.getParameter("url");
		FileUtil.showImage(response, SystemParamCache.getInstance().get("baseurl") + url);
	}

	/**
	 * 文件下载类
	 * @param request
	 * @param response
	 * @throws ApplicationException
	 */
	public void downFile(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		String attachId = request.getParameter("id");
		if(attachId == null || "".equals(attachId)) {
			throw new ApplicationException("下载文件名为空");
		}
		MessageAttachBean messageAttachBean = messageAttachService.findById(Integer.parseInt(attachId));
		FileUtil.downFile(response, messageAttachBean.getAttachName(), SystemParamCache.getInstance().get("baseurl") + messageAttachBean.getAttachUrl());
	}

	/**
	 * 通过消息类型获取功能标题
	 * @param messageType
	 * @return
	 */
	private String messageTypeToModuleName(int messageType) {
		String typeName = "";
		switch (messageType) {
			case SystemConstantType.MESSAGE_TYPE_NOTICE:
				typeName = "通知公示";
				break;
			case SystemConstantType.MESSAGE_TYPE_MATCHINTRO:
				typeName = "赛事简介";
				break;
			case SystemConstantType.MESSAGE_TYPE_ACMINTRO:
				typeName = "工大ACM简介";
				break;
			case SystemConstantType.MESSAGE_TYPE_LASTMATCH:
				typeName = "最近赛事";
				break;
			case SystemConstantType.MESSAGE_TYPE_TEAM:
				typeName = "工大团队";
				break;
			case SystemConstantType.MESSAGE_TYPE_SCORE:
				typeName = "竞赛成绩";
				break;
			default:
				typeName = "搜索结果";
				break;
		}
		return typeName;
	}

	/**
	 * 通过消息类型获取模块标识
	 * @param messageType
	 * @return
	 */
	private String messageTypeToModuleSign(int messageType) {
		String moduleSign = "";
		switch(messageType) {
			case SystemConstantType.MESSAGE_TYPE_NOTICE:
				moduleSign = "NOTICE";
				break;
			case SystemConstantType.MESSAGE_TYPE_MATCHINTRO:
				moduleSign = "MATCHINTRO";
				break;
			case SystemConstantType.MESSAGE_TYPE_ACMINTRO:
				moduleSign = "ACMMANAGER";
				break;
			case SystemConstantType.MESSAGE_TYPE_LASTMATCH:
				moduleSign = "LASTMATCH";
				break;
			case SystemConstantType.MESSAGE_TYPE_TEAM:
				moduleSign = "TEAMMANAGER";
				break;
			case SystemConstantType.MESSAGE_TYPE_SCORE:
				moduleSign = "SCOREMANAGER";
				break;
		}
		return moduleSign;
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
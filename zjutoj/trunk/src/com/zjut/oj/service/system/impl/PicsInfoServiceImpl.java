package com.zjut.oj.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.cache.SystemParamCache;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.entity.system.PicsInfoBean;
import com.zjut.oj.service.system.PicsInfoService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.FileUtil;
import com.zjut.oj.util.QueryHelper;

@Service("picsInfoService")
@Transactional
public class PicsInfoServiceImpl extends ServiceSupport implements PicsInfoService
{
	/**
	 * 首页图片新增
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(List<PicsInfoBean> picsInfoList, String deleteAttachIds) throws ApplicationException{
		for(PicsInfoBean picsInfoBean : picsInfoList) {
			//文件上传
			String url = FileUtil.fileUpdate(picsInfoBean.getFile(), "", picsInfoBean.getPicTitle(), "home", SystemParamCache.getInstance().get("baseurl").toString());
			picsInfoBean.setPicUrl(url);
			picsInfoDao.insert(picsInfoBean);
		}

		//删除附件
		if(deleteAttachIds != null && !deleteAttachIds.equals("")) {
			String[] ids = deleteAttachIds.split(",");
			for(String id : ids) {
				if(!id.equals("")) {
					PicsInfoBean picsInfoBean = picsInfoDao.findById(Integer.parseInt(id));
					picsInfoDao.delete(picsInfoBean);
				}
			}
		}

		QueryHelper queryHelper = new QueryHelper();
		queryHelper.createClass(PicsInfoBean.class, "a");
		picsInfoList = picsInfoDao.queryForList(queryHelper);
		if(picsInfoList.isEmpty()) {
			throw new ApplicationException("首页图片不能为空");
		}
	}

	@Override
	public void delete(PicsInfoBean picsInfoBean) throws ApplicationException{
		picsInfoDao.delete(picsInfoBean);
	}

	@Override
	public void update(PicsInfoBean picsInfoBean) throws ApplicationException{
		picsInfoDao.update(picsInfoBean);
	}

	@Override
	public PicsInfoBean findById(Integer id) throws ApplicationException{
		return picsInfoDao.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PicsInfoBean> queryForList(PicsInfoBean picsInfoBean)
			throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(PicsInfoBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<PicsInfoBean> list = this.picsInfoDao.queryForList(queryHelper);
		return list;
	}
}
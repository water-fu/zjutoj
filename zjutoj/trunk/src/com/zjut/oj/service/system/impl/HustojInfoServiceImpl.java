package com.zjut.oj.service.system.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.HustojInfoBean;
import com.zjut.oj.entity.system.HustojUserBean;
import com.zjut.oj.service.system.HustojInfoService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;
import com.zjut.oj.util.encrypt.EncryptFactory;
import com.zjut.oj.util.encrypt.IEncrypt;

@Service("hustojInfoService")
@Transactional
public class HustojInfoServiceImpl extends ServiceSupport implements HustojInfoService {
	@Override
	public int insert(HustojInfoBean hustojInfoBean) throws ApplicationException {
		return hustojInfoDao.insert(hustojInfoBean);
	}

	/**
	 * 删除
	 * @see com.zjut.oj.service.system.HustojInfoService#delete(com.zjut.oj.entity.system.HustojInfoBean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(HustojInfoBean hustojInfoBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(HustojUserBean.class, "a");
		queryHelper.addCondition("a.matchId=?", hustojInfoBean.getMatchId());
		List<HustojUserBean> list = this.hustojUserDao.queryForList(queryHelper);
		if(!list.isEmpty()) {
			throw new ApplicationException("该竞赛已经有用户报名，不能进行删除");
		}

		hustojInfoBean = hustojInfoDao.findById(hustojInfoBean.getMatchId());
		hustojInfoDao.delete(hustojInfoBean);
	}

	@Override
	public void update(HustojInfoBean hustojInfoBean) throws ApplicationException {
		hustojInfoDao.update(hustojInfoBean);
	}

	@Override
	public HustojInfoBean findById(Integer id) throws ApplicationException {
		return hustojInfoDao.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HustojInfoBean> queryForList(HustojInfoBean hustojInfoBean)
			throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(HustojInfoBean.class, "a");
		queryHelper.addOrderProperty("operDate", false);
		List<HustojInfoBean> list = this.hustojInfoDao.queryForList(queryHelper);
		return list;
	}

	@Override
	public PageQueryResult queryForPage(HustojInfoBean hustojInfoBean,
										PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(HustojInfoBean.class, "a");
		queryHelper.addOrderProperty("matchId", true);
		pageQueryResult = this.hustojInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	/**
	 * 竞赛报名
	 *
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public HustojInfoBean matchSign(String mid) throws ApplicationException {
		String matchId = "";
		try {
			IEncrypt iEncrypt = EncryptFactory.getInstance("AESEncrypt");
			matchId = iEncrypt.decodePassword(mid, SystemConstantType.PASS_SALT);
			Integer.parseInt(matchId);
		} catch (Exception e) {
			throw new ApplicationException("解析参数失败，请勿修改参数的字母", e);
		}

		QueryHelper queryHelper = new QueryHelper(HustojInfoBean.class, "a");
		queryHelper.addCondition("a.matchId=?", Integer.parseInt(matchId));
		List<HustojInfoBean> list = this.hustojInfoDao.queryForList(queryHelper);
		if (list.isEmpty()) {
			throw new ApplicationException("您填写的竞赛不存在，请勿修改参数的字母");
		}

		HustojInfoBean hustojInfoBean = list.get(0);
		long curr = new Date().getTime();
		long signFrom = hustojInfoBean.getSignFrom().getTime();
		long signEnd = hustojInfoBean.getSignEnd().getTime();

		// 还未到报名
		if (curr < signFrom) {
			throw new ApplicationException(hustojInfoBean.getMatchTitle() + ",竞赛报名时间为: "
					+ hustojInfoBean.getSignFrom() + ",请等待");
		}

		// 报名截至
		if (curr > signEnd) {
			throw new ApplicationException(hustojInfoBean.getMatchTitle() + ",竞赛报名已经结束");
		}

		return hustojInfoBean;
	}
}
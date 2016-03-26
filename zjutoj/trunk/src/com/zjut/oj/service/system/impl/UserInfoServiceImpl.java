package com.zjut.oj.service.system.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.match.ContestUserBean;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;
import com.zjut.oj.util.encrypt.EncryptFactory;
import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.service.system.UserInfoService;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl extends ServiceSupport implements UserInfoService
{
	public int insert(UserInfoBean userInfoBean) throws ApplicationException{
		return userInfoDao.insert(userInfoBean);
	}

	public void delete(UserInfoBean userInfoBean) throws ApplicationException{
		userInfoDao.delete(userInfoBean);
	}

	public void update(UserInfoBean userInfoBean) throws ApplicationException{
		userInfoDao.update(userInfoBean);
	}

	public UserInfoBean findById(long id) throws ApplicationException{
		return null;
	}

	@Override
	public PageQueryResult queryForPage(UserInfoBean userInfoBean, PageQueryResult pageQueryResult) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(UserInfoBean.class, "a");
		pageQueryResult = this.userInfoDao.queryForPage(pageQueryResult, queryHelper);
		return pageQueryResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void importExcelDataSave(HttpServletRequest request, Object[] obj)
			throws ApplicationException {
		int contestId = Integer.parseInt(request.getParameter("contestId"));
		if(obj.length == 1) {
			//判断用户表中是否存在，不存在新增。
			UserInfoBean userInfoBean = (UserInfoBean) obj[0];

			QueryHelper queryHelper = new QueryHelper(UserInfoBean.class, "a");
			queryHelper.addCondition("a.loginSign=?", userInfoBean.getLoginSign());
			List<UserInfoBean> list = userInfoDao.queryForList(queryHelper);
			if(list.isEmpty()) {
				userInfoBean.setUserType(3);
				userInfoBean.setLoginPass(EncryptFactory.getInstance("MD5Encrypt").encodePassword("zjut123456", SystemConstantType.PASS_SALT));
				userInfoBean.setUserMail(" ");
				userInfoBean.setUserName(userInfoBean.getLoginSign());

				userInfoBean.setUserId(userInfoDao.insert(userInfoBean));
			}else {
				userInfoBean = list.get(0);
			}

			queryHelper.clear();
			queryHelper = new QueryHelper(ContestUserBean.class, "a");
			queryHelper.addCondition("a.userInfo=?", userInfoBean);
			queryHelper.addCondition("a.contestId=?", contestId);
			List<ContestUserBean> list1 = contestUserDao.queryForList(queryHelper);
			if(!list1.isEmpty()) {
				throw new ApplicationException("登录名[" + userInfoBean.getLoginSign() + "]已经存在");
			}

			ContestUserBean contestUserBean = new ContestUserBean();
			contestUserBean.setUserInfo(userInfoBean);
			contestUserBean.setContestId(contestId);

			contestUserDao.insert(contestUserBean);
		}
	}
}
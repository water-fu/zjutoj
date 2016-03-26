package com.zjut.oj.service.system.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.common.ServiceSupport;
import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.service.system.LoginInfoService;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.QueryHelper;
import com.zjut.oj.util.encrypt.EncryptFactory;
import com.zjut.oj.util.encrypt.IEncrypt;

@Transactional
@Service("loginInfoService")
public class LoginInfoServiceImpl extends ServiceSupport implements LoginInfoService
{

	@SuppressWarnings("unchecked")
	@Override
	public UserInfoBean login(UserInfoBean userInfoBean) throws ApplicationException {
		QueryHelper queryHelper = new QueryHelper(UserInfoBean.class, "a");
		queryHelper.addCondition("a.loginSign=?", new Object[]{userInfoBean.getLoginSign()});
		List<UserInfoBean> list = this.loginInfoDao.queryForList(queryHelper);
		if(list.isEmpty()) {
			throw new ApplicationException("用户名不存在");
		}
		if(list.size() > 1) {
			throw new ApplicationException("存在重复用户名，请联系系统管理员");
		}
		IEncrypt iEncrypt = EncryptFactory.getInstance("MD5Encrypt");
		if(!iEncrypt.isPasswordValid(list.get(0).getLoginPass(), userInfoBean.getLoginPass(), SystemConstantType.PASS_SALT)) {
			throw new ApplicationException("密码错误");
		}
		return list.get(0);
	}

	@Override
	public void loginOut() {

	}

}
package com.zjut.oj.common;

import java.util.HashMap;
import java.util.Map;

import com.zjut.oj.reader.HustOjFinalUserReader;
import com.zjut.oj.reader.HustOjUserReader;
import com.zjut.oj.reader.UserInfoReader;
import com.zjut.oj.service.system.HustojFinalUserService;
import com.zjut.oj.service.system.HustojUserService;
import com.zjut.oj.service.system.UserInfoService;

/**
 * 配置表名对应的dataReader和service
 * @author fusj
 */
public class TableNameMapper {
	@SuppressWarnings("rawtypes")
	private static final Map<String, Class[]> map = new HashMap<String, Class[]>();

	static {
		// 用户信息导入
		map.put("system_user_info", new Class[] { UserInfoReader.class, UserInfoService.class });
		// 竞赛用户导入
		map.put("system_hustoj_user", new Class[] { HustOjUserReader.class, HustojUserService.class });

		map.put("final_hustoj_user", new Class[] {HustOjFinalUserReader.class, HustojFinalUserService.class});
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getClazz(String tableName) {
		return map.get(tableName);
	}
}

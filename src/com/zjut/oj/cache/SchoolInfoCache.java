/**
 * Tianque.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.zjut.oj.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.ConfigParse;

/**
 * 总体说明
 * 	学院下拉列表缓存
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: SchoolInfoCache.java,v 0.1 2015-3-30 下午6:46:27 Exp $
 */
public class SchoolInfoCache extends BaseCache {

	private final Logger logger = LoggerFactory.getLogger(SchoolInfoCache.class);

	/**
	 *
	 */
	public SchoolInfoCache() {
		setCacheBool(true);
		super.setCacheName("学院信息");
	}

	/**
	 * 得到缓存类的实例
	 */
	public static synchronized SchoolInfoCache getInstance() throws ApplicationException {
		SchoolInfoCache schoolInfoCache = (SchoolInfoCache) BaseCache.getCachedInstance(SchoolInfoCache.class.getName());
		if(schoolInfoCache == null) {
			schoolInfoCache = new SchoolInfoCache();
			schoolInfoCache.doSmartLoad();
			BaseCache.setCachedInstance(SchoolInfoCache.class.getName(),
					schoolInfoCache);
		}
		return schoolInfoCache;
	}

	/**
	 *
	 */
	@Override
	protected void reloadAllData() throws ApplicationException {
		synchronized(mutex)
		{
			logger.info(">>>>> 开始获取学院信息...");
			try {
				List<String> list = ConfigParse.parstTXTFile("school.txt");
				int i = 1;
				for (String string : list) {
					put(i++, string);
				}
			} catch (IOException e) {
				throw new ApplicationException("加载学院信息数据异常");
			}
		}
	}

	/**
	 * 获取下拉列表的数据
	 *
	 * @return
	 * @throws ApplicationException
	 */
	public List<String> getSelectData() throws ApplicationException {
		List<String> list = new ArrayList<String>();

		int i = 1;
		while (i <= cacheMap.size()) {
			list.add(cacheMap.get(i++).toString());
		}
		return list;
	}
}

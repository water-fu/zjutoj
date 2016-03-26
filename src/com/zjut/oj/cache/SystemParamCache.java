package com.zjut.oj.cache;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.ConfigParse;

/**
 * 系统配置参数缓存
 * @author Administrator
 *
 */
public class SystemParamCache extends BaseCache {

	private static Log log = LogFactory.getLog(SystemParamCache.class);
	private String[] configs = {"baseurl", "mail_sendAccount"};

	public SystemParamCache() {
		setCacheBool(true);
		super.setCacheName("系统参数");
	}

	/**
	 * 得到缓存类的实例
	 */
	public static synchronized SystemParamCache getInstance() throws ApplicationException {
		SystemParamCache zjutOjCache = (SystemParamCache) BaseCache.getCachedInstance(SystemParamCache.class.getName());
		if(zjutOjCache == null) {
			zjutOjCache = new SystemParamCache();
			zjutOjCache.doSmartLoad();
			BaseCache.setCachedInstance(SystemParamCache.class.getName(),
					zjutOjCache);
		}
		return zjutOjCache;
	}

	@Override
	protected void reloadAllData() throws ApplicationException {
		synchronized(mutex)
		{
			log.info(">>>>> 开始获取系统参数...");
			Map<String, String> map = ConfigParse.parseConfig("zjutoj.properties", configs);
			for(String config : configs) {
				put(config, map.get(config));
			}
		}
	}
}

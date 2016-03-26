package com.zjut.oj.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zjut.oj.entity.study.TypeInfoBean;
import com.zjut.oj.service.study.TypeInfoService;
import com.zjut.oj.util.ApplicationException;

/**
 * 类型信息缓存
 * @author Administrator
 *
 */
public class TypeInfoCache extends BaseCache {

	private static Log log = LogFactory.getLog(TypeInfoCache.class);

	private TypeInfoService typeInfoService = (TypeInfoService) ac.getBean("typeInfoService");

	public TypeInfoCache() {
		setCacheBool(true);
		super.setCacheName("类型信息");
	}

	/**
	 * 得到缓存类的实例
	 */
	public static synchronized TypeInfoCache getInstance() throws ApplicationException {
		TypeInfoCache typeInfoCache = (TypeInfoCache) BaseCache.getCachedInstance(TypeInfoCache.class.getName());
		if(typeInfoCache == null) {
			typeInfoCache = new TypeInfoCache();
			typeInfoCache.doSmartLoad();
			BaseCache.setCachedInstance(TypeInfoCache.class.getName(),
					typeInfoCache);
		}
		return typeInfoCache;
	}

	@Override
	protected void reloadAllData() throws ApplicationException {
		synchronized(mutex)
		{
			log.info(">>>>> 开始获取类型信息...");
			List<TypeInfoBean> list = typeInfoService.queryForList(new TypeInfoBean());
			for(TypeInfoBean typeInfoBean : list) {
				put(typeInfoBean.getTypeId(), typeInfoBean.getTypeName());
			}
		}
	}

	/**
	 * 获取下拉列表的数据
	 *
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("rawtypes")
	public List<String> getSelectData() throws ApplicationException {
		List<String> list = new ArrayList<String>();
		Iterator it = cacheMap.keySet().iterator();
		while (it.hasNext()) {
			Object key = (Object) it.next();
			list.add(cacheMap.get(key).toString());
		}
		return list;
	}
}

package com.zjut.oj.common;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;
import com.zjut.oj.util.QueryHelper;

@Repository
public abstract class DaoSupport<T> extends HibernateDaoSupport implements IDaoSupport<T> {
	private Log log = LogFactory.getLog(DaoSupport.class);
	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public DaoSupport() {
		this.clazz = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass(), 0);
	}

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public T findById(Integer id) throws ApplicationException {
		log.debug("enter findById");
		T obj = this.getHibernateTemplate().get(clazz, id);
		if (obj == null) {
			obj = this.newInstace();
		}
		log.debug("exit findById");
		return obj;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List queryForList(final QueryHelper queryHelper) throws ApplicationException {
		return this.getHibernateTemplate().execute(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				log.debug("enter queryForList");
				List<Object> parameters = queryHelper.getParameters();
				Query query = session.createQuery(queryHelper.getListQueryHql());

				if (parameters != null) {
					for (int i = 0; i < parameters.size(); i++) {
						query.setParameter(i, parameters.get(i));
					}
				}
				List list = query.list();
				log.debug("exit queryForList");
				return list;
			}
		});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageQueryResult queryForPage(final PageQueryResult pageQueryResult, final QueryHelper queryHelper)
			throws ApplicationException {
		return this.getHibernateTemplate().execute(new HibernateCallback<PageQueryResult>() {
			@Override
			public PageQueryResult doInHibernate(Session session) throws HibernateException, SQLException {
				log.debug("enter queryForMap");
				List<Object> parameters = queryHelper.getParameters();
				Query query = session.createQuery(queryHelper.getListQueryHql());

				if (parameters != null) {
					for (int i = 0; i < parameters.size(); i++) {
						query.setParameter(i, parameters.get(i));
					}
				}
				query.setFirstResult(pageQueryResult.getStartNum());
				query.setMaxResults(pageQueryResult.getPageSize());

				List list = query.list();

				Query countQuery = session.createQuery(queryHelper.getCountQueryHql());
				if (parameters != null) {
					for (int i = 0; i < parameters.size(); i++) {
						countQuery.setParameter(i, parameters.get(i));
					}
				}
				Long count = (Long) countQuery.uniqueResult();

				pageQueryResult.setTotalCount(count);
				pageQueryResult.setList(list);

				log.debug("exit queryForMap");
				return pageQueryResult;
			}
		});
	}

	@Override
	public int insert(T t) throws ApplicationException {
		log.debug("enter insert");
		int id = (Integer) this.getHibernateTemplate().save(t);
		log.debug("exit insert");
		return id;
	}

	@Override
	public void update(T t) throws ApplicationException {
		log.debug("enter update");
		this.getHibernateTemplate().update(t);
		log.debug("exit update");
	}

	@Override
	public void delete(T t) throws ApplicationException {
		log.debug("enter delete");
		this.getHibernateTemplate().delete(t);
		log.debug("exit delete");
	}

	/**
	 * 工具方法，返回空对象
	 * @param clazz
	 * @return
	 * @throws ApplicationException
	 */
	private T newInstace() throws ApplicationException {
		try {
			T obj = this.clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});
			return obj;
		} catch (Exception e) {
			throw new ApplicationException("创建" + this.clazz.getName() + "类异常，请联系系统管理员");
		}
	}
}

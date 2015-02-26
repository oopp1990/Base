package cq.base.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import cq.base.dao.BaseDao;
import cq.base.entity.BaseBean;
import cq.base.entity.LogBean;
import cq.base.util.BaseUtil;

public class SSH2BaseDaoImpl<T extends BaseBean> extends HibernateTemplate implements BaseDao<T> {

	private AnnotationSessionFactoryBean sessionFactory;
	
	private Class entityClass;

	public SSH2BaseDaoImpl() {
    	try {
    		if(!this.getClass().equals(SSH2BaseDaoImpl.class)){
            	entityClass=BaseUtil.getTemplateClass(this.getClass());
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	/** 
	 * ͨ��Spring��ʵ��ע��SessionFactory�����
	 */
	public void setSessionFactory(AnnotationSessionFactoryBean sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Class getTableClass() {
		// TODO Auto-generated method stub
		return entityClass;
	}
	public void setTableClass(Class entityClass ) {
		this.entityClass = entityClass;
	}
	public boolean add(T baseBean) throws Exception {

//		if (baseBean.getCreateTime() == null) {
//			baseBean.setCreateTime(BaseUtil.getNowWithSqlDate());
//		}
//		if (baseBean.getUpdateTime() == null) {
//			baseBean.setUpdateTime(BaseUtil.getNowWithSqlDate());
//		}
		super.save(baseBean);
		return true;
	}
	
	public boolean deletes(Serializable[] ids) throws Exception {
		if (ids == null) {
			throw new Exception("�������鲻��Ϊ��");
		}
		if(this.isLogicDeletes()){
			for (int i = 0; i < ids.length; i++) {
				BaseBean baseBean = get(ids[i]);
//				baseBean.setDeleted(true);
				this.update(baseBean);
			}
			return true;
		}
		List dataList = new ArrayList();
		for (int i = 0; i < ids.length; i++) {
			Object obj = get(ids[i]);
			dataList.add(obj);
		}
		super.clear();
		super.deleteAll(dataList);
		return true;
	}

	public boolean delete(T baseBean) throws Exception {
		// TODO Auto-generated method stub
		super.delete(baseBean);
		return true;
	}

	public boolean update(T baseBean) throws Exception {
//		if (baseBean.getUpdateTime() == null) {
//			baseBean.setUpdateTime(BaseUtil.getNowWithSqlDate());
//		}
		super.update(baseBean);
		return true;
	}

	public List<T> list() throws Exception {
		List<T> baseList = new ArrayList<T>();
		String hql = "from " + getTableClass().getSimpleName();
		if (isListByLogicDelete()) {
			hql = getNotLogisDelelesHql(hql);
		}
		this.setCacheQueries(true);
		baseList = super.find(hql);
		if(baseList==null){
			baseList=new ArrayList<T>();
		}
		return baseList;
	}

	public T get(Serializable id) throws Exception {
		T baseBean = (T) super.get(getTableClass(), id);
		return baseBean;
	}

	public List<T> list(String hql) throws Exception {
		if (isListByLogicDelete()) {
			hql = getNotLogisDelelesHql(hql);
		}
		List<T> baseList = new ArrayList<T>();
		this.setCacheQueries(true);
		baseList = super.find(hql);
		if(baseList==null){
			baseList=new ArrayList<T>();
		}
		return baseList;
	}

	public List<T> listBySql(String sql) throws Exception {
		if (isListByLogicDelete()) {
			sql = getNotLogisDelelesHql(sql);
		}
		SQLQuery q = this.getSession().createSQLQuery(sql);
		q.addEntity(getTableClass());
		//q.setCacheable(true);
		List<T> baseList=q.list();
		if(baseList==null){
			baseList=new ArrayList<T>();
		}
		return baseList;
	}

	public int count() throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) from "
				+ this.getTableClass().getSimpleName();
		if (isListByLogicDelete()) {
			sql = getNotLogisDelelesHql(sql);
		}
		int count = Integer.parseInt(getSession().createQuery(sql)
				.uniqueResult()
				+ "");
		return count;
	}

	public int count(String hql) throws Exception {
		// TODO Auto-generated method stub
		String sql = "select count(*) " + hql.substring(hql.indexOf("from"));
		if (isListByLogicDelete()) {
			sql = getNotLogisDelelesHql(sql);
		}
		int count = Integer.parseInt(getSession().createQuery(sql)
				.uniqueResult()
				+ "");
		return count;
	}

	public List<T> list(String hql, int pageNum, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		if (isListByLogicDelete()) {
			hql = getNotLogisDelelesHql(hql);
		}
		Query q = getSession().createQuery(hql);
		q.setFirstResult((pageNum - 1) * pageSize);
		q.setMaxResults(pageSize);
//		q.setCacheable(true);
		List<T> baseList=q.list();
		if(baseList==null){
			baseList=new ArrayList<T>();
		}
		return baseList;
	}

	public List<T> list(DetachedCriteria dc, int pageNum, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		Criteria c = dc.getExecutableCriteria(getSession());
		c.setFirstResult((pageNum - 1) * pageSize);
		c.setMaxResults(pageSize);
		c.setCacheable(true);
		List<T> baseList=c.list();
		if(baseList==null){
			baseList=new ArrayList<T>();
		}
		return baseList;
	}

	public int count(DetachedCriteria dc)
			throws Exception {
		// TODO Auto-generated method stub
		Criteria c = dc.getExecutableCriteria(getSession());
		int totalCount = ((Integer) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();  
		return totalCount;
	}

	public List<T> listBySql(String sql, int pageNum, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		if (isListByLogicDelete()) {
			sql = getNotLogisDelelesHql(sql);
		}
		SQLQuery q = this.getSession().createSQLQuery(sql);
		q.setFirstResult((pageNum - 1) * pageSize);
		q.setMaxResults(pageSize);
		q.addEntity(getTableClass());
		//q.setCacheable(true);
		List<T> baseList=q.list();
		if(baseList==null){
			baseList=new ArrayList<T>();
		}
		return baseList;
	}

	public int countBySql(String sql) throws Exception {
		// TODO Auto-generated method stub
		sql = "select count(*) from (" + sql + ") as a";
		if (isListByLogicDelete()) {
			sql = getNotLogisDelelesHql(sql);
		}
		int count = Integer.parseInt(getSession().createSQLQuery(sql)
				.uniqueResult()
				+ "");
		return count;
	}

	public int execute(String sql) throws Exception {
		SessionFactoryImpl impl = (SessionFactoryImpl) getSessionFactory();
		ConnectionProvider provider = impl.getConnectionProvider();
		Connection conn = provider.getConnection();
		int result = conn.createStatement().executeUpdate(sql);
		return result;
	}

	public T load(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		T baseBean = (T) super.load(getTableClass(), id);
		return baseBean;
	}

	public List<Map> listMapBySql(String sql, int pageNum, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		if (isListByLogicDelete()) {
			sql = getNotLogisDelelesHql(sql);
		}
		SQLQuery q = this.getSession().createSQLQuery(sql);
		q.setFirstResult((pageNum - 1) * pageSize);
		q.setMaxResults(pageSize);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//		q.setCacheable(true);
		List<Map> baseList=q.list();
		if(baseList==null){
			baseList=new ArrayList<Map>();
		}
		return baseList;
	}

	public List<Map> listMapBySql(String sql) throws Exception {
		// TODO Auto-generated method stub
		if (isListByLogicDelete()) {
			sql = getNotLogisDelelesHql(sql);
		}
		SQLQuery q = this.getSession().createSQLQuery(sql);
		q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//		q.setCacheable(true);
		List<Map> baseList=q.list();
		if(baseList==null){
			baseList=new ArrayList<Map>();
		}
		return baseList;
	}

	public boolean isLogicDeletes() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isListByLogicDelete() {
		// TODO Auto-generated method stub
		String str = " select * from user where id=:id";
		Map<String, Object> pa = new HashMap<String, Object>();
		pa.put("id", "");
		
		return false;
	}

	protected String getNotLogisDelelesHql(String hql) {
		String hqlStr = "";
		hql = hql.replace("from(", "from (");
		if (hql.toUpperCase().indexOf("from ".toUpperCase()) >= 0) {
			while (hql.toUpperCase().indexOf("from ".toUpperCase()) >= 0) {
				String headStr = hql.substring(0, hql.toUpperCase().indexOf(
						"from ".toUpperCase()));
				String footStr = hql.substring(hql.toUpperCase().indexOf(
						"from ".toUpperCase()) + 5);
				hql = headStr + "f_r_o_m" + footStr;
			}
			String[] strs = hql.split("f_r_o_m");
			for (int i = 0; i < strs.length; i++) {
				if (i > 0) {
					hqlStr += " from ";
					String str = BaseUtil.firstTrim(strs[i]);
					if (str.indexOf("(") == 0) {
						hqlStr += strs[i];
					} else {
						if (str.toUpperCase().indexOf("where".toUpperCase()) >= 0) {
							String headStr = str.substring(0, str.toUpperCase()
									.indexOf("where".toUpperCase()) + 5);
							String footStr = str.substring(str.toUpperCase()
									.indexOf("where".toUpperCase()) + 5);
							hqlStr += headStr + " isDeleted = 0 and " + footStr;
						} else {
							if (str.toUpperCase().indexOf("as ".toUpperCase()) >= 0) {
								if (str.toUpperCase()
										.indexOf(")".toUpperCase()) >= 0) {
									// ���)��as ����
									if (str.toUpperCase().indexOf(
											"as ".toUpperCase()) < str
											.toUpperCase().indexOf(
													")".toUpperCase())) {
										if (str
												.indexOf(
														" ",
														str
																.toUpperCase()
																.indexOf(
																		"as "
																				.toUpperCase()) + 3) >= 0) {
											String headStr = str
													.substring(
															0,
															str
																	.indexOf(
																			" ",
																			str
																					.toUpperCase()
																					.indexOf(
																							"as "
																									.toUpperCase()) + 3));
											String footStr = str
													.substring(str
															.indexOf(
																	" ",
																	str
																			.toUpperCase()
																			.indexOf(
																					"as "
																							.toUpperCase()) + 3));
											hqlStr += headStr
													+ " where isDeleted = 0  "
													+ footStr;
										} else if (str
												.indexOf(
														")",
														str
																.toUpperCase()
																.indexOf(
																		"as "
																				.toUpperCase()) + 3) >= 0) {
											String headStr = str
													.substring(
															0,
															str
																	.indexOf(
																			")",
																			str
																					.toUpperCase()
																					.indexOf(
																							"as "
																									.toUpperCase()) + 3) - 1);
											String footStr = str
													.substring(str
															.indexOf(
																	")",
																	str
																			.toUpperCase()
																			.indexOf(
																					"as "
																							.toUpperCase()) + 3) - 1);
											hqlStr += headStr
													+ " where isDeleted = 0  "
													+ footStr;
										}
									} else {
										String headStr = str.substring(0, str
												.toUpperCase().indexOf(
														")".toUpperCase()));
										String footStr = str.substring(str
												.toUpperCase().indexOf(
														")".toUpperCase()));
										hqlStr += headStr
												+ " where isDeleted = 0  "
												+ footStr;
									}
								} else {
									if (str.indexOf(" ", str.toUpperCase()
											.indexOf("as ".toUpperCase()) + 3) >= 0) {
										String headStr = str
												.substring(
														0,
														str
																.indexOf(
																		" ",
																		str
																				.toUpperCase()
																				.indexOf(
																						"as "
																								.toUpperCase()) + 3));
										String footStr = str
												.substring(str
														.indexOf(
																" ",
																str
																		.toUpperCase()
																		.indexOf(
																				"as "
																						.toUpperCase()) + 3));
										hqlStr += headStr
												+ " where isDeleted = 0  "
												+ footStr;
									} else if (str.indexOf(")", str
											.toUpperCase().indexOf(
													"as ".toUpperCase()) + 3) >= 0) {
										String headStr = str
												.substring(
														0,
														str
																.indexOf(
																		")",
																		str
																				.toUpperCase()
																				.indexOf(
																						"as "
																								.toUpperCase()) + 3) - 1);
										String footStr = str
												.substring(str
														.indexOf(
																")",
																str
																		.toUpperCase()
																		.indexOf(
																				"as "
																						.toUpperCase()) + 3) - 1);
										hqlStr += headStr
												+ " where isDeleted = 0  "
												+ footStr;
									}
								}
							} else if (str.toUpperCase().indexOf(
									" ".toUpperCase()) >= 0
									&& str.toUpperCase().indexOf(
											")".toUpperCase()) >= 0) {
								if (str.toUpperCase()
										.indexOf(" ".toUpperCase()) < str
										.toUpperCase().indexOf(
												")".toUpperCase())) {
									String headStr = str.substring(0, str
											.toUpperCase().indexOf(
													" ".toUpperCase()));
									String footStr = str.substring(str
											.toUpperCase().indexOf(
													" ".toUpperCase()));
									hqlStr += headStr
											+ " where isDeleted = 0  "
											+ footStr;
								} else {
									String headStr = str.substring(0, str
											.toUpperCase().indexOf(
													")".toUpperCase()));
									String footStr = str.substring(str
											.toUpperCase().indexOf(
													")".toUpperCase()));
									hqlStr += headStr
											+ " where isDeleted = 0  "
											+ footStr;
								}
							} else if (str.toUpperCase().indexOf(
									" ".toUpperCase()) >= 0) {
								String headStr = str.substring(0, str
										.toUpperCase().indexOf(
												" ".toUpperCase()));
								String footStr = str.substring(str
										.toUpperCase().indexOf(
												" ".toUpperCase()));
								hqlStr += headStr + " where isDeleted = 0  "
										+ footStr;
							} else if (str.toUpperCase().indexOf(
									")".toUpperCase()) >= 0) {
								String headStr = str.substring(0, str
										.toUpperCase().indexOf(
												")".toUpperCase()));
								String footStr = str.substring(str
										.toUpperCase().indexOf(
												")".toUpperCase()));
								hqlStr += headStr + " where isDeleted = 0  "
										+ footStr;
							} else {
								hqlStr += str + " where isDeleted = 0 ";
							}
						}
					}
				} else {
					hqlStr += strs[i];
				}
			}
		}
		return hqlStr;
	}

	public void log(String type, String operator, String level)
			throws Exception {
		// TODO Auto-generated method stub
		LogBean logBean=new LogBean(BaseUtil.getEntityAlias(entityClass),type,operator);
		super.save(logBean);
	}
	
	public int insertOrUpdateOrDeleteByHql(String hql){
		Query q = getSession().createQuery(hql);
		int i = q.executeUpdate();
		return i;
	}
	
	
}
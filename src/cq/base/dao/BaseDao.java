package cq.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cq.base.entity.BaseBean;


public interface BaseDao<T extends BaseBean> {

	/**
	 * ���Ӧ��class
	 * @return ���Ӧ��class
	 */
	public Class getTableClass();
	
	/**
	 * ���Ƿ���Ҫ�߼�ɾ��
	 * @return
	 */
	public boolean isLogicDeletes();
	
	/**
	 * ���һ����¼
	 * @param baseBean
	 * @return
	 */
	public boolean add(T baseBean) throws Exception;
	
	/**
	 * ɾ��һ������
	 * @param baseBean ����
	 * @return
	 * @throws Exception 
	 */
	public boolean delete(T baseBean) throws Exception;
	
	/**
	 * ɾ��һЩ����¼
	 * @param ids ��������
	 * @return
	 * @throws ProcessException 
	 */
	public boolean deletes(Serializable[] ids) throws Exception;
	
	/**
	 * ����һ����¼
	 * @param baseBean
	 * @return
	 */
	public boolean update(T baseBean) throws Exception;
	
	/**
	 * ��ѯ���м�¼
	 * @return
	 */
	public List<T> list() throws Exception;
	
	/**
	 * ���hql��ѯ��¼
	 * @return
	 */
	public List<T> list(String hql) throws Exception;
	
	/**
	 * ���hql��ѯ��¼
	 * @return
	 */
	public List<T> list(String hql, int pageNum, int pageSize) throws Exception;
	
	
	/**
	 * ���sql��ѯ��¼
	 * @return
	 */
	public List<T> listBySql(String sql) throws Exception;
	
	/**
	 * ���sql��ѯ��¼
	 * @return
	 */
	public List<T> listBySql(String sql, int pageNum, int pageSize) throws Exception;
	
	/**
	 * ͳ��
	 * @return
	 */
	public int count() throws Exception;
	
	/**
	 * ���hqlͳ��
	 * @param hql
	 * @return
	 */
	public int count(String hql) throws Exception;
	
	/**
	 * ���sqlͳ��
	 * @param sql
	 * @return
	 */
	public int countBySql(String sql) throws Exception;
	

	/**
	 * ��ѯĳ����¼
	 * @param id ����
	 * @return
	 */
	public T get(Serializable id) throws Exception;
	
	/**
	 * ��ѯĳ����¼
	 * @param id ����
	 * @return
	 */
	public T load(Serializable id) throws Exception;
	
	/**
	 * ִ��sql
	 * @return Ӱ�������
	 * @throws Exception
	 */
	public int execute(String sql) throws Exception;
	
	/**
	 * ���sql��ѯ��ҳ��ݣ�����ӳ�伯��
	 * @param sql 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Map> listMapBySql(String sql, int pageNum, int pageSize) throws Exception ;
	
	/**
	 * ���sql��ѯ��ݣ�����ӳ�伯��
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<Map> listMapBySql(String sql)throws Exception;
	
	/**
	 * �洢��־
	 * @param type
	 * @param operator
	 * @param level
	 * @throws Exception
	 */
	public void log(String type,String operator,String level)throws Exception;
	
	
	int insertOrUpdateOrDeleteByHql(String hql);
	
	void setTableClass(Class entityClass );
}

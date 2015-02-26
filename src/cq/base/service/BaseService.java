package cq.base.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import cq.base.dao.BaseDao;
import cq.base.entity.BaseBean;
import cq.base.entity.PageBean;



public interface BaseService<T extends BaseBean> {
	/**
	 * �õ����ݲ�����
	 * @return
	 */
	public BaseDao getBaseDao();
	
	/**
	 * ��ѯһ��
	 * @return
	 */
	public T get(Serializable id) throws Exception;
	
	/**
	 * ɾ��һЩ
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean deletes(Serializable[] ids) throws Exception;
	
	/**
	 * ����һ��
	 * @param baseBean
	 * @return
	 * @throws Exception
	 */
	public boolean update(T baseBean) throws Exception;
	
	/**
	 * ���һ��
	 * @param baseBean
	 * @return
	 * @throws Exception
	 */
	public boolean add(T baseBean) throws Exception;
	
	/**
	 * ��ѯ��ҳ����
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean list(T baseBean,PageBean pageBean) throws Exception;
	
	/**
	 * ����ʵ���ѯ����
	 * @param baseBean
	 * @return
	 * @throws Exception
	 */
	public List<T> list(T baseBean) throws Exception;
	
	/**
	 * ����ҵ������Ĳ�����
	 * @param author
	 */
	public abstract void setAuthor(String author);
	
	/**
	 * ��ȡҵ������Ĳ�����
	 * @param author
	 */
	public abstract String getAuthor();
	
	/**
	 * ������
	 * @return
	 */
	public Field[] getClassDeclaredFields();
	
	/**
	 * ������
	 * @return
	 */
	public Field[] getClassFields();
}

package cq.base.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import cq.base.dao.BaseDao;
import cq.base.entity.BaseBean;
import cq.base.entity.PageBean;



public interface BaseService<T extends BaseBean> {
	/**
	 * 得到数据操作类
	 * @return
	 */
	public BaseDao getBaseDao();
	
	/**
	 * 查询一个
	 * @return
	 */
	public T get(Serializable id) throws Exception;
	
	/**
	 * 删除一些
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean deletes(Serializable[] ids) throws Exception;
	
	/**
	 * 更新一个
	 * @param baseBean
	 * @return
	 * @throws Exception
	 */
	public boolean update(T baseBean) throws Exception;
	
	/**
	 * 添加一个
	 * @param baseBean
	 * @return
	 * @throws Exception
	 */
	public boolean add(T baseBean) throws Exception;
	
	/**
	 * 查询分页数据
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean list(T baseBean,PageBean pageBean) throws Exception;
	
	/**
	 * 根据实体查询数据
	 * @param baseBean
	 * @return
	 * @throws Exception
	 */
	public List<T> list(T baseBean) throws Exception;
	
	/**
	 * 设置业务操作的操作人
	 * @param author
	 */
	public abstract void setAuthor(String author);
	
	/**
	 * 获取业务操作的操作人
	 * @param author
	 */
	public abstract String getAuthor();
	
	/**
	 * 防代理
	 * @return
	 */
	public Field[] getClassDeclaredFields();
	
	/**
	 * 防代理
	 * @return
	 */
	public Field[] getClassFields();
}

package cq.base.dao.impl;
import java.io.Serializable;
import java.util.List;

import cq.base.dao.BaseDao;
import cq.base.entity.BaseBean;
import cq.base.util.BaseUtil;

public class BaseDaoImpl<T extends BaseBean> implements BaseDao {
	
	private Class entityClass;
	
	public BaseDaoImpl() {
    	try {
    		if(!this.getClass().equals(BaseDaoImpl.class)){
            	entityClass=BaseUtil.getTemplateClass(this.getClass());
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public Class getTableClass() {
		// TODO Auto-generated method stub
		return entityClass;
	}

	public boolean add(BaseBean baseBean) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public int count() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int count(String hql) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public int countBySql(String sql) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean delete(BaseBean baseBean) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deletes(Serializable[] ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public int execute(String sql) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public BaseBean get(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLogicDeletes() {
		// TODO Auto-generated method stub
		return false;
	}

	public List list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List list(String hql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List list(String hql, int pageNum, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List listBySql(String sql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List listBySql(String sql, int pageNum, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List listMapBySql(String sql, int pageNum, int pageSize)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List listMapBySql(String sql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseBean load(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void log(String type, String operator, String level)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public boolean update(BaseBean baseBean) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertOrUpdateOrDeleteByHql(String hql) {
		// TODO �Զ���ɵķ������
		return 0;
	}

	@Override
	public void setTableClass(Class entityClass) {
		// TODO 自动生成的方法存根
		this.entityClass = entityClass;
	}
	
}
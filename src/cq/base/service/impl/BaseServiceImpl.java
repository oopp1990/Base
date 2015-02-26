package cq.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cq.base.dao.BaseDao;
import cq.base.entity.BaseBean;
import cq.base.entity.LogBean;
import cq.base.entity.PageBean;
import cq.base.service.BaseService;
import cq.base.util.BaseUtil;

public class BaseServiceImpl<T extends BaseBean> implements BaseService<T> {
	
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean add(T baseBean) throws Exception {
//		baseBean.setCreateTime(BaseUtil.getNowWithSqlDate());
//		baseBean.setCreateAuthor(this.getAuthor());
//		baseBean.setUpdateTime(BaseUtil.getNowWithSqlDate());
//		baseBean.setUpdateAuthor(this.getAuthor());
		boolean result=this.getBaseDao().add(baseBean);
		this.getBaseDao().log( "update", this.getAuthor(), LogBean.LEVEL_INFO);
		return result;
	}

	public boolean deletes(Serializable[] ids) throws Exception {
		// TODO Auto-generated method stub
		boolean result=this.getBaseDao().deletes(ids);
		this.getBaseDao().log( "delete", this.getAuthor(), LogBean.LEVEL_INFO);
		return result;
	}

	public T get(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return (T)this.getBaseDao().get(id);
	}

	public boolean update(T baseBean) throws Exception {
		// TODO Auto-generated method stub
//		baseBean.setUpdateTime(BaseUtil.getNowWithSqlDate());
//		baseBean.setUpdateAuthor(this.getAuthor());
		BaseBean temp=getBaseDao().get(baseBean.getId());
		BaseUtil.copyBean(baseBean, temp, new Class[]{Set.class});
		boolean result=this.getBaseDao().update(temp);
		if(result){
			this.getBaseDao().log( "add", this.getAuthor(), LogBean.LEVEL_INFO);
		}
		return result;
	}

	public PageBean list(T baseBean, PageBean pageBean) throws Exception {
		// TODO Auto-generated method stub
		if(pageBean==null)
		{
			try {
				List<BaseBean> baseList=this.getBaseDao().list();
				if(baseList==null){
					baseList=new ArrayList<BaseBean>();
				}
				pageBean=new PageBean();
				pageBean.setDataList(baseList);
				pageBean.setDataSize(baseList.size());
				pageBean.setPageSize(baseList.size());
				pageBean.setPageNum(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}else
		{
			List dataList=new ArrayList();
			int count=0;
			if(pageBean.toSql().trim().length()>0)
			{
				try {
					List totalList=new ArrayList();
					if(pageBean.toTotalSql().trim().length()>0){
						if(pageBean.isMapDataType()){
							totalList=getBaseDao().listMapBySql(pageBean.toTotalSql(), 1, 1);
						}else{
							totalList=getBaseDao().listBySql(pageBean.toTotalSql(), 1, 1);
						}
					}
					if(pageBean.isMapDataType()){
						dataList=getBaseDao().listMapBySql(pageBean.toSql(), pageBean.getPageNum(), pageBean.getPageSize());
					}else{
						dataList=getBaseDao().listBySql(pageBean.toSql(), pageBean.getPageNum(), pageBean.getPageSize());
					}
					dataList.addAll(totalList);
					count=getBaseDao().countBySql(pageBean.toCountSql());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(pageBean.toHql().trim().length()>0){
				try {
					dataList=getBaseDao().list(pageBean.toHql(), pageBean.getPageNum(), pageBean.getPageSize());
					count=getBaseDao().count(pageBean.toCountHql());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					pageBean.setHql("from "+getBaseDao().getTableClass().getSimpleName());
					dataList=getBaseDao().list(pageBean.toHql(), pageBean.getPageNum(), pageBean.getPageSize());
					count=getBaseDao().count(pageBean.toCountHql());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			pageBean.setDataList(dataList);
			pageBean.setDataSize(count);
		}
		return pageBean;
	}

	public List<T> list(T baseBean) throws Exception {
		// TODO Auto-generated method stub
		return this.getBaseDao().list();
	}
	
	/**
	 * 防代理
	 * @return
	 */
	public Field[] getClassDeclaredFields(){
		return this.getClass().getDeclaredFields();
	}
	
	/**
	 * 防代理
	 * @return
	 */
	public Field[] getClassFields(){
		return this.getClass().getFields();
	}

	public BaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return BaseUtil.getBaseDao(this);
	}
	
	
}

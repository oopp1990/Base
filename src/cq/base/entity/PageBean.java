package cq.base.entity;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cq.base.util.BaseUtil;

public class PageBean<T extends BaseBean> {
	
	private String listUser;		//操作用户
		
	private String hql="";			//数据查询hql条件
	
	private String countHql="";		//数据统计hql条件
		
	private String sql="";			//数据查询sql条件
	
	private String countSql="";		//数据统计sql条件
	
	private String totalSql="";		//数据合计sql条件
	
	private int pageSize=12;		//每页数据条数
	
	private int pageNum=1;			//页码
	
	private int pageCount=0;		//总页数
	
	private int dataSize=0;			//总数据条数
		
	private String query="";		//查询值
	
	private String qtype="";		//查询字段
	
	private String sortName="";		//排序字段
	
	private String sortOrder="asc";	//排序方式
	
//	private boolean existNextBtn;	//是否需要下一页按钮
//	
//	private boolean existBackBtn;	//是否需要上一页按钮
//	
//	private boolean existFirstBtn;	//是否需要首页按钮
//	
//	private boolean existLastBtn;	//是否需要尾页按钮
	
	private List<T> dataList;		//数据集合
	
	private boolean isMapDataType=false;	//数据类型
	
	private String[] fields;		//需要查询的字段
	public PageBean()
	{
		
	}

	public String toHql() {
		return hql;
	}
	
	public String toSql() {
		return sql;
	}

	public String toCountHql() {
		return countHql;
	}

	public String toCountSql() {
		return countSql;
	}

	public String toTotalSql() {
		return totalSql;
	}
	
	public void setTotalSql(String totalSql) {
		this.totalSql = totalSql;
	}

	public void setDc(DetachedCriteria dc) {
		if(this.getSortOrder().equals("asc")){
			dc.addOrder(Order.asc(sortName));
		}else
		{
			dc.addOrder(Order.asc(sortName));
		}
		if(qtype!=null&&qtype.trim().length()>0)
		{
			dc.add(Restrictions.like(qtype, query));
		}
	}

	public void setHql(String hql) {
		
		if(!(hql.indexOf(" where ")>0||hql.indexOf(" WHERE ")>0))
		{
			hql+=" where 1=1";
		}
		if(qtype!=null&&qtype.trim().length()>0)
		{
			hql+=" and "+qtype+" like '%"+query+"%' ";
		}
		this.countHql=hql;
		if(sortName!=null&&sortName.trim().length()>0&&(sql.toUpperCase().indexOf("ORDER BY")<0||sql.toUpperCase().indexOf("(ORDER BY")>0||sql.toUpperCase().indexOf("( ORDER BY")>0))
		{
			hql+=" order by "+sortName+" "+sortOrder;
		}
		this.hql = hql;
	}

	public void setSql(String sql) {
		countSql=sql;
		if(sortName!=null&&sortName.trim().length()>0&&(sql.toUpperCase().indexOf("ORDER BY")<0||sql.toUpperCase().indexOf("(ORDER BY")>0||sql.toUpperCase().indexOf("( ORDER BY")>0))
		{
			sql+=" order by "+sortName+" "+sortOrder;
		}
		this.sql = sql;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public int getPageCount() {
		return dataSize%pageSize==0?dataSize/pageSize:dataSize/pageSize+1;
	}
/*
	public boolean isExistNextBtn() {
		return getPageCount()>0&&pageNum<getPageCount();
	}

	public boolean isExistBackBtn() {
		return getPageCount()>0&&pageNum>1;
	}

	public boolean isExistFirstBtn() {
		return getPageCount()>0&&pageNum>1;
	}

	public boolean isExistLastBtn() {
		return getPageCount()>0&&pageNum<getPageCount();
	}
*/
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isMapDataType() {
		return isMapDataType;
	}

	public void setMapDataType(boolean isMapDataType) {
		this.isMapDataType = isMapDataType;
	}
	/*
	public String toString() {
		return new BaseUtil().getPageBeanJsonStr(this);
	}
	*/
	public List<T> getDataList()
	{
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String getListUser() {
		return listUser;
	}

	public void setListUser(String listUser) {
		this.listUser = listUser;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}

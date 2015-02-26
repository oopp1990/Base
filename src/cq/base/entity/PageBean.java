package cq.base.entity;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cq.base.util.BaseUtil;

public class PageBean<T extends BaseBean> {
	
	private String listUser;		//�����û�
		
	private String hql="";			//���ݲ�ѯhql����
	
	private String countHql="";		//����ͳ��hql����
		
	private String sql="";			//���ݲ�ѯsql����
	
	private String countSql="";		//����ͳ��sql����
	
	private String totalSql="";		//���ݺϼ�sql����
	
	private int pageSize=12;		//ÿҳ��������
	
	private int pageNum=1;			//ҳ��
	
	private int pageCount=0;		//��ҳ��
	
	private int dataSize=0;			//����������
		
	private String query="";		//��ѯֵ
	
	private String qtype="";		//��ѯ�ֶ�
	
	private String sortName="";		//�����ֶ�
	
	private String sortOrder="asc";	//����ʽ
	
//	private boolean existNextBtn;	//�Ƿ���Ҫ��һҳ��ť
//	
//	private boolean existBackBtn;	//�Ƿ���Ҫ��һҳ��ť
//	
//	private boolean existFirstBtn;	//�Ƿ���Ҫ��ҳ��ť
//	
//	private boolean existLastBtn;	//�Ƿ���Ҫβҳ��ť
	
	private List<T> dataList;		//���ݼ���
	
	private boolean isMapDataType=false;	//��������
	
	private String[] fields;		//��Ҫ��ѯ���ֶ�
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

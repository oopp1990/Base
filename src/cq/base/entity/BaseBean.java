package cq.base.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.inject.Default;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alibaba.fastjson.JSON;

import cq.base.annotation.Alias;
import cq.base.util.BaseUtil;

@MappedSuperclass
@Alias(alias = "实体记录")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseBean implements Serializable {
	// json的字段需要public声明
	// @Column(name="create_time",nullable = false,updatable = false)
	// public Date createTime=Calendar.getInstance().getTime(); //数据创建时间
	// @Column(name="create_author")
	// public String createAuthor; //数据更新人员
	// @Column(name="update_time")
	// public Date updateTime=Calendar.getInstance().getTime(); //数据更新时间
	// @Column(name="update_author")
	// public String updateAuthor; //数据更新人员
	// @Column(name="is_deleted")
	// public boolean isDeleted = false; //是否逻辑删除
	// @Transient
	// public String[] fields; //需要的查询字段
	
	
	/**
	 * 得到主键
	 * 
	 * @return
	 */
	@Transient
	public Serializable getId() {
		return BaseUtil.getEntityId(this);
	}

	/**
	 * 得到主键数组
	 * 
	 * @return
	 */
	@Transient
	public Serializable[] getIds() {
		return BaseUtil.getEntityIds(this);
	}

	/**
	 * 得到实体标识(用于日志)
	 * 
	 * @return
	 */
	@Transient
	public String getEntityTitle() {
		return BaseUtil.getEntityAlias(this.getClass());
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
	public String toHql() {
		return "from " + this.getClass().getSimpleName();
	}
	
	// public Date getCreateTime() {
	// return createTime;
	// }
	//
	// public void setCreateTime(Date createTime) {
	// this.createTime = createTime;
	// }
	//
	// public Date getUpdateTime() {
	// return updateTime;
	// }
	//
	// public void setUpdateTime(Date updateTime) {
	// this.updateTime = updateTime;
	// }
	//
	// public String getUpdateAuthor() {
	// return updateAuthor;
	// }
	//
	// public void setUpdateAuthor(String updateAuthor) {
	// this.updateAuthor = updateAuthor;
	// }
	//
	// public boolean isDeleted() {
	// return isDeleted;
	// }
	//
	// public void setDeleted(boolean isDeleted) {
	// this.isDeleted = isDeleted;
	// }
	//
	// public String getCreateAuthor() {
	// return createAuthor;
	// }
	//
	// public void setCreateAuthor(String createAuthor) {
	// this.createAuthor = createAuthor;
	// }

	// public String[] getFields() {
	// return fields;
	// }
	//
	// public void setFields(String[] fields) {
	// this.fields = fields;
	// }

	/**
	 * 防代理
	 * 
	 * @return
	 * 
	 *         public Field[] getClassDeclaredFields(){ return
	 *         this.getClass().getDeclaredFields(); }
	 */
	/**
	 * 防代理
	 * 
	 * @return
	 * 
	 *         public Field[] getClassFields(){ return
	 *         this.getClass().getFields(); }
	 */
}
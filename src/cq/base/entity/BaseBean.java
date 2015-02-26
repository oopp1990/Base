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
@Alias(alias = "ʵ���¼")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseBean implements Serializable {
	// json���ֶ���Ҫpublic����
	// @Column(name="create_time",nullable = false,updatable = false)
	// public Date createTime=Calendar.getInstance().getTime(); //���ݴ���ʱ��
	// @Column(name="create_author")
	// public String createAuthor; //���ݸ�����Ա
	// @Column(name="update_time")
	// public Date updateTime=Calendar.getInstance().getTime(); //���ݸ���ʱ��
	// @Column(name="update_author")
	// public String updateAuthor; //���ݸ�����Ա
	// @Column(name="is_deleted")
	// public boolean isDeleted = false; //�Ƿ��߼�ɾ��
	// @Transient
	// public String[] fields; //��Ҫ�Ĳ�ѯ�ֶ�
	
	
	/**
	 * �õ�����
	 * 
	 * @return
	 */
	@Transient
	public Serializable getId() {
		return BaseUtil.getEntityId(this);
	}

	/**
	 * �õ���������
	 * 
	 * @return
	 */
	@Transient
	public Serializable[] getIds() {
		return BaseUtil.getEntityIds(this);
	}

	/**
	 * �õ�ʵ���ʶ(������־)
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
	 * ������
	 * 
	 * @return
	 * 
	 *         public Field[] getClassDeclaredFields(){ return
	 *         this.getClass().getDeclaredFields(); }
	 */
	/**
	 * ������
	 * 
	 * @return
	 * 
	 *         public Field[] getClassFields(){ return
	 *         this.getClass().getFields(); }
	 */
}
package cq.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import cq.base.annotation.Alias;
import cq.base.annotation.Ids;
import cq.base.util.BaseUtil;

@Entity()
@Alias(alias="日志")
public class LogBean extends BaseBean {
	
	public static final String LEVEL_INFO="信息";
	
	public static final String LEVEL_WARNING="警告";
	
	public static final String LEVEL_ERROR="错误";
	
	@Id 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String lid;				//日志编号
	
	private String lauthor = "";	//操作人
	private Date ltime=BaseUtil.getNowTime();				//操作时间
    private String lbean = "";		//操作实体
    private String llevel= "";		//日志级别
    private String ltype = "";		//操作类型
    @Transient @Ids
	private Long[] lids;

	public Serializable getId() {
		// TODO Auto-generated method stub
		return lid;
	}

	
	public Serializable[] getIds() {
		// TODO Auto-generated method stub
		return lids;
	}
	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}
	
	public String getLauthor() {
		return lauthor;
	}

	public void setLauthor(String lauthor) {
		this.lauthor = lauthor;
	}


	public Date getLtime() {
		return ltime;
	}

	public void setLtime(Date ltime) {
		this.ltime = ltime;
	}

	public Long[] getLids() {
		return lids;
	}

	public void setLids(Long[] lids) {
		this.lids = lids;
	}
	
	public String getLbean() {
		return lbean;
	}


	public void setLbean(String lbean) {
		this.lbean = lbean;
	}


	public String getLtype() {
		return ltype;
	}


	public void setLtype(String ltype) {
		this.ltype = ltype;
	}

	/**
	 * 
	 * @param lbean 实体别称
	 * @param ltype 操作类型
	 * @param lauthor 操作人员
	 */
	public LogBean(String lbean, String ltype, String lauthor) {
		super();
		this.lauthor = lauthor;
		this.lbean = lbean;
		this.ltype = ltype;
	}
	
	
	/**
	 * 
	 * @param lbean 实体别称
	 * @param ltype 操作类型
	 * @param lauthor 操作人员
	 * @param llevel 日志级别
	 */
	public LogBean(String lbean, String ltype, String lauthor, String llevel) {
		super();
		this.lauthor = lauthor;
		this.lbean = lbean;
		this.llevel = llevel;
		this.ltype = ltype;
	}


	public LogBean() {
		super();
	}


	public String getLlevel() {
		return llevel;
	}


	public void setLlevel(String llevel) {
		this.llevel = llevel;
	}

}

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
@Alias(alias="��־")
public class LogBean extends BaseBean {
	
	public static final String LEVEL_INFO="��Ϣ";
	
	public static final String LEVEL_WARNING="����";
	
	public static final String LEVEL_ERROR="����";
	
	@Id 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String lid;				//��־���
	
	private String lauthor = "";	//������
	private Date ltime=BaseUtil.getNowTime();				//����ʱ��
    private String lbean = "";		//����ʵ��
    private String llevel= "";		//��־����
    private String ltype = "";		//��������
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
	 * @param lbean ʵ����
	 * @param ltype ��������
	 * @param lauthor ������Ա
	 */
	public LogBean(String lbean, String ltype, String lauthor) {
		super();
		this.lauthor = lauthor;
		this.lbean = lbean;
		this.ltype = ltype;
	}
	
	
	/**
	 * 
	 * @param lbean ʵ����
	 * @param ltype ��������
	 * @param lauthor ������Ա
	 * @param llevel ��־����
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

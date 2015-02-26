package cq.base.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cq.base.entity.BaseBean;
import cq.base.entity.PageBean;
import cq.base.entity.ResultBean;
import cq.base.service.BaseService;
import cq.base.util.BaseUtil;


public class BaseAction {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected String message;
	protected PrintWriter out;
	protected PageBean<BaseBean> pageBean;
	protected String hql;
	protected String sql;
	
	/**
	 * �õ�����ķ������
	 * @return
	 */
	protected BaseService getBaseService(){
		return BaseUtil.getBaseService(this);
	}
	
	/**
	 * �õ������ʵ�����
	 * @return
	 */
	protected BaseBean getBaseBean(){
		return BaseUtil.getBaseBean(this);
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public PageBean<BaseBean> getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean<BaseBean> pageBean) {
		this.pageBean = pageBean;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * ��ʼ�����л���
	 * @throws IOException 
	 */
	protected void initContext() throws IOException
	{
		response=(HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
		request=(HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
//		session=request.getSession();
//		response.setCharacterEncoding("utf-8");
		out = response.getWriter();
	}
	
	/**
	 * ��ѯһЩ
	 * @return
	 * @throws Exception
	 */
	public String listJson()
	{
		try {
			initContext();
//			if(this.getBaseBean()!=null){
//				this.getBaseBean().setUpdateAuthor(session.getAttribute("username")+"");
//			}
			if(pageBean!=null){
				pageBean.setListUser(session.getAttribute("username")+"");
				out.print(this.getBaseService().list(this.getBaseBean(), pageBean));
			}else{
				out.print(this.getBaseService().list(this.getBaseBean()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ����һ��
	 * @return
	 * @
	 */
	public String updateJson()
	{
		boolean result=false;
		try {
			initContext();
//			getBaseBean().setUpdateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setUpdateAuthor(session.getAttribute("username")+"");
			result=getBaseService().update(getBaseBean());
			message=getBaseBean().getId()+"";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message="����ʧ��";
			e.printStackTrace();
		}
		try {
			printBaseBeanToJson(new ResultBean(message,result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ����һ���й���������
	 * @return
	 * @
	 */
	public String updateJsonByRelation()
	{
		boolean result=false;
		try {
			initContext();
//			getBaseBean().setUpdateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setUpdateAuthor(session.getAttribute("username")+"");
			result=getBaseService().update(getBaseBean());
			message=getBaseBean().getId()+"";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message="����ʧ��";
			e.printStackTrace();
		}
		try {
			printBaseBeanToJson(new ResultBean(message,result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ���һ��
	 * @return
	 * @
	 */
	public String addJson()
	{
		boolean result=false;
		try {
			initContext();
//			getBaseBean().setCreateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setCreateAuthor(session.getAttribute("username")+"");
//			getBaseBean().setUpdateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setUpdateAuthor(session.getAttribute("username")+"");
			result=getBaseService().add(getBaseBean());
			message=getBaseBean().getId()+"";
			} catch (Exception e) {
			// TODO Auto-generated catch block
			message="����ʧ��";
			e.printStackTrace();
		}
		try {
			printBaseBeanToJson(new ResultBean(message,result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ɾ��һЩ
	 * @return
	 * @
	 */
	public String deletesJson()
	{
		boolean result=false;
		try {
			result=getBaseService().deletes(getBaseBean().getIds());
			message=getBaseBean().getIds()+"";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message="ɾ��ʧ��";
			e.printStackTrace();
		}
		try {
			printBaseBeanToJson(new ResultBean(message,result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ѯһ��
	 * @return
	 * @
	 */
	public String getJson()
	{
		BaseBean baseBean=null;
		try {
			baseBean = (BaseBean) getBaseService().get(getBaseBean().getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(baseBean!=null)
		{
			try {
				printBaseBeanToJson(baseBean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			try {
				printNullToJson();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	/**
	 * ��ѯһЩ
	 * @return
	 * @
	 */
	public String list()
	{
		try {
			initContext();
			if(pageBean!=null){
				pageBean=this.getBaseService().list(this.getBaseBean(),pageBean);
				request.setAttribute("pageBean", pageBean);
			}else{
				List dataList=this.getBaseService().list(this.getBaseBean());
				request.setAttribute("list", dataList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return "list";
	}
	
	/**
	 * ����һ��
	 * @return
	 * @
	 */
	public String update()
	{
		try {
			initContext();
//			getBaseBean().setCreateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setCreateAuthor(session.getAttribute("username")+"");
//			getBaseBean().setUpdateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setUpdateAuthor(session.getAttribute("username")+"");
			getBaseService().update(getBaseBean());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * ���һ��
	 * @return
	 * @
	 */
	public String add()
	{
		try {
			initContext();
//			getBaseBean().setCreateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setCreateAuthor(session.getAttribute("username")+"");
//			getBaseBean().setUpdateTime(BaseUtil.getNowWithSqlDate());
//			getBaseBean().setUpdateAuthor(session.getAttribute("username")+"");
			getBaseService().add(getBaseBean());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * ɾ��һЩ
	 * @return
	 * @
	 */
	public String deletes()
	{
		try {
			getBaseService().deletes(getBaseBean().getIds());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * ��ѯһ��
	 * @return
	 * @
	 */
	public String get()
	{
		BaseBean baseBean=null;
		try {
			baseBean = (BaseBean) getBaseService().get(getBaseBean().getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			initContext();
			request.setAttribute("type", request.getParameter("type"));
			request.setAttribute("data", baseBean);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "get";
	}
	
	/**
	 * ������ϵ�json��ʽ������
	 * @param fieldName ʵ��
	 * @throws IOException
	 
	protected void printBaseListToJson(List<BaseBean> baseList) throws Exception
	{
		initContext();
		out.print("{\""+getClassNameToFieldName(getBaseService().getBaseDao().getTableClass().getSimpleName())+"\":[");
		int i=0;
		for(BaseBean baseBean:baseList)
		{
			
			baseBean.setFields(this.getBaseBean()!=null?this.getBaseBean().getFields():null);
			if(i>0)
			{
				out.print(",");
			}
			out.print(baseBean);
			i++;
		}
		out.print("]}");
		out.flush();
		out.close();
	}
	*/
	/**
	 * ������ϵ�json��ʽ������
	 * @param fieldName ʵ��
	 * @throws IOException
	 */
	protected void printMapListToJson(List<Map> dataList) throws Exception
	{
		initContext();
		out.print("{\""+getClassNameToFieldName(getBaseService().getBaseDao().getTableClass().getSimpleName())+"\":[");
		int i=0;
		for(Map map:dataList)
		{
			if(i>0)
			{
				out.print(",");
			}
			out.print("{");
			Iterator it=map.keySet().iterator();
			int j=0;
			while(it.hasNext()){
				String key=(String)it.next();
				Object value=map.get(key);
				if(j>0){
					out.print(",");
				}
				out.print("\""+key+"\":\""+value+"\"");
				j++;
			}
			out.print("}");
			i++;
		}
		out.print("]}");
		out.flush();
		out.close();
	}
	
	/**
	 * ���null��json��ʽ������
	 * @param fieldName ʵ��
	 * @throws IOException
	 */
	protected void printNullToJson() throws Exception
	{
		initContext();
		out.print("{\""+getClassNameToFieldName(getBaseService().getBaseDao().getTableClass().getSimpleName())+"\":null}");
		out.flush();
		out.close();
	}
	
	/**
	 * ���json��ʽ������
	 * @param baseBean ʵ��
	 * @throws IOException
	 */
	protected void printBaseBeanToJson(BaseBean baseBean)throws Exception 
	{
		initContext();
		out.print("{\""+getClassNameToFieldName(baseBean.getClass().getSimpleName())+"\":");
		out.print(baseBean);
		out.print("}");
		out.flush();
		out.close();
	}
	
	/**
	 * ����ת������
	 * @param tableName
	 * @return
	 */
	protected String getClassNameToFieldName(String tableName)
	{
		return tableName.substring(0,1).toLowerCase()+tableName.substring(1);
	}
	
	/**
	 * ������
	 * @return
	 */
	public Field[] getClassDeclaredFields(){
		return this.getClass().getDeclaredFields();
	}
	
	/**
	 * ������
	 * @return
	 */
	public Field[] getClassFields(){
		return this.getClass().getFields();
	}
	
	public String insertByHql(){
		return null;
	}
	
	public String updateByHql(){
		return null;
	}
	
	public String deleteByHql(){
		return null;
	}
	
	public String listByHql(){
		return null;
	}
}

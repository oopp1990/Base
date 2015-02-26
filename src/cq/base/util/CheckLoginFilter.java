package cq.base.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginFilter implements Filter {
	
	private String excludeUrlPatten="";
	
	private boolean isOpened=false;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		if(isOpened){
			String uri=request.getRequestURI().substring(request.getContextPath().length());
//			System.out.println(uri+","+(uri.indexOf(excludeUrlPatten)<0&&uri.length()>1));
			boolean flag=true;
			String[] keys=excludeUrlPatten.split("#");
			for(String key:keys){
				if(uri.indexOf(key)>0){
					flag=false;
				}
			}
			String str=request.getParameter("system");
			if(str!=null&&str.equals("telphole")){
				flag=false;
			}
			if((uri.indexOf(".action")>0||uri.indexOf(".jsp")>0)&&uri.length()>1&&flag){
				Object obj=request.getSession().getAttribute("loginUser");
				if(obj==null){
					if(request.getCharacterEncoding()==null){
						response.setCharacterEncoding("utf-8");
					}
				 	String path = request.getContextPath();
					String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
					
//					String filePath=request.getServletPath();
//					String loginPath="login.jsp";
//					for(int i=2;i<filePath.split("/").length;i++){
//						loginPath="../"+loginPath;
//					}
//					request.setAttribute("message","你还没有登录或网页已过期,请先登录系统再继续操�?);
//					RequestDispatcher rd=request.getRequestDispatcher(loginPath);
//					rd.forward(request, response);
					response.getWriter().print("<script>alert(decodeURI('%E4%BD%A0%E8%BF%98%E6%B2%A1%E6%9C%89%E7%99%BB%E5%BD%95%E6%88%96%E7%BD%91%E9%A1%B5%E5%B7%B2%E8%BF%87%E6%9C%9F,%E8%AF%B7%E5%85%88%E7%99%BB%E5%BD%95%E7%B3%BB%E7%BB%9F%E5%86%8D%E7%BB%A7%E7%BB%AD%E6%93%8D%E4%BD%9C '));window.top.location.href='"+basePath+"login.jsp';</script>");
					return ;
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig cfg) throws ServletException {
		// TODO Auto-generated method stub
		excludeUrlPatten=cfg.getInitParameter("excludeUrlPatten");
		isOpened=Boolean.parseBoolean(cfg.getInitParameter("isOpened"));
	}

}

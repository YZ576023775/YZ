package com.yz.searchservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yz.pagetool.PageBean;

public class SearchEngineServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SearchEngineService ses = new SearchEngineService();
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("utf-8");
		String intent = req.getParameter("intent");
		String flag = req.getParameter("flag");
		flag=flag==null?"":flag;
		if (intent.equals("search")) {
			String keywords = req.getParameter("keywords");
			keywords=keywords.trim();
			if (keywords==null || keywords.trim().length()==0) {
					if (flag.equals("ajax")) {
						return;
					} else {
						resp.sendRedirect("index.jsp");
						return;
					}
			}
			
			if (flag.equals("ajax")) {
				req.setCharacterEncoding("utf-8");
				resp.setContentType("text/json");
				resp.setCharacterEncoding("utf-8");
				List<String> list =Arrays.asList(new String[]{"artist","music","album","mv"});
				List<Map<String, String>> beanMap = ses.searchHighLightByParseCatagory(keywords, list, 0, 1);
//				System.out.println(beanMap);
//				System.out.println(JSON.toJSONString(beanMap,true));
				if (beanMap!=null) {
				resp.getWriter().print(JSON.toJSONString(beanMap));
//				resp.getWriter().write("<xml><KeyWord>林俊杰</KeyWord><KeyWord>爱</KeyWord><KeyWord>周杰伦</KeyWord></xml>");	
				} else {
					return;
				}
				
			}else {
			
//			ServletContext application = this.getServletContext();
//			ses.otherSearchStrings(application, keywords);//数据库取
//			String sessionId = req.getSession().getId();
//			Set<String> set1 = (Set<String>) application.getAttribute(sessionId);//数据库取
//			if (set1 != null&&set1.size()>0) {
//				set1.add(keywords);
//				application.setAttribute(sessionId, set1);//持久化
//			} else {
//				Set<String> set = new HashSet<String>();
//				set.add(keywords);
//				application.setAttribute(sessionId, set);//持久化
//			}
			
				String currentPage = req.getParameter("cp");
				currentPage = (currentPage == null ? "1" : currentPage);
				
				PageBean<Map<String, String>> pageBean = ses.searchHighLightByParse(keywords,
						Integer.parseInt(currentPage));
				
				if (pageBean!=null) {
					// System.out.println(JSON.toJSONString(pageBean.getDatas()));
					
//					ServletContext application = this.getServletContext();
//					ses.otherSearchStrings(application, keywords);// 数据库取
					String sessionId = req.getSession().getId();
					List<String> list = mongoDBJDBC.selectByKeywords(keywords, sessionId, 8);
					System.out.println("其他用户也搜	"+list);
					req.setAttribute("otherList", list);
//					Set<String> set1 = (Set<String>) application
//							.getAttribute(sessionId);// 数据库取
					Set<String> set1 =mongoDBJDBC.selectBySessionId(sessionId);
					if (set1 != null && set1.size() > 0) {
						set1.add(keywords);
//						application.setAttribute(sessionId, set1);// 持久化
						mongoDBJDBC.update(sessionId, set1);
					} else {
						Set<String> set = new HashSet<String>();
						set.add(keywords);
//						application.setAttribute(sessionId, set);// 持久化
						mongoDBJDBC.insert(sessionId, set);
					}
					
					req.setAttribute("pageBean", pageBean);
					req.setAttribute("keywords", keywords);
					req.getRequestDispatcher("/WEB-INF/show.jsp").forward(req, resp);
					
				} else {
					req.setCharacterEncoding("utf-8");
					resp.setContentType("text/html");
					resp.setCharacterEncoding("utf-8");
						req.getRequestDispatcher("WEB-INF/error1.jsp").forward(req, resp);
//					PrintWriter pw = resp.getWriter();
//					pw.write("<h1 style='margin:0 auto;'>很抱歉，未能找到相关搜索结果！3秒后跳转...<h1>");
//					pw.write("<script>window.location.href='WEB-INF/error.jsp';</script>");
//					pw.write("<script>function redirect(){ window.location.href='index.jsp';}</script>");
//					pw.write("<script>setTimeout('redirect',3000);</script>");
//					resp.addHeader("refresh", "3;URL=index.jsp");
					
				}
			}

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}



}

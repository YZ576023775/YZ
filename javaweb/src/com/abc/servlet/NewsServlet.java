package com.abc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.dao.NewsDAO;
import com.abc.daoimpl.NewsDaoImpl;
import com.abc.entity.News;

@SuppressWarnings("serial")
public class NewsServlet extends HttpServlet {
	
	public void save(HttpServletRequest request, HttpServletResponse response){
		try{
	
			PrintWriter out = response.getWriter();
			String title=request.getParameter("title");
			String news_desc=request.getParameter("news_desc");
//			System.out.println(title);
//			System.out.println(news_desc);
			//title = new String(title.getBytes("iso-8859-1"),"utf-8");
			//news_desc = new String(news_desc.ge tBytes("iso-8859-1"),"utf-8");
			News news=new News();
			news.setTitle(title);
			news.setnews_desc(news_desc);
			ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
			if(newsDAO.save(news)){
			out.print("<script>alert('保存成功');</script>");
			out.print("<script>window.location.href='../user/login_success.jsp';</script>");
			}
		}
		catch(Exception ex){
			System.out.println("NewsServlet++++++++save()"+ex.getMessage());
		}
	}
	public void update(HttpServletRequest request, HttpServletResponse response){
		try{
			
			PrintWriter out = response.getWriter();
			String id=request.getParameter("id");
			
			String title=request.getParameter("title");
			String news_desc=request.getParameter("news_desc");
			System.out.println("++++"+id+title+news_desc);
//			System.out.println(title);
//			System.out.println(news_desc);
			//title = new String(title.getBytes("iso-8859-1"),"utf-8");
			//news_desc = new String(news_desc.ge tBytes("iso-8859-1"),"utf-8");
		/*	News news=new News();
			news.setId(Integer.parseInt(id));
			news.setTitle(title);
			news.setnews_desc(news_desc);*/
			ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			NewsDAO newsDAO= (NewsDAO)ac.getBean("newsdaoimpl");
	
			if(newsDAO.update(Integer.parseInt(id),title,news_desc)!=0){
				out.print("<script>alert('更新成功');</script>");
				out.print("<script>window.location.href='../user/login_success.jsp';</script>");
			}
		}
		catch(Exception ex){
			System.out.println("NewsServlet++++++++save()"+ex.getMessage());
		}
	}
	public void find(HttpServletRequest request,HttpServletResponse response){
		
				try {
					PrintWriter out = response.getWriter();

					String id = request.getParameter("id");
					NewsDAO newsDAO = new NewsDaoImpl();
					out.print(newsDAO.find(Integer.valueOf(id)).getnews_desc());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		
	}
	private void set(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
	}
	public void delete(HttpServletRequest request, HttpServletResponse response){
		 String id = request.getParameter("id");
		 String username = request.getParameter("username");
		 if(username!=null){
		 NewsDAO newsDAO = new NewsDaoImpl();
		 try {
			if(newsDAO.delete(Integer.parseInt(id))!=0){
				 response.getWriter().write("<script>alert('删除成功！')</script>");
				 response.getWriter().write("<script>window.location.href='../servlet/NewsServlet?methodName=findAll'</script>");
			 }else {
				 response.getWriter().write("<script>alert('删除失败!')</script>");
				 response.getWriter().write("<script>window.location.href='../servlet/NewsServlet?methodName=findAll'</script>");
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }else{
			 try {
				 response.getWriter().write("<script>alert('请先登录！')</script>");
				response.getWriter().write("<script>window.location.href='../index.jsp'</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 }
	}
	public void findAll(HttpServletRequest request, HttpServletResponse response){
		
				
					try {
						set(request, response);
						System.out.println("+++++	public void findAll");
						NewsDAO newsDAO = new NewsDaoImpl();
						String currentPageIndex = request.getParameter("currentPageIndex");
						String keywords = request.getParameter("keywords");
						keywords = (keywords==null)?"":new String(keywords.getBytes("iso-8859-1"),"utf-8");
							int currentIndex = (currentPageIndex==null)?0:Integer.parseInt(currentPageIndex);
							System.out.println("NewsServlet.java++++++findAll() +++++++currentIndex+"+currentIndex);
							ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
							newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
						 List<News> list = newsDAO.findAll(currentIndex,keywords);
						 //System.out.println("list++++++++++++"+list);
						 int count = newsDAO.getCount(keywords);
						 	int pages = count%10==0 ? (count/10) : (count/10+1);
						 	List<Integer> pageIndexList= new ArrayList<Integer>();
						 	for (int i = 1; i <=pages; i++) {
								
						 		pageIndexList.add(i);
							}
						 	System.out.println("+++++++++++++++pageIndexList++++"+pageIndexList);
						 	if(request.getParameter("mobile")!=null){
						 		StringBuffer sb = new StringBuffer();
						 		sb.append("[");
						 		for(int i=0;i<list.size();i++){
						 			if(i<list.size()-1){
						 			sb.append("{");
						 			sb.append("\"id\":"+"\""+list.get(i).getId()+"\",");
						 			sb.append("\"title\":"+"\""+list.get(i).getTitle()+"\",");
						 			sb.append("\"news_desc\":"+"\""+list.get(i).getnews_desc()+"\"");
						 			sb.append("},");
						 			}else{
						 				sb.append("{");
							 			sb.append("\"id\":"+"\""+list.get(i).getId()+"\",");
							 			sb.append("\"title\":"+"\""+list.get(i).getTitle()+"\",");
							 			sb.append("\"news_desc\":"+"\""+list.get(i).getnews_desc()+"\"");
							 			sb.append("}");
						 			}
						 		}
						 		sb.append("]");
						 		System.out.println(sb);
//						 		JSONArray jsonArray = new JSONArray(list);
						 		System.out.println("sb"+sb.toString());
						 		response.getWriter().write(sb.toString());
						 		return;
						 	}
						// request.setAttribute("a", "a");
//					 System.out.println("1");
//					 System.out.println(list);
						 	request.setAttribute("keywords", keywords);
						 	request.setAttribute("list", list);
						 	request.setAttribute("pages", pages);
						 	request.setAttribute("currentPageIndex", currentPageIndex);
						 	request.setAttribute("pageIndexList", pageIndexList);
						 	System.out.println("+++++++getRequestDispatcher++++++");
						 request.getRequestDispatcher("../news/findAll.jsp").forward(request, response);

						 	
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BeansException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			
		
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		set(request, response);
		if(request.getParameter("methodName")==null){
			return;
		}
		if(request.getParameter("methodName").equals("findAll")){
			findAll(request, response);
		}
		if(request.getParameter("methodName").equals("save")){
			save(request, response);
		}
		if(request.getParameter("methodName").equals("find")){
			find(request, response);
		}
		if(request.getParameter("methodName").equals("delete")){
			delete(request, response);
		}
		if(request.getParameter("methodName").equals("update")){
			update(request, response);
		}
	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

}

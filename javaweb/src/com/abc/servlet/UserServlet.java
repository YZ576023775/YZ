package com.abc.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.dao.UserDAO;
import com.abc.entity.User;

public class UserServlet extends HttpServlet {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private void set(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
	}

	public void find(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		try {
			set(request, response);
			out = response.getWriter();
			if (request.getParameter("username").equals("")
					|| request.getParameter("userpwd").equals("")) {
				out.print("<script>alert('请输入用户名密码')</script>");
				out.print("<script>window.location.href='../index.jsp';</script>");

				//out.print("请输入用户名密码");
				;
			}
			String username = request.getParameter("username");
			String userpwd = request.getParameter("userpwd");
			User user = new User();
			user.setUsername(username);
			user.setUserpwd(userpwd);
			ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl");
			boolean result = userDAO.find(user);
			if (result) {
				request.getSession().setAttribute("username", username);
				request.getRequestDispatcher("../user/login_success.jsp").forward(
						request, response);
			} else {
				out.print("<script>alert('账号密码不匹配！请重新输入!')</script>");
				out.print("<script>window.location.href='../index.jsp';</script>");
				
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	boolean flag=false;
	public void findByName(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		try {
			set(request, response);
			out = response.getWriter();
			
			String username = request.getParameter("username");
			ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl1");
			boolean result = userDAO.findByName(username);
			/*if (result) {
				flag=true;
//				out.print("<script>alert('账号已存在！请重新输入!')</script>");
//				out.print("<script>window.location.href='../index.jsp';</script>");
				
			}else{
				flag=false;
			}*/
			flag=result;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void save(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("save+++++++++start");
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		
		try {
			set(request, response);
			PrintWriter out=response.getWriter();
//			UserDAO userDAO = new UserDaoImpl();
			ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
			UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl");
			User user = new User();
			user.setUsername(username);
			user.setUserpwd(userpwd);
			boolean result=userDAO.save(user);
			System.out.println("UserServlet++++++save()+++result++++"+result);
			if(result){
				out.print("<script>alert('注冊成功！')</script>");
				out.print("<script>window.location.href='../index.jsp'</script>");
			}else {
				out.print("<script>alert('注冊失败!')</script>");
				out.print("<script>window.location.href='../user/register.jsp';</script>");
			}
		} catch (Exception ex) {
			System.out.println( ex.getMessage());
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		try {
			out=response.getWriter();
			set(request, response);
			

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (request.getParameter("methodName").equals("")) {
			System.out.println("++++++methodName+++++"+request.getParameter("methodName"));
			return;
		}

		if (request.getParameter("methodName").equals("find")) {
			find(request, response);
		}
		if (request.getParameter("methodName").equals("findByName")) {
			findByName(request, response);

			out.print(flag);
		}

		if (request.getParameter("methodName").equals("save")) {
		/*	String username = request.getParameter("username");
			String userpwd = request.getParameter("userpwd");
			UserDAO userDAO = new UserDaoImpl();*/
			//userDAO.find(user);
			findByName(request, response);
			System.out.println(flag);
			if(flag){
				out.print("<script>alert('用户已存在！')</script>");
				out.print("<script>window.location.href='../user/register.jsp'</script>");
			}else{
			save(request, response);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

package com.eoot.jspprj;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.parser.Scanner;

@WebServlet("/add") 
public class Add extends HttpServlet{

//service�� GET��û�� POST��û ��� ó�����ش�. 
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
		//�ٵ� ���α׷��� �ϴٺ��� �̰� �������� ���ϴ� ��찡 �ִ�. ����ó�� ���� �����ϴ� ��찡 �ƴϱ�, �̹� �ʱⰪ ������ �� �Ѱܹ��� ���... ���� ����å �ʿ�. 
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		String _x = request.getParameter("x");
		String _y = request.getParameter("y");
		
		int x = 0;
		int y = 0; 
		if(request.getMethod().equals("POST")){
		if(_x != null && !_x.equals(""))
			x = Integer.parseInt(_x);
		
		if(_y != null && !_y.equals(""))
			y = Integer.parseInt(_y);
		}
/*		if(request.getMethod().equals("POST")){
			x = Integer.parseInt(request.getParameter("x"));
			y = Integer.parseInt(request.getParameter("y"));
		}*/
		
		int sum = x + y;
		
		//�� ���� ����� ����غ���
		String _save = request.getParameter("save");
		
		if(_save != null){
			String _sum = request.getParameter("sum");
//			_save = new String(_save.getBytes("ISO-8859-1"),"UTF-8"); �̰� ���� ���� ���;���.
			//_save������ ���� �ٽ� UTF-8�� �����Ͽ� ���ڿ��� ����� �ִ� �۾��� �� �ش�. 
			System.out.println(_save);
			
			if(_save.equals("�۾�")){
				ServletContext application = request.getServletContext();
				application.setAttribute("sum", _sum);
				System.out.println("saved in app");
			}
			
			else if(_save.equals("session")){
				HttpSession session = request.getSession(); //�� �κи� �� �ٸ� ����!!
				session.setAttribute("sum", _sum);
				System.out.println("saved in session");
			}
			
			else if(_save.equals("cookie")){
				Cookie cookie = new Cookie("sum", _sum);
				cookie.setMaxAge(24*60*60); //24�ð�
				response.addCookie(cookie);
				System.out.println("saved in cookie");
			}
		
		}
		
		//application�� ������ ��� 
		/*if(request.getParameter("save") != null){
			ServletContext application = request.getServletContext();
			String _sum = request.getParameter("sum");
			application.setAttribute("sum", _sum);
		}*/
		
		PrintWriter out = response.getWriter();
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Insert title here</title>");
		out.write("</head>");
		out.write("<body>");
		out.write("<form action = \"add\" method = \"post\" >");
		out.write("<ul>");
		out.write("<li><label for = \"x\">X:</label><input name = \"x\"/></li>");
		out.write("<li><label for = \"y\">Y:</label><input name = \"y\"/></li>");
		out.write("<li><label for = \"sum\">SUM:</label><input name = \"sum\" value = \"" + sum + "\"/></li>");
		out.write("</ul>");
		out.write("<input type = \"submit\" value = \"����\" />");
		out.write("<input type = \"submit\" name = \"save\" value = \"�۾�\" />");
		out.write("<input type = \"submit\" name = \"save\" value = \"session\" />");
		out.write("<input type = \"submit\" name = \"save\" value = \"cookie\" />");
		out.write("</form>");
		out.write("<p><a href = \"index\">Ȩ���� ���ư���</a></p>");
		out.write("</body>");
		out.write("</html>");
	
	}
}

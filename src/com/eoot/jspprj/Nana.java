package com.eoot.jspprj;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nana") //nana�� ���ε�. ���� 404���� �ȶ��. ��,��Ĺ�� 7.0�̻��� ������ ����.
public class Nana extends HttpServlet{
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8"); //��½� 
		response.setContentType("text/html; charset=UTF-8");
		
//		request//�Էµ���
//		?�� cnt����. cnt�� �̸� ��ӵǾ� �־��. 
//		request�� �������� ������ ���ڿ��̵�. _����ٷ� �ӽú������� ��Ÿ������. 
//		if(_cnt�� ���� �ƴϸ�)
//			cnt = _cnt�� ������ ��ȯ;
//		http://..../hello?cnt=1
/*		�װ��� ������� ������� ��û ���� ���� 
		http://..../hello?cnt=3 //3�̶�� ���ڿ��� ���޵�
		http://..../hello?cnt= //���ڿ��� ���޵�. �̰�� ���� ����. ���ڷ� ��ȯ�� �ȵǴϱ�. 
		http://..../hello? //null
		http://..../hello //null
*/		
		
		String _cnt = request.getParameter("cnt");
		
		int cnt = 100; 
		
		if(_cnt != null)
			cnt = Integer.parseInt(_cnt);
	
		PrintWriter out = response.getWriter();
		for(int i = 0; i < cnt; i++)
			out.println(i+1 +". �ȳ� ����<br />");
	}
}

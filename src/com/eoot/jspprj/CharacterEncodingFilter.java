package com.eoot.jspprj;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter{

	public FilterConfig filterConfig;
	private String encoding;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final ServletRequest request,final ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		System.out.println("before ����");
//		request.setCharacterEncoding("UTF-8"); //���� ���ִµ�, �̷��� ���� ���ڸ� �Ѱܹ޴� ������� ����. 
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
//		System.out.println("after ����");
//		���ʹ� ���� ���������, ��Ĺ�� ��. �� �̸����� ���� ������ٰ� ���� web.xml�� �˷���� �Ѵ�. 
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
	}
	

}

package com.eoot.jspprj;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener, ServletContextAttributeListener{

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		System.out.println("���ø����̼� �Ӽ�" + event.getName()
				+ "�� ��" + event.getValue() + "���� �߰���");	
	}
	
	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		System.out.println("���ø����̼� �Ӽ�" + event.getName()
				+ "�� ���ŵ�");	
	}
	
	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		System.out.println("���ø����̼� �Ӽ�" + event.getName()
				+ "�� �����");	
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("���ø����̼� �����");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		System.out.println("���ø����̼� �ʱ�ȭ��");
	}

}

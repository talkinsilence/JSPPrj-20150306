package nami.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eoot.jspprj.dao.mybatis.MyBatisMain;

public class Program {

	public static void main(String[] args) {
		/*NoticeDao noticeDao = new JdbcNoticeDao();
		NoticeView view = new NoticeView();
		view.setNoticeDao(noticeDao);*/
		//�� ���� ��ü�� �����ϰ� �ϳ��� �������� �����ϴ� �� �κ��� ���������� ��Ź�� ����.  
		
		/*MyBatisMain.start();*/
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("nami/spring/spring-context.xml");
		
/*		NoticeView view = (NoticeView) context.getBean("view");
		view.print();*/
/*		Exam exam = (Exam) context.getBean("exam");
		System.out.println((exam.total()));*/
		ExamMng mng = (ExamMng) context.getBean("mng");
		mng.print();
		
	}
}

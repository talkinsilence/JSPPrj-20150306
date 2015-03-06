package nami.java.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StopWatch;

import com.eoot.jspprj.dao.NoticeDao;

public class DaoLogHandler implements InvocationHandler {
	
	private NoticeDao noticeDao;
	
	public DaoLogHandler(NoticeDao noticeDao) {
		// TODO Auto-generated constructor stub
		this.noticeDao = noticeDao;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) //invoke? ���ڷκ��� ���Ǵ� �Լ��� ȣ���.
			throws Throwable {
		// TODO Auto-generated method stub
		
		//�տ��� ó���� ������ �ִٸ� ���⿡.
		Log log = LogFactory.getLog(this.getClass()); //import: commons�� �޾ƿ���. 
		StopWatch sw = new StopWatch();
		log.info("�Լ� ȣ�� ����");
		sw.start();
		
		//�߽�. �־���. ���ڴ� ȣ��. 
		Object result = method.invoke(noticeDao, args);
		
		//�ڿ��� ó���� ������ �ִٸ� ���⿡. 
		sw.stop();
		log.info("�Լ� ȣ�� ��!!!!!!");
		log.info("[ȣ�� �޼ҵ�] : " + method.getName());
		log.info("[ȣ�� �ð�] : " + sw.getTotalTimeMillis());
		
		return result;
	}

}

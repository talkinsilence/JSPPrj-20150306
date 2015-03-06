package nami.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StopWatch;

public class LogAroundAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		// TODO Auto-generated method stub
		
		//�տ��� ó���� ������ �ִٸ� ���⿡.
		Log log = LogFactory.getLog(this.getClass()); //import: commons�� �޾ƿ���. 
		StopWatch sw = new StopWatch();
		log.info("�Լ� ȣ�� ����");
		sw.start();
		
		//�߽�. �־���. ���ڴ� ȣ��. 
		Object result = method.proceed();
		
		//�ڿ��� ó���� ������ �ִٸ� ���⿡. 
		sw.stop();
		log.info("�Լ� ȣ�� ��!!!!!!");
		log.info("[ȣ�� �޼ҵ�] : " + method.getMethod().getName());
		log.info("[ȣ�� �ð�] : " + sw.getTotalTimeMillis());
		
		return result;
	}

}

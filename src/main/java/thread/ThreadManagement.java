package thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ThreadManagement implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		executor.submit(() -> {
			try {
				Thread.sleep(5000);
				System.out.println("asdasdasd");
				invocation.proceed();
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
		});
		return null;
	}

	

}

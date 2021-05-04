import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.SongDAO;
import listener.AddListener;
import model.SongDTO;

public class MusicManager  implements ServletContextListener{
	public static void main(String[] args) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Initializing...");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}

}

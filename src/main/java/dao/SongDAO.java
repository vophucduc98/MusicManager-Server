package dao;

import java.util.List;

import javax.management.Notification;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import jms.JMSPublisher;
import model.SongDTO;
import service.SongDAOMbean;

public class SongDAO extends Thread implements SongDAOMbean, NotificationPublisherAware {

	SessionFactory sessionFactory;
	NotificationPublisher publisher;
	JMSPublisher messagePublisher;
	String method;
	SongDTO dto;
	int id;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setMessagePublisher(JMSPublisher messagePublisher) {
		this.messagePublisher = messagePublisher;
	}

	@Transactional
	public synchronized void add(SongDTO dto) throws InterruptedException {
		method = "Add";
		this.dto = dto;
		run();
//		System.out.print("Adding");
//		loading();
//		Session session = sessionFactory.openSession();
//		Transaction transaction = session.beginTransaction();
//		session.persist(dto);
//		transaction.commit();
//		session.close();
//		this.publisher.sendNotification(new Notification("ADD", this, 0));
//		messagePublisher.send();
	}

	@Transactional
	public synchronized void delete(int id) throws InterruptedException {
		method = "Delete";
		this.id = id;
		run();
//		System.out.print("Deleting");
//		loading();
//		Session session = sessionFactory.openSession();
//		Transaction transaction = session.beginTransaction();
//		SongDTO dto = session.load(SongDTO.class, id);
//		session.delete(dto);
//		transaction.commit();
//		session.close();
//		messagePublisher.send();
	}

	@Transactional
	public synchronized void update(SongDTO dto) throws InterruptedException {
		method = "Update";
		this.dto = dto;
		run();
//		boolean flag = true;
//		SongDTO temp = this.findOne(dto.getId());
//		if (temp!=null)
//			flag = true;
//		else 
//			flag = false;
//		if (flag) {
//			System.out.println("Updating");
//			loading();
//			Session session = sessionFactory.openSession();
//			Transaction transaction = session.beginTransaction();
//			session.merge(dto);
//			transaction.commit();
//			session.close();
//			messagePublisher.send();
//			return flag;
//		}
//		return flag;
	}

	@Transactional
	public List<SongDTO> findAll() {
		Session session = sessionFactory.openSession();
		List<SongDTO> list = session.createQuery("SELECT s FROM SongDTO s", SongDTO.class).getResultList();
		session.close();
		return list;
	}

	@Transactional
	public SongDTO findOne(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		SongDTO dto = session.get(SongDTO.class, id);
		transaction.commit();
		session.close();
		return dto;
	}

	@Override
	public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
		this.publisher = notificationPublisher;
	}

	@Override
	public synchronized void run() {
		super.run();
		switch (method) {
		case "Add": {
			System.out.println("ADDING");
			try {
				loading();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.persist(dto);
			transaction.commit();
			session.close();
			this.publisher.sendNotification(new Notification("ADD", this, 0));
			messagePublisher.send();
		}
			break;
		case "Update": {
			System.out.println("UPDATING");
			try {
				loading();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SongDTO temp = this.findOne(dto.getId());
			boolean flag = true;
			if (temp!=null)
				flag = true;
			else 
				flag = false;
			if (flag) {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.merge(dto);
			transaction.commit();
			session.close();
			messagePublisher.send();
			}
		}
			break;
		case "Delete": {
			System.out.println("DELETING");
			try {
				loading();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			SongDTO dto = session.load(SongDTO.class, id);
			session.delete(dto);
			transaction.commit();
			session.close();
			messagePublisher.send();
		}
			break;
		}
	}
	
	public void loading () throws InterruptedException {
		for (int i=0;i<10;i++) {
			System.out.print('/');
			Thread.sleep(50);
			System.out.print('\b');
			System.out.print('-');
			Thread.sleep(50);
			System.out.print('\b');
			System.out.print('\\');
			Thread.sleep(50);
			System.out.print('\b');
		}	
	}

}

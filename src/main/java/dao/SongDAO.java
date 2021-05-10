package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import api.SongDAOMbean;
import jms.JMSPublisher;
import model.SongDTO;
import model.SongVO;

public class SongDAO implements SongDAOMbean {

	SessionFactory sessionFactory;
	JMSPublisher messagePublisher;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setMessagePublisher(JMSPublisher messagePublisher) {
		this.messagePublisher = messagePublisher;
	}
	
	public synchronized void add(SongVO vo) throws InterruptedException {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		// Find a new way
		SongDTO dto = new SongDTO(vo.getName(), vo.getArtist(), vo.getDuration());
		session.persist(dto);
		transaction.commit();
		session.close();
		messagePublisher.send();
	}

	public synchronized void delete(int id) throws InterruptedException {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		SongDTO dto = session.load(SongDTO.class, id);
		Query query = session.createQuery("DELETE FROM SongDTO s WHERE s.id = :id");
		query.setParameter("id", dto.getId());
		query.executeUpdate();
//		session.delete(dto);
		transaction.commit();
		session.close();
		messagePublisher.send();
	}

	public synchronized void update(SongVO vo) throws InterruptedException {
		boolean flag = true;
		SongVO temp = this.findOne(vo.getId());
		if (temp!=null)
			flag = true;
		else 
			flag = false;
		if (flag) {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			//session.merge(vo);
			Query query = session.createQuery("UPDATE SongDTO s SET s.name = :name, s.artist = :artist, s.duration = :duration WHERE s.id = :id");
			query.setParameter("name", vo.getName());
			query.setParameter("artist", vo.getArtist());
			query.setParameter("duration", vo.getDuration());
			query.setParameter("id", vo.getId());
			query.executeUpdate();
			transaction.commit();
			session.close();
			messagePublisher.send();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<SongVO> findAll() {
		Session session = sessionFactory.openSession();
		List<SongVO> list = session
				.createQuery("SELECT s.id AS id, s.name AS name, s.artist AS artist, s.duration AS duration FROM SongDTO s")
				.setResultTransformer(Transformers.aliasToBean(SongVO.class))
				.list();
		session.close();
		return list;
	}

	@SuppressWarnings("deprecation")
	public SongVO findOne(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		SongVO vo = (SongVO) session.createQuery("SELECT s.id AS id, s.name AS name, s.artist AS artist, s.duration AS duration FROM SongDTO s WHERE s.id = :id")
				.setParameter("id", id)
				.setResultTransformer(Transformers.aliasToBean(SongVO.class))
				.list()
				.get(0);
		transaction.commit();
		session.close();
		return vo;
	}

}

package cn.itcast.state;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppState {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/state/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}

	@Test
	public void testFlushAndClear() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		/**
		 * 临时对象:
		 *   * oid=null
		 *   * 在数据库中没有对应的记录
		 *   * 在session的缓存中没有
		 */
		Customer c=new Customer();
		c.setName("xxx");
	    
		/**
		 * s.save(c)
		 *   * 该对象变为持久对象.
		 *   * oid不为null
		 *   * session的一级缓存中有该对象
		 *   * 数据库中没有对应的记录[可能有,也可能没有].
		 */
		s.save(c);

		/**
		 * 持久对象.
		 *   * oid不为null
		 *   * session的一级缓存中有该对象
		 *   * 数据库中有对应的记录
		 */
		Customer c1=(Customer)s.get(Customer.class, 2);
		
		s.flush();
		tx.commit();
		s.close();
		
		/*
		 * session关闭:游离对象
		 *   * 在session的一级缓存中没有该对象
		 *   * 该对象oid不为null
		 *   * 在数据中有对应的记录[可能有也可能没有]
		 */
		System.out.println(c);
//////////////////////////////////////////////////////////////////////////////////////////////////////////		
		s = sf.openSession();
		tx = s.beginTransaction();
		
		Customer c2=(Customer)s.get(Customer.class, 3);
		s.delete(c2);
		
		/**
		 * c2就是删除对象.
		 *   * oid不能null
		 *   * 缓存没有
		 *   * 数据库中没有对应的记录
		 */
		s.flush();
		tx.commit();
		s.close();
		
	}
	
	@Test @SuppressWarnings("unused")
	public void testEvict() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c1=(Customer)s.get(Customer.class, 2);
		/*
		 * s.evict(c1)
		 *  * 把一个持久对象转化为游离对象
		 *  * 把对象c1,从sesison的一级缓存中删除
		 */
		s.evict(c1);
		
		Customer c2=(Customer)s.get(Customer.class, 2);
	
		s.flush();
		tx.commit();
		s.close();
		
	}
	
	@Test @SuppressWarnings("unused")
	public void testDelete() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c1=(Customer)s.get(Customer.class, 2);
		s.flush();
		tx.commit();
		s.close();
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////		
		s = sf.openSession();
		tx = s.beginTransaction();
		s.delete(c1);
		
		s.flush();
		tx.commit();
		s.close();
		
	}
}
package cn.gaily.test;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.oid.Customer;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/gaily/test/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf = config.buildSessionFactory();
	}

	@Test
	public void test1() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c1 = (Customer)s.load(Customer.class, new Integer(1));
		Customer c2 = (Customer)s.load(Customer.class, new Integer(1));
		System.out.println(c1 == c2);

		tx.commit();
		s.close();
	}
	
	@Test
	public void test2() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c = (Customer)s.load(Customer.class, new Integer(1));
		c.setName("Jake");
		c.setName("mike");
		
		tx.commit();
		s.close();
	}

}

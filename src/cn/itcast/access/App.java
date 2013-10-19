package cn.itcast.access;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf=null;
	static{
		Configuration config=new Configuration();
		config.configure("cn/itcast/access/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf=config.buildSessionFactory();
	}
	
	@Test
    public  void findCustomerById() {
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		Integer id=2;
		@SuppressWarnings("unused")
		Customer c=(Customer)s.get(Customer.class, id);
		//System.out.println(c.getId()+"   "+c.getName());
		System.out.println("xxxxx");
		tx.commit();
		s.close();
	}

}

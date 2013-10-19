package cn.itcast.oid.composite02;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/oid/composite02/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf = config.buildSessionFactory();
	}
	
	
	@Test @SuppressWarnings("unused")
	public void  testComposite() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c=new Customer();
	    
		CustomerID id=new CustomerID();
		id.setFirstName("a");
		id.setLastName("b");
		
		c.setId(id);
	    c.setDes("xxxxx");
	    
		s.save(c);
		
		tx.commit();
		s.close();
	}

}
package cn.itcast.search;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppMany2oneSet {
	
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/search/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	
	@Test
    public void fetchjoinload(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Order o=(Order)s.get(Order.class, 1);
    	
    	Customer c=o.getCustomer();
    	System.out.println(c.getId()+"  "+c.getName());
    	
    	tx.commit();
		s.close();
    }
}
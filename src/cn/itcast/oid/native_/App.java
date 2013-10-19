package cn.itcast.oid.native_;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/oid/native_/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf = config.buildSessionFactory();
	}
	
	
	@Test @SuppressWarnings("unused")
	public void  testIncrment() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();

		Customer c=new Customer();
		c.setName("方世玉");
		c.setAge(12);
		c.setDes("ddd");
		
		s.save(c);

		System.out.println("c.getId()    "+c.getId());
		System.out.println("xxxxxxxxxxxxxxxxxxxx");
		
		System.out.println("ppppppppppppppppppppp");
		
		tx.commit();
		s.close();
	}

}
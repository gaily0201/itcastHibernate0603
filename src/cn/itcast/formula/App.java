package cn.itcast.formula;

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
		config.configure("cn/itcast/formula/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf=config.buildSessionFactory();
	}
	

	@Test
    public  void saveCustomer() {
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
        Customer c=new Customer();
        c.setName("银");
        c.setAge(12);
        c.setDes("xxx");
        c.setPrice(36d);
		
		s.save(c);
		
		tx.commit();
		s.close();
	}

	
	
	
	@Test
    public  void findCustomerById() {
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		Integer id=2;
		Customer c=(Customer)s.get(Customer.class, id);
		System.out.println(c.getId()+"   "+c.getName()+c.getPrice()+"   "+c.getTotalPrice());
		System.out.println("xxxxx");
		tx.commit();
		s.close();
	}

}

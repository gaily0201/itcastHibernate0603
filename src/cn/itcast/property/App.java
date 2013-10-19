package cn.itcast.property;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/property/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf = config.buildSessionFactory();
	}

	@Test
	public void saveCustomer() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c = new Customer();
		//c.setName("银");
		c.setAge(89);
		c.setDes("dexx");
		s.save(c);
		tx.commit();
		s.close();
	}
	
	//<class>dynamic-update属性
	@Test
	public void updateCustomer_dynamic() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c = (Customer) s.get(Customer.class, 1);
		//c.setName("铁");
		c.setAge(26);
		c.setDes("yyyy");

		s.update(c);
		
		tx.commit();
		s.close();
	}

	
	

	@Test
	public void updateCustomerById() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c = new Customer();
		c.setId(1);
		c.setName("铁");
		c.setAge(25);
		c.setDes("px");

		s.update(c);
		
		tx.commit();
		s.close();
	}

	@Test
	public void findCustomerById() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Integer id = 2;
		Customer c = (Customer) s.get(Customer.class, id);
		System.out.println(c.getId() + "   " + c.getName());
		System.out.println("xxxxx");
		tx.commit();
		s.close();
	}

}

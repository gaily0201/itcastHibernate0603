package cn.itcast.one2onepk;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {

	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/one2onepk/hibernate.cfg.xml");
		config.addClass(Company.class);
		config.addClass(Address.class);
		sf = config.buildSessionFactory();
	}
	
	@Test
	public  void insert(){
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
	    
		Company c=new Company();
		c.setName("传智播客");
		
		Address address=new Address();
		address.setCity("北京");
		address.setCountry("中国");
		
        c.setAddress(address);
        address.setCompany(c);
        
        s.save(c);
        s.save(address);
        
	    
        tx.commit();
	    s.close();
	}
}
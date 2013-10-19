package cn.itcast.cache;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/cache/hibernate.cfg.xml");
		sf = config.buildSessionFactory();
	}
	
	@Test
    public void saveOrderAndCustomer(){
    	Session s=sf.openSession();
    	Session s2=sf.openSession();
    	System.out.println(s==s2);
    	
    	//<property name="current_session_context_class">thread</property>
    	Session s3=sf.getCurrentSession();
    	Session s4=sf.getCurrentSession();
    	System.out.println(s3==s4);
    }
}
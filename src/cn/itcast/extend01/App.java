package cn.itcast.extend01;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	
	static SessionFactory sf = null;
	
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/extend01/hibernate.cfg.xml");
		config.addClass(Employee.class);
		sf = config.buildSessionFactory();
	}
	
	//知识点1: 保存员工信息
	@Test
    public void saveEmployee(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Employee ee=new Employee();
    	ee.setName("郭靖");
    	
    	HourEmployee he=new HourEmployee();
    	he.setName("郭芙");
    	he.setRate(2000d);
    	
    	SalaryEmployee se=new SalaryEmployee();
    	se.setName("郭襄");
    	se.setSalary(3000d);
    	
        s.save(ee);
        s.save(se);
        s.save(he);
    	
		tx.commit();
		s.close();
    }
	
	//知识点2: 查询钟点工信息
	@Test
    public void findHourEmployee(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
        //Query query=s.createQuery("from  HourEmployee he");
        Query query=s.createQuery("from  Employee ee");
        query.list();
    	
		tx.commit();
		s.close();
    }
	
	
	
	

}
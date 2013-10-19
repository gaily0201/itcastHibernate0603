package cn.itcast.extend02;

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
		config.configure("cn/itcast/extend02/hibernate.cfg.xml");
		config.addClass(Employee.class);
		sf = config.buildSessionFactory();
	}
	
	//知识点3: 保存员工信息
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
	
	//知识点4: 查询钟点工信息
	@Test
    public void findHourEmployee(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
        Query query=s.createQuery("from  HourEmployee he");
        query.list();
    	
		tx.commit();
		s.close();
    }
	
	
	//知识点5: 查询员工信息
	@Test
    public void findEmployee(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
        Query query=s.createQuery("from Employee e");
        query.list();
    	
		tx.commit();
		s.close();
    }

	//知识点6: 删除员工信息(不用使用级联)
	@Test
    public void deleteEmployee(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
        Employee employee=(Employee)s.get(Employee.class, 2);
    	s.delete(employee);
		tx.commit();
		s.close();
    }
	
	//知识点7:查询唯一的员工,不是钟点工也不是正式员工
	@Test
	 public void findEmployeeOnlyOne(){
	    	Session s=sf.openSession();
	    	Transaction tx=s.beginTransaction();
	    	Query query=s.createQuery("from Employee e "+
	    			                   " where "+
	    			                   "  e.id not in(select h.id from HourEmployee h) and "+
	    			                   "  e.id not in(select s.id from SalaryEmployee s)");
	    	query.list();
	    	
			tx.commit();
			s.close();
	    }
}
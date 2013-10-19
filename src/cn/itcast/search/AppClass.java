package cn.itcast.search;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppClass {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/search/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	//知识点5:类级别检索策略
	/*
	 * 类级别检索策略
	 *    * get方法立即检索策略
	 *    * load方法
	 *       * lazy=true:延迟检索
	 *       * lazy=false:立即检索
	 */
	@Test
    public void load(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Customer c=(Customer)s.load(Customer.class, 1);
    	//Customer c=(Customer)s.get(Customer.class, 1);
    	System.out.println("xxxxxxxxxxxxxxxxxxx");
    	c.getAge();        
    	tx.commit();
		s.close();
    }
	
	
	/*
	 * 使用query接口查询:
	 *   * 不管lazy=true还是false,都是立即检索
	 */
	@Test
    public void query(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Query query=s.createQuery("from Customer c");
    	List list=query.list();
    	list.get(0);
    	list.get(1);
    	list.get(2);
    	
    	tx.commit();
		s.close();
    }

	
	
	
	

}
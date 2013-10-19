package cn.itcast.state;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppFlushCommitClear {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/state/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}

	//知识点4_2:清理session的缓存  测试flush和clear方法的使用
	@Test
	public void testFlushAndClear() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c=(Customer)s.get(Customer.class, 2);  //select
		/*
		 * 清理缓存,让缓存中的数据和数据库中的数据同步【把缓存中的数据写入到数据库中】.
		 *         方向:从缓存到数据库
		 * 注意:缓存中的数据不消失.        
		 */
		s.flush();
		Customer c1=(Customer)s.get(Customer.class, 2);  //没有select
		/**
		 * 清空缓存,相当于list.removeAll();
		 */
		s.clear();
		Customer c2=(Customer)s.get(Customer.class, 2);  //有select
		tx.commit();
		s.close();
	}
	
	//知识点4_4:清理session缓存    测试refresh方法的使用
	@Test
	public void testRefresh() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c=(Customer)s.get(Customer.class, 2);  //select
		c.setName("三");
		
		/**
		 * 让缓存区的数据和数据同步,方向:从数据库到缓存
		 */
		s.refresh(c);

		tx.commit();
		s.close();
	}

	//知识点4_5:清理session的缓存(设置缓存的清理模式)
	@Test
	public void testFlush() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();

		Customer c=new Customer();
		c.setName("xxxxx");
		
		s.save(c);
		
		s.flush(); //insert
		
		tx.commit();
		s.close();
	}
	
	
	//知识点4_5:清理session的缓存(设置缓存的清理模式)
	@Test
	public void testFlush21() {
		Session s = sf.openSession();
		
		//设置清理缓存的模式
		s.setFlushMode(FlushMode.NEVER);
		Transaction tx = s.beginTransaction();
		Customer c=new Customer();
		c.setName("xxxxx");
		s.save(c);
		s.flush();
		tx.commit();
		s.close();
	}
		
	//知识点4_6:清理session的缓存(设置缓存的清理模式)
	@Test
	public void testBatchInsert() {
		Session s = sf.openSession();
		
		//设置清理缓存的模式
		s.setFlushMode(FlushMode.NEVER);
		Transaction tx = s.beginTransaction();

		for(int i=0;i<1050;i++){
		    Customer c=new Customer();
		    c.setName("tom"+i);
	     	s.save(c);
	     	
	     	if(i%1000==0){
	     		//刷新缓存区的数据到数据库中
	     		s.flush();
	     		s.clear();
	     	}
		}
		
		s.flush();

		tx.commit();
		s.close();
	}
	
	@Test
	public void xxxxxxx() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		s.setFlushMode(FlushMode.AUTO);
		Customer c=new Customer();
		c.setName("tttt");
		s.save(c);
		
		Customer c2=(Customer)s.get(Customer.class, 8);  //select
		
		
		s.close();
	}

}
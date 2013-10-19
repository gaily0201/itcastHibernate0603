package cn.itcast.oid;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/oid/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf = config.buildSessionFactory();
	}

	@Test
	public void saveCustomer() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c = new Customer();
		c.setName("金");
		c.setAge(80);
		c.setDes("tttt");
		s.save(c);
		tx.commit();
		s.close();
	}
	
	//理解session的一级缓存
	@Test @SuppressWarnings("unused")
	public void findCustomer() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		/**
		 * 查询id=1的数据时,(查询)
		 *    * 首先到session的一级缓存中去查找id=1的对象
		 *       * 如果没有找到,此时要查询数据库,查询的id=1的信息要放置到session的一级缓存中.
		 *       * 如果找了,直接使用
		 */
		Customer c1 = (Customer) s.get(Customer.class, 1);  //select
		Customer c2 = (Customer) s.get(Customer.class, 2);  //select
		Customer c3 = (Customer) s.get(Customer.class, 1);   //不产生select语句
		
		System.out.println(c1==c2); //false
		System.out.println(c1==c3);  //true

		System.out.println("xxxxx");
		tx.commit();
		s.close();
	}
	
	
	
	
	//知识点5_3:理解session的快照
	@Test @SuppressWarnings("unused")
	public void testSessionKuaizhao() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		/*
		 * 当查询id=1时,
		 *   * 先到sesison的一级缓存中查找id=1的对象
		 *      * 没有找到,以id=1为条件查询数据库,查询的数据库，放置到session的一级缓存一份,同时还要放置到快照[快照中放置的对象的属性值]中一份
		 *      
		 *   * 只有查询时，才会有快照.   
		 */
		Customer c1 = (Customer) s.get(Customer.class, 1);
		c1.setName("铁");
		
		
		/*
		 * s.flush();
		 *   * 清理缓存,拿缓存中的数据和快照中的数据做比对【比对属性】
		 *      * 如果相等,不会产生update语句
		 *      * 如果不相等,会产生update语句
		 */
		s.flush();
		
		/*
		 * tx.commit();
		 *  * s.flush:清理缓存,刷新缓存中的数据到数据库.让缓存中的数据库和数据库中的数据同步
		 *  * tx.commit(); 提交.
		 */
		tx.commit();
		s.close();
	}
	
	
	//知识点5_3:理解Id的生成
	@Test @SuppressWarnings("unused")
	public void testId() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();

		Customer c=new Customer();
		c.setName("方世玉");
		c.setAge(12);
		c.setDes("ddd");
		
		System.out.println("保存前  "+c.getId());//null
		
		//把数据到放置到session的一级缓存中,同时分配一个oid
		s.save(c);
		
		System.out.println("保存后  "+c.getId());  //有值
		
		//清理缓存,把数据刷新到数据库中.此时数据库还没有确认
		s.flush();
		
		//确认
		tx.commit();
		
		s.close();
	}

}
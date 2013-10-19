package cn.itcast.search;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppMany2one {
	
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/search/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	/*
	 * 测试关联级别的检索Many2one
	 * fetch="join/select"  
	 * lazy="false/proxy/no-proxy"
	 * 配置many-to-one name="customer" class="cn.itcast.search.Customer"  fetch="join" lazy="false"
	 * 使用load和get方法测试:
	 *   * fetch="join" lazy="false":迫切左外连接
	 *   * fetch="join" lazy="proxy":迫切左外连接
	 *   * fetch="join" lazy="no-proxy":迫切左外连接
	 */
	@Test
    public void fetchjoinload(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Order o=(Order)s.load(Order.class, 1);
        System.out.println(o.getId()+"   "+o.getOrderNumber()+"  "+o.getPrice());
    	
    	Customer c=o.getCustomer();
    	System.out.println(c.getId()+"  "+c.getName());
    	
    	tx.commit();
		s.close();
    }
	
	
	/*
	 * 测试关联级别的检索Many2one
	 * fetch="join/select"  
	 * lazy="false/proxy/no-proxy"
	 * 配置many-to-one name="customer" class="cn.itcast.search.Customer"  fetch="join" lazy="false"
	 * 使用query接口测试:
	 *   * fetch="join" lazy="false":立即查询订单关联的客户
	 *   * fetch="join" lazy="proxy":立即查询订单关联的客户
	 */
	@Test
    public void fetchjoinquery(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Query query=s.createQuery("from Order o");
    	List<Order> list=query.list();
    	
    	for(Order o:list){
    		System.out.println(o.getCustomer().getName() +"   "+o.getCustomer().getName());
    	}
    	
    	
    	tx.commit();
		s.close();
    }

	
	/*
	 * 测试关联级别的检索Many2one
	 * fetch="join/select"  
	 * lazy="false/proxy/no-proxy"
	 * 配置many-to-one name="customer" class="cn.itcast.search.Customer"  fetch="select" lazy="false"
	 * 使用load和get方法测试:
	 *   * fetch="select" lazy="false":立即查询订单关联的客户
	 *   * fetch="select" lazy="proxy":迫切左外连接
	 *       * 如果Customer.hbm.xml中lazy="false,立即查询订单关联的客户
	 *           <class name="cn.itcast.search.Customer" table="customers"  lazy="false">
	 *             * 如果Customer.hbm.xml中lazy="true,延迟查询订单关联的客户
	 *           <class name="cn.itcast.search.Customer" table="customers"  lazy="true">
	 */
	@Test
    public void fetchselectload(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Order o=(Order)s.load(Order.class, 1);
        System.out.println(o.getId()+"   "+o.getOrderNumber()+"  "+o.getPrice());
    	
    	Customer c=o.getCustomer();
    	System.out.println(c.getId()+"  "+c.getName());
    	
    	tx.commit();
		s.close();
    }
	
	/*
	 * 测试关联级别的检索Many2one
	 * fetch="join/select"  
	 * lazy="false/proxy/no-proxy"
	 * 配置many-to-one name="customer" class="cn.itcast.search.Customer"  fetch="join" lazy="false"
	 * 使用query接口测试:
	 *   * fetch="select" lazy="false":立即查询订单关联的客户
	 *   * fetch="select" lazy="proxy":迫切左外连接
	 *       * 如果Customer.hbm.xml中lazy="false,立即查询订单关联的客户
	 *           <class name="cn.itcast.search.Customer" table="customers"  lazy="false">
	 *             * 如果Customer.hbm.xml中lazy="true,延迟查询订单关联的客户
	 *           <class name="cn.itcast.search.Customer" table="customers"  lazy="true">
	 */
	@Test
    public void fetchselectquery(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Query query=s.createQuery("from Order o");
    	List<Order> list=query.list();
    	
    	for(Order o:list){
    		System.out.println(o.getCustomer().getName() +"   "+o.getCustomer().getName());
    	}
    	
    	tx.commit();
		s.close();
    }
	
	
}
package cn.itcast.search;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppSet {
	
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/search/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	/*
	 * 测试关联级别的检索
	 * fetch="join/select/subselect"  
	 * lazy="true/false/extra"
	 * 配置<set name="orders" table="orders" inverse="true" fetch="join"  lazy="false">
	 * 使用load和get方法测试:
	 *   * fetch="join" lazy="false":迫切左外连接
	 *   * fetch="join" lazy="true":迫切左外连接
	 *   * fetch="join" lazy="extra":迫切左外连接
	 */
	@Test
    public void fetchjoinload(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Customer c=(Customer)s.load(Customer.class, 1);
    	Set<Order> orderes=c.getOrders();
        Iterator<Order> it=orderes.iterator();
        while(it.hasNext()){
        	Order o=it.next();
        	System.out.println(o.getId() +"   "+o.getOrderNumber()+"  "+o.getPrice());
        }
    	
    	
    	tx.commit();
		s.close();
    }
	
	/*
	 * 测试关联级别的检索
	 * fetch="join/select/subselect"  
	 * lazy="true/false/extra"
	 * 配置<set name="orders" table="orders" inverse="true" fetch="join"  lazy="false">
	 * 使用query接口测试:
	 *   * fetch="join" lazy="false":查询订单集合立即检索
	 *      *  select * from customers [id=1,2,3]
	 *      *  select * from orders where id=1
	 *      *  select * from orders where id=2
	 *      *  select * from orders where id=3
	 *   
	 *   * fetch="join" lazy="true":查询订单集合延迟检索
	 *      *  select * from customers [id=1,2,3]
	 *      *  select * from orders where id=1语句,当执行list.get(0).getOrders().size();代码时, 才执行
	 *      *  select * from orders where id=2语句,当执行list.get(1).getOrders().size();代码时, 才执行
	 *      *  select * from orders where id=3语句,当执行list.get(2).getOrders().size();代码时, 才执行
	 *   
	 *   * fetch="join" lazy="extra":查询订单集合增加延迟检索,
	 *      select count(id) from orders  where customer_id =?
	 *      list.get(0).getOrders().size();,获取订单集合的数量,就查数量.
	 */
	@Test
    public void fetchjoinQuery(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Query query=s.createQuery("from Customer c");
    	List<Customer> list=query.list();
    	
    	list.get(0).getOrders().size();
    	list.get(1).getOrders().size();
    	list.get(2).getOrders().size();
    	
    	tx.commit();
		s.close();
    }
	
	/*
	 * 测试关联级别的检索
	 * fetch="join/select/subselect"  
	 * lazy="true/false/extra"
	 * 配置<set name="orders" table="orders" inverse="true" fetch="select"  lazy="false">
	 * 使用load和get方法测试:
	 *   * fetch="select" lazy="false":查询订单集合采用立即检索
	 *   * fetch="select" lazy="true":查询订单集合采用延迟检索
	 *   * fetch="select" lazy="extra":查询订单集合采用增强延迟检索.
	 *        * select count(id)  from orders  where customer_id =?
	 */
	@Test
    public void fetchselectload(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Customer c=(Customer)s.load(Customer.class, 1);
    	Set<Order> orderes=c.getOrders();
     
    	c.getOrders().size();
    	
    	tx.commit();
		s.close();
    }
	
	
	/*
	 * 测试关联级别的检索
	 * fetch="join/select/subselect"  
	 * lazy="true/false/extra"
	 * 配置<set name="orders" table="orders" inverse="true" fetch="select"  lazy="false">
	 * 使用query接口测试:
	 *   * fetch="select" lazy="false":查询订单集合立即检索
	 *      *  select * from customers [id=1,2,3]
	 *      *  select * from orders where id=1
	 *      *  select * from orders where id=2
	 *      *  select * from orders where id=3
	 *   
	 *   * fetch="select" lazy="true":查询订单集合延迟检索
	 *      *  select * from customers [id=1,2,3]
	 *      *  select * from orders where id=1语句,当执行list.get(0).getOrders().size();代码时, 才执行
	 *      *  select * from orders where id=2语句,当执行list.get(1).getOrders().size();代码时, 才执行
	 *      *  select * from orders where id=3语句,当执行list.get(2).getOrders().size();代码时, 才执行
	 *   
	 *   * fetch="select" lazy="extra":查询订单集合增加延迟检索,
	 *      select count(id) from orders  where customer_id =?
	 *      list.get(0).getOrders().size();,获取订单集合的数量,就查数量.
	 */
	@Test
    public void fetchselectQuery(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Query query=s.createQuery("from Customer c");
    	List<Customer> list=query.list();
    	
    	list.get(0).getOrders().size();
    	list.get(1).getOrders().size();
    	list.get(2).getOrders().size();
    	
    	tx.commit();
		s.close();
    }
	
	
	/*
	 * 测试关联级别的检索
	 * fetch="join/select/subselect"  
	 * lazy="true/false/extra"
	 * 配置<set name="orders" table="orders" inverse="true" fetch="subselect"  lazy="false">
	 * 使用load和get方法测试:
	 *   * fetch="subselect" lazy="false":查询订单集合采用立即检索
	 *   * fetch="subselect" lazy="true":查询订单集合采用延迟检索
	 *   * fetch="subselect" lazy="extra":查询订单集合采用增强延迟检索.
	 *        * select count(id)  from orders  where customer_id =?
	 */
	@Test
    public void fetchsubselectload(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Customer c=(Customer)s.load(Customer.class, 1);
    	Set<Order> orderes=c.getOrders();
     
    	c.getOrders().size();
    	
    	tx.commit();
		s.close();
    }
	
	/*
	 * 测试关联级别的检索
	 * fetch="join/select/subselect"  
	 * lazy="true/false/extra"
	 * 配置<set name="orders" table="orders" inverse="true" fetch="subselect"  lazy="false">
	 * 使用query接口测试:
	 *   * fetch="subselect" lazy="false":查询订单集合立即检索,采用子查询
	 *      *  select * from customers [id=1,2,3]
	 *      *  select * from orders where id in( select customer0_.id from customers customer0_)
	 *   
	 *   * fetch="subselect" lazy="true":查询订单集合延迟检索+子查询
	 *      *  select * from customers [id=1,2,3]
	 *      *  select * from orders where id in( select customer0_.id from customers customer0_)语句,
	 *         当执行list.get(0).getOrders().size();代码时, 才执行
	 *   
	 *   * fetch="subselect" lazy="extra":查询订单集合增加延迟检索,
	 *      select count(id) from orders  where customer_id =?
	 *      list.get(0).getOrders().size();,获取订单集合的数量,就查数量.
	 */
	@Test
    public void fetchsubselectQuery(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Query query=s.createQuery("from Customer c");
    	List<Customer> list=query.list();
    	
    	list.get(0).getOrders().size();
    	list.get(1).getOrders().size();
    	list.get(2).getOrders().size();
    	
    	tx.commit();
		s.close();
    }

}
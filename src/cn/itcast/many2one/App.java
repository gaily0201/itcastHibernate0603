package cn.itcast.many2one;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/many2one/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	
	/*
	 *  insert into orders values(2,"NO01",45,null);
	 *  insert into customers values(1,'"刘备');
	 *  update orders set customer_id=1 where id=2
	 */
	//知识点1:先保存订单，再保存客户
	@Test
    public void saveOrderAndCustomer(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Customer c=new Customer();
    	c.setName("关羽");
    	
    	Order o=new Order();
    	o.setOrderNumber("NO02");
    	o.setPrice(56d);
    	
    	//建立订单和客户的关联
    	o.setCustomer(c);
    	
    	//保存订单
    	s.save(o);
    	
    	//保存客户
    	s.save(c);
    	
		tx.commit();
		s.close();
    }
	
	
	//知识点2:先保存客户，再保存订单
	/*
	 *  insert into customers values(1,'"刘备');
	 *  insert into orders values(2,"NO01",45,1);
	 */
	//知识点1:先保存订单，再保存客户
	@Test
    public void saveCustomerAndOrder(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Customer c=new Customer();
    	c.setName("刘备");
    	
    	Order o=new Order();
    	o.setOrderNumber("NO01");
    	o.setPrice(45d);
    	
    	//建立订单和客户的关联
    	o.setCustomer(c);
    	
    	//保存客户
    	s.save(c);
    	
    	//保存订单
    	s.save(o);
    	
		tx.commit();
		s.close();
    }
	
	//查询订单
	@Test
    public void findOrder(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Order o=(Order)s.get(Order.class, 1);
    	System.out.println(o.getId()+"  "+o.getOrderNumber()+"  "+o.getPrice()+"  ");
    	
    	
    	/*查询订单的时候,关联到客户
    	 *  <many-to-one name="customer" class="cn.itcast.many2one.Customer"> 
		         <column name="customer_id"></column>
		      </many-to-one>
    	 */
    	Customer c=o.getCustomer();
    	System.out.println(c.getId()+"    "+c.getName());
    	
		tx.commit();
		s.close();
    }
	
	
	
	//知识点4:先保存客户，再保存订单
	@Test
    public void saveCustomerAndOrder2(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	//临时对象,不再sesison的一级缓存中,OID=null
    	Customer c=new Customer();
    	c.setName("刘备");
    	
    	//临时对象  不再sesison的一级缓存中  OID=null
    	Order o=new Order();
    	o.setOrderNumber("NO01");
    	o.setPrice(45d);
    	
    	//建立订单和客户的关联
    	o.setCustomer(c);
    	
    	//保存客户
    	//s.save(c);
    	
    	//保存订单,把Order对象放入session的一级缓存,同时，给对象分配一个唯一的OID
    	//此时Order对象变为持久对象
    	s.save(o);
    	
		tx.commit();
		s.close();
    }
	
	
	
	


}
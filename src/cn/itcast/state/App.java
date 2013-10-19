package cn.itcast.state;


import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/state/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	
	//知识点6:保存客户和订单(客户和订单建立双向关联)
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
    	//o.setCustomer(c);
    	
    	//建立客户和订单的关联
    	c.getOrders().add(o);
    	
    
    	//保存客户
    	s.save(c);
    	
    	//保存订单
    	s.save(o);
    	
		tx.commit();
		s.close();
    }
	
	
	//知识点7:保存客户和不保存订单
	@Test
    public void saveCustomerAndNoOrder(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	//临时对象 没有oid oid=null
    	Customer c=new Customer();
    	c.setName("关羽");
    	
    	//临时对象 oid=null
    	Order o=new Order();
    	o.setOrderNumber("NO02");
    	o.setPrice(56d);
    	
    	//建立订单和客户的关联
    	o.setCustomer(c);
    	
    	//建立客户和订单的关联
    	c.getOrders().add(o);
    	
    
    	//保存客户,放置客户到session的一级缓存 这时要分配给客户一个oid,转化为持久对象
    	//客户所关联的订单集合是一个临时对象.
    	s.save(c);
    	
    	//保存订单
    	//s.save(o);
    	
		tx.commit();
		s.close();
    }
	
	
	//知识点8:查询客户和订单
	@Test
    public void findCustomerAndOrder(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    		
        Customer c=(Customer)s.get(Customer.class, 1);
    	System.out.println(c.getId()+"    "+c.getName());
    	
    	/*
    	 *  <set name="orders" table="orders" cascade="save-update">
            <key>
            <column name="customer_id"></column>
	        </key>
	              <one-to-many class="cn.itcast.many2onedouble.Order"/>
	         </set>
    	 */
    	Set<Order> set=c.getOrders();
    	for(Order o:set){
    		System.out.println(o.getId()+"    "+o.getOrderNumber()+"   "+o.getPrice());
    	}
    	
		tx.commit();
		s.close();
    }
	
	
	//知识点9:对象导航
	@Test
    public void Objectrelation(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
   
    	Order o1=new Order();
    	o1.setOrderNumber("NO01");
    	o1.setPrice(10d);
    	
    	Order o2=new Order();
    	o2.setOrderNumber("NO02");
    	o2.setPrice(20d);
    	
    	Order o3=new Order();
    	o3.setOrderNumber("NO03");
    	o3.setPrice(30d);
    	
    	Customer c=new Customer();
    	c.setName("关羽");
    	
    	//order1关联到customer 而customer没有关联到order1
        o1.setCustomer(c);
          
        //customer关联到order2 order3   而order2 order3 没有关联到customer
        c.getOrders().add(o2);
        c.getOrders().add(o3);
        
        s.save(o2);
        
    	tx.commit();
		s.close();
    }

	//知识点10:保持程序的健壮性
	@Test
    public void programRalation(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
   
    	Order o1=new Order();
    	o1.setOrderNumber("NO01");
    	o1.setPrice(10d);
    	
    	Customer c=new Customer();
    	c.setName("关羽");
    	
    	//订单关联到客户
        //o1.setCustomer(c);//加上和没有加上没有影响
        //客户关联到订单
        c.getOrders().add(o1);
        
        s.save(c);
        
    	tx.commit();
		s.close();
    }
	
	
	//初始化数据库,执行三次
	@Test
    public void initData(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
   
    	Order o1=new Order();
    	o1.setOrderNumber("NO01");
    	o1.setPrice(10d);
    	
    	Order o2=new Order();
    	o2.setOrderNumber("NO02");
    	o2.setPrice(20d);
    	
    	Order o3=new Order();
    	o3.setOrderNumber("NO03");
    	o3.setPrice(30d);
    	
    	Customer c=new Customer();
    	c.setName("张飞");
    	
        o1.setCustomer(c);
        o2.setCustomer(c);
        o3.setCustomer(c);
          
        c.getOrders().add(o1);
        c.getOrders().add(o2);
        c.getOrders().add(o3);
        s.save(o2);
    	tx.commit();
		s.close();
    }

	//知识点11:订单变更客户  更改订单表id=6的customer_id=3更改为4
	@Test
    public void change_customer_id(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
   
        //获取id=6的订单
    	Order o6=(Order)s.get(Order.class, 6);
    	
    	//获取id=4的客户
    	Customer c4=(Customer)s.get(Customer.class, 4);
    	
    	//建立6号订单和4号客户的关联
    	o6.setCustomer(c4);
    	
    	//在4号客户的订单集合中加入6号订单
    	c4.getOrders().add(o6);
      	
    	s.flush();
    	
    	tx.commit();
		s.close();
    }
	
	//解除6号订单和3号客户的关联
	@Test
    public void remove_customer_id(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
   
        //获取id=6的订单
    	Order o6=(Order)s.get(Order.class, 6);
    	
    	//获取id=4的客户
    	Customer c3=(Customer)s.get(Customer.class, 3);
    	
    	//设置6号订单关联的客户为null
    	o6.setCustomer(null);
    	
    	//在3号客户的订单集合中,删除6号订单
    	c3.getOrders().remove(o6);
      	
    	s.flush();
    	tx.commit();
		s.close();
    }
	
	
	//知识点13:级联删除1号客户的同时，删除1号客户所关联的订单
	@Test
    public void remove_customer(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
   
    	//获取id=1的客户
    	Customer c1=(Customer)s.get(Customer.class, 1);
    	
    	s.delete(c1);
    	
    	s.flush();
    	tx.commit();
		s.close();
    }

	
	//知识点14:解除关联关系 ---父子关系  解除6号订单和3号客户的关联,同时删除6号订单
	@Test
    public void remove_Order(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
   
    	Order o6=(Order)s.get(Order.class, 6);
    	
    	Customer c3=(Customer)s.get(Customer.class, 3);
    	
    	o6.setCustomer(null);
    	c3.getOrders().remove(o6);
    	
    	s.flush();
    	tx.commit();
		s.close();
    }
}
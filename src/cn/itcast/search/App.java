package cn.itcast.search;

import java.util.Random;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/search/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	//知识点1:初始化测试数据,执行3次.
	@Test
    public void initData(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();

    	Customer c=new Customer();
    	c.setName("jack");
    	c.setAge(20+new Random().nextInt(10));
    	s.save(c);
    	
    	for(int i=0;i<10;i++){
    	  Order o=new Order();
    	  o.setOrderNumber("NO"+i);
    	  o.setPrice(3000d+new Random().nextInt(1000));
    	  o.setCustomer(c);
    	  c.getOrders().add(o);
          s.save(o);    		
    	}
    	tx.commit();
		s.close();
    }
	
	/*知识点2:区分立即检索和延迟检索
	 *   * class name="cn.itcast.search.Customer" table="customers"  lazy="false"
	 *      * lazy="false":立即检索,不管是不是需要访问对象中属性值,都执行select语句.
	 *      * lazy="true":延迟检索,当需要访问对象中属性值的时候,才执行select语句.
	 *      
	 *   * load方法有立即检索和延迟检索
	 *   * get总是立即检索   
	 */
	@Test
    public void loadlazy(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	Customer c=(Customer)s.get(Customer.class, 1);
    	System.out.println("xxxxxxxxxxxxxxxxxxx");
    	c.getAge();        
    	tx.commit();
		s.close();
    }
	
	
	
	//知识点3:理解延迟检索中的代理,只有延迟检索才有代理,立即检索没有代理
	@Test
    public void loadproxy(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	/*
    	 * lazy=true:是延迟检索,创建一个代理对象,放置session的缓存中
    	 *    * 在代理对象中保存:id的值=1,所以c.getId()不查询数据库.
    	 *    * c.getAge()要访问对象中的值,这时,以代理对象中保存:id的值=1,查询数据库,初始化对象.
    	 */
    	Customer c=(Customer)s.load(Customer.class, 1);
    	c.getClass();  //不产生select语句
    	c.getId();     //不产生select语句
    	c.getAge();    //产生select语句    
    	tx.commit();
    	s.close();
    }
	
	
	//知识点3:初始化延迟检索中的代理
	@Test
    public void initloadproxy(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Customer c=(Customer)s.load(Customer.class, 1);
    	
    	//如果对象c没有被初始化
        if(!Hibernate.isInitialized(c)){
        	
        	//开始初始化.查询数据库,执行select语句 
        	Hibernate.initialize(c);//等价于c.getAge();
        	
        }
    	tx.commit();
    	s.close();
    }	
}
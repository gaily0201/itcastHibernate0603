package cn.itcast.primer;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	
	static SessionFactory sf=null;
	static{
		/*
		 * 加载hibernate.cfg.xml映射文件
		 *   * config:保存了连接数据库的信息和映射文件的配置信息
		 */
		Configuration config=new Configuration();
		
		/*
		 * config.configure();
		 *    * 默认加载类路径下的hibernate.cfg.xml
		 * config.configure("cn/itcast/primer/hibernate.cfg.xml");
		 *    * 加载指定路径下的hibernate.cfg.xml文件
		 */
		config.configure("cn/itcast/primer/hibernate.cfg.xml");
		
		/*
		 * 方法一:通过代码的方式加载映射文件
		 *   config.addResource("cn/itcast/primer/Customer.hbm.xml");
		 * 方法二:
		 *    * config.addClass(Customer.class);
		 *    * 要求:Customer.hbm.xml配置文件的名称必须和Customer.class的类名一致,同时两个文件要放置到同一个目录下
		 */
		//方法一
		//config.addResource("cn/itcast/primer/Customer.hbm.xml");
		
		//方法二
		config.addClass(Customer.class);
		 /*
		  * 获取sessionFactory
		  * * 利用config保存的信息创建SessionFactory,
		  * * SessionFactory 保存了连接数据库的信息和映射文件的配置信息,预定义的sql语句
		  *   SessionFactory是线程安全的,最好有一个sessionFactory
		  */
		 sf=config.buildSessionFactory();
	}
	
	@Test
    public  void saveCustomer() {
		//获取session
		Session s=sf.openSession();
		//开始事务
		Transaction tx=s.beginTransaction();
		//实例Customer对象
		Customer c=new Customer();
		c.setName("银晶");
		c.setAge(54);
		c.setDes("需");
		//保存
		s.save(c);
		//提交
		tx.commit();
		//关闭session
		s.close();
	}
	
	@Test
    public  void updateCustomer() {
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();

		Customer c=new Customer();
		c.setId(1);
		c.setName("铁晶");
		c.setAge(53);
		c.setDes("酷");
		s.update(c);
		
		tx.commit();
		s.close();
	}
	
	@Test
    public  void findCustomerById() {
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		Integer id=1;
		Customer c=(Customer)s.get(Customer.class, id);
		System.out.println(c.getId()+"   "+c.getName());
		
		tx.commit();
		s.close();
	}


	@Test
    public  void deleteCustomerById() {
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		Integer id=1;
		Customer c=(Customer)s.get(Customer.class, id);
		
		s.delete(c);
		
		tx.commit();
		s.close();
	}
	
	
	@Test
    public  void findCustomers() {
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		/*
		 * hql:面向对象的语句
		 * Query:接口:执行对数据库的crud操作
		 */
		Query query=s.createQuery("from Customer c");
		List<Customer> list=query.list();
		for(Customer c:list){
			System.out.println(c.getId()  +"   "+c.getName());
		}
		
		tx.commit();
		s.close();
	}
	
	
	
}

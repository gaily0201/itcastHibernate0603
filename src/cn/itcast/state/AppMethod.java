package cn.itcast.state;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppMethod {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/state/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}

	@Test
	public void saveOrderAndCustomer() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();

		Order o1 = new Order();
		o1.setOrderNumber("NO01");
		o1.setPrice(10d);

		Order o2 = new Order();
		o2.setOrderNumber("NO02");
		o2.setPrice(20d);

		Order o3 = new Order();
		o3.setOrderNumber("NO03");
		o3.setPrice(30d);

		Customer c = new Customer();
		c.setName("马超");

		o1.setCustomer(c);
		o2.setCustomer(c);
		o3.setCustomer(c);

		c.getOrders().add(o1);
		c.getOrders().add(o2);
		c.getOrders().add(o3);

		s.save(o2);
		s.save(o1);
		s.save(o3);
		s.save(c);

		tx.commit();
		s.close();
	}

	//知识点13_1: 操纵持久化对象-update()
	@Test
	public void updatePersist() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c1 = (Customer) s.get(Customer.class, 1);
		s.evict(c1);
		s.update(c1);
		tx.commit();
		s.close();
	}
	
	//知识点13_2: 操纵持久化对象-update()
	/*
	 *  * <class name="cn.itcast.state.Customer" table="customers" select-before-update="true">
	 *    * select-before-update="true":在更新之前查询
	 * 
	 */
	@Test
	public void updatePersistClass() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c1 = (Customer) s.get(Customer.class, 1);

		s.evict(c1);
		
		s.update(c1);
		
		tx.commit();
		s.close();
	}
	
	//知识点13_3: 操纵持久化对象-update()
	@Test
	public void updatePersistOID() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c1 = (Customer) s.get(Customer.class, 1);
		s.evict(c1);
		Customer c2 = (Customer) s.get(Customer.class, 1);
		s.update(c1);
		tx.commit();
		s.close();
	}
	
	//知识点13_4: 操纵持久化对象-update()
	/*
	 *  这里设置:
	 *         * <class name="cn.itcast.state.Customer" table="customers" select-before-update="false">
	 *           或者不加
	 *         * select-before-update="true":在更新之前查询
	 */
	@Test
	public void updatePersistRemove() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		Customer c4 = (Customer) s.get(Customer.class, 4);

		s.evict(c4);
		
		System.out.println("xxxxxxxxxxxxxxxx");
		//如果更新时,数据库不存在响应的记录,则抛出异常
		s.update(c4);
		tx.commit();
		s.close();
	}
	
	
	//知识点14_2: 操纵持久化对象-saveOrupdate()
	@Test
	public void saveOrupdatePersist() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c=new Customer();
		c.setName("xxxxxxx");
		
		//此时执行update操作
		s.saveOrUpdate(c);

		tx.commit();
		s.close();
	}
	
	
	//知识点14_3: 操纵持久化对象-saveOrupdate()
	@Test
	public void saveOrupdateEvict() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c4 = (Customer) s.get(Customer.class, 4);
		s.evict(c4);
		
		//如果是游离对象,执行update操作
		s.saveOrUpdate(c4);
		
		tx.commit();
		s.close();
	}
	
	//知识点14_4: 操纵持久化对象-saveOrupdate()
	@Test
	public void saveOrupdatePersistx() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c4 = (Customer) s.get(Customer.class, 4);
		
		//如果是持久对象,不执行任何操作
		s.saveOrUpdate(c4);
		
		tx.commit();
		s.close();
	}
	
	/*
	 * 知识点14_5: 操纵持久化对象-saveOrupdate()
	 *  判断临时对象的标准:
	 *  * 如果Customer类中private Integer id;定义为Integer类型,
	 *      * id==null:此时表示Customer对象是一个临时对像
     *  * 如果 private int id=4;定义为int类型 
     *      需要在Customer.hbm.xml文件中的id标签中增加unsaved-value属性  <id name="id" type="integer" unsaved-value="0">
     *      * 如果Customer类中id的属性值和id标签中unsaved-value的值相同,则此时对象为临时对象,执行save操作
     *      * 如果Customer类中id的属性值和id标签中unsaved-value的值不相同,则此时对象为不是临时对象,执行update操作
	 */
	@Test
	public void saveOrupdateInt() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c=new Customer();
		c.setName("xxxxxxx");
		
		s.saveOrUpdate(c);
		
		tx.commit();
		s.close();
	}
	
	/*
	 * 知识点15: 操纵持久化对象-get()  load()
	 *  get和load方法区别:
	 *     * 如果查询的id不存在
	 *        * get方法返回null
	 *        * load方法抛出异常
	 *     * get方法总是立即检索
	 *       load:可以配置成立即检索和延迟检索 
	 */
	@Test
	public void getOrLoad() {
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		Customer c6 = (Customer) s.load(Customer.class, 6);
		
		System.out.println("c6  =  "+c6);
		
		tx.commit();
		s.close();
	}
	
	
}
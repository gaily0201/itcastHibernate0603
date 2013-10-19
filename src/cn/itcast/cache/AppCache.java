package cn.itcast.cache;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppCache {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/cache/hibernate.cfg.xml");
		sf = config.buildSessionFactory();
	}
	
	//知识点12:测试二级缓存和散列数据
	@Test @SuppressWarnings("unused")
    public void testSecondCache(){
	  Session s=sf.openSession();
	  Transaction tx=s.beginTransaction();
	  
	  /**
	   * 类级别的二级缓存
	   * 由于启用了二级缓存,并且配置了<class-cache class="cn.itcast.cache.Customer" usage="read-write" />   
	   * 查询出来的Customer对象,放置到一级缓存一份,同时又放置到二级缓存中
	   * 二级缓存中放置端的是对象的散装数据[放置的是对象中的属性的值]
	   * 要查询数据库 
	   */
	  Customer  c=(Customer)s.get(Customer.class, 1);
	  System.out.println(" c   "+c);
	  
	  //从一级缓存查找，找到,这时不再去二级缓存查找,没有查询数据库
	  Customer c1=(Customer)s.get(Customer.class, 1);
	  System.out.println(" c1   "+c1);
	  tx.commit();
	  s.close();
	  
	  s=sf.openSession();
	  tx=s.beginTransaction();
	  
	  /*
	   * 在一级缓存没有查找到.到二级缓存查找.有,获取二级缓存中对象的属性值,重新创建一个新的对象.
	   *   没有查询数据库,不产生select语句
	   */
	  Customer  c3=(Customer)s.get(Customer.class, 1);
	  System.out.println(" c3   "+c3);
	  tx.commit();
	  s.close();
	  
	  
	  s=sf.openSession();
	  tx=s.beginTransaction();
	  
	  /*
	   * 在一级缓存没有查找到.到二级缓存查找.有,没有查询数据库,不产生select语句
	   */
	  Customer  c4=(Customer)s.get(Customer.class, 1);
	  System.out.println(" c4   "+c4);
	  tx.commit();
	  s.close();
    }
	
	//知识点13:测试一级缓存更新数据会同步到二级缓存
	@Test @SuppressWarnings("unused")
    public void testUpdateSecondCache(){
	  Session s=sf.openSession();
	  Transaction tx=s.beginTransaction();
	  
	  /**
	   * 执行select语句,查询数据库
	   *   * 放置值到一级缓存一份[name=jack]
	   *   * 放置值到二级缓存一份[name=jack]
	   */
	  Customer  c3=(Customer)s.get(Customer.class, 3);
	  
	  c3.setName("xxxxx");//[name=xxxx]
	  
	  //修改一级缓存的值,同时又会修改二级缓存的值
	  s.flush();
	  tx.commit();
	  s.close();
	  
	  s=sf.openSession();
	  tx=s.beginTransaction();
	 
	  /**
	   * 从二级缓存查找[name="xxxxx"]
	   */
	  Customer  c4=(Customer)s.get(Customer.class, 3);
	  System.out.println(c4.getName());
	  tx.commit();
	  s.close();
    }
	
	//知识点14:测试二级缓存的数据存放到临时目录
	@Test @SuppressWarnings("unused")
    public void testowerFlow(){
	  Session s=sf.openSession();
	  Transaction tx=s.beginTransaction();
	  Query query=s.createQuery("from Order o");

	  query.list().size();
	  
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  
	  tx.commit();
	  s.close();
    }

	
	//测试集合级别的缓存
	@Test @SuppressWarnings("unused")
    public void testCollectionCache(){
	  Session s=sf.openSession();
	  Transaction tx=s.beginTransaction();
	  
	  Customer  c=(Customer)s.get(Customer.class, 1);
	  /*
	   * 如果没有配置<class-cache class="cn.itcast.cache.Order" usage="read-write" />所以二级缓存中不能存放Order对象
	   *   *  即使是集合中的Order对象也不行
	   *   *  c.getOrders()，获取订单集合
	   *   *  又配置了 <collection-cache  collection="cn.itcast.cache.Customer.orders" usage="read-write"/>集合级别的缓存
	   *   *  在集合级别的缓存中存放了Order对象的id的值:10个:id=1 ,2,3,4,5,6,7,8,9
	   * 
	   * 如果配置<class-cache class="cn.itcast.cache.Order" usage="read-write" /> 那么二级缓存中能存放Order对象
	   *   * c.getOrders()，获取订单集合
	   *   * 又配置了 <collection-cache  collection="cn.itcast.cache.Customer.orders" usage="read-write"/>集合级别的缓存
	   *   * 在集合级别的缓存中存放了Order对象的id的值:10个:id=1 ,2,3,4,5,6,7,8,9
	   *     在类级别的缓存中,存放的是订单集合中10个Order对象的散列数据
	   */
	  c.getOrders().size();
	  
	  tx.commit();
	  s.close();
	  
	  s=sf.openSession();
	  tx=s.beginTransaction();
	  
	  Customer  c2=(Customer)s.get(Customer.class, 1);
	  
	  /*
	   * 这时从集合级别的缓存中获取10个Order对象的id值,id=1 ,2,3,4,5,6,7,8,9
	   *  以id的值条件到类级别的二级缓存中查找对象,
	   *    * 如果有,直接返回对象,不再查询数据库
	   *    * 如果没有,以id的值为条件,到数据库中查询,产生10条select语句
	   *       select order0_.id as id1_0_, order0_.orderNumber as orderNum2_1_0_, order0_.price as price1_0_, 
	   *           order0_.customer_id as customer4_1_0_ from orders order0_ where order0_.id=?
	   */
	  c2.getOrders().size();
	  
	  tx.commit();
	  s.close();
    }
	
	//知识点15:测试时间戳缓存区域
	@Test
	public void testUpdateTimeStampCache(){
		  Session s=sf.openSession();
		  Transaction tx=s.beginTransaction();

		  //执行该查询时,记录查询的时间T1,该时间要记录到类级别的时间区域
		  Customer  c=(Customer)s.get(Customer.class, 1);
		  
		  //写方法。叫直接修改数据库的值 ,,记录更新的时间T2，保存更新时间戳缓存区域,不会影响到二级缓存
		  Query query=s.createQuery("update Customer c set c.age=45  where c.id=1");
		  query.executeUpdate();
		  
		  tx.commit();
		  s.close();
		  
		  
		  s=sf.openSession();
		  tx=s.beginTransaction();

		  /*
		   *要比对T1和T2的时间
		   *  * 如果 T1>T2，直接从二级缓存获取,不查询数据库
		   *  * 如果 T1<T2，直接证明数据库的数据发生了变化,这时，要查询数据库,产生select语句
		   */
		  c=(Customer)s.get(Customer.class, 1);
		  
		  tx.commit();
		  s.close();
	}
	
	/*
	 * 查询缓存中存放的是Customer对象的id直观 3个[id=1 2 3] 查询条件,真正的散列数据放置到类级别中
	 * 只对query接口有效.同集合级别的缓存一样.
	 */
	@Test
	public void testQueryCache(){
		  Session s=sf.openSession();
		  Transaction tx=s.beginTransaction();

		  Query query=s.createQuery("from Customer");
		  //设置启用查询缓存
		  query.setCacheable(true);
		  query.list().size();
		  
		  tx.commit();
		  s.close();
		  
		  s=sf.openSession();
		  tx=s.beginTransaction();

		  query=s.createQuery("from Customer");
		  query.setCacheable(true);
		  query.list().size();
		  
		  tx.commit();
		  s.close();
	}
	
	
}
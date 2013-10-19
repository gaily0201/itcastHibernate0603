package cn.itcast.search;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class AppBatch {
	
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/search/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	/*
	 * 从一的一端查询
	 * 增加 batch-size="3" 减少select语句的数目:
	 * 
	 *    select
		        orders0_.customer_id as customer4_0_1_,
		        orders0_.id as id1_,
		        orders0_.id as id1_0_,
		        orders0_.orderNumber as orderNum2_1_0_,
		        orders0_.price as price1_0_,
		        orders0_.customer_id as customer4_1_0_ 
         from
              orders orders0_ 
         where
             orders0_.customer_id in (
                    ?, ?, ?
           )

	 *   
	 * <set name="orders" table="orders" inverse="true" batch-size="3">
          <key>
            <column name="customer_id"></column>
         </key>
         <one-to-many class="cn.itcast.search.Order"/>
      </set>
	 */
	@Test
    public void batch1(){
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
	
	
	/**
	 * 从多的一端查询,在一的一端设置
	 *    <class name="cn.itcast.search.Customer" table="customers"  lazy="false" batch-size="3">
	 *   select
        customer0_.id as id0_0_,
        customer0_.name as name0_0_,
	        customer0_.age as age0_0_ 
	    from
	        customers customer0_ 
	    where
	        customer0_.id in (
	            ?, ?, ?
	        )
	 * 
	 * 
	 */
	@Test
    public void batch2(){
		Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
        Query query=s.createQuery("from Order o");
        List<Order> list=query.list();
  
        for(Order o:list){
        	System.out.println(o.getId()+"  "+o.getOrderNumber());
        }
        
    	tx.commit();
		s.close();
    }
}
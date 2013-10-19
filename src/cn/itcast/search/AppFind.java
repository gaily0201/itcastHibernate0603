package cn.itcast.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * 测试hibernate的检索方式
 * @author fengfwei
 */
public class AppFind {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/search/hibernate.cfg.xml");
		config.addClass(Customer.class);
		config.addClass(Order.class);
		sf = config.buildSessionFactory();
	}
	
	@Test  @SuppressWarnings({ "unused", "unchecked" })
    public void find(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
/*******************************************************************************************************************/
    //知识点1:  简单的查询
//      Query query=s.createQuery("from Customer c where c.name='tom'");
//	  List<Customer> list=query.list();
    	
    //使用qbc查询(标准化查询)	
	  //创建Criteria接口,执行查询操作,参数时要操作的pojo的class类型
	 // Criteria cr=s.createCriteria(Customer.class);
	  
	  //Criterion的实例,表示一个查询条件,Restrictions查询表单式,提供设置查询条件的方法
	  //Criterion cn=Restrictions.eq("name", "tom");
	  //在Criteria接口中,加入查询条件
	  //cr.add(cn);
	  
	  //cr.list();
/*******************************************************************************************************************/
//知识点4:  多态查询(是指查询出当前类及所有子类的实例)
//      Query query=s.createQuery("from Customer c");
//      query.list();
//      
//      query=s.createQuery("from java.lang.Object c");
//      query.list();
//      
//      
//      query=s.createQuery("from java.io.Serializable c");
//      query.list();

/*******************************************************************************************************************/
//知识点5:  对查询结果排序   
//        Query query=s.createQuery("from Customer c order by c.id desc");
//        query.list();
//        	
//    	
//        Criteria cr=s.createCriteria(Customer.class);
//        cr.addOrder(org.hibernate.criterion.Order.desc("id"));
//        cr.list();
/*******************************************************************************************************************/
//知识点6:  分页查询
//         Query query=s.createQuery("from Order o order by id asc");
//         query.setFirstResult(10);
//         query.setMaxResults(10);
//         query.list();
    	
//         Criteria cr=s.createCriteria(Order.class);
//         cr.addOrder(org.hibernate.criterion.Order.asc("id"));
//         cr.setFirstResult(10);
//         cr.setMaxResults(10);
//         cr.list();    	
/*******************************************************************************************************************/
//知识点7: 检索单个对象 
    	   //hql
//    	   Query query=s.createQuery("from Customer c order by c.id asc");
//           query.setMaxResults(1);
//           query.uniqueResult();  //获取唯一的结果
//           
//           //qbc
//           Criteria cr=s.createCriteria(Order.class);
//           cr.addOrder(org.hibernate.criterion.Order.asc("id"));
//           cr.setMaxResults(1);
//           cr.uniqueResult();
/*******************************************************************************************************************/
 //知识点8_1: 绑定参数的形式,按参数名称绑定
    	     // cname cage:是自己定义的名称.  
//    	     Query query=s.createQuery("from Customer c where c.name=:cname  and c.age=:cage ");
//    	     query.setString("cname", "tom");
//    	     query.setInteger("cage", 27);
//    	     query.list();
/*******************************************************************************************************************/
//知识点8_2: 绑定参数的形式,按参数位置绑定,从0开始
//    	     Query query=s.createQuery("from Customer c where c.name=? and c.age=? ");
//    	     query.setString(0, "tom");
//    	     query.setInteger(1, 27);
//    	     query.list();

/*******************************************************************************************************************/
 //知识点9: 在映射文件中定义命名查询语句
    	/*
    	 * <query name="customerByNameAndAge">
			  <![CDATA[from Customer c where c.name=? and c.age=?]]>
			</query>
    	 */
//    	     Query query=s.getNamedQuery("customerByNameAndAge");
//    	     query.setString(0, "tom");
//    	     query.setInteger(1, 27);
//    	     query.list();
    	     
/*******************************************************************************************************************/
//知识点10:    迫切左外连接left outer join fetch [返回的集合中，放置的是Customer对象,Customer对象关联到Orders集合]
//     Query query=s.createQuery("from Customer c left outer join fetch c.orders o");
//  	 List<Customer> list=query.list();  
//  	 Set<Customer> setc=new HashSet(list);
//  	 Iterator<Customer> itt=setc.iterator();
//     while(itt.hasNext()){
//  		 Customer c=itt.next();
//        
//  		 Set<Order> set=c.getOrders();
//  		 Iterator<Order> it=set.iterator();
//  		 while(it.hasNext()){
//  			 Order o=it.next();
//  			 System.out.println(c.getId()+" "+c.getName()+" "+o.getId()+"  "+o.getOrderNumber());
//  		 }
//  		 
//         System.out.println();  		 
//  	 }
/*******************************************************************************************************************/
   //知识点11: 左外连接left outer join [返回的集合中,存放的是长度为2数组,数组的第0位是Customer对象,第1位Order对象]
//     Query query=s.createQuery("from Customer c left outer join c.orders o");
//  	 List list=query.list();  
//  	 for(int i=0;i<list.size();i++){
//  		 Object[] pair=(Object[])list.get(i);
//  		 Customer c=(Customer)pair[0];
//  		 Order o=(Order)pair[1];
//  		 System.out.println(c.getId()+" "+c.getName()+" "+o.getId()+"  "+o.getOrderNumber());
//  	 }

/*******************************************************************************************************************/
  //知识点12:    内连接
//    Query query=s.createQuery("from Customer c inner join c.orders o where c.name like ?");
//    query.setString(0, "%tom%");
//    query.list();
    
    
//    Query query=s.createQuery("from Customer c inner join c.orders o where c.id=1 ");
//    query.list();
    	
    
    //Query query=s.createQuery("from Customer c inner join c.orders o where o.customer.id=1 and c.name='tom'");
    //query.list();
    	
/*******************************************************************************************************************/
 //知识点13:    迫切内连接inner join fetch
//     Query query=s.createQuery("from Customer c inner join  fetch c.orders o");
//     query.list();
    	
    	
/*******************************************************************************************************************/
//知识点15:    右连接  right outer join
//     Query query=s.createQuery("from Customer c  right outer join  c.orders o");
//     query.list();
//    	
    	
/*******************************************************************************************************************/
 //知识点17:投影查询
   	//查询客户id 客户名称   订单号,返回的list集合中存放的是对象数组,长度为3 c.id对应数组的0位 c.name对应数组的2位  o.orderNumber对应数组的2位
//      Query query=s.createQuery("select c.id,c.name,o.orderNumber from Customer c inner join  c.orders o where o.orderNumber like '%NO%'");
//      query.list();
//      
//      Query query=s.createQuery("select new cn.itcast.search.CustomerRow(c.id,c.name,o.orderNumber) from Customer c inner join  c.orders o where o.orderNumber like '%NO%'");
//      query.list();
/*******************************************************************************************************************/
   //使用聚集函数
//    	Query query=s.createQuery("select  count(c) from Customer c");
//    	Long count=(Long)query.uniqueResult();
    	
/*******************************************************************************************************************/
    //知识点18:报表查询   分组
    	Query query=s.createQuery("select c.name,count(o)  from Customer c inner join c.orders o group by c.name");
    	query.list();
/*******************************************************************************************************************/
    	
    	
/*******************************************************************************************************************/
/*******************************************************************************************************************/
/*******************************************************************************************************************/
/*******************************************************************************************************************/
/*******************************************************************************************************************/

    	
    	
    
    	
    	
    	
    	
    	
    	
    	
    	
    	tx.commit();
		s.close();
    }
	
	
}
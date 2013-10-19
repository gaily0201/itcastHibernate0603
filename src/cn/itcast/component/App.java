package cn.itcast.component;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/component/hibernate.cfg.xml");
		config.addClass(Customer.class);
		sf = config.buildSessionFactory();
	}
	
	//知识点1:测试插入
	@Test
    public void saveCustomer(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
    	Customer c=new Customer();
    	c.setName("关羽");
    	
//    	Address homeAddress=new Address("鬼街","鬼城","山西","101010");
//    	Address comAddress=new Address("三街","北京市","北京","100000");
    	
    	Address homeAddress=new Address("南京路","上海市","上海","200000");
    	Address comAddress=new Address("苏州街","北京市","北京","200000");
    	
    	c.setHomeAddress(homeAddress);
    	c.setComAddress(comAddress);
    	
    	s.save(c);
		tx.commit();
		s.close();
    }

	
	//知识点2:测试查询
	@Test
    public void queryCustomer(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
        Customer c=(Customer)s.get(Customer.class, 2);
    	if(c.getHomeAddress()!=null){
    		Address homeAddress=c.getHomeAddress();
    		System.out.println(homeAddress.getStreet()+"  "+homeAddress.getCity());
    	}
    	
		tx.commit();
		s.close();
    }
	
	//知识点3:测试更新
	@Test
    public void updateCustomer(){
    	Session s=sf.openSession();
    	Transaction tx=s.beginTransaction();
    	
        Customer c=(Customer)s.get(Customer.class, 2);
        c.setName("黄忠");
        
    	if(c.getHomeAddress()!=null){
    		Address homeAddress=c.getHomeAddress();
    		homeAddress.setStreet("川街");
    		homeAddress.setCity("成都");
    		homeAddress.setProvince("四川");
    		homeAddress.setZipcode("5000899");
    	}
    	
		tx.commit();
		s.close();
    }
	
	
	//知识点4:测试特殊的更新
	 @Test
	 public void updateOtherCustomer(){
	    	Session s=sf.openSession();
	    	Transaction tx=s.beginTransaction();
	    	
	        Customer c=(Customer)s.get(Customer.class, 2);
	        Address ha=c.getHomeAddress();
	        if(ha==null){
	        	System.out.println("ha  ==  null");
	        	ha=new Address("四街","上地","北京","000000");
	        	c.setHomeAddress(ha);
	        }

	        tx.commit();
			s.close();
	    }
}
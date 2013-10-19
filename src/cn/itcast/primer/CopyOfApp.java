package cn.itcast.primer;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CopyOfApp {

	public static void main(String[] args) {

		/*
		 * 加载hibernate.cfg.xml映射文件
		 * config:保存了连接数据库的信息和映射文件的配置信息
		 */
		Configuration config=new Configuration();
		//默认加载类路径下的hibernate.cfg.xml
		config.configure();
		
		
		 /*
		  * 获取sessionFactory
		  * * 利用config保存的信息创建SessionFactory,
		  * * SessionFactory 保存了连接数据库的信息和映射文件的配置信息,预定义的sql语句
		  *   SessionFactory是线程安全的,最好有一个sessionFactory
		  */
		SessionFactory sf=config.buildSessionFactory();
		
		//获取session
		Session s=sf.openSession();
		
		//开始事务
		Transaction tx=s.beginTransaction();

		//实例Customer对象
		Customer c=new Customer();
		c.setName("金晶");
		c.setAge(56);
		c.setDes("幻");
		
		//保存
		s.save(c);
		
		//提交
		tx.commit();
		
		//关闭session
		s.close();
		
		
	}

}

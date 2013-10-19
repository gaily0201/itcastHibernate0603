package cn.itcast.many2many;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;

public class App {
	static SessionFactory sf = null;
	static {
		Configuration config = new Configuration();
		config.configure("cn/itcast/many2many/hibernate.cfg.xml");
		config.addClass(Student.class);
		config.addClass(Course.class);
		sf = config.buildSessionFactory();
	}
	
	
	//知识点4:测试保存
	@Test
	public void saveStudentAndCourse(){
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		Student s1=new Student("关羽");
		Student s2=new Student("张飞");
		
		Course c1=new Course("语文");
		Course c2=new Course("数学");

		s1.getCourses().add(c1);
		s1.getCourses().add(c2);
		s2.getCourses().add(c1);
		s2.getCourses().add(c2);
		
		c1.getStudents().add(s1);
		c1.getStudents().add(s2);
		c2.getStudents().add(s1);
		c2.getStudents().add(s2);
		

		s.save(c1);
		s.save(c2);
		s.save(s1);
		s.save(s2);
		
		
		tx.commit();
		s.close();
	}
	
	
	//知识点5:解除1号学生和1号课程的关联关系
	@Test
	public void removeMany2Many(){
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		//查询1号学生
		 Student s1=(Student)s.get(Student.class, 1);
			
		//查询1好课程
	     Course c1=(Course)s.get(Course.class, 1);
	     
	    //在学生的课程集合中删除1号课程
	     s1.getCourses().remove(c1); 
	     
	    //在课程的学生的集合中删除1号学生
	     c1.getStudents().remove(s1);
	     
		tx.commit();
		s.close();
	}
	
	
	//知识点6:改变1号学生和2号课程的关联关系,改为1号学生和1号课程
	@Test
	public void changeMany2Many(){
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		//查询1号学生
		 Student s1=(Student)s.get(Student.class, 1);
			
		//查询1好课程
	     Course c1=(Course)s.get(Course.class, 1);
	     
	     //查询2号课程
	     Course c2=(Course)s.get(Course.class, 2);
	     
	    //在学生的课程集合中删除1号课程
	     s1.getCourses().remove(c2); 
	     
	    //在课程的学生的集合中删除1号学生
	     c2.getStudents().remove(s1);
	     
	     
	     s1.getCourses().add(c1);
	     c1.getStudents().add(s1);
	     
	     
		tx.commit();
		s.close();
	}
	
	
	//知识点7:删除2号学生,中间引用不能删除
	@Test
	public void removeStudent(){
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		//查询1号学生
		 Student s2=(Student)s.get(Student.class, 2);
		 s.delete(s2);
	     
	     
		tx.commit();
		s.close();
	}
	
	
	//知识点8:删除1号课程,能删除 因为课程端是主控方,不用配置级联
	/*
	 * 主控方,只能处理到主控方的表和中间表,不能处理其他的表
	 *       级联可以处理关联的表.
	 */
	@Test
	public void removeCourse(){
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		 Course c1=(Course)s.get(Course.class, 1);
		 s.delete(c1);
	     
		tx.commit();
		s.close();
	}
	
	//知识点9:删除1号课程的同时,要把1号和2号学生删掉？
	@Test
	public void removeCourseCascade(){
		Session s=sf.openSession();
		Transaction tx=s.beginTransaction();
		
		 Course c1=(Course)s.get(Course.class, 1);
		 s.delete(c1);
	     
		tx.commit();
		s.close();
	}

	
	
}
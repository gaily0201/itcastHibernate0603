package cn.itcast.cache;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	
	private Integer id;
	private String name;
	private Integer age;
	
	//一个客户有多个订单  =new HashSet(0);[放置空指针异常,0节省资源]
	private Set<Order> orders=new HashSet<Order>(0);
	
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}

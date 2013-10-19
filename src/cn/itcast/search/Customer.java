package cn.itcast.search;

import java.util.HashSet;
import java.util.Set;

public class Customer  {
	
	private Integer id;
	private String name;
	private Integer age;
	
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

	public void setId(int id) {
		this.id = id;
	}

}

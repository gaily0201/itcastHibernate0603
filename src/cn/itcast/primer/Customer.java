package cn.itcast.primer;


@SuppressWarnings("serial")
public class Customer {
	
	/*
	 * create table customers
		(
		  id              int primary key,
		  name        varchar(12),
		  age           int,
		  des           text
		)
	 * 
	 */
	
	//OID:Object id,表示hibernate中持久化类的唯一标识
	private Integer id;
	private String name;
	private Integer age;
	private String des;
	
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
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
}

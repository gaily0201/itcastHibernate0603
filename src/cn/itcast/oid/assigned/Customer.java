package cn.itcast.oid.assigned;

@SuppressWarnings("serial")
public class Customer {

	//把name作为主键  自然主键  OID
	private String name;
	private Integer age;
	private String des;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

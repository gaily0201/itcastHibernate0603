package cn.itcast.oid.composite01;

@SuppressWarnings("serial")
public class Customer implements java.io.Serializable {

	//映射自然主键,要求这两个字段联合唯一
	private String firstName;
	private String lastName;
	
	private String des;

	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

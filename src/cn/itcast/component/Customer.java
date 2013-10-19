package cn.itcast.component;

public class Customer {
	
	private Integer id;
	private String name;
	
	//表示家庭地址
	private Address homeAddress;
	
	//表示公司地址
	private Address comAddress;
	
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
	public Address getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	public Address getComAddress() {
		return comAddress;
	}
	public void setComAddress(Address comAddress) {
		this.comAddress = comAddress;
	}
}

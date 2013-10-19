package cn.itcast.component;

/*
 * 值(value)类型: 没有 OID, 不能被单独持久化, 生命周期依赖于所属的持久化类的对象的生命周期,组件类型就是一种值类型(Address)
 * 
 */
public class Address {
	private String street;
	private String city;
	private String province;
	private String zipcode;
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address(String street, String city, String province, String zipcode) {
		this.street = street;
		this.city = city;
		this.province = province;
		this.zipcode = zipcode;
	}

	public Address() {
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}

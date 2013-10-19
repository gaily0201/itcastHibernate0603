package cn.itcast.oid.composite02;

@SuppressWarnings("serial")
public class Customer implements java.io.Serializable {

	//OID 联合的
	private CustomerID id;

	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public CustomerID getId() {
		return id;
	}

	public void setId(CustomerID id) {
		this.id = id;
	}
}

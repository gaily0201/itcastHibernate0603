package cn.itcast.search;

public class CustomerRow {
	// c.id,c.name,o.orderNumber
	private Integer id;
	private String name;
	private String orderNumber;

	public CustomerRow(Integer id, String name, String orderNumber) {
		this.id = id;
		this.name = name;
		this.orderNumber = orderNumber;
	}

	public CustomerRow() {
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}

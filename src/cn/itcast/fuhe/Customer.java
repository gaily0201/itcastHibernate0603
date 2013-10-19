package cn.itcast.fuhe;

@SuppressWarnings("serial")
public class Customer {
	private Integer id;
	
	private String firstName;
	private String lastName;
	
	
	private Integer age;
	private String des;
	
	public String getName() {
		return this.firstName+","+this.lastName;
	}
	public void setName(String name) {
        if(name!=null&&!"".equals(name.trim())){
        	String str[]=name.split(",");
            this.firstName=str[0];
            this.lastName=str[1];
        }
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

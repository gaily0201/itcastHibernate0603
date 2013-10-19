package cn.itcast.many2many;

import java.util.HashSet;
import java.util.Set;

public class Course {
	private Integer id;
	private String name;

	private Set<Student> students = new HashSet(0);

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Integer getId() {
		return id;
	}

	public Course(String name) {
		this.name = name;
	}

	public Course() {
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
}

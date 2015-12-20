package com.dy.test.basic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "t_user")
public class User {
	private int id;
	private String name;
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}

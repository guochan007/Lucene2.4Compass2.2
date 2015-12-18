package com.pojo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;
import org.hibernate.annotations.GenericGenerator;



@Entity
//@Table(name="students") 
@Searchable(alias =  "students" )
public class Students{
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	@SearchableId(name="id")
	private int id;
	
	@Column(name = "username", length = 40)
	@SearchableProperty(name="username",index = Index.ANALYZED,store = Store.YES)
	private String username;
	
	@Column(name = "address", length = 40)
	@SearchableProperty(name="address",index = Index.ANALYZED,store = Store.YES)
	private String address;
	
	public Students() {
	}
	
	public Students(String username, String address) {
		super();
		
		this.username = username;
		this.address = address;
	}
	
	public Students(int id, String username, String address) {
		super();
		this.id = id;
		this.username = username;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Students [id=" + id + ", username=" + username + ", address=" + address + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
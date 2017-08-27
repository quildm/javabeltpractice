package com.codingdojo.belta.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@Table(name="packages")
public class PackageModel {
	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 @Size(min=3, message="name must be greater than 3 characters")
	 private String name;
	 
	 @Min(1)
	 private String cost;
	 
	 private Boolean available;
	 
	 private Date createdAt;
	 private Date updatedAt;
	 
	 @OneToMany(mappedBy="choosed_package", fetch = FetchType.LAZY)
	 private List<User> users;
	 
	public PackageModel(){
		
	}
		public PackageModel(String name, String cost){
		this.name = name;
		this.cost = cost;
		this.available = false;
		 this.createdAt = new Date();
		 this.updatedAt = new Date();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	
}

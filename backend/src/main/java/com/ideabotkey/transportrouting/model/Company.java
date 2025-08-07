package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "companies")
public class Company {

	
	   public Company(String name, String address, String contactPerson, String contactEmail, String contactPhone, String status) {
	        this.name = name;
	        this.address = address;
	        this.contactPerson = contactPerson;
	        this.contactEmail = contactEmail;
	        this.contactPhone = contactPhone;
	        this.status = status;
	    }
    
	public Company() {
		super();
	}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String address;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @ManyToMany(mappedBy = "companies")
    @JsonBackReference
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

}

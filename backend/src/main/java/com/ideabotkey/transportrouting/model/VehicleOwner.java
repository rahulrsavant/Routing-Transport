package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "vehicle_owners")
public class VehicleOwner {

	
	public VehicleOwner(String name, String contactEmail, String contactPhone, String address, String status) {
		super();
//		this.id = id;
		this.name = name;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
		this.address = address;
		this.status = status;
	}
	
	public VehicleOwner() {
		// TODO Auto-generated constructor stub
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

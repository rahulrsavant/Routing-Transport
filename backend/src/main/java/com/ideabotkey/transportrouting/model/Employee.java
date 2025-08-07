package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "company_id")
        @ToString.Exclude
        private Company company;

	private String name;
	private String email;
	private String phone;
	private String address;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Employee(Long id, String name, String email, String phone, String address, String status,
			LocalDateTime createdAt, LocalDateTime updatedAt, Company company) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.company = company;
	}

}

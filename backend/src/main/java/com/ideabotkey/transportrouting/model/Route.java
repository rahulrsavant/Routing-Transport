package com.ideabotkey.transportrouting.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import com.converter.StopsConverter;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "routes")
public class Route {

	public Route(Long id, Contract contract, String name, String startPoint, String endPoint, List<String> stops,
			String estimatedTime, String status) {
		this.id = id;
		this.contract = contract;
		this.name = name;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.stops = stops;
		this.estimatedTime = estimatedTime;
		this.status = status;
	}

	public Route(String name, String startPoint, String endPoint, List<String> stops, String estimatedTime,
			Contract contract, String status) {
		this.name = name;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.stops = stops;
		this.estimatedTime = estimatedTime;
		this.contract = contract;
		this.status = status;
	}

	public Route() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contract_id")
	@ToString.Exclude
	private Contract contract;

	private String name;
	private String startPoint;
	private String endPoint;

	@Convert(converter = StopsConverter.class)
	@Lob
	private List<String> stops;

	private String estimatedTime;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

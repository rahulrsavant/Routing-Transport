package com.ideabotkey.transportrouting.dto;

/**
 * DTO representing a booked seat with the employee who booked it.
 */
public class BookingSeatEmployeeDto {
    private String seatNumber;
    private Long employeeId;
    private String employeeName;

    public BookingSeatEmployeeDto(String seatNumber, Long employeeId, String employeeName) {
        this.seatNumber = seatNumber;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public BookingSeatEmployeeDto() {
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}

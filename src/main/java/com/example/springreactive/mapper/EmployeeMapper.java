package com.example.springreactive.mapper;

import com.example.springreactive.dto.EmployeeDto;
import com.example.springreactive.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto mapToEmployeeDto(Employee entity){
        return new EmployeeDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getSecondName(),
                entity.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto dto){
        return new Employee(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getSecondName(),
                dto.getEmail()
        );
    }
}

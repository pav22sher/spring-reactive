package com.example.springreactive.service;

import com.example.springreactive.dto.EmployeeDto;
import com.example.springreactive.entity.Employee;
import com.example.springreactive.mapper.EmployeeMapper;
import com.example.springreactive.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> getById(String id) {
        Mono<Employee> employeeMono = employeeRepository.findById(id);
        return employeeMono.map((employee -> EmployeeMapper.mapToEmployeeDto(employee)));
    }

    @Override
    public Flux<EmployeeDto> getAll() {
        Flux<Employee> employeeFlux = employeeRepository.findAll();
        return employeeFlux
                .map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> save(EmployeeDto dto) {
        Employee employee = EmployeeMapper.mapToEmployee(dto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee.map((employeeEntity) -> EmployeeMapper.mapToEmployeeDto(employeeEntity));
    }


    @Override
    public Mono<EmployeeDto> update(EmployeeDto dto) {
        Mono<Employee> employeeMono = employeeRepository.findById(dto.getId());
        return employeeMono.flatMap((existingEmployee) -> {
            existingEmployee.setFirstName(dto.getFirstName());
            existingEmployee.setLastName(dto.getLastName());
            existingEmployee.setEmail(dto.getEmail());
            return employeeRepository.save(existingEmployee);
        }).map((employee -> EmployeeMapper.mapToEmployeeDto(employee)));
    }

    @Override
    public Mono<Void> delete(String id) {
        return employeeRepository.deleteById(id);
    }
}

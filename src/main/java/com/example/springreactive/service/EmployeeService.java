package com.example.springreactive.service;

import com.example.springreactive.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> getById(String id);
    Flux<EmployeeDto> getAll();
    Mono<EmployeeDto> save(EmployeeDto dto);
    Mono<EmployeeDto> update(EmployeeDto dto);
    Mono<Void> delete(String id);
}

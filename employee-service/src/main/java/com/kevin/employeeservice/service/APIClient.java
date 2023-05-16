package com.kevin.employeeservice.service;

import com.kevin.employeeservice.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// !!! Note: openFeign library will dynamically create impl for the interface
//@FeignClient(url = "http://localhost:8080", value="DEPARTMENT-SERVICE")

// Eureka client will use load balancer to call the service name registered in the eureka server
@FeignClient(name="DEPARTMENT-SERVICE")
public interface APIClient {

    // Build get department rest api
    // this is the method signature copied from Department service controller
    // the only difference is return is return type is DTO object instead of ResponseEntity<DepartmentDTO>
    @GetMapping("api/departments/{code}")
    DepartmentDTO getDepartment(@PathVariable("code") String code);
}

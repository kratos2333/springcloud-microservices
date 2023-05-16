package com.kevin.employeeservice.service;

import com.kevin.employeeservice.dto.APIResponseDTO;
import com.kevin.employeeservice.dto.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    APIResponseDTO getEmployeeById(Long employeeId);

}

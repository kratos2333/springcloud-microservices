package com.kevin.employeeservice.service.impl;

import com.kevin.employeeservice.dto.APIResponseDTO;
import com.kevin.employeeservice.dto.DepartmentDTO;
import com.kevin.employeeservice.dto.EmployeeDTO;
import com.kevin.employeeservice.entity.Employee;
import com.kevin.employeeservice.repository.EmployeeRepo;
import com.kevin.employeeservice.service.APIClient;
import com.kevin.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.logging.Logger;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static Logger LOGGER = Logger.getLogger("EmployeeServiceImpl");

    private final EmployeeRepo employeeRepo;

    private final ModelMapper modelMapper;

//    private final RestTemplate restTemplate;

//    private final WebClient webClient;

    private APIClient apiClient;

//    @Autowired
//    public EmployeeServiceImpl(EmployeeRepo employeeRepo, ModelMapper modelMapper, RestTemplate restTemplate) {
//        this.employeeRepo = employeeRepo;
//        this.modelMapper = modelMapper;
//        this.restTemplate = restTemplate;
//    }

//    @Autowired
//    public EmployeeServiceImpl(EmployeeRepo employeeRepo, ModelMapper modelMapper, WebClient webClient) {
//        this.employeeRepo = employeeRepo;
//        this.modelMapper = modelMapper;
//        this.webClient = webClient;
//    }

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo, ModelMapper modelMapper, APIClient apiClient) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
        this.apiClient = apiClient;
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEmployee = employeeRepo.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
//    @CircuitBreaker(name="${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name="${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public APIResponseDTO getEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById()  method");
        Employee employee = employeeRepo.findById(employeeId).get();

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        // Method 1: RestClient
//        ResponseEntity<DepartmentDTO> responeDepartment =
//                restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
//                        DepartmentDTO.class);

//        DepartmentDTO departmentDTO = responeDepartment.getBody();

        // Method 2: WebClient
//        DepartmentDTO departmentDTO = webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDTO.class)
//                .block();
        // Method 3: openfeign
        DepartmentDTO departmentDTO = apiClient.getDepartment(employee.getDepartmentCode());

        return new APIResponseDTO(employeeDTO,departmentDTO);
    }

    // fallback method
    public APIResponseDTO getDefaultDepartment(Long employeeId, Exception exception) {
        LOGGER.info("inside getDefaultDepartment()  method");
        Employee employee = employeeRepo.findById(employeeId).get();

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setName("R&D Department");
        departmentDTO.setCode("RD001");
        departmentDTO.setDescription("Research and Development Department");



        return new APIResponseDTO(employeeDTO,departmentDTO);
    }
}

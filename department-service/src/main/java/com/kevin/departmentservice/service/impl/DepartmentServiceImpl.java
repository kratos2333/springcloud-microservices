package com.kevin.departmentservice.service.impl;

import com.kevin.departmentservice.dto.DepartmentDTO;
import com.kevin.departmentservice.entity.Department;
import com.kevin.departmentservice.repository.DepartmentRepo;
import com.kevin.departmentservice.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepo departmentRepo;

    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepo departmentRepo, ModelMapper modelMapper) {
        this.departmentRepo = departmentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {

        // convert dto to entity
        Department department = modelMapper.map(departmentDTO, Department.class);
        Department savedDepartment = departmentRepo.save(department);
        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO getDepartmentByCode(String code) {
        Department department = departmentRepo.findByCode(code);
        return modelMapper.map(department, DepartmentDTO.class);
    }
}

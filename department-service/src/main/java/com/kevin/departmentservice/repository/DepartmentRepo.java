package com.kevin.departmentservice.repository;

import com.kevin.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Department findByCode(String code);
}

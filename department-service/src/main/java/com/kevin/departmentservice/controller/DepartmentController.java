package com.kevin.departmentservice.controller;

import com.kevin.departmentservice.dto.DepartmentDTO;
import com.kevin.departmentservice.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Kevin's Department Controller",
        description = "Department Controller Exposes REST APIs for Department-Service"
)
@RestController
@RequestMapping("api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @Operation(
            summary = "Save Department REST API",
            description = "Save Department API is used to save department"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    // build save department rest API
    @PostMapping
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentDTO departmentDTO){
        DepartmentDTO savedDTO = departmentService.saveDepartment(departmentDTO);
        return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Department REST API",
            description = "Get Department API is used to get department object"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Success"
    )
    @GetMapping("{code}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("code") String code){
        DepartmentDTO fetchedDTO = departmentService.getDepartmentByCode(code);
        return new ResponseEntity<>(fetchedDTO, HttpStatus.OK);
    }
}

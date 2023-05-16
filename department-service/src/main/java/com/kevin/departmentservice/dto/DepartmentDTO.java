package com.kevin.departmentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "DepartmentDTO Model Information"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long id;

    @Schema(
            description = "Department Name"
    )
    private String name;
    @Schema(
            description = "Department Description"
    )
    private String description;
    @Schema(
            description = "Department Code"
    )
    private String code;
}

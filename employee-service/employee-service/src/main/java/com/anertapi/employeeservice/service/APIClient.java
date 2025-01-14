package com.anertapi.employeeservice.service;

import com.anertapi.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "http://localhost:8080",value = "DEPARTMENT-SERVICE")
@FeignClient(name = "DEPARTMENT-SERVICE")                    //for load balancing due to multiple instances
public interface APIClient {
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartment(@PathVariable("department-code") String departmentCode);
    }

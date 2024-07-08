package com.anertapi.employeeservice.service;

import com.anertapi.employeeservice.dto.APIResponseDto;
import com.anertapi.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto savedEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long employeeId);//added //updated
}

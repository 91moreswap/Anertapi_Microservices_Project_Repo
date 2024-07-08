package com.anertapi.employeeservice.controller;

import com.anertapi.employeeservice.dto.APIResponseDto;
import com.anertapi.employeeservice.dto.EmployeeDto;
import com.anertapi.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    //Build save employee POST REST API
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
       EmployeeDto savedEmployee = employeeService.savedEmployee(employeeDto);
       return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Build get employee GET REST API
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getEmployeeById(@PathVariable("id") Long employeeId){
       APIResponseDto apiResponseDto = employeeService.getEmployeeById(employeeId);
       return new ResponseEntity<>(apiResponseDto,HttpStatus.OK);
    }


}

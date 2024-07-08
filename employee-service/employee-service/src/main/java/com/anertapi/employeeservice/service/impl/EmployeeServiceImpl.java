package com.anertapi.employeeservice.service.impl;

import com.anertapi.employeeservice.dto.APIResponseDto;
import com.anertapi.employeeservice.dto.DepartmentDto;
import com.anertapi.employeeservice.dto.EmployeeDto;
import com.anertapi.employeeservice.dto.OrganizationDto;
import com.anertapi.employeeservice.entity.Employee;
import com.anertapi.employeeservice.mapper.AutoEmployeeMapper;
import com.anertapi.employeeservice.repository.EmployeeRepository;
import com.anertapi.employeeservice.service.APIClient;
import com.anertapi.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;

  //  private RestTemplate restTemplate;              //updated for RestTemplate

    private WebClient webClient;                    //updated for WebClient

  //  private APIClient apiClient;
    @Override
    public EmployeeDto savedEmployee(EmployeeDto employeeDto) {
        //convert employee dto into employee jpa entity
        Employee employee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getDepartmentCode(),   //updated
                employeeDto.getOrganizationCode()
        );
     //   Employee employee = AutoEmployeeMapper.MAPPER.mapToDepartment(employeeDto);
       Employee savedEmployee = employeeRepository.save(employee);

        //convert savedEmployee jpa entity into savedEmployeeDto

       EmployeeDto savedEmployeeDto = new EmployeeDto(
               savedEmployee.getId(),
               savedEmployee.getFirstName(),
               savedEmployee.getLastName(),
               savedEmployee.getEmail(),
               savedEmployee.getDepartmentCode(),       //updated
               savedEmployee.getOrganizationCode()
       );
    //    EmployeeDto savedEmployeeDto = AutoEmployeeMapper.MAPPER.mapToDepartmentDto(savedEmployee);

        return savedEmployeeDto;
    }
   // @CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {                //updated

        LOGGER.info("inside getEmployeeById() method");

       Employee employee = employeeRepository.findById(employeeId).get();

//       //updated for RestTemplate
//      ResponseEntity<DepartmentDto> responseEntity = restTemplate
//              .getForEntity("http://localhost:8080/api/departments/"
//                      +employee.getDepartmentCode(), DepartmentDto.class);
//
//      DepartmentDto departmentDto = responseEntity.getBody();   //updated


        //Updated for WebClient
        //make call from employee service to department service
      DepartmentDto departmentDto =  webClient.get()
                .uri("http://localhost:8080/api/departments/" +employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();


        //make call from employee service to organization service
        OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8083/api/organizations/" + employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();


        //Updated for FeignClient
    //   DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

       EmployeeDto employeeDto = new EmployeeDto(
               employee.getId(),
               employee.getFirstName(),
               employee.getLastName(),
               employee.getEmail(),
               employee.getDepartmentCode(),     //updated
               employee.getOrganizationCode()
       );

       // EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToDepartmentDto(employee);
        //updated APIResponseDto
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);

       return apiResponseDto;
       // return AutoEmployeeMapper.MAPPER.mapToDepartmentDto(employee);
    }
    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {

        LOGGER.info("inside getDefaultDepartment() method");

        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD01");
        departmentDto.setDepartmentDescription("Reasearch and Development Department");

        EmployeeDto employeeDto = new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode(),    //updated
                employee.getOrganizationCode()
        );

    //    EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToDepartmentDto(employee);
        //updated APIResponseDto
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);

        return apiResponseDto;
        // return AutoEmployeeMapper.MAPPER.mapToDepartmentDto(employee);
    }
    }

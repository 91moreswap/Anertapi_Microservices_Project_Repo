package com.anertapi.employeeservice.mapper;

import com.anertapi.employeeservice.dto.EmployeeDto;
import com.anertapi.employeeservice.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoEmployeeMapper {

    AutoEmployeeMapper MAPPER = Mappers.getMapper(AutoEmployeeMapper.class);

    EmployeeDto mapToDepartmentDto(Employee department);
    Employee mapToDepartment(EmployeeDto departmentDto);

    EmployeeDto mapToOrganizationDto(Employee organization);
    Employee mapToOrganization(EmployeeDto organizationDto);
}

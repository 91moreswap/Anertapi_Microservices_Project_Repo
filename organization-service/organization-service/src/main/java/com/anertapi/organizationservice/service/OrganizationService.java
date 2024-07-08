package com.anertapi.organizationservice.service;

import com.anertapi.organizationservice.dto.OrganizationDto;
import com.anertapi.organizationservice.entity.Organization;

public interface OrganizationService {
    OrganizationDto saveOrganization(OrganizationDto organizationDto);
    OrganizationDto getOrganizationByCode(String organizationCode);

}

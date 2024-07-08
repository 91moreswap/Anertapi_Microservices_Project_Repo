package com.anertapi.organizationservice.service.impl;

import com.anertapi.organizationservice.dto.OrganizationDto;
import com.anertapi.organizationservice.entity.Organization;
import com.anertapi.organizationservice.mapper.OrganizationMapper;
import com.anertapi.organizationservice.repository.OrganizationRepository;
import com.anertapi.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;
    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        //convert organizationdto into organization jpa entity
        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);

       Organization savedOrganization = organizationRepository.save(organization);

        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
       Organization organization = organizationRepository.findByOrganizationCode(organizationCode);

        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}

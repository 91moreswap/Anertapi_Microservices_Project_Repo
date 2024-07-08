package com.anertapi.organizationservice.controller;

import com.anertapi.organizationservice.dto.OrganizationDto;
import com.anertapi.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService organizationService;

    //Build save organization REST API
    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto){
       OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);

       return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    }

    //Build get organization by code REST API
@GetMapping("{code}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable("code") String organizationCode){
      OrganizationDto organizationDto =  organizationService.getOrganizationByCode(organizationCode);

      return ResponseEntity.ok(organizationDto);
    }
}

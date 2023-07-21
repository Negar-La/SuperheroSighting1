package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Organization;

import java.util.List;

public interface OrganizationService {

    public Organization addOrganization(Organization organization);
    public List<Organization> getAllOrganizations();
    public Organization getOrganizationByID(int id);
    public void updateOrganization(Organization organization);
    public void deleteOrganizationByID(int id);
}

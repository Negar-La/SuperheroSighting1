package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.LocationDao;
import com.we.SuperHeroSightings.dao.OrganizationDao;
import com.we.SuperHeroSightings.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrganizationServiceImpl implements OrganizationService{

    @Autowired
    private OrganizationDao organizationDao;
    @Override
    public Organization addOrganization(Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public Organization getOrganizationByID(int id) {
        return organizationDao.getOrganizationByID(id);
    }

    @Override
    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }

    @Override
    public void deleteOrganizationByID(int id) {
        organizationDao.deleteOrganizationByID(id);
    }
}

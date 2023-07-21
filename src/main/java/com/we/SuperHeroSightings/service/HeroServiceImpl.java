package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.HeroDao;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HeroServiceImpl implements HeroService {

    @Autowired
    private HeroDao heroDao;


    @Override
    public Hero getHeroByID(int id) {

        return heroDao.getHeroByID(id);
    }

    @Override
    public List<Hero> getAllHeros() {

        return heroDao.getAllHeros();
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {

        return heroDao.addHero(hero);
    }

    @Override
    public void updateHero(Hero hero) {

        heroDao.updateHero(hero);
    }

    @Override
    public void deleteHeroByID(int id) {

        heroDao.deleteHeroByID(id);
    }

    @Override
    public List<Hero> getHeroesByLocation(Location location) {

        return heroDao.getHerosByLocation(location);
    }

    @Override
    public List<Hero> getHeroesByOrganization(Organization organization) {
        return heroDao.getHerosByOrganization(organization);
    }
}


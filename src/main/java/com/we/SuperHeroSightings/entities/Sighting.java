
package com.we.SuperHeroSightings.entities;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author jtriolo
 */
public class Sighting {

    private int id;
    @NotBlank(message = "Description cannot be empty.")
    @Size(max = 255, message = "Description must be fewer than 255 characters")
    private String description;
    @NotNull(message = "Invalid date.")
    private LocalDateTime date;
    @NotNull(message = "Invalid hero.")
    private Hero hero;
    @NotNull(message = "Invalid location.")
    private Location location;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.date);
        hash = 13 * hash + Objects.hashCode(this.hero);
        hash = 13 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.hero, other.hero)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }


    
    
    
}

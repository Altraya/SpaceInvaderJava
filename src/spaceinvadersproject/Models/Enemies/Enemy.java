/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models.Enemies;

import spaceinvadersproject.Models.Aircrafts.Aircraft;
import spaceinvadersproject.Models.Entity;

/**
 * Represent an abstract enemy
 * @author Karakayn
 */
public abstract class Enemy extends Entity{
    
    //Name of enemy
    protected String name;
    //Affixe like << Super boss of the 2nd level >>
    protected String affixe;
    protected Aircraft aircraft;

    public Enemy(String ref, int x, int y) {
        super(ref, x, y);
    }

    //Type of enemy Boss / Creep / or other specific type
    public enum EType{ 
        Creep,
        Boss
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the affixe
     */
    public String getAffixe() {
        return affixe;
    }

    /**
     * @param affixe the affixe to set
     */
    public void setAffixe(String affixe) {
        this.affixe = affixe;
    }

    /**
     * @return the aircraft
     */
    public Aircraft getAircraft() {
        return aircraft;
    }

    /**
     * @param aircraft the aircraft to set
     */
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
            
}

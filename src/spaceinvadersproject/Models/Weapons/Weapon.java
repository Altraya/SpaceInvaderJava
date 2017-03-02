/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models.Weapons;

import java.util.Random;

/**
 * Represent a weapon, used by player or enemies
 * @author Karakayn
 */
public class Weapon {

    public enum EType{
        Small,
        Medium,
        Large
    }
    
    private String _name;
    private int _minDamage;
    private int _maxDamage;
    //time we need to wait until the weapon be ready
    private float _chargingTime;
    //number of time we can use our weapon without have any time to wait for charging
    private float _chargingCounter;
    private EType _type;
    
    public Weapon(String name, int degatMin, int degatMax, EType type, float tpsRecharge)
    {
        this._name = name;
        this._minDamage = degatMin;
        this._maxDamage = degatMax;
        this._type = type;
        this._chargingTime = tpsRecharge;
        this._chargingCounter = tpsRecharge;
    }
    
    @Override
    public String toString()
    {
        String str = "";
        str += "Nom : "+this._name+System.lineSeparator();
        str += "Degats : "+this._minDamage+" -> "+this._maxDamage+System.lineSeparator();
        str += "Degat moyen : "+this.getDegatsMoyen()+System.lineSeparator();
        str += "Temps de recharge : "+this._chargingTime+System.lineSeparator();
        str += "Type : "+this._type+System.lineSeparator();        
        return str;
    }
    
    public float getDegatsMoyen()
    {
        return (_minDamage+_maxDamage)/2;
    }
}
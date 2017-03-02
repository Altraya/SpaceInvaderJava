/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models.Armory;

import java.util.ArrayList;
import spaceinvadersproject.Models.Weapons.Weapon;

/**
 * Armory who store weapons
 * @author Karakayn
 */
public class Armory {

    private ArrayList<Weapon> _weaponList = new ArrayList<>();

    private static Armory INSTANCE = new Armory();

    /** Point d'accès pour l'instance unique du singleton */
    public static Armory getInstance()
    {	return INSTANCE;
    }
    
    private Armory()
    {
        this.setWeaponList(this.init()); 
    }

    /**
     * Initialise l'armurerie
     * @return une liste d'weapon de différents types
     */
    private ArrayList<Weapon> init()
    {
        ArrayList<Weapon> v = new ArrayList<>();
        /*
        Weapon trotinette = new Weapon("Trotinette", 2000, 4000, Weapon.EType.Explosif, 0);
        Weapon dague = new Weapon("Dague", 1, 10, Weapon.EType.Direct, 0);
        Weapon missile = new Weapon("Missile", 800000, 10000000, Weapon.EType.Guide, 0); 
        v.add(trotinette);
        v.add(dague);
        v.add(missile);*/
        return v;
    }
    
    public void addWeapon(Weapon weapon)
    {
        this._weaponList.add(weapon);
    }
    
    public void removeWeapon(Weapon weapon)
    {
        this._weaponList.remove(weapon);
    }
    
    public void displayArmory()
    {
        for (Weapon currentWeapon : this._weaponList) {
            System.out.println("Weapon : ");
            System.out.println(currentWeapon.toString());
        }
    }
    
    /**
     * @return the weaponList
     */
    public ArrayList<Weapon> getWeaponList() {
        return _weaponList;
    }

    /**
     * @param weaponList the weaponList to set
     */
    public void setWeaponList(ArrayList<Weapon> weaponList) {
        this._weaponList = weaponList;
    }
    
    /**
     * Check if the weapon in parameter exist in the armory
     * @param weapon : weapon to verify
     * @throws spaceinvadersproject.Models.Armory.ArmoryException
     */
    public void checkIfWeaponExist(Weapon weapon) throws ArmoryException
    {
        //pas besoin de return true ou false pour savoir si on trouve ou pas l'weapon vu qu'on leve une exception
        if(!this._weaponList.contains(weapon))
            throw new ArmoryException();
        
    }
}

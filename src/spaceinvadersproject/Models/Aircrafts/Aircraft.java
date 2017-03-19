/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models.Aircrafts;

import java.util.ArrayList;
import spaceinvadersproject.Models.Weapons.Weapon;
import spaceinvadersproject.Game.ECote;

/**
 * Represent an abstrat aircraft
 * @author Karakayn
 */
public abstract class Aircraft {
    protected int _maxStructPoint;
    protected int _maxShieldPoint;
    protected int _currentStructPoint; 
    protected int _currentShieldPoint;
    protected boolean _destroyed;
    protected ArrayList<Weapon> _listEquipedWeapons = new ArrayList<>();
    
    protected int _x;
    protected int _y;
    /** The speed at which the player's ship should move (pixels/sec) */
    private double moveSpeed;
        
    public Aircraft()
    {
        this._maxStructPoint = 0;
        this._maxShieldPoint = 0;
        this._currentShieldPoint = _maxShieldPoint;
        this._currentStructPoint = _maxStructPoint;
        moveSpeed = 300;
        this._destroyed = false;
    }
    public Aircraft(int ptsStructMax, int ptsBouclier)
    {
        this._maxStructPoint = ptsStructMax;
        this._maxShieldPoint = ptsBouclier;
        this._currentShieldPoint = _maxShieldPoint;
        this._currentStructPoint = _maxStructPoint;
        this._destroyed = false;
    }
    
    public void move(ECote cote, int vitesse) {
        switch(cote){
            case LEFT:
                this._x -= 20*vitesse;
                break;
            case RIGHT:
                this._x+= 20*vitesse;
                break;
        }
    }

    /**
     * @return the _maxStructPoint
     */
    public int getMaxStructPoint() {
        return _maxStructPoint;
    }

    /**
     * @param _maxStructPoint the _maxStructPoint to set
     */
    public void setMaxStructPoint(int _maxStructPoint) {
        this._maxStructPoint = _maxStructPoint;
    }

    /**
     * @return the _maxShieldPoint
     */
    public int getMaxShieldPoint() {
        return _maxShieldPoint;
    }

    /**
     * @param _maxShieldPoint the _maxShieldPoint to set
     */
    public void setMaxShieldPoint(int _maxShieldPoint) {
        this._maxShieldPoint = _maxShieldPoint;
    }

    /**
     * @return the _currentStructPoint
     */
    public int getCurrentStructPoint() {
        return _currentStructPoint;
    }

    /**
     * @param _currentStructPoint the _currentStructPoint to set
     */
    public void setCurrentStructPoint(int _currentStructPoint) {
        this._currentStructPoint = _currentStructPoint;
    }

    /**
     * @return the _currentShieldPoint
     */
    public int getCurrentShieldPoint() {
        return _currentShieldPoint;
    }

    /**
     * @param _currentShieldPoint the _currentShieldPoint to set
     */
    public void setCurrentShieldPoint(int _currentShieldPoint) {
        this._currentShieldPoint = _currentShieldPoint;
    }

    /**
     * @return the _destroyed
     */
    public boolean isDestroyed() {
        return _destroyed;
    }

    /**
     * @param _destroyed the _destroyed to set
     */
    public void setDestroyed(boolean _destroyed) {
        this._destroyed = _destroyed;
    }

    /**
     * @return the _listEquipedWeapons
     */
    public ArrayList<Weapon> getListEquipedWeapons() {
        return _listEquipedWeapons;
    }

    /**
     * @param _listEquipedWeapons the _listEquipedWeapons to set
     */
    public void setListEquipedWeapons(ArrayList<Weapon> _listEquipedWeapons) {
        this._listEquipedWeapons = _listEquipedWeapons;
    }


    /**
     * @return the _x
     */
    public int getX() {
        return _x;
    }

    /**
     * @param x the _x to set
     */
    public void setX(int x) {
        this._x = x;
    }

    /**
     * @return the _y
     */
    public int getY() {
        return _y;
    }

    /**
     * @param y the _y to set
     */
    public void setY(int y) {
        this._y = y;
    }

    /**
     * @return the moveSpeed
     */
    public double getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * @param moveSpeed the moveSpeed to set
     */
    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}

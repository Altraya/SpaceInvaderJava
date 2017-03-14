/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models.Player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import spaceinvadersproject.Models.Aircrafts.Aircraft;
import spaceinvadersproject.Models.Aircrafts.OldAircraft;
import spaceinvadersproject.Models.Enemies.Enemy;
import spaceinvadersproject.Models.Entity;
import spaceinvadersproject.SpaceInvaderGame;

/**
 * Represent the player
 * @author Karakayn
 */
public class Player extends Entity{
    private String name;
    private String firstname;
    private String nickname;
    
    protected Aircraft _aircraft;
        
    public Player(String picturePath,int x,int y, String name, String firstname, String nickname)
    {   
        super(picturePath,x,y);
        Hashtable<String, String> format = new Hashtable<String, String>();
        format = this.formateFirstnameAndName(name, firstname, nickname);
        this.name = format.get("name");
        this.firstname = format.get("firstname");
        this.nickname = format.get("nickname");
        this._aircraft = new OldAircraft(10, 15);

    }
    
    private static Hashtable<String, String> formateFirstnameAndName(String name, String firstname, String nickname)
    {
        String s1 = name.substring(0, 1).toUpperCase();
        String nameCapitalized = s1 + name.substring(1).toLowerCase();
        
        String s2 = firstname.substring(0, 1).toUpperCase();
        String firstnameCapitalized = s2 + firstname.substring(1).toLowerCase();
        
        String s3 = nickname.substring(0, 1).toUpperCase();
        String nicknameCapitalized = s3 + nickname.substring(1).toLowerCase();
        
        Hashtable<String, String> format = new Hashtable<String, String>();
        format.put("name", nameCapitalized);
        format.put("firstname", firstnameCapitalized);
        format.put("nickname", nicknameCapitalized);
        return format;
    }

    /**
     * @return the _aircraft
     */
    public Aircraft getAircraft() {
        return _aircraft;
    }
    
    /**
     * Request that the ship move itself based on an elapsed ammount of
     * time
     * 
     * @param delta The time that has elapsed since last move (ms)
     */
    public void move(long delta) {
        // if we're moving left and have reached the left hand side
        // of the screen, don't move
        if ((dx < 0) && (x < 10)) {
                return;
        }
        // if we're moving right and have reached the right hand side
        // of the screen, don't move
        if ((dx > 0) && (x > 750)) {
                return;
        }

        super.move(delta);
    }

    /**
     * Notification that the player's ship has collided with something
     * 
     * @param other The entity with which the ship has collided
     */
    public void collidedWith(Entity other) {
        // if its an alien, notify the game that the player
        // is dead
        if (other instanceof Enemy) {
            SpaceInvaderGame.getInstance().notifyDeath();
        }
    }

}

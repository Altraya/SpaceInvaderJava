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

/**
 * Represent the player
 * @author Karakayn
 */
public class Player {
    private String name;
    private String firstname;
    private String nickname;
    
    protected Aircraft _aircraft;
        
    public Player(String name, String firstname, String nickname, String picturePath)
    {   
        Hashtable<String, String> format = new Hashtable<String, String>();
        format = this.formateFirstnameAndName(name, firstname, nickname);
        this.name = format.get("name");
        this.firstname = format.get("firstname");
        this.nickname = format.get("nickname");
        this._aircraft = new OldAircraft(10, 15);

        BufferedImage picture;
        try {
            picture = ImageIO.read(new File(picturePath));
            this._aircraft.setPicture(picture);

        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}

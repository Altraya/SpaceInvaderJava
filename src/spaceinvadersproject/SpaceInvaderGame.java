/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import spaceinvadersproject.Helpers.IniFile;
import spaceinvadersproject.Models.Enemies.Enemy;
import spaceinvadersproject.Models.Player.Player;

/**
 * Represent the game entity [Singleton]
 * @author Karakayn
 */
public class SpaceInvaderGame {

    public enum ECote {
        LEFT,
        RIGHT
    }
    
    enum ELevel {
        SPACE_INVADER_LEVEL,
        SPACE_LEVEL
    }
    protected ELevel currentLevel;
    
    protected Player player;
    protected ArrayList<Enemy> enemies = new ArrayList<>();
    protected int maxScreenWidth = 0;
    protected int maxScreenHeight = 0;

    
    // Singleton
    private static SpaceInvaderGame INSTANCE = null;
    
    public static SpaceInvaderGame getInstance()
    {			
        if (INSTANCE == null)
        { 	
            INSTANCE = new SpaceInvaderGame();	
        }
        return INSTANCE;
    }
    
    public SpaceInvaderGame()
    {
        //set the default first level
        currentLevel = ELevel.SPACE_INVADER_LEVEL;
        initGame();
    }
    
    public void initGame()
    {
        Hashtable<String, String> options = loadOptions("config.ini");
        player = new Player(options.get("playerName"), options.get("playerFirstname"), options.get("playerNickname"), options.get("playerPicturePath"));
       

    }
    
    private Hashtable<String, String> loadOptions(String path)
    {
        IniFile ini;
        try {
            ini = new IniFile(path);
            Hashtable<String, String> format = new Hashtable<String, String>();
            format.put("playerName", ini.getString("Player", "name", "DefaultPlayerName"));
            format.put("playerFirstname", ini.getString("Player", "firstname", "DefaultPlayerFirstName"));
            format.put("playerNickname", ini.getString("Player", "nickname", "DefaultNickName#Kevin"));
            format.put("playerPicturePath", ini.getString("Player", "picturepath", "ressources/defaultPlayer.png"));            
            return format;
        } catch (IOException ex) {
            Logger.getLogger(SpaceInvaderGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
     * @return the currentLevel
     */
    public ELevel getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @return the enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the maxScreenWidth
     */
    public int getMaxScreenWidth() {
        return maxScreenWidth;
    }

    /**
     * @param maxScreenWidth the maxScreenWidth to set
     */
    public void setMaxScreenWidth(int maxScreenWidth) {
        this.maxScreenWidth = maxScreenWidth;
    }

    /**
     * @return the maxScreenHeight
     */
    public int getMaxScreenHeight() {
        return maxScreenHeight;
    }

    /**
     * @param maxScreenHeight the maxScreenHeight to set
     */
    public void setMaxScreenHeight(int maxScreenHeight) {
        this.maxScreenHeight = maxScreenHeight;
    }
}

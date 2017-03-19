/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject;

import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Logger;

import spaceinvadersproject.Helpers.IniFile;
import spaceinvadersproject.Models.Enemies.Enemy;

/**
 * Represent a level
 * This class load the correspondant level's config file and help to initialize
 * the game with it
 * @author Karakayn
 */
public class Level {
    
    enum ELevel {
        SPACE_INVADER_LEVEL,
        SPACE_LEVEL
    }
    private ELevel _currentLevelName;
    
    public Level(String path, ELevel number) throws InstantiationException, IllegalAccessException
    {
        _currentLevelName = number;
        loadLevelFromIni(path);
    }
    
    public Level(ELevel number) throws InstantiationException, IllegalAccessException
    {
        this("Levels.ini", number);
    }
    
    private void loadLevelFromIni(String path) throws InstantiationException, IllegalAccessException
    {
        IniFile ini;
        try {
            ini = new IniFile(path);
            Hashtable<String, String> format = new Hashtable<String, String>();
            int nbEnemies = ini.getInt("Level_"+_currentLevelName, "nbEnemies", 0);
            format.put("nbEnemies", Integer.toString(nbEnemies));
            for(int i=0; i < nbEnemies; i++)
            {
                int maxStructPoint = ini.getInt("Enemy_"+_currentLevelName, "maxStructPoint", 0);
                int maxShieldPoint = ini.getInt("Enemy_"+_currentLevelName, "maxShieldPoint", 0);
                String pictureUrl = ini.getString("Enemy_"+_currentLevelName, "picture", "sprites/miniSI_pink.png");
                String type = ini.getString("Enemy_"+_currentLevelName, "type", "SpaceInvader");
                try {
                    Class enemyClass = Class.forName(type); //exemple : SpaceInvader class
                    Enemy currentEnemy = (Enemy) enemyClass.newInstance();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
    }
    
}

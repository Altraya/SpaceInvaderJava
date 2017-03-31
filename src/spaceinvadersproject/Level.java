/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.logging.Logger;

import spaceinvadersproject.Helpers.IniFile;
import spaceinvadersproject.Models.Enemies.Enemy;
import spaceinvadersproject.Models.Enemies.SpaceInvader;
import spaceinvadersproject.Models.Player.Player;

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
    
    private int enemyNumber;
    
    public Level(String path, ELevel number, int marginLeft, int marginTop) throws InstantiationException, IllegalAccessException
    {
        _currentLevelName = number;
        loadLevelFromIni(path, marginLeft, marginTop);
    } 
    
    public Level(ELevel number, int marginLeft, int marginTop) throws InstantiationException, IllegalAccessException
    {
        this("Levels.ini", number, marginLeft, marginTop);
    }
    
    /**
     * Load in the game the information of the level from ini file
     * @param path path of the ini file
     * @param marginLeft margin on the left of the rows
     * @param marginTop margin on the top of the rows
     * @return the number of ennemies created
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    private void loadLevelFromIni(String path, int marginLeft, int marginTop) throws InstantiationException, IllegalAccessException
    {
        IniFile ini;
        try {
            ini = new IniFile(path);

            String keyLevel = "Level_"+_currentLevelName;
            
            int nbEnemies = ini.getInt(keyLevel, "nbEnemies", 0);
            int widthSprite = ini.getInt(keyLevel, "widthSprite", 100);
            int heightSprite = ini.getInt(keyLevel, "heightSprite", 73);
            int maxRow = ini.getInt(keyLevel, "maxRow", 5);
            int maxEnemyByRow = ini.getInt(keyLevel, "maxEnemyByRow", 12);

            String picturePlayerUrl = ini.getString(keyLevel+"_Player", "picture", "sprites/player.png");
            String audioPlayerUrl = ini.getString(keyLevel+"_Player", "audio", "audios/shoot.wav");
            String playerFirstName = ini.getString(keyLevel+"_Player", "firstName", "DefaultPlayerFirstName");
            String playerLastName = ini.getString(keyLevel+"_Player", "lastName", "DefaultPlayerLastName");
            String playerNickName = ini.getString(keyLevel+"_Player", "nickname", "DefaultPlayerNickName");
            int widthSpritePlayer = ini.getInt(keyLevel+"_Player", "widthSprite", 100);
            int heightSpritePlayer = ini.getInt(keyLevel+"_Player", "heightSprite", 100);
            // create the player ship and place it roughly in the center of the screen
            Game.getInstance().setPlayer(new Player(picturePlayerUrl, audioPlayerUrl, Game.getInstance().getMaxScreenWidth()/2,Game.getInstance().getMaxScreenHeight()-(heightSpritePlayer*2), playerFirstName, playerLastName, playerNickName));
            Game.getInstance().getPlayer().setX(Game.getInstance().getMaxScreenWidth() / 2 - (Game.getInstance().getPlayer().getSprite().getWidth() / 2) );
            Game.getInstance().getEntities().add(Game.getInstance().getPlayer());
            
            // create a block of enemy (5 rows, by 12 enemy, spaced evenly)
            enemyNumber = 0;
            for (int row=0;row<maxRow;row++) {
                for (int x=0;x<maxEnemyByRow;x++) {
                    String type = "SpaceInvader";
                    String pictureCurrentEnemy = "sprites/miniSI_yellow.png";
                    
                    //type of enemy
                    try {
                        type = ini.getString(keyLevel+"_Enemy_"+enemyNumber, "type", "SpaceInvader");
                    } catch(NullPointerException ex)
                    {     
                        //here when we don't have all key with enemy count so fill with default unit
                        type = ini.getString(keyLevel+"_Enemy_AllOthers", "type", "SpaceInvader");
                    }
                    
                    //sprite picture
                    try {
                        pictureCurrentEnemy = ini.getString(keyLevel+"_Enemy_"+enemyNumber, "picture", "sprites/miniSI_pink.png");
                    }catch(NullPointerException ex)
                    {
                        //here when we don't have all key with enemy count so fill with the good one
                        pictureCurrentEnemy = ini.getString(keyLevel+"_Enemy_AllOthers", "picture", "sprites/miniSI_red.png");
                    }
                    
                    try {
                        String rscFile = "spaceinvadersproject.Models.Enemies."+type;
                        
                        Class enemyClass = Class.forName(rscFile); //exemple : SpaceInvader class
                        
                        //get type of arguments to construct it dynamically
                        Class[] cArg = new Class[3]; //Our constructor has 3 arguments
                        cArg[0] = String.class; //First argument is of *object* type String
                        cArg[1] = int.class; //Second argument is of *primitive* type int
                        cArg[2] = int.class; //Third argument is of *primitive* type int
                        
                        
                        Enemy currentEnemy = (Enemy) enemyClass.getDeclaredConstructor(cArg).newInstance(pictureCurrentEnemy,marginLeft+(x*widthSprite),(marginTop)+row*heightSprite);
                        Game.getInstance().getEntities().add(currentEnemy);
                        enemyNumber++;
                        
                    } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch(ClassNotFoundException ex)
                    {
                        System.out.println("Can't load class entity");
                        //bug in load the class entity, so we create a normal space invader
                        Enemy currentEnemy = (Enemy) new SpaceInvader("sprites/miniSI_yellow.png",marginLeft+(x*widthSprite),(marginTop)+row*heightSprite);
                        Game.getInstance().getEntities().add(currentEnemy);
                        enemyNumber++;
                    }
                    
                }
            }      
            
        }   catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the enemyNumber
     */
    public int getEnemyNumber() {
        return enemyNumber;
    }

    /**
     * @param enemyNumber the enemyNumber to set
     */
    public void setEnemyNumber(int enemyNumber) {
        this.enemyNumber = enemyNumber;
    }
}

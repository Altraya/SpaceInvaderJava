/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject;

import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Logger;
import spaceinvadersproject.Helpers.IniFile;
import spaceinvadersproject.Level.ELevel;
import spaceinvadersproject.Models.Enemies.Enemy;
import spaceinvadersproject.Models.Enemies.SpaceInvader;
import spaceinvadersproject.Models.Entity;
import spaceinvadersproject.Models.Player.Player;
import spaceinvadersproject.Models.ShotEntity;

/**
 * Represent the game entity [Singleton]
 * @author Karakayn
 */
public class SpaceInvaderGame {

    public enum ECote {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    
    /** The stragey that allows us to use accelerate page flipping */
    private BufferStrategy strategy;
    /** True if the game is currently "running", i.e. the game loop is looping */
    private boolean gameRunning = true;
    /** The list of entities that need to be removed from the game this loop */
    private ArrayList removeList = new ArrayList();
    /** The speed at which the player's ship should move (pixels/sec) */
    private double moveSpeed = 300;
    /** The time at which last fired a shot */
    private long lastFire = 0;
    /** The number of aliens left on the screen */
    private int ennemyCount;

    /** The message to display which waiting for a key press */
    private String message = "";
    /** True if we're holding up game play until a key has been pressed */
    private boolean waitingForKeyPress = true;
    /** True if the left cursor key is currently pressed */
    private boolean leftPressed = false;
    /** True if the right cursor key is currently pressed */
    private boolean rightPressed = false;
    /** True if we are firing */
    private boolean firePressed = false;
    /** True if game logic needs to be applied this loop, normally as a result of a game event */
    private boolean logicRequiredThisLoop = false;
    
    //Current level loading from level.ini file
    private Level currentLevel;
    
    private Player player;
    //all entities in our game (player / enemies / shot ...)
    private ArrayList<Entity> entities = new ArrayList<>();
    
    private int maxScreenWidth = 0;
    private int maxScreenHeight = 0;

    
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
        try {
            //set the default first level
            currentLevel = new Level(ELevel.SPACE_INVADER_LEVEL);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SpaceInvaderGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initGame();
    }
    
    public void initGame()
    {
        Hashtable<String, String> options = loadOptions("config.ini");
        setPlayer(new Player(options.get("playerPicturePath"), maxScreenWidth/2, maxScreenHeight/2, options.get("playerName"), options.get("playerFirstname"), options.get("playerNickname")));
       
        //TODO faire le load en fonction de l'option
        setEnnemyCount(0);
        for (int row=0;row<5;row++) {
            for (int x=0;x<12;x++) {
                Entity currentEnemy = new SpaceInvader("ressources/miniSIt.png",100+(x*50),(50)+row*30);
                entities.add(currentEnemy);
                setEnnemyCount(getEnnemyCount() + 1);
            }
        }
    }
    
    /**
    * Start a fresh game, this should clear out any old data and
    * create a new set.
    */
    private void startGame() {
        // clear out any existing entities and intialise a new set
        entities.clear();
        initGame();

        //TODO faire en sorte que ca prenne les niveaux pour start le bon niveau
        // blank out any keyboard settings we might currently have
        setLeftPressed(false);
        setRightPressed(false);
        setFirePressed(false);
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
            Logger.getLogger(SpaceInvaderGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
    * Attempt to fire a shot from the player. Its called "try"
    * since we must first check that the player can fire at this 
    * point, i.e. has he/she waited long enough between shots
    */
    public void tryToFire() {
        // check that we have waiting long enough to fire
        //TODO
        int firingInterval = 500;
        if (System.currentTimeMillis() - lastFire < firingInterval) {
                return;
        }

        // if we waited long enough, create the shot entity, and record the time.
        lastFire = System.currentTimeMillis();
        ShotEntity shot = new ShotEntity("ressources/shot.png",this.player.getAircraft().getX()+10,this.player.getAircraft().getY()-30);
        entities.add(shot);
    }
    
    /**
    * Notification from a game entity that the logic of the game
    * should be run at the next opportunity (normally as a result of some
    * game event)
    */
    public void updateLogic() {
        setLogicRequiredThisLoop(true);
    }

    /**
     * Remove an entity from the game. The entity removed will
     * no longer move or be drawn.
     * 
     * @param entity The entity that should be removed
     */
    public void removeEntity(Entity entity) {
        getRemoveList().add(entity);
    }

    /**
    * Notification that the player has died. 
    */
    public void notifyDeath() {
        setMessage("Oh no! They got you, try again?");
        setWaitingForKeyPress(true);
    }

    /**
     * Notification that the player has won since all the aliens
     * are dead.
     */
    public void notifyWin() {
        setMessage("Well done! You Win!");
        setWaitingForKeyPress(true);
    }
        
    /**
    * Notification that an alien has been killed
    */
    public void notifyEnemyKilled() {
        // reduce the alient count, if there are none left, the player has won!

        ennemyCount--;
        if (ennemyCount == 0) {
            notifyWin();
        }

        // if there are still some enemies left then they all need to get faster, so
        // speed up all the existing enemies
        for (int i=0;i<entities.size();i++) {
            Entity entity = (Entity) entities.get(i);

            if (entity instanceof SpaceInvader) {
                // speed up by 2%
                entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
            }
        }
    }
        
    /**
     * @return the currentLevel
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @return the enemies
     */
    public ArrayList<Entity> getEntities() {
        return entities;
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

    /**
     * @return the strategy
     */
    public BufferStrategy getStrategy() {
        return strategy;
    }

    /**
     * @param strategy the strategy to set
     */
    public void setStrategy(BufferStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @return the gameRunning
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * @param gameRunning the gameRunning to set
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * @return the removeList
     */
    public ArrayList getRemoveList() {
        return removeList;
    }

    /**
     * @param removeList the removeList to set
     */
    public void setRemoveList(ArrayList removeList) {
        this.removeList = removeList;
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

    /**
     * @return the lastFire
     */
    public long getLastFire() {
        return lastFire;
    }

    /**
     * @param lastFire the lastFire to set
     */
    public void setLastFire(long lastFire) {
        this.lastFire = lastFire;
    }

    /**
     * @return the ennemyCount
     */
    public int getEnnemyCount() {
        return ennemyCount;
    }

    /**
     * @param ennemyCount the ennemyCount to set
     */
    public void setEnnemyCount(int ennemyCount) {
        this.ennemyCount = ennemyCount;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the waitingForKeyPress
     */
    public boolean isWaitingForKeyPress() {
        return waitingForKeyPress;
    }

    /**
     * @param waitingForKeyPress the waitingForKeyPress to set
     */
    public void setWaitingForKeyPress(boolean waitingForKeyPress) {
        this.waitingForKeyPress = waitingForKeyPress;
    }

    /**
     * @return the leftPressed
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * @param leftPressed the leftPressed to set
     */
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    /**
     * @return the rightPressed
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    /**
     * @param rightPressed the rightPressed to set
     */
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    /**
     * @return the firePressed
     */
    public boolean isFirePressed() {
        return firePressed;
    }

    /**
     * @param firePressed the firePressed to set
     */
    public void setFirePressed(boolean firePressed) {
        this.firePressed = firePressed;
    }

    /**
     * @return the logicRequiredThisLoop
     */
    public boolean isLogicRequiredThisLoop() {
        return logicRequiredThisLoop;
    }

    /**
     * @param logicRequiredThisLoop the logicRequiredThisLoop to set
     */
    public void setLogicRequiredThisLoop(boolean logicRequiredThisLoop) {
        this.logicRequiredThisLoop = logicRequiredThisLoop;
    }

    /**
     * @param currentLevel the currentLevel to set
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @param enemies the enemies to set
     */
    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}

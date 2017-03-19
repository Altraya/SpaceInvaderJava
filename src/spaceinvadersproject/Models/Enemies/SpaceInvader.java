/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models.Enemies;

import spaceinvadersproject.Game;
import spaceinvadersproject.Models.Entity;

/**
 * Represent a space invader enemy with more power
 * @author Karakayn
 */
public class SpaceInvader extends Enemy{

    /** The speed at which the alient moves horizontally */
    private double moveSpeed = 75;

    /**
     * Create a new alien entity
     * 
     * @param ref The sprite which should be displayed for this alien
     * @param x The intial x location of this alien
     * @param y The intial y location of this alient
     */
    public SpaceInvader(String ref,int x,int y) {
        super(ref,x,y);
        dx = -moveSpeed;
    }

    /**
     * Request that this alien moved based on time elapsed
     * 
     * @param delta The time that has elapsed since last move
     */
    public void move(long delta) {
        // if we have reached the left hand side of the screen and
        // are moving left then request a logic update 
        if ((dx < 0) && (x < 10)) {
                Game.getInstance().updateLogic();
        }
        // and vice versa, if we have reached the right hand side of 
        // the screen and are moving right, request a logic update
        if ((dx > 0) && (x > 750)) {
                Game.getInstance().updateLogic();
        }

        // proceed with normal move
        super.move(delta);
    }

    /**
     * Update the game logic related to aliens
     */
    public void doLogic() {
        // swap over horizontal movement and move down the
        // screen a bit
        dx = -dx;
        y += 10;

        // if we've reached the bottom of the screen then the player
        // dies
        if (y > Game.getInstance().getMaxScreenHeight()) {
            Game.getInstance().notifyDeath();
        }
    }
	
    /**
     * Notification that this alien has collided with another entity
     * 
     * @param other The other entity
     */
    public void collidedWith(Entity other) {
        // collisions with aliens are handled elsewhere

    }

}

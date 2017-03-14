/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models;

import spaceinvadersproject.Models.Enemies.Enemy;
import spaceinvadersproject.SpaceInvaderGame;

/**
 *
 * @author Karakayn
 */
public class ShotEntity extends Entity {
    /** The vertical speed at which the players shot moves */
    private double moveSpeed = -300;
    /** The game in which this entity exists */
    private spaceinvadersproject.SpaceInvaderGame game;
    /** True if this shot has been "used", i.e. its hit something */
    private boolean used = false;

    /**
     * Create a new shot from the player
     * 
     * @param sprite The sprite representing this shot
     * @param x The initial x location of the shot
     * @param y The initial y location of the shot
     */
    public ShotEntity(String sprite,int x,int y) {
        super(sprite,x,y);

        this.game = SpaceInvaderGame.getInstance();

        dy = moveSpeed;
    }

    /**
     * Request that this shot moved based on time elapsed
     * 
     * @param delta The time that has elapsed since last move
     */
    public void move(long delta) {
        // proceed with normal move

        super.move(delta);

        // if we shot off the screen, remove ourselfs

        if (y < -100) {
            SpaceInvaderGame.getInstance().removeEntity(this);
        }
    }

    /**
     * Notification that this shot has collided with another
     * entity
     * 
     * @parma other The other entity with which we've collided
     */
    public void collidedWith(Entity other) {
        // prevents double kills, if we've already hit something,
        // don't collide
        if (used) {
            return;
        }

        // if we've hit an alien, kill it!

        if (other instanceof Enemy) {
            // remove the affected entities

            SpaceInvaderGame.getInstance().removeEntity(this);
            SpaceInvaderGame.getInstance().removeEntity(other);

            // notify the game that the alien has been killed

            SpaceInvaderGame.getInstance().notifyEnemyKilled();
            used = true;
        }
    }
}

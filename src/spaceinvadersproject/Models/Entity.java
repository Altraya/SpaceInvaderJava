/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersproject.Models;

import java.awt.Graphics;
import java.awt.Rectangle;
import spaceinvadersproject.Models.Sprites.Sprite;
import spaceinvadersproject.Models.Sprites.SpriteStore;

/**
 *
 * @author Karakayn
 */
public abstract class Entity {
    /** The current x location of this entity */ 
    protected double x;
    /** The current y location of this entity */
    protected double y;
    /** The sprite that represents this entity */
    protected Sprite sprite;
    /** The current speed of this entity horizontally (pixels/sec) */
    protected double dx;
    /** The current speed of this entity vertically (pixels/sec) */
    protected double dy;
    /** The rectangle used for this entity during collisions resolution */
    private Rectangle me = new Rectangle();
    /** The rectangle used for other entities during collision resolution */
    private Rectangle him = new Rectangle();

    public Entity() {
        this.sprite = SpriteStore.get().getSprite("ressources/defaultPlayer.png");
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * Construct a entity based on a sprite image and a location.
     * 
     * @param picturePath The reference to the image to be displayed for this entity
     * @param x The initial x location of this entity
     * @param y The initial y location of this entity
     */
    public Entity(String picturePath,int x,int y) {
        this.sprite = SpriteStore.get().getSprite(picturePath);
        this.x = x;
        this.y = y;
    }

    /**
     * Request that this entity move itself based on a certain ammount
     * of time passing.
     * 
     * @param delta The ammount of time that has passed in milliseconds
     */
    public void move(long delta) {
        // update the location of the entity based on move speeds
        x += (delta * dx) / 1000;
        y += (delta * dy) / 1000;
    }

    /**
     * Set the horizontal speed of this entity
     * 
     * @param dx The horizontal speed of this entity (pixels/sec)
     */
    public void setHorizontalMovement(double dx) {
        this.dx = dx;
    }

    /**
     * Set the vertical speed of this entity
     * 
     * @param dx The vertical speed of this entity (pixels/sec)
     */
    public void setVerticalMovement(double dy) {
        this.dy = dy;
    }

    /**
     * Get the horizontal speed of this entity
     * 
     * @return The horizontal speed of this entity (pixels/sec)
     */
    public double getHorizontalMovement() {
        return dx;
    }

    /**
     * Get the vertical speed of this entity
     * 
     * @return The vertical speed of this entity (pixels/sec)
     */
    public double getVerticalMovement() {
        return dy;
    }

    /**
     * Draw this entity to the graphics context provided
     * 
     * @param g The graphics context on which to draw
     */
    public void draw(Graphics g) {
        sprite.draw(g,(int) x,(int) y);
    }

    /**
     * Do the logic associated with this entity. This method
     * will be called periodically based on game events
     */
    public void doLogic() {
    }

    /**
     * Get the x location of this entity
     * 
     * @return The x location of this entity
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Get the y location of this entity
     * 
     * @return The y location of this entity
     */
    public int getY() {
        return (int) y;
    }

    /**
     * Check if this entity collised with another.
     * 
     * @param other The other entity to check collision against
     * @return True if the entities collide with each other
     */
    public boolean collidesWith(Entity other) {
        me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
        him.setBounds((int) other.x,(int) other.y,other.sprite.getWidth(),other.sprite.getHeight());

        return me.intersects(him);
    }

    /**
     * Notification that this entity collided with another.
     * 
     * @param other The entity with which this entity collided.
     */
    public abstract void collidedWith(Entity other);
}

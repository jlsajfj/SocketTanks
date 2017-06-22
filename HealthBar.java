 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * HealthBar generates and maintains a custom-sized image of a Health Bar
 * in green and red (red being missing HP).
 * 
 * Accepts any size - use Constants.
 * 
 * NOT FINISHED! Forgive my lack of commenting...
 * 
 * @author Jordan Cohen
 * @version 0.0.1 (2012)
 */
public class HealthBar extends Actor
{

    
    // Declare Instance Variables
    private int maxHP;
    private int currHP = 0;
    private double currPercentHP;
    private int redBarSize;
    private int greenBarSize;

    // Declare Instance Images
    private GreenfootImage bar;
    private GreenfootImage blank;
    
    // Some constants - can be changed to suit size of related objects
    private final int HP_BAR_WIDTH = 48;
    private final int HP_BAR_HEIGHT = 6;
    private final int OFFSET = 36;

    // Declare Instance Objects
    private Actor target;

    // Declare some Color objects
    private Color myGreen = new Color (0, 255, 0);
    private Color myRed = new Color (255, 0, 0);

    /**
     * Main constructor - this is only called by the other Constructors
     * and is not intended to be called directly.
     */
    public HealthBar()
    {
        bar = new GreenfootImage(HP_BAR_WIDTH, HP_BAR_HEIGHT);
        blank = new GreenfootImage(1,1);
        bar.setColor(myGreen);
        bar.fill();
        if (currHP == maxHP)
        {
            this.setImage(blank);
        }
        else
        {
            this.setImage(bar);
        }
    }

    /**
     * Constructor that takes one int - for objects starting with max hit points.
     * This will set both current and maximum hit points to the same value.
     */
    public HealthBar(int inMaxHP)
    {
        this(); // Calst he Main constructor (above)
        maxHP = inMaxHP;
        currHP = inMaxHP;
    }

    /**
     * Constructor takes an int for current and max hitpoints and also takes in an
     * Actor, which this HP Bar will follow - Whenever the Actor moves, so will this
     * hp bar. If the Actor is removed from the World, this HP bar will destroy itself.
     * 
     * NOTE: This is the Constructor used in the Bug simulation
     */
    public HealthBar (int inMaxHP, Actor target)
    {
        this(inMaxHP);
        this.target = target;
    }

    /**
     * Constructor that takes in a different value for current and max HP, ideal for
     * when a new health bar is needed for an Actor that doesn't have full HP.
     */
    public HealthBar(int inMaxHP, int inCurrHP)
    {
        this();
        maxHP = inMaxHP;
        currHP = inCurrHP;
    }

    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (target.getWorld() != null)
        {
            setLocation (target.getX(), target.getY() - OFFSET);
        }
        else
            getWorld().removeObject(this);
    }    

    /**
     * update Method:
     * 
     * Expects new current HP
     * 
     * Returns true if HP has changed (needs an update)
     * Returns false if HP has not changed (to avoid excessive processing)
     */
    public void update (int newCurrHP)
    {
        // Don't do anything if current HP hasn't changed
        if (newCurrHP != currHP)
        {
            if (newCurrHP == maxHP)
            {
                this.setImage(blank);
                //return false;
            }
            else
            {
                
                // Redraw HP bar w/ appropriate amount of green and red based on
                // current HP divided by max HP
                currHP = newCurrHP;
                currPercentHP = (double) currHP / maxHP;
                greenBarSize = (int) (currPercentHP * HP_BAR_WIDTH);
                redBarSize = HP_BAR_WIDTH - greenBarSize;
                //Troubleshooting code:
                //System.out.println("CurrHP: " + currHP + " curr%HP: " + currPercentHP);
                //System.out.println("GreenBar: " + greenBarSize + " RedBar: " + redBarSize);
                bar.setColor(myGreen);
                bar.fillRect(0,0,greenBarSize, HP_BAR_HEIGHT);
                bar.setColor(myRed);
                bar.fillRect(greenBarSize, 0, redBarSize, HP_BAR_HEIGHT);
                this.setImage(bar);
                //return true;
            }
        }
        //return false;
    }


}

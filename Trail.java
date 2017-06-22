
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Trail here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trail extends Actor
{
    int life=0;
    boolean fast;

    public Trail(){
        this(0);
    }

    public Trail(boolean fast){
        this(0);
        this.fast=true;
    }

    public Trail(int r){
        setRotation(r);
        this.fast=false;
    }

    /**
     * Act - do whatever the Trail wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        life++;
        if(life%40==0){
            this.getImage().setTransparency(Math.max(0,getImage().getTransparency() - 17*(fast?2:1)));
        }
        if(getImage().getTransparency()<=0){
            getWorld().removeObject(this);
        }
    }    
}

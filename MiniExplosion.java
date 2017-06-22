 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Explosion widget.<br />
 * <b>RUN SETUP BEFORE USING</b>
 * 
 * @author Joey Ma
 * @version March 2017
 */
public class MiniExplosion extends Actor
{
    public static GreenfootImage[] frames = new GreenfootImage[17];
    private int cnt = 1;
    /**
     * Sets up the pictures.
     */
    public static void setUp(){
        for(int i = 0; i < 17; i++){
            frames[i] = new GreenfootImage(i+".png");
            frames[i].scale(frames[i].getWidth(),frames[i].getHeight());
        }
    }
    
    /**
     * Creates a explosion.
     */
    public MiniExplosion(){
        Greenfoot.playSound("miniBOOM.mp3");
        setImage(frames[0]);
    }

    /**
     * Updates the pictures and delets itself after completing the frames.
     */
    public void act()
    {
        custom();
        if(cnt == 17){
            getWorld().removeObject(this);
        }else{
            setImage(frames[cnt]);
            cnt++;
        }
    }
    
    /**
     * Edit only this when implementing in a project.
     */
    private void custom(){
        if(cnt==1){
            ArrayList<Tank> b = (ArrayList)getIntersectingObjects(Tank.class);
            for(Tank p: b){
                p.DIE(6);
            }
            for(Crate c: (ArrayList<Crate>) getIntersectingObjects(Crate.class)){
                c.DIE(6);
            }
        }
    }
}

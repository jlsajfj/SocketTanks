import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Laser extends Actor
{
    Tank shooter;
    int time;
    public Laser(Tank t){
        shooter = t;
        time = 0;
        setImage(new GreenfootImage("Laser"+(shooter.player?".png":"-Enemy.png")));
    }

    public void addedToWorld(World w){
        setRotation(shooter.getRotation()+shooter.gunRot);
    }

    public void act(){
        if(time==0){
            MainWorld w = (MainWorld) getWorld();
            Tank t = w.tanks[shooter.player?1:0];
            if(intersects(t)){
                t.DIE(50);
            }
            for(Crate c: (java.util.ArrayList<Crate>) getIntersectingObjects(Crate.class)){
                c.DIE(30);
            }
        }
        if(time>=0){
            getImage().setTransparency(getImage().getTransparency()-5);
            if(getImage().getTransparency()<=5)
                getWorld().removeObject(this);
        }
        time++;
    }

    public void resetTime(){
        time=0;
    }

    public void cd(){
        time=-50;
    }
}

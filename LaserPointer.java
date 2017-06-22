import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LaserPointer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserPointer extends Actor
{
    Tank shooter;
    public LaserPointer(Tank t){
        shooter=t;
        setImage(new GreenfootImage("LaserPointer"+(shooter.player?".png":"-Enemy.png")));
        setLocation(shooter.cg[0][0]+shooter.getX(),shooter.cg[0][1]+shooter.getY());
        setRotation(shooter.getRotation()+shooter.gunRot);
    }

    public void act(){
        MainWorld w = (MainWorld) getWorld();
        w.guns[shooter.player?0:1].update(Math.max(0,w.guns[shooter.player?0:1].getPercentage()-0.02));
        if(w.guns[shooter.player?0:1].getPercentage()==0.0){
            double c = Math.cos(Math.toRadians(shooter.getRotation()+shooter.gunRot));
            double s = Math.sin(Math.toRadians(shooter.getRotation()+shooter.gunRot));
            getWorld().addObject(new Laser(shooter), getX()+(int)(600*c), getY()+(int)(600*s));
            getWorld().removeObject(this);
        }
        else{
            setLocation(shooter.cg[0][0]+shooter.getX(),shooter.cg[0][1]+shooter.getY());
            setRotation(shooter.getRotation()+shooter.gunRot);
        }
    }
}

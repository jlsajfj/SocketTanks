import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Laser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MiniLaser extends Actor
{
    Tank shooter;
    int time;
    public MiniLaser(Tank t){
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
            if(isVI(t)){
                t.DIE(15);
            }
            for(Crate c: (java.util.ArrayList<Crate>) getIntersectingObjects(Crate.class)){
                if(isVI(c)){
                    c.DIE(10);
                }
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

    public boolean isVI(Actor A)
    {
        double d_Hypot=Math.hypot(getImage().getWidth(),getImage().getHeight());
        GreenfootImage i=new GreenfootImage((int)d_Hypot,(int)d_Hypot);
        i.drawImage(getImage(),i.getWidth()/2-getImage().getWidth()/2,i.getHeight()/2-getImage().getHeight()/2);
        i.rotate(getRotation());
        int w=i.getWidth(),h=i.getHeight(),x=getX(),y=getY();
 
        GreenfootImage Ai = A.getImage();
        d_Hypot=Math.hypot(Ai.getWidth(),Ai.getHeight());
        GreenfootImage
        i2=new GreenfootImage((int)d_Hypot,(int)d_Hypot);
        i2.drawImage(Ai,i2.getWidth()/2-Ai.getWidth()/2,i2.getHeight()/2-Ai.getHeight()/2);
        i2.rotate(A.getRotation());
        Ai=i2;
 
        i2 = new GreenfootImage(w,h);
        int Aw=Ai.getWidth(),Ah=Ai.getHeight(),Ax=A.getX(),Ay=A.getY();
        i2.drawImage(Ai,Ax-x-(Aw/2-w/2),Ay-y-(Ah/2-h/2));
        for(int yi = 0; yi<h ; yi++)
            for(int xi = 0; xi<w ; xi++)
                if(i2.getColorAt(xi,yi).getAlpha()>0 && i.getColorAt(xi,yi).getAlpha()>0)
                    return true;
        return false;
    }
}

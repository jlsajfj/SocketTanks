import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Rocket here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rocket extends SmoothMover
{
    private int speed;
    private Tank shooter;

    public Rocket(Tank shooter){
        speed=5;
        this.shooter = shooter;
    }

    public Rocket(){
        speed=5;
        shooter=null;
    }

    /**
     * Act - do whatever the Rocket wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(speed*2);
        speed++;
        for(Tank t: (java.util.ArrayList<Tank>) getIntersectingObjects(Tank.class)){
            if(t!=shooter&&isVA(t)){
                getWorld().addObject(new MiniExplosion(),getX(),getY());
                getWorld().removeObject(this);
                return;
            }
        }
        for(Crate c: (java.util.ArrayList<Crate>) getIntersectingObjects(Crate.class)){
            if(isVA(c)){
                getWorld().addObject(new MiniExplosion(),getX(),getY());
                getWorld().removeObject(this);
                return;
            }
        }
        if(getX()<-100) getWorld().removeObject(this);
        else if(getY()<-100) getWorld().removeObject(this);
        else if(getX()>MainWorld.WIDTH+100) getWorld().removeObject(this);
        else if(getY()>MainWorld.HEIGHT+100) getWorld().removeObject(this);
    }

    public boolean isVA(Actor A)
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

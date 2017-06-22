import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PickUp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PickUp extends SmoothMover
{
    public static GreenfootImage[] pics;
    public static final int cnt = 3;
    private int type, sy, time;

    public static void setUp(){
        pics = new GreenfootImage[cnt];
        pics[0] = new GreenfootImage("HealthPack.png");
        pics[1] = new GreenfootImage("EnergyPack.png");
        pics[2] = new GreenfootImage("EnergyPackBlue.png");
    }

    public PickUp(){
        this(0);
    }

    public PickUp(int type){
        this.type = type;
        time = 0;
        setImage(pics[type]);
    }

    public int getType(){
        return type;
    }

    public void addedToWorld(World w){
        sy=getY();
    }

    public void act() 
    {
        try{
            setLocation(getX(),sy+Math.sin(Math.toRadians(time*3)));
            time++;
            try{
                for(Tank t: (java.util.List<Tank>) getIntersectingObjects(Tank.class)){
                    if(t==null) continue;
                    if(type == 0)
                        t.LIVE(50);
                    if(type == 1)
                        t.SPEED();
                    if(type == 2)
                        t.POWER();
                    getWorld().removeObject(this);
                    return;
                }
                for(Crate c: (java.util.List<Crate>) getIntersectingObjects(Crate.class)){
                    getWorld().removeObject(this);
                    return;
                }
            }
            catch(java.util.ConcurrentModificationException e){
                Greenfoot.start();
            }
        }
        catch(java.lang.NullPointerException e){
            return;
        }
    }
    
    public void shift(){
        time = 60;
    }
}

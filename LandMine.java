
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class LandMine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LandMine extends Actor
{
    private int speed, cnt;
    public LandMine(){
        speed = 15;
        cnt=0;
    }

    /**
     * Act - do whatever the LandMine wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(speed);
        speed--;
        speed = Math.max(0,speed);
        if(getImage().getTransparency()>10) getImage().setTransparency(getImage().getTransparency()-8);
        if(getImage().getTransparency()<=10&&cnt==0){
            try{
                Tank p = (Tank)getOneObjectAtOffset(0, 0, Tank.class);
                if (p != null)
                {
                    Greenfoot.playSound("deet.mp3");
                    cnt=1;
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(cnt!=0){
            cnt++;
            if(cnt==55){
                getWorld().addObject(new MiniExplosion(),getX(),getY());
                getWorld().removeObject(this);
            }
        }
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fire here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fire extends SmoothMover
{
    Tank s;
    public Fire(Tank s){
        this.s=s;
        setRotation(s.getRotation()+s.gunRot+Greenfoot.getRandomNumber(21)-10);
    }

    public void act(){
        move(10);
        getImage().rotate((Greenfoot.getRandomNumber(11)+10)*(Greenfoot.getRandomNumber(2)==1?1:-1));
        getImage().setTransparency(Math.max(0,getImage().getTransparency()-17));
        if(getImage().getTransparency()==0){
            getWorld().removeObject(this);
            return;
        }
        
        for(Tank t: (java.util.ArrayList<Tank>) getIntersectingObjects(Tank.class)){
            if(t!=null){
                if(t==s) return;
                if(isVA(t)){
                    t.DIE(0.5);
                }
            }
        }
        
        for(Crate c: (java.util.ArrayList<Crate>) getIntersectingObjects(Crate.class)){
            if(c!=null){
                if(isVA(c)){
                    c.DIE(0.5);
                }
            }
        }
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
                {
                    return true;
        }
        return false;
    }
}

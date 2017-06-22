import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World
{
    private Tank[] selection;
    private SocketTanks logo;
    private PickATank pick;
    private int sy,time,sy2;
    /**
     * Constructor for objects of class Menu.
     * 
     */
    public Menu()
    {
        super(MainWorld.WIDTH, MainWorld.HEIGHT, 1, false);
        logo = new SocketTanks();
        pick = new PickATank();
        logo.getImage().setTransparency(0);
        pick.getImage().setTransparency(0);
        addObject(logo, MainWorld.WIDTH/2, 150);
        addObject(pick, MainWorld.WIDTH/2, 560);
        Tank.setUp();
        selection = new Tank[Tank.bgnum];
        for(int i = 0; i < Tank.bgnum; i++){
            selection[i] = new Tank(1,i);
            addObject(selection[i],MainWorld.WIDTH/(Tank.bgnum+1)*(i+1),375+(i%2==1?100:0));
            selection[i].getImage().setTransparency(0);
        }
        time=0;
        sy=logo.getY();
        sy2=pick.getY();
        setPaintOrder(Tank.class, Trail.class);
    }

    public void act(){
        /*
        if(Greenfoot.isKeyDown("space")){
            int x = Greenfoot.getMouseInfo().getX();
            int y = Greenfoot.getMouseInfo().getY();
            for(Tank t: selection){
                if(x>=t.getX()&&x<t.getX()+t.getImage().getWidth()&&y>=t.getY()&&y<t.getY()+t.getImage().getHeight())
                {
                    Greenfoot.setWorld(new MainWorld(new Tank(t.getGun())));
                }
            }
        }*/
        if(logo.getImage().getTransparency()<255&&time%4==0){
            logo.getImage().setTransparency(logo.getImage().getTransparency()+5);
            for(Tank t: selection){
                t.getImage().setTransparency(t.getImage().getTransparency()+5);
            }
        }
        else if(logo.getImage().getTransparency()==255){
            //first time time=201
            GreenfootImage temp = new GreenfootImage(getBackground());
            GreenfootImage temp2 = new GreenfootImage(128,128);
            temp2.drawImage(temp,127,0);
            temp2.drawImage(temp,-1,0);
            setBackground(temp2);
            if((time-200)%12==0){
                for(Tank t: selection){
                    addObject(new Trail(true), t.getX(),t.getY());
                }
            }
            for(Trail t: (java.util.ArrayList<Trail>) getObjects(Trail.class)){
                t.setLocation(t.getX()-1,t.getY());
            }
            if(pick.getImage().getTransparency()<255&&time%2==0){
                pick.getImage().setTransparency(pick.getImage().getTransparency()+5);
            }
            if(pick.getImage().getTransparency()==255){
                for(int i = 0; i < Tank.bgnum; i++){
                    if(Greenfoot.mouseClicked(selection[i])){
                        Greenfoot.setWorld(new Selection(i, getBackground(), time));
                    }
                }
            }
        }
        logo.setLocation(logo.getX(),sy+Math.sin(Math.toRadians(time*4))*5);    
        pick.setLocation(pick.getX(),sy2+Math.sin(Math.toRadians(time*3)));
        time++;
    }
}

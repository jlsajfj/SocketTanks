import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Testing here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Testing extends World
{
    Tank a;
    /**
     * Constructor for objects of class Testing.
     * 
     */
    public Testing()
    {
        super(MainWorld.WIDTH, MainWorld.HEIGHT, 1);
        Tank.setUp();
        PickUp.setUp();
        Missile.setUp();
        Explosion.setUp();
        MiniExplosion.setUp();
        a = new Tank(0, true);
        setPaintOrder(ProgressBar.class, Explosion.class, MiniExplosion.class, Fire.class, HealthBar.class, Bullet.class, Missile.class, Rocket.class, Tank.class, LandMine.class, Trail.class);
        addObject(a, 100, MainWorld.HEIGHT/2);
    }

    public void act(){
        char keys = MainWorld.getKeys();
        a.sendKeys(keys);
        if(Greenfoot.getRandomNumber(100)==0){
            addObject(new PickUp(Greenfoot.getRandomNumber(PickUp.cnt)),Greenfoot.getRandomNumber(MainWorld.WIDTH/8*7)+MainWorld.WIDTH/16,Greenfoot.getRandomNumber(MainWorld.HEIGHT/8*7)+MainWorld.HEIGHT/16);
        }
    }
}

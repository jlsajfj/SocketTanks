public class Crate extends greenfoot.Actor
{
    public static int SIZE = 100;
    private double hp;
    private HealthBar hpBar;

    public Crate(){
        hp = 100;
        hpBar = new HealthBar ((int)hp, this);
    }

    public void addedToWorld(greenfoot.World w)
    {
        w.addObject(hpBar, getX(), getY());
        hpBar.update((int)hp);
    }

    public void DIE(double damage){
        hp-=damage;
        if(hp<=0){
            getWorld().removeObject(this);
        }
        hpBar.update(Math.max((int)hp,0));
    }
}

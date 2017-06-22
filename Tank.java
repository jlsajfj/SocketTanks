import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Tank here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tank extends SmoothMover implements java.io.Serializable
{
    //number of base gun parts (plus one for blank)
    public static final int bgnum = 7;
    public static GreenfootImage[] baseGun = new GreenfootImage[bgnum];
    public static GreenfootImage[] enemyGun = new GreenfootImage[bgnum];
    public static GreenfootImage base;
    public static GreenfootImage enemyBase;
    public static final int W = 0, A = 1, S = 2, D = 3, J = 4, L = 5, SPACE = 6;
    private GreenfootImage cImg;
    private HealthBar hpBar;
    private double hp;
    private int pu;
    private int cd;
    private int gun;
    public int gunRot;
    public int speed;
    private int hpMax;
    private double time;
    private boolean dead;
    private boolean left;
    private boolean debug;
    public boolean player;
    public static final int[][] gunLoc = {
            {0,0},
            {50,0},
            {65,0},
            {50,0},
            {50,0},
            {41,-18},
            {27,-28}};
    public static final int[] mid = {47,45};
    public int[][] cg;

    public static void setUp(){
        baseGun[0]=new GreenfootImage("Radar.png");
        baseGun[1]=new GreenfootImage("BigGun.png");
        baseGun[2]=new GreenfootImage("FlameThrower.png");
        baseGun[3]=new GreenfootImage("TripleGun.png");
        baseGun[4]=new GreenfootImage("MassiveGun.png");
        baseGun[5]=new GreenfootImage("Volcano.png");
        baseGun[6]=new GreenfootImage("Rockets.png");
        enemyGun[0]=new GreenfootImage("Radar-Enemy.png");
        enemyGun[1]=new GreenfootImage("BigGun-Enemy.png");
        enemyGun[2]=new GreenfootImage("FlameThrower-Enemy.png");
        enemyGun[3]=new GreenfootImage("TripleGun-Enemy.png");
        enemyGun[4]=new GreenfootImage("MassiveGun-Enemy.png");
        enemyGun[5]=new GreenfootImage("Volcano-Enemy.png");
        enemyGun[6]=new GreenfootImage("Rockets-Enemy.png");
        base=new GreenfootImage("Base.png");
        enemyBase=new GreenfootImage("Base-Enemy.png");
    }

    public boolean isPU(){
        return pu!=0;
    }

    public int getGun(){
        return gun;
    }

    public Tank(int dead, int gun){
        this.dead = true;
        this.gun = gun;
        pu=0;
    }

    public Tank(int gun, boolean debug){
        this(debug,gun);
        this.debug = true;
    }

    public Tank(){
        this(false);
    }

    public Tank(int gun){
        this(false, gun);
    }

    public Tank(boolean player){
        this(player, Greenfoot.getRandomNumber(7));
    }

    public Tank(boolean player, int gun){
        cImg=new GreenfootImage(base);
        this.gun = gun;
        gunRot = 0;
        hp = hpMax = (int)(500*(gun==1?1.5:1)*(gun==0?2:1)*(gun==2?0.75:1));
        speed = 1;
        time = 0;
        hpBar = new HealthBar ((int)hp, this);
        this.player = player;
        dead=false;
        debug=false;
        left=true;
        pu=0;
    }

    public void makePlayer(){
        this.player = true;
    }

    public void makeEnemy(){
        this.player = false;
    }

    public void addedToWorld (World w)
    {
        if(!dead){
            w.addObject (hpBar, getX(), getY());
            hpBar.update((int)hp);
        }
        GreenfootImage temp = (player?(new GreenfootImage(base)):(new GreenfootImage(enemyBase)));//96,92
        GreenfootImage tempGun = (player?(new GreenfootImage(baseGun[gun])):(new GreenfootImage(enemyGun[gun])));
        temp.drawImage(tempGun,0,0);
        setImage(temp);
    }

    public void sendKeys(char key){
        boolean forward = false;
        if(key==0) return;
        boolean[] keys = new boolean[7];
        for(int i = 0; i < 7; i++){
            keys[i]=(key&(1<<i))!=0;
        }
        if(keys[W]){
            forward = true;
            move(speed*(gun==0||gun==2?2:1)*(pu!=0?1.5:1));
        }
        if(keys[A]){
            setRotation(getRotation()-1);
            forward = true;
        }
        if(keys[S]){
            forward = true;
            move(-speed*(gun==0||gun==2?2:1)*(pu!=0?1.5:1));
        }
        if(keys[D]){
            setRotation(getRotation()+1);
            forward = true;
        }
        if(gun!=0&&keys[J]){
            if(keys[A]) gunRot++;
            if(keys[D]) gunRot--;
            gunRot-=2;
        }
        if(gun!=0&&keys[L]){
            if(keys[A]) gunRot++;
            if(keys[D]) gunRot--;
            gunRot+=2;
        }
        if(forward) time+=1*(gun==0||gun==2?2:1)*(pu!=0?1.5:1);
        if(time>=12){
            getWorld().addObject(new Trail(getRotation()),getX(),getY());
            time = 0;
        }
        if(keys[SPACE]){
            if(!debug){
                MainWorld w = (MainWorld) getWorld();
                if(w.guns[player?0:1].getPercentage()!=0.0){
                    switch(gun){
                        case 0:
                        if(w.guns[player?0:1].getPercentage()>=0.05){
                            LandMine l = new LandMine();
                            l.setRotation(getRotation());
                            getWorld().addObject(l,getX(),getY());
                            w.guns[player?0:1].update(w.guns[player?0:1].getPercentage()-0.05);
                        }
                        break;
                        case 1:
                        if(w.guns[player?0:1].getPercentage()==1.0){
                            Missile c = new Missile(this);
                            c.setRotation(getRotation()+gunRot);
                            getWorld().addObject(c,cg[0][0]+getX(),cg[0][1]+getY());
                            w.guns[player?0:1].update(0);
                        }
                        break;
                        case 2:
                        if(w.guns[player?0:1].getPercentage()>=0.05){
                            getWorld().addObject(new Fire(this),cg[0][0]+getX(),cg[0][1]+getY());
                            w.guns[player?0:1].update(w.guns[player?0:1].getPercentage()-0.05);
                        }
                        break;
                        case 3:
                        if(w.guns[player?0:1].getPercentage()==1.0){
                            getWorld().addObject(new MiniLaserPointer(this),cg[0][0]+getX(),cg[0][1]+getY());
                            w.guns[player?0:1].update(w.guns[player?0:1].getPercentage()-0.05);
                        }
                        break;
                        case 4:
                        if(w.guns[player?0:1].getPercentage()==1.0){
                            getWorld().addObject(new LaserPointer(this),cg[0][0]+getX(),cg[0][1]+getY());
                            w.guns[player?0:1].update(w.guns[player?0:1].getPercentage()-0.05);
                        }
                        break;
                        case 5:
                        if(w.guns[player?0:1].getPercentage()>=0.1){
                            Bullet a = new Bullet(this);
                            a.setRotation(getRotation()+gunRot);
                            getWorld().addObject(a,cg[0][0]+getX(),cg[0][1]+getY());
                            Bullet b = new Bullet(this);
                            b.setRotation(getRotation()+gunRot);
                            getWorld().addObject(b,cg[1][0]+getX(),cg[1][1]+getY());
                            w.guns[player?0:1].update(w.guns[player?0:1].getPercentage()-0.1);
                        }
                        break;
                        case 6:
                        if(w.guns[player?0:1].getPercentage()>=0.25){
                            if(cd==0){
                                Greenfoot.playSound("Bottle Rocket-SoundBible.com-332895117.mp3");
                                Rocket d = new Rocket(this);
                                d.setRotation(getRotation()+gunRot);
                                getWorld().addObject(d,cg[left?0:1][0]+getX(),cg[left?0:1][1]+getY());
                                cd = 10;
                                left = !left;
                                w.guns[player?0:1].update(w.guns[player?0:1].getPercentage()-0.25);
                            }
                            else cd--;
                        }
                        break;
                    }
                }
            }
            else{
                switch(gun){
                    case 0:
                    LandMine l = new LandMine();
                    l.setRotation(getRotation());
                    getWorld().addObject(l,getX(),getY());
                    break;
                    case 1:
                    Missile c = new Missile(this);
                    c.setRotation(getRotation()+gunRot);
                    getWorld().addObject(c,cg[0][0]+getX(),cg[0][1]+getY());
                    break;
                    case 2:
                    Fire f = new Fire(this);
                    getWorld().addObject(f,cg[0][0]+getX(),cg[0][1]+getY());
                    break;
                    case 5:
                    Bullet a = new Bullet(this);
                    a.setRotation(getRotation()+gunRot);
                    getWorld().addObject(a,cg[0][0]+getX(),cg[0][1]+getY());
                    Bullet b = new Bullet(this);
                    b.setRotation(getRotation()+gunRot);
                    getWorld().addObject(b,cg[1][0]+getX(),cg[1][1]+getY());
                    break;
                    case 6:
                    if(cd==0){
                        Greenfoot.playSound("Bottle Rocket-SoundBible.com-332895117.mp3");
                        Rocket d = new Rocket(this);
                        d.setRotation(getRotation()+gunRot);
                        getWorld().addObject(d,cg[left?0:1][0]+getX(),cg[left?0:1][1]+getY());
                        cd = 10;
                        left = !left;
                    }
                    else cd--;
                    break;
                }
            }
        }
    }

    /**
     * Act - do whatever the Tank wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!dead){
            GreenfootImage temp = (player?(new GreenfootImage(base)):(new GreenfootImage(enemyBase)));//96,92
            GreenfootImage tempGun = (player?(new GreenfootImage(baseGun[gun])):(new GreenfootImage(enemyGun[gun])));
            tempGun.rotate(gunRot);
            temp.drawImage(tempGun,0,0);
            setImage(temp);
            double s = Math.sin(Math.toRadians(getRotation()+gunRot));
            double c = Math.cos(Math.toRadians(getRotation()+gunRot));
            cg = new int[][]{{(int)(gunLoc[gun][0]*c-gunLoc[gun][1]*s),(int)(gunLoc[gun][1]*c+gunLoc[gun][0]*s)},
                {(int)(gunLoc[gun][0]*c-gunLoc[gun][1]*s*-1),(int)(gunLoc[gun][1]*c*-1+gunLoc[gun][0]*s)}};
            if(cd>0) cd--;
            if(pu>0) pu--;
        }
    }

    public void DIE(double dmg){
        hp-=dmg;
        hp=(hp<0?0:hp);
        hpBar.update((int)hp);
        if(hp==0){
            getWorld().removeObject(this);
        }
    }

    public void LIVE(int heal){
        hp+=heal;
        hp=(hp>hpMax?hpMax:hp);
        hpBar.update((int)hp);
    }

    public void SPEED(){
        pu = 150;
    }

    public void POWER(){
        MainWorld w = (MainWorld) getWorld();
        w.guns[player?0:1].update(1.0);
    }

    public void move(double s){
        super.move(s);
        for(Crate c: (java.util.ArrayList<Crate>) getIntersectingObjects(Crate.class)){
            if(isVA(c)){
                super.move(-s);
                return;
            }
        }
    }

    public void fmove(double s){
        super.move(s);
    }
    
    public void setRotation(int rotation){
        int sr = getRotation();
        super.setRotation(rotation);
        for(Crate c: (java.util.ArrayList<Crate>) getIntersectingObjects(Crate.class)){
            if(isVA(c)){
                super.setRotation(sr);
                return;
            }
        }
    }

    public void fsetRotation(int rotation){
        super.setRotation(rotation);
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

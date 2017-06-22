import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MainWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainWorld extends World
{
    public ProgressBar[] guns;
    public Tank[] tanks;
    public Reciever r;
    public Sender s;
    public static final int W = 0, A = 1, S = 2, D = 3, J = 4, L = 5, SPACE = 6, WIDTH = 900, HEIGHT = 600;
    public boolean ready,connected;
    public java.util.ArrayList<java.util.ArrayList<Boolean>> crates;
    /**
     * Constructor for objects of class MainWorld.
     * 
     */
    public MainWorld(Tank tank)
    {
        super(WIDTH, HEIGHT, 1, false);
        Tank.setUp();
        PickUp.setUp();
        Missile.setUp();
        Explosion.setUp();
        MiniExplosion.setUp();
        guns = new ProgressBar[2];
        tanks = new Tank[2];
        tank.makePlayer();
        tanks[0]=tank;
        setPaintOrder(ProgressBar.class, Fire.class, Explosion.class, MiniExplosion.class, HealthBar.class, MiniLaser.class, Laser.class, MiniLaserPointer.class, LaserPointer.class, Bullet.class, Missile.class, Rocket.class, Crate.class, Tank.class, PickUp.class, LandMine.class, Trail.class);

        prepare();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        guns[0] = new ProgressBar(100, 20, java.awt.Color.CYAN, java.awt.Color.GREEN);
        guns[1] = new ProgressBar(100, 20, java.awt.Color.BLUE, java.awt.Color.RED);
        addObject(guns[0], 62, 22);
        addObject(guns[1], WIDTH-62, 22);
        addObject(tanks[0], 110, HEIGHT/2);
        while(true){
            try{
                int port = (int)(Math.random()*(65536-49152+1))+49152;
                r = new Reciever(this,port);
                break;
            }catch(RuntimeException e){
                System.out.println("Port is occupied");
            }
        }
        ready = false;
        connected = false;
    }

    public void act(){
        if(!ready){
            setupConnection();
            ready = true;
        }
        else if(connected){
            char keys = getKeys();
            tanks[0].sendKeys(keys);
            s.sendKeys(keys);
            if(guns[0].getPercentage()<1){
                guns[0].update(guns[0].getPercentage()+0.01);
            }
            if(guns[1].getPercentage()<1){
                guns[1].update(guns[1].getPercentage()+0.01);
            }
            if(Greenfoot.getRandomNumber(300)==0){
                PickUp p = new PickUp(Greenfoot.getRandomNumber(PickUp.cnt));
                int x = Greenfoot.getRandomNumber(MainWorld.WIDTH/8*7)+MainWorld.WIDTH/16;
                int y = Greenfoot.getRandomNumber(MainWorld.HEIGHT/8*7)+MainWorld.HEIGHT/16;
                addObject(p,x,y);
                s.sendPickUp(p,x,y);
            }
        }
    }

    private void setupConnection(){
        while(true){
            try{
                String host = javax.swing.JOptionPane.showInputDialog("Input opponent's IP Address (Yours is "+r.getIP().split("/")[1]+")");
                int port;
                while(true){
                    try{
                        port = Integer.parseInt(javax.swing.JOptionPane.showInputDialog("Input opponent's Port (Yours is "+r.getPort()+")"));
                        break;
                    }catch(java.lang.NumberFormatException e){
                    }
                }
                s = new Sender(host,port);
                r.start();
                //s.sendCrates(crates); crates = new java.util.ArrayList<java.util.ArrayList<Boolean>>(); for(int i = 0; i < MainWorld.WIDTH/Crate.SIZE; i++){ crates.add(new java.util.ArrayList<Boolean>()); for(int j = 0; j < MainWorld.HEIGHT/Crate.SIZE; j++){ crates.get(i).add(Greenfoot.getRandomNumber(2)==1); } }
                s.sendTank(tanks[0]);
                break;
            }
            catch(java.lang.RuntimeException e){
            }
        }
    }

    public void connect(){
        connected=true;
    }

    public static char getKeys(){
        java.util.ArrayList<String> keys = new java.util.ArrayList<String>();
        char temp = 0;
        if(Greenfoot.isKeyDown("w")){
            temp+=1;
        }
        if(Greenfoot.isKeyDown("a")){
            temp+=1<<1;
        }
        if(Greenfoot.isKeyDown("s")){
            temp+=1<<2;
        }
        if(Greenfoot.isKeyDown("d")){
            temp+=1<<3;
        }
        if(Greenfoot.isKeyDown("j")){
            temp+=1<<4;
        }
        if(Greenfoot.isKeyDown("l")){
            temp+=1<<5;
        }
        if(Greenfoot.isKeyDown("space")){
            temp+=1<<6;
        }
        return temp;
    }
}

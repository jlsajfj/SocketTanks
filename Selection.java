import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Selection here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Selection extends World
{
    private int choice, time, st;
    private double dist, tt, tr;
    private Tank[] tanks;
    private PickATank pick;
    private SocketTanks logo;
    private GreenfootImage original, concrete, bg;
    private SimpleTimer timer;
    private GoBack goBack;
    private Start start;
    private boolean transition, transition1;
    /**
     * Constructor for objects of class Selection.
     * 
     */
    public Selection(int selection, GreenfootImage bg, int time)
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(MainWorld.WIDTH, MainWorld.HEIGHT, 1, false);
        setBackground(bg);
        choice = selection;
        tanks = new Tank[Tank.bgnum];
        pick = new PickATank();
        logo = new SocketTanks();
        for(int i = 0; i < Tank.bgnum; i++){
            tanks[i] = new Tank(1,i);
            addObject(tanks[i],MainWorld.WIDTH/(Tank.bgnum+1)*(i+1),375+(i%2==1?100:0));
        }
        addObject(logo, MainWorld.WIDTH/2, 150);
        addObject(pick, MainWorld.WIDTH/2, 560);
        original = new GreenfootImage(tanks[choice].getImage());
        concrete = new GreenfootImage("Concrete.jpg");
        this.time = time;
        st=96;
        dist = distance(tanks[choice].getX(),tanks[choice].getY(),MainWorld.WIDTH/2,MainWorld.HEIGHT/2);
        tt=255;
        tr=0;
        this.bg = bg;
        timer = new SimpleTimer();
        goBack = new GoBack();
        start = new Start();
        goBack.getImage().setTransparency(0);
        start.getImage().setTransparency(0);
        int spacing = 20;
        addObject(goBack,MainWorld.WIDTH/2-spacing-goBack.getImage().getWidth()/2, 560);
        addObject(start,MainWorld.WIDTH/2+spacing+start.getImage().getWidth()/2, 560);
        transition = false;
        transition1 = false;
    }

    public void act(){
        if(logo.getImage().getTransparency()>0&&time%2==0){
            logo.getImage().setTransparency(logo.getImage().getTransparency()-5);
            pick.getImage().setTransparency(pick.getImage().getTransparency()-5);
            for(Tank t: tanks){
                if(t==tanks[choice]) continue;
                t.getImage().setTransparency(t.getImage().getTransparency()-5);
            }
            logo.setLocation(logo.getX(),150+Math.sin(Math.toRadians(time*4))*5);
            pick.setLocation(pick.getX(),560+Math.sin(Math.toRadians(time*3)));
        }
        else if(logo.getImage().getTransparency()==0){
            start.setLocation(start.getX(),560+Math.sin(Math.toRadians(time*3)));
            goBack.setLocation(goBack.getX(),560+Math.sin(Math.toRadians(time*2)));
            if(tanks[choice].getImage().getHeight()<276){
                tanks[choice].setImage(new GreenfootImage(original));
                tanks[choice].getImage().scale(st,st-4);
                st++;
                tanks[choice].turnTowards(MainWorld.WIDTH/2,MainWorld.HEIGHT/2);
                tanks[choice].fmove(dist/184.0);
                tanks[choice].fsetRotation(0);
                tt-=255/184.0;
                bg.setTransparency((int)(tt+0.5));
                GreenfootImage temp2 = new GreenfootImage(128,128);
                temp2.drawImage(concrete,0,0);
                temp2.drawImage(bg,0,0);
                setBackground(temp2);
                timer.mark();
            }
            else{
                double millis = timer.millisElapsed();
                millis/=1000;
                tr+=millis*60;
                tanks[choice].fsetRotation((int)(tr+0.5));
                timer.mark();
                if(!transition&&start.getImage().getTransparency()<255&&time%2==1){
                    start.getImage().setTransparency(start.getImage().getTransparency()+5);
                    goBack.getImage().setTransparency(goBack.getImage().getTransparency()+5);
                }
                else if(start.getImage().getTransparency()==255){
                    if(Greenfoot.mouseClicked(goBack)){
                        transition=true;
                        bg=new GreenfootImage("Dirt.png");
                        bg.setTransparency(0);
                    }
                    if(Greenfoot.mouseClicked(start)){
                        transition1=true;
                        bg=new GreenfootImage("Dirt.png");
                        bg.setTransparency(0);
                    }
                }
            }
        }
        if(transition){
            if(start.getImage().getTransparency()>0&&time%2==0){
                start.getImage().setTransparency(start.getImage().getTransparency()-5);
                goBack.getImage().setTransparency(goBack.getImage().getTransparency()-5);
                tanks[choice].getImage().setTransparency(tanks[choice].getImage().getTransparency()-5);
                bg.setTransparency(bg.getTransparency()+5);
                GreenfootImage temp2 = new GreenfootImage(128,128);
                temp2.drawImage(concrete,0,0);
                temp2.drawImage(bg,0,0);
                setBackground(temp2);
            }
            if(start.getImage().getTransparency()==0){
                Greenfoot.setWorld(new Menu());
            }
        }
        if(transition1){
            if(start.getImage().getTransparency()>0&&time%2==0){
                start.getImage().setTransparency(tanks[choice].getImage().getTransparency()-5);
                goBack.getImage().setTransparency(tanks[choice].getImage().getTransparency()-5);
                tanks[choice].getImage().setTransparency(tanks[choice].getImage().getTransparency()-5);
                bg.setTransparency(bg.getTransparency()+5);
                GreenfootImage temp2 = new GreenfootImage(128,128);
                temp2.drawImage(concrete,0,0);
                temp2.drawImage(bg,0,0);
                setBackground(temp2);
            }
            if(start.getImage().getTransparency()==0){
                Greenfoot.setWorld(new MainWorld(new Tank(true, choice)));
            }
        }
        time++;
    }

    public double distance(int x1, int y1, int x2, int y2){
        int dx = x2-x1;
        int dy = y2-y1;
        return Math.sqrt(dx*dx+dy*dy);
    }
}

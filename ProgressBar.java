 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * A progress bar with the color format losely based of Jordan Cohen's buttons
 * 
 * @author Joey Ma
 * @version March 2017
 */
public class ProgressBar extends Actor
{
    // Declare variables
    private GreenfootImage myImage;
    private int width;
    private int height;
    private double percentage;
    private Color start;
    private Color end;
    private Color bg;

    /**
     * Create a progress bar with the specified width and height. Starts at red and ends at green.
     * @param width width of progress bar
     * @param height width of progress bar
     */
    public ProgressBar(int width, int height){
        this(width,height,Color.RED,Color.GREEN);
    }

    /**
     * Create a progress bar with the specified width, height, starting color and ending color.
     * @param width width of progress bar
     * @param height width of progress bar
     * @param start color when progress bar is at 0%
     * @param end color when progress bar is at 100%
     */
    public ProgressBar(int width, int height, Color start, Color end){
        this(width,height,start,end, Color.WHITE);
    }

    /**
     * Create a progress bar with the specified width, height, starting color, ending color and background color.
     * @param width width of progress bar
     * @param height width of progress bar
     * @param start color when progress bar is at 0%
     * @param end color when progress bar is at 100%
     * @param bg background color of the progress bar
     */
    public ProgressBar(int width, int height, Color start, Color end, Color bg){
        this.width = width;
        this.height = height;
        this.start=start;
        this.end=end;
        this.bg=bg;
        percentage=0;
        updatePic();
    }

    /**
     * Update the progress bar to a percentage between 0 and 100. Values below 0 will count as 0 and values above 100 with count as 100.
     * @param p percentage to update bar to
     */
    public void update(int p){
        percentage=p/100.0;
        percentage=Math.max(0,percentage);
        percentage=Math.min(1,percentage);
        updatePic();
    }

    /**
     * Update the progress bar to a percentage between 0 and 1. Values below 0 will count as 0 and values above 1 with count as 1.
     * @param p percentage to update bar to
     */
    public void update(double p){
        percentage=p;
        percentage=Math.max(0,percentage);
        percentage=Math.min(1,percentage);
        updatePic();
    }
    
    /**
     * Returns the percentage of the bar currently.
     * @return double Current percentage between 0 and 1.
     */
    public double getPercentage(){
        return percentage;
    }
    
    private void updatePic(){
        if((int)((width+4)*percentage)==0){
            myImage = new GreenfootImage(width + 8, height + 8);
            myImage.setColor (bg);
            myImage.fill();

            myImage.setColor (Color.BLACK);
            myImage.drawRect (0,0,width + 7, height + 7);
        }
        else{
            GreenfootImage tempImage = new GreenfootImage(Math.max(1,(int)((width+4)*percentage)),height+4);
            int p = (int)percentage;
            Color c = new Color((int)((start.getRed()*(1-p))+(end.getRed()*p)),   //Red
                                (int)((start.getGreen()*(1-p))+end.getGreen()*p), //Green
                                (int)((start.getBlue()*(1-p))+end.getBlue()*p),   //Blue
                                (int)((start.getAlpha()*(1-p))+end.getAlpha()*p));//Alpha
            tempImage.setColor(c);
            tempImage.fill();
            myImage = new GreenfootImage(width + 8, height + 8);
            myImage.setColor (bg);
            myImage.fill();
            myImage.drawImage (tempImage, 2, 2);

            myImage.setColor (Color.BLACK);
            myImage.drawRect (0,0,width + 7, height + 7);
        }
        setImage(myImage);
    }
}
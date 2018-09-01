package me.jameshunt.electricsmash;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by James on 9/15/2014.
 */
public class Tile {

    int x,y;
    int imageLocation;
    int imageLocationFire;
    //Bitmap fireball;
    int width,height;
    Paint p =new Paint();
    int drawDirection=0;
    int count=0;
    int moveSpeed=18; //18
    int howKilled=0;
    int lastCurrentX,lastCurrentY;
    int fade=1;
    int[] xValues;
    int[] yValues;
    boolean drawFireball=false;
    //int pixelNum=80;  //160 80
    boolean faded=false;
    int num;
    boolean updated=true;

    public Tile()
    {
        //this.fireball=fireball;
        lastCurrentX=0;
        lastCurrentY=8;
        imageLocationFire=1;
    }
    public Tile(int x, int y, int imageLocation,int width,int height,int[] xValues, int[] yValues)
    {
        this.x=x;
        this.y=y;
        this.imageLocation=imageLocation;
        this.width=width;
        this.height=height;
        lastCurrentX=0;
        lastCurrentY=8;
        this.xValues=xValues;
        this.yValues=yValues;
        imageLocationFire=1;
    }

    public void draw(Canvas c,Bitmap icons1)
    {
        if(icons1!=null&&icons1.isRecycled()==false) {

            int pixelNum = icons1.getWidth() / 8;

            if (imageLocation >= 0) {
                c.drawBitmap(icons1, new Rect(imageLocation * pixelNum - pixelNum, 0, imageLocation * pixelNum, pixelNum), new Rect(x, y, x + width / 8, y + width / 8), p);
            } else {
                c.drawBitmap(icons1, new Rect(num * pixelNum - pixelNum, 0, num * pixelNum, pixelNum), new Rect(x, y, x + width / 8, y + width / 8), p);
            }
        }
    }

    public void drawFireball(Canvas c,Bitmap fireball)
    {
        int pixelNum = fireball.getWidth() / 5;
        //c.drawBitmap(fireball, new Rect(imageLocationFire * 160 - 160, 0, imageLocationFire * 160, 160), new Rect(x-width/32, y-width/16, x + width/8+width/32, y+width/8), p);
        c.drawBitmap(fireball, new Rect(imageLocationFire * pixelNum - pixelNum, 0, imageLocationFire * pixelNum, pixelNum), new Rect(x-width/32, y-width/20, x + width/8+width/32, y+width/8), p);
    }

    public boolean drawFade(Canvas c,Bitmap icons1) //fade
    {

        int pixelNum = icons1.getWidth() / 8;

        Paint z = new Paint();
        //z.setAlpha(255-fade*20);
        //z.setAlpha(255-fade*8+1);
        z.setAlpha(255-fade*12);
        //z.setAlpha(255-fade*3);  //to see bug #2 clearly
        fade++;
        Rect h=new Rect(imageLocation * pixelNum - pixelNum, 0, imageLocation * pixelNum, pixelNum);
        Rect q=new Rect(x, y, x + width/8, y+width/8);

        c.drawBitmap(icons1, h, q, z);
        if(fade==21) {//81 to see bug #2 clearly //32,21
            fade=1;

            faded=true;
            return true;
        }
        else
            return false;
    }



    public boolean levelEndFade(Canvas c,Bitmap icons1) //fade
    {
        int pixelNum = icons1.getWidth() / 8;

        Paint z = new Paint();
        //z.setAlpha(255-fade*20);
        //z.setAlpha(255-fade*8+1);
        if(255-fade*12>13)
            z.setAlpha(255-fade*12);
        else
            z.setAlpha(0);
        //z.setAlpha(255-fade*3);  //to see bug #2 clearly
        fade++;
        Rect h=new Rect(imageLocation * pixelNum - pixelNum, 0, imageLocation * pixelNum, pixelNum);
        Rect q=new Rect(x, y, x + width/8, y+width/8);

        c.drawBitmap(icons1, h, q, z);
        if(fade==25) {//81 to see bug #2 clearly //32,21
            fade=1;

            faded=true;
            return true;
        }
        else
            return false;
    }

    public boolean levelStartFade(Canvas c,Bitmap icons1) //fade
    {
        int pixelNum = icons1.getWidth() / 8;

        Paint z = new Paint();
        //z.setAlpha(255-fade*20);
        //z.setAlpha(255-fade*8+1);
        int fadeNum=fade-4;
        if(fadeNum<0)
            fadeNum=0;

        if(0+fadeNum*12<242)
            z.setAlpha(0+fadeNum*12);
        else
            z.setAlpha(255);
        //z.setAlpha(255-fade*3);  //to see bug #2 clearly
        fade++;
        Rect h=new Rect(imageLocation * pixelNum - pixelNum, 0, imageLocation * pixelNum, pixelNum);
        Rect q=new Rect(x, y, x + width/8, y+width/8);

        c.drawBitmap(icons1, h, q, z);
        if(fade==25) {//81 to see bug #2 clearly //32,21
            fade=1;

            faded=true;
            return true;
        }
        else
            return false;
    }

    public boolean drawShuffle(Canvas c, Bitmap icons1)
    {
        if(drawDirection==1) //move block to left
        {
                x+=moveSpeed;
                draw(c,icons1);
                    return true;
        }

        else if(drawDirection==3)
        {
                x-=moveSpeed;
                draw(c,icons1);

                    return true;
        }
        else if(drawDirection==2)
        {
            //Log.d("2 ", lastCurrentY + "");
                y+=moveSpeed;
                draw(c,icons1);
            return true;

        }
        else if(drawDirection==4)
        {
            //Log.d("", lastCurrentY + "");

                y-=moveSpeed;
                draw(c,icons1);
                    return true;

        }

        //c.drawBitmap(icons, new Rect(imageLocation * 160 - 160, 0, imageLocation * 160, 160), new Rect(x, y, x + width/8, y+width/8), p);
        return false;
    }

    public boolean drawFingerMotion(Canvas c, Bitmap icons1)
    {

        for(int j=0;j<8;j++)
        {
            //if(Math.abs(x-xValues[lastCurrentX+1])<moveSpeed+1)
            if(x==xValues[j])
            {
                lastCurrentX=j;
                j=8;
            }
        }
        for(int i=8;i<16;i++)
        {
            //if(Math.abs(y-yValues[i])<3)
            if(y==yValues[i])
            {
                lastCurrentY=i;
                i=16;
            }
        }


            if(drawDirection==1) //move block to left
            {
                if(x!=xValues[lastCurrentX+1]) {
                    x+=moveSpeed;
                    draw(c,icons1);
                    if(Math.abs(x-xValues[lastCurrentX+1])<moveSpeed+1)
                    {
                        x=xValues[lastCurrentX+1];
                        drawDirection=0;

                        return true;
                    }

                }
            }

            else if(drawDirection==3)
            {
                if(x!=xValues[lastCurrentX-1]) {
                    x-=moveSpeed;
                    draw(c,icons1);
                    if(Math.abs(x-xValues[lastCurrentX-1])<moveSpeed+1)
                    {
                        x=xValues[lastCurrentX-1];
                        drawDirection=0;

                        return true;
                    }

                }
            }
            else if(drawDirection==2)
            {
                //Log.d("2 ", lastCurrentY + "");
                if(y!=yValues[lastCurrentY+1]) {
                    y+=moveSpeed;
                    draw(c,icons1);
                    if(Math.abs(y-yValues[lastCurrentY+1])<moveSpeed+1)
                    {
                        y=yValues[lastCurrentY+1];
                        drawDirection=0;

                        return true;
                    }

                }
            }
            else if(drawDirection==4)
            {
                //Log.d("", lastCurrentY + "");
                if(y!=yValues[lastCurrentY-1]) {
                    y-=moveSpeed;
                    draw(c,icons1);
                    if(Math.abs(y-yValues[lastCurrentY-1])<moveSpeed+1)
                    {
                        y=yValues[lastCurrentY-1];
                        drawDirection=0;

                        return true;
                    }

                }
            }

            //c.drawBitmap(icons, new Rect(imageLocation * 160 - 160, 0, imageLocation * 160, 160), new Rect(x, y, x + width/8, y+width/8), p);
        return false;


    }

}

package com.example.zach.spacestory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Zach on 12/3/2016.
 */

public class Player {
    final float MAXMIND = 100;
    final float MAXBODY = 100;
    final float MAXOXY = 100;
    float mind,body;
    float oxy;
    float id;
    boolean dead;
    Paint p;

    public Player(int mindAt, int bodyAt, int oxyAt, int idAt){
        oxy = oxyAt;
        mind = mindAt;
        body = bodyAt;
        dead = false;
        id = idAt;

        p = new Paint();
        p.setColor(Color.RED);
    }

    public void displayPlayer(Canvas c){
        int spacing = 70;
        p.setColor(Color.WHITE);
        p.setTextSize(40);
        c.drawText("Oxygen Levels = " + oxy, c.getWidth()/6,c.getHeight()/8 + 50, p);
        c.drawText("Mental Levels = " + body, c.getWidth()/6,c.getHeight()/8 + 50 + spacing, p);
        c.drawText("Physical Levels = " + mind, c.getWidth()/6,c.getHeight()/8 + 50 + (spacing*2), p);
    }


    public void upMind(int amount){mind+=amount;}
    public void upBody(int amount){body+=amount;}
    public void downMind(int amount){mind-=amount; if(mind <= 0){dead = true;}}
    public void downBody(int amount){body-=amount; if(body <= 0){dead = true;}}
    public void upOxy(int amount){if(oxy + amount <= 100){oxy+=amount;}}
    public void downOxy(int amount){oxy-=amount; if(oxy <= 0){dead = true;}}

    public float getMind() {return mind;}
    public float getBody() {return body;}
    public float getOxy() {return oxy;}
    public float getId(){return id;}
}

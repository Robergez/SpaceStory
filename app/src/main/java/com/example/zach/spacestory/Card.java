package com.example.zach.spacestory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Zach on 12/3/2016.
 */

public class Card {


    Paint p;
    Random r;
    String story;
    boolean event;
    int amount;
    boolean left, right, up, down;
    boolean swapColor;

    public Card() {
        event = false;
        story = "";
        amount = 11;
        left = false;
        right = false;
        up = false;
        down = false;

        p = new Paint();
        r = new Random();
        p.setColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        swapColor = false;


    }

    public void displayCard(Canvas c, int id){

        c.drawRect(0,0,c.getWidth(),c.getHeight(),p);
    }

    public void displayDeck(Canvas c, int[][] ary){
        int spacingW = c.getWidth()/ary[0].length;
        int spacingH = c.getHeight()/ary[0].length;

        for(int i = 0; i<ary[0].length; i++){
            for(int a = 0; a<ary[0].length; a++){
                if(ary[i][a] != -1){
                    c.drawRect(i*spacingW,a*spacingH,i*spacingW + spacingW,a*spacingH+spacingH,p);
                }
            }
        }
    }

    public String intro() {
        up = true;
        return "After a distress call from a mining crew. You fly to an asteroid and enter the drilling caves. It's dark and the light-strips have stopped functioning. A carnal, hungry force echoes from the darkness. You don't want to walk into the mines, but the call of the dangered mining team beckons you. You enter. Five steps. Then, the entrance of the cave collapses and you're trapped.";
    }

    public void drawCard(Canvas c) {

    }

    public String makeSelf(int id) {
        story = "";
        switch (id) {
            case 0:
                story = create0();
                break;
            case 1:
                story = create1();
                break;
            case 2:
                story = create2();
                break;
            case 3:
                story = create3();
                break;
            case 4:
                story = create4();
                break;
            case 5:
                story = create5();
                break;
            case 6:
                story = create6();
                break;
            case 7:
                story = create7();
                break;
            case 8:
                story = create8();
                break;
            case 9:
                story = create9();
                break;
            case 10:
                story = create10();
                break;
        }
        return story;
    }

    public String create0() {
        up = true;
        down = true;
        left = true;
        right = true;
        return " ";
    }

    public String create1() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "Something bad happens.";
    }

    public String create2() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "Something really bad happens.";
    }

    public String create3() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "Something so bad it made it into this game happens.";
    }

    public String create4() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "Something sticky and drippy on the walls.";
    }

    public String create5() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "It's dark.";
    }

    public String create6() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "It's so dark.";
    }

    public String create7() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "I have to write these individually.";
    }

    public String create8() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "There's no shortcut for storytelling (as he types in random words as a shortcut).";
    }

    public String create9() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "Ouch!.";
    }

    public String create10() {
        up = true;
        down = true;
        left = true;
        right = true;
        return "The last and final card (in the order, there's still probably more cards to draw.";
    }


    public String getStory() {
        return story;
    }

    public boolean getEvent() {
        return event;
    }

    public int getAmount() {
        return amount;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public boolean[] getDirections() {
        boolean[] temp = new boolean[4];
        temp[0] = up;
        temp[1] = down;
        temp[2] = left;
        temp[3] = right;
        return temp;
    }

    public void setSwapColor(boolean b){swapColor = b;}

}

class ColorKeeper{
    int r, g, b;

    public ColorKeeper(int nr, int ng, int nb){
        r = nr;
        g = ng;
        b = nb;
    }

    public int getR(){return r;}
    public int getG(){return g;}
    public int getB(){return b;}
}

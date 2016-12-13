package com.example.zach.spacestory;

/**
 * Created by Zach on 12/3/2016.
 */

public class Event {

    int eventId;

    public Event(){
        eventId = 0;
    }


    public void getEvent(Player p){
        switch(eventId){
            case 0:
                break;
            case 1:
                p.downMind(2);
                break;
            case 2:
                p.downBody(2);
                break;
            case 3:
                p.downOxy(50);
                break;
            case 4:
                p.upBody(4);
                break;
            case 5:
                p.upMind(4);
                break;
            case 6:
                p.upOxy(50);
                break;
            case 7:
                p.downMind(1);
                break;
            case 8:
                p.downMind(1);
                break;
            case 9:
                break;
            case 10:
                p.upBody(1);
                break;
        }
    }

    public void setEventId(int e){
        eventId = e;
    }
}

package com.example.zach.spacestory;

/**
 * Created by Zach on 11/29/2016.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Zach on 10/25/2016.
 */
public class MyView extends SurfaceView implements SurfaceHolder.Callback {

    protected final int TEXTDELAY = 30;
    protected Context context;

    DrawingThread thread;
    Paint text;
    Paint shapes;

    ArrayList<Player> players;
    Card cards;
    Event event;

    Bitmap ship;

    int[][]board;
    int playerX, playerY;

    int[] cardGrab;
    int delay,delayRun,canvasW,canvasH;
    int fontSize,textTimer,textItterator;
    int turn,directionChoose;
    int gameRun; // 0 = intro, 1 = game
    boolean gameStarted,textEnded,directions,resize;


    public MyView(Context ctx, AttributeSet attrs) {
        super(ctx,attrs);
        context = ctx;

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        text=new Paint();
        text.setTextAlign(Paint.Align.LEFT);
        text.setColor(Color.WHITE);
        text.setTextSize(24);

        shapes = new Paint();
        shapes.setColor(Color.BLACK);

        gameStarted = false;
        textEnded = false;
        directions = false;
        fontSize = 0;
        textTimer = 0;
        textItterator = 0;

        players = new ArrayList<Player>();
        players.add(new Player(5,5,100,1));
        cards = new Card();
        event = new Event();

        cardGrab = new int[cards.getAmount()];
        for(int i = 0; i<cardGrab.length; i++){
            cardGrab[i] = i;
        }

        turn = 1;
        directionChoose =-1;
        gameRun = 0;
        delay = TEXTDELAY;
        delayRun = 0;
        canvasH = 0;
        canvasW = 0;
        resize = false;

        board = new int[20][20];
        for(int i = 0; i<board[0].length; i++){
            for(int a = 0; a<board[0].length; a++){
                board[i][a] = -1;
            }
        }
        playerX = board.length/2;
        playerY = board[0].length/2;

        board[playerX][playerY] = 0;



    }

    public void customDraw(Canvas c) {
        shapes.setColor(Color.BLACK);
        c.drawRect(0,0,c.getWidth(),c.getHeight(),shapes);
        if(!resize){
            canvasW = c.getWidth();
            canvasH = c.getHeight();
            resizeImages(canvasW,canvasH);
            resize = false;
        }
        if(!gameStarted){
            fontSize = 50;
            text.setTextSize(fontSize);
            directions = true;
            Random r = new Random();
            c.drawBitmap(ship,c.getWidth()/2 - (ship.getWidth()/2) + r.nextInt(10), c.getHeight()/2 - (ship.getHeight()/2)+ r.nextInt(10),null);

            if(delay > 0) {
                drawSequenceString(cards.intro(), 50, 50, c);
                if(textEnded == true){
                    delay--;
                }
            }else {
                textItterator = 0;
                gameStarted = true;
                delay = TEXTDELAY;
                textEnded = false;
            }

        }else {
            takeTurn(turn,c);

        }
    }

    /** Game Methods
     *
     */

    public void takeTurn(int t,Canvas c){
        int p = 1;

        if(p == -1){

        }else{
            if(directions) {
                cards.displayDeck(c,board);
                displayDirections(c);
            }else{
                cards.displayCard(c,board[playerX][playerY]);
                if(delay > 0) {
                    drawSequenceString(cards.makeSelf(board[playerX][playerY]), 50, 50, c);
                    if(textEnded == true){
                        delay--;
                    }
                }else {
                    event.getEvent(players.get(0));
                    directions = true;
                    textItterator = 0;
                    delay = TEXTDELAY;
                    cards.setSwapColor(true);
                    takeTurn(t,c);
                }
            }

        }
    }

    public void displayDirections(Canvas c){
        directions = true;

        players.get(0).displayPlayer(c);

        shapes.setColor(Color.GRAY);
        boolean[] temp = new boolean[0];
        temp = cards.getDirections().clone();

        if(temp[0]){ // up
            c.drawRect(c.getWidth()/8,0,c.getWidth() - (c.getWidth()/8),c.getHeight()/8,shapes);
        }

        if(temp[1]){ // down
            c.drawRect(c.getWidth()/8,c.getHeight()-(c.getHeight()/8),c.getWidth() - (c.getWidth()/8),c.getHeight(),shapes);
        }

        if(temp[2]){ //left
            c.drawRect(0,c.getHeight()/8,c.getWidth()/8,c.getHeight()-(c.getHeight()/8),shapes);
        }

        if(temp[3]){ //right
            c.drawRect(c.getWidth() - (c.getWidth()/8),c.getHeight()/8,c.getWidth(),c.getHeight()-(c.getHeight()/8),shapes);
        }




    }

    /** Draw methods
     *
     */

    public void resizeImages(int w, int h){
        ship = decodeSampledBitmapFromResource(getResources(), R.drawable.spaceship, 100, 100);
    }
    public void drawSequenceString(String s, int x, int y, Canvas c){
        String[] breakDown = getLengths(s,c);
        String[] follow = new String[50];
        int spacing = 70;
        int newY = y;
        int crossPoint = (c.getHeight()-y)/spacing;
        if(y + (textItterator * spacing) > c.getHeight()){
            int far = textItterator-crossPoint;
            newY -= far * spacing;
        }

        if(textItterator < breakDown.length){
            if(textTimer < breakDown[textItterator].length()){
                textEnded = false;
                String n = segmentString(breakDown[textItterator],textTimer);
                c.drawText(n,x, newY + (textItterator * spacing),text);
                textTimer++;
            }else{
                follow[textItterator] = breakDown[textItterator];
                textItterator++;
                textTimer = 0;
            }
        }else{
            textEnded = true;
        }

        if(textItterator > 0){
            for(int i = 0; i<textItterator; i++){
                c.drawText(breakDown[i],x,newY + (i*spacing),text);
            }
        }
    }



    /** Math methods
     *
     */

    public boolean delay(int time){
        boolean temp = false;

        delayRun++;
        if(delayRun == time){
            temp = false;
            delayRun = 0;
        }


        return temp;
    }
    public int makeCard(){
        int temp = -1;
        Random r = new Random();
        temp = r.nextInt(cardGrab.length-1);
        event.setEventId(temp);
        return temp;
    }

    public String grabCard(){
        String s = "";
        Random r = new Random();
        int which = r.nextInt(cardGrab.length-1);
        s = cards.makeSelf(cardGrab[which]);

        int[] temp1 = new int[cardGrab.length-1];

        if(which != cardGrab.length-1) {
            for (int i = 0; i < cardGrab.length - 1; i++) {
                if (i >= which) {
                    cardGrab[i] = cardGrab[i + 1];
                }
            }
        }
        for(int i = 0;i<temp1.length; i++){
            temp1[i] = cardGrab[i];
        }
        cardGrab = new int[temp1.length];
        for(int i = 0; i<cardGrab.length; i++){
            cardGrab[i] = temp1[i];
        }

        return s;
    }

    public int getBounds(String s){
        int width = 0;
        for(int i = 0; i<s.length();i++){
            if(s.charAt(i) <= 'z' && s.charAt(i) >= 'a'){
                width+=fontSize/2;
            }else if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'){
                width+=fontSize;
            }else if(s.charAt(i) == ' '){
                width+=fontSize/2;
            }
        }

        return width;
    }

    public String[] getLengths(String s, Canvas c){
        String[] temp = new String[50];
        String run = "";
        int nextNull = 0;

        for(int i = 0; i<s.length(); i++){
            run += s.charAt(i);
            if(s.charAt(i) == ' ') {
                if (getBounds(run) > c.getWidth() - (fontSize * 2)) {
                    if (nextNull < temp.length) {
                        temp[nextNull] = run;
                        run = "";
                        nextNull++;
                    }
                }
            }else if (i == s.length() - 1) {
                temp[nextNull] = run;
                nextNull++;
            }
        }
        if(temp[0] == null){
            temp[0] = run;
        }

        String[] temp2 = new String[nextNull];
        for(int i = 0; i<temp2.length; i++){
            temp2[i] = temp[i];
        }

        return temp2;
    }
    public String segmentString(String s, int charsAt){
        if(charsAt < s.length()){
            String temp = "";
            for(int i = 0; i<charsAt; i++){
                temp+=s.charAt(i);
            }
            return temp;
        }else{
            return "String Index out of bounds error.";
        }
    }
    public Bitmap resizeBitmap(Bitmap b, int newWidth, int newHeight) {
        int w = b.getWidth();
        int h = b.getHeight();
        float scaleWidth = ((float) newWidth) / w;
        float scaleHeight = ((float) newHeight) / h;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                b, 0, 0, w, h, matrix, false);
        b.recycle();
        return resizedBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        boolean[] temp = new boolean[0];
        temp = cards.getDirections().clone();

        if(directions){
            if(y < canvasH/8 && temp[0]){
                directionChoose = 0;
                playerY--; // up
                if(board[playerX][playerY] == -1) {
                    board[playerX][playerY] = makeCard();
                }
                directions = false;
            }else if(y>= canvasH/8 && x <= canvasW/8 && temp[2]){
                directionChoose = 1;
                playerX--; // left
                if(board[playerX][playerY] == -1) {
                    board[playerX][playerY] = makeCard();
                }
                directions = false;
            }else if(y >= canvasH - (canvasH/8) && temp[1]){
                directionChoose = 2;
                playerY++; //down
                if(board[playerX][playerY] == -1) {
                    board[playerX][playerY] = makeCard();
                }
                directions = false;
            }else if(y>= canvasH/8 && x >= canvasW - (canvasW/8) && temp[3]){
                directionChoose = 3;
                playerX++; //right
                if(board[playerX][playerY] == -1) {
                    board[playerX][playerY] = makeCard();
                }
                directions = false;
            }
        }

        return true;
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
        boolean waitingForDeath = true;
        while(waitingForDeath) {
            try {
                thread.join();
                waitingForDeath = false;
            }
            catch (Exception e) {
                Log.v("Thread Exception", "Waiting on drawing thread to die: " + e.getMessage());
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread= new DrawingThread(holder, context, this);
        thread.setRunning(true);
        thread.start();
    }

    class DrawingThread extends Thread {
        private boolean running;
        private Canvas canvas;
        private SurfaceHolder holder;
        private Context context;
        private MyView view;

        private int FRAME_RATE = 30;
        private double delay = 1.0 / FRAME_RATE * 1000;
        private long time;

        public DrawingThread(SurfaceHolder holder, Context c, MyView v) {
            this.holder=holder;
            context = c;
            view = v;
            time = System.currentTimeMillis();
        }

        void setRunning(boolean r) {
            running = r;
        }

        @Override
        public void run() {
            super.run();
            while(running){
                if(System.currentTimeMillis() - time > delay) {
                    time = System.currentTimeMillis();
                    canvas = holder.lockCanvas();
                    if(canvas!=null){
                        view.customDraw(canvas);
                        holder.unlockCanvasAndPost(canvas);
                    }

                }
            }
        }



    }

    public int rollDie(){
        Random r = new Random();

        return r.nextInt(6)+1;
    }

    public int[] rollDice(int howMany){
        int[] temp = new int[howMany];
        Random r = new Random();

        for(int i = 0; i<howMany; i++){
            temp[i] = r.nextInt(6)+1;
        }

        return temp;
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


}
package me.jameshunt.electricsmash;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


public class GameGrid extends AppCompatActivity {

    //private SensorManager mSensorManager;
    //private float mAccel; // acceleration apart from gravity
    //private float mAccelCurrent; // current acceleration including gravity
    //private float mAccelLast; // last acceleration including gravity
    //private final SensorEventListener mSensorListener = new SensorEventListener() {

       /* public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };*/

    PlaceholderFragment fragInfo;
    final Handler handler = new Handler();
    /*static boolean goBack=false;
    static boolean startNew=false;*/
    //static Context sup;

    public void musicEnd() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                if(variables.goBack) {
                    continueMusic=true;
                    variables.keepsGoing=true;
                    variables.goBack=false;
                    //Intent myIntent = new Intent(GameGrid.this, MainActivity.class);
                    //GameGrid.this.startActivity(myIntent);
                    finish();

                    return;
                }
                else if(variables.startNew) {
                    continueMusic=true;
                    variables.keepsGoing=true;
                    variables.startNew=false;
                    Intent myIntent = new Intent(GameGrid.this, GameGrid.class);
                    myIntent.putExtra("new","0");
                    GameGrid.this.startActivity(myIntent);
                    finish();
                    return;

                }
                /*else if ((mAccel > 10||mAccel<-10)&&MusicManager.musicOn) {
                    /*Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken."+mAccel, Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("",mAccel+"");
                    //checkSong=(int)(Math.random() * range) + 0;
                    MusicManager.MUSIC++;
                    MusicManager.pause();
                    MusicManager.start(c, MusicManager.MUSIC,true);
                    if(MusicManager.MUSIC==9)
                        MusicManager.MUSIC=-1;
                    //MusicManager.MUSIC=MusicManager.MUSIC++;
                }*/

                //Do something after 200ms
                if(MusicManager.musicOn)
                MusicManager.check(GameGrid.this);
                musicEnd();
            }
        }, 200);
    }


    //Bundle extras = getIntent().getExtras();
    //Intent intent = getIntent();//
    boolean continueMusic;

    @Override
    public void onBackPressed() {
        continueMusic=false;
   // super.onBackPressed();
    variables.notFrontPageStillOpen=true;
        variables.keepsGoing=true;
        //Intent myIntent = new Intent(GameGrid.this, MainActivity.class);
        //GameGrid.this.startActivity(myIntent);
        finish();
    }

    @Override
    public void onResume ()
    {
        super.onResume();
        variables.keepsGoing=false;
        if(variables.closedFromNotMain)
        {
            variables.closedFromNotMain=false;
            Log.d("check1","reset");
        }
        //mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        continueMusic = false;
        musicEnd();
        if(MusicManager.musicOn)
        {
            MusicManager.start(this, MusicManager.MUSIC);
        }
    }


    @Override
    public void onPause ()
    {
        super.onPause();
        //mSensorManager.unregisterListener(mSensorListener);
        if (!continueMusic) {
            MusicManager.pause();

        }

    }


    @Override
    public void onStop ()
    {
        super.onStop();
        if(variables.keepsGoing==false)
        {
            variables.closedFromNotMain=true;
            variables.submitTime=true;
                variables.name = "timeInGame";

        }
        //sup=null;
        Log.d("check",variables.closedFromNotMain+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variables.notFrontPageStillOpen=true;
        //sup=getApplicationContext();
        /*mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;*/

        String value = getIntent().getStringExtra("new");
        ///if (extras != null) {


        //}

        if (MusicManager.restart&MusicManager.musicOn)
        {
            MusicManager.release();
            MusicManager.MUSIC=0;
            MusicManager.start(this, MusicManager.MUSIC,true);
        }

        setContentView(R.layout.activity_game_grid);

        if (savedInstanceState == null) {

            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            String myMessage = value;
            bundle.putString("message", myMessage );
            fragInfo = new PlaceholderFragment();
            fragInfo.setArguments(bundle);
            ft.replace(R.id.container, fragInfo);
            ft.commit();

            /*android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            PlaceholderFragment fragmentDemo = PlaceholderFragment.newInstance(value);
            ft.replace(R.id.container, fragmentDemo);
            ft.commit();*/


            /*getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();*/
        }
    }

    public PlaceholderFragment getFragInfo() {
        return fragInfo;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        String someInt="hi";
        private int wid,ht;
        DBAdapter myDb;
        DBAdapter2 myDb2;


        static Tile[][] gridTiles=new Tile[8][16];
        int points;
        int hMulti;
        int level=0;
        int levelProgress=0;
        int amountToSubtract=0;
        Cursor cursor;
        Cursor cursor2;
        boolean checkIfGame=false;
        boolean reallyGameOver=false;
        int sign=0;
        int shuffle;
        //static int shuffleAdd;
        int shuffleAddFromPoints;

        Bitmap icons1;
        Bitmap background;
        Bitmap resizedLogo;
        Bitmap resizedPattern;
        Bitmap playAgain;
        Bitmap homeButton;
        Bitmap reshuffle;
        //Bitmap icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
        Bitmap fireball;
        Bitmap explosion;
        Bitmap star;
        Bitmap bar;
        Bitmap gradientFlash;
        int bw; //bitmap width and height for logo
        int bh;
        double scaleFactor;
        int patternW;
        int levelDisplay=0;
        boolean first = true;
        Paint paint3 = new Paint();
        boolean levelComplete = false;
        static boolean newTileSet=true;
        static boolean startNewSaveThing=false;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Get back arguments
//            SomeInt= getArguments().getString("index");
            //variables.recyled=false;
            //icons1 = BitmapFactory.decodeResource(sup.getResources(), R.drawable.sprite);
            someInt = this.getArguments().getString("message");
            Log.d("something",""+someInt);
            variables.shuffleAdd=0;
            if(variables.signedIn)
            {
                sign=1;
            }
        }

        public DBAdapter getMyDb() {
            return myDb;
        }

        public DBAdapter2 getMyDb2() {
            return myDb2;
        }

        /*public static PlaceholderFragment newInstance(String valueforR) {
            PlaceholderFragment f = new PlaceholderFragment();


            Bundle args = new Bundle();
            args.putString("index",valueforR);
            f.setArguments(args);
            return f;

            /*Bundle args = new Bundle();
            args.putString("someInt", valueforR);
            fragmentDemo.setArguments(args);
            return fragmentDemo;
        }*/



        public PlaceholderFragment()
        {

        }

        @Override
        public void onStop()
        {

            variables.shuffleAdd=0;

            if(myDb.checkOpen()) {
                //variables.recyled=true;
                //myDb.deleteAll();
                if (checkIfGame == false) {

                } else {
                    if(startNewSaveThing==false) {
                        myDb.deleteDB();
                        Log.d("", "deleting database");
                    }
                    sign = 2;
                }

                cursor2 = myDb2.getAllRows();
                if (cursor2.moveToLast()) {
                    int highscore = cursor2.getInt(DBAdapter2.COL_HIGHSCORE);
                    int highMulti = cursor2.getInt(DBAdapter2.COL_HIGHMULTI);
                    shuffle += variables.shuffleAdd + shuffleAddFromPoints;
                    if (points > highscore || highscore == 0) {
                        if (hMulti > highMulti) {

                            myDb2.insertRow(points, level, points, levelProgress, hMulti, sign, shuffle);

                        } else {
                            myDb2.insertRow(points, level, points, levelProgress, highMulti, sign, shuffle);
                        }
                    } else {

                        if (hMulti > highMulti) {

                            myDb2.insertRow(points, level, highscore, levelProgress, hMulti, sign, shuffle);
                        } else {
                            myDb2.insertRow(points, level, highscore, levelProgress, highMulti, sign, shuffle);
                        }


                    }
                } else
                    myDb2.insertRow(points, level, points, levelProgress, hMulti, sign, shuffle);

                // myDb.close();
                // myDb2.close();

            }
            Log.d("stopped","stopped");

            background.recycle();
            resizedLogo.recycle();
            resizedPattern.recycle();
            icons1.recycle();
            gradientFlash.recycle();
            playAgain.recycle();
            homeButton.recycle();
            reshuffle.recycle();
            fireball.recycle();
            explosion.recycle();
            star.recycle();
            bar.recycle();

            super.onStop();
        }

        @Override
        public void onPause()
        {
            super.onPause();

            if(myDb.checkOpen()) {
                Log.d(checkIfGame+"",""+checkIfGame);
                //variables.recyled=true;
                if(checkIfGame==false) {

                }
                else {
                    if(startNewSaveThing==false) {
                        myDb.deleteDB();
                        Log.d("", "deleting database");
                    }
                    sign=2;
                }
                cursor2=myDb2.getAllRows();
                if(cursor2.moveToLast()) {
                    int highscore=cursor2.getInt(DBAdapter2.COL_HIGHSCORE);
                    int highMulti=cursor2.getInt(DBAdapter2.COL_HIGHMULTI);
                    shuffle+=variables.shuffleAdd+shuffleAddFromPoints;
                    if(points>highscore||highscore==0) {
                        if(hMulti>highMulti)
                        {

                            myDb2.insertRow(points, level, points, levelProgress,hMulti,sign,shuffle);
                        }
                        else
                        {
                            myDb2.insertRow(points, level, points, levelProgress,highMulti,sign,shuffle);
                        }
                    }
                    else {

                        if(hMulti>highMulti)
                        {

                            myDb2.insertRow(points, level, highscore, levelProgress,hMulti,sign,shuffle);
                        }
                        else
                        {
                            myDb2.insertRow(points, level, highscore,levelProgress,highMulti,sign,shuffle);
                        }


                    }
                }
                else
                    myDb2.insertRow(points, level, points,levelProgress,hMulti,sign,shuffle);

                //long newId =
                //myDb.close();
                //myDb2.close();

            }

        }

        @Override
        public void onResume ()
        {
            super.onResume();
                myDb.open();
                myDb2.open();

            /*icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
            background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg);
            resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo);
            resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_pattern);*/
            first=true;
            if(levelDisplay==0) {
                levelDisplay = 1;
                getLevelPics();
                levelDisplay=0;
            }
            else
                getLevelPics();
            first=false;

            playAgain = BitmapFactory.decodeResource(getResources(), R.drawable.playagain);
            homeButton = BitmapFactory.decodeResource(getResources(), R.drawable.home);
            reshuffle = BitmapFactory.decodeResource(getResources(), R.drawable.shufflepic);
            //Bitmap icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
            fireball = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
            explosion = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
            star = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            bar = BitmapFactory.decodeResource(getResources(), R.drawable.bar);
            gradientFlash = BitmapFactory.decodeResource(getResources(), R.drawable.gradient);
            //bw = resizedLogo.getWidth(); //bitmap width and height for logo
            //bh = resizedLogo.getHeight();





            background = Bitmap.createScaledBitmap(background, wid, ht, true);

            TypedValue outValue = new TypedValue();
            getResources().getValue(R.dimen.buttonSize, outValue, true);
            float value = outValue.getFloat();

            //homeButton=Bitmap.createScaledBitmap(homeButton, homeButton.getWidth()/5*4, homeButton.getHeight()/5*4, true);
            //playAgain = Bitmap.createScaledBitmap(playAgain,homeButton.getWidth()*2+20,playAgain.getHeight()/5*4,true);

            homeButton=Bitmap.createScaledBitmap(homeButton, intValue(homeButton.getWidth() / value), intValue(homeButton.getHeight()/value), true);
            reshuffle=Bitmap.createScaledBitmap(reshuffle, intValue(reshuffle.getWidth()/value), intValue(reshuffle.getHeight()/value), true);
            playAgain = Bitmap.createScaledBitmap(playAgain,intValue(playAgain.getWidth()/value),intValue(playAgain.getHeight()/value),true);
            bar = Bitmap.createScaledBitmap(bar,wid+wid/10,bar.getHeight()/2,true);
            gradientFlash = Bitmap.createScaledBitmap(gradientFlash,ht,ht,true);

            outValue = new TypedValue();
            getResources().getValue(R.dimen.sc2, outValue, true);
            scaleFactor = outValue.getFloat()/4.0;

            resizedLogo = Bitmap.createScaledBitmap(resizedLogo, intValue(bw * scaleFactor), intValue(bh * scaleFactor), true);
            resizedPattern = Bitmap.createScaledBitmap(resizedPattern, wid / 4, wid / 4, true);
            patternW = resizedPattern.getWidth(); //width for the pattern

        }


        public void getLevelPics()
        {
            if (levelComplete == true||first) {

                if(MusicManager.musicOn&&!first)
                {
                    MusicManager.MUSIC=level-amountToSubtract;
                    MusicManager.pause();
                    MusicManager.start(getActivity(), MusicManager.MUSIC,true);
                    if(MusicManager.MUSIC==9)
                    amountToSubtract=10;
                }


                if (levelDisplay % 6 == 0) {
                    background.recycle();
                    background=null;
                    background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_orange);
                    resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.bg_pattern_orange);
                    resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo_orange);
                    icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_orange);
                    paint3.setColor(Color.argb(192,144,121,72));
                } else if (levelDisplay % 5 == 0) {
                    background.recycle();
                    background=null;
                    background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_yellow);
                    resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.bg_pattern_yellow);
                    resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo_yellow);
                    icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_yellow);
                    paint3.setColor(Color.argb(192,185,190,106));
                } else if (levelDisplay % 4 == 0) {
                    background.recycle();
                    background=null;
                    background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_bw);
                    resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.bg_pattern_bw);
                    resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo_bw);
                    icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_bw);
                    paint3.setColor(Color.argb(192,120,128,125));
                } else if (levelDisplay % 3 == 0) {
                    background.recycle();
                    background=null;
                    background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_green);
                    resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.bg_pattern_green);
                    resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo_green);
                    icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_green);
                    paint3.setColor(Color.argb(192,37,121,94));
                } else if (levelDisplay % 2 == 0) {
                    background.recycle();
                    background=null;
                    background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_blue);
                    resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.bg_pattern);
                    resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo_blue);
                    icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_blue);
                    paint3.setColor(Color.argb(192,52,112,160));
                } else {
                    background.recycle();
                    background=null;
                    background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg);
                    resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_pattern);
                    resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo);
                    icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
                    paint3.setColor(Color.argb(192,52,112,160));
                }
                background = Bitmap.createScaledBitmap(background, wid, ht, true);
                resizedPattern = Bitmap.createScaledBitmap(resizedPattern, wid / 4, wid / 4, true);
                resizedLogo = Bitmap.createScaledBitmap(resizedLogo, intValue(bw * scaleFactor), intValue(bh * scaleFactor), true);
                System.gc();
            }
        }


        public int intValue(double num) {
            return (int) num;
        }

        private class DrawView extends View implements View.OnTouchListener {
            Paint paint = new Paint();
            Paint paint2 = new Paint();

            Paint paint4 = new Paint();
            //Context sup;

            MediaPlayer sound; //explosion
            MediaPlayer soundStar;
            MediaPlayer go;
            MediaPlayer gameOverSound;

            //double scaleFactor = 0.7; //scale factor for logo
            //double scaleFactor = getResources().getInteger(R.integer.sc) / 4.0;
           // double scaleFactor;
            //Bitmap resizedLogo;
            //Bitmap resizedPattern;
            //Bitmap resizedback;
            //int patternW; //width for the pattern

            boolean once = false;
            boolean onceOnce = false;
            boolean ONLYdOoNCE=false;

            int xGrid = 8;
            int yGrid = 16;
            int NUM_IMGS = 6;

            int touchY = 0;       //where your finger is
            int touchX = 0;

            boolean freezeInput = false;

            int touchYStart = 0;       //where your finger was when you first touched the screen
            int touchXStart = 0;


            int touchXGridStart, touchXGridEnd;
            int touchYGridStart, touchYGridEnd;

            Tile[] mouseMove = new Tile[2];


            //Tile[][] gridTiles=new Tile[8][16];
            int[] xValues = new int[8];
            int[] yValues = new int[16];

            /* int[] tileXCheck = new int[20];
             int[] tileYCheck = new int[20];*/
            int[] howKilled = new int[20];
            int check = 0;
            boolean finishFade = false;
            boolean checkForPointsFreeze = false;
            int moveBack = 0;
            boolean checkMoveBack = false;

            int[] numToFall = new int[8];
            int[] numToStartFall = new int[8];
            boolean[] finishFalling = new boolean[8];
            boolean[] generateTiles = new boolean[8];

            long secToNano = 1800000000; //1.8    //2.1 seconds for multiplier
            //long secToNano = 500000000;
            long startTime, checkTime;
            long pauseTime=0;
            boolean showTime=false;
            long hintTime;
            double barTime=(double)getResources().getInteger(R.integer.time);

            //int multiplier;
            int numForMulti = 1; //may need to change based on different phones


            int displayPointsOnTiles[][] = new int[8][8];

            Rect gridDraw;

            Rect src, dest;
            Rect srcExplosion, destExplosion;

            int fireNum, hyperNum,starNum = 0;
            int explodeX, explodeY = 40;
            int explosionNum = 0;


            int tillLevelEnd = 10;



            int pointsThisLevel = 0;
            int hScore;
            int newHighscoreNum=-1;

            //DBAdapter myDb;

            Paint mPaint;

            RadialGradient gradient;
            int alphaHint;
            boolean fadeAlpha;

            Shader shader;
            RectF rect; //for progress bar
            RectF rect2;

            int[] hintX=new int[30];
            int[] hintY=new int[30];
            boolean doHint=true;
            int increment;
            int increment2;
            int show;
            boolean doShuffle=false;
            int doShuffleNum=0;
            int flashX=-3000;
            int repsFlash=45000;
            boolean flashShuffle = true;

            boolean alreadySaved=false;

            boolean levelFadeIn=false;

            boolean sayGo=true;
            boolean goAlreadySaid=false;
            boolean gameOverAlreadySaid=false;

            double bannerTextSizeT= 2.25;
            double bannerTextSizeB=1.9;




            final Handler handler = new Handler();
            final Handler handler1 = new Handler();

            public int intValue(double num) {
                return (int) num;
            }



            public void gradientPosition() {

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(flashShuffle) {
                            repsFlash = 20;
                            flashX += getResources().getInteger(R.integer.flashSpeed);
                        }
                        if(flashX>2000) {
                            flashX = -3000;
                            repsFlash=45000;
                        }

                        invalidate(gridDraw);
                        gradientPosition();
                    }
                }, repsFlash);
            }

            public void fireUpdate() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        /*flashX+=300;
                        if(flashX>1000)
                            flashX=-1000;*/



                        if(newHighscoreNum>=1)
                        {
                            newHighscoreNum++;
                            if(newHighscoreNum>40) {
                                newHighscoreNum = 0;
                                variables.newHigh=true;

                            }
                        }

                        if(gradient!=null)
                        {
                            if(alphaHint==20)
                            {
                                fadeAlpha=false;
                            }
                            else if(alphaHint==140)
                            {
                                fadeAlpha=true;
                            }

                            if(fadeAlpha==true)
                            {
                                alphaHint-=10;
                            }
                            else
                                alphaHint+=10;
                        }

                        //Do something after 100ms
                        if (hyperNum < 7)
                            hyperNum++;
                        else
                            hyperNum = 1;


                        if (fireNum < 5)
                            fireNum++;
                        else
                            fireNum = 1;

                        if (starNum < 3)
                            starNum++;
                        else
                            starNum = 1;
                        invalidate(gridDraw);
                        fireUpdate();
                    }
                }, 100);
            }

            public DrawView(Context context) {
                super(context);
                setWillNotDraw(false);
                //sup = context;
                startNewSaveThing=false;
                /*levelDisplay=1;
                getLevelPics();
                first=false;
                levelDisplay=0;*/
                icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
                background = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg);
                resizedLogo = BitmapFactory.decodeResource(getResources(), R.drawable.es_logo);
                resizedPattern = BitmapFactory.decodeResource(getResources(), R.drawable.es_bg_pattern);
                playAgain = BitmapFactory.decodeResource(getResources(), R.drawable.playagainbtn);
                homeButton = BitmapFactory.decodeResource(getResources(), R.drawable.home);
                reshuffle = BitmapFactory.decodeResource(getResources(), R.drawable.gamecenter_icon);
                //Bitmap icons1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
                fireball = BitmapFactory.decodeResource(getResources(), R.drawable.fireball);
                explosion = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
                star = BitmapFactory.decodeResource(getResources(), R.drawable.star);
                bar = BitmapFactory.decodeResource(getResources(), R.drawable.bar);
                gradientFlash = BitmapFactory.decodeResource(getResources(), R.drawable.gradient);
                bw = resizedLogo.getWidth(); //bitmap width and height for logo
                bh = resizedLogo.getHeight();
                Point size = new Point();

                    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();




                try {
                    display.getSize(size);
                    wid = size.x;
                    ht=size.y;
                } catch (NoSuchMethodError e) {
                    ht = display.getHeight();
                    wid=display.getWidth();
                }


                //gridDraw=new Rect(0,intValue(ht/4.5),wid,intValue(ht/4.5+wid));
                //gridDraw=new Rect(0,intValue(ht/4.5),wid,intValue(ht/4.5+wid));
                gridDraw = new Rect(0, 0, wid, ht);

                //score=new Rect(0,intValue(ht / 5),intValue(wid/2.5),ht/4);

                //fireballRect=new Rect(x1,y1,x2,y2);
                //fireballRect=new Rect(0,0,wid,ht);
                //runnable.run();

                setOnTouchListener(this);

                background = Bitmap.createScaledBitmap(background, wid, ht, true);

                TypedValue outValue = new TypedValue();
                getResources().getValue(R.dimen.buttonSize, outValue, true);
                float value = outValue.getFloat();

                //homeButton=Bitmap.createScaledBitmap(homeButton, homeButton.getWidth()/5*4, homeButton.getHeight()/5*4, true);
                //playAgain = Bitmap.createScaledBitmap(playAgain,homeButton.getWidth()*2+20,playAgain.getHeight()/5*4,true);

                homeButton=Bitmap.createScaledBitmap(homeButton, intValue(homeButton.getWidth() / value), intValue(homeButton.getHeight()/value), true);
                reshuffle=Bitmap.createScaledBitmap(reshuffle, intValue(reshuffle.getWidth()/value), intValue(reshuffle.getHeight()/value), true);
                playAgain = Bitmap.createScaledBitmap(playAgain,intValue(playAgain.getWidth()/value),intValue(playAgain.getHeight()/value),true);
                bar = Bitmap.createScaledBitmap(bar,wid+wid/10,bar.getHeight()/2,true);
                gradientFlash = Bitmap.createScaledBitmap(gradientFlash,ht,ht,true);

                outValue = new TypedValue();
                getResources().getValue(R.dimen.sc2, outValue, true);
                scaleFactor = outValue.getFloat()/4.0;

                resizedLogo = Bitmap.createScaledBitmap(resizedLogo, intValue(bw * scaleFactor), intValue(bh * scaleFactor), true);
                resizedPattern = Bitmap.createScaledBitmap(resizedPattern, wid / 4, wid / 4, true);
                patternW = resizedPattern.getWidth(); //width for the pattern

                dest = new Rect(0, intValue(ht / 6.2), background.getWidth(), intValue(ht / 4.5));
                src = new Rect(0, intValue(background.getHeight() / 6.2), background.getWidth(), intValue(background.getHeight() / 4.5));
                rect2=new RectF(wid/2-intValue(wid/2.3),ht/6,wid/2+intValue(wid/2.3),intValue(ht/4)); //for "new high score"
                srcExplosion = new Rect();
                destExplosion = new Rect();
                fireUpdate();
                gradientPosition();
                paint.setColor(Color.WHITE);
                paint2.setColor(Color.DKGRAY);
                paint2.setAlpha(200);
                paint.setTextAlign(Paint.Align.CENTER);
                shader = new LinearGradient(0, intValue(ht / 4.55) - getResources().getInteger(R.integer.bar), 0, intValue(ht / 4.55) - getResources().getInteger(R.integer.bar) / 2, Color.rgb(255, 200, 250), Color.rgb(145, 76, 144), Shader.TileMode.CLAMP);
                paint3.setColor(Color.argb(192,52,112,160));
                paint4.setColor(Color.rgb(111,233,32));
                //openDB();
                myDb = new DBAdapter(this.getContext());
                myDb2 = new DBAdapter2(this.getContext());
                //myDb2.insertRow(0, 0, 0);
                for (int x = 0; x < 22; x++) {
                    hintX[x]=-1;
                    hintY[x]=-1;
                }

                    gradient= new RadialGradient(wid / 2, ht / 2,ht / 3, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.MIRROR);
                    mPaint = new Paint();
                    mPaint.setColor(Color.BLACK);
                    mPaint.setStrokeWidth(1);
                    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                    mPaint.setShader(gradient);


            }

            public DrawView(Context context, AttributeSet attrs) {
                super(context, attrs);
                paint.setColor(Color.BLACK);
                setOnTouchListener(this);
            }

            public DrawView(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
                paint.setColor(Color.BLACK);
                setOnTouchListener(this);
            }


            int roundUp(int n) {
                return (n + 9) / 10 * 10;
            }


            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                boolean checkForMorePoints = false;

                // Log.d("",hScore+"");

                if(background.isRecycled())
                return;

                canvas.drawBitmap(background, 0, 0, paint);
                canvas.drawRect(0, intValue(ht / 4.5 ),wid,intValue(ht / 4.5 + (patternW * 4)),paint3);
                canvas.drawBitmap(resizedLogo, wid / 2 - intValue(bw * scaleFactor / 2), ht / 60-resizedLogo.getHeight()/10, paint);
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        canvas.drawBitmap(resizedPattern, wid / 4 * x, intValue(ht / 4.5 + (patternW * y)), paint);
                    }
                }

                if (doShuffle == true) {
                    if (doShuffleNum == 0) {
                        for (int x = 0; x < xGrid; x++) {
                            for (int y = 8; y < yGrid; y++) {
                                int range = (4 - 1) + 1;
                                range=(int)(Math.random() * range) + 1;
                                gridTiles[x][y].drawDirection = range;
                                gridTiles[x][y].moveSpeed=getResources().getInteger(R.integer.fallSpeed)*7;
                            }
                        }
                    }
                    if (doShuffleNum < 15) {
                        doShuffleNum++;
                        for (int x = 0; x < xGrid; x++) {
                            for (int y = 8; y < yGrid; y++) {
                                gridTiles[x][y].drawShuffle(canvas,icons1);

                            }
                        }
                    } else {
                        if(doShuffleNum==15)
                        {
                            for (int i = 0; i < xGrid; i++) {
                                for (int j = 8; j < yGrid; j++) {
                                    int num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                    gridTiles[i][j].imageLocation=num_img;

                                    if (i > 1) {
                                        while (gridTiles[i - 2][j].imageLocation == num_img && gridTiles[i - 1][j].imageLocation == num_img) {
                                            num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                            gridTiles[i][j].imageLocation = (num_img);
                                        }
                                    }
                                    if (j > 9) {
                                        while (gridTiles[i][j - 2].imageLocation == num_img && gridTiles[i][j - 1].imageLocation == num_img) {
                                            num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                            gridTiles[i][j].imageLocation = (num_img);

                                            if (i > 1) {
                                                while (gridTiles[i - 2][j].imageLocation == num_img && gridTiles[i - 1][j].imageLocation == num_img) {
                                                    num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                                    gridTiles[i][j].imageLocation = (num_img);
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }

                        if(doShuffleNum<30)
                        {
                            doShuffleNum++;
                            for (int x = 0; x < xGrid; x++) {
                                for (int y = 8; y < yGrid; y++) {
                                    gridTiles[x][y].moveSpeed=-Math.abs(getResources().getInteger(R.integer.fallSpeed)*7);
                                    gridTiles[x][y].drawShuffle(canvas,icons1);

                                }
                            }
                        }
                        else {
                            for (int x = 0; x < xGrid; x++) {
                                for (int y = 8; y < yGrid; y++) {
                                    gridTiles[x][y].moveSpeed=getResources().getInteger(R.integer.fallSpeed);
                                    gridTiles[x][y].drawDirection=0;
                                }
                            }
                            doShuffleNum = 0;
                            doShuffle = false;
                            //once = false;
                            checkIfGame = false;
                            reallyGameOver = false;
                            freezeInput = false;
                            flashShuffle=true;
                        }
                    }
                } else {


                    if (once == false) {


                       /* if (ONLYdOoNCE == false) {
                            ONLYdOoNCE = true;
                            for (int i = 0; i < xGrid; i++) {
                                for (int j = 8; j < yGrid; j++) {
                                    gridTiles[i][j] = new Tile();
                                }
                            }
                        }*/
                        levelFadeIn=false;
                        doHint=false;
                        once = !once;
                        level++;
                        levelDisplay++;

                        if(levelDisplay>6)
                            levelDisplay=1;

                        getLevelPics();





                        levelComplete = false;


                        explosionNum = 0;
                        //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.


                        // Log.d("someInt",someInt);


                        if (someInt.equals("1")) {

                            if (onceOnce == false) {
                                cursor2 = myDb2.getRow();
                                if (cursor2.moveToFirst()) {
                                    hScore = cursor2.getInt(DBAdapter2.COL_HIGHSCORE);
                                    level = cursor2.getInt(DBAdapter2.COL_LEVEL);
                                    points = cursor2.getInt(DBAdapter2.COL_SCORE);
                                    levelProgress = cursor2.getInt(DBAdapter2.COL_PROGRESS);
                                    shuffle = cursor2.getInt(DBAdapter2.COL_SHUFFLE);
                                    //hMulti=cursor2.getInt(DBAdapter2.COL_HIGHMULTI);
                                }
                                onceOnce = true;
                            }
                            levelDisplay=level;
                            first=true;
                            getLevelPics();
                            first=false;

                            someInt = "hi";
                            cursor = myDb.getAllRows();

                            /*LoadGame.curse=cursor;
                            LoadGame.wid=wid;
                            LoadGame.xValues=xValues;
                            LoadGame.patternW=patternW;
                            LoadGame.ht=ht;
                            LoadGame.yValues=yValues;*/
                            variables.doneLoadingMap=false;

                            new LoadGame((GameGrid)getActivity(),cursor,xValues,yValues,wid,ht,patternW).execute();
                            /*if (cursor.moveToFirst()) {
                                do {
                                    // Process the data:
                                    //int id = cursor.getInt(DBAdapter.COL_ROWID);
                                    int xD = cursor.getInt(DBAdapter.COL_X);
                                    int yD = cursor.getInt(DBAdapter.COL_Y);
                                    int imageLocationD = cursor.getInt(DBAdapter.COL_IMAGELOCATION);
                                    int howKilledD = cursor.getInt(DBAdapter.COL_HOWKILLED);;


                                    //gridTiles[xD][yD].
                                    xValues[xD] = wid / 8 * xD;
                                    yValues[yD] = intValue(ht / 4.5 + (patternW * (yD - 8)) / 2);
                                    gridTiles[xD][yD] = new Tile(wid / 8 * xD, intValue(ht / 4.5 + (patternW * (yD - 8)) / 2), imageLocationD, wid, ht, xValues, yValues);
                                    gridTiles[xD][yD].howKilled = howKilledD;
                                } while (cursor.moveToNext());

                                myDb.deleteAll();
                            }*/
                        } else {
                            variables.newHigh=false;
                            cursor2 = myDb2.getRow();
                            if (cursor2.moveToFirst()) {
                                hScore = cursor2.getInt(DBAdapter2.COL_HIGHSCORE);
                                shuffle = cursor2.getInt(DBAdapter2.COL_SHUFFLE);
                            }

                            for (int i = 0; i < xGrid; i++) {
                                for (int j = 8; j < yGrid; j++) {
                                    int num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                    xValues[i] = wid / 8 * i;
                                    yValues[j] = intValue(ht / 4.5 + (patternW * (j - 8)) / 2);
                                    gridTiles[i][j] = new Tile(wid / 8 * i, intValue(ht / 4.5 + (patternW * (j - 8)) / 2), num_img, wid, ht, xValues, yValues);
                                /*gridTiles[i][j].x=wid / 8 * i;
                                gridTiles[i][j].y=intValue(ht / 4.5 + (patternW * (j - 8)) / 2);
                                gridTiles[i][j].imageLocation=num_img;
                                gridTiles[i][j].width=wid;
                                gridTiles[i][j].height=ht;
                                gridTiles[i][j].xValues=xValues;
                                gridTiles[i][j].yValues=yValues;
                                gridTiles[i][j].howKilled=0;*/

                                    if (i > 1) {
                                        while (gridTiles[i - 2][j].imageLocation == num_img && gridTiles[i - 1][j].imageLocation == num_img) {
                                            num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                            gridTiles[i][j].imageLocation = (num_img);
                                        }
                                    }
                                    if (j > 9) {
                                        while (gridTiles[i][j - 2].imageLocation == num_img && gridTiles[i][j - 1].imageLocation == num_img) {
                                            num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                            gridTiles[i][j].imageLocation = (num_img);

                                            if (i > 1) {
                                                while (gridTiles[i - 2][j].imageLocation == num_img && gridTiles[i - 1][j].imageLocation == num_img) {
                                                    num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                                    gridTiles[i][j].imageLocation = (num_img);
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                            variables.doneLoadingMap=true;
                        }
                        //space=Math.abs(gridTiles[0][0].x-gridTiles[1][0].x);


                        for (int i = 0; i < 8; i++) {
                            numToStartFall[i] = 40;
                            finishFalling[i] = true;
                        }

                        tillLevelEnd = level * 3 + 20;
                       // tillLevelEnd = level * 0 + 3;

                        newTileSet=true;

//                        Log.d("",gridTiles[7][15].imageLocation+" nullcrapsave");
                        if(variables.currentlySavingMap==false) {

                            while(gridTiles[7][15]==null)
                            {
                              //  Log.d("","saving game - top"+gridTiles[0][8]+"           "+gridTiles[7][15]);

                            }
                           // Log.d("","saving game");
                            new SaveGame((GameGrid)getActivity()).execute();
                        }

                    }

                    //Log.d("sup",gridTiles[0][0].x+" "+gridTiles[0][0].y);

                    checkTime = System.nanoTime();

                    if (startTime + secToNano < checkTime) {
                        numForMulti = 1;
                        showTime = false;
                    } else if (hintTime != 0)
                        hintTime = System.nanoTime();


                    //Log.d("multiplier",multiplier+"");


                    //paint.setTextSize(60);


                    // int allFall = 0;
                    if(levelFadeIn==false)
                    { freezeInput=true;
                        for (int x = 0; x < 8; x++) {
                            for (int y = 8; y < 16; y++) {
                                if(gridTiles[x][y].levelStartFade(canvas,icons1)&&(x==7&&y==15))
                                {
                                    levelFadeIn=true;
                                   // sayGo=false;
                                }
                            }
                        }

                        if(sayGo)
                        {
                            //TODO
                            //do popup and sound
                            if(go==null&&goAlreadySaid==false)
                                go = MediaPlayer.create(this.getContext(), R.raw.go);
                            if(go!=null&&!go.isPlaying()&&goAlreadySaid==false) {
                                goAlreadySaid=true;
                                go.start();
                                Log.d("GO sound","played");
                            }

                            canvas.drawRect(0, intValue(ht / bannerTextSizeT), wid, intValue(ht / bannerTextSizeB), paint2);
                            paint.setStyle(Paint.Style.STROKE);
                            canvas.drawRect(1, intValue(ht / bannerTextSizeT), wid-1, intValue(ht / bannerTextSizeB), paint);
                            paint.setStyle(Paint.Style.FILL);
                            paint.setTextSize(getResources().getDimension(R.dimen.text_size) * 2);
                            canvas.drawText("GO", wid / 2, intValue(ht / bannerTextSizeB) - paint.getTextSize()/3, paint);

                        }
                        else if(go!=null&&go.isPlaying()==false)
                        {
                            go.reset();
                            go.release();
                            go=null;
                        }

                        if(levelFadeIn==false)
                            return;

                            freezeInput=false;
                    }




                    for (int x = 0; x < 8; x++) {
                        for (int y = 8; y < 16; y++) {

                            if (gridTiles[x][y] == null)
                                Log.d("null", x + " " + y);

                            if (gridTiles[x][y].drawFireball) {
                                gridTiles[x][y].imageLocationFire = fireNum;
                                gridTiles[x][y].drawFireball(canvas,fireball);
                            }


                            if (gridTiles[x][y].drawDirection != 0) {
                                if (gridTiles[x][y].drawFingerMotion(canvas,icons1)) {
                                    gridTiles[x][y].drawDirection = 0;


                                    if (x == touchXGridStart && y == touchYGridStart && gridTiles[x][y].howKilled == 3) {

                                        for (int a = 0; a < 8; a++) {
                                            for (int b = 8; b < 16; b++) {
                                                if (gridTiles[a][b].imageLocation == gridTiles[touchXGridEnd][touchYGridEnd].imageLocation) {
                                                    gridTiles[a][b].howKilled = 1;
                                                    numToFall[x] = 0;
                                                    numToStartFall[x] = 40;
                                                }
                                            }
                                        }
                                        gridTiles[x][y].howKilled = 1;
                                    } else if (x == touchXGridEnd && y == touchYGridEnd && gridTiles[x][y].howKilled == 3) {

                                        for (int a = 0; a < 8; a++) {
                                            for (int b = 8; b < 16; b++) {
                                                if (gridTiles[a][b].imageLocation == gridTiles[touchXGridStart][touchYGridStart].imageLocation) {
                                                    gridTiles[a][b].howKilled = 1;
                                                    numToFall[x] = 0;
                                                    numToStartFall[x] = 40;
                                                }
                                            }
                                        }
                                        gridTiles[x][y].howKilled = 1;
                                    }
                                    if (mouseMove[0] == null)
                                        mouseMove[0] = gridTiles[touchXGridEnd][touchYGridEnd];
                                    else if (mouseMove[1] == null)
                                        mouseMove[1] = gridTiles[touchXGridStart][touchYGridStart];


                                }
                                //System.out.println("one");
                                invalidate(gridDraw);
                            } else if (gridTiles[x][y].howKilled != 0 && levelComplete == false) {
                                //System.out.print(gridTiles[x][y].getHowKilled());
                                // allFall--;

                                if (gridTiles[x][y].howKilled == 1) {
                                    gridTiles[x][y].drawFireball = false;
                                }

                                if (gridTiles[x][y].howKilled == 2) {
                                    //System.out.print("fireball");
                                    gridTiles[x][y].drawFireball = true;
                                    //gridTiles[x][y]=(Fireball)gridTiles[x][y];
                                    //gridTiles[x][y].howKilled=0;
                                    //Log.d("fire","");
                                    // allFall += 2;
                                    gridTiles[x][y].draw(canvas,icons1);
                                    //gridTiles[x][y].drawFireball(canvas);
                                    //invalidate(new Rect(x * gridTiles[x][y].getSize(), intValue(y * wid/8 + ht / 4.5), x * wid/8 + wid/8, intValue(y * wid/8 + wid/8 + ht / 4.5)));
                                } else if (gridTiles[x][y].howKilled == 3) {
                                    gridTiles[x][y].imageLocation = -1;
                                    //gridTiles[x][y].imageLocation=hyperNum;
                                    gridTiles[x][y].num = hyperNum;
                                    // allFall += 2;
                                    gridTiles[x][y].draw(canvas,icons1);
                                    //gridTiles[x][y].draw(canvas);
                                    System.out.print("hypercube");
                                } else if (gridTiles[x][y].howKilled == 4) {
                                    //x5 thing
                                    gridTiles[x][y].draw(canvas,icons1);
                                    //canvas.drawBitmap(star,gridTiles[x][y].x,gridTiles[x][y].y,paint);
                                    //canvas.drawBitmap(star,new Rect(starNum * 80 - 80, 0, starNum * 80, 80),new Rect(gridTiles[x][y].x-wid/32, gridTiles[x][y].y-wid/20, gridTiles[x][y].x + wid/8+wid/32, gridTiles[x][y].y+wid/8),paint);
                                    int pixelPerTile = star.getWidth() / 3;
                                    canvas.drawBitmap(star, new Rect(starNum * pixelPerTile - pixelPerTile, 0, starNum * pixelPerTile, pixelPerTile), new Rect(gridTiles[x][y].x + wid / 64, gridTiles[x][y].y + wid / 64, gridTiles[x][y].x + wid / 8 - wid / 64, gridTiles[x][y].y + wid / 8 - wid / 64), paint);
                                } else if (gridTiles[x][y].drawFade(canvas,icons1)) {
                                    //Log.d("hi",gridTiles[x][y].getHowKilled()+"");
                                    gridTiles[x][y].howKilled = 0;
                                    gridTiles[x][y].imageLocation = 0;
                                    //gridTiles[x][y]=null;
                                    checkForPointsFreeze = false;
                                    invalidate(gridDraw);
                                    finishFade = true;
                                    numToFall[x]++;
                                    if (numToStartFall[x] == 40) {
                                        numToStartFall[x] = y;
                                    } else if (y > numToStartFall[x])
                                        numToFall[x]--;

                                    finishFalling[x] = false;

                                    //Log.d("verb"," "+numToStartFall[x]);



                                /*if(startTime+secToNano>checkTime)
                                {
                                    if(multiplier==0)
                                        multiplier++;

                                    multiplier++;
                                }
                                else
                                    multiplier=0;*/
                                    points += 10 * (numForMulti);
                                    pointsThisLevel += 10 * (numForMulti);
                                    shuffleAddFromPoints = points / 50000;

                                    showTime = true;
                                    startTime = System.nanoTime();

                                } else {
                                    displayPointsOnTiles[x][y - 8] = roundUp(10 * numForMulti);
                                    canvas.drawText("+" + displayPointsOnTiles[x][y - 8], gridTiles[x][y].x + wid / 16, gridTiles[x][y].y + wid / 12, paint);
                                    startTime = System.nanoTime();
                                    pauseTime = System.nanoTime();
                                    invalidate(gridDraw);

                                }


                                //System.out.println("two");
                            } else if (gridTiles[x][y].howKilled != 0 && levelComplete == true) {

                                explodeX=40;
                                explodeY=40;

                                if (gridTiles[x][y].levelEndFade(canvas,icons1))
                                    once = false;
                                freezeInput = true;
                                pointsThisLevel = 0;
                                levelProgress = 0;
                                goAlreadySaid=false;
                                //sayGo=true;

                            } else {
                                //if (gridTiles[x][y].y == yValues[y])
                                // allFall++;
                                gridTiles[x][y].draw(canvas,icons1);
                            }
                        }
                    }
                    //Log.d("",""+allFall);
                    if ((levelProgress >= tillLevelEnd) || levelComplete == true) {

                        if (levelComplete != true) {
                            NUM_IMGS = 6;
                            mouseMove[0] = null;
                            mouseMove[1] = null;
                            levelComplete = true;
                            for (int x = 0; x < 8; x++) {
                                for (int y = 0; y < 8; y++) {
                                    gridTiles[x][y] = null;
                                }
                            }
                        } else {
                            for (int x = 0; x < 8; x++) {
                                for (int y = 8; y < 16; y++) {
                                    if (gridTiles[x][y].howKilled == 1)
                                        points += 10 * (numForMulti);
                                    if (displayPointsOnTiles[x][y - 8] != 0) {
                                        paint.setTextSize(getResources().getDimension(R.dimen.text_size) / 3 * 2);
                                        paint.setTextAlign(Paint.Align.CENTER);
                                        canvas.drawText("+" + displayPointsOnTiles[x][y - 8], gridTiles[x][y].x + wid / 16, gridTiles[x][y].y + wid / 12, paint);
                                    }

                                    gridTiles[x][y].howKilled = 5;
                                    gridTiles[x][y].drawFireball = false;

                                }
                            }
                            paint.setTextSize(getResources().getDimension(R.dimen.text_size));
                        }

                        paint2.setColor(Color.DKGRAY);
                        paint2.setAlpha(200);
                        canvas.drawRect(0, intValue(ht / bannerTextSizeT), wid, intValue(ht / bannerTextSizeB), paint2);
                        paint.setStyle(Paint.Style.STROKE);
                        canvas.drawRect(1, intValue(ht / bannerTextSizeT), wid-1, intValue(ht / bannerTextSizeB), paint);
                        paint.setStyle(Paint.Style.FILL);
                        paint.setTextSize(getResources().getDimension(R.dimen.text_size) * 2);
                        canvas.drawText("Level Complete", wid / 2, intValue(ht / bannerTextSizeB) - paint.getTextSize() / 3, paint);
                        paint.setTextSize(getResources().getDimension(R.dimen.text_size));


                    } else {

                        if (pointsThisLevel > 2000) {
                            NUM_IMGS = 8;
                        } else if (pointsThisLevel > 1000) {
                            NUM_IMGS = 7;
                        } else {
                            NUM_IMGS = 6;
                        }


                        for (int x = 0; x < 8; x++) {
                            for (int y = 8 - numToFall[x] - 1; y < 8; y++)
                                if (generateTiles[x] == false || gridTiles[x][y] == null) {
                                    int num_img = (int) Math.ceil(Math.random() * NUM_IMGS);
                                    gridTiles[x][y] = new Tile(wid / 8 * x, intValue(ht / 4.5 + (patternW * (y - 8)) / 2), num_img, wid, ht, xValues, yValues);
                                    gridTiles[x][y].moveSpeed = getResources().getInteger(R.integer.fallSpeed);
                                    generateTiles[x] = true;
                                } else if (gridTiles[x][y] != null) {
                                    if (gridTiles[x][y].y > intValue(ht / 4.5) - wid / 8 + 1)
                                        gridTiles[x][y].draw(canvas,icons1);
                                }
                        }


                        canvas.drawBitmap(background, src, dest, paint);

                        paint.setTextAlign(Paint.Align.RIGHT);
                        paint.setTextSize(getResources().getDimension(R.dimen.text_size) / 5 * 4);
                        if (points > hScore) {
                            if (newHighscoreNum == -1&&variables.newHigh==false)
                                newHighscoreNum = 1;
                            hScore = points;
                        }
                        canvas.drawText("High Score: " + hScore, wid-wid/50, intValue(ht / 6.5), paint);
                        canvas.drawText("Level: " + level, wid-wid/50, intValue(ht / 5.3), paint);
                        paint.setTextAlign(Paint.Align.LEFT);
                        //paint.setTextSize(getResources().getDimension(R.dimen.text_size));

                        //canvas.drawText(multiplier+"", 10, 60, paint);
                        //canvas.drawText((numForMulti)+"", 300, 60, paint);
                        canvas.drawText("Score: " + points, 10, intValue(ht / 6.5), paint);

                        canvas.drawText("Shuffles: " + (shuffle+variables.shuffleAdd+shuffleAddFromPoints), 10, intValue(ht / 5.3), paint);




                        //canvas.drawRect(10,intValue(ht / 4.5)-60,intValue(wid/2.5),intValue(ht / 4.5),paint);

                        //paint.setTextSize(40);
                        paint.setTextSize(getResources().getDimension(R.dimen.text_size) / 3 * 2);
                        paint.setTextAlign(Paint.Align.CENTER);


                        //Paint paint = new Paint();
                        paint.setShader(shader);
                        //canvas.drawRect(new RectF(0, 0, 100, 40), paint);
                        rect = new RectF(-20, intValue(ht / 4.53) - getResources().getInteger(R.integer.bar), (int) (levelProgress / (double) tillLevelEnd * wid), intValue(ht / 4.53));
                        //Log.d("progress",levelProgress+" "+tillLevelEnd);
                        canvas.drawRoundRect(rect, 5, 21, paint);

                        paint.setShader(null);

                        canvas.drawBitmap(bar,-20,intValue(ht/4.57),paint);
                        canvas.drawBitmap(bar,-20,intValue(ht/4.52)+resizedPattern.getHeight()*4,paint);

                        if (newHighscoreNum >= 1) {
                            //Log.d("",""+newHighscoreNum);
                            paint.setTextAlign(Paint.Align.CENTER);
                            paint.setTextSize(getResources().getDimension(R.dimen.text_size) * 2);
                            canvas.drawRoundRect(rect2, 40, 40, paint2);
                            canvas.drawText("New High Score!", wid / 2, intValue(ht / 4.4), paint);
                            //variables.newHigh=true;
                        }

                        paint.setTextSize(getResources().getDimension(R.dimen.text_size) / 3 * 2);
                        canvas.drawBitmap(gradientFlash,flashX,0,paint);

                        if (numForMulti > 1 || showTime) {
                            //paint.setColor(Color.rgb(255,0,0));
                            canvas.drawRect(intValue((double) wid / 2 - ((double) (startTime + secToNano - checkTime) / barTime)), intValue(ht / 5.5), intValue((double) wid / 2 + ((double) (startTime + secToNano - checkTime) / barTime)), intValue(ht / 5.5 + 10), paint4);
                            //paint.setColor(Color.WHITE);
                        }
                        else if(increment<=3&&increment!=0)
                        {
                            canvas.drawText(increment+" moves left!", wid / 2, intValue(ht/5.3), paint);
                        }
                        if (hintTime + secToNano < checkTime && hintTime != 0 && doHint && reallyGameOver == false) {
                            checkHint(true);
                            doHint = false;

                            //else
                            // Log.d("blah",hintX[show]+" " +hintY[show]);
                            // Log.d("blah2",increment+"");
                        }

                        if (doHint == false) {
                            if (increment != increment2) {
                                show = (int) Math.ceil(Math.random() * (increment - 1));
                                increment2 = increment;
                            }
                            //Log.d("gradient","gradient");
                            if (hintX[show] != -1) {
                                gradient = new RadialGradient(xValues[hintX[show]] + wid / 16, yValues[hintY[show]] + wid / 16, wid / 16, Color.TRANSPARENT, Color.argb(alphaHint, 0, 221, 255), Shader.TileMode.MIRROR);

                                //canvas.drawRect(xValues[hintX[show]], yValues[hintY[show]], xValues[hintX[show]] + wid / 8, yValues[hintY[show]] + wid / 8, paint2);
                                mPaint.setShader(gradient);
                                canvas.drawCircle(xValues[hintX[show]] + wid / 16, yValues[hintY[show]] + wid / 16, wid / 8, mPaint);
                            }
                        }


                        if (explodeX != 40 && explodeY != 40) {
                            //int left, int top, int right, int bottom

                            explosionDraw(canvas);

                        }
                        else if(sound!=null&&sound.isPlaying()==false)
                        {
                            sound.reset();
                            sound.release();
                            sound=null;
                        }

                        if(soundStar!=null&&soundStar.isPlaying()==false)
                        {
                            soundStar.reset();
                            soundStar.release();
                            soundStar=null;
                        }


                        if(variables.doneLoadingMap)
                        for (int x = 0; x < 8; x++) {
                            for (int y = 15; y >= 8; y--) {
                                if (gridTiles[x][y].imageLocation == 0 && finishFalling[x] == true) {
                                    numToFall[x]++;
                                    if (numToStartFall[x] == 40) {
                                        numToStartFall[x] = y;
                                    } else if (y > numToStartFall[x])
                                        numToFall[x]--;

                                    finishFalling[x] = false;
                                }
                            }

                            //System.out.print(numToStartFall[x]+" ");
                            if (numToStartFall[x] != 40 && finishFalling[x] == false) {
                                for (int y = numToStartFall[x] - 1; y > 8 - numToFall[x] - 1; y--) {

                                    if (gridTiles[x][y] != null) {
                                        //gridTiles[x][y].y++;
                                        gridTiles[x][y].y += gridTiles[x][y].moveSpeed;

                                    }

                                    //if (gridTiles[x][y] != null && gridTiles[x][y].y == yValues[numToStartFall[x] + numToFall[x] - 1]) {
                                    //if(Math.abs(x-xValues[lastCurrentX+1])<moveSpeed+1)
                                    while (numToStartFall[x] + numToFall[x] - 1 >= 16)
                                        numToFall[x]--;

                                    if (gridTiles[x][y] != null && Math.abs(gridTiles[x][y].y - yValues[numToStartFall[x] + numToFall[x] - 1]) < gridTiles[x][y].moveSpeed + 1) {
                                        finishFalling[x] = true;
                                        generateTiles[x] = false;
                                /*mouseMove[0] = null;
                                mouseMove[1] = null;*/
                                        //gridTiles[x][y+numToFall[x]].imageLocation=gridTiles[x][y].imageLocation;
                                /*Log.d("h",y+numToFall[x]+" ");
                                Log.d("h",numToFall[x]+" ");*/
                                        int z = 0;
                                        while (gridTiles[x][y - z] != null) {
                                            //gridTiles[x][y-z].y--;
                                            gridTiles[x][y - z].y = yValues[numToStartFall[x] + numToFall[x] - 1 - z];
                                            gridTiles[x][y - z].moveSpeed = 0;
                                            gridTiles[x][y + numToFall[x] - z] = gridTiles[x][y - z];

                                            gridTiles[x][y - z] = null;


                                            checkForMorePoints = true;


                                   /* gridTiles[x][y + numToFall[x] - 1] = gridTiles[x][y - 1];
                                    gridTiles[x][y + numToFall[x] - 2] = gridTiles[x][y - 2];
                                    gridTiles[x][y + numToFall[x] - 3] = gridTiles[x][y - 3];
                                    gridTiles[x][y + numToFall[x] - 4] = gridTiles[x][y - 4];*/
                                            z++;
                                        }
                                        for (int i = 0; i < 8; i++) {
                                            gridTiles[x][i] = null;
                                            if (displayPointsOnTiles[x][i] != 0)
                                                displayPointsOnTiles[x][i] = 0;


                                        }
                                        //yValues[j] = intValue(ht / 4.5 + (patternW * (j-8)) / 2);
                                        //gridTiles[x][y]=null;


                                /*countAllBlocksDropped[x]++;
                                if(countAllBlocksDropped[x]==numToFall[x]*2)
                                {
                                    numToFall[x]=0;
                                    numToStartFall[x]=40;
                                }*/


                                    }
                                    //Log.d("",yValues[y]+" ");
                                }

                        /*for(int y=15;y>=8;y--)
                        {
                            if(gridTiles[x][y].imageLocation==0&&finishFalling[x]==true)
                            {
                                numToFall[x]++;
                                if(numToStartFall[x]==40)
                                {
                                    numToStartFall[x]=y;
                                }
                                else if(y>numToStartFall[x])
                                    numToFall[x]--;

                                finishFalling[x]=false;
                            }
                        }*/
                            }
                            if (finishFalling[x] == false) {
                                freezeInput = true;
                                invalidate(gridDraw);
                                for (int y = 0; y < 8; y++) {
                                    if (displayPointsOnTiles[x][y] != 0)
                                        canvas.drawText("+" + displayPointsOnTiles[x][y], gridTiles[x][y + 8].x + wid / 16, gridTiles[x][y + 8].y + wid / 12, paint);

                                    startTime = System.nanoTime();


                                }

                            } else if (finishFalling[x] == true) {
                                numToFall[x] = 0;
                                numToStartFall[x] = 40;
                                checkHint(false);

                                for (int y = 8; y < 16; y++) {
                                    gridTiles[x][y].moveSpeed = getResources().getInteger(R.integer.fallSpeed); //18
                                    doHint = true;
                            /*if(displayPointsOnTiles[x][y-8]!=0)
                            displayPointsOnTiles[x][y-8]=0;*/
                                }
                            }

                    /*if(drawFalling[x]==true&&finishFalling[x]==true)
                    {
                        for (int y = 8; y < 16; y++) {
                            if(displayPointsOnTiles[x][y-8]!=0)
                                displayPointsOnTiles[x][y-8]=0;
                        }
                    }*/

                        }
                        int hi = 0;
                        for (int x = 0; x < 8; x++) {
                            for (int y = 8; y < 16; y++) {
                                if (gridTiles[x][y].imageLocation == 0) {
                                    checkForMorePoints = false;
                                    if (hi == 0)
                                        invalidate(gridDraw);
                                    hi++;
                                }
                            }
                        }

                        if (checkForMorePoints || explodeX != 40) {
                            if (checkForMorePoints) {
                                checkForPoints();

                            }
                            invalidate(gridDraw);
                        }


                /*for(int x=0;x<8;x++) {
                    //System.out.print(numToStartFall[x]+" ");
                    if(numToStartFall[x]==40&&finishFalling[x]==true&&checkForMorePoints==true) {
                        checkForPoints();
                    }

                }*/


                        //Log.d("",mouseMove[0]+""+mouseMove[1]);
                        if (mouseMove[0] != null && mouseMove[1] != null) {
                            gridTiles[touchXGridEnd][touchYGridEnd] = mouseMove[1];
                            gridTiles[touchXGridStart][touchYGridStart] = mouseMove[0];


                            mouseMove[0] = null;
                            mouseMove[1] = null;
                            //freezeInput = false;
                            check++;
                        } else {
                            mouseMove[0] = null;
                            mouseMove[1] = null;
                        }
                        //Log.d("test two",mouseMove[0]+""+mouseMove[1]);
                        if (check == 1) {
                            if (checkForPointsFreeze == false) {
                                checkForPointsFreeze = checkForPoints();

                                if (moveBack == 0 && checkForPointsFreeze == false && checkMoveBack == false) {
                                    moveBlockOnGrid(); //turn on to move pieces back to their spots if checkForPoints() returns false
                                    checkMoveBack = true;
                                    doHint = true;
                                }

                            } else
                                moveBack++;

                            if (finishFade) {
                                invalidate(gridDraw);
                                finishFade = false;
                            }

                        }
                    }

                    //checkIfGame=true;
                    if (checkIfGame == true) {

                        if(gameOverSound==null&&gameOverAlreadySaid==false)
                            gameOverSound = MediaPlayer.create(this.getContext(), R.raw.gameover);
                        if(gameOverSound!=null&&!gameOverSound.isPlaying()&&gameOverAlreadySaid==false) {
                            gameOverAlreadySaid=true;
                            gameOverSound.start();
                        }

                        if(gameOverSound!=null&&gameOverSound.isPlaying()==false)
                        {
                            gameOverSound.reset();
                            gameOverSound.release();
                            gameOverSound=null;
                        }

                        if(soundStar!=null&&soundStar.isPlaying()==false)
                        {
                            soundStar.reset();
                            soundStar.release();
                            soundStar=null;
                        }

                        paint2.setColor(Color.BLACK);
                        paint2.setAlpha(150);
                        canvas.drawRect(0, 0, wid, ht, paint2);
                        //canvas.drawRect(wid / 6, intValue(ht / 4.3), wid * 5 / 6, ht / 3, paint2);
                        //canvas.drawRect(wid/6, intValue(ht / 2.9), intValue(wid*2.93/6), intValue(ht / 2.2), paint2);
                        //canvas.drawRect(intValue(wid*3.07/6), intValue(ht / 2.9), wid*5/6,intValue(ht / 2.2), paint2);
                        //paint.setStyle(Paint.Style.STROKE);
                        //canvas.drawRect(wid / 6 + 1, intValue(ht / 4.3), wid * 5 / 6, ht / 3, paint);
                        //canvas.drawRect(1, 1, wid, ht, paint);
                        //canvas.drawRect(wid/6+1, intValue(ht / 2.9), intValue(wid*2.93/6), intValue(ht / 2.2), paint);
                        //canvas.drawRect(intValue(wid * 3.07 / 6) + 1, intValue(ht / 2.9), wid * 5 / 6, intValue(ht / 2.2), paint);
                        //paint.setStyle(Paint.Style.FILL);


                        //paint.setStyle(Paint.Style.STROKE);
                        //
                        //paint.setStyle(Paint.Style.FILL);


                        //paint.setTextSize(getResources().getDimension(R.dimen.text_size) * 2);
                        //canvas.drawText("Game Over", wid / 2, intValue(ht / 3.3), paint);
                        //canvas.drawText("Menu", wid / 3, intValue(ht / 2.4), paint);
                        canvas.drawBitmap(playAgain, wid / 2 - homeButton.getWidth()/2, intValue(ht / 2.93), paint);
                        canvas.drawBitmap(reshuffle, wid / 2 - reshuffle.getWidth()/2, intValue(ht / 4.1), paint);
                       // canvas.drawText(shuffle+variables.shuffleAdd+shuffleAddFromPoints+"",wid/2+wid/3,intValue(ht / 2.6),paint);
                        canvas.drawBitmap(homeButton, wid / 2 - playAgain.getWidth() / 2, intValue(ht / 2.275), paint);

                    }

                    //Log.d("",canvas.getClipBounds()+"");
                }
            }

            public void checkHint(boolean test)
            {

                if(!variables.doneLoadingMap)
                    return;


                int picX = gridTiles[0][8].imageLocation;
                int picY = gridTiles[0][8].imageLocation;

                for (int x = 0; x < 22; x++) {
                        hintX[x]=-1;
                        hintY[x]=-1;
                }
                if(test)
                checkIfGame=true;

                int countX = 0;
                int countY = 0;
                increment=0;

                for (int x = 0; x < 8; x++) {
                    for (int y = 8; y < 16; y++) {



                        if (gridTiles[x][y].imageLocation == picY && gridTiles[x][y].imageLocation != 0) {
                            //System.out.println(gridTiles[x][y].getImageLocation()+" "+gridTiles[x][y].getX()+" "+gridTiles[x][y].getY());
                            countY++;
                            if (y == 8 && x == 0)
                                countY--;

                            if (countY == 1) {
                                //hypercube
                                if ((y >= 8 && y - 1 >= 8)) {

                                    if(y-2>=8&&gridTiles[x][y-2].imageLocation!=picY)
                                    {
                                       // if(gridTiles[x][y-2].imageLocation!=picY&&((x-1>=0&&gridTiles[x-1][y-2].imageLocation==picY)||(x+1<=7&&gridTiles[x+1][y-2].imageLocation==picY)||(y-3>=8&&gridTiles[x][y-3].imageLocation==picY)))
                                       // {
                                       //     Log.d("check here",x+" "+y);
                                        //}
                                        if(x-1>=0&&gridTiles[x-1][y-2].imageLocation==picY)
                                        {
                                            //Log.d("check here1",x+" "+(y-8-2));
                                            hintX[increment]=x-1;
                                            hintY[increment]=y-2;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(x+1<=7&&gridTiles[x+1][y-2].imageLocation==picY)
                                        {
                                            //Log.d("check here2",x+" "+(y-8-2));
                                            hintX[increment]=x+1;
                                            hintY[increment]=y-2;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(y-3>=8&&gridTiles[x][y-3].imageLocation==picY)
                                        {
                                            //Log.d("check here3",x+" "+(y-8-2));
                                            hintX[increment]=x;
                                            hintY[increment]=y-3;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                    }
                                    if(y+1<16&&gridTiles[x][y+1].imageLocation!=picY)
                                    {
                                        if(x-1>=0&&gridTiles[x-1][y+1].imageLocation==picY)
                                        {
                                            //Log.d("check here1",x+" "+(y-8+1));
                                            hintX[increment]=x-1;
                                            hintY[increment]=y+1;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(x+1<=7&&gridTiles[x+1][y+1].imageLocation==picY)
                                        {
                                            //Log.d("check here2",x+" "+(y-8+1));
                                            hintX[increment]=x+1;
                                            hintY[increment]=y+1;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(y+2<16&&gridTiles[x][y+2].imageLocation==picY)
                                        {
                                            //Log.d("check here3",x+" "+(y-8+1));
                                            hintX[increment]=x;
                                            hintY[increment]=y+2;
                                            increment++;
                                            checkIfGame=false;
                                        }

                                    }


                                }
                            }


                        }else
                        {
                            countY=0;
                            picY = gridTiles[x][y].imageLocation;
                        }

                        if(y==15)
                        {
                            countY=-1;
                        }

                        if(y-2>=8&&gridTiles[x][y-2].imageLocation==picY&&gridTiles[x][y-1].imageLocation!=picY&&gridTiles[x][y].imageLocation==picY)
                        {
                            if(x-1>=0&&gridTiles[x-1][y-1].imageLocation==picY)
                            {
                                //Log.d("check here1",x+" "+(y-8-1));
                                hintX[increment]=x-1;
                                hintY[increment]=y-1;
                                increment++;
                                checkIfGame=false;
                            }
                            else if(x+1<=7&&gridTiles[x+1][y-1].imageLocation==picY)
                            {
                                //Log.d("check here2",x+" "+(y-8-1));
                                hintX[increment]=x+1;
                                hintY[increment]=y-1;
                                increment++;
                                checkIfGame=false;
                            }
                        }









                        if (gridTiles[y-8][x+8].imageLocation == picX && gridTiles[y-8][x+8].imageLocation != 0) {
                            countX++;
                            if (y == 8 && x == 0)
                                countX--;

                            if (countX == 1) {
                                //hypercube
                                if (y >= 8 && y - 1 >= 8) {

                                    if(y-2>=8&&gridTiles[y-8-2][x+8].imageLocation!=picX) {

                                        if(x-1>=0&&gridTiles[y-8-2][x+8-1].imageLocation==picX)
                                        {
                                            //Log.d("check here2",(y-8-2)+" "+(x));
                                            hintX[increment]=y-8-2;
                                            hintY[increment]=x+8-1;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(x+1<8&&gridTiles[y-8-2][x+8+1].imageLocation==picX)
                                        {
                                            //Log.d("check here2",(y-8-2)+" "+(x));
                                            hintX[increment]=y-8-2;
                                            hintY[increment]=x+8+1;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(y-3>=8&&gridTiles[y-8-3][x+8].imageLocation==picX)
                                        {
                                            //Log.d("check here2",(y-8-2)+" "+(x));
                                            hintX[increment]=y-8-3;
                                            hintY[increment]=x+8;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                    }
                                    if(y+1<16&&gridTiles[y-8+1][x+8].imageLocation!=picX) {

                                        if(x-1>=0&&gridTiles[y-8+1][x+8-1].imageLocation==picX)
                                        {
                                            //Log.d("check here2",(y-8+1)+" "+(x));
                                            hintX[increment]=y-8+1;
                                            hintY[increment]=x+8-1;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(x+1<8&&gridTiles[y-8+1][x+8+1].imageLocation==picX)
                                        {
                                            //Log.d("check here2",(y-8+1)+" "+(x));
                                            hintX[increment]=y-8+1;
                                            hintY[increment]=x+8+1;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                        else if(y+2<16&&gridTiles[y-8+2][x+8].imageLocation==picX)
                                        {
                                           //Log.d("check here2",(y-8+1)+" "+(x));
                                            hintX[increment]=y-8+2;
                                            hintY[increment]=x+8;
                                            increment++;
                                            checkIfGame=false;
                                        }
                                    }
                                }
                            }


                        }else
                        {
                            countX=0;
                            picX = gridTiles[y-8][x+8].imageLocation;
                        }

                        if(y==15)
                        {
                            countX=-1;
                        }

                        if(y-2>=8&&gridTiles[y-2-8][x+8].imageLocation==picX&&gridTiles[y-1-8][x+8].imageLocation!=picX&&gridTiles[y-8][x+8].imageLocation==picX)
                        {
                            if(x-1>=0&&gridTiles[y-8-1][x+8-1].imageLocation==picX)
                            {
                                //Log.d("check here2",(y-8-1)+" "+(x));
                                hintX[increment]=y-8-1;
                                hintY[increment]=x+8-1;
                                increment++;
                                checkIfGame=false;
                            }
                            else if(x+1<=7&&gridTiles[y-8-1][x+8+1].imageLocation==picX)
                            {
                                //Log.d("check here2",(y-8-1)+" "+(x));
                                hintX[increment]=y-8-1;
                                hintY[increment]=x+8+1;
                                increment++;
                                checkIfGame=false;
                            }
                        }

                    }
                }
                if(points>100) {
                    //checkIfGame = true;
                    //shuffle=0;
                }
            }



            public boolean checkForPoints() //checks to see if things are in a row or column of three or more
            {
                if(!variables.doneLoadingMap)
                    return false;
               /* tileXCheck = new int[20];
                tileYCheck = new int[20];*/
                boolean playStarSound=false;
                howKilled = new int[20];
                int picX = gridTiles[0][8].imageLocation;
                int picY = gridTiles[0][8].imageLocation;

                int countX = 0;
                int countY = 0;


                for (int x = 0; x < 8; x++) {
                    for (int y = 8; y < 16; y++) {
                        gridTiles[x][y].y=yValues[y];
                        gridTiles[x][y].x=xValues[x];
                        /*if(displayPointsOnTiles[x][y-8]!=0)
                            displayPointsOnTiles[x][y-8]=0;*/



                        if (gridTiles[x][y].imageLocation == picY && gridTiles[x][y].imageLocation != 0) {
                            //System.out.println(gridTiles[x][y].getImageLocation()+" "+gridTiles[x][y].getX()+" "+gridTiles[x][y].getY());
                            countY++;
                            if (y == 8 && x == 0)
                                countY--;

                            if (countY == 4) {
                                //hypercube
                                if (y >= 8 && y - 1 >= 8 && y - 2 >= 8 && y - 3 >= 8 && y - 4 >= 8) {
                                    if(gridTiles[x][y].howKilled==0&&gridTiles[x][y].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[x][y].howKilled=1;
                                    else if(gridTiles[x][y].howKilled==2&&gridTiles[x][y].drawFireball==true)
                                    {
                                        explodeX=x;
                                        explodeY=y;
                                        //Log.d("vert"," kaboom");
                                        if(x-1>=0) {
                                            if(y+1<16)
                                                gridTiles[x-1][y+1].howKilled = 1;

                                            gridTiles[x-1][y].howKilled = 1;
                                            gridTiles[x-1][y-1].howKilled = 1;
                                        }
                                        if(x+1<=7) {
                                            if(y+1<16)
                                                gridTiles[x+1][y+1].howKilled = 1;

                                            gridTiles[x+1][y].howKilled = 1;
                                            gridTiles[x+1][y-1].howKilled = 1;
                                        }
                                        if(y+1<16)
                                            gridTiles[x][y+1].howKilled=1;
                                    }
                                    if(gridTiles[x][y].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][y].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[x][c+8].howKilled=1;
                                        }
                                    }

                                    if(gridTiles[x][y - 1].howKilled==0&&gridTiles[x][y-1].drawFireball==false)
                                        gridTiles[x][y - 1].howKilled=1;
                                    else if(gridTiles[x][y - 1].howKilled==2&&gridTiles[x][y-1].drawFireball==true)
                                    {
                                        explodeX=x;
                                        explodeY=y-1;
                                        //Log.d("vert"," kaboom");
                                        if(x-1>=0) {
                                            gridTiles[x-1][y].howKilled = 1;
                                            gridTiles[x-1][y-1].howKilled = 1;
                                            gridTiles[x-1][y-2].howKilled = 1;
                                        }
                                        if(x+1<=7) {
                                            gridTiles[x+1][y].howKilled = 1;
                                            gridTiles[x+1][y-1].howKilled = 1;
                                            gridTiles[x+1][y-2].howKilled = 1;
                                        }
                                    }
                                    if(gridTiles[x][y-1].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][y-1].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[x][c+8-1].howKilled=1;
                                        }
                                    }
                                    gridTiles[x][y - 2].howKilled=1;
                                    gridTiles[x][y - 3].howKilled=1;
                                    gridTiles[x][y - 4].howKilled=3;


                                    if(gridTiles[x][y].drawFireball==true)
                                    {
                                        gridTiles[x][y].drawFireball=false;
                                        gridTiles[x][y].howKilled=1;
                                    }
                                    else if(gridTiles[x][y-1].drawFireball==true)
                                    {
                                        gridTiles[x][y-1].drawFireball=false;
                                        gridTiles[x][y-1].howKilled=1;
                                    }
                                    // multiplier++;

                                }
                                /*else
                                    Log.d("5 vert","");*/
                            } else if (countY == 3) {
                                //fireball

                                if (y >= 8 && y - 1 >= 8 && y - 2 >= 8 && y - 3 >= 8) {

                                    if(gridTiles[x][y].howKilled==0&&gridTiles[x][y].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[x][y].howKilled=1;
                                    else if(gridTiles[x][y].howKilled==2&&gridTiles[x][y].drawFireball==true)
                                    {
                                        explodeX=x;
                                        explodeY=y;
                                        //Log.d("vert"," kaboom");
                                        if(x-1>=0) {
                                            if(y+1<16)
                                                gridTiles[x-1][y+1].howKilled = 1;

                                            gridTiles[x-1][y].howKilled = 1;
                                            gridTiles[x-1][y-1].howKilled = 1;
                                        }
                                        if(x+1<=7) {
                                            if(y+1<16)
                                                gridTiles[x+1][y+1].howKilled = 1;

                                            gridTiles[x+1][y].howKilled = 1;
                                            gridTiles[x+1][y-1].howKilled = 1;
                                        }
                                        if(y+1<16)
                                            gridTiles[x][y+1].howKilled=1;
                                    }
                                    if(gridTiles[x][y].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][y].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[x][c+8].howKilled=1;
                                        }
                                    }
                                    gridTiles[x][y - 1].howKilled=1;
                                    gridTiles[x][y - 2].howKilled=1;
                                    gridTiles[x][y - 3].howKilled=2;


                                    if(gridTiles[x][y].drawFireball==true)
                                    {
                                        gridTiles[x][y].drawFireball=false;
                                        gridTiles[x][y].howKilled=1;
                                    }
                                    // multiplier++;

                                }
                                /*else
                                    Log.d("4 vert","");*/
                            } else if (countY == 2) {

                                if (y >= 8 && y - 1 >= 8 && y - 2 >= 8) {

                                    //multiplier++;
                                    if(gridTiles[x][y].howKilled==0&&gridTiles[x][y].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[x][y].howKilled=1;
                                    else if(gridTiles[x][y].howKilled==2&&gridTiles[x][y].drawFireball==true)
                                    {
                                        explodeX=x;
                                        explodeY=y;
                                        //Log.d("vert"," kaboom");
                                        if(x-1>=0) {
                                            if(y+1<16)
                                                gridTiles[x-1][y+1].howKilled = 1;

                                            gridTiles[x-1][y].howKilled = 1;
                                            gridTiles[x-1][y-1].howKilled = 1;
                                        }
                                        if(x+1<=7) {
                                            if(y+1<16)
                                                gridTiles[x+1][y+1].howKilled = 1;

                                            gridTiles[x+1][y].howKilled = 1;
                                            gridTiles[x+1][y-1].howKilled = 1;
                                        }
                                        if(y+1<16)
                                            gridTiles[x][y+1].howKilled=1;
                                    }
                                    if(gridTiles[x][y].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][y].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[x][c+8].howKilled=1;
                                        }
                                    }

                                    if(gridTiles[x][y - 1].howKilled==0&&gridTiles[x][y-1].drawFireball==false)
                                        gridTiles[x][y - 1].howKilled=1;
                                    else if(gridTiles[x][y - 1].howKilled==2&&gridTiles[x][y-1].drawFireball==true)
                                    {
                                        explodeX=x;
                                        explodeY=y-1;
                                        //Log.d("vert"," kaboom");
                                        if(x-1>=0) {
                                            gridTiles[x-1][y].howKilled = 1;
                                            gridTiles[x-1][y-1].howKilled = 1;
                                            gridTiles[x-1][y-2].howKilled = 1;
                                        }
                                        if(x+1<=7) {
                                            gridTiles[x+1][y].howKilled = 1;
                                            gridTiles[x+1][y-1].howKilled = 1;
                                            gridTiles[x+1][y-2].howKilled = 1;
                                        }
                                    }
                                    if(gridTiles[x][y-1].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][y-1].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[x][c+8].howKilled=1;
                                        }
                                    }

                                    if(gridTiles[x][y - 2].howKilled==0&&gridTiles[x][y-2].drawFireball==false)
                                        gridTiles[x][y - 2].howKilled=1;
                                    else if(gridTiles[x][y - 2].howKilled==2&&gridTiles[x][y-2].drawFireball==true)
                                    {
                                        explodeX=x;
                                        explodeY=y-2;
                                        //Log.d("vert"," kaboom");
                                        if(x-1>=0) {
                                            gridTiles[x-1][y-1].howKilled = 1;
                                            gridTiles[x-1][y-2].howKilled = 1;
                                            if(y-3>=8)
                                                gridTiles[x-1][y-3].howKilled = 1;
                                        }
                                        if(x+1<=7) {
                                            gridTiles[x+1][y-1].howKilled = 1;
                                            gridTiles[x+1][y-2].howKilled = 1;
                                            if(y-3>=8)
                                                gridTiles[x+1][y-3].howKilled = 1;

                                        }
                                        if(y-3>=8)
                                            gridTiles[x][y-3].howKilled=1;
                                    }
                                    if(gridTiles[x][y-2].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][y-2].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[x][c+8].howKilled=1;
                                        }
                                    }

                                    if(gridTiles[x][y].drawFireball==true)
                                    {
                                        gridTiles[x][y].drawFireball=false;
                                        gridTiles[x][y].howKilled=1;
                                    }
                                    else if(gridTiles[x][y-1].drawFireball==true)
                                    {
                                        gridTiles[x][y-1].drawFireball=false;
                                        gridTiles[x][y-1].howKilled=1;
                                    }
                                    else if(gridTiles[x][y-2].drawFireball==true)
                                    {
                                        gridTiles[x][y-2].drawFireball=false;
                                        gridTiles[x][y-2].howKilled=1;
                                    }
                                    /*gridTiles[x][y].howKilled=1;
                                    gridTiles[x][y - 1].howKilled=1;
                                    gridTiles[x][y - 2].howKilled=1;*/


                                }
                               /* else
                                    Log.d("3 vert","");*/

                            }
                            //System.out.println(countY);
                        } else {
                            picY = gridTiles[x][y].imageLocation;

                            countY = 0;
                            /*if(y==15&&y==14&&y==13)
                                countY-=3;
                            else if(y==15&&y==14)
                                countY-=2;
                            else if(y==15)
                                countY-=1;*/
                        }

                        if(y==15)
                        {
                            countY=-1;
                        }


                        if (gridTiles[y-8][x+8].imageLocation == picX && gridTiles[y-8][x+8].imageLocation != 0) {
                            //System.out.println(gridTiles[x][y].getImageLocation()+" "+gridTiles[x][y].getX()+" "+gridTiles[x][y].getY());
                            countX++;
                            if (y == 8 && x == 0)
                                countX--;

                            if (countX == 4) {
                                //hypercube

                                if (y >= 8 && y - 1 >= 8 && y - 2 >= 8 && y - 3 >= 8 && y - 4 >= 8) {

                                    //multiplier++;
                                    if(gridTiles[y-8][x+8].howKilled==0&&gridTiles[y-8][x+8].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[y-8][x+8].howKilled=1;
                                    else if(gridTiles[y-8][x+8].howKilled==2&&gridTiles[y-8][x+8].drawFireball==true)
                                    {
                                        explodeX=y-8;
                                        explodeY=x+8;
                                        if(x+8-1>=8) {

                                            gridTiles[y-8-1][x+8-1].howKilled=1;
                                            gridTiles[y-8][x+8-1].howKilled=1;

                                            if(y-8+1<=7)
                                                gridTiles[y-8+1][x+8-1].howKilled=1;
                                        }
                                        if(x+8+1<16) {

                                            if(y-8+1<=7)
                                                gridTiles[y-8+1][x+8+1].howKilled=1;

                                            gridTiles[y-8][x+8+1].howKilled=1;
                                            gridTiles[y-8-1][x+8+1].howKilled=1;

                                        }
                                        if(y-8+1<=7)
                                            gridTiles[y-8+1][x+8].howKilled=1;

                                    }
                                    if(gridTiles[y-8][x+8].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][x+8].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[y-8][c+8].howKilled=1;
                                        }
                                    }

                                    if(gridTiles[y - 1-8][x+8].howKilled==0&&gridTiles[y - 1-8][x+8].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[y - 1-8][x+8].howKilled=1;
                                    else if(gridTiles[y - 1-8][x+8].howKilled==2&&gridTiles[y - 1-8][x+8].drawFireball==true)
                                    {
                                        explodeX=y-8-1;
                                        explodeY=x+8;
                                        if(x+8-1>=8) {

                                            gridTiles[y-8-2][x+8-1].howKilled=1;
                                            gridTiles[y-8-1][x+8-1].howKilled=1;
                                            gridTiles[y-8][x+8-1].howKilled=1;
                                        }
                                        if(x+8+1<16) {

                                            gridTiles[y-8-2][x+8+1].howKilled=1;
                                            gridTiles[y-8-1][x+8+1].howKilled=1;
                                            gridTiles[y-8][x+8+1].howKilled=1;

                                        }

                                    }
                                    if(gridTiles[y-8-1][x+8].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][x+8].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[y-8-1][c+8].howKilled=1;
                                        }
                                    }
                                    gridTiles[y - 2-8][x+8].howKilled=3;
                                    gridTiles[y - 3-8][x+8].howKilled=1;
                                    gridTiles[y - 4-8][x+8].howKilled=1;

                                    if(gridTiles[y-8][x+8].drawFireball==true)
                                    {
                                        gridTiles[y-8][x+8].drawFireball=false;
                                        gridTiles[y-8][x+8].howKilled=1;
                                    }
                                    else if(gridTiles[y - 1-8][x+8].drawFireball==true)
                                    {
                                        gridTiles[y - 1-8][x+8].drawFireball=false;
                                        gridTiles[y - 1-8][x+8].howKilled=1;
                                    }


                                }
                                /*else
                                    Log.d("5 horiz","");*/
                            } else if (countX == 3) {
                                //fireball

                                if (y >= 8 && y - 1 >= 8 && y - 2 >= 8 && y - 3 >= 8) {

                                    //multiplier++;
                                    if(gridTiles[y-8][x+8].howKilled==0&&gridTiles[y-8][x+8].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[y-8][x+8].howKilled=1;
                                    else if(gridTiles[y-8][x+8].howKilled==2&&gridTiles[y-8][x+8].drawFireball==true)
                                    {
                                        explodeX=y-8;
                                        explodeY=x+8;
                                        if(x+8-1>=8) {

                                            gridTiles[y-8-1][x+8-1].howKilled=1;
                                            gridTiles[y-8][x+8-1].howKilled=1;

                                            if(y-8+1<=7)
                                                gridTiles[y-8+1][x+8-1].howKilled=1;
                                        }
                                        if(x+8+1<16) {

                                            if(y-8+1<=7)
                                                gridTiles[y-8+1][x+8+1].howKilled=1;

                                            gridTiles[y-8][x+8+1].howKilled=1;
                                            gridTiles[y-8-1][x+8+1].howKilled=1;

                                        }
                                        if(y-8+1<=7)
                                            gridTiles[y-8+1][x+8].howKilled=1;

                                    }
                                    if(gridTiles[y-8][x+8].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][x+8].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[y-8][c+8].howKilled=1;
                                        }
                                    }
                                    gridTiles[y - 1-8][x+8].howKilled=1;
                                    gridTiles[y - 2-8][x+8].howKilled=2;
                                    gridTiles[y - 3-8][x+8].howKilled=1;

                                    if(gridTiles[y-8][x+8].drawFireball==true)
                                    {
                                        gridTiles[y-8][x+8].drawFireball=false;
                                        gridTiles[y-8][x+8].howKilled=1;
                                    }


                                }
                                /*else
                                {
                                    Log.d("4 horiz","");
                                }*/
                            } else if (countX == 2) {

                                if (y >= 8 && y - 1 >= 8 && y - 2 >= 8) {

                                    if(gridTiles[y-8][x+8].howKilled==0&&gridTiles[y-8][x+8].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[y-8][x+8].howKilled=1;
                                    else if(gridTiles[y-8][x+8].howKilled==2&&gridTiles[y-8][x+8].drawFireball==true)
                                    {
                                        explodeX=y-8;
                                        explodeY=x+8;
                                        if(x+8-1>=8) {

                                            gridTiles[y-8-1][x+8-1].howKilled=1;
                                            gridTiles[y-8][x+8-1].howKilled=1;

                                            if(y-8+1<=7)
                                                gridTiles[y-8+1][x+8-1].howKilled=1;
                                        }
                                        if(x+8+1<16) {

                                            if(y-8+1<=7)
                                                gridTiles[y-8+1][x+8+1].howKilled=1;

                                            gridTiles[y-8][x+8+1].howKilled=1;
                                            gridTiles[y-8-1][x+8+1].howKilled=1;

                                        }
                                        if(y-8+1<=7)
                                            gridTiles[y-8+1][x+8].howKilled=1;

                                    }
                                    if(gridTiles[y-8][x+8].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][x+8].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[y-8][c+8].howKilled=1;
                                        }
                                    }

                                    if(gridTiles[y - 1-8][x+8].howKilled==0&&gridTiles[y - 1-8][x+8].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[y - 1-8][x+8].howKilled=1;
                                    else if(gridTiles[y - 1-8][x+8].howKilled==2&&gridTiles[y - 1-8][x+8].drawFireball==true)
                                    {
                                        explodeX=y-8-1;
                                        explodeY=x+8;
                                        if(x+8-1>=8) {

                                            gridTiles[y-8-2][x+8-1].howKilled=1;
                                            gridTiles[y-8-1][x+8-1].howKilled=1;
                                            gridTiles[y-8][x+8-1].howKilled=1;
                                        }
                                        if(x+8+1<16) {

                                            gridTiles[y-8-2][x+8+1].howKilled=1;
                                            gridTiles[y-8-1][x+8+1].howKilled=1;
                                            gridTiles[y-8][x+8+1].howKilled=1;

                                        }

                                    }
                                    if(gridTiles[y-8-1][x+8].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][x+8].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[y-8-1][c+8].howKilled=1;
                                        }
                                    }


                                    if(gridTiles[y - 2-8][x+8].howKilled==0&&gridTiles[y - 2-8][x+8].drawFireball==false)//||gridTiles[x][y].howKilled==1)
                                        gridTiles[y - 2-8][x+8].howKilled=1;
                                    else if(gridTiles[y - 2-8][x+8].howKilled==2&&gridTiles[y - 2-8][x+8].drawFireball==true)
                                    {
                                        explodeX=y-8-2;
                                        explodeY=x+8;
                                        if(x+8-1>=8) {
                                            if(y-3>=8)
                                                gridTiles[y-8-3][x+8-1].howKilled=1;
                                            gridTiles[y-8-2][x+8-1].howKilled=1;
                                            gridTiles[y-8-1][x+8-1].howKilled=1;
                                        }
                                        if(x+8+1<16) {
                                            if(y-3>=8)
                                                gridTiles[y-8-3][x+8+1].howKilled=1;
                                            gridTiles[y-8-2][x+8+1].howKilled=1;
                                            gridTiles[y-8-1][x+8+1].howKilled=1;

                                        }
                                        if(y - 3-8>=0)
                                            gridTiles[y - 3-8][x+8].howKilled=1;

                                    }
                                    if(gridTiles[y-8-2][x+8].howKilled==4)
                                    {
                                        Log.d("vert"," star");
                                        playStarSound=true;
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[c][x+8].howKilled=1;
                                        }
                                        for(int c=0;c<8;c++)
                                        {
                                            gridTiles[y-8-2][c+8].howKilled=1;
                                        }
                                    }




                                    // gridTiles[y - 1-8][x+8].howKilled=1;
                                    //gridTiles[y - 2-8][x+8].howKilled=1;
                                    //multiplier++;






                                    if(gridTiles[y-8][x+8].drawFireball==true)
                                    {
                                        gridTiles[y-8][x+8].drawFireball=false;
                                        gridTiles[y-8][x+8].howKilled=1;
                                    }
                                    else if(gridTiles[y - 1-8][x+8].drawFireball==true)
                                    {
                                        gridTiles[y - 1-8][x+8].drawFireball=false;
                                        gridTiles[y - 1-8][x+8].howKilled=1;
                                    }
                                    else if(gridTiles[y - 2-8][x+8].drawFireball==true)
                                    {
                                        gridTiles[y - 2-8][x+8].drawFireball=false;
                                        gridTiles[y - 2-8][x+8].howKilled=1;
                                    }


                                }
                                /*else
                                    Log.d("3 horiz","");*/


                            }
                            //System.out.println(countY);
                        } else {
                            picX = gridTiles[y-8][x+8].imageLocation;
                            countX = 0;

                        }

                        if(y-8==7)
                            countX=-1;


                    }
                }

                if(playStarSound)
                {
                    if(soundStar==null)
                        soundStar=MediaPlayer.create(this.getContext(), R.raw.star);
                    if(soundStar.isPlaying()==false&&MusicManager.musicOn)
                    {
                        soundStar.start();
                    }
                }


                for (int x = 0; x < 8; x++) {
                    for (int y = 8; y < 16; y++) {

                        if(gridTiles[x][y].howKilled==2||gridTiles[x][y].howKilled==3||gridTiles[x][y].howKilled==4)
                        {
                            gridTiles[x][y].updated=true;
                        }

                        if(gridTiles[x][y].howKilled==1)
                        {

                            gridTiles[x][y].updated=true;
                            alreadySaved=false;

                            check=0;
                            numForMulti++;
                            if(numForMulti>hMulti) {
                                hMulti = numForMulti;
                            }
                            if(numForMulti%5==0)
                            {
                                gridTiles[x][y].howKilled=4;
                            }
                            levelProgress++;
                            freezeInput=true;

                            return true;
                        }

                    }
                }

                        if(alreadySaved==false&&checkIfGame==false&&variables.currentlySavingMap==false)
                        {
                            alreadySaved=true;
                            Log.d("","saving game");
                            new SaveGame((GameGrid)getActivity()).execute();
                        }
                        //Log.d("save",""+cursor.getCount());
                check=0;
                freezeInput=false;

                return false;

            }



            public void explosionDraw(Canvas canvas)
            {

                int pixelPerTile = explosion.getWidth() / 12;

                //int left, int top, int right, int bottom
                srcExplosion.set(explosionNum / 5 * pixelPerTile, 0, (explosionNum / 5 + 1) * pixelPerTile, pixelPerTile);
                destExplosion.set(xValues[explodeX] - wid / 8, yValues[explodeY] - wid / 8, xValues[explodeX] + wid / 8 * 2, yValues[explodeY] + wid / 8 * 2);
                    /*srcExplosion.bottom=40;
                    srcExplosion.top=0;
                    srcExplosion.left=explosionNum*40;
                    srcExplosion.right=(explosionNum+1)*40;*/

                    /*destExplosion.left=explodeX-wid/8;
                    destExplosion.right=explodeX+wid/8*2;
                    destExplosion.top=explodeY-wid/8;
                    destExplosion.bottom=explodeY+wid/8*2;*/
                canvas.drawBitmap(explosion, srcExplosion, destExplosion, paint);




                explosionNum++;
                if (explosionNum > 60) {
                    explosionNum = 0;
                    explodeX = 40;
                    explodeY = 40;
                }

                if(sound==null)
                    sound = MediaPlayer.create(this.getContext(), R.raw.explosion);

                if(sound!=null&&sound.isPlaying()==false&&MusicManager.musicOn)
                {
                    Log.d("sup","sup");

                    sound.start();
                }

            }


            public void moveBlockOnGrid() //with your finger
            {
                int yDisplace;
                int xDisplace;
                hintTime=System.nanoTime();

                if(touchYStart>intValue(ht / 4.5)&&touchYStart<intValue(ht / 4.5 + (patternW * 4)))
                {
                    touchYGridStart=((touchYStart-intValue(ht / 4.5))/(patternW/2))+8;
                    touchXGridStart=touchXStart/(patternW/2);
                    //touchYGridStart=((1150-intValue(1920 / 4.5))/(1080/4*4/8))+1;


                    /*if(touchX>=wid)
                    {
                        touchX=wid-1;
                    }*/

                    touchYGridEnd=((touchY-intValue(ht / 4.5))/(patternW/2))+8;
                    touchXGridEnd=touchX/(patternW/2);

                    yDisplace=Math.abs(touchYStart-touchY);
                    xDisplace=Math.abs(touchXStart-touchX);

                    if(touchYGridEnd>touchYGridStart)
                    {
                        touchYGridEnd=touchYGridStart+1;
                    }
                    else if(touchYGridEnd<touchYGridStart)
                    {
                        touchYGridEnd=touchYGridStart-1;
                    }

                    if(touchXGridEnd>touchXGridStart)
                    {
                        touchXGridEnd=touchXGridStart+1;
                    }
                    else if(touchXGridEnd<touchXGridStart)
                    {
                        touchXGridEnd=touchXGridStart-1;
                    }

                    if(touchYGridEnd>=16)
                    {
                        touchYGridEnd=15;
                    }
                    if(touchYGridEnd<8)
                    {
                        touchYGridEnd=8;
                    }
                    if(touchXGridEnd<0)
                    {
                        touchXGridEnd=0;
                    }
                    if(touchXGridEnd>=8)
                    {
                        touchXGridEnd=7;
                    }


                    if(touchYGridStart>=16)
                    {
                        touchYGridStart=15;
                    }
                    if(touchYGridStart<8)
                    {
                        touchYGridStart=8;
                    }
                    if(touchXGridStart<0)
                    {
                        touchXGridStart=0;
                    }
                    if(touchXGridStart>=8)
                    {
                        touchXGridStart=7;
                    }

                    //System.out.println(touchXGridStart+" "+touchYGridStart+"       "+touchXGridEnd+" "+touchYGridEnd);

                    if((Math.abs(touchXGridStart-touchXGridEnd)==1)||(Math.abs(touchYGridStart-touchYGridEnd)==1))
                    {
                        //if((Math.abs(touchXGridStart-touchXGridEnd)==1)&&!(Math.abs(touchYGridStart-touchYGridEnd)>0))
                        if(xDisplace>=yDisplace)
                        {
                            //move along x
                            /*gridTiles[touchXGridStart][touchYGridStart].setDoNotDraw(true);
                            gridTiles[touchXGridEnd][touchYGridEnd].setDoNotDraw(true);*/
                            if(touchXGridStart>touchXGridEnd)
                            {
                                Log.d("",touchXGridStart+" "+touchXGridEnd);
                                if(gridTiles[touchXGridStart][touchYGridStart]!=gridTiles[touchXGridEnd][touchYGridStart]) {
                                    gridTiles[touchXGridStart][touchYGridStart].drawDirection = 3;
                                    gridTiles[touchXGridEnd][touchYGridStart].drawDirection = 1;
                                    touchYGridEnd = touchYGridStart;
                                    freezeInput=true;
                                }
                            }
                            else
                            {
                                if(gridTiles[touchXGridStart][touchYGridStart]!=gridTiles[touchXGridEnd][touchYGridStart]) {
                                    gridTiles[touchXGridStart][touchYGridStart].drawDirection = 1;
                                    gridTiles[touchXGridEnd][touchYGridStart].drawDirection = 3;
                                    touchYGridEnd = touchYGridStart;
                                    freezeInput=true;
                                }
                            }

                            invalidate(gridDraw);

                        }
                        else if(yDisplace>xDisplace)
                        //if(!(Math.abs(touchXGridStart-touchXGridEnd)>0)&&(Math.abs(touchYGridStart-touchYGridEnd)==1))
                        {
                            /*//move along y
                            gridTiles[touchXGridStart][touchYGridStart].setDoNotDraw(true);
                            gridTiles[touchXGridEnd][touchYGridEnd].setDoNotDraw(true);*/
                            if(touchYGridStart>touchYGridEnd)
                            {
                                if(gridTiles[touchXGridStart][touchYGridStart]!=gridTiles[touchXGridStart][touchYGridEnd]) {
                                    gridTiles[touchXGridStart][touchYGridStart].drawDirection = 4;
                                    gridTiles[touchXGridStart][touchYGridEnd].drawDirection = 2;
                                    touchXGridEnd = touchXGridStart;
                                    freezeInput=true;
                                }
                            }
                            else
                            {
                                if(gridTiles[touchXGridStart][touchYGridStart]!=gridTiles[touchXGridStart][touchYGridEnd]) {
                                    gridTiles[touchXGridStart][touchYGridStart].drawDirection = 2;
                                    gridTiles[touchXGridStart][touchYGridEnd].drawDirection = 4;
                                    touchXGridEnd = touchXGridStart;
                                    freezeInput=true;
                                }
                            }

                            invalidate(gridDraw);

                        }

                    }
                }

            }






            @Override
            public boolean onTouch(View v, MotionEvent event) {

               // Log.d("sup","sup");
                //Log.d("xy",(touchXStart/(patternW/2))+" "+(((touchYStart-intValue(ht / 4.5))/(patternW/2))+8));


                if(!freezeInput&&checkIfGame==false) {
                    moveBack=0;
                    checkMoveBack=false;
                    checkForPointsFreeze=false;
                    check=0;

                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN: {
                            touchXStart = (int) event.getX();
                            touchYStart = (int) event.getY();
                        }
                        case MotionEvent.ACTION_UP: {
                            touchX = (int) event.getX();
                            touchY = (int) event.getY();
                        }
                    }
                    moveBlockOnGrid();
                }
                else if(checkIfGame)
                {


                    //Log.d("blashsdfsd",""+(shuffleAdd+shuffle+shuffleAddFromPoints));
                    //canvas.drawBitmap(homeButton,wid/2-homeButton.getWidth()-10,intValue(ht / 2.93),paint);
                    if(event.getX()>wid/2-homeButton.getWidth()/2&&event.getX()<wid/2+homeButton.getWidth()/2&&event.getY()>intValue(ht / 2.275)&&event.getY()<intValue(ht / 2.275)+homeButton.getHeight())
                    {

                        variables.goBack=true;
                        reallyGameOver=true;
                        Log.d("blash",event.getX()+" "+event.getY());
                    }
                    if(event.getX()>wid/2-reshuffle.getWidth()/2&&event.getX()<wid/2+reshuffle.getWidth()&&event.getY()>intValue(ht / 4.1)&&event.getY()<intValue(ht / 4.1)+reshuffle.getHeight())
                    {
                        if(variables.purchaseOnce==false) {
                            variables.purchaseOnce=true;
                            //reshuffle //show the number of reshuffles they have
                            if (variables.shuffleAdd + shuffle+shuffleAddFromPoints > 0) {
                                variables.shuffleAdd--;
                                checkIfGame = false;
                                freezeInput = true;
                                doShuffle = true;
                                variables.purchaseOnce=false;
                                flashShuffle=false;
                                repsFlash=6000;
                                flashX=-3000;
                                gameOverAlreadySaid=false;

                            } else if(variables.shuffleAdd + shuffle+shuffleAddFromPoints <= 0){
                                //Context context = getContext();
                                variables.keepsGoing=true;
                            }
                        }
                    }
                    if(event.getX()>wid/2-playAgain.getWidth()/2&&event.getX()<wid/2+playAgain.getWidth()/2&&event.getY()>intValue(ht / 2.93)&&event.getY()<intValue(ht / 2.93)+playAgain.getHeight())
                    {
                        variables.startNew=true;
                        reallyGameOver=true;
                        startNewSaveThing=true;
                        //Log.d("blashghfgfghfgh",event.getX()+" "+event.getY());
                    }
                }

                return true;
            }


        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_game_grid, container, false);
            //return rootView;
            super.onCreateView(inflater, container, savedInstanceState);
            DrawView h = new DrawView(this.getActivity());
            return h;
        }

    }
}

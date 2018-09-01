


package me.jameshunt.electricsmash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {
    /*MediaPlayer player;*/
    boolean continueMusic;
    int width;
    int height;

    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("com.rayrok.james.electricsmash", MODE_PRIVATE);


        setContentView(R.layout.activity_main);

        Point size = new Point();

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        try {
            display.getSize(size);
            width = size.x;
            height = size.y;
        } catch (NoSuchMethodError e) {
            height = display.getHeight();
            width = display.getWidth();
        }


        DBAdapter myDb = new DBAdapter(this);

        myDb.open();
        Cursor cursor = myDb.getAllRows();
        if (cursor.getCount() > 0)
            findViewById(R.id.resume).setVisibility(View.VISIBLE);
        myDb.close();

    }

    public void mute(View view) {

        if (MusicManager.musicOn) {
            //MusicManager.pause();
            ImageView img = (ImageView) findViewById(R.id.mute);
            img.setImageResource(R.drawable.muteonbtn);
        } else {
            //MusicManager.start(this, MusicManager.MUSIC);
            ImageView img = (ImageView) findViewById(R.id.mute);
            img.setImageResource(R.drawable.mutebtn);

        }
        MusicManager.musicOn = !MusicManager.musicOn;

        prefs.edit().putBoolean("mute", MusicManager.musicOn).commit();
    }


    public void start(View view) {
        //continueMusic=true;
        // MusicManager.release();

        variables.name = "playbutton";
        Intent myIntent = new Intent(MainActivity.this, GameGrid.class);
        myIntent.putExtra("new", "0");
        MusicManager.restart = true;
        MainActivity.this.startActivity(myIntent);
        //Intent intent = new Intent(this, Grid.class);
        //startActivity(intent);
    }

    public void resume(View view) {


        variables.name = "resumeButton";
        continueMusic = true;
        findViewById(R.id.textView).setVisibility(View.VISIBLE);
        /*CharSequence text = "loading save";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.setGravity(Gravity.TOP,0,height/4);
        toast.show();*/
        Intent myIntent = new Intent(MainActivity.this, GameGrid.class);
        myIntent.putExtra("new", "1");
        MusicManager.restart = false;
        MainActivity.this.startActivity(myIntent);
        //Intent intent = new Intent(this, Grid.class);
        //startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener(mSensorListener);
        /*if (!continueMusic) {
            MusicManager.pause();
            //player.pause();
        }*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicManager.release();
    }

    @Override
    public void onStart() {
        super.onStart();

        variables.submitTime = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.d("time", "" + variables.time.getElapsedTimeDouble());
        variables.submitTime = true;
        if (!variables.notFrontPageStillOpen) {
            variables.name = "timeInGame";
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        variables.closedFromNotMain = false;
        variables.notFrontPageStillOpen = false;
        //Log.d("",""+variables.time.getElapsedTimeDouble());

        //mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textView).setVisibility(View.INVISIBLE);
        //player.start();
        DBAdapter myDb = new DBAdapter(this);
        DBAdapter2 myDb2 = new DBAdapter2(this);
        myDb.open();
        myDb2.open();
        Cursor cursor = myDb.getAllRows();
        Cursor cursor2 = myDb2.getAllRows();
        if (cursor.getCount() > 0) {
            findViewById(R.id.resume).setVisibility(View.VISIBLE);
        }
        myDb.close();
        myDb2.close();


        continueMusic = false;


        MusicManager.musicOn = prefs.getBoolean("mute", MusicManager.musicOn);
        //.looping=variables.prefs.getBoolean("looping", true);

        if (MusicManager.musicOn) {

            //MusicManager.release();
            //MusicManager.start(this, MusicManager.MUSIC);
            MusicManager.pause();
        } else {
            ImageView img = (ImageView) findViewById(R.id.mute);
            img.setImageResource(R.drawable.muteonbtn);
        }

    }
}

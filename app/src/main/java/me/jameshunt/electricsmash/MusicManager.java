package me.jameshunt.electricsmash;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;


/**
 * modified by James on 10/16/2014.
 * credit goes to http://www.rbgrn.net/content/307-light-racer-20-days-61-64-completion
 */
public class MusicManager {
    static boolean musicOn=true;
   // static boolean looping=variables.prefs.getBoolean("looping", true);
    static boolean restart=false;
    static MediaPlayer mp;
    private static final String TAG = "MusicManager";
    public static int MUSIC=0;
    public static int spot;
    private static int currentMusic = -1;
    private static int previousMusic = -1;


    public static void start(Context context, int music) {
        start(context, music, false);
    }

    public static void check(final Context context)
    {
        if(mp!=null) {
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    MUSIC++;

                    //MusicManager.mp=null;
                    start(context, MUSIC, true);
                }
            });
        }
    }


    public static void start(Context context, int music, boolean force) {
        if (!force && currentMusic > -1) {
// already playing some music and not forced to change
            return;
        }
        if (currentMusic == music) {
// already playing this music
            return;
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
// playing some other music, pause it and change
            pause();
        }
        currentMusic = music;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
        mp = null;
        if (mp != null) {
            if (!mp.isPlaying()) {
                mp.start();
            }
        } else {
            if (music == 0) {
                mp = MediaPlayer.create(context, R.raw.es1);
            } else if (music == 1) {
                mp = MediaPlayer.create(context, R.raw.es2);
            } else if (music == 2) {
                mp = MediaPlayer.create(context, R.raw.es3);
            } else if(music == 3){
                mp = MediaPlayer.create(context, R.raw.es4);
            } else if (music == 4) {
                mp = MediaPlayer.create(context, R.raw.es5);
            } else if (music == 5) {
                mp = MediaPlayer.create(context, R.raw.es6);
            } else if (music == 6) {
                mp = MediaPlayer.create(context, R.raw.es7);
            } else if (music == 7) {
                mp = MediaPlayer.create(context, R.raw.es8);
            } else if (music == 8) {
                mp = MediaPlayer.create(context, R.raw.es9);
            } else if (music == 9) {
                mp = MediaPlayer.create(context, R.raw.es10);
            }
            else {
                mp = MediaPlayer.create(context, R.raw.es1);
                music=0;
            }

            MUSIC=music;
            //players.put(spot, mp);
            spot++;
            if (mp == null) {
                Log.e(TAG, "player was not created successfully");
            } else {
                try {
                        mp.setLooping(true);
                    mp.start();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }




    public static void pause() {

        if(mp==null)
        {
            return;
        }

        if(mp.isPlaying())
        {
            mp.pause();
        }
// previousMusic should always be something valid
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
        currentMusic = -1;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
    }

    public static void release() {
        Log.d(TAG, "Releasing media players");
        if(mp==null)
            return;

        mp.reset();
        mp.release();
        mp=null;



        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
        currentMusic = -1;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
    }
}

package me.jameshunt.electricsmash;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by James on 12/17/2014.
 */
public class LoadGame extends AsyncTask<Void, Void, Void> {

    Cursor curse;
    int[] xValues = new int[8];
    int[] yValues = new int[16];
    int wid;
    int ht;
    int patternW;
    GameGrid gameGrid;
    //static DBAdapter adapter;

    public LoadGame(GameGrid gameGrid, Cursor curse, int[] xValues, int[] yValues, int wid, int ht, int patternW) {
        this.curse = curse;
        this.xValues = xValues;
        this.yValues = yValues;
        this.wid = wid;
        this.ht = ht;
        this.patternW = patternW;

        this.gameGrid = gameGrid;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Tile[][] grid = new Tile[8][16];

        if (curse.moveToFirst()) {
            do {
                // Process the data:
                //int id = cursor.getInt(DBAdapter.COL_ROWID);
                int xD = curse.getInt(DBAdapter.COL_X);
                int yD = curse.getInt(DBAdapter.COL_Y);
                int imageLocationD = curse.getInt(DBAdapter.COL_IMAGELOCATION);
                int howKilledD = curse.getInt(DBAdapter.COL_HOWKILLED);


                //gridTiles[xD][yD].
                xValues[xD] = wid / 8 * xD;
                yValues[yD] = intValue(ht / 4.5 + (patternW * (yD - 8)) / 2);
                grid[xD][yD] = new Tile(wid / 8 * xD, intValue(ht / 4.5 + (patternW * (yD - 8)) / 2), imageLocationD, wid, ht, xValues, yValues);
                grid[xD][yD].howKilled = howKilledD;
            } while (curse.moveToNext());

            //myDb.deleteAll();


        }
        GameGrid.PlaceholderFragment.gridTiles=grid;
        variables.doneLoadingMap=true;
        if(gameGrid.getFragInfo().getMyDb().checkOpen()) {
            gameGrid.getFragInfo().getMyDb().deleteAll();
            Log.d("","save deleted");
        }

        return null;
    }

    public int intValue(double num) {
        return (int) num;
    }
}
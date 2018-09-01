package me.jameshunt.electricsmash;

import android.os.AsyncTask;

/**
 * Created by James on 1/14/2015.
 */
public class SaveGame extends AsyncTask<Void, Void, Void> {

    GameGrid gameGrid;

    public SaveGame(GameGrid gameGrid) {
        this.gameGrid = gameGrid;
    }

    @Override
    protected Void doInBackground(Void... params) {

        boolean once=false;
        variables.currentlySavingMap=true;
        //Log.d("save", "game");
        for (int x = 0; x < 8; x++) {
            for (int y = 8; y < 16; y++) {
                //if(GameGrid.PlaceholderFragment.gridTiles[x][y]==null||GameGrid.PlaceholderFragment.gridTiles[x][y].updated) {
                    GameGrid.PlaceholderFragment.gridTiles[x][y].updated=false;
                    if(GameGrid.PlaceholderFragment.newTileSet)
                    {
                        if(once==false)
                        {
                            gameGrid.getFragInfo().getMyDb().deleteAll();
                            once=true;
                        }
                        /*Log.d(GameGrid.PlaceholderFragment.myDb+"","nullcrap "+x+" "+y);
                        Log.d(GameGrid.PlaceholderFragment.gridTiles[x][y]+"","nullcrap");
                        Log.d(GameGrid.PlaceholderFragment.gridTiles[x][y].howKilled+"","nullcrap");
                        Log.d(GameGrid.PlaceholderFragment.gridTiles[x][y].imageLocation+"","nullcrap");*/
                        if(GameGrid.PlaceholderFragment.gridTiles[x][y]!=null||gameGrid.getFragInfo().getMyDb()!=null)
                            gameGrid.getFragInfo().getMyDb().insertRow(x, y, GameGrid.PlaceholderFragment.gridTiles[x][y].imageLocation, GameGrid.PlaceholderFragment.gridTiles[x][y].howKilled);

                    }
                    else {
                        gameGrid.getFragInfo().getMyDb().updateRow(x, y, GameGrid.PlaceholderFragment.gridTiles[x][y].imageLocation, GameGrid.PlaceholderFragment.gridTiles[x][y].howKilled);
                    }

                    //Log.d("save", "game saved");
               // }

            }
        }
        GameGrid.PlaceholderFragment.newTileSet=false;
        variables.currentlySavingMap=false;
        return null;
    }

    public int intValue(double num) {
        return (int) num;
    }
}
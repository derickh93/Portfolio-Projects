package com.example.cooli.dericksmatchinggame;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Title: DericksMatchingGame
 * Filename: Main2Activity.java
 * Date Written: April 1, 2018
 * Due Date: April 5, 2018
 * Description: Defines the methods that will be used to create the hard mode for a basic memory game.
 * In the hard mode there is a delay of 1000 mili secs before wrong selections are flipped back over.
 * There are methods to save and restore the instance of the application. There is a method to
 * reset the game to the easy level. There is also an onClick method to determine what happens upon each click.
 * There are also methods to determine what happens once the user clicks on the menu options.
 * Once the high level has been completed the user will have the option to reset the game.
 *
 * @author Derick Hansraj
 */
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    //instance variables
    ImageButton buttonArr[] = new ImageButton[20];
    ArrayList<Integer> array_image = new ArrayList<Integer>();
    int clickCount = 0;
    int posOne = 0;
    int posTwo = 0;
    ImageButton clickOne;
    ImageButton clickTwo;
    int matches = 0;

    /**
     * onCreate method -- This method will define and load the operations performed upon the launch
     * of the application.
     *
     * @param savedInstanceState The instance that is passed.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.strFloatingButton, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        for (int i = 0; i < 20; i++) {
            ImageButton current = (ImageButton) findViewById(getResources().getIdentifier("imageButton" + i, "id",
                    this.getPackageName()));
            buttonArr[i] = current;
        }

        TypedArray imgs = getResources().obtainTypedArray(R.array.imagesArray);
        for (int i = 0; i < 20; i++) {
            int current = imgs.getResourceId(i, -1);
            array_image.add(current);
        }
        Collections.shuffle(array_image);

        TextView textView = (TextView) findViewById(R.id.txtTimer);

    }

    /**
     * onCreateOptionsMenu method -- This method will define and load the operations that will create
     * the desired menu options.This will inflate the menu.
     *
     * @param menu The menu that is passed.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * onOptionsItemSelected method -- This method will determine which item was selected from the
     * options menu.
     *
     * @param item The item that is passed.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Reset) {
            reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onClick method -- Once this method has been assigned to a button the button
     * responds to the onClick events defined. This method keeps track of the current
     * button that has been clicked. If that button is clicked it will determine whether there
     * is a match and change the button to the appropriate image. Once the game is complete the
     * game will display a toast and ask the user to reset to play again.
     *
     * @param view the current event that is drawn and being handled.
     */
    public void onClick(View view) {

        ImageButton image = (ImageButton) findViewById(view.getId());
        int pos = Integer.parseInt(image.getTag().toString());
        image.setImageResource(array_image.get(pos));
        image.setClickable(false);
        if(clickCount == 0) {
            posOne = pos;
            clickOne = image;
            clickCount++;
        }
        else if (clickCount == 1) {
            posTwo = pos;
            clickTwo = image;

            if(array_image.get(posOne).toString().equalsIgnoreCase(array_image.get(posTwo).toString())){
                buttonArr[posOne].setImageResource(R.drawable.gameimagecomplete);
                buttonArr[posTwo].setImageResource(R.drawable.gameimagecomplete);
                buttonArr[posOne].setClickable(false);
                buttonArr[posOne].setClickable(false);
                matches++;

            }
            else {
                for (int i = 0; i < 20; i++) {
                    buttonArr[i].setClickable(false);
                }
                // delay setting the images back to the default so the user can see what was chosen
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        // set the image back to the default
                        buttonArr[posOne].setImageResource(R.drawable.gameimagedefault);
                        buttonArr[posTwo].setImageResource(R.drawable.gameimagedefault);
                        for (int i = 0; i < 20; i++) {
                            buttonArr[i].setClickable(true);
                        }
                    }
                }, 1000);
            }
            clickCount = 0;
        }
        if(matches == 10) {
            Toast.makeText(getApplicationContext(), R.string.strCompleteHard, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 20; i++) {
                buttonArr[i].setClickable(false);
            }
        }
    }

    /**
     * reset method -- This method will reset the game. This will be accomplished by displaying a toast
     * indicating that the game has been reset. Then load the MainActivity which is the easy version of
     * the game.
     */
    public void reset() {
        startActivity(new Intent(Main2Activity.this, MainActivity.class));

    }

    /**
     * onSaveInstanceState method -- This method will save the user selected state of the activity.
     * This is done by saving desired variables and a super call to the onRestoreInstanceState method.
     *
     * @param savedInstanceState The bundle that will be passed.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("click",clickCount);
        savedInstanceState.putInt("posOne",posOne);
        savedInstanceState.putInt("posTwo",posTwo);
        savedInstanceState.putInt("matches", matches);
        savedInstanceState.putIntegerArrayList("list",array_image);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * onRestoreInstanceState method -- This method will restore the user selected state of the
     * activity. This is done by restoring the saved variables along with a super call to the
     * onRestoreInstanceState method.
     *
     * @param restoredInstanceState The bundle that will be passed.
     */
    public void onRestoreInstanceState(Bundle restoredInstanceState) {
        clickCount = restoredInstanceState.getInt("click");
        posOne = restoredInstanceState.getInt("posOne");
        posTwo = restoredInstanceState.getInt("posTwo");
        matches = restoredInstanceState.getInt("matches");
        array_image = restoredInstanceState.getIntegerArrayList("list");
        super.onRestoreInstanceState(restoredInstanceState);
    }

    /**
     * onBackPressed method -- This method will determine what happens when the back button is
     * clicked.
     */
    public void onBackPressed() {
    }
}
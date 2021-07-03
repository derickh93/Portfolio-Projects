package com.example.theboss.tictactoe;

//import statements

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Title: TicTacToe
 * Filename: MainActivity.java
 * Date Written: February 11, 2018
 * Due Date: February 12, 2018
 * Description: Defines the methods that will be used to create a basic tic tac toe
 * user interface. There will be 9 buttons in total. The nine buttons will represent
 * the tic tac toe board. There are two spinners located in the menu  along with a reset
 * button. The first spinner will allow player x to choose a color. The second spinner will
 * allow player o to choose a color. Once the first button is clicked an x will be placed there
 * and further clicks leads to an alternating input between 'X' and 'O'. If the reset button is
 * clicked the game will restart.
 *
 * @author Derick Hansraj
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //instance variable to be used
    int count = 0;
    Button buttonArr[] = new Button[9];
    String colorX = "BLACK";
    String colorO = "BLACK";
    String firstLetter = "X";
    String secondLetter = "O";
    Spinner spinner;
    Spinner spinner2;


    /**
     * onCreate method -- This method will define and load the operations performed upon the launch
     * of the application.
     *
     * @param savedInstanceState The instance that is passed.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Loop that will put all 9 buttons into an array of buttons
        for (int i = 0; i < 9; i++) {
            Button current = (Button) findViewById(getResources().getIdentifier("button" + i, "id",
                    this.getPackageName()));
            buttonArr[i] = current;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Derick Hansraj\nn00827531@students.ncc.edu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * onCreateOptionsMenu method -- This method will define and load the operations that will create
     * the desired menu options.This will inflate the menu as well as add two spinners to the menu.
     *
     * @param menu The menu that is passed.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //adds first spinner

        MenuItem spinnerItem = menu.findItem(R.id.color_spinnerX);

        spinner = (Spinner) spinnerItem.getActionView();

        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        //adds second spinner

        MenuItem spinnerItem2 = menu.findItem(R.id.color_spinnerO);

        spinner2 = (Spinner) spinnerItem2.getActionView();

        spinner2.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter2);

        return true;
    }

    /**
     * onOptionsItemSelected method -- This method will determine which item was selected from the
     * options menu.
     *
     * @param item The item that is passed.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        //determine which option was selected
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_Reset) {
            reset();
            return true;
        }
        if (id == R.id.color_spinnerX) {
            return true;
        }
        if (id == R.id.color_spinnerO) {
            return true;
        }

        if (id == R.id.change_Letter) {
            final String[] choices = getResources().getStringArray(R.array.letter_array);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose your letters...");
            builder.setItems(choices, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String first = String.valueOf(choices[which].charAt(0));
                    String second = String.valueOf(choices[which].charAt(6));
                    for (int k = 0; k < 9; k++) {
                        if (buttonArr[k].getText().toString().equals(firstLetter))
                            buttonArr[k].setText(first);

                        if (buttonArr[k].getText().toString().equals(secondLetter))
                            buttonArr[k].setText(second);
                    }
                    firstLetter = String.valueOf(choices[which].charAt(0));
                    secondLetter = String.valueOf(choices[which].charAt(6));
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * reset method -- This method will reset the game. This will be accomplished by displaying a toast
     * indicating that the game has been reset. Then each button will be set blank and clickable again.
     * The count will also be reset to 0 so that each click alternates in the correct order.
     */
    public void reset() {
        Toast resetStr = Toast.makeText(getApplicationContext(), "Game has been reset!!!", Toast.LENGTH_SHORT);
        resetStr.setGravity(Gravity.CENTER, 50, 50);
        resetStr.show();

        for (int i = 0; i < 9; i++) {
            buttonArr[i].setText(R.string.empty_Str);
            buttonArr[i].setClickable(true);
        }
        count = 0;
    }

    /**
     * onClick method -- Once this method has been assigned to a button the button
     * responds to the onClick events defined. This method keeps track of the current
     * button that has been clicked. If that button is the reset button it will reset
     * the game. If the button is one of the other 9 buttons it will change the state
     * according to the alternating pattern. If a button is already clicked it will not be changed.
     * Once it is possible to have a winner the checkWinner method will be called.
     *
     * @param view the current event that is drawn and being handled.
     */
    public void onClick(View view) {
        //variables
        Button btn = (Button) findViewById(view.getId());
        String buttonText = btn.getText().toString();
        //if statement to determine whether an x or o goes into a button once it is clicked
        if (count < 9) {
            if (buttonText.equals("")) {
                if (count % 2 == 0) {
                    btn.setText(firstLetter);
                    btn.setTextColor(Color.parseColor(colorX));
                    btn.setClickable(false);
                    count++;
                } else {
                    btn.setText(secondLetter);
                    btn.setTextColor(Color.parseColor(colorO));
                    btn.setClickable(false);
                    count++;
                }
            }
            if (count >= 5)
                checkWinner();
        }
    }

    /**
     * checkWinner method -- This method will be called once it is possible to have a winner. There
     * are 8 possible ways to win the game. Once it is determined that there is a winner a message
     * will be displayed using toast to indicate the winner. If the game is a draw a message will indicate
     * so and the game will be reset.
     */
    private void checkWinner() {
        int firstWin;
        int secondWin;
        int[][] checkArr = {{2, 4, 6}, {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}};
        boolean bool = true;
        while (bool) {
            for (int i = 0; i < checkArr.length; i++) {
                firstWin = 0;
                secondWin = 0;
                for (int j = 0; j < checkArr[i].length; j++) {
                    if (buttonArr[checkArr[i][j]].getText().toString().equalsIgnoreCase(firstLetter))
                        firstWin++;
                    else if (buttonArr[checkArr[i][j]].getText().toString().equalsIgnoreCase(secondLetter))
                        secondWin++;
                }
                if (firstWin == 3) {
                    Toast firstWinStr = Toast.makeText(getApplicationContext(), "First Player Won!!!", Toast.LENGTH_SHORT);
                    firstWinStr.setGravity(Gravity.CENTER, 50, 50);
                    firstWinStr.show();
                    reset();
                    bool = false;
                }
                if (secondWin == 3) {
                    Toast secondWinStr = Toast.makeText(getApplicationContext(), "Second Player Won!!!", Toast.LENGTH_SHORT);
                    secondWinStr.setGravity(Gravity.CENTER, 50, 50);
                    secondWinStr.show();
                    reset();
                    bool = false;
                }
            }
            bool = false;
        }
        if (count == 9) {
            Toast drawstr = Toast.makeText(getApplicationContext(), "Draw!!!", Toast.LENGTH_SHORT);
            drawstr.setGravity(Gravity.CENTER, 50, 50);
            drawstr.show();
            reset();
        }
    }

    /**
     * onItemSelected method -- This method will be once a item has been selected from either one of
     * the spinners. The proper code will execute depending on which one was selected. If the first
     * once is selected player x's color will change. If the second one is selected player o's
     * color will change.
     *
     * @param adapterView the adapterView selected
     * @param view        the current view
     * @param i           the int position of the item selected
     * @param l           the position of the item selected
     */
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        if (adapterView == spinner) {
            for (int k = 0; k < 9; k++) {
                if (buttonArr[k].getText().toString().equalsIgnoreCase("X"))
                    buttonArr[k].setTextColor(Color.parseColor(item));
                colorX = item;
            }
        }

        if (adapterView == spinner2) {
            for (int k = 0; k < 9; k++) {
                if (buttonArr[k].getText().toString().equalsIgnoreCase("O"))
                    buttonArr[k].setTextColor(Color.parseColor(item));
                colorO = item;
            }
        }
    }

    /**
     * onNothingSelected method -- This method will indicate what to be done when nothing is selected
     * from the menu options.
     * so and the game will be reset.
     *
     * @param adapterView
     */
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("colorX",colorX);
        savedInstanceState.putString("colorO",colorO);
        savedInstanceState.putString("firstLetter",firstLetter);
        savedInstanceState.putString("secondLetter",secondLetter);
        super.onSaveInstanceState(savedInstanceState);

    }

    public void onRestoreInstanceState(Bundle restoredInstanceState) {
        restoredInstanceState.getString("colorX");
        restoredInstanceState.getString("colorO");
        restoredInstanceState.getString("firstLetter");
        restoredInstanceState.getString("secondLetter");
        super.onRestoreInstanceState(restoredInstanceState);
    }
}

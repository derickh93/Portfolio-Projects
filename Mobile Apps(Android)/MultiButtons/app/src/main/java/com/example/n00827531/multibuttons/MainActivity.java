package com.example.n00827531.multibuttons;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Button btns[];
    int clickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btns = new Button[4];
        int btnId = R.id.button0;

        for(int i = 0; i < btns.length; i++) {
            btns[i] = (Button) findViewById(btnId);
            btns[i].setOnClickListener(this);
            btnId++;
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem spinnerItem = menu.findItem(R.id.color_spinner);

        Spinner spinner = (Spinner) spinnerItem.getActionView();

        spinner.setOnItemSelectedListener(this); //for now, ignore the error you get

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.colors_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your background color...");

        String[] choices = {"Yellow", "Green"};

        builder.setItems(choices, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    for (int j = 0; j < 4; j++) {
                        Button button = (Button) findViewById(getResources().getIdentifier("button" + j, "id",
                                getPackageName()));
                        button.setBackgroundColor(Color.YELLOW);
                    }
                }

                if (which == 1) {
                    for (int j = 0; j < 4; j++) {
                        Button button = (Button) findViewById(getResources().getIdentifier("button" + j, "id",
                                getPackageName()));
                        button.setBackgroundColor(Color.GREEN);
                    }
                }
            }
        });
                AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_reset:
                reset();
                return true;
            case R.id.color_change:
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void reset() {
        for (int j = 0; j < 4; j++) {
            Button button = (Button) findViewById(getResources().getIdentifier("button" + j, "id",
                    this.getPackageName()));
            button.setTextColor(Color.BLACK);
            button.setBackgroundResource(android.R.drawable.btn_default);
            btns[j].setClickable(true);
            clickCounter = 0;
            Toast toast = Toast.makeText(getApplicationContext(), "Reset!", Toast.LENGTH_SHORT);
            toast.show();

        }

    }

        @Override
        public void onClick (View view){
            int id = view.getId();
            switch (id) {
                case R.id.button0:
                    btns[0].setBackgroundColor(Color.BLUE);
                    btns[0].setClickable(false);
                    clickCounter++;
                    break;
                case R.id.button1:
                    btns[1].setBackgroundColor(Color.RED);
                    btns[1].setClickable(false);
                    clickCounter++;
                    break;
                case R.id.button2:
                    btns[2].setBackgroundColor(Color.YELLOW);
                    btns[2].setClickable(false);
                    clickCounter++;
                    break;
                case R.id.button3:
                    btns[3].setBackgroundColor(Color.GREEN);
                    btns[3].setClickable(false);
                    clickCounter++;
                    break;
            }
            if (clickCounter == 4) {
                Toast toast = Toast.makeText(getApplicationContext(), "You've clicked them all!", Toast.LENGTH_SHORT);
                toast.show();
                reset();

            }
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (i == 0) {
            for (int j = 0; j < 4; j++) {
                Button button = (Button) findViewById(getResources().getIdentifier("button" + j, "id",
                        this.getPackageName()));
                button.setTextColor(Color.BLACK);
            }
        }

        if (i == 1) {
            for (int j = 0; j < 4; j++) {
                Button button = (Button) findViewById(getResources().getIdentifier("button" + j, "id",
                        this.getPackageName()));
                button.setTextColor(Color.MAGENTA);
            }
        }

        if (i == 2) {
            for (int j = 0; j < 4; j++) {
                Button button = (Button) findViewById(getResources().getIdentifier("button" + j, "id",
                        this.getPackageName()));
                button.setTextColor(Color.CYAN);
            }
        }
    }

        @Override
        public void onNothingSelected (AdapterView < ? > adapterView) {

        }
    }

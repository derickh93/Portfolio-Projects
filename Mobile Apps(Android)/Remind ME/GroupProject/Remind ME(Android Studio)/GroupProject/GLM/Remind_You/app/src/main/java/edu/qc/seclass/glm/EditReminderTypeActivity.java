package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class EditReminderTypeActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    Button btnUpt;
    EditText txtName, myEditText;
    Intent intent;
    Bundle bundle;
    int pos;

    ReminderType rem;
    public ArrayList<ReminderType> allReminderT;
    Realm mRealm;
    ListView listView;
    ArrayAdapter aAdapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rem = (ReminderType)getIntent().getSerializableExtra("ReminderType"); //Obtaining data
            pos = (Integer)getIntent().getIntExtra("positionT",-1); //Obtaining data
            allReminderT = (ArrayList)getIntent().getSerializableExtra("typeList"); //Obtaining data
        }
        setContentView(R.layout.activity_edit_reminder_type);
        listView = (ListView) findViewById(R.id.listView6);

        btnUpt = (Button) findViewById(R.id.save);

        txtName = (EditText) findViewById(R.id.in_name);

        btnUpt.setOnClickListener(this);

        intent = this.getIntent();
        bundle = intent.getExtras();

        myEditText = (EditText) findViewById(R.id.in_name);

        txtName.setText(rem.getName());
        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getDefaultInstance();

        //**************************************************************************************
        // getting Reminder Class query
        RealmQuery<Reminder> queryRem = mRealm.where(Reminder.class);
        queryRem = queryRem.contains("type", rem.getName());
        final RealmResults<Reminder> results1 = queryRem.findAll();
        // int variable to told result1 size
        int sizeQuery = results1.size();

        String reminderName[] = new String [sizeQuery] ;

        for(int i = 0; i < sizeQuery; i++) {
            reminderName[i] = (results1.get(i).getName());
        }

        aAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reminderName);
        listView.setAdapter(aAdapt);
        //***************************************************************************************
    }
    @Override
    public void onClick(View v) {

        if (v == btnUpt) {
            String tName = txtName.getText().toString();
            ReminderType newReminderType = new ReminderType(tName);
            int id = allReminderT.get(pos).getReminderTypeID();
            updateReminderType(id,newReminderType);
            allReminderT.set(pos,newReminderType);
            ArrayAdapter aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allReminderT);
            ReminderTypeActivity.listView.setAdapter(aAdapt);
            ReminderTypeActivity.typeList = allReminderT;
            this.finish();
        }
    }


    public void updateReminderType(final int id,final ReminderType uptRem) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ReminderType update = realm.where(ReminderType.class).equalTo("reminderTypeID", id).findFirst();
                update.setName(uptRem.getName());





                RealmQuery<Reminder> queryRem = mRealm.where(Reminder.class);
                queryRem = queryRem.contains("type", rem.getName());
                final RealmResults<Reminder> results1 = queryRem.findAll();
                // int variable to told result1 size
                int sizeQuery = results1.size();

                for(int i = 0; i < sizeQuery; i++) {
                    results1.setString("type", txtName.getText().toString());
                }
            }
        });
    }
}

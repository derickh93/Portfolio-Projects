package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.*;
import android.view.View;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Serializable, CompoundButton.OnCheckedChangeListener {
    // initialize variables
    public static ListView listView;
    private Button btnAdd, btnViewList, btnAddType, btnEditRem,btnHome;
    public static  ArrayList<Reminder> allReminders;
    public ArrayList<ReminderList> allLists = new ArrayList<>();
    public ArrayList<ReminderType> typeList = new ArrayList<>();
    Realm mRealm;
    Reminder remind;
    ReminderList remindList;
    ReminderAdapter2 versionAdapter;
    public ArrayList<Reminder> checkList = new ArrayList<Reminder>();
    Reminder temp;
    static String[] reminderName;
    ArrayAdapter<Reminder> aAdapt;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViewMain);
        btnAdd = (Button) findViewById(R.id.btnAddReminder);
        btnViewList = (Button) findViewById(R.id.gotolist);
        btnAddType = (Button) findViewById(R.id.gototypes);
        btnEditRem = (Button) findViewById(R.id.btnEditReminder);
        btnHome = (Button) findViewById(R.id.gotoall);
        allReminders = new ArrayList<>();

        //initializing Realm ORM
        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getDefaultInstance();

        // getting Reminder Class query
        RealmQuery<Reminder> queryRem = mRealm.where(Reminder.class);
        // finding all Reminder Class query and assigning them to result1 list
        RealmResults<Reminder> results1 = queryRem.findAll();
        // int variable to told result1 size
        int sizeQuery = results1.size();

        // Bundle to be able to transfer data to different activity
        Bundle extras = getIntent().getExtras();
       // if (extras != null) {
           // allReminders = (ArrayList) getIntent().getSerializableExtra("listRem"); //Obtaining data
        //}
       // else{

            // converting all Realmlist to ArrayList *needed to transfer data between activities
            for (int i = 0; i < sizeQuery; i++) {

                remind = new Reminder(results1.get(i));
                allReminders.add(remind);
            }
        //}

        // Obtaining data from different activities
        if (extras != null) {
            allLists = (ArrayList) getIntent().getSerializableExtra("allList"); //Obtaining data
        }

        if (extras != null) {
            typeList = (ArrayList) getIntent().getSerializableExtra("typeList"); //Obtaining data
        }

        // Inserting data to listView to show the user
        //aAdapt = new ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1, allReminders);
        //listView.setAdapter(aAdapt);

        // getting Reminder Class query
        RealmQuery<Reminder> queryRem3 = mRealm.where(Reminder.class);
        final RealmResults<Reminder> results3 = queryRem3.findAll();
        // int variable to told result1 size
        int sizeQuery3 = results3.size();

        reminderName = new String [sizeQuery3] ;

        for(int i = 0; i < sizeQuery3; i++) {
            reminderName[i] = (results3.get(i).getName());
        }


        // getting Reminder Class query
        RealmQuery<Reminder> queryRem2 = mRealm.where(Reminder.class);
        // finding all Reminder Class query and assigning them to result1 list
        queryRem2 = queryRem2.in("name", reminderName, Case.SENSITIVE);
        // int variable to told result1 size

        final RealmResults<Reminder> results2 = queryRem2.findAll();

        int sizeQuery2 = results2.size();

        for (int i = 0; i < sizeQuery2; i++) {

            temp = new Reminder(results2.get(i));
            checkList.add(temp);
        }

        versionAdapter = new ReminderAdapter2(MainActivity.this,R.layout.item_row,checkList);
        listView.setAdapter(versionAdapter);

        aAdapt = new ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1,allReminders);

        btnEditRem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                aAdapt = new ArrayAdapter<Reminder>(MainActivity.this, android.R.layout.simple_list_item_1,allReminders);
                listView.setAdapter(aAdapt);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkList.clear();
                // getting Reminder Class query
                RealmQuery<Reminder> queryRem2 = mRealm.where(Reminder.class);
                // finding all Reminder Class query and assigning them to result1 list
                queryRem2 = queryRem2.in("name", reminderName, Case.SENSITIVE);
                // int variable to told result1 size

                final RealmResults<Reminder> results2 = queryRem2.findAll();

                int sizeQuery2 = results2.size();

                for (int i = 0; i < sizeQuery2; i++) {

                    temp = new Reminder(results2.get(i));
                    checkList.add(temp);
                }
                versionAdapter = new ReminderAdapter2(MainActivity.this,R.layout.item_row,checkList);
                listView.setAdapter(versionAdapter);
            }
        });

        // Add button. Moves to AddReminderActivity. Bundle exists to transfer data over
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), AddReminderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList", typeList);
                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);


            }
        });

        // List Button. Moves to ListActivity, showing the list of ReminderList class
        btnViewList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), ListActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList", typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);

            }
        });

        //Type button (Name bit misleading). moves to show list of Reminder Types available.
        btnAddType.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), ReminderTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList", typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);

            }
        });


        // ListView. this is where list of Reminder is created for the user.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activity2Intent = new Intent(getApplicationContext(), EditReminderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reminder", allReminders.get(position));
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList", typeList);

                bundle.putInt("position", position);
                activity2Intent.putExtras(bundle);
                startActivity(activity2Intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void updateReminder(final int id,final Reminder uptRem) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Reminder update = realm.where(Reminder.class).equalTo("reminderID", id).findFirst();
                update.setIsSelected(uptRem.getIsSelected());
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int position = listView.getPositionForView(compoundButton);

        if (position != ListView.INVALID_POSITION){
            Reminder reminder = checkList.get(position);
            reminder.setIsSelected(b);


            Calendar d = Calendar.getInstance();
            d.set(Calendar.HOUR_OF_DAY, reminder.getHour());
            d.set(Calendar.MINUTE, reminder.getMinute());
            d.set(Calendar.DATE,reminder.getDay());
            d.set(Calendar.MONTH,reminder.getMonth());
            d.set(Calendar.YEAR,reminder.getYear());
            d.set(Calendar.SECOND,0);
            long timeNow = d.getTimeInMillis();
            Log.d("alarmtime","MainActivity Time: " + " " + reminder.getHour() + " " + reminder.getMinute()
                    + " " + reminder.getDay() + " " + reminder.getMonth()  + " " + reminder.getYear() + "\n" + timeNow);


            int primId = checkList.get(position).getReminderID();

            if(b == true){
                setAlarm(timeNow, primId);
            }
            if(b == false) {
                cancelAlarm(primId);
            }

            updateReminder(primId,reminder);

        }
    }

    private void setAlarm(long timeInMilli, int x)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, x, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilli, pendingIntent);

        Toast.makeText(this, "This Alarm is set", Toast.LENGTH_SHORT).show();
    }

    private void cancelAlarm(int x)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, x, intent, 0);

        alarmManager.cancel(pendingIntent);

        Toast.makeText(this, "This Alarm is canceled", Toast.LENGTH_SHORT).show();
    }
}

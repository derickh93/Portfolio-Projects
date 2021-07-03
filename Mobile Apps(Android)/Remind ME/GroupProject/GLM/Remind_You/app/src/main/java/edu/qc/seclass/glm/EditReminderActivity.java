package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditReminderActivity extends AppCompatActivity implements
        View.OnClickListener, Serializable {

    Button btnDatePicker, btnTimePicker,btnDel,btnUpt;
    EditText txtDate, txtTime,txtName,myEditText;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int month,day,thisYear;
    Intent intent;
    Bundle bundle;
    int passedPos;
    Realm mRealm;
    AutoCompleteTextView txtType;
    long timeNow = 0;

    Reminder rem;
    public ArrayList<Reminder> allReminders;
    static String[] reminderType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getDefaultInstance();
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                rem = (Reminder)getIntent().getSerializableExtra("reminder"); //Obtaining data
                passedPos = (Integer)getIntent().getIntExtra("position",-1); //Obtaining data
                allReminders = (ArrayList)getIntent().getSerializableExtra("listRem"); //Obtaining data
            }
            setContentView(R.layout.activity_edit_reminder);


        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        btnDel = (Button) findViewById(R.id.btn_del);
        btnUpt = (Button) findViewById(R.id.btn_upd);


        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);
        myEditText = (EditText) findViewById(R.id.in_type);


        txtName = (EditText) findViewById(R.id.in_name);
        txtType = (AutoCompleteTextView) findViewById(R.id.in_type);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnUpt.setOnClickListener(this);

        intent = this.getIntent();
        bundle = intent.getExtras();

        txtDate.setText(rem.getDate());
        txtName.setText(rem.getName());
        txtTime.setText(rem.getTime());
        txtType.setText(rem.getType());
        day = rem.getDay();
        thisYear = rem.getYear();
        month = rem.getMonth();


        //***************************************************************************
        // getting Reminder Class query
        RealmQuery<ReminderType> queryRem = mRealm.where(ReminderType.class);
        final RealmResults<ReminderType> results1 = queryRem.findAll();
        // int variable to told result1 size
        int sizeQuery = results1.size();

        reminderType = new String [sizeQuery] ;

        for(int i = 0; i < sizeQuery; i++) {
            reminderType[i] = (results1.get(i).getName());
        }
        //*****************************************************************************

        ArrayAdapter<String> adapterAuto2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, reminderType);
        txtType.setAdapter(adapterAuto2);

        ImageView image_type = (ImageView)findViewById(R.id.image_type);

        image_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                txtType.showDropDown();
            }
        });


        myEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event)
            {
                boolean handled=false;

                // Some phones disregard the IME setting option in the xml, instead
                // they send IME_ACTION_UNSPECIFIED so we need to catch that
                if(EditorInfo.IME_ACTION_DONE==actionId || EditorInfo.IME_ACTION_UNSPECIFIED==actionId)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    btnDatePicker.performClick();

                    handled=true;
                }

                return handled;
            }
        });

    }



    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();

           mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            if(year < c.get(Calendar.YEAR) || monthOfYear < c.get(Calendar.MONTH) || dayOfMonth < c.get(Calendar.DAY_OF_MONTH)){
                                Toast.makeText(getApplicationContext(), "Selected date has passed, Please choose a future date", Toast.LENGTH_LONG).show();
                            }
                            else {
                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                btnTimePicker.performClick();
                                month = monthOfYear;
                                day = dayOfMonth;
                                thisYear = year;
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            rem.getTime();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if(hourOfDay < c.get(Calendar.HOUR_OF_DAY) || minute < c.get(Calendar.MINUTE)){
                                Toast.makeText(getApplicationContext(), "Selected time has passed, Please choose a future time", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Calendar d = Calendar.getInstance();
                                txtTime.setText(hourOfDay + ":" + minute);
                                d.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                d.set(Calendar.MINUTE, minute);
                                d.set(Calendar.DATE, day);
                                d.set(Calendar.MONTH, month);
                                d.set(Calendar.YEAR, thisYear);
                                d.set(Calendar.SECOND,0);
                                timeNow = d.getTimeInMillis();
                                Log.d("alarmtime", "EditActivity Time: " + timeNow + "\n" + hourOfDay + " " + minute + " " + day + " " + month + " "
                                        + thisYear);
                            }
                        }
                    }, mHour, mMinute, false);

            timePickerDialog.show();
        }

        if (v == btnUpt) {
            String tName = txtName.getText().toString();
            String tType = txtType.getText().toString();
            String tDate = txtDate.getText().toString();
            String tTime = txtTime.getText().toString();
            if(tName.length() == 0 || tType.length() == 0){
                Toast.makeText(this, "Reminder name and Reminder type cannot be blank", Toast.LENGTH_SHORT).show();
            }
            else {
                Reminder newReminder = new Reminder(tName, tType, tDate, tTime, rem.getIsSelected());
                newReminder.setReminderID(rem.getReminderID());

                int primId = allReminders.get(passedPos).getReminderID();
                Log.d("primid", "primid: " + primId + " " + tName + " " + tType + " " + tDate + " " + tTime);
                updateReminder(primId, newReminder);

                allReminders.set(passedPos, newReminder);
                ArrayAdapter aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allReminders);
                MainActivity.listView.setAdapter(aAdapt);
                MainActivity.allReminders = allReminders;


                Calendar d = Calendar.getInstance();
                d.set(Calendar.HOUR_OF_DAY, rem.getHour());
                d.set(Calendar.MINUTE, rem.getMinute());
                d.set(Calendar.DATE, day);
                d.set(Calendar.MONTH, month);
                d.set(Calendar.YEAR, thisYear);
                d.set(Calendar.SECOND,0);
                timeNow = d.getTimeInMillis();
                updateAlarm(timeNow, primId);
                this.finish();
            }
        }

        if (v == btnDel) {
            Reminder remove = allReminders.get(passedPos);
            final Reminder toRemove = mRealm.where(Reminder.class).equalTo("reminderID",remove.getReminderID()).findFirst();
            cancelAlarm(toRemove.getReminderID());
            allReminders.remove(passedPos);
            deleteReminder(toRemove);
            ArrayAdapter aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allReminders);
            MainActivity.listView.setAdapter(aAdapt);
            MainActivity.allReminders = allReminders;
            this.finish();
        }
    }


    public void deleteReminder(final Reminder delRem) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (delRem != null) {
                    delRem.deleteFromRealm();
                }
            }
        });
    }

    public void updateReminder(final int id,final Reminder uptRem) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                    Reminder update = realm.where(Reminder.class).equalTo("reminderID", id).findFirst();
                    update.setDate(uptRem.getDate());
                    update.setTime(uptRem.getTime());
                    update.setName(uptRem.getName());
                    update.setType(uptRem.getType());


            }
        });
    }

    // !!!: This is real code for alarm, but it does not work as intended. It works, but it can only set
    //  one alarm. There should be a way to set up multiple alarm, but I was unable to figure it out

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

    private void updateAlarm(long timeInMilli, int x)
    {
        cancelAlarm(x);
        setAlarm(timeInMilli,x);

        Toast.makeText(this, "This Alarm is updated", Toast.LENGTH_SHORT).show();
    }
}

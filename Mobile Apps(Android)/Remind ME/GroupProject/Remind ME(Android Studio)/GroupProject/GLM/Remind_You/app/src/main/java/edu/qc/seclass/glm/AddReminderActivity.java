package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
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

import java.util.ArrayList;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDatePicker, btnTimePicker,btnAdd,btnAddType;
    EditText txtDate, txtTime, myEditText;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public ArrayList<Reminder> allReminders = new ArrayList<>();
    public ArrayList<ReminderList> allLists = new ArrayList<>();
    public ArrayList<ReminderType> typeList = new ArrayList<>();
    Realm mRealm;

    AutoCompleteTextView txtName, txtType;
    static String[] reminderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getDefaultInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            allReminders = (ArrayList)getIntent().getSerializableExtra("listRem"); //Obtaining data
        }

        if (extras != null) {
            allLists = (ArrayList)getIntent().getSerializableExtra("allList"); //Obtaining data
        }

        if (extras != null) {
            typeList = (ArrayList)getIntent().getSerializableExtra("typeList"); //Obtaining data
        }

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAddType = (Button) findViewById(R.id.btn_addType);


        myEditText = (EditText) findViewById(R.id.in_type);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        txtName = (AutoCompleteTextView) findViewById(R.id.in_name);
        txtType = (AutoCompleteTextView) findViewById(R.id.in_type);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnAddType.setOnClickListener(this);

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
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if(year < c.get(Calendar.YEAR) || monthOfYear < c.get(Calendar.MONTH) || dayOfMonth < c.get(Calendar.DAY_OF_MONTH)){
                                Toast.makeText(getApplicationContext(), "Selected date has passed, Please choose a future date", Toast.LENGTH_LONG).show();
                            }
                            else {
                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                btnTimePicker.performClick();
                            }
                        }


                    }, mYear, mMonth, mDay);


            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
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
                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnAdd) {
            final Reminder compareReminder = mRealm.where(Reminder.class).equalTo("name", txtName.getText().toString()).findFirst();
            final ReminderType compareReminderType = mRealm.where(ReminderType.class).equalTo("name", txtType.getText().toString()).findFirst();

            if(txtName.getText().toString().length() == 0){
                Toast.makeText(getApplicationContext(), "Reminder Name cannot be left blank", Toast.LENGTH_SHORT).show();
            }
            else {
                String tName = txtName.getText().toString();
                String tType = txtType.getText().toString();
                String tDate = txtDate.getText().toString();
                String tTime = txtTime.getText().toString();

                Reminder newReminder = new Reminder(tName, tType, tDate, tTime, false);

                if (compareReminder == null && compareReminderType != null) {
                    allReminders.add(newReminder);
                    addReminder(newReminder);
                    Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listRem", allReminders);
                    bundle.putSerializable("allList", allLists);
                    bundle.putSerializable("typeList", typeList);

                    activity2Intent.putExtras(bundle);
                    this.finish();
                    startActivity(activity2Intent);
                } else if (compareReminder != null) {
                    Toast.makeText(getApplicationContext(), "Reminder already exists, Cannot add duplicate", Toast.LENGTH_SHORT).show();
                } else if (compareReminderType == null) {
                    Toast.makeText(getApplicationContext(), "Reminder Type does not exists, Please add Reminder Type", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if(v == btnAddType) {
            final ReminderType compareReminderType = mRealm.where(ReminderType.class).equalTo("name", txtType.getText().toString()).findFirst();
            if(txtType.getText().toString().length() == 0){
                Toast.makeText(getApplicationContext(), "Reminder Type cannot be left blank", Toast.LENGTH_SHORT).show();
            }
            else{
            Realm realm = null;
            if (compareReminderType == null) {
                try {
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {


                            try {
                                Number maxId = realm.where(ReminderType.class).max("reminderTypeID");
                                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                                ReminderType addRemType = new ReminderType(txtType.getText().toString());
                                addRemType.setReminderTypeID(nextId);
                                realm.insertOrUpdate(addRemType);


                            } catch (RealmPrimaryKeyConstraintException e) {
                                Toast.makeText(getApplicationContext(), "Primary Key exists, Cannot added duplicate", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Reminder Type does not exists, Please add Reminder Type", Toast.LENGTH_SHORT).show();
            }
            }
        }
    }

    public void addReminder(final Reminder addRem) {

        Realm realm = null;

            try {
                realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {


                        try {
                            Number maxId = realm.where(Reminder.class).max("reminderID");
                            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                            addRem.setReminderID(nextId);
                            realm.insertOrUpdate(addRem);


                        } catch (RealmPrimaryKeyConstraintException e) {
                            Toast.makeText(getApplicationContext(), "Primary Key exists, Cannot added duplicate", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } finally {
                if (realm != null) {
                    realm.close();
                }
            }

    }
    @Override
    public void onBackPressed() {
        //your method call
        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(activity2Intent);
        super.onBackPressed();
    }
}

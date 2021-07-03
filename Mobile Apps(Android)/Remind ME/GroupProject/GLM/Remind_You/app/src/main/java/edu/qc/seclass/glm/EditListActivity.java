package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditListActivity extends AppCompatActivity implements View.OnClickListener, Serializable, CompoundButton.OnCheckedChangeListener {

    Button btnAdd,btnDel,btnSave,btnEditL,btnSelDel;
    TextView txtName,txtnewName;
    AutoCompleteTextView txtaddRem;
    Intent intent;
    Bundle bundle;
    int pos;
    ListView listView;

    ReminderList rem;
    public ArrayList<ReminderList> allReminderL;
    public ArrayList<Reminder> checkList = new ArrayList<Reminder>();

    ReminderAdapter versionAdapter;
    ArrayAdapter<Reminder> aAdapt;

    Reminder temp;
    Realm mRealm;
    static String[] reminderName;
    static int selDelCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            rem = (ReminderList)getIntent().getSerializableExtra("ReminderList"); //Obtaining data
            pos = (Integer)getIntent().getIntExtra("positionL",-1); //Obtaining data
            allReminderL = (ArrayList)getIntent().getSerializableExtra("allList"); //Obtaining data
        }
        setContentView(R.layout.activity_edit_list);
        btnAdd = (Button) findViewById(R.id.addToList);
        btnDel = (Button) findViewById(R.id.deleteList);
        btnSave = (Button) findViewById(R.id.saveList);
        btnEditL = (Button) findViewById(R.id.editList);
        btnSelDel = (Button)findViewById(R.id.seldelAll);


        listView = (ListView) findViewById(R.id.listView5);
        txtName = (TextView) findViewById(R.id.in_name);
        txtaddRem = (AutoCompleteTextView) findViewById(R.id.addReminder);
        txtnewName = (TextView) findViewById(R.id.newListName);

        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnEditL.setOnClickListener(this);
        btnSelDel.setOnClickListener(this);


        intent = this.getIntent();
        bundle = intent.getExtras();

        txtName.setText(rem.getListName());
        Realm.init(getApplicationContext());



        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getDefaultInstance();

        // getting Reminder Class query
        RealmQuery<ListToReminderRelation> queryRem = mRealm.where(ListToReminderRelation.class);
        queryRem = queryRem.contains("reminderList", txtName.getText().toString());
        final RealmResults<ListToReminderRelation> results1 = queryRem.findAll();
        // int variable to told result1 size
        int sizeQuery = results1.size();

        String reminderName[] = new String [sizeQuery] ;

        for(int i = 0; i < sizeQuery; i++) {
            reminderName[i] = (results1.get(i).getReminder());
            //checkList.add(temp);
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

        //***************************************************************************
        // getting Reminder Class query
        RealmQuery<Reminder> queryRem3 = mRealm.where(Reminder.class);
        final RealmResults<Reminder> results3 = queryRem3.findAll();
        // int variable to told result1 size
        int sizeQuery3 = results3.size();

        reminderName = new String [sizeQuery3] ;

        for(int i = 0; i < sizeQuery3; i++) {
            reminderName[i] = (results3.get(i).getName());
        }

        ArrayAdapter<String> adapterAuto2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, reminderName);
        txtaddRem.setAdapter(adapterAuto2);

        ImageView image_type = (ImageView)findViewById(R.id.image_type);

        image_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                txtaddRem.showDropDown();
            }
        });


        versionAdapter = new ReminderAdapter(EditListActivity.this,R.layout.item_row,checkList);
        listView.setAdapter(versionAdapter);

        listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {

                ListToReminderRelation removeReminder = results1.get(position);
                ListToReminderRelation existingRem = mRealm.where(ListToReminderRelation.class)
                        .equalTo("reminderList", removeReminder.getReminderList()).equalTo("reminder",removeReminder.getReminder()).findFirst();
                 deleteRelationListReminder(existingRem);
                Toast.makeText(getApplicationContext(), "Reminder Removed from list", Toast.LENGTH_LONG).show();
                versionAdapter = new ReminderAdapter(EditListActivity.this,R.layout.item_row,checkList);

                checkList.clear();
                // getting Reminder Class query
                RealmQuery<ListToReminderRelation> queryRem = mRealm.where(ListToReminderRelation.class);
                queryRem = queryRem.contains("reminderList", txtName.getText().toString());
                final RealmResults<ListToReminderRelation> results1 = queryRem.findAll();
                // int variable to told result1 size
                int sizeQuery = results1.size();

                String reminderName[] = new String [sizeQuery] ;

                for(int i = 0; i < sizeQuery; i++) {
                    reminderName[i] = (results1.get(i).getReminder());
                    //checkList.add(temp);
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
                listView.setAdapter(versionAdapter);
                return true;
            }
        });
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int position = listView.getPositionForView(compoundButton);

        if (position != ListView.INVALID_POSITION){
            Reminder reminder = checkList.get(position);
            reminder.setIsSelected(b);
            Calendar d = Calendar.getInstance();

            d.set(Calendar.HOUR_OF_DAY, reminder.getDay());
            d.set(Calendar.MINUTE, reminder.getMinute());
            d.set(Calendar.DATE,reminder.getDay());
            d.set(Calendar.MONTH,reminder.getMonth());
            d.set(Calendar.YEAR,reminder.getYear());
            long timeNow = d.getTimeInMillis();
            if(b == true){
                setAlarm(timeNow, reminder.getReminderID());
            }
            if(b == false) {
                cancelAlarm(reminder.getReminderID());
            }

            int primId = checkList.get(position).getReminderID();
            updateReminder(primId,reminder);


           // Toast.makeText(this, "Selected : "+ b, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        final Reminder addToList = mRealm.where(Reminder.class).equalTo("name",txtaddRem.getText().toString()).findFirst();

        if (v == btnAdd) {
            if(addToList != null){
                ListToReminderRelation existingRem = mRealm.where(ListToReminderRelation.class)
                        .equalTo("reminderList", txtName.getText().toString()).equalTo("reminder",addToList.getName())
                        .findFirst();
                if(existingRem == null) {
                    ListToReminderRelation userInput = new ListToReminderRelation(txtName.getText().toString(), txtaddRem.getText().toString());
                    addLtoRR(userInput);
                    RealmConfiguration config =
                            new RealmConfiguration.Builder()
                                    .deleteRealmIfMigrationNeeded()
                                    .build();
                    Realm.setDefaultConfiguration(config);

                    mRealm = Realm.getDefaultInstance();

                    // getting Reminder Class query
                    RealmQuery<ListToReminderRelation> queryRem = mRealm.where(ListToReminderRelation.class);
                    queryRem = queryRem.contains("reminderList", txtName.getText().toString());
                    RealmResults<ListToReminderRelation> results1 = queryRem.findAll();
                    // int variable to told result1 size
                    int sizeQuery = results1.size();
                    final ArrayAdapter bAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, results1);
                    listView.setAdapter(bAdapt);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Reminder already exist in this list", Toast.LENGTH_LONG).show();
                }

            } else
            {
                Toast.makeText(getApplicationContext(), "Reminder does not exist, Please create reminder", Toast.LENGTH_LONG).show();
            }

        }
        if (v == btnSave) {
            String tName = txtnewName.getText().toString();

            if(tName.length() == 0) {
                Toast.makeText(getApplicationContext(), "List name is empty, Changes cannot be made", Toast.LENGTH_SHORT).show();
            }
            else {
                RealmQuery<ListToReminderRelation> queryRem = mRealm.where(ListToReminderRelation.class);
                queryRem = queryRem.contains("reminderList", txtName.getText().toString());
                final RealmResults<ListToReminderRelation> results1 = queryRem.findAll();

                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        results1.setString("reminderList", txtnewName.getText().toString());
                    }
                });
                //***********************************************************
                ReminderList newReminderList = new ReminderList(tName);
                int id = allReminderL.get(pos).getReminderListID();
                updateReminderList(id, newReminderList);
                allReminderL.set(pos, newReminderList);
                ArrayAdapter aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allReminderL);
                ListActivity.listView.setAdapter(aAdapt);
                ListActivity.allLists = allReminderL;
                this.finish();
            }
        }
        if (v == btnDel) {
            ListToReminderRelation existingRem = mRealm.where(ListToReminderRelation.class)
                    .equalTo("reminderList", txtName.getText().toString()).findFirst();
            while(existingRem != null) {
                deleteRelationListReminder(existingRem);
                existingRem = mRealm.where(ListToReminderRelation.class)
                        .equalTo("reminderList", txtName.getText().toString()).findFirst();
            }


            ReminderList remove = allReminderL.get(pos);
            final ReminderList toRemove = mRealm.where(ReminderList.class).equalTo("reminderListID",remove.getReminderListID()).findFirst();
            deleteReminder(toRemove);
            allReminderL.remove(pos);
            ArrayAdapter aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allReminderL);
            ListActivity.listView.setAdapter(aAdapt);
            ListActivity.allLists = allReminderL;
            this.finish();
        }
        //*****************************************************************
        if(v == btnEditL) {
            // getting Reminder Class query
            RealmQuery<ListToReminderRelation> queryRem = mRealm.where(ListToReminderRelation.class);
            queryRem = queryRem.contains("reminderList", txtName.getText().toString());
            RealmResults<ListToReminderRelation> results1 = queryRem.findAll();
            // int variable to told result1 size
            int sizeQuery = results1.size();
            final ArrayAdapter bAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, results1);
            listView.setAdapter(bAdapt);
        }
        if(v == btnSelDel) {
            selDelCount++;
            // getting Reminder Class query
            RealmQuery<ListToReminderRelation> queryRem = mRealm.where(ListToReminderRelation.class);
            queryRem = queryRem.contains("reminderList", txtName.getText().toString());
            final RealmResults<ListToReminderRelation> results1 = queryRem.findAll();
            // int variable to told result1 size
            int sizeQuery = results1.size();

            String reminderName[] = new String [sizeQuery] ;

            for(int i = 0; i < sizeQuery; i++) {
                reminderName[i] = (results1.get(i).getReminder());
                //checkList.add(temp);
            }



            // getting Reminder Class query
            RealmQuery<Reminder> queryRem2 = mRealm.where(Reminder.class);
            // finding all Reminder Class query and assigning them to result1 list
            queryRem2 = queryRem2.in("name", reminderName, Case.SENSITIVE);
            // int variable to told result1 size

            final RealmResults<Reminder> results2 = queryRem2.findAll();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if(selDelCount%2 == 1) {
                        results2.setBoolean("isSelected", false);
                    }
                    else{
                        results2.setBoolean("isSelected",true);
                    }
                }
            });

            int sizeQuery2 = results2.size();
            checkList.clear();

            for (int i = 0; i < sizeQuery2; i++) {

                temp = new Reminder(results2.get(i));
                checkList.add(temp);
            }


            versionAdapter = new ReminderAdapter(EditListActivity.this,R.layout.item_row,checkList);
            listView.setAdapter(versionAdapter);
        }
    }

    public void updateReminderList(final int id,final ReminderList uptRem) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ReminderList update = realm.where(ReminderList.class).equalTo("reminderListID", id).findFirst();
                update.setListName(uptRem.getListName());
            }
        });
    }

    public void deleteReminder(final ReminderList delRem) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (delRem != null) {
                    delRem.deleteFromRealm();
                }
            }
        });
    }
    public void deleteRelationListReminder(final ListToReminderRelation delRem) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (delRem != null) {
                    delRem.deleteFromRealm();
                }
            }
        });
    }


    public void addLtoRR (final ListToReminderRelation addL2R) {

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {


                    try {
                        Number maxId = realm.where(ListToReminderRelation.class).max("ListToReminderRelationID");
                        int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                        addL2R.setListToReminderRelationID(nextId);
                        realm.insertOrUpdate(addL2R);


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

    public void updateReminder(final int id,final Reminder uptRem) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Reminder update = realm.where(Reminder.class).equalTo("reminderID", id).findFirst();
                update.setIsSelected(uptRem.getIsSelected());
            }
        });
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

package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static edu.qc.seclass.glm.ListActivity.listView;

public class AddReminderTypeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddList;
    EditText txtName;
    public ArrayList<Reminder> allReminders = new ArrayList<>();
    public ArrayList<ReminderList> allLists;
    public ArrayList<ReminderType> typeList = new ArrayList<>();
    Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_type);

        btnAddList = (Button) findViewById(R.id.addList);
        txtName = (EditText) findViewById(R.id.in_name);

        btnAddList.setOnClickListener(this);

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
    }



    @Override
    public void onClick(View v) {

        final ReminderType compareReminderType = mRealm.where(ReminderType.class).equalTo("name", txtName.getText().toString()).findFirst();
        String tName = txtName.getText().toString();
        if(tName.length() == 0) {
            Toast.makeText(getApplicationContext(), "Reminder type name cannot be left blank", Toast.LENGTH_SHORT).show();
        }
        else {
            if (compareReminderType == null) {
                ReminderType rlist = new ReminderType(tName);

                typeList.add(rlist);
                addReminderType(rlist);


                Intent activity2Intent = new Intent(getApplicationContext(), ReminderTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("typeList", typeList);

                activity2Intent.putExtras(bundle);
                ArrayAdapter aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typeList);
                ReminderTypeActivity.listView.setAdapter(aAdapt);
                finish();
                startActivity(activity2Intent);
            } else {
                Toast.makeText(getApplicationContext(), "Reminder Type does exists, Please choose different Reminder Type Name", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void addReminderType(final ReminderType addRem) {

        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {


                    try {
                        Number maxId = realm.where(ReminderType.class).max("reminderTypeID");
                        int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                        addRem.setReminderTypeID(nextId);
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
        Intent activity2Intent = new Intent(getApplicationContext(), ReminderTypeActivity.class);
        startActivity(activity2Intent);
        super.onBackPressed();
    }
}


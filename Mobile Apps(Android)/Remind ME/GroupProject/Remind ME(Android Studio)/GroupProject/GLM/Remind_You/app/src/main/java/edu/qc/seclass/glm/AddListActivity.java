package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddListActivity extends AppCompatActivity implements
        View.OnClickListener {

    Button btnAddList;
    EditText txtName;
    public ArrayList<Reminder> allReminders = new ArrayList<>();
    public ArrayList<ReminderList> allLists;
    public ArrayList<ReminderType> typeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        btnAddList = (Button) findViewById(R.id.addList);
        txtName = (EditText) findViewById(R.id.in_name);

        btnAddList.setOnClickListener(this);

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
        String tName = txtName.getText().toString();
        if(tName.length() == 0) {
            Toast.makeText(getApplicationContext(), "List name cannot be left blank", Toast.LENGTH_SHORT).show();
        }
        else {
            ReminderList rlist = new ReminderList(tName);
            allLists.add(rlist);
            addReminderList(rlist);


            Intent activity2Intent = new Intent(getApplicationContext(), ListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("allList", allLists);
            bundle.putSerializable("listRem", allReminders);
            bundle.putSerializable("typeList", typeList);

            activity2Intent.putExtras(bundle);
            ArrayAdapter aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allLists);
            ListActivity.listView.setAdapter(aAdapt);
            finish();
            startActivity(activity2Intent);
        }

    }

    public void addReminderList(final ReminderList addRem) {

        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {


                    try {
                        Number maxId = realm.where(ReminderList.class).max("reminderListID");
                        int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                        addRem.setReminderListID(nextId);
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
        Intent activity2Intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(activity2Intent);
        super.onBackPressed();
    }
}


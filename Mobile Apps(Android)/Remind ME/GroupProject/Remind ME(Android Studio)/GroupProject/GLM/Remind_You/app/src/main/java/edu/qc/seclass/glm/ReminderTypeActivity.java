package edu.qc.seclass.glm;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReminderTypeActivity extends AppCompatActivity {

    public static ListView listView;
    private Button btnAdd, btnViewList, btnAddType, btnAll;
    public ArrayList<Reminder> allReminders = new ArrayList<>();
    public ArrayList<ReminderList> allLists = new ArrayList<>();
    public static ArrayList<ReminderType> typeList;
    ArrayAdapter aAdapt;
    Realm mRealm;
    ReminderType remType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_type);
        listView = findViewById(R.id.listView4);
        btnAdd = (Button) findViewById(R.id.btnAddReminder);
        btnViewList = (Button) findViewById(R.id.gotolist);
        btnAddType = (Button) findViewById(R.id.gototypes);
        btnAll = (Button) findViewById(R.id.gotoall);
        typeList = new ArrayList<>();

        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getDefaultInstance();

        RealmQuery<ReminderType> queryRem = mRealm.where(ReminderType.class);
        RealmResults<ReminderType> results2 = queryRem.findAll();
        int sizeQuery = results2.size();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            allReminders = (ArrayList) getIntent().getSerializableExtra("listRem"); //Obtaining data
        }

        if (extras != null) {
            allLists = (ArrayList) getIntent().getSerializableExtra("allList"); //Obtaining data
        }

        //if (extras != null) {
          //  typeList = (ArrayList)getIntent().getSerializableExtra("typeList"); //Obtaining data
        //}

       // typeList.clear();
        for(int i = 0; i < sizeQuery;i++) {
            remType = new ReminderType (results2.get(i));
            typeList.add(remType);
        }

        aAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, typeList);
        listView.setAdapter(aAdapt);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), AddReminderTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList",typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);


            }
        });

        btnViewList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), ListActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList",typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);

            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList",typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activity2Intent = new Intent(getApplicationContext(), EditReminderTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ReminderType", typeList.get(position));
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList",typeList);

                bundle.putInt("positionT", position);
                activity2Intent.putExtras(bundle);
                startActivity(activity2Intent);
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
    }

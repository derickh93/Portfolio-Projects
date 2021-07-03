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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public static ListView listView;
    private Button btnAdd, btnViewList, btnAddType, btnAll;
    public static ArrayList<ReminderList> allLists = new ArrayList<>();
    public ArrayList<Reminder> allReminders = new ArrayList<>();
    public ArrayList<ReminderType> typeList = new ArrayList<>();
    Realm mRealm;
    ReminderList remindList;
    ReminderAdapter ReminderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView2);
        btnAdd = (Button) findViewById(R.id.btnAddReminder2);
        btnViewList = (Button) findViewById(R.id.gotolist2);
        btnAddType = (Button) findViewById(R.id.gototypes2);
        btnAll = (Button) findViewById(R.id.gotoall2);

        allLists = new ArrayList<>();

        Realm.init(getApplicationContext());


        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getDefaultInstance();

        RealmQuery<ReminderList> queryRem = mRealm.where(ReminderList.class);
        RealmResults <ReminderList> results2 = queryRem.findAll();
        int sizeQuery = results2.size();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            allReminders = (ArrayList)getIntent().getSerializableExtra("listRem"); //Obtaining data
        }

        //if (extras != null) {
           // allLists = (ArrayList)getIntent().getSerializableExtra("allList"); //Obtaining data
       // }

        if (extras != null) {
            typeList = (ArrayList)getIntent().getSerializableExtra("typeList"); //Obtaining data
        }

        //allLists.clear();
        for(int i = 0; i < sizeQuery;i++) {
            remindList = new ReminderList (results2.get(i));
            allLists.add(remindList);
        }

        final ArrayAdapter bAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allLists);
        listView.setAdapter(bAdapt);

        btnAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("allList",allLists);
                bundle.putSerializable("listRem",allReminders);
                bundle.putSerializable("typeList",typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), AddListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("allList",allLists);
                bundle.putSerializable("listRem",allReminders);
                bundle.putSerializable("typeList",typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);
            }
        });

        btnAddType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), ReminderTypeActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("listRem",allReminders);
                bundle.putSerializable("allList",allLists);
                bundle.putSerializable("typeList",typeList);

                activity2Intent.putExtras(bundle);
                finish();
                startActivity(activity2Intent);

            }
        });

        listView.setAdapter(bAdapt);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activity2Intent = new Intent(getApplicationContext(), EditListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ReminderList",allLists.get(position));
                bundle.putSerializable("listRem", allReminders);
                bundle.putSerializable("allList", allLists);
                bundle.putSerializable("typeList",typeList);

                bundle.putInt("positionL",position);
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

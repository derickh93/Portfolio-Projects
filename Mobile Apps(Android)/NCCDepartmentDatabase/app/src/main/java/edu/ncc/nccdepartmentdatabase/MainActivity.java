package edu.ncc.nccdepartmentdatabase;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;


/**
 * Title: NCCDepartmentDatabase
 * Filename: MainActivity.java
 * Date Written: April 28, 2018
 * Due Date: May 6, 2018
 * Description: Defines a class that will access the NCC departments database. The information will
 * be used to display a listview of departments. There are buttons that will list certain departments
 * as well as an option to search for a specific department.
 *
 * @author Derick Hansraj
 */
public class MainActivity extends ListActivity {

    //instance variables
    private DepartmentInfoSource datasource;
    private ArrayAdapter<DepartmentEntry> adapter;
    private static String ORDER_BY = DepartmentInfoHelper.LOCATION;
    static final int SEARCH_REQUEST = 1;

    /**
     * onCreate method -- This method will define and load the operations performed upon the launch
     * of the application.
     *
     * @param savedInstanceState The instance that is passed.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DepartmentInfoSource(this);
        datasource.open();

        List<DepartmentEntry> values = datasource.getAllDepartments();

        // add departments to the database if it is currently empty
        if (values.isEmpty())
        {
            new ParseURL().execute();
        }
    }

    /**
     * onCreateOptionsMenu method -- This method will define and load the operations that will create
     * the desired menu options.This will inflate the menu as well as add two spinners to the menu.
     *
     * @param menu The menu that is passed.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * onClick method -- Once this method has been assigned to a button the button
     * responds to the onClick events defined. This method keeps track of the current
     * button that has been clicked. Every button will list search the database and return a
     * different list. The search button will allow the user to enter a specific department to
     * search for.
     *
     * @param view the current event that is drawn and being handled.
     */
    public void onClick(View view) {
        DepartmentEntry dept;
        List<DepartmentEntry> values;
        switch (view.getId()) {
            case R.id.show:
                values = datasource.getAllDepartments();
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;
            case R.id.dean_btn:
                values = datasource.findDepartments(DepartmentInfoHelper.NAME + " LIKE ? OR " + DepartmentInfoHelper.NAME +
                        " LIKE ? ",null,"%Dean%");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;
            case R.id.a_d_button:
                values = datasource.findDepartments(DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR "
                        + DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION +
                        " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION +
                        " LIKE ? " , null,"%Cluster A%","%Building A%","%Cluster B%","%Building B%","%Cluster C%","%Building C%","%Cluster D%","%Building D%");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;
            case R.id.tower_btn:
                values = datasource.findDepartments(DepartmentInfoHelper.LOCATION + " LIKE ? OR " + DepartmentInfoHelper.LOCATION +
                        " LIKE ? ", ORDER_BY +" ASC","%Tower%");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;
            case R.id.center_btn:
                values = datasource.findDepartments(DepartmentInfoHelper.NAME + " LIKE ? OR " + DepartmentInfoHelper.NAME +
                        " LIKE ? ", null,"%Center%");
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;
            case R.id.search_btn:
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                startActivityForResult(i,SEARCH_REQUEST);
                break;
        }
        if(view.getId() != R.id.search_btn)
         adapter.notifyDataSetChanged();
    }

    /**
     * onActivityResult method -- This method will receive input from Main2Activity and return it
     * as a string search option for the departments in the database.
     *
     * @param requestCode - the int representing the request code returned.
     * @param  resultCode - the int representing teh result code returned.
     * @param data - the data being returned from the intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        List<DepartmentEntry> values;
        if(requestCode == SEARCH_REQUEST) {
            if(resultCode == RESULT_OK) {
                if(data != null) {
                    String result = data.getStringExtra("KEY");
                    values = datasource.findDepartments(DepartmentInfoHelper.NAME + " LIKE ? OR " + DepartmentInfoHelper.NAME +
                            " LIKE ? ", null,"%"+result+"%");
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                    setListAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * onDestroy method -- Perform any final cleanup before the activity is destroyed
     *
     */
    public void onDestroy()
    {
        datasource.close();
        super.onDestroy();
    }


    private class ParseURL extends AsyncTask<Void, Void, String> {

        /**
         * doInBackground method -- Connects to the NCC directory database and reads data in while
         * adding to the data source.
         * @param params - The parameters passed
         */
        protected String doInBackground(Void... params) {
            String str;
            String deptName;
            String deptPhone;
            String deptLocation;
            String deptEmail;
            Document doc;
            int count = 0;

            try {
                // connect to the webpage
                doc = Jsoup.connect("http://www.ncc.edu/contactus/deptdirectory.shtml").get();

                // find the body of the webpage
                Elements tableEntries = doc.select("tbody");
                for (Element e : tableEntries)
                {
                    // look for a row in the table
                    Elements trs = e.getElementsByTag("tr");

                    // for each element in the row (there are 5)
                    for (Element e2 : trs)
                    {
                        // get the table descriptor
                        Elements tds = e2.getElementsByTag("td");

                        // ignore the first row
                        if (count > 0) {
                            // get the department name and remove the formatting tags
                            if(tds.get(0).text().length() > 1)
                                deptName = tds.get(0).text();
                            else
                                deptName = "Name Unknown";

                            // get the department phone number
                            if(tds.get(1).text().length() > 1)
                                deptPhone = tds.get(1).text();
                            else
                                deptPhone = "Phone Unknown";

                            //get the department email address
                            if(tds.get(3).text().length() > 1)
                                deptEmail = tds.get(3).text();
                            else
                                deptEmail = "Email Unknown";

                            // get the department location
                            if(tds.get(4).text().length() > 1)
                                deptLocation = tds.get(4).text();
                            else
                                deptLocation = "Location Unknown";

                            datasource.addDept(deptName, deptLocation, deptPhone, deptEmail);
                        }
                        count++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        /**
         * onPostExecute method -- Runs on the UI thread after doInBackground(Params...). The
         * specified result is the value returned by doInBackground(Params...).
         *
         */
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            Log.d("PARSING", "async task has completed");
        }
    }
}
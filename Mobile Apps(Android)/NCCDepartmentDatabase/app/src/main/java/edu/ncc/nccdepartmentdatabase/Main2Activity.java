package edu.ncc.nccdepartmentdatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Title: NCCDepartmentDatabase
 * Filename: Main2Activity.java
 * Date Written: April 28, 2018
 * Due Date: May 6, 2018
 * Description: Defines a class that will get input from the user and return it using onActivityResult
 * options.
 *
 * @author Derick Hansraj
 */
public class Main2Activity extends AppCompatActivity {
    EditText edtName;
    Button btnSearch;

    /**
     * onCreate method -- This method will define and load the operations performed upon the launch
     * of the application.
     *
     * @param savedInstanceState The instance that is passed.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edtName = (EditText) findViewById(R.id.searchText);
        btnSearch = (Button) findViewById(R.id.searchBtn);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtName.getText().toString();

                Intent BackIntent = new Intent();
                BackIntent.putExtra("KEY",input);
                setResult(RESULT_OK,BackIntent);
                finish();
            }
        });
    }
}

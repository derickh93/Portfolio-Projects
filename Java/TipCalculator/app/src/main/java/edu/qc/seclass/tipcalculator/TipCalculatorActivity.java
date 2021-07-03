
/**
 * Title: TipCalculator
 * Filename: TipCalculatorActivity.java
 * Date Written: October 3, 2019
 * Due Date: October 5, 2019
 * Description: Application that will calculate the tip and total per person based on the bill
 * amount and the number of people.
 * @author Derick Hansraj
 **/
package edu.qc.seclass.tipcalculator;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipCalculatorActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user touches the button */
    public void computeBtn(View view)
    {
        //gets the ids for the required edit text elements
        EditText amount = findViewById(R.id.checkAmountValue) ;
        EditText party = findViewById(R.id.partySizeValue) ;

        EditText fifteenTip = findViewById(R.id.fifteenPercentTipValue) ;
        EditText fifteenTotal = findViewById(R.id.fifteenPercentTotalValue) ;
        EditText twentyTip = findViewById(R.id.twentyPercentTipValue) ;
        EditText twentyTotal = findViewById(R.id.twentyPercentTotalValue) ;
        EditText twentyFiveTip = findViewById(R.id.twentyfivePercentTipValue) ;
        EditText twentyFiveTotal = findViewById(R.id.twentyfivePercentTotalValue) ;


        //if the amount or party size is empty a toast is displayed
        if(amount.getText().toString().length() == 0 || party.getText().toString().length() == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Empty or incorrect value(s)!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            amount.setText("");
            party.setText("");
            fifteenTip.setText("");
            twentyFiveTip.setText("");
            twentyTip.setText("");
            twentyTotal.setText("");
            twentyFiveTotal.setText("");
            fifteenTotal.setText("");
        }
        //if the amount or party is not empty we parse the values and store them in a string
        else {
            int amountInt = Integer.parseInt(amount.getText().toString());
            int partyInt = Integer.parseInt(party.getText().toString());

            //if the amount or party is a negative number a toast is displayed
            if (partyInt <= 0 || amountInt <= 0) {
                Context context = getApplicationContext();
                CharSequence text = "Empty or incorrect value(s)!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                amount.setText("");
                party.setText("");
                fifteenTip.setText("");
                twentyFiveTip.setText("");
                twentyTip.setText("");
                twentyTotal.setText("");
                twentyFiveTotal.setText("");
                fifteenTotal.setText("");
            } else {
                //if the party and amount is not empty or a negative number the required values
                //are calculated and displayed in the correct edit text elements.

                int fifteenTipInt = (int) Math.round((amountInt / partyInt) * .15);
                int twentyTipInt = (int) Math.round((amountInt / partyInt) * .20);
                int twentyFiveTipInt = (int) Math.round((amountInt / partyInt) * .25);

                int fifteenTotalInt = ((amountInt / partyInt) + fifteenTipInt);
                int twentyTotalInt = ((amountInt / partyInt) + twentyTipInt);
                int twentyFiveTotalInt = ((amountInt / partyInt) + twentyFiveTipInt);

                fifteenTip.setText("$" + fifteenTipInt);
                twentyTip.setText("$" + twentyTipInt);
                twentyFiveTip.setText("$" + twentyFiveTipInt);

                fifteenTotal.setText("$" + fifteenTotalInt);
                twentyTotal.setText("$" + twentyTotalInt);
                twentyFiveTotal.setText("$" + twentyFiveTotalInt);
            }
        }


    }
}
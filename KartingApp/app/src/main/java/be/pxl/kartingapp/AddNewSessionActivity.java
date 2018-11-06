package be.pxl.kartingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.pxl.kartingapp.data.KartingDbHelper;

public class AddNewSessionActivity extends AppCompatActivity {

    private EditText et_numberOfLaps;
    private LinearLayout layout;
    private EditText tv_chosenDate;
    private Button b_addSession;

    private Calendar calendar;
    private int year, month, day;

    private KartingDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewsession);
        ArrayList<String> arguments = getIntent().getExtras().getStringArrayList("args");

        final int circuitId = Integer.parseInt(arguments.get(0)) ;
        final String trackLayout = arguments.get(1);

        et_numberOfLaps = (EditText) findViewById(R.id.et_number_of_laps);
        layout = (LinearLayout) findViewById(R.id.layout_lap_times);
        tv_chosenDate = (EditText) findViewById(R.id.tv_chosen_date);
        b_addSession = (Button) findViewById(R.id.b_add_session);

        b_addSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> lapTimes = new ArrayList<>();
                boolean allLaptimesAreCorrect = true;
                for( int i = 0; i < layout.getChildCount(); i++ ){
                    if( layout.getChildAt(i) instanceof EditText && layout.getChildAt(i).getId() != R.id.tv_chosen_date ){
                        String laptime = ((EditText) layout.getChildAt(i)).getText().toString();
                        if(isCorrectLaptime(laptime)){
                            lapTimes.add(laptime);
                        }else{
                            allLaptimesAreCorrect = false;
                        }

                    }
                }

                if(allLaptimesAreCorrect){
                    addSessionWithLaptimes(trackLayout, tv_chosenDate.getText().toString(), circuitId, lapTimes);
                    finish();
                }

            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        et_numberOfLaps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                   //todo input check for numbers
                    int numberOflines = Integer.parseInt(charSequence.toString());
                    addLines(numberOflines);
                } else if(layout.getChildCount() != 0) {
                    layout.removeAllViews();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private boolean isCorrectLaptime(String laptime) {
        if(laptime.matches("([0-9][0-9]):([0-9][0-9]):([0-9][0-9])")){
            return true;
        }
        CharSequence text = "Please enter laptime as hh:mm:ss";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return false;
    }

    private void addLines(int numberOfLines){
        layout.removeAllViews();

        for (int i = 1; i <= numberOfLines; i++){
            EditText et = new EditText(this);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            et.setId(i);
            et.setLayoutParams(p);
            et.setHint("Enter lap time");
            layout.addView(et);
        }


    }

    private void showDate(int year, int month, int day) {
        tv_chosenDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void addSessionWithLaptimes(String layout, String date, int circuitId, List<String>lapTimes){
       long testEnterLapTimesID = 0;
        dbHelper = new KartingDbHelper(this);
        long createdSessionId = dbHelper.insertSession(layout, date, circuitId);

        for (int i = 0; i < lapTimes.size(); i++){
            dbHelper.insertLap(lapTimes.get(i), createdSessionId);
            System.out.println(testEnterLapTimesID);
        }

        System.out.println(dbHelper.getAllLaptimesBySessionId(3));


    }


}

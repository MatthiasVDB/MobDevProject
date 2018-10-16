package be.pxl.kartingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class AddNewSessionActivity extends AppCompatActivity {

    private EditText et_numberOfLaps;
    private LinearLayout layout;
    private EditText tv_chosenDate;

    private Calendar calendar;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewsession);

        et_numberOfLaps = (EditText) findViewById(R.id.et_number_of_laps);
        layout = (LinearLayout) findViewById(R.id.layout_lap_times);
        tv_chosenDate = (EditText) findViewById(R.id.tv_chosen_date);

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
                   //todo generate edittexts
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
}

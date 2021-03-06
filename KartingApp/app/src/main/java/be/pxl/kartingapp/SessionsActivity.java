package be.pxl.kartingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.SessionCursors;

public class SessionsActivity extends FragmentActivity {

    private TextView tvCircuitName;
    private long circuitId = -1;
    private SessionListAdapter adapterFull;
    private SessionListAdapter adapterShort;
    private int allSessionsFull;
    private int allSessionsShort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        tvCircuitName = findViewById(R.id.tv_circuit);

        Button bCreateNewSession;
        Button bShowLineChart;
        RecyclerView sessionFullRecyclerView;
        RecyclerView sessionShortRecyclerView;
        Intent intent = getIntent();
        List<String> circuit = intent.getStringArrayListExtra("circuit");

        KartingDbHelper dbHelper = new KartingDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CircuitCursors circuitCursors = new CircuitCursors(db);
        SessionCursors sessionCursors = new SessionCursors(db);

        String circuitName = circuit.get(1);
        tvCircuitName.setText(circuitName);
        circuitId = circuitCursors.getCircuitIdByName(circuitName);

        Cursor sessionsFull = sessionCursors.getAllSessionsFullByCircuitId(circuitId);
        Cursor sessionsShort = sessionCursors.getAllSessionsShortByCircuitId(circuitId);

        allSessionsFull = sessionsFull.getCount();
        allSessionsShort = sessionsShort.getCount();

        sessionFullRecyclerView = findViewById(R.id.rv_sessionsFull);
        sessionFullRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterFull = new SessionListAdapter(this, sessionsFull, (SessionListFragment) getFragmentManager().findFragmentById(R.id.sessions));
        sessionFullRecyclerView.setAdapter(adapterFull);

        sessionShortRecyclerView = findViewById(R.id.rv_sessionsShort);
        sessionShortRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterShort = new SessionListAdapter(this, sessionsShort, (SessionListFragment) getFragmentManager().findFragmentById(R.id.sessions));
        sessionShortRecyclerView.setAdapter(adapterShort);

        bCreateNewSession = (Button) findViewById(R.id.b_create_new_session);
        bCreateNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfRadioButtonIsChecked((RadioGroup) findViewById(R.id.trackLayout))) {
                    Intent intent = new Intent(SessionsActivity.this, AddNewSessionActivity.class);
                    //bundle passes circuitId and trackLayout to next activity
                    Bundle bundle = bundleArguments();
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        });

        bShowLineChart = (Button) findViewById(R.id.b_show_line_chart);
        bShowLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfRadioButtonIsChecked((RadioGroup) findViewById(R.id.trackLayout))) {
                    if(getCheckedTrackLayout().equals("Full") && allSessionsFull < 2){
                        ShowEmptySessionsToast();
                    } else if(getCheckedTrackLayout().equals("Short") && allSessionsShort < 2){
                        ShowEmptySessionsToast();
                    }else {
                        Intent intent = new Intent(SessionsActivity.this, DrawLineChartActivity.class);
                        Bundle bundle = bundleArguments();
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean checkIfRadioButtonIsChecked(RadioGroup group) {
        if (circuitId == -1) {
            CharSequence text = "Please select a circuit first.";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            return false;
        } else if (group.getCheckedRadioButtonId() == -1) {
            CharSequence text = "Please select a track layout first.";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private String getCheckedTrackLayout() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.trackLayout);
        String radioValue = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        return radioValue.split(" ")[0];
    }

    private void ShowEmptySessionsToast(){
        CharSequence text = "There aren't enough sessions";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private Bundle bundleArguments() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("" + circuitId);
        arguments.add(getCheckedTrackLayout());
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("args", arguments);
        return bundle;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        KartingDbHelper dbHelper = new KartingDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SessionCursors sessionCursors = new SessionCursors(db);

        Cursor shortCircuit = sessionCursors.getAllSessionsShortByCircuitId(circuitId);
        Cursor fullCircuit = sessionCursors.getAllSessionsFullByCircuitId(circuitId);

        if(adapterFull != null && adapterShort != null){
            adapterShort.swapCursor(shortCircuit);
            adapterFull.swapCursor(fullCircuit);

            allSessionsShort =shortCircuit.getCount();
            allSessionsFull = fullCircuit.getCount();
        }
    }

}

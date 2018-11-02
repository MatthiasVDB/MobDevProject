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
import android.widget.EditText;

import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.SessionCursors;
import be.pxl.kartingapp.models.Session;

public class SessionsActivity extends FragmentActivity {

    private Button bCreateNewSession;
    private Button bShowLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        //TODO Finish SessionActivity

        bCreateNewSession = (Button) findViewById(R.id.b_create_new_session);
        bCreateNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SessionsActivity.this, AddNewSessionActivity.class);
                startActivity(intent);
            }
        });

        bShowLineChart = (Button) findViewById(R.id.b_show_line_chart);
        bShowLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SessionsActivity.this, DrawLineChartActivity.class);
                startActivity(intent);
            }
        });

        SessionListAdapter adapter;
        RecyclerView sessionRecyclerView;

        Intent intent = getIntent();
        List<String> circuit = intent.getStringArrayListExtra("circuit");

        KartingDbHelper dbHelper = new KartingDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CircuitCursors circuitCursors = new CircuitCursors(db);
        SessionCursors sessionCursors = new SessionCursors(db);

        long circuitId = circuitCursors.getCircuitIdByName(circuit.get(1));

        Cursor sessions = sessionCursors.getAllSessionsByCircuitId(circuitId);

        sessionRecyclerView = findViewById(R.id.rv_sessions);
        sessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SessionListAdapter(this, sessions, (SessionListFragment) getFragmentManager().findFragmentById(R.id.sessions));
        sessionRecyclerView.setAdapter(adapter);

        //((EditText) findViewById(R.id.editTextItem)).setText(item);
    }
}

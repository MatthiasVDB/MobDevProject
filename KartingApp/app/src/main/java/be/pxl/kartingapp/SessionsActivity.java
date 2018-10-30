package be.pxl.kartingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.SessionCursors;
import be.pxl.kartingapp.models.Session;

public class SessionsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        //TODO Finish SessionActivity

        SessionListAdapter adapter;
        RecyclerView sessionRecyclerView;

        Intent intent = getIntent();
        List<String> circuit = intent.getStringArrayListExtra("circuit");

        KartingDbHelper dbHelper = new KartingDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CircuitCursors circuitCursors = new CircuitCursors(db);
        SessionCursors sessionCursors = new SessionCursors(db);

        long circuitId = circuitCursors.getCircuitIdByName(circuit.get(1));

        Cursor sessions = sessionCursors.getAllSessionDatesByCircuitId(circuitId);

        sessionRecyclerView = findViewById(R.id.rv_sessions);
        sessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SessionListAdapter(this, sessions, (SessionListFragment) getFragmentManager().findFragmentById(R.id.sessions));
        sessionRecyclerView.setAdapter(adapter);

        //((EditText) findViewById(R.id.editTextItem)).setText(item);
    }
}

package be.pxl.kartingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.models.Session;

public class SessionsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO complete oncreate to show list of sessions

        setContentView(R.layout.activity_sessions);

        Intent intent = getIntent();
        List<String> circuit = intent.getStringArrayListExtra("circuit");

        KartingDbHelper dbHelper = new KartingDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CircuitCursors circuitCursors = new CircuitCursors(db);

        long circuitId = circuitCursors.getCircuitIdByName(circuit.get(1));

        List<Session> sessions = dbHelper.getAllCircuitSessionsByCircuitId(circuitId);

        //((EditText) findViewById(R.id.editTextItem)).setText(item);
    }
}

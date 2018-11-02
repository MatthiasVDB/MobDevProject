package be.pxl.kartingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.LapCursors;

public class LapsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laps);

        LapListAdapter adapter;
        RecyclerView lapRecyclerView;

        Intent intent = getIntent();
        long sessionId = intent.getLongExtra("session", -1);

        KartingDbHelper dbHelper = new KartingDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        LapCursors lapCursors = new LapCursors(db);

        Cursor laps = lapCursors.getAllLapsBySessionId(sessionId);

        lapRecyclerView = findViewById(R.id.rv_laps);
        lapRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LapListAdapter(this, laps);
        lapRecyclerView.setAdapter(adapter);
    }
}

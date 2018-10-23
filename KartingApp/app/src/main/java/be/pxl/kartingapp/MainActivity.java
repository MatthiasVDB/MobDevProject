package be.pxl.kartingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.sql.Date;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;

public class MainActivity extends AppCompatActivity {

    private Button bCreateNewSession;
    private Button bShowLineChart;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RecyclerView kartingRecyclerView;
        CircuitListAdapter adapter;

        KartingDbHelper dbHelper = new KartingDbHelper(this);
        db = dbHelper.getWritableDatabase();

        CircuitCursors circuitCursors = new CircuitCursors(db);

        bCreateNewSession = (Button) findViewById(R.id.b_create_new_session);
        bCreateNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewSessionActivity.class);
                startActivity(intent);
            }
        });

        bShowLineChart = (Button) findViewById(R.id.b_show_line_chart);
        bShowLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DrawLineChartActivity.class);
                startActivity(intent);
            }
        });

        kartingRecyclerView = this.findViewById(R.id.rv_circuits);
        kartingRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Uncomment when you want to add dummy data
        //dbHelper.insertCircuit("dummyKarting", "dummyAddress");
        //dbHelper.insertSession("FULL", new Date(0), 1);
        //dbHelper.insertLap("0:43.123", 1);

        adapter = new CircuitListAdapter(this, circuitCursors.getAllCircuits());
        kartingRecyclerView.setAdapter(adapter);

        //api call
        new GetAllKartingsTask().execute();


    }



}

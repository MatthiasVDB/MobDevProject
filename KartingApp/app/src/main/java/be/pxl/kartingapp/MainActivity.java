package be.pxl.kartingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Date;

import be.pxl.kartingapp.data.KartingContract;
import be.pxl.kartingapp.data.KartingDbHelper;

public class MainActivity extends AppCompatActivity {

    private Button bCreateNewSession;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bCreateNewSession = (Button) findViewById(R.id.b_create_new_session);
        bCreateNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewSessionActivity.class);
                startActivity(intent);
            }
        });

        KartingDbHelper dbHelper = new KartingDbHelper(this);

        db = dbHelper.getWritableDatabase();
        //Uncomment when you want to add dummy data
        //System.out.println(dbHelper.insertCircuit("dummyKarting", "dummyAddress"));
        //System.out.println(dbHelper.insertSession("FULL", new Date(0), 1));
        //System.out.println(dbHelper.insertLap("0:43.123", 1));
    }

    private Cursor getAllCircuits() {
        return db.query(
                KartingContract.CircuitEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

}

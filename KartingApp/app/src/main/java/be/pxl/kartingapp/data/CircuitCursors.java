package be.pxl.kartingapp.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CircuitCursors {
    private SQLiteDatabase db;

    public CircuitCursors(SQLiteDatabase db) {
        this.db = db;
    }

    public Cursor getAllCircuits() {
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

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

    public long getCircuitIdByName(String name){
        long circuitId = -1;
        String Query = "Select * from circuit where name = \"" + name +"\"";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    circuitId = cursor.getLong(cursor.getColumnIndex("_id"));
                    cursor.moveToNext();
                }
            }
        }

        cursor.close();
        return circuitId;
    }
}

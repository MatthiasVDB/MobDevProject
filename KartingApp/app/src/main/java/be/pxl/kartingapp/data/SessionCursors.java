package be.pxl.kartingapp.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SessionCursors {
    private SQLiteDatabase db;

    public SessionCursors(SQLiteDatabase db) {
        this.db = db;
    }

    public Cursor getAllSessionsFullByCircuitId(long circuitId) {

        String query = "SELECT * FROM " + KartingContract.SessionEntry.TABLE_NAME + " WHERE " +
                KartingContract.SessionEntry.TRACK_LAYOUT +  " = \"Full\" " + " AND " +
                KartingContract.SessionEntry.CIRCUIT_ID + " = " + circuitId;

        return db.rawQuery(query, null);
    }

    public Cursor getAllSessionsShortByCircuitId(long circuitId) {

        String query = "SELECT * FROM " + KartingContract.SessionEntry.TABLE_NAME + " WHERE " +
                KartingContract.SessionEntry.TRACK_LAYOUT +  " = \"Short\" " + " AND " +
                KartingContract.SessionEntry.CIRCUIT_ID + " = " + circuitId;

        return db.rawQuery(query, null);
    }
}

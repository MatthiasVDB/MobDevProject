package be.pxl.kartingapp.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SessionCursors {
    private SQLiteDatabase db;

    public SessionCursors(SQLiteDatabase db) {
        this.db = db;
    }

    public Cursor getAllSessionDatesByCircuitId(long circuitId) {

        String query = "SELECT " + KartingContract.SessionEntry.SESSION_DATE + " FROM " + KartingContract.SessionEntry.TABLE_NAME + " WHERE " +
                KartingContract.SessionEntry.CIRCUIT_ID + " = " + circuitId;

        return db.rawQuery(query, null);
    }
}

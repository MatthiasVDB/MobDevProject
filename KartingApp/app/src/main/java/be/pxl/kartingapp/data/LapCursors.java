package be.pxl.kartingapp.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LapCursors {
    private SQLiteDatabase db;

    public LapCursors(SQLiteDatabase db) {
        this.db = db;
    }

    public Cursor getAllLapsBySessionId(long sessionId) {

        String query = "SELECT * FROM " + KartingContract.LapEntry.TABLE_NAME + " WHERE " +
                KartingContract.LapEntry.SESSION_ID + " = " + sessionId;

        return db.rawQuery(query, null);
    }
}

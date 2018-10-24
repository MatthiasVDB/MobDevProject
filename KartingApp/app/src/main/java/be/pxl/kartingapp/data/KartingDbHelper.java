package be.pxl.kartingapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class KartingDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "karting.db";
    private static final int DATABASE_VERSION = 1;

    public KartingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CIRCUIT_TABLE = "CREATE TABLE " + KartingContract.CircuitEntry.TABLE_NAME + " (" +
                KartingContract.CircuitEntry._ID + " INTEGER PRiMARY KEY AUTOINCREMENT, " +
                KartingContract.CircuitEntry.NAME + " TEXT NOT NULL, " +
                KartingContract.CircuitEntry.ADDRESS + " TEXT NOT NULL); ";
        final String SQL_CREATE_SESSION_TABLE = "CREATE TABLE " + KartingContract.SessionEntry.TABLE_NAME + " (" +
                KartingContract.SessionEntry._ID + " INTEGER PRiMARY KEY AUTOINCREMENT, " +
                KartingContract.SessionEntry.TRACK_LAYOUT + " TEXT NOT NULL, " +
                KartingContract.SessionEntry.SESSION_DATE + " DATE NOT NULL, " +
                KartingContract.SessionEntry.CIRCUIT_ID + " INTEGER NOT NULL, FOREIGN KEY (" + KartingContract.SessionEntry.CIRCUIT_ID + ") REFERENCES " + KartingContract.CircuitEntry.TABLE_NAME + "(_ID)); ";
        final String SQL_CREATE_LAP_TABLE = "CREATE TABLE " + KartingContract.LapEntry.TABLE_NAME + " (" +
                KartingContract.LapEntry._ID + " INTEGER PRiMARY KEY AUTOINCREMENT, " +
                KartingContract.LapEntry.LAP_TIME + " TEXT NOT NULL, " +
                KartingContract.LapEntry.SESSION_ID + " INTEGER NOT NULL, FOREIGN KEY (" + KartingContract.LapEntry.SESSION_ID + ") REFERENCES " + KartingContract.SessionEntry.TABLE_NAME + "(_ID)); ";

        db.execSQL(SQL_CREATE_CIRCUIT_TABLE);
        db.execSQL(SQL_CREATE_SESSION_TABLE);
        db.execSQL(SQL_CREATE_LAP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + KartingContract.CircuitEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + KartingContract.SessionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + KartingContract.LapEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insertCircuit(String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (checkIsDataAlreadyInDBorNot(KartingContract.CircuitEntry.TABLE_NAME, KartingContract.CircuitEntry.NAME, name)) {
            return 0;
        }
        values.put(KartingContract.CircuitEntry.NAME, name);
        values.put(KartingContract.CircuitEntry.ADDRESS, address);
        return db.insert(KartingContract.CircuitEntry.TABLE_NAME, null, values);
    }

    public long insertSession(String layout, Date date, int circuitId) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(date);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KartingContract.SessionEntry.TRACK_LAYOUT, layout);
        values.put(KartingContract.SessionEntry.SESSION_DATE, formattedDate);
        values.put(KartingContract.SessionEntry.CIRCUIT_ID, circuitId);
        return db.insert(KartingContract.SessionEntry.TABLE_NAME, null, values);
    }

    public long insertLap(String time, long sessionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KartingContract.LapEntry.LAP_TIME, time);
        values.put(KartingContract.LapEntry.SESSION_ID, sessionId);
        return db.insert(KartingContract.LapEntry.TABLE_NAME, null, values);
    }

    public boolean checkIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue) {
        String Query = "Select * from " + TableName + " where " + dbfield + " = \"" + fieldValue + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}

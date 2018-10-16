package be.pxl.kartingapp.data;

import android.provider.BaseColumns;

public final class KartingContract {

    private KartingContract() {}

    public static class CircuitEntry implements BaseColumns {
        public static final String TABLE_NAME = "circuit";
        public static final String NAME = "name";
        public static final String ADDRESS = "address";
    }

    public static class SessionEntry implements BaseColumns {
        public static final String TABLE_NAME = "circuitSession";
        public static final String SESSION_DATE = "sessionDate";
        public static final String TRACK_LAYOUT = "trackLayout";
        public static final String CIRCUIT_ID = "circuit_id";
    }

    public static class LapEntry implements BaseColumns {
        public static final String TABLE_NAME = "lap";
        public static final String LAP_TIME = "lapTime";
        public static final String SESSION_ID = "session_id";
    }
}

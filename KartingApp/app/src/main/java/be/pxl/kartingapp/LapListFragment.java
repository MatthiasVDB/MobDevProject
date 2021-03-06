package be.pxl.kartingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.LapCursors;

public class LapListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_laps_fragment, container, false);

        LapListAdapter adapter;
        RecyclerView lapRecyclerView;
        Long sessionId;
        Bundle bundle = getArguments();

        if(bundle != null){
            sessionId = getArguments().getLong("session");

            KartingDbHelper dbHelper = new KartingDbHelper(getActivity().getBaseContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            LapCursors lapCursors = new LapCursors(db);

            Cursor laps = lapCursors.getAllLapsBySessionId(sessionId);

            lapRecyclerView = view.findViewById(R.id.rv_laps);
            lapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

            adapter = new LapListAdapter(getActivity().getBaseContext(), laps);
            lapRecyclerView.setAdapter(adapter);
        }
        return view;
    }
}

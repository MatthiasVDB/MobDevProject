package be.pxl.kartingapp;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.SessionCursors;

public class SessionListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sessions_fragment, container, false);

        //TODO Complete SessionListFragment

        SessionListAdapter adapter;
        RecyclerView sessionRecyclerView;

        Bundle bundle = getArguments();

        List<String> circuit;

        if(bundle != null){
            circuit = getArguments().getStringArrayList("circuit");

            KartingDbHelper dbHelper = new KartingDbHelper(getActivity().getBaseContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            CircuitCursors circuitCursors = new CircuitCursors(db);
            SessionCursors sessionCursors = new SessionCursors(db);

            long circuitId = circuitCursors.getCircuitIdByName(circuit.get(1));

            Cursor sessions = sessionCursors.getAllSessionDatesByCircuitId(circuitId);

            sessionRecyclerView = getView().findViewById(R.id.rv_sessions);
            sessionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

            adapter = new SessionListAdapter(getActivity().getBaseContext(), sessions, (SessionListFragment) getFragmentManager().findFragmentById(R.id.sessions));
            sessionRecyclerView.setAdapter(adapter);
        }
        return view;
    }
}

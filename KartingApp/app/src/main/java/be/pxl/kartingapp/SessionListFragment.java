package be.pxl.kartingapp;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.SessionCursors;

public class SessionListFragment extends Fragment {

    private Button bCreateNewSession;
    private Button bShowLineChart;
    private RadioGroup rgTrackLayout;
    long circuitId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sessions_fragment, container, false);

        //TODO Pass Track Layout with both button onClick calls (also in SessionsActivity)

        rgTrackLayout = (RadioGroup) view.findViewById(R.id.trackLayout);

        bCreateNewSession = (Button) view.findViewById(R.id.b_create_new_session);
        bCreateNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfRadioButtonIsChecked(rgTrackLayout)) {
                    Intent intent = new Intent(getActivity().getBaseContext(), AddNewSessionActivity.class);
                    startActivity(intent);
                }
            }
        });

        bShowLineChart = (Button) view.findViewById(R.id.b_show_line_chart);
        bShowLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfRadioButtonIsChecked(rgTrackLayout)) {
                    Intent intent = new Intent(getActivity().getBaseContext(), DrawLineChartActivity.class);
                    startActivity(intent);
                }
            }
        });

        SessionListAdapter adapterFull;
        SessionListAdapter adapterShort;
        RecyclerView sessionFullRecyclerView;
        RecyclerView sessionShortRecyclerView;

        Bundle bundle = getArguments();

        List<String> circuit;

        if(bundle != null){
            circuit = getArguments().getStringArrayList("circuit");

            KartingDbHelper dbHelper = new KartingDbHelper(getActivity().getBaseContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            CircuitCursors circuitCursors = new CircuitCursors(db);
            SessionCursors sessionCursors = new SessionCursors(db);

            circuitId = circuitCursors.getCircuitIdByName(circuit.get(1));

            Cursor sessionsFull = sessionCursors.getAllSessionsFullByCircuitId(circuitId);
            Cursor sessionsShort = sessionCursors.getAllSessionsShortByCircuitId(circuitId);

            sessionFullRecyclerView = view.findViewById(R.id.rv_sessionsFull);
            sessionFullRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

            adapterFull = new SessionListAdapter(getActivity().getBaseContext(), sessionsFull, (SessionListFragment) getFragmentManager().findFragmentById(R.id.sessions));
            sessionFullRecyclerView.setAdapter(adapterFull);

            sessionShortRecyclerView = view.findViewById(R.id.rv_sessionsShort);
            sessionShortRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

            adapterShort = new SessionListAdapter(getActivity().getBaseContext(), sessionsShort, (SessionListFragment) getFragmentManager().findFragmentById(R.id.sessions));
            sessionShortRecyclerView.setAdapter(adapterShort);
        }
        return view;
    }

    private boolean checkIfRadioButtonIsChecked(RadioGroup group) {
        if (circuitId == -1) {
            CharSequence text = "Please select a circuit first.";
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            return false;
        } else if (group.getCheckedRadioButtonId() == -1) {
            CharSequence text = "Please select a track layout first.";
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}

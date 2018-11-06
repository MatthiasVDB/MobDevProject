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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;
import be.pxl.kartingapp.data.SessionCursors;

public class SessionListFragment extends Fragment {

    private RadioGroup rgTrackLayout;
    private long circuitId = -1;
    private SessionListAdapter adapterFull;
    private SessionListAdapter adapterShort;
    private int allSessionsFull;
    private int allSessionsShort;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sessions_fragment, container, false);

        Button bCreateNewSession;
        Button bShowLineChart;
        RecyclerView sessionFullRecyclerView;
        RecyclerView sessionShortRecyclerView;
        List<String> circuit;
        Bundle bundle = getArguments();

        rgTrackLayout = (RadioGroup) view.findViewById(R.id.trackLayout);

        bCreateNewSession = (Button) view.findViewById(R.id.b_create_new_session);
        bCreateNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfRadioButtonIsChecked(rgTrackLayout)) {
                    Intent intent = new Intent(getActivity().getBaseContext(), AddNewSessionActivity.class);
                    //bundle passes circuitId and trackLayout to next activity
                    Bundle bundle = bundleArguments();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        bShowLineChart = (Button) view.findViewById(R.id.b_show_line_chart);
        bShowLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkIfRadioButtonIsChecked(rgTrackLayout)) {
                    if (getCheckedTrackLayout().equals("Full") && allSessionsFull < 2) {
                        ShowEmptySessionsToast();
                    } else if (getCheckedTrackLayout().equals("Short") && allSessionsShort < 2) {
                        ShowEmptySessionsToast();
                    } else {
                        Intent intent = new Intent(getActivity().getBaseContext(), DrawLineChartActivity.class);
                        Bundle bundle = bundleArguments();
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });

        if(bundle != null) {
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

    private String getCheckedTrackLayout() {
        int id = rgTrackLayout.getCheckedRadioButtonId();
        View radioButton = rgTrackLayout.findViewById(id);
        int radioId = rgTrackLayout.indexOfChild(radioButton);
        RadioButton btn = (RadioButton) rgTrackLayout.getChildAt(radioId);
        String radioValue = (String) btn.getText();
        return radioValue.split(" ")[0];
    }

    private Bundle bundleArguments() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("" + circuitId);
        arguments.add(getCheckedTrackLayout());
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("args", arguments);
        return bundle;
    }

    private void ShowEmptySessionsToast(){
        CharSequence text = "There aren't enough sessions";
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        KartingDbHelper dbHelper = new KartingDbHelper(getActivity().getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SessionCursors sessionCursors = new SessionCursors(db);

        Cursor shortCircuit = sessionCursors.getAllSessionsShortByCircuitId(circuitId);
        Cursor fullCircuit = sessionCursors.getAllSessionsFullByCircuitId(circuitId);
        if(adapterFull != null && adapterShort != null){
            adapterShort.swapCursor(shortCircuit);
            adapterFull.swapCursor(fullCircuit);

            allSessionsShort =shortCircuit.getCount();
            allSessionsFull = fullCircuit.getCount();


        }

    }


}

package be.pxl.kartingapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;

public class CircuitListFragment extends Fragment implements CallbackInterface {

    private SQLiteDatabase db;
    private List<String> kartingNames;
    private List<String> kartingAddresses;
    private KartingDbHelper dbHelper;
    private CircuitListAdapter adapter;
    private CircuitCursors circuitCursors;
    private RecyclerView kartingRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_circuitlist_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new KartingDbHelper((MainActivity)getActivity());
        db = dbHelper.getWritableDatabase();

        circuitCursors = new CircuitCursors(db);

        kartingRecyclerView = getView().findViewById(R.id.rv_circuits);
        kartingRecyclerView.setLayoutManager(new LinearLayoutManager((MainActivity)getActivity()));

        adapter = new CircuitListAdapter((MainActivity)getActivity(), circuitCursors.getAllCircuits(), this);
        kartingRecyclerView.setAdapter(adapter);

        //api call
        GetAllKartingsTask getAllKartingsTask = new GetAllKartingsTask();
        getAllKartingsTask.delegate = this;
        getAllKartingsTask.execute();
    }

    @Override
    public void processFinished(List<String> names, List<String> addresses) {
        kartingNames = names;
        kartingAddresses = addresses;

        for (int i = 0; i < names.size(); i++) {
            dbHelper.insertCircuit(names.get(i), addresses.get(i));
        }

        adapter = new CircuitListAdapter((MainActivity)getActivity(), circuitCursors.getAllCircuits(), this);
        kartingRecyclerView.setAdapter(adapter);
    }

}

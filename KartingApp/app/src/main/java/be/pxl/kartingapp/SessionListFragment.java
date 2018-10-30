package be.pxl.kartingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import be.pxl.kartingapp.data.CircuitCursors;
import be.pxl.kartingapp.data.KartingDbHelper;

public class SessionListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sessions_fragment, container, false);

        //TODO Complete SessionListFragment

        Bundle bundle = getArguments();


        CircuitListAdapter adapter;
        RecyclerView kartingRecyclerView;

        kartingRecyclerView =  getView().findViewById(R.id.rv_sessions);
        kartingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //adapter = new SessionListAdapter(getActivity(), , this);
        //kartingRecyclerView.setAdapter(adapter);

        return view;
    }
}

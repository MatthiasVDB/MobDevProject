package be.pxl.kartingapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import be.pxl.kartingapp.models.Session;

public class SessionListAdapter extends RecyclerView.Adapter<SessionListAdapter.SessionListViewHolder> {
    private Context context;
    private Cursor cursor;
    private SessionListFragment fragment;

    public SessionListAdapter(Context context, Cursor cursor, SessionListFragment fragment) {
        this.context = context;
        this.cursor = cursor;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SessionListAdapter.SessionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sessionrecyclerview_list_item, parent, false);
        return new SessionListAdapter.SessionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionListAdapter.SessionListViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String sessionDate = cursor.getString(cursor.getColumnIndex("sessionDate"));

        holder.dateTextView.setText(sessionDate);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor!= null) {
            cursor.close();
        }

        cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }


    class SessionListViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView dateTextView;

        private SessionListViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.tv_sessionDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int selectedPosition = getAdapterPosition();

            ArrayList<Long> sessions = new ArrayList<>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                sessions.add(cursor.getLong(cursor.getColumnIndex("_id")));
                cursor.moveToNext();
            }

            long selectedSessionId = sessions.get(selectedPosition);

            LapListFragment lapListFragment = (LapListFragment) fragment.getFragmentManager().findFragmentById(R.id.laps);
            if (lapListFragment != null && lapListFragment.isVisible()) {
                // Visible: send bundle
                LapListFragment newFragment = new LapListFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("session", selectedSessionId);
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.replace(lapListFragment.getId(), newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
            else {
                // Not visible: start as intent
                Intent intent = new Intent(fragment.getActivity().getBaseContext(), LapsActivity.class);
                intent.putExtra("session", selectedSessionId);
                fragment.getActivity().startActivity(intent);
            }
        }
    }
}

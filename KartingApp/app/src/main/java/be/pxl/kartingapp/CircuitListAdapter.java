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

import java.util.ArrayList;

public class CircuitListAdapter extends RecyclerView.Adapter<CircuitListAdapter.CircuitListViewHolder> {

    private Context context;
    private Cursor cursor;
    private CircuitListFragment fragment;

    public CircuitListAdapter(Context context, Cursor cursor, CircuitListFragment fragment) {
        this.context = context;
        this.cursor = cursor;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CircuitListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.circuitrecyclerview_list_item, parent, false);
        return new CircuitListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CircuitListViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String circuit = cursor.getString(cursor.getColumnIndex("name"));
        String address = cursor.getString(cursor.getColumnIndex("address"));

        address = address.replace(", Belgium", "");

        holder.circuitTextView.setText(circuit);
        holder.addressTextView.setText(address);
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


    class CircuitListViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView addressTextView;
        TextView circuitTextView;

        private CircuitListViewHolder(View itemView) {
            super(itemView);
            circuitTextView = itemView.findViewById(R.id.tv_circuitName);
            addressTextView = itemView.findViewById(R.id.tv_circuitAddress);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ArrayList<String> selectedItem = new ArrayList<>();
            selectedItem.add(addressTextView.getText().toString());
            selectedItem.add(circuitTextView.getText().toString());

            SessionListFragment sessionListFragment = (SessionListFragment) fragment.getFragmentManager().findFragmentById(R.id.sessions);
            if (sessionListFragment != null && sessionListFragment.isVisible()) {
                // Visible: send bundle
                SessionListFragment newFragment = new SessionListFragment();
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("circuit", selectedItem);
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
                transaction.replace(sessionListFragment.getId(), newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
            else {
                // Not visible: start as intent
                Intent intent = new Intent(fragment.getActivity().getBaseContext(), SessionsActivity.class);
                intent.putStringArrayListExtra("circuit", selectedItem);
                fragment.getActivity().startActivity(intent);
            }
        }
    }
}

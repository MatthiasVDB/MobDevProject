package be.pxl.kartingapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CircuitListAdapter extends RecyclerView.Adapter<CircuitListAdapter.CircuitListViewHolder> {

    private Context context;
    private Cursor cursor;

    public CircuitListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
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


    class CircuitListViewHolder extends RecyclerView.ViewHolder {
        TextView addressTextView;
        TextView circuitTextView;

        private CircuitListViewHolder(View itemView) {
            super(itemView);
            circuitTextView = itemView.findViewById(R.id.tv_circuitName);
            addressTextView = itemView.findViewById(R.id.tv_circuitAddress);
        }
    }
}

package be.pxl.kartingapp.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.pxl.kartingapp.R;

public class KartingAdapter extends RecyclerView.Adapter<KartingAdapter.KartingListViewHolder> {

    private Context context;
    private Cursor cursor;

    public KartingAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public KartingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.kartingrecyclerview_list_item, parent, false);
        return new KartingListViewHolder(view);
        */
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull KartingListViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String circuit = cursor.getString(cursor.getColumnIndex("_id"));
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

    /*
    public void swapCursor(Cursor newCursor) {
        if (cursor!= null) {
            cursor.close();
        }

        cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
    */

    class KartingListViewHolder extends RecyclerView.ViewHolder {
        TextView addressTextView;
        TextView circuitTextView;

        private KartingListViewHolder(View itemView) {
            super(itemView);
            //circuitTextView = itemView.findViewById(R.id.tv_name);
            //addressTextView = itemView.findViewById(R.id.tv_address);
        }
    }
}

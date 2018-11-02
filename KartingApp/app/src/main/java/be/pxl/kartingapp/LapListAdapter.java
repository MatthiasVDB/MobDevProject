package be.pxl.kartingapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LapListAdapter extends RecyclerView.Adapter<LapListAdapter.LapListViewHolder> {
    private Context context;
    private Cursor cursor;

    public LapListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public LapListAdapter.LapListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.laprecyclerview_list_item, parent, false);
        return new LapListAdapter.LapListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LapListAdapter.LapListViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String lapTime = cursor.getString(cursor.getColumnIndex("lapTime"));

        holder.lapTimeTextView.setText(lapTime);
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


    class LapListViewHolder extends RecyclerView.ViewHolder {
        TextView lapTimeTextView;

        private LapListViewHolder(View itemView) {
            super(itemView);
            lapTimeTextView = itemView.findViewById(R.id.tv_lapTime);
        }
    }
}

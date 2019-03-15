package nl.stefandv.level_5_assignment_2.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nl.stefandv.level_5_assignment_2.R;
import nl.stefandv.level_5_assignment_2.model.Game;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Game> mData;

    public RecyclerViewAdapter(List<Game> data) {
        this.mData = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final Game game = mData.get(position);
        holder.titleTextView.setText((game.getMTitle()));
        holder.statusTextView.setText((game.getMStatus()));
        holder.platformTextView.setText((game.getMPlatform()));
        holder.dateTextView.setText(game.getMdate());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView statusTextView;
        TextView dateTextView;
        TextView platformTextView;
        CardView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            platformTextView = itemView.findViewById(R.id.platformTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            parentLayout = itemView.findViewById(R.id.card_view);
            parentLayout.setClickable(true);

        }
    }
    public void swapList (List<Game> newList) {
        mData = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

}
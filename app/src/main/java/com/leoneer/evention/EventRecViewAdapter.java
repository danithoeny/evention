package com.leoneer.evention;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRecViewAdapter extends RecyclerView.Adapter<EventRecViewAdapter.Viewholder> {

    private ArrayList<Event> eventList = new ArrayList<>();
    private final Context context;

    public EventRecViewAdapter(Context context, ArrayList<Event> eventArrayList) {
        this.context = context;
        this.eventList = eventArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventcard, parent, false);
        Viewholder holder = new Viewholder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("allEvents_ArrayList", eventList);

        holder.txtEventName.setText(eventList.get(position).getEventName());
        holder.txtEventMode.setText(eventList.get(position).getEventMode());

        if (position == 0){
            holder.btnDelete.setVisibility(View.INVISIBLE);
            holder.txtNumTeams.setVisibility(View.INVISIBLE);
        } else {
            if (eventList.get(position).getPointsWin() > 0) {
                holder.txtWinPts.setText("win pts.: " + eventList.get(position).getPointsWin());
                holder.txtWinPts.setVisibility(View.VISIBLE);
            }
            if (eventList.get(position).getPointsDraw() > 0) {
                holder.txtDrawPts.setText("draw pts.: " + eventList.get(position).getPointsDraw());
                holder.txtDrawPts.setVisibility(View.VISIBLE);
            }
            holder.txtNumTeams.setText(eventList.get(position).getEventTeams().size() + " teams");
        }

        switch (eventList.get(position).getEventMode()) {
            case "League":
                holder.eventImage.setImageResource(R.drawable.league);
                break;
            case "K.O.-System":
                holder.eventImage.setImageResource(R.drawable.kosystem);
                break;
            case "Tournament":
                holder.eventImage.setImageResource(R.drawable.tournament);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0){
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        FragmentNewEvent fragmentNewEvent = new FragmentNewEvent();
                        fragmentNewEvent.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                fragmentNewEvent).commit();
                } else {
                        Toast.makeText(context, eventList.get(position).getEventName() + " clicked!", Toast.LENGTH_SHORT).show();

                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        FragmentEvent fragmentNewEvent = new FragmentEvent(position);
                        fragmentNewEvent.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                fragmentNewEvent).addToBackStack(null).commit();
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
                PrefConfig.writeEventListInPref(holder.itemView.getContext(), eventList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public ArrayList<Event> getEvents() {
        return eventList;
    }

    public void setEvents(ArrayList<Event> events) {
        this.eventList = events;
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private final TextView txtEventName;
        private final TextView txtEventMode;
        private final TextView txtWinPts;
        private final TextView txtDrawPts;
        private final ImageView eventImage;
        private final ImageButton btnDelete;
        private final TextView txtNumTeams;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtEventMode = itemView.findViewById(R.id.txtEventMode);
            eventImage = itemView.findViewById(R.id.eventImage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            txtNumTeams = itemView.findViewById(R.id.txtNumTeams);
            txtWinPts = itemView.findViewById(R.id.txtWinPts);
            txtDrawPts = itemView.findViewById(R.id.txtDrawPts);
        }
    }

    public void removeAt(int position){
        eventList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eventList.size());
    }
}

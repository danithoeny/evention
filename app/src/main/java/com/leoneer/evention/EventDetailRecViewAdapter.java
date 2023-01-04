package com.leoneer.evention;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventDetailRecViewAdapter extends RecyclerView.Adapter<EventDetailRecViewAdapter.Viewholder> {

    private ArrayList<Team> eventTeams = new ArrayList<>();
    private ArrayList<Event> eventList;

    public EventDetailRecViewAdapter(ArrayList<Event> events) {
        this.eventList = events;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamcard,parent,false);
        Viewholder holder = new Viewholder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        int platz = position + 1;

        holder.txtTeamName.setText(eventTeams.get(position).getTeamName());

        String playerNames = "";
        for (int j = 0; j < eventTeams.get(position).getTeamPlayers().size(); j++){
            if (j==0){
                playerNames = eventTeams.get(position).getTeamPlayers().get(j).getPlayerName();
            } else {
                playerNames = playerNames + "\n" + eventTeams.get(position).getTeamPlayers().get(j).getPlayerName();
            }
        }
        holder.txtPlayerName.setText(playerNames);

        holder.txtFor.setText("" + eventTeams.get(position).getTeamFor());
        holder.txtAgainst.setText("" + eventTeams.get(position).getTeamAgainst());
        holder.txtDiff.setText("" + eventTeams.get(position).getTeamDiff());
        holder.txtPts.setText("" + eventTeams.get(position).getTeamPoints());

    }

    @Override
    public int getItemCount() {
        return eventTeams.size();
    }

    public ArrayList<Team> getTeamsArrayList() {
        return eventTeams;
    }

    public void setPlayerArrayList(ArrayList<Team> teams) {
        this.eventTeams = teams;
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private final TextView txtTeamName;
        private final RelativeLayout parent;
        private final TextView txtPlayerName;
        private final TextView txtFor;
        private final TextView txtAgainst;
        private final TextView txtDiff;
        private TextView txtPts;



        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtTeamName = itemView.findViewById(R.id.txtTeamName);
            txtPlayerName = itemView.findViewById(R.id.txtPlayer);
            parent = itemView.findViewById(R.id.parent);
            txtFor = itemView.findViewById(R.id.txtFor);
            txtAgainst = itemView.findViewById(R.id.txtAgst);
            txtDiff = itemView.findViewById(R.id.txtDiff);
            txtPts = itemView.findViewById(R.id.txtPts);
        }
    }
}

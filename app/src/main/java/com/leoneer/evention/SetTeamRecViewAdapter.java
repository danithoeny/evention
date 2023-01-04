package com.leoneer.evention;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SetTeamRecViewAdapter extends RecyclerView.Adapter<SetTeamRecViewAdapter.Viewholder> {

    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private boolean inATeam;

    public SetTeamRecViewAdapter() {
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setplayercard,parent,false);
        Viewholder holder = new Viewholder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        int platz = position + 1;
        holder.rlCard.setBackgroundColor(Color.RED);

        holder.txtName.setText(platz + " " + playerArrayList.get(position).getPlayerName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (playerArrayList.get(position).isInATeam() == true){
                    holder.rlCard.setBackgroundColor(Color.RED);
                    playerArrayList.get(position).setInATeam(false);
                } else {
                    holder.rlCard.setBackgroundColor(Color.GREEN);
                    playerArrayList.get(position).setInATeam(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerArrayList.size();
    }

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private final TextView txtName;
        private final RelativeLayout parent;
        private final RelativeLayout rlCard;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            parent = itemView.findViewById(R.id.parent);
            rlCard = itemView.findViewById(R.id.rlCard);


        }
    }

    public void removeAt(int position){
        playerArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, playerArrayList.size());
    }
}

package com.leoneer.evention;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayersRecViewAdapter extends RecyclerView.Adapter<PlayersRecViewAdapter.Viewholder> {

    private ArrayList<Player> playerArrayList = new ArrayList<>();

    public PlayersRecViewAdapter() {
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playercard,parent,false);
        Viewholder holder = new Viewholder(view);
        ArrayList<Player> playerSafe = new ArrayList<>();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        int platz = position + 1;
        holder.txtName.setText(platz + " " + playerArrayList.get(position).getPlayerName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
                PrefConfig.writePlayerListInPref(holder.itemView.getContext(), playerArrayList);
            }
        });


        if(PrefConfig.readPlayerListFromPref(holder.itemView.getContext()).get(position).isPlayerActive() == true) {
            holder.ckbPlayerActive.setChecked(true);
        }

        if(PrefConfig.readPlayerListFromPref(holder.itemView.getContext()).get(position).isPlayerActive() == false) {
            holder.ckbPlayerActive.setChecked(false);
        } //NEU

        holder.ckbPlayerActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    playerArrayList.get(position).setPlayerActive(isChecked);
                    PrefConfig.writePlayerListInPref(holder.itemView.getContext(), playerArrayList);
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

        private final CheckBox ckbPlayerActive;
        private final TextView txtName;
        private final RelativeLayout parent;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            ckbPlayerActive = itemView.findViewById(R.id.ckbPlayerActive);
            txtName = itemView.findViewById(R.id.txtName);
            parent = itemView.findViewById(R.id.parent);

        }
    }

    public void removeAt(int position){
        playerArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, playerArrayList.size());
    }
}

package com.leoneer.evention;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameDayRecViewAdapter extends RecyclerView.Adapter<GameDayRecViewAdapter.Viewholder> {

    private ArrayList<Game> eventGames = new ArrayList<>();
    private ArrayList<Event> eventList;
    private Integer eventNum;
    private Integer oldScore1;
    private Integer oldScore2;
    private Integer oldPts1;
    private Integer oldPts2;

    public GameDayRecViewAdapter(ArrayList<Event> events, int num) {
        this.eventList = events;
        this.eventNum = num;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gamecard,parent,false);
        Viewholder holder = new Viewholder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.txtTeamName1.setText(eventGames.get(position).getTeam1());
        holder.txtTeamName2.setText(eventGames.get(position).getTeam2());

        if(eventGames.get(position).isEnded() == true){
            holder.txtTeamScore1.setText("" + eventList.get(eventNum).getEventGames().get(position).getScore1());
            holder.txtTeamScore2.setText("" + eventList.get(eventNum).getEventGames().get(position).getScore2());
            holder.gameCard.setBackgroundColor(Color.GRAY);
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strOldScore1 = holder.txtTeamScore1.getText().toString();
                oldScore1 = new Integer(strOldScore1).intValue();

                String strOldScore2 = holder.txtTeamScore2.getText().toString();
                oldScore2 = new Integer(strOldScore2).intValue();

                holder.edtTeamScore1.setText(holder.txtTeamScore1.getText());
                holder.edtTeamScore2.setText(holder.txtTeamScore2.getText());

                holder.btnCheck.setVisibility(View.VISIBLE);
                holder.edtTeamScore1.setVisibility(View.VISIBLE);
                holder.edtTeamScore2.setVisibility(View.VISIBLE);

                holder.btnEdit.setVisibility(View.INVISIBLE);
                holder.txtTeamScore1.setVisibility(View.INVISIBLE);
                holder.txtTeamScore2.setVisibility(View.INVISIBLE);
            }
        });

        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtTeamScore1.setText(holder.edtTeamScore1.getText());
                holder.txtTeamScore2.setText(holder.edtTeamScore2.getText());

                String stringScore1 = holder.edtTeamScore1.getText().toString();
                int score1 = new Integer(stringScore1).intValue();
                eventGames.get(position).setScore1(score1);

                String stringScore2 = holder.edtTeamScore2.getText().toString();
                int score2 = new Integer(stringScore2).intValue();
                eventGames.get(position).setScore2(score2);

                int dif1 = score1 - oldScore1;
                int dif2 = score2 - oldScore2;

                int result = score1 - score2;
                int pts1 = 0;
                int pts2 = 0;

                if (result > 0) {
                    pts1 = eventList.get(eventNum).getPointsWin();
                    pts2 = 0;
                }
                if(result < 0) {
                    pts1 = 0;
                    pts2 = eventList.get(eventNum).getPointsWin();
                }
                if (result == 0) {
                    if (eventList.get(eventNum).getPointsDraw() == 0) {
                        pts1 = 0;
                        pts2 = 0;
                    } else {
                        pts1 = eventList.get(eventNum).getPointsDraw();
                        pts2 = eventList.get(eventNum).getPointsDraw();
                    }
                }

                oldPts1 = eventGames.get(position).getPts1();
                oldPts2 = eventGames.get(position).getPts2();

                int difPts1;
                int difPts2;

                if (oldPts1 != null && oldPts2 != null){
                    difPts1 = pts1 - oldPts1;
                    difPts2 = pts2 - oldPts2;
                } else {
                    difPts1 = pts1;
                    difPts2 = pts2;
                }

                for (int i = 0; i < eventList.get(eventNum).getEventTeams().size(); i++){
                    int actFor = eventList.get(eventNum).getEventTeams().get(i).getTeamFor();
                    int actAg = eventList.get(eventNum).getEventTeams().get(i).getTeamAgainst();
                    int actP = eventList.get(eventNum).getEventTeams().get(i).getTeamPoints();

                    if (eventList.get(eventNum).getEventTeams().get(i).getTeamName().equals(eventGames.get(position).getTeam1())){
                        eventList.get(eventNum).getEventTeams().get(i).setTeamFor(actFor + dif1);
                        eventList.get(eventNum).getEventTeams().get(i).setTeamAgainst(actAg + dif2);
                        eventList.get(eventNum).getEventTeams().get(i).setTeamDiff((actFor+dif1)-(actAg+dif2));

                        eventList.get(eventNum).getEventTeams().get(i).setTeamPoints(actP + difPts1);
                    }

                    if (eventList.get(eventNum).getEventTeams().get(i).getTeamName().equals(eventGames.get(position).getTeam2())){
                        eventList.get(eventNum).getEventTeams().get(i).setTeamFor(actFor + dif2);
                        eventList.get(eventNum).getEventTeams().get(i).setTeamAgainst(actAg + dif1);
                        eventList.get(eventNum).getEventTeams().get(i).setTeamDiff((actFor+dif2)-(actAg+dif1));

                        eventList.get(eventNum).getEventTeams().get(i).setTeamPoints(actP + difPts2);
                    }

                    eventGames.get(position).setPts1(pts1);
                    eventGames.get(position).setPts2(pts2);
                }

                eventGames.get(position).setEnded(true);
                holder.gameCard.setBackgroundColor(Color.GRAY);

                eventList.get(eventNum).setEventGames(eventGames);



                PrefConfig.writeEventListInPref(holder.itemView.getContext(), eventList);

                holder.btnCheck.setVisibility(View.INVISIBLE);
                holder.edtTeamScore1.setVisibility(View.INVISIBLE);
                holder.edtTeamScore2.setVisibility(View.INVISIBLE);

                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.txtTeamScore1.setVisibility(View.VISIBLE);
                holder.txtTeamScore2.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventGames.size();
    }

    public ArrayList<Game> getGamesArrayList() {
        return eventGames;
    }

    public void setGamesArrayList(ArrayList<Game> games) {
        this.eventGames = games;
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private final TextView txtTeamName1;
        private final TextView txtTeamName2;
        private final RelativeLayout parent;
        private final TextView txtPlayerName;
        private final ImageButton btnEdit;
        private final ImageButton btnCheck;
        private final TextView txtTeamScore1;
        private final TextView txtTeamScore2;
        private final EditText edtTeamScore1;
        private final EditText edtTeamScore2;
        private RelativeLayout gameCard;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txtTeamName1 = itemView.findViewById(R.id.txtTeamName1);
            txtTeamName2 = itemView.findViewById(R.id.txtTeamName2);
            txtPlayerName = itemView.findViewById(R.id.txtPlayer);
            parent = itemView.findViewById(R.id.parent);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnCheck = itemView.findViewById(R.id.btnCheck);
            txtTeamScore1 = itemView.findViewById(R.id.txtTeam1Score);
            txtTeamScore2 = itemView.findViewById(R.id.txtTeam2Score);
            edtTeamScore1 = itemView.findViewById(R.id.edtTeam1Score);
            edtTeamScore2 = itemView.findViewById(R.id.edtTeam2Score);
            gameCard = itemView.findViewById(R.id.gameCard);

        }
    }
}

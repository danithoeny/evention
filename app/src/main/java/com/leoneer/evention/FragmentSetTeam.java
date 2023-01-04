package com.leoneer.evention;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FragmentSetTeam extends Fragment {

    private final ArrayList<Player> activePlayersList;
    private RecyclerView rvActPlayerList;
    private TextView txtComment;
    private ArrayList<Player> newTeamPlayers;
    private ArrayList<Player> leftOvers;
    private final int teamNummer;
    private ImageButton btnNextTeam;
    private final ArrayList<Event> eventList;
    private ArrayList<Team> eventTeams;
    private ArrayList<ArrayList<Game>> eventGameDays;
    private int numGameDays;
    private ArrayList<Game> eventGames;

    public FragmentSetTeam(ArrayList<Event> events, ArrayList<Team> teams, ArrayList<Player> activePlayers, int teamNumber) {
        eventList = events;
        eventTeams = teams;
        activePlayersList = activePlayers;
        teamNummer = teamNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_team, container, false);
        rvActPlayerList = view.findViewById(R.id.rvActPlayerList);
        txtComment = view.findViewById(R.id.txtComment);
        btnNextTeam = view.findViewById(R.id.btnNextTeam);

        txtComment.setText("Select Players for Team " + teamNummer + ":");

        showPlayers();

        btnNextTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTeamPlayers = new ArrayList<>();
                leftOvers = new ArrayList<>();

                if (eventTeams.size() == 0){
                    eventTeams = new ArrayList<>();
                }

                for (int i = 0; i < activePlayersList.size(); i++){
                    if (activePlayersList.get(i).isInATeam()){
                        newTeamPlayers.add(activePlayersList.get(i));
                    } else {
                        leftOvers.add(activePlayersList.get(i));
                    }
                }

                Team newTeam = new Team("Team " + teamNummer, newTeamPlayers, true);
                eventTeams.add(newTeam);



                if (leftOvers.size() == 0){
                    eventList.get(eventList.size()-1).setEventTeams(eventTeams);

                    createGameDays(eventTeams);
                    eventList.get(eventList.size()-1).setEventGames(eventGames);

                    PrefConfig.writeEventListInPref(getActivity().getApplicationContext(),eventList);
                    Toast.makeText( getActivity(),eventList.get(eventList.size()-1).getEventName() + " was saved!",Toast.LENGTH_SHORT).show();

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentEventView fragment = new FragmentEventView();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            fragment).commit();
                } else {
                    Toast.makeText( getActivity(), "Team " + teamNummer + " created!",Toast.LENGTH_SHORT).show();

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentSetTeam fragment = new FragmentSetTeam(eventList,eventTeams,leftOvers, teamNummer + 1);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                            fragment).commit();

                }
            }
        });

        return view;
    }

    public void showPlayers() {
        SetTeamRecViewAdapter adapter = new SetTeamRecViewAdapter();
        adapter.setPlayerArrayList(activePlayersList);
        rvActPlayerList.setAdapter(adapter);
        rvActPlayerList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void createGameDays(ArrayList<Team> teams) {
        eventGames = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            Team team1 = teams.get(i);
            for (int j = i + 1; j < teams.size(); j++) {
                Team team2 = teams.get(j);
                eventGames.add(new Game(team1.getTeamName(), team2.getTeamName()));
            }
        }

    }
}